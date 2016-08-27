package com.bohusoft.htmlfetch.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class BaseHttpClient {
	protected static Logger logger = Logger.getLogger("BaseHttpClient");
	private static HttpClient httpclient;
	private static String cookieValue="isg=AA514ACD6149BAC0BFE172DF9BD4AF6D; ali_beacon_id=101.65.46.170.1425099134444.153925.4; cna=fjd4DZekBCICAaouQWWZewoD; ali_apache_track=\"c_ms=1|c_mt=3|c_mid=b2b-2135715960|c_lid=kongjishisecom\"; _cn_slid_=\"m0Ql%2BnA5vS\"; last_mid=b2b-2135715960; __last_loginid__=kongjishisecom; alicnweb=touch_tb_at%3D1425485925433%7Clastlogonid%3Dkongjishisecom%7Cshow_inter_tips%3Dfalse; ali_ab=101.65.111.102.1425182518613.8; __utma=62251820.953275058.1425279023.1425279023.1425279023.1; __utmz=62251820.1425279023.1.1.utmcsr=view.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/cms/itbu/app/index.html; JSESSIONID=8L78SHuu1-hDETfFqFIdocKUWgm4-Z6xam5P-jU2; _tmp_ck_0=\"1BRDMcAoIccAQdzfzIQeAWjdC0pw6j9uubCkXGfE2WARMU6GrmtOGEYCFI%2Buw6i1t9%2F5YVceRptTyiFa2lo9Qf0a1UPA%2BZtTH0JOpjmIVrHjoPX74Kh3MLKmdd1adrD9SvZHb2iavrjFbkLqEdrzBTMdR29ZzdhjUQAabVKdPGdD76xG0eeZem6r2r7hzwmJSQSCtt2MVqLXfU%2Bsl3Y14ROdha5PCg8gOzae2fFRQEfJerX8sUft4KJ%2F%2B1lAvmMXJ23zwnNx8VW6NEmP7yWzGp7NwnJNXvP3YNrd6duiE7fF28xypMx%2BM6tNVCD6yAE4PahJuD9Sm6%2FJnfgkKItVFsL%2B5PMpB39%2F%2FxMJtEYuY8%2BIScQc3LvI274GUAb1TED9zKyIdYl%2FMmMvEW7Ud0Nzt8WYMKGqpo6MXrIfB1nx0TfpEMz5YB9Wm2KQGV3BO6NRt8R71H5F24KtGqYvd3h0kb0tfUr79kfEgfiZcOdx9HNxoMvB0rAPU8WrJDy7rf4FWtKwtMS2fHSg3pQHFRy9iIOn4GL%2Bv6Sewd0W7c6X5Mm77LEbOtlTV0kVYK7wA9CFFr9TjULGqO0%3D\"; __cn_logon__=true; ali_apache_tracktmp=\"c_w_signed=Y\"; _nk_=\"4XqXIR1qHwaJZNpI%2BUE0Kw%3D%3D\"; LoginUmid=\"%2Fv%2F54EBq%2BpQdA%2F%2F93Tt0V88xFIBdWXf2ijLPK54fDihHKPcCw7TGng%3D%3D\"; userID=\"TXCwIFJjO7nDcjS6V3o2J0nzxCTzjlzdJ4zVn7jahM86sOlEpJKl9g%3D%3D\"; userIDNum=\"Q66meEyBOf1nhRthAUduHA%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1425483903230; unb=2135715960; __cn_logon_id__=kongjishisecom; cn_tmp=\"Z28mC+GqtZ0s9gBWlWQ3oKZL3kJv01YZSxJPP0Npl7CGE5apwtkL/+GRO3vYOWrhXTlTpK2rvHq9O1LOIA7AF13fWKZkjLT3nRZBSuGZtcLjyXPfjKju2v/tmh9xRSvOBJ9XrqGV+JEVvo3AdzK8sk6KaZ+BOvP8B6vK8nWAmo99AvJnzasE895Djc5wCb5yNOqceyJaQw+uYPkOE6v8A+7UKnKbr1kqeqciiDwAxD49sILHLMJo2CJNxwsJ05jS\"; _is_show_loginId_change_block_=b2b-2135715960_false; _show_force_unbind_div_=b2b-2135715960_false; _show_sys_unbind_div_=b2b-2135715960_false; _show_user_unbind_div_=b2b-2135715960_false; __rn_refer_login_id__=kongjishisecom; __rn_alert__=false";
	public static HttpClient getHttpClient(){
		if(httpclient==null){
			HttpState initialState = new HttpState();

		    Cookie mycookie = new Cookie(".1688.com", "Cookie", "stuff", "/", null, false);
		    mycookie.setValue(cookieValue);
		    initialState.addCookie(mycookie);

		    // Get HTTP client instance
		    httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());
		    httpclient.getHttpConnectionManager().
		        getParams().setConnectionTimeout(60000);
		    httpclient.setState(initialState);
	
		   // httpclient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		}
	    return httpclient;
	}
	public static String get(String url,String encode){
		if(httpclient==null)getHttpClient();

		GetMethod httpget = new GetMethod(url);
		
		String ret=null;
	    try {
			int result = httpclient.executeMethod(httpget);
			ret=IOUtils.toString(httpget.getResponseBodyAsStream(),encode);
	
		} catch (HttpException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			httpget.releaseConnection();
		}
		return ret;
	}
	
	
	public static void main(String[] args) {
		String url="http://detail.1688.com/offer/41008620450.html";
		String html=BaseHttpClient.get(url,"gbk");
		System.out.println(html);
	}
}
