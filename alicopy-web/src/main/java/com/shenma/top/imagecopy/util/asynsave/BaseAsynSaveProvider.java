package com.shenma.top.imagecopy.util.asynsave;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.service.AlibabaRequestService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.dao.SaveTaskDao;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.entity.SaveTask;
import com.shenma.top.imagecopy.service.AlibabafinalSaveService;
import com.shenma.top.imagecopy.service.TaobaoFinalSaveService;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.exception.BusinessException;

public class BaseAsynSaveProvider {
	protected static Logger logger = Logger.getLogger("AsynSaveProvider");
     
	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	@Autowired
	private SaveTaskDao saveTaskDao;
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	@Autowired 
	private AliConstant aliConstant;
	
	@Autowired
	private AlibabafinalSaveService alibabafinalSaveService;
	
	@Autowired
	private TaobaoFinalSaveService taobaoFinalSaveService;
	
	@Autowired
	private AlibabaRequestService alibabaRequestService;
	
	protected void runSaveTask(SaveTask task){
		MqRecordItem mqItem=null;
		try{
			setSession(task.getMemberId());
			Map<String,Object> params= JacksonJsonMapper.getInstance().readValue(task.getParam(),HashMap.class);
			String url=(String) params.get("url");
			Boolean isSavaPic=(Boolean) params.get("picStatus");
			Integer recordId=(Integer) params.get("mqRecordeId");
			mqItem=mqRecordItemDao.findOne(recordId);
			if(mqItem==null){
				throw new BusinessException("生成记录被删除,recordId:"+recordId);
			}
			Offer offer=null;
			if(task.getType()==1){
				offer=alibabafinalSaveService.save(url, isSavaPic, mqItem, params);
			}else if(task.getType()==2){
				String catId=params.get("catId").toString();
				offer=taobaoFinalSaveService.save(url, catId, isSavaPic, mqItem, params);
			}else{
				
			}
			mqItem.setOfferId(offer.getOfferId().toString());
			mqItem.setTitle(offer.getSubject());
        	mqItem.setStatus(1);
		}catch(BusinessException e){
			mqItem.setStatus(2);
			mqItem.setErrorMsg(e.getMessage());
			logger.error(e);
		}catch(Exception e){
			mqItem.setStatus(2);
			String eMsg=e.toString();
			if(e.toString().length()>200){
				eMsg=e.toString().substring(0,200);
			}
			mqItem.setErrorMsg(eMsg);
			logger.error(e);
		}finally{
			if(mqItem!=null){
				mqItem.setEndTime(new Date());
				mqRecordItemDao.saveAndFlush(mqItem);
			}
		}
		//复制成功,删除任务记录
    	saveTaskDao.delete(task.getId());
	}
	
	protected void setSession(String memberId) throws TimeoutException, InterruptedException, MemcachedException, IOException{
		AliToken info=memCachedUtil.getByAppkeyAndMemberId(aliConstant.APP_KEY,memberId);
		info=alibabaRequestService.refreshTokenWithEffectiveness(info);
		SessionUtil.setAliSession(info);
	}
}
