package com.shenma.aliutil.sdk.ali.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.sdk.ali.util.CommonUtil;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.AppInfo;


/**
 * 托管式授权服务类，只适用于在应用市场售卖的app
 * 注意：在应用市场售卖的app请使用托管式授权
 */
@Component
public class HostedAuthService extends AuthService{

	
	@Autowired
	private AliConstant aliConstant;
    /**
     * 返回托管式授权流程中获取code这一步的url
     * @param host 请求的主机名，包括域名和端口
     * @param params 请求参数map，包括client_id,redirect_uri以及可选的state、scope和view
     * @param appSecretKey app签名密钥
     * @return 请求的完整url，用户在浏览器中打开此url然后输入自己的用户名密码进行授权，之后就会得到code
     */
    public static String getHostedAuthUrl(String host, Map<String, String> params, String appSecretKey){
        if(params == null){
            return null;
        }
        String url = "https://" + host + "/openapi/";
        String namespace = "system.oauth2";
        String name = "startAuth";
        int version = 1;
        String protocol = "param";
        if(params.get("client_id") == null || params.get("redirect_uri") == null){
            System.out.println("params is invalid, lack neccessary key!");
            return null;
        }
        params.put("client_user_id", "testApiTools");
        params.put("response_type", "code");
        params.put("need_refresh_token", "true");
        String appKey = params.get("client_id");
        String urlPath = CommonUtil.buildInvokeUrlPath(namespace, name, version, protocol, appKey);
        String signature = CommonUtil.signatureWithParamsAndUrlPath(urlPath, params, appSecretKey);
        params.put("_aop_signature", signature);
        url += urlPath;
        return CommonUtil.getWholeUrl(url, params);
    }

    
    public  String getLoginUrl(String customerUrl){
    	String host = "gw.open.1688.com";//国际交易请用"gw.api.alibaba.com"
        String client_id = aliConstant.APP_KEY;
        String appSecret = aliConstant.APP_Secret;
        String redirectUrl=aliConstant.redirect_uri;//填写app入口url
        String state = "test";//用户自定义参数，建议填写
        
        //测试获取托管式授权的临时令牌code
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", client_id);
        params.put("redirect_uri", redirectUrl);
        params.put("state", customerUrl);
        String startAuthResult = getHostedAuthUrl(host, params, appSecret);
        return startAuthResult;
    }
    public static String getLoginUrl(String appKey,String customerUrl){
    	String host = "gw.open.1688.com";//国际交易请用"gw.api.alibaba.com"
        AppInfo appInfo=AliConstant.appMap.get(appKey);
        
        //测试获取托管式授权的临时令牌code
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", appInfo.getAppKey());
        params.put("redirect_uri", appInfo.getRedirectUri());
        params.put("state", customerUrl);
        String startAuthResult = getHostedAuthUrl(host, params, appInfo.getAppSecret());
        return startAuthResult;
    }
    public static void main(String[] args) {
		String code=getLoginUrl("1014423", "");
		System.out.println(code);
	}
}
