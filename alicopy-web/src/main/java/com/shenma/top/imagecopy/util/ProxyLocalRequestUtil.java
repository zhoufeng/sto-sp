package com.shenma.top.imagecopy.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.Request;
import com.shenma.aliutil.sdk.ali.auth.HostedAuthService;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.HttpOpenClient;
import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;

public class ProxyLocalRequestUtil {
	
	
	public static LinkedHashMap<String,Object> reqAlibabaApi(String method,Map<String,Object> params,AliToken info,AlibabaClient client,Request apiRequest) throws InterruptedException, ExecutionException, TimeoutException, UnsupportedEncodingException, CopyBussinessException{
		LinkedHashMap<String,Object> ret=null;
		String url=AliConstant.proxy_url;
		//String cateURL="http://www.kongjishise.com:8080/alicopy/open/api";
        Map<String,Object> retprams=new HashMap<String, Object>(3);
        retprams.put("method", method);
        retprams.put("params", params);
        retprams.put(AliConstant.ali_info_name, info);
		String developModel=(String) CustomerPropertyPlaceholderConfigurer.getContextProperty("develop_mode");
		if(StringUtils.isEmpty(developModel))developModel="localhost";
		if(DevelopModelEnum.serverModel.getModelType().equals(developModel)){
			ret = client.send(apiRequest,null);
		}else if(DevelopModelEnum.localhostModel.getModelType().equals(developModel)){
			ret=HttpOpenClient.post(url,retprams);
		}else{
			throw new CopyBussinessException("请求参数developModel错误,无此参数");
		}
		return ret;
	}
	
	public static String getLoginUlr(HostedAuthService hostedAuthService,String customerUrl64) throws UnsupportedEncodingException, CopyBussinessException{
		String url=AliConstant.proxy_url+"/getLoginUrl";
		String developModel=(String) CustomerPropertyPlaceholderConfigurer.getContextProperty("develop_mode");
		if(DevelopModelEnum.serverModel.getModelType().equals(developModel)){
			url=hostedAuthService.getLoginUrl(customerUrl64);
		}else if(DevelopModelEnum.localhostModel.getModelType().equals(developModel)){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("url", customerUrl64);
			Map<String,Object> ret=HttpOpenClient.post(url,params);
			url=(String) ret.get("url");
		}else{
			throw new CopyBussinessException("请求参数developModel错误,无此参数");
		}
		return url;
	}
}
