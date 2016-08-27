package com.shenma.top.imagecopy.util.fetchhtml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.reqdubbo.HtmlDetailDubboUtil;
@Component
public class FetchDetailHtmlUtil {

	protected static Logger logger = Logger.getLogger("AlibabafinalSaveService");
	private static Map<String,String> cookies=new HashMap<String, String>();
	public static String cookieValue="ali_beacon_id=122.235.245.110.1440829590888.710740.7; isg=2F5F4003955740B77F0B549E413818B9; l=Ara23Pow1dLZqt5lUA8Eb/NvJua41/oR; cna=lj5oDigy1QsCAXrr9W7RVdWe; ali_ab=115.197.184.89.1440912718303.2; ali_apache_track=\"c_ms=2|c_mt=3|c_mid=b2b-1623492085|c_lid=alitestforisv01\"; _cn_slid_=\"Yw77U%2Faevo\"; __last_loginid__=alitestforisv01; alicnweb=touch_tb_at%3D1449563295295%7Clastlogonid%3Dalitestforisv01%7Cshow_inter_tips%3Dfalse; ad_prefer=\"2015/12/08 15:35:17\"; h_keys=\"%u7fbd%u7ed2%u670d#%u7ae5%u8f66#%u7eaf%u68c9%u6bdb%u5dfe#%u7529%u8102%u673a#%u7eaf%u68c9T%u6064#%u6ed1%u77f3%u74f7%u7ba1#%u8fd0%u52a8%u88e4#%u56db%u4ef6%u5957#%u5851%u80f6%u73a9%u5177#%u4e66%u5305\"; __utma=62251820.1455606200.1441097959.1448768800.1449198257.21; __utmz=62251820.1449198257.21.15.utmcsr=kongjishise.com:8080|utmccn=(referral)|utmcmd=referral|utmcct=/alicopy/top/productcopy/one; CNZZDATA1253659577=1457817981-1441255945-http%253A%252F%252Foffer.1688.com%252F%7C1449563558; last_mid=b2b-1623492085; JSESSIONID=8L78OHuu1-ldlU8P0jR95FAZl5XD-gmpREWP-uJhN; _tmp_ck_0=\"Wfdu5mLMLAGnQjSescqTDyEEQm5eBbcd%2FPNv0HMR8ehtopWM1mVcj9FufE4e2TcRwVBylxhNgPxiNk9rR%2BNC7EniBNI6Jbvyj1xEJLWzbg9gIrmsazeQB0gb%2FqahyqPVUhLk5lYmdI17TafO5n9W1K8Ubx84P4NFqUgzCtGmsQ489QMFa1DGVRzXEEcxbvyvGDAm%2BHcotQ76owny0ywre3NLOfV3rRkNCTLgK9hFi0akZW4a1sVU6CSShcAW9fD6vCb7o63W%2FPHxmgxnyVSBDCyKmjXR6%2B5vAu2LbetoMlr%2FT9Wx9z7XEe8YN9jbPE%2F3RDtO2gjK0Sf1gHvReX8DQE6eWK1fsSrKV%2FTJP84EEjdGnWSgMqRFHwfWdix3TRL%2BMDrl%2FbNbnHocOUZXVu3KdmnKsjwbDWT%2FQDEJO%2FpNdIiDA9pu0i3j6MXLp28ZVTm7HJyi40JGQiSjTOeaJw6AiF%2FzsBflApZmKDOy6VrjfzM0fCARjoyNM5A6G3oeDL5Eci8UQu1qzSgt0l9cya9kwQ%3D%3D\"; __cn_logon__=true; ali_apache_tracktmp=\"c_w_signed=Y\"; _csrf_token=1449558201497; userID=\"ErBDlm7903Usue5a710T1MglkS5l96nSeVZxwVRavgo6sOlEpJKl9g%3D%3D\"; LoginUmid=\"EmAW9W4G%2FYtRb9gbxqpXhI0jlt79J40J7StVPCGySMsogBSl%2FbuCDg%3D%3D\"; userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\"; _is_show_loginId_change_block_=b2b-1623492085_false; _show_force_unbind_div_=b2b-1623492085_false; _show_sys_unbind_div_=b2b-1623492085_false; _show_user_unbind_div_=b2b-1623492085_false; __rn_alert__=false; _ITBU_IS_FIRST_VISITED_=*xC-i2FIYvmIuMGk4vGxT%3Apm0f33hcnd%7Cgzbalin%3Apm0ducce89%7C*xC-i2FHLvCv0OFIWOm8T%3Apm0c2gg1cl%7C*xC-i2FIWvmI0vGv0OmNT%3Apm0f5ds07t%7C*xC-i2FIbOFIWMCQWOmcuvGvuMgTT%3Apm0f5dsi89%7Cmxc138111%3Apm0f5dt7iu; __cn_logon_id__=alitestforisv01;cn_tmp=\"Z28mC+GqtZ3NJkA74i0LmXwopqGKU5eX6yNpIAx4DSx6XVYZ9mTMMokyj8CZcUy5Mpw8PqPyRGv6jKoTMCJX2gXvRBBKBHXbB3braIA1e1WXrJ2dGHsJVRsT4UVBqrsZFOJIOunA//PAOI+B9ke3FRt/6RKwR5DZAiNc+9OuiyOsmmbCnsvt/IY+Urqd8FZkuIiVv/IOE+l1qtlyS0husYtIrOzZ+LS/k2NSczzH57+wAXHhiT5tJCD+WdMxBHcK\"; unb=1623492085; sec=5666975dd49140696e38f633581656860e72b704";
	static{
		String[] arr=cookieValue.split(";");
		for(String cookiestr:arr){
			String[] cookiearr=cookiestr.split("=");
			if(cookiearr.length==2){
				cookies.put(cookiearr[0], cookiearr[1]);
			}else{
				cookies.put(cookiearr[0], "");
			}
			
		}
	}
	
	@Autowired
	private HtmlDetailDubboUtil htmlDetailDubboUtil;
	
	private static HttpClient httpclient;
	
	public static HttpClient getHttpClient(){
		if(httpclient==null){
			HttpState initialState = new HttpState();
		    Cookie mycookie = new Cookie(".taobao.com", "mycookie", "stuff", "/", null, false);
		    mycookie.setValue(cookieValue);
		    initialState.addCookie(mycookie);
	
		    // Get HTTP client instance
		    httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());
		    httpclient.getHttpConnectionManager().
		        getParams().setConnectionTimeout(30000);
		    httpclient.setState(initialState);
	
		    httpclient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		}
	    return httpclient;
	}
	
	public  String  getJsoupReq(String url) throws IOException, InterruptedException{
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0").referrer("http://alisec.1688.com/checkcodev3.php?apply=laputa&http_referer="+url).cookies(cookies).timeout(12000).get(); 
		return doc.html();
	}
	public   String  getNwJsForLaotu(String url) throws IOException, InterruptedException{
		GetMethod httpget = new GetMethod(url);
		String body=null;
	    try {
			int result = httpclient.executeMethod(httpget);
			body=IOUtils.toString(httpget.getResponseBodyAsStream(),"utf-8");
		} catch (Exception e) {
			logger.error(e);
		} finally{
			httpget.releaseConnection();
		}
	    return body;
	}
	public   String  getNwJsForLaoGong(String url) throws IOException, InterruptedException{
		url=url.replaceAll("pageNo", "pageNum");
		GetMethod httpget = new GetMethod(url);
		String body=null;
	    try {
			int result = httpclient.executeMethod(httpget);
			body=IOUtils.toString(httpget.getResponseBodyAsStream(),"utf-8");
			HashMap ret=JacksonJsonMapper.getInstance().readValue(body,HashMap.class);
			Map<String,Object> respone=(Map<String, Object>) ret.get("fetch_page_response");
			Map<String,Object> job=(Map<String, Object>) respone.get("job");
			body=(String) job.get("content");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpget.releaseConnection();
		}
	    return body;
	}
	public String getHtmlForDubbo(String url){
		return htmlDetailDubboUtil.fetchHtmlDetail(url);
	}
}
