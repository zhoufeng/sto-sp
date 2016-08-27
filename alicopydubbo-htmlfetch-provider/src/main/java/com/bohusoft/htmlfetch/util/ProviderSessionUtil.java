package com.bohusoft.htmlfetch.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohusoft.dubboapi.exception.DubboApiException;
import com.bohusoft.dubboapi.exception.DubboApiExceptionEnums;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;

@Service
public class ProviderSessionUtil {
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	protected static Logger logger = Logger.getLogger("ProviderSessionUtil");
	public  void  setThreadLocalAlitoken(String memberSessionId) throws DubboApiException{
		AliToken alitoken=null;
		try {
			alitoken=memCachedUtil.get(memberSessionId);
			logger.debug("alitoken:"+alitoken.getMemberId());
			SessionUtil.setAliSession(alitoken);
		} catch (Exception e) {
			logger.error("调用远程memcache出错!"+e.getMessage());
			throw new DubboApiException(DubboApiExceptionEnums.MEMCACHE_REQ_FAIL,e);
		}
		
	}
}
