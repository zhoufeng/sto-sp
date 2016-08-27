package com.bohusoft.htmlfetch.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohusoft.dubboapi.entity.ImageBean;
import com.bohusoft.dubboapi.exception.DubboApiException;
import com.bohusoft.dubboapi.exception.DubboApiExceptionEnums;
import com.bohusoft.dubboapi.service.AliImageHandleService;
import com.bohusoft.htmlfetch.util.ImageSaveManager;
import com.bohusoft.htmlfetch.util.ProviderSessionUtil;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
@Service
public class AliImageHandleServiceImp implements AliImageHandleService{
	private static final long maxSize = (2 << 16) * 30; //3m的文件大小
	protected static Logger logger = Logger.getLogger("AliImageHandleServiceImp");
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AliConstant aliConstant;
	
	@Autowired
	private ProviderSessionUtil providerSessionUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> saveImages(List<String> urlList,String albumId,String name,AliToken aliToken) {
		Map<String,Object> ret=new HashMap<String,Object>(4);
		//将alitoken注入到session
		SessionUtil.setAliSession(aliToken);
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		ret.put("resultList", resultList);
		
		//多线程请求图片
		List<ImageBean> list=null;
		try {
			list=ImageSaveManager.reqImages(urlList);			
		} catch (Exception e) {
			logger.error("读取图片出错!地址:"+e.getMessage());
			ret.put("errorMsg", "读取图片出错!");
		}
		
		//多线程
		List<Future<Map<String,Object>>> fuList=new ArrayList<Future<Map<String,Object>>>(list.size());
		for(ImageBean bean:list){
			byte[] data=bean.getData();
			String url = bean.getUrl();
			Map<String,Object> bMap=new HashMap<String,Object>(3);
			bMap.put("url", url);
			resultList.add(bMap);
			try{
				if (data == null || data.length == 0)
					throw new DubboApiException(DubboApiExceptionEnums.URL_NO_FOUND);
				if (data.length > maxSize) {
					throw new DubboApiException(DubboApiExceptionEnums.PIC_MAXSIZE);
				}
				Future<Map<String,Object>> fu=ImageSaveManager.saveToAli(albumId,name,data,bMap,albumService);
				fuList.add(fu);
			} catch (DubboApiException e){
				logger.error("保存图片出错!地址:"+url+":"+e.getMessage());
				bMap.put("errorMsg", "保存图片出错!请在试一次!");
			}catch (Exception e) {
				logger.error("保存图片出错!地址:"+url+":"+e.getMessage());
				bMap.put("errorMsg", "保存图片错误!");
			}
		}
		Map<String,Integer> convergeMap=new HashMap<String,Integer>();
		String errorCodes="";
		//错误格式修改
		for(Future<Map<String,Object>> fu:fuList){
			try {
				Map<String,Object> retMap=fu.get();
				if(retMap.containsKey("errorCode")){
					convergeMap.put(retMap.get("errorCode").toString(), convergeMap.get("errorMsg"));
				}
			} catch (InterruptedException e) {
				logger.error("保存阿里巴巴服务器获取出future出问题");
			} catch (ExecutionException e) {
				logger.error("保存阿里巴巴服务器获取出问题");
			}
		}
		String errorMsg="";
		if(convergeMap.containsKey(DubboApiExceptionEnums.PIC_SPACE_FULL.getCode())){
			ret.put("errorCode",DubboApiExceptionEnums.PIC_SPACE_FULL.getCode());
			ret.put("errorMsg",DubboApiExceptionEnums.PIC_SPACE_FULL.getMessage());
		}
		if(convergeMap.containsKey(DubboApiExceptionEnums.ALBUM_FULL.getCode())){
			ret.put("errorCode",DubboApiExceptionEnums.ALBUM_FULL.getCode());
			ret.put("errorMsg",DubboApiExceptionEnums.ALBUM_FULL.getMessage());
		}
		return ret;
	}

	@SuppressWarnings({ "unused", "unchecked"})
	@Override
	public Map<String,Object> saveImage(String url,String albumId,String name,String description,AliToken aliToken) {
		Map<String,Object> ret=new HashMap<String,Object>(4);
		//将alitoken注入到session
		SessionUtil.setAliSession(aliToken);
		
		ImageBean bean=null;
		try {
			bean=ImageSaveManager.reqImage(url);
			byte[] data=bean.getData();
			if (data == null || data.length == 0)
				throw new DubboApiException(DubboApiExceptionEnums.URL_NO_FOUND);
			if (data.length > maxSize) {
				throw new DubboApiException(DubboApiExceptionEnums.PIC_MAXSIZE);
			}
			Map<String, Object> imageBean = albumService.uploadImage(albumId,name, description, data);
			Map<String, Object> result = ((Map<String, Object>) imageBean.get("result"));
			List<Map<String, Object>> rtlist = (List<Map<String, Object>>) result.get("toReturn");
			String lastImageUrl = (String) rtlist.get(0).get("url");
			lastImageUrl = aliConstant.image_uri_prefix + lastImageUrl;
			ret.put("newUrl", lastImageUrl);
		}catch (DubboApiException e) {
			logger.error("保存图片出错!地址:"+url+":"+e.getMessage());
			ret.put("errorMsg","保存图片出错原因:"+e.getMessage());
			ret.put("errorCode", e.getEnums().getCode());
			return ret;
		} catch(AliReqException e){
			logger.error("保存图片出错!地址:"+url+":"+e.getMessage());
			if(e.getCode().equals(DubboApiExceptionEnums.PIC_SPACE_FULL.getCode())){
				ret.put("errorCode",DubboApiExceptionEnums.PIC_SPACE_FULL.getCode());
				ret.put("errorMsg",DubboApiExceptionEnums.PIC_SPACE_FULL.getMessage());
			}
			if(e.getCode().equals(DubboApiExceptionEnums.ALBUM_FULL.getCode())){
				ret.put("errorCode",DubboApiExceptionEnums.ALBUM_FULL.getCode());
				ret.put("errorMsg",DubboApiExceptionEnums.ALBUM_FULL.getMessage());
			}
			return ret;
		}catch (Exception e) {
			logger.error("保存图片出错!地址:"+url+":"+e.getMessage());
			ret.put("errorMsg", "保存图片出错!");
			ret.put("errorCode", DubboApiExceptionEnums.COMMON_FAIL);
			return ret;
		}
		return ret;
		
	}

}
