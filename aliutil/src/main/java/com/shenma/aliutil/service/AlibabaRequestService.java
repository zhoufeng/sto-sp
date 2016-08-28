package com.shenma.aliutil.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.openapi.client.AlibabaClient;
import com.alibaba.openapi.client.Request;
import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.shenma.aliutil.entity.member.MemberInfo;
import com.shenma.aliutil.entity.platform.IsvOrderItemDto;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.sdk.ali.auth.HostedAuthService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.AlibabaFactory;
import com.shenma.aliutil.util.HttpOpenClient;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;

@Component
public class AlibabaRequestService {
	
	protected static Logger logger = Logger.getLogger("AlibabaRequestService");
	private static final Map<String,String> successfalseMap=new HashMap<String, String>();
	public static final Map<String,String> adminMap=new HashMap<String, String>();

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private AlibabaFactory alibabaFactory;
	
	@Autowired
	private AliConstant aliConstant;
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	
	/*@Reference(cluster="failfast")
	private AliApiService aliApiService;*/
	
	
	static{
		successfalseMap.put("offer.getPublishOfferList", "offer.getPublishOfferList");
		successfalseMap.put("offer.getAllOfferList", "offer.getAllOfferList");
		successfalseMap.put("offerGroup.hasOpened", "offerGroup.hasOpened");
		adminMap.put("时尚潮流看向我", "15167137910");
		adminMap.put("博虎软件有限公司", "博虎软件有限公司");
		adminMap.put("顺发铜业", "顺发铜业888");
		adminMap.put("alitestforisv01", "test1234");
	}
	/**
	 * 用户通过应用市场来访问app时，会带code参数，app通过这个code来换取用户的授权信息
	 * @param code
	 * @return
	 */
	public AuthorizationToken getToken(String appKey,String appSecret,String code){
		AlibabaClient client=alibabaFactory.getInstance(appKey,appSecret);
		AuthorizationToken authorizationToken = client.getToken(code,30*24*60*1000);
		return authorizationToken;
	}
	
	/**
	 * 如果已经有用户的授权信息，可以通过已有的refreshToken来换取新的access_token
	 * @param refreshToken
	 * @return
	 */
	public AuthorizationToken getRefreshToken(String appKey,String appSecret,String refreshToken){
		AlibabaClient client=alibabaFactory.getInstance(appKey,appSecret);
		AuthorizationToken authorizationToken = client.refreshToken(refreshToken);
		return authorizationToken;
	}
	
	
	public AliToken saveInfoToSession(String code) throws AliReqException, TimeoutException, InterruptedException, MemcachedException, IOException, ParseException{
		AuthorizationToken authorizationToken=this.getToken(aliConstant.APP_KEY,aliConstant.APP_Secret,code);
		AliToken aliToken=new AliToken();
		aliToken.setAppKey(aliConstant.APP_KEY);
		aliToken.setAppSecret(aliConstant.APP_Secret);
		aliToken.setAccessToken(authorizationToken.getAccess_token());
		aliToken.setRefreshToken(authorizationToken.getRefresh_token());
		aliToken.setResourceOwner(authorizationToken.getResource_owner());
		aliToken.setExpiresTime(authorizationToken.getExpires_time().getTime());
		aliToken.setMemberId(authorizationToken.getMemberId());
		SessionUtil.setAliSession(aliToken);   //设置到threadLocal里面
		MemberInfo memberInfo=memberService.getMemberInfo(String.valueOf(aliToken.getMemberId()));

		SessionUtil.setAliSession(aliToken);   //设置到threadLocal里面
		aliToken.setSellerName(memberInfo.getSellerName());
		parseUserLevel(aliToken);	
		return aliToken;
	}
	
	public AliToken saveInfoToSessionLocal(String code) throws AliReqException, TimeoutException, InterruptedException, MemcachedException, IOException, ParseException{
		AliToken aliToken=null;
		AuthorizationToken authorizationToken=this.getToken(aliConstant.APP_KEY,aliConstant.APP_Secret,code);
		aliToken=new AliToken();
		aliToken.setAppKey(aliConstant.APP_KEY);
		aliToken.setAppSecret(aliConstant.APP_Secret);
		aliToken.setAccessToken(authorizationToken.getAccess_token());
		aliToken.setRefreshToken(authorizationToken.getRefresh_token());
		aliToken.setResourceOwner(authorizationToken.getResource_owner());
		aliToken.setExpiresTime(authorizationToken.getExpires_time().getTime());
		aliToken.setMemberId(authorizationToken.getMemberId());
		SessionUtil.setAliSession(aliToken);   //设置到threadLocal里面
		MemberInfo memberInfo=memberService.getMemberInfo(String.valueOf(aliToken.getMemberId()));
		SessionUtil.setAliSession(aliToken);   //设置到threadLocal里面
		aliToken.setSellerName(memberInfo.getSellerName());
		parseUserLevel(aliToken);	
		return aliToken;
	}

	public void parseUserLevel(AliToken aliToken) throws AliReqException,
			ParseException, TimeoutException, InterruptedException,
			MemcachedException, IOException {
		//设置收费
		//System.out.println(1);
		if(aliConstant.APP_KEY.equals("1018632")||aliConstant.APP_KEY.equals("1019762")||aliConstant.APP_KEY.equals("1019749")){
			aliToken.setUserLevel(1);
		}else if(adminMap.containsKey(aliToken.getResourceOwner())){
			aliToken.setUserLevel(5);
		}else {
			IsvOrderItemDto isvOrderItemDto=platformService.getByMemberId();
			if(isvOrderItemDto!=null&&DateUtils.truncatedCompareTo(isvOrderItemDto.getGmtServiceEnd(), new Date(), Calendar.MINUTE)>0){
			aliToken.setOver(true);
			Integer days=Integer.valueOf((isvOrderItemDto.getGmtServiceEnd().getTime() - isvOrderItemDto.getGmtCreate().getTime()) / (24 * 60 * 60 * 1000)+"");
			if(isvOrderItemDto.getPaymentAmount()<1d){//免费用户
				aliToken.setUserLevel(1);
			}else{
				aliToken.setUserLevel(4);
			}
			long left=(isvOrderItemDto.getGmtServiceEnd().getTime() - new Date().getTime())/ (24 * 60 * 60 * 1000)+1;
			Integer leftDay=Integer.valueOf(left +"");
			aliToken.setLeftDay(leftDay);
			}else{
				aliToken.setOver(false);
				aliToken.setUserLevel(-1);
			}
		}
		//aliToken.setUserLevel(2);
		memCachedUtil.set(aliConstant.APP_KEY+"_"+aliToken.getMemberId(),aliToken);//设置memcache信息
	}
	
	public void parseUserLevelLocal(AliToken aliToken) throws AliReqException,
	ParseException, TimeoutException, InterruptedException,
	MemcachedException, IOException {
		//设置收费
		//System.out.println(1);
		if(aliConstant.APP_KEY.equals("1018632")||aliConstant.APP_KEY.equals("1019762")||aliConstant.APP_KEY.equals("1019749")){
			aliToken.setUserLevel(1);
		}else if(adminMap.containsKey(aliToken.getResourceOwner())){
			aliToken.setUserLevel(5);
		}else {
			IsvOrderItemDto isvOrderItemDto=platformService.getByMemberId();
			if(isvOrderItemDto!=null&&DateUtils.truncatedCompareTo(isvOrderItemDto.getGmtServiceEnd(), new Date(), Calendar.MINUTE)>0){
				aliToken.setOver(true);
				Integer days=Integer.valueOf((isvOrderItemDto.getGmtServiceEnd().getTime() - isvOrderItemDto.getGmtCreate().getTime()) / (24 * 60 * 60 * 1000)+"");
				if(isvOrderItemDto.getPaymentAmount()<1d){//免费用户
					aliToken.setUserLevel(1);
				}else {
					aliToken.setUserLevel(4);
				}
				Integer lefDay=Integer.valueOf((isvOrderItemDto.getGmtServiceEnd().getTime() - new Date().getTime()) / (24 * 60 * 60 * 1000)+"");
				aliToken.setLeftDay(lefDay);
			}else{
				aliToken.setOver(false);
				aliToken.setUserLevel(-1);
			}
		}
		//aliToken.setUserLevel(2);
		memCachedUtil.set(aliConstant.APP_KEY+"_"+aliToken.getMemberId(),aliToken);//设置memcache信息
		}
	
	/**
	 * 当accesstoken失效的时候用这个.
	 * @param aliToken
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @throws IOException
	 */
	public  boolean refreshTokenWithEffectiveness(AliToken aliToken) throws TimeoutException, InterruptedException, MemcachedException, IOException{
		if(aliToken.getExpiresTime()<System.currentTimeMillis()+600000){//判断accessKey是否过期.
			Map<String, String> params =new HashMap<String, String>();
			params.put("client_id", aliToken.getAppKey());
			params.put("client_secret", aliToken.getAppSecret());
			params.put("redirect_uri", AliConstant.appMap.get(aliToken.getAppKey()).getRedirectUri());
			params.put("refresh_token", aliToken.getRefreshToken());
			String accessToken=HostedAuthService.refreshToken("gw.open.1688.com", params);
			aliToken.setAccessToken(accessToken);
			aliToken.setExpiresTime(System.currentTimeMillis()+36000000);
			memCachedUtil.set(aliToken.getAppKey()+"_"+aliToken.getMemberId(), aliToken);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		Date currentDate=new Date();
		long Time = (currentDate.getTime() / 1000) + Integer.parseInt("59") * 60;
		currentDate.setTime(Time * 1000);
		int i=DateUtils.truncatedCompareTo(new Date(), currentDate, Calendar.MINUTE);
		System.out.println(i);
	}
	
	
	private Map<String,Object> reqAliAppLocal(String method,Map<String,Object> params) throws AliReqException{
		//设置用户授权的access_token
				AliToken info=SessionUtil.getAliSession();

				//初始化请求策略，包括请求编码方式，请求的超时时间等
				//RequestPolicy basePolicy = new RequestPolicy().setContentCharset("UTF-8").setTimeout(30000);
				AlibabaClient client=alibabaFactory.getInstance(info.getAppKey(),info.getAppSecret());
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
				LinkedHashMap<String, Object> ret=null;
				try {
					ret = client.send(apiRequest,null);
					//ret=HttpOpenClient.getSkuInfo(method,params,info);
				} catch (Exception e) {
					logger.error(e);
					throw new AliReqException("500", "阿里服务器请求失败!"+e);
				}
				if(ret.get("result")==null){
					 if(method.equals("app.order.get")){//订单查询
						if(ret.containsKey("successed")&&ret.get("successed").toString().equals("true")){
							return ret;
						}else{
							throw new AliReqException("450", ret.toString());
						}
					}else if(method.equals("offer.new")){
						if(ret.containsKey("errorMsg")){
							throw new AliReqException("450", ret.get("errorMsg").toString());
						}
					}else{
						if(ret.containsKey("errorCode")){
							throw new AliReqException(ret.get("errorCode").toString(), ret.get("errorMsg").toString());
						}
						if(ret.containsKey("message")){
							List<String> message=(List<String>) ret.get("message");
							throw new AliReqException("450", message.get(0));
						}
					}
					
					List<Object> message=(List<Object>) ret.get("message");
					List<Object> code=(List<Object>) ret.get("code");
					if(code==null){
						logger.error("错误方法："+method);
						throw new AliReqException("400", "返回格式不正确!");
					}else{
						logger.error("错误返回2：message："+message.get(0)+",code:"+code.get(0));
						throw new AliReqException(code.get(0).toString(), message.get(0).toString());
					}
				}else{
					if(method.equals("ibank.image.upload")){
						if(ret.containsKey("errorCode")){
							if(ret.get("errorCode").toString().equals("120009")){
								throw new AliReqException(ret.get("errorCode").toString(), "上传的图片过大");
							}else if(ret.get("errorCode").toString().equals("120008")){
								throw new AliReqException(ret.get("errorCode").toString(), "图片空间容量已满");
							}else if(ret.get("errorCode").toString().equals("120007")){
								throw new AliReqException(ret.get("errorCode").toString(), "单个相册的图片数量已满");
							}else if(ret.get("errorCode").toString().equals("090008")){
								throw new AliReqException(ret.get("errorCode").toString(), "上传图片失败,请重新试一次");
							}
						}
					}
					
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
							logger.error("错误方法："+method);
							throw new AliReqException("400", "返回格式不正确!");
						}else{
							logger.error("错误返回11：message："+message.get(0)+",code:"+code.get(0));
							throw new AliReqException(code.get(0).toString(), message.get(0).toString());
						}
					}
				}
				return ret;
	}
	
	/**
	 * 请求阿里巴巴API
	 * @param params
	 * @throws AliReqException 
	 */
	public Map<String,Object> reqAliApp(String method,Map<String,Object> params) throws AliReqException{
		//设置用户授权的access_token
		Map<String,Object> ret=reqAliAppLocal(method, params);
		/*if(aliRequestType.equals("local")){
		}else{
			ret=aliApiService.reqAliApi(method, params, memCachedUtil.getMemcacheId(aliConstant.APP_KEY, memberId));
		}*/
		return ret;
	}
	
	
	/**
	 * 请求阿里巴巴API
	 * @param session
	 * @throws AliReqException 
	 */
/*	public <T> T  reqAliApp(String method,Map<String,Object> params,Class<T> T) throws AliReqException{
		//初始化请求策略，包括请求编码方式，请求的超时时间等
		RequestPolicy basePolicy = new RequestPolicy().setContentCharset("UTF-8").setTimeout(5000);
		AlibabaClient client=AlibabaFactory.getInstance();
		 //1、调用开放数据，无需授权
		RequestPolicy noAuthPolicy = basePolicy.clone();
		//调用获取开放平台系统时间: cn.alibaba.open 分组的含义，开放平台默认值 ; system.time.get 具体的某一个api的名字
		Object result = client.send(new Request("system", "currentTime"),null,noAuthPolicy);
		//2、调用隐私数据，需要用户授权
		RequestPolicy testPolicy =  basePolicy.clone();
		//请求需要包含签名以及授权
		testPolicy.setNeedAuthorization(true).setUseSignture(true);
		//调用获取单个交易的详情
		Request apiRequest = new Request("cn.alibaba.open", method,1);
		//需要传递的参数，如复杂结构，则需要传递合法的json串，如["1","2"],{"key":"value"}
		for(Entry<String,Object> o:params.entrySet()){
			apiRequest.setParam(o.getKey(), o.getValue());
		}
		//设置用户授权的access_token
		Map<String,Object> ss=SessionThread.get();
		AliToken info=(AliToken) ss.get(AliConstant.ali_info_name);
		apiRequest.setAccessToken(info.getAccessToken());
		//返回的结果一般是合法的json串，用户需要自己处理
		T ret=null;
		try {
			ret = client.send(apiRequest,T,testPolicy);
		} catch (Exception e) {
			logger.error(e);
			throw new AliReqException("500", "阿里服务器请求失败!");
		}
		return ret;
	}
*/
}
