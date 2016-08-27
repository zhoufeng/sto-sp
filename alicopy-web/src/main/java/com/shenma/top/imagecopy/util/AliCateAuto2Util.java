package com.shenma.top.imagecopy.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.shenma.top.imagecopy.util.exception.BusinessException;



public class AliCateAuto2Util {
	/*
	 * 
	 */
	public static long startTime=System.currentTimeMillis();
	public static Integer num=0;
	protected static Logger logger = Logger.getLogger("HttpClientAliUtil");
	private static HttpClient httpclient=null;

	public static String cookieValue="ali_beacon_id=117.136.13.51.1457585905626.827514.9; cna=8exnDyQO7nkCAXWIDTN2nPq1; l=AsfHLXu-p8EghUseqUcEM7PnV3GRzJuu; alicnweb=touch_tb_at%3D1471536742611%7Clastlogonid%3Dkongjishisecom%7Cshow_inter_tips%3Dfalse; __last_loginid__=kongjishisecom; ali_apache_track=\"c_ms=1|c_mt=3|c_mid=b2b-2135715960|c_lid=kongjishisecom\"; _cn_slid_=\"m0Ql%2BnA5vS\"; last_mid=b2b-2135715960; __utma=62251820.1445308915.1457746361.1471099046.1471446557.43; __utmz=62251820.1471446557.43.27.utmcsr=fuwu.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/; ali_ab=115.219.33.106.1457795959128.4; ad_prefer=\"2016/07/25 22:17:49\"; h_keys=\"%u997c%u5e72#%u963f%u91cc%u5df4%u5df4%u5206%u9500%u5e73%u53f0#%u513f%u7ae5%u5957%u4ef6#%u8fde%u8863%u88d9#%u7eaf%u68c9T%u6064\"; isg=AklJo-9sek3ITgbxbt-f2vo1WXVu30x-Y0ZB_-u-ujBvMmhEM-WummFkAhlV; JSESSIONID=8L781jNv1-EaCWTD769jsf0K0fHB-QxSH9uP-4mC; _tmp_ck_0=\"aEQCnrnG8uGGL0w1evCaC0CvFved7z4jrVuO%2BJ6fHnH7%2B7fZpoe9snmX5PIXs%2F5b1aQydF0UU6eidqaOAfOsldw2zHxYRBhV8ayXSMPnHNp6Jl4F%2BEAIlO7Gy%2FHALd3Pb3pEf82jrOb1t1595KEMeskYNlEQTZD%2B8syRZqDi4kNL%2FYV8mY1FDR2IVo8KvLEGNscamUUaIeCVSq3J3pTlCMDUBbQL0FQyVMGaP8rJLcmvGlbUYhwRfMLq3DR2Z7Hjv4AbEjPAvxOQyco0OqZXmZdg2244lSH2oe4RDL9x6GvHg%2BTBGQQD0YgwO1WSwud5hljaQWlaxTMu3UcQREONxhdCIRYWWB9j5CIsL7In3mF9jhF9mxLTFCiGCLVxwLA7oWR7sKya7MUn9HViyGj8VIkHWQMbymMqnaIhZnEJJzr5gtJCDlyNxJuJmzzVYoY%2FJyMgN0D0V%2Bd1VRLDm65rpQORaFHm3Y%2FAX8EFK2LYVi45bAQnsIpm04YUPaycOLMoVBIcjFLqPbN6%2FmXv88uIFXFDGRM%2BL1%2BRXRqZ1Qju%2Bl7u4EA%2FeSuzGQ%3D%3D\"; __cn_logon__=true; __cn_logon_id__=kongjishisecom; ali_apache_tracktmp=\"c_w_signed=Y\"; _nk_=\"4XqXIR1qHwaJZNpI%2BUE0Kw%3D%3D\"; tbsnid=3KdnFi3DxRE5%2Fh6K6cl3A2P%2BnnkNUX%2F%2Fxtt6QBcGYy06sOlEpJKl9g%3D%3D; LoginUmid=\"5bKWLcinI%2B2job4ZN69p4qItjQR8EGTsNgTc5BFMqKO%2BkczvUn3C%2FQ%3D%3D\"; userID=\"TXCwIFJjO7nDcjS6V3o2J0nzxCTzjlzdJ4zVn7jahM86sOlEpJKl9g%3D%3D\"; unb=2135715960; userIDNum=\"Q66meEyBOf1nhRthAUduHA%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1471536812277; __utmc=62251820; cn_tmp=\"Z28mC+GqtZ0s9gBWlWQ3oKZL3kJv01YZSxJPP0Npl7CGE5apwtkL/+GRO3vYOWrhXTlTpK2rvHq9O1LOIA7AF1s+ayQv45ALMWgl0En1C8QrLtHk9FW23MpIX7GNjpcb4MxzSMI3MnN5aRfNGd15ARm3Lfmmbe5wHI/Ok42QTCDmTtkv+YPJfTBplo5eg1YYasj+5OiWRhFQwTxGdrEIt0KbOF/RU8FimTJ9fmBgOPxtZGOOMepe4iUQdfRdwFzH\"; _is_show_loginId_change_block_=b2b-2135715960_false; _show_force_unbind_div_=b2b-2135715960_false; _show_sys_unbind_div_=b2b-2135715960_false; _show_user_unbind_div_=b2b-2135715960_false; __rn_alert__=false";

	public static String csrf_token="7fae23153cbb81731e94d653eee15107";

	public static HttpClient getHttpClient(){
		if(httpclient==null){
			HttpState initialState = new HttpState();

		    Cookie mycookie = new Cookie(".1688.com", "mycookie", "stuff", "/", null, false);
		    mycookie.setValue(cookieValue);
		    initialState.addCookie(mycookie);
	
		    // Get HTTP client instance
		    httpclient = new HttpClient();
		    httpclient.getHttpConnectionManager().
		        getParams().setConnectionTimeout(30000);
		    httpclient.setState(initialState);
	
		}
	    return httpclient;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized String getSkuInfo(Integer topCategoryId,Integer secondCategoryId,Integer thirdCategoryId,Integer tradeType) throws InterruptedException{
		if(httpclient==null)getHttpClient();
		long endTime=System.currentTimeMillis();
		if(endTime-startTime>100){
			
		}else{
			//Thread.sleep(80);
		}
		startTime=endTime;
		num++;
		String cateURL="http://offer.1688.com/offer/post/fill_product_info.htm";
		PostMethod httppost=new PostMethod(cateURL);
		NameValuePair token   = new NameValuePair("_csrf_token", csrf_token);
        NameValuePair catType      = new NameValuePair("catType", "0");
        NameValuePair currentPage   = new NameValuePair("currentPage", "chooseCategory");
        NameValuePair fromWhere = new NameValuePair("fromWhere", "normal");
        NameValuePair operator = new NameValuePair("operator", "new");
        NameValuePair price = new NameValuePair("price", "0.0");
        NameValuePair topCategoryIdNP = new NameValuePair("topCategoryId", topCategoryId.toString());
        NameValuePair secondCategoryIdNP = new NameValuePair("secondCategoryId", secondCategoryId.toString());
        NameValuePair tradeTypeN = new NameValuePair("tradeType", tradeType.toString());
        NameValuePair thirdCategoryIdN = new NameValuePair("thirdCategoryId", thirdCategoryId==null?"":thirdCategoryId.toString());
        NameValuePair[] params=null;
        if(thirdCategoryId==null){
        	params=new NameValuePair[] {token, catType, currentPage, fromWhere,operator,price,topCategoryIdNP,secondCategoryIdNP,tradeTypeN};
        }else{
        	params=new NameValuePair[] {token, catType, currentPage, fromWhere,operator,price,topCategoryIdNP,secondCategoryIdNP,tradeTypeN,thirdCategoryIdN};
        }
        httppost.setRequestBody(params);

        String body=null;
        try {
			httpclient.executeMethod(httppost);
			body=httppost.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error(e);
		} finally{
			 httppost.releaseConnection();
		}
        Document doc=Jsoup.parse(body);
        String properties=doc.select("#properties").attr("data-mod-config");
        if(properties==null)throw new BusinessException();
        //System.out.println(properties);
       
        return properties;

	}
	public static void main(String[] args) {
		AliCateAuto2Util.getHttpClient();
		//AliCateAuto2Util.getSkuInfo("4","422",null,"1");
	}
}
