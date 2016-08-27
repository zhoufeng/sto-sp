package com.bohusoft.aliapi.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.bohusoft.aliapi.exception.AliReqException;
import com.bohusoft.aliapi.exception.RemoteException;
import com.bohusoft.aliapi.util.AlibabaFactory;
import com.bohusoft.aliapi.util.HttpOpenClient;
import com.bohusoft.aliapi.util.MemCachedUtil;
import com.bohusoft.dubboapi.service.AliApiService;
import com.shenma.aliutil.service.AliToken;

@Service
public class AliApiServiceImp implements AliApiService {
	private static final Map<String,String> successfalseMap=new HashMap<String, String>();
	protected static Logger logger = Logger.getLogger("AliApiServiceImp");
	static{
		successfalseMap.put("offer.getPublishOfferList", "offer.getPublishOfferList");
		successfalseMap.put("offerGroup.hasOpened", "offerGroup.hasOpened");
	}
	@Autowired
	private AlibabaFactory alibabaFactory;
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	@Autowired
	private HttpOpenClient httpOpenClient;
	
	/**
	 * 用户通过应用市场来访问app时，会带code参数，app通过这个code来换取用户的授权信息
	 * @param code
	 * @return
	 */
	public AliToken getToken(String appKey,String appSecret,String code){
		AlibabaClient client=alibabaFactory.getInstance(appKey,appSecret);
		AuthorizationToken authorizationToken = client.getToken(code);
		AliToken aliToken=new AliToken();
		aliToken.setAppKey(appKey);
		aliToken.setAppSecret(appSecret);
		aliToken.setAccessToken(authorizationToken.getAccess_token());
		aliToken.setRefreshToken(authorizationToken.getRefresh_token());
		aliToken.setResourceOwner(authorizationToken.getResource_owner());
		aliToken.setExpiresTime(authorizationToken.getExpires_time().getTime());
		aliToken.setMemberId(authorizationToken.getMemberId());
		return aliToken;
	}
	


	public Map<String,Object> reqAliApi(String method, Map<String,Object> params, String memcacheId)  {
		//设置用户授权的access_token
		LinkedHashMap<String, Object> ret=new LinkedHashMap<String, Object>();
		
		AliToken info;
		try {
			info = memCachedUtil.get(memcacheId);
		}catch (Exception e1) {
			ret.put("remoteError",1);
			ret.put("remoteErrorMsg","请求缓存出错!"+e1);
			return ret;
		}
		AlibabaClient client;
		try {
			client = alibabaFactory.getInstance(info.getAppKey(),info.getAppSecret());
		} catch (Exception e1) {
			ret.put("remoteError",RemoteException.ALI_API_CLIENT);
			ret.put("remoteErrorMsg","获得阿里巴巴api客户端出错!");
			return ret;
		}
		try{
		
			//初始化请求策略，包括请求编码方式，请求的超时时间等
			//RequestPolicy basePolicy = new RequestPolicy().setContentCharset("UTF-8").setTimeout(30000);
			
			 /*//1、调用开放数据，无需授权
			RequestPolicy noAuthPolicy = basePolicy.clone();
			//调用获取开放平台系统时间: cn.alibaba.open 分组的含义，开放平台默认值 ; system.time.get 具体的某一个api的名字
			Object result = client.send(new Request("system", "currentTime"),null,noAuthPolicy);*/
			//2、调用隐私数据，需要用户授权
			//RequestPolicy testPolicy =  basePolicy.clone();
			//请求需要包含签名以及授权
			//testPolicy.setNeedAuthorization(true).setUseSignture(true);
			//调用获取单个交易的详情
			Request apiRequest = new Request("cn.alibaba.open", method,1);
			//需要传递的参数，如复杂结构，则需要传递合法的json串，如["1","2"],{"key":"value"}
			for(Entry<String,Object> o:params.entrySet()){
				apiRequest.setParam(o.getKey(), o.getValue());
			}
			apiRequest.setAccessToken(info.getAccessToken());
			//返回的结果一般是合法的json串，用户需要自己处理
			try {
				ret = client.send(apiRequest,null);
				//ret=httpOpenClient.getSkuInfo(method,params,info);
			} catch (Exception e) {
				logger.error(e);
				throw new AliReqException(AliReqException.ALI_REQ_FAIL, "阿里服务器请求失败!");
			}
			if(ret.get("result")==null){
				if(method.equals("ibank.image.upload")){
					if(ret.containsKey("errorCode")){
						if(ret.get("errorCode").toString().equals("120009")){
							throw new AliReqException(AliReqException.PIC_MAXSIZE, "上传的图片过大");
						}else if(ret.get("errorCode").toString().equals("120008")){
							throw new AliReqException(AliReqException.PIC_SPACE_FULL, "图片空间容量已满");
						}else if(ret.get("errorCode").toString().equals("120007")){
							throw new AliReqException(AliReqException.ALBUM_FULL, "单个相册的图片数量已满");
						}else if(ret.get("errorCode").toString().equals("090008")){
							throw new AliReqException(AliReqException.UPLOAD_FAIL, "上传图片失败,请重新试一次");
						}
					}
				}else if(method.equals("app.order.get")){//订单查询
					if(ret.containsKey("successed")&&ret.get("successed").toString().equals("true")){
						return ret;
					}else{
						throw new AliReqException(AliReqException.COMM_SEARCH_FAIL, ret.toString());
					}
				}else if(method.equals("offer.new")){
					if(ret.containsKey("errorMsg")){
						throw new AliReqException(AliReqException.COMM_SEARCH_FAIL, ret.get("errorMsg").toString());
					}
				}else{
					if(ret.containsKey("errorCode")){
						throw new AliReqException(AliReqException.COMM_SEARCH_FAIL, ret.get("errorMsg").toString());
					}
					if(ret.containsKey("message")){
						List<String> message=(List<String>) ret.get("message");
						throw new AliReqException(AliReqException.COMM_SEARCH_FAIL, message.get(0));
					}
				}
				
				List<Object> message=(List<Object>) ret.get("message");
				List<Object> code=(List<Object>) ret.get("code");
				if(code==null){
					throw new AliReqException(AliReqException.COMM_SEARCH_FAIL, "返回格式不正确!");
				}else{
					throw new AliReqException(code.get(0).toString(), message.get(0).toString());
				}
			}else{
				if(method.equals("offer.expire")||method.equals("offer.search")){
					return ret;
				}
				if(ret.containsKey("success")){
					boolean issuccess=(Boolean) ret.get("success");
					if(issuccess)return ret;
				}
				Map<String,Object> map=(Map<String, Object>) ret.get("result");
				boolean issuccess=(Boolean) map.get("success");
				if(issuccess==false){
					if(successfalseMap.containsKey(method))return ret;
					List<Object> message=(List<Object>) ret.get("message");
					List<Object> code=(List<Object>) ret.get("code");
					if(code==null){
						throw new AliReqException(AliReqException.COMM_SEARCH_FAIL, "返回格式不正确!");
					}else{
						throw new AliReqException(code.get(0).toString(), message.get(0).toString());
					}
				}
			}
		}catch (AliReqException e) {
			logger.error(e.getMessage());
			ret.put("remoteError",e.getCode());
			ret.put("remoteErrorMsg",e.getMessage());
			return ret;
		}  catch (Exception e) {
			logger.error(e.getMessage());
			ret.put("remoteError",RemoteException.COMMON);
			ret.put("remoteErrorMsg", "请求aliapi的服务器其他错误!"+e.getMessage());
			return ret;
		}
		return ret;
	}

}
