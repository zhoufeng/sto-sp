package com.shenma.top.imagecopy.util.reqdubbo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcException;
import com.bohusoft.dubboapi.service.AliImageHandleService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.shenma.top.imagecopy.util.exception.BusinessException;

@Component
public class ImageSaveForDubboUtil{
	protected static Logger logger = Logger.getLogger("ImageSaveForDubboUtil");
	@Autowired
	private AliImageHandleService aliImageHandleService;
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	@Autowired
	private AliConstant aliConstant;
	
	public Map<String,Object> saveImage(String url,String albumId,String name,String description){
		Map<String,Object> remoteMap=null;
		try {
			remoteMap=aliImageHandleService.saveImage(url, albumId, name,"",SessionUtil.getAliSession());
		} catch (RpcException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessException.RPC_ERROR,"dubbo远程请求失败,请再试或者联系管理员");
		}
		if(remoteMap.containsKey("errorMsg")){
			throw new BusinessException(BusinessException.COMMON,remoteMap.get("errorMsg").toString());
		}
		return remoteMap;
	}
	
	public Map<String,Object> saveImages(List<String> romteList,String albumId,String name,String description) throws BusinessException{
		Map<String,Object> remoteMap=null;
		try {
			remoteMap=aliImageHandleService.saveImages(romteList, albumId, name,SessionUtil.getAliSession());
		} catch (RpcException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessException.RPC_ERROR,"dubbo远程请求失败,请再试或者联系管理员");
		}
		if(remoteMap.containsKey("errorMsg")){
			throw new BusinessException(BusinessException.COMMON,remoteMap.get("errorMsg").toString());
		}
		return remoteMap;
	}
	
	/*public Map<String,Object> saveImage(String url,String albumId,String name,String description) throws ApiException, AliReqException{
		Map<String, Object> ret=new HashMap<String,Object>(2);
		if(url.endsWith("startFlag.gif")||url.endsWith("endFlag.gif")){
			ret.put("newUrl", url);
			return ret;
		}
		byte[] imgByte = ImageUtil.readUrlImage(url);
		if (imgByte == null || imgByte.length == 0)
			throw new BusinessException(ErrorMessages.image_url_not_exit,
					"图片地址不存在url:" + url);
		long maxSize = (2 << 16) * 30;
		if (imgByte.length > maxSize) {
			throw new ApiException("文件超过3M");
		}
		Map<String, Object> bean = albumService.uploadImage(albumId,name, description, imgByte);
		Map<String, Object> result = (Map<String, Object>) bean.get("result");
		List<Map<String, Object>> rtlist = (List<Map<String, Object>>) result.get("toReturn");
		String lastImageUrl = (String) rtlist.get(0).get("url");
		lastImageUrl = aliConstant.image_uri_prefix + lastImageUrl;
		ret.put("newUrl", lastImageUrl);
		return ret;
		
	}*/
}
