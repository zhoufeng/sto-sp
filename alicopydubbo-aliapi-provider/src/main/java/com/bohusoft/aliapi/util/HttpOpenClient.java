package com.bohusoft.aliapi.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.service.AliToken;

@Component
public class HttpOpenClient {
	protected static Logger logger = Logger.getLogger("HttpClientAliUtil");
	private  HttpClient httpclient;
	private  String cookieValue="ali_beacon_id=183.130.96.208.1414115717527.718637.3; cna=Tua/DFpT2CYCATy6vyBrapXJ; ali_apache_track=\"c_ms=1|c_mt=3|c_mid=b2b-2135715960|c_lid=kongjishisecom\"; _cn_slid_=\"m0Ql%2BnA5vS\"; __last_loginid__=kongjishisecom; alicnweb=work2015_welcome_end%3DY%7Ctouch_tb_at%3D1415427535256%7Clastlogonid%3Dkongjishisecom; hp_ab_is_marked_2015=1; ali_ab=183.130.121.148.1415000667873.2; ad_prefer=\"2014/11/04 16:22:47\"; h_keys=\"%u6df1%u5733%u709c%u529b%u7535%u5b50%u6709%u9650%u516c%u53f8\"; last_mid=b2b-2135715960; JSESSIONID=8L78OEqv1-zAbS82wL1JxxreFmL4-79LyzuO-FMOV; _tmp_ck_0=\"sUVIYWD8qDTItxJx5C%2BY3CMsi929XA8BGU3wG%2BvSLJsLSxEG4jFVAVvTPFijQfOoL2Rj2Ueje4UHrUqpjgAU2z%2BsSVGwyHo1%2BgHLB%2FRSDK0JSrbRYqDGaXWgLaovITJHWaSOQ1LE%2FiWhUiDovCsUgQW1fSncsZKJQq6YmE6yKSZTsvDeHAGQJapnPzDwMsTSbCi8f6%2B2wye%2FjSRJ2GzF9z0cJpfGX6l072ZG%2FaOvLWC8Mncy2erD9toJ%2BlgMemH%2FDi%2FPPMHPJ6VSsvJB0EHlxuCXVtu10X63tmV%2B1GUEACttj9c59LEd28t2kuiukJzBxTOWieAMhOLOzsWL9R0tFp92FVzwYRJrxKqdukMj%2FVwmYBJPfp8YjOBb162nxb1Fx7M7D1xXI1hev63N4QhXKl7erbbbAily5YXV%2F0PvCkP76Q%2BfXTfyzyQwemqM5WuSEkKAb%2Fc4eFrDcRPNAOoTdqPeh8m75PpoD4nCPnKa%2Bq99LpWaAkW7GBn3ozT1sa1cFZyAgvTTV5UV8HMdU%2BhwOo%2F0QgfKN9SoipBJi56fkUjsnNoxH4gxPABYmuPpMCww3Km5p%2Bz7rJQ%3D\"; _csrf_token=1415424927669; __cn_logon__=true; __cn_logon_id__=kongjishisecom; ali_apache_tracktmp=\"c_w_signed=Y\"; userID=\"TXCwIFJjO7nDcjS6V3o2J0nzxCTzjlzdJ4zVn7jahM86sOlEpJKl9g%3D%3D\"; LoginUmid=\"gRuPdTSwH36di2X5sVuDgvSo%2BVhrSQK5Tkf6UC6Yu%2FQQoN5blcC8mQ%3D%3D\"; cn_tmp=\"Z28mC+GqtZ0s9gBWlWQ3oKZL3kJv01YZSxJPP0Npl7CGE5apwtkL/+GRO3vYOWrhXTlTpK2rvHq9O1LOIA7AF1YojZJsx4g4ztDFfNX3fUfeLmAvx9cjnyI8aKnzoRZGR7Vz9/P1vw6s1/DTw4adbcANNRc6OKhIsbJdubBvrsN8jy+3Q+xiSpEmsCLffgFAtjibPtSCfpLJluKumfsjL7/sMn5isrLJ0vFdJr24KZ4QlzeUJVNQFeGCM0vJeeoa\"; unb=2135715960; userIDNum=\"Q66meEyBOf1nhRthAUduHA%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _nk_=\"4XqXIR1qHwaJZNpI%2BUE0Kw%3D%3D\"; _is_show_loginId_change_block_=b2b-2135715960_false; _show_force_unbind_div_=b2b-2135715960_false; _show_sys_unbind_div_=b2b-2135715960_false; _show_user_unbind_div_=b2b-2135715960_false; __rn_refer_login_id__=kongjishisecom; __rn_alert__=false";
    
	@Value("#{aliUtilConfig['proxy_url']}")
	private String proxyUrl="";
	
	public  HttpClient getHttpClient(){
		if(httpclient==null){
			HttpState initialState = new HttpState();

		    Cookie mycookie = new Cookie(".1688.com", "mycookie", "stuff", "/", null, false);
		    mycookie.setValue(cookieValue);
		    initialState.addCookie(mycookie);
	
		    // Get HTTP client instance
		    httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());
		    httpclient.getHttpConnectionManager().
		        getParams().setConnectionTimeout(30000);
		    //httpclient.setState(initialState);
	
		   // httpclient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		}
	    return httpclient;
	}
	

	
	@SuppressWarnings("unchecked")
	public  LinkedHashMap<String,Object>  getSkuInfo(String method,Map<String,Object> params,AliToken info) throws UnsupportedEncodingException{
		if(httpclient==null)getHttpClient();
		//String cateURL="http://www.kongjishise.com:8080/alicopy/open/api";
		PostMethod httppost=new PostMethod(proxyUrl);
		
        httppost.setRequestHeader("contentType", "application/json");
        httppost.setRequestHeader("Accept", "application/json");
        Map<String,Object> prams=new HashMap<String, Object>(3);
        prams.put("method", method);
        prams.put("params", params);
        prams.put("aliInfo", info);
        ObjectMapper objectMapper = new ObjectMapper();
        String json=null;
        LinkedHashMap<String,Object> ret=null;
		try {
			json = objectMapper.writeValueAsString(prams);
		}catch (Exception e1) {
			logger.error("转换hashmap参数出错:"+e1);
		}
        RequestEntity entiry=new StringRequestEntity(json,"application/json","utf-8");
        httppost.setRequestEntity(entiry);
        String body=null;
        try {
			httpclient.executeMethod(httppost);
			body=IOUtils.toString(httppost.getResponseBodyAsStream(),Charsets.UTF_8);
			System.out.println(body);
			ret=objectMapper.readValue(body, LinkedHashMap.class);
		} catch (Exception e) {
			logger.error("请求服务器返回的格式出错:"+e);
		} finally{
			 httppost.releaseConnection();
		}
        return ret;
	}
	

}	
