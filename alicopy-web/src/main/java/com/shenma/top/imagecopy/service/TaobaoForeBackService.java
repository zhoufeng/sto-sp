package com.shenma.top.imagecopy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohusoft.alicopy.parse.AlibabaDetailHtmlParse;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.dao.SaveTaskDao;
import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;
import com.shenma.top.imagecopy.ecxeption.DuplicateCopyException;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.entity.SaveTask;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.asynsave.AsynSaveUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;

@Service
public class TaobaoForeBackService {
	protected static Logger logger = Logger.getLogger("TaobaoForeBackService");
	@Autowired
	private TaobaoFinalSaveService taobaoFinalSaveService;
	
	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	@Autowired
	private AsynSaveUtil asynSaveUtil;
	
	@Autowired
	private SaveTaskDao saveTaskDao;
	
	@Autowired
	private AlibabaDetailHtmlParse alibabaDetailHtmlParse;
	
	public Map<String,Object> saveTaobaoItem(Map<String,Object> variables,boolean isBatch){
		//boolean picStatus=(boolean) variables.get("picStatus");
		//return setBackground(variables,isBatch);
		/*if(picStatus){
		}else{
			return foreground(variables);
		}*/
		return foreground(variables);
	}
	
	/**
	 * 后台线程保存
	 */
	private Map<String,Object> setBackground(Map<String,Object> variables,boolean isBatch){
		Map<String,Object> ret=new HashMap<String,Object>();
		AliToken aliToken=SessionUtil.getAliSession();
		try {
			//设置mqRecordItem
			MqRecordItem mqItem=initialMqRecordItem(variables);
			mqItem.setStatus(10); //正在排队复制
			mqItem=mqRecordItemDao.saveAndFlush(mqItem);
			String catId=variables.get("catId").toString();
			//设置task
			SaveTask task=new SaveTask();
			variables.put("mqRecordeId", mqItem.getId());
			variables.put("catId", catId);
			task.setMemberId(SessionUtil.getAliSession().getMemberId());
			String param=JacksonJsonMapper.getInstance().writeValueAsString(variables);
			task.setParam(param);
			task.setStaus(0);
			task.setType(2);
			task.setBatchType(isBatch?1:0);
			task.setMemberId(aliToken.getMemberId());
			task=saveTaskDao.saveAndFlush(task);
			asynSaveUtil.offerTask(task);
			
			Integer size=asynSaveUtil.getQueueSize(isBatch);
			ret.put("msg", "已经在排队复制了,在您前面还有"+size+"个复制,请在复制历史里查看进度");
		} catch (Exception e) {
			logger.error(e);
			ret.put("errorMsg", "复制失败,请联系管理员");
		}
		return ret;
	}
	/**
	 * 初始记录,默认状态status=0
	 * @param variables
	 * @return
	 */
	private MqRecordItem initialMqRecordItem(Map<String,Object> variables){
		String url=variables.get("url").toString();
		Boolean picStatus=Boolean.valueOf(variables.get("picStatus").toString());
		MqRecordItem mqItem=new MqRecordItem();
		AliToken aliToken=SessionUtil.getAliSession();
		mqItem.setUserId(aliToken.getMemberId());
    	mqItem.setUrl(url);
    	mqItem.setCreateTime(new Date());
    	mqItem.setStatus(0);
    	
    	mqItem.setDelStatus(0);
    	mqItem.setPicStatus(picStatus?1:0);
    	mqItem.setUserName(aliToken.getSellerName());
    	mqItem.setAcountName(aliToken.getResourceOwner());
    	String querystr=url.substring(url.indexOf("id=")+3,url.length());
    	String oldOfferId=querystr.split("&")[0];
    	mqItem.setOldOfferId(oldOfferId);
    	Document doc=JsonpUtil.getAliDefaultConnet(url);
    	if(url.contains("taobao.com")){
	    	Element element=doc.select("#J_Title .tb-main-title").first();
			String subject=element.attr("data-title");
	    	mqItem.setTitle(subject);
	    	mqItem.setZhutuImage(setTaobaoZhutuImage(doc));
    	}else if(url.contains("tmall.com")){
    		Pattern pattern = Pattern.compile("(?<=TShop.Setup\\()[\\s\\S]*?(?=\\)\\;)");
    		Matcher matcher = pattern.matcher(doc.html());
    		
    		String obj=null;
    		if(matcher.find()){
    			obj=matcher.group(0);
    		}
    		Map<String, Object> map = null;
    		try {
    			map=JacksonJsonMapper.getInstance().readValue(obj, HashMap.class);
			} catch (Exception e) {
				logger.error("解析天猫标题出错,url:"+url);
			}
    		Map<String, Object> detail=(Map<String, Object>) map.get("itemDO");
    		String subject=detail.get("title").toString();
    		mqItem.setTitle(subject);
    		mqItem.setZhutuImage(genTmailZhutuImage(doc));
    	}
 
    	return mqItem;
	}
	
	/**
	 * 功能描述,处理主图图片
	 * @return
	 */
	public String genTmailZhutuImage(Document doc){
		Element element=doc.getElementById("J_UlThumb");
		Elements images=element.select("img[src]");
		String imageUrl="";
		if(images.size()>0){
			imageUrl=images.get(0).attr("src");
			if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
			imageUrl=imageUrl.substring(0, imageUrl.indexOf(".jpg")+4);
		}
		return imageUrl;
	}
	
	/**
	 * 功能描述,处理主图图片
	 * @return
	 */
	private String setTaobaoZhutuImage(Document doc){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element element=doc.getElementById("J_UlThumb");
		Elements images=element.select("img[data-src]");
		String imageUrl="";
		if(images.size()>0){
			imageUrl=images.get(0).attr("data-src");
			if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
			imageUrl=imageUrl.substring(0, imageUrl.indexOf(".jpg")+4);

		}
		return imageUrl;
	}
	
	/**
	 * 前台程序
	 */
	private Map<String,Object> foreground(Map<String,Object> variables){
		MqRecordItem mqItem=initialMqRecordItem(variables);
		String url=variables.get("url").toString();
		String catId=variables.get("catId").toString();
		boolean picStatus=(boolean) variables.get("picStatus");
		//重复判断
		boolean ignoreType=(boolean) variables.get("ignoreType");
		if(ignoreType){
			String ignoreTypeVal=(String) variables.get("ignoreTypeVal");
			if(ignoreTypeVal.equals("H")){
				List<MqRecordItem> itmsList=mqRecordItemDao.findByUserIdAndOldOfferIdAndStatus(mqItem.getUserId(), mqItem.getOldOfferId(),1);
				if(itmsList.size()>0){
					Map<String,Object> ret=new HashMap<String,Object>();
					ret.put("errorCode","666");
					ret.put("name",mqItem.getUrl());
					ret.put("errorMsg", "已经自动忽略该地址的复制,因为已经存在该地址复制记录,如果要强行复制,请取消忽略重复商品选项");
					return ret;
				}
			}
		}
		mqItem=mqRecordItemDao.saveAndFlush(mqItem);
		return saveItem(url, catId,picStatus, mqItem,variables);
	}
	
	/**
	 * 单个复制功能.
	 * @param url
	 * @param picStatus
	 * @param mqItem
	 * @return
	 */
	private Map<String, Object> saveItem(String url, String catId,boolean picStatus, MqRecordItem mqItem,Map<String,Object> variables) {
		Offer offer=null;
		Map<String,Object> ret=new HashMap<String,Object>();
		try {
			offer=taobaoFinalSaveService.save(url, catId, picStatus, mqItem,variables);
			
			mqItem.setOfferId(offer.getOfferId().toString());
			//mqItem.setErrorMsg("复制成功");
        	mqItem.setStatus(1);
        	mqRecordItemDao.saveAndFlush(mqItem);
		} catch (AliReqException e) {
			mqItem.setStatus(2);
			mqItem.setErrorMsg(catId.toString()+e.getMessage()+":"+e.getCode());
			mqRecordItemDao.saveAndFlush(mqItem);
			ret.put("errorCode", e.getCode());
			ret.put("errorMsg", e.getMessage());
			ret.put("name",mqItem.getTitle());
			logger.error("复制出错,url:["+url+"]",e);
			return ret;
		}catch (DuplicateCopyException e) {
			mqRecordItemDao.delete(mqItem);
			ret.put("errorCode", e.getCode());
			ret.put("errorMsg", e.getMessage());
			logger.error("复制出错,url:["+url+"]",e);
			return ret;
		}catch (BusinessException e) {
			mqItem.setStatus(2);
			mqItem.setErrorMsg(catId.toString()+e.getMessage());
			mqRecordItemDao.saveAndFlush(mqItem);
			ret.put("errorCode",e.getCode());
			ret.put("errorMsg", e.getMessage());
			ret.put("name",mqItem.getTitle());
			logger.error("复制出错,url:["+url+"]",e);
			return ret;
		} catch (Exception e) {
			mqItem.setStatus(2);
			String eMsg=e.toString();
			if(e.toString().length()>250){
				eMsg=e.toString().substring(0,250);
			}
			mqItem.setErrorMsg(catId.toString()+"服务器错误!"+eMsg);
			mqRecordItemDao.saveAndFlush(mqItem);
			ret.put("errorCode", 101);
			ret.put("errorMsg", "服务器错误!");
			ret.put("name",mqItem.getTitle());
			logger.error("宝贝宝贝出错",e);
			return ret;
		} 
		ret.put("url", "http://detail.1688.com/offer/"+offer.getOfferId().toString()+".html");
		ret.put("editurl", "http://offer.1688.com/offer/post/fill_product_info.htm?offerId="+offer.getOfferId().toString()+"&operator=edit");
		ret.put("name",offer.getSubject());
		return ret;
	}
}
