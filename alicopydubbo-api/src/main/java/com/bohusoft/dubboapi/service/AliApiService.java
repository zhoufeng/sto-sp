package com.bohusoft.dubboapi.service;

import java.util.Map;

import com.shenma.aliutil.service.AliToken;

/**
 * 调用阿里巴巴接口
 * @author zhoufeng
 *
 */
public interface AliApiService {
	public Map<String,Object> reqAliApi(String method,Map<String,Object> params,String memcacheId);
	public AliToken getToken(String appKey,String appSecret,String code);
}
