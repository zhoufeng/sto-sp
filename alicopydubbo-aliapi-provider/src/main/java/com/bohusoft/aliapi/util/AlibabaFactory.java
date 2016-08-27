package com.bohusoft.aliapi.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.policy.ClientPolicy;

@Component
public class AlibabaFactory implements InitializingBean{
	@Value("#{aliUtilConfig['aliApiClientTimeOut']}")
	private static final int TIME_OUT=20000; //20秒
	
	@Autowired
	private List<AppInfo> appKeySecrets;
	private static Map<String,AlibabaClient> clientMap=new HashMap<String,AlibabaClient>(8);
	

	public AlibabaClient getInstance(String appkey,String appSecret){
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
	

	@Override
	public void afterPropertiesSet() throws Exception {
		for(AppInfo appInfo:appKeySecrets){
			String appKey=appInfo.getAppKey();
			String appSecret=appInfo.getAppSecret();
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
