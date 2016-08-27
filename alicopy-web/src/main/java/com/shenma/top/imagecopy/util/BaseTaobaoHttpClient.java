package com.shenma.top.imagecopy.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class BaseTaobaoHttpClient {
	protected static Logger logger = Logger.getLogger("BaseTaobaoHttpClient");
	private static HttpClient httpclient;
	private static String cookieValue="cna=UMSPDRvu6xQCAVeguydlxnDO; isg=AC634A72647E4581BDCABFA7F7E2013E; t=0b90fdee346515b439550d46b1f4fde9; uc3=nk2=CNiwlnAu2FKwVEBHZ%2FI%3D&id2=UUkNYalu8hFUIg%3D%3D&vt3=F8dATk9rV4o05MQVHMs%3D&lg2=Vq8l%2BKCLz3%2F65A%3D%3D; lgc=kongjishisecom; tracknick=kongjishisecom; _tb_token_=vOpy4zoxRv6h; cookie2=0ce8f1ea898725547ef9a7e69d13e1a0; CNZZDATA1000279581=955718889-1427174608-%7C1427189626; otherx=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0; swfstore=147; whl=-1%260%260%260; x=__ll%3D-1%26_ato%3D0; pnm_cku822=; cq=ccp%3D0; ck1=";
	public static HttpClient getHttpClient(){
		if(httpclient==null){
			HttpState initialState = new HttpState();

		    Cookie mycookie = new Cookie(".tmail.com", "Cookie", "stuff", "/", null, false);
		    //mycookie.setValue(cookieValue);
		    initialState.addCookie(mycookie);
		    // Get HTTP client instance
		    httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());
		    httpclient.getHttpConnectionManager().
		        getParams().setConnectionTimeout(60000);
		    httpclient.setState(initialState);
	
		}
	    return httpclient;
	}
	public static String get(String url,Map<String,String> params,String encode){
		if(httpclient==null)getHttpClient();
		GetMethod httpget = new GetMethod(url);
		httpget.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
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
		String url="http://komanic.tmall.com";
		String html=BaseTaobaoHttpClient.get(url,new HashMap<String, String>(),"gbk");
		System.out.println(html);
	}
}
