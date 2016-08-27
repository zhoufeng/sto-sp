package com.shenma.top.imagecopy.controller.rmi;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.sdk.ali.auth.HostedAuthService;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.service.AlibabaRequestService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.BeanUtil;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.shenma.top.imagecopy.util.Constant;
import com.taobao.api.ApiException;

@Controller
@RequestMapping("/open/api")
public class RestfulRmiController {
	protected static Logger logger = Logger.getLogger("RestfulRmiController");
	
	@Autowired
	private AlibabaRequestService alibabaRequestService;
	
	@Autowired
	private HostedAuthService hostedAuthService;
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	@Autowired
	private AliConstant aliConstant;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="")
	@ResponseBody 
	public Map<String,Object> opnapi(@RequestBody Map<String,Object> variables) throws ApiException, IllegalAccessException, InvocationTargetException{
		Map<String,Object> ret=new HashMap<String,Object>();
		try {
		AliToken aliToken=BeanUtil.map2Bean((LinkedHashMap)variables.get(AliConstant.ali_info_name),AliToken.class);
		//AliToken aliToken=memCachedUtil.getByAppkeyAndMemberId(aliConstant.APP_KEY, "b2b-2135715960");
		if(aliToken==null){
			ret.put("errorCode", 103);
			ret.put("errorMsg", "从memcache取到的值为null");
			return ret;
		}
		String method=(String) variables.get("method");
		Map<String,Object> params=(Map<String, Object>) variables.get("params");
		SessionUtil.setAliSession(aliToken);
		
			ret=alibabaRequestService.reqAliApp(method, params);
		}  catch (AliReqException e) {
			ret.put("errorCode", e.getCode());
			ret.put("errorMsg", e.getMessage());
			logger.error(e);
		}catch (Exception e) {
			ret.put("errorCode", 101);
			ret.put("errorMsg", e.getMessage());
			logger.error(e);
		}
		return ret;
	}
	
	@RequestMapping(value="/test")
	@ResponseBody
	public String test(){
		return "success";
	}
	
	@RequestMapping(value="/getToken")
	@ResponseBody
	public AliToken getToken(){
		AliToken aliToken=memCachedUtil.getByAppkeyAndMemberId(aliConstant.APP_KEY, "b2b-2135715960");
		return aliToken;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getLoginUrl")
	@ResponseBody
	public Map<String,Object> getLoginUrl(@RequestParam("customerUrl") String customerUrl){
		Map<String,Object> ret=new HashMap<String,Object>(1);
		String url=hostedAuthService.getLoginUrl(customerUrl);
		ret.put("url", url);
		return ret;
	}
}
