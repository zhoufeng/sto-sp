package com.shenma.top.imagecopy.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
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
import com.shenma.top.imagecopy.util.RunningTaskManager;
import com.shenma.top.imagecopy.util.asynsave.AsynSaveUtil;
import com.shenma.top.imagecopy.util.exception.BusinessException;

@Service
public class AliForeBackSevice {
	protected static Logger logger = Logger.getLogger("AliForeBackSevice");
	@Autowired
	private AlibabafinalSaveService alibabafinalSaveService;
	
	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	@Autowired
	private AsynSaveUtil asynSaveUtil;
	
	@Autowired
	private SaveTaskDao saveTaskDao;
	
	@Autowired
	private AlibabaDetailHtmlParse alibabaDetailHtmlParse;
	
	public Map<String,Object> saveAliItem(Map<String,Object> variables,boolean isBatch){
		
		boolean picStatus=(boolean) variables.get("picStatus");
		/*if(picStatus){
			return setBackground(variables,isBatch);
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
			
			//设置task
			SaveTask task=new SaveTask();
			variables.put("mqRecordeId", mqItem.getId());
			task.setMemberId(SessionUtil.getAliSession().getMemberId());
			String param=JacksonJsonMapper.getInstance().writeValueAsString(variables);
			task.setParam(param);
			task.setStaus(0);
			task.setType(1);
			task.setBatchType(isBatch?1:0);
			task.setMemberId(aliToken.getMemberId());
			task=saveTaskDao.saveAndFlush(task);
			asynSaveUtil.offerTask(task);

			Integer size=asynSaveUtil.getQueueSize(isBatch);
			ret.put("msg", "已经在排队复制了,在您前面还有"+size+"个复制,请在复制历史里查看进度");
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		String url=(String) variables.get("url");
		MqRecordItem mqItem=new MqRecordItem();
		AliToken aliToken=SessionUtil.getAliSession();
		boolean picStatus=(boolean) variables.get("picStatus");
		String userId=aliToken.getMemberId();
		mqItem.setUserId(userId);
    	mqItem.setUrl(url);
    	mqItem.setCreateTime(new Date());
    	mqItem.setStatus(0);
    	mqItem.setDelStatus(0);
    	mqItem.setPicStatus(picStatus?1:0);
    	mqItem.setUserName(aliToken.getSellerName());
    	mqItem.setAcountName(aliToken.getResourceOwner());
    	String oldOfferId=url.substring(url.indexOf("offer/")+6,url.indexOf(".html"));
    	mqItem.setOldOfferId(oldOfferId);
    	Document doc=JsonpUtil.getAliDefaultConnet(url);
    	String subject = alibabaDetailHtmlParse.parseSubject(doc);
    	mqItem.setTitle(subject);
    	try {
			List<String> images=alibabaDetailHtmlParse.parseZhutu(doc);
			mqItem.setZhutuImage(images.size()>0?images.get(0):null);
		} catch (CopyBussinessException e) {
			logger.error("主图解析失败!url:"+mqItem.getUrl()+e.getMessage());
		}
    	return mqItem;
	}
	
	/**
	 * 前台程序
	 */
	private Map<String,Object> foreground(Map<String,Object> variables){
		MqRecordItem mqItem=initialMqRecordItem(variables);
		String url=(String) variables.get("url");
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
		return saveItem(url, picStatus, mqItem,variables);
	}
	
	/**
	 * 单个复制功能.
	 * @param url
	 * @param picStatus
	 * @param mqItem
	 * @return
	 */
	private Map<String, Object> saveItem(String url, boolean picStatus, MqRecordItem mqItem,Map<String,Object> selfInfo) {
		Offer offer=null;
		Map<String,Object> ret=new HashMap<String,Object>();
		try {
			offer=alibabafinalSaveService.save(url,picStatus,mqItem,selfInfo);
			//mqItem.setOldOfferId(oldOfferId);
			mqItem.setOfferId(offer.getOfferId().toString());
			mqItem.setTitle(offer.getSubject());
			mqItem.setEndTime(new Date());
			//mqItem.setErrorMsg("复制成功");
        	mqItem.setStatus(1);
        	mqRecordItemDao.saveAndFlush(mqItem);
		} catch (AliReqException e) {
			mqItem.setStatus(2);
			mqItem.setErrorMsg(e.getMessage());
			mqRecordItemDao.saveAndFlush(mqItem);
			ret.put("errorCode", e.getCode());
			ret.put("errorMsg", e.getMessage());
			logger.error(e);
			return ret;
		}catch (DuplicateCopyException e) {
			mqRecordItemDao.delete(mqItem);
			ret.put("errorCode", e.getCode());
			ret.put("errorMsg", e.getMessage());
			return ret;
		}catch (BusinessException e) {
			mqItem.setStatus(2);
			mqItem.setErrorMsg(e.getMessage());
			mqRecordItemDao.saveAndFlush(mqItem);
			ret.put("errorCode",e.getCode());
			ret.put("errorMsg", e.getMessage());
			logger.error(e);
			return ret;
		} catch (Exception e) {
			mqItem.setStatus(2);
			String eMsg=e.toString();
			if(e.toString().length()>250){
				eMsg=e.toString().substring(0,250);
			}
			mqItem.setErrorMsg("服务器错误!"+e);
			mqRecordItemDao.saveAndFlush(mqItem);
			ret.put("errorCode", 101);
			ret.put("errorMsg", "服务器错误!");
			logger.error(e);
			return ret;
		}
		
		ret.put("url", "http://detail.1688.com/offer/"+offer.getOfferId()+".html");
		ret.put("editurl", "http://offer.1688.com/offer/post/fill_product_info.htm?offerId="+offer.getOfferId()+"&operator=edit#");
		ret.put("name",offer.getSubject());
		
		return ret;
	}
	
}
