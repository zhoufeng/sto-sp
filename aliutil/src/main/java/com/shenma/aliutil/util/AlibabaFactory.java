package com.shenma.aliutil.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.policy.ClientPolicy;

@Component
public class AlibabaFactory implements InitializingBean{

	private  Integer TIME_OUT=60000; //60秒
	
	private static Map<String,AlibabaClient> clientMap=new HashMap<String,AlibabaClient>(8);
		
	@Autowired
	private AliConstant aliConstant;

	public  AlibabaClient getInstance(String appkey,String appSecret){
		if(!clientMap.containsKey(appkey)){
			ClientPolicy policy = ClientPolicy.getDefaultChinaAlibabaPolicy();
			policy.setTimeout(TIME_OUT).setContentCharset("UTF-8");
			policy.setNeedAuthorization(true).setUseSignture(true);
			//设置app的appKey以及对应的密钥，信息由注册app时生成
			policy.setAppKey(appkey).setSigningKey(appSecret);
			AlibabaClient alibabaClient=new AlibabaClient(policy); 
			alibabaClient.start();
			clientMap.put(appkey, alibabaClient);
			return alibabaClient;
		}else{
			return clientMap.get(appkey);
		}
		
	}
	
	public  AlibabaClient getInstance(){
		return getInstance(aliConstant.APP_KEY, aliConstant.APP_Secret);
	}
	
	public void afterPropertiesSet() throws Exception {
		for(String appKeySecret:AliConstant.appKeySecrets){
			String appKey=appKeySecret.split(":")[0];
			String appSecret=appKeySecret.split(":")[1];
			ClientPolicy policy = ClientPolicy.getDefaultChinaAlibabaPolicy();
			policy.setTimeout(TIME_OUT).setContentCharset("UTF-8");
			policy.setNeedAuthorization(true).setUseSignture(true);
			//设置app的appKey以及对应的密钥，信息由注册app时生成
			policy.setAppKey(appKey).setSigningKey(appSecret);
			AlibabaClient alibabaClient=new AlibabaClient(policy); 
			alibabaClient.start();
			clientMap.put(appKey, alibabaClient);
		}
		
	}
}
