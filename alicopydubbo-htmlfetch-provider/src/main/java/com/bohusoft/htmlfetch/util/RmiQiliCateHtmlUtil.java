package com.bohusoft.htmlfetch.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class RmiQiliCateHtmlUtil {
	protected static Logger logger = Logger.getLogger("HttpClientAliUtil");
	private static HttpClient httpclient;
	//private static String cookieValue="ali_ab=60.186.191.32.1413019689875.3; t=1f7a884768f2acbce1a6df052c6a4781; lzstat_uv=2989152196550654538|2945527@2948565@2043323@3045821@2801066@2945730@2798379@3492151@3005619@3201199; cna=Tua/DFpT2CYCATy6vyBrapXJ; uc3=nk2=AHLOucE%3D&id2=UonYsJWYHUg%3D&vt3=F8dATkfWvLR6zOyWOx0%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; unt=chy33%26center; tracknick=chy33; _cc_=VT5L2FSpdA%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26_ato%3D0%26__ll%3D-1; mt=ci=87_1&cyk=4_0; lgc=chy33; swfstore=28130; v=0; cookie2=1c8d32bbab0b65bf2307fda01700d2b8; _tb_token_=4739733b5abe; pnm_cku822=199UW5TcyMNYQwiAiwTR3tCf0J%2FQnhEcUpkMmQ%3D%7CUm5Ockt3Q3xAeExyS39Cey0%3D%7CU2xMHDJ%2BH2QJZwBxX39Rb1Z4WHYqSy1BJlgiDFoM%7CVGhXd1llXGBUa1dvW2VcaFVpXmNBdEB8RXhGf0R9RHtGeUd5VwE%3D%7CVWldfS0SMg42DCwQJAQqfFh9XWA2AHQZfQdqRidJbQFwAy17LQ%3D%3D%7CVmhIGCIePgMjHysWKAg0CTUNLRElGicHOwYzDi4SJhkkBDgFPAFXAQ%3D%3D%7CV25Tbk5zU2xMcEl1VWtTaUlwJg%3D%3D; uc1=lltime=1419210004&cookie14=UoW2%2Bq7wk8NVpQ%3D%3D&existShop=true&cookie16=UIHiLt3xCS3yM2h4eKHS9lpEOw%3D%3D&cookie21=VT5L2FSpdeCsOSyjpv%2FIyw%3D%3D&tag=3&cookie15=UtASsssmOIJ0bQ%3D%3D; existShop=MTQxOTIxMzE2OQ%3D%3D; sg=39b; cookie1=UUpp%2FR8QxpxAYWAxutojY19Hy0t2hAvTcVejMmYWlHw%3D; unb=18311849; publishItemObj=Ng%3D%3D; _l_g_=Ug%3D%3D; _nk_=chy33; cookie17=UonYsJWYHUg%3D; isg=9DC0B543F8A10B05BC9FE1623B6DF334; whl=-1%260%260%260; lzstat_ss=2976764592_0_1419241959_3201199; linezing_session=lLQMhaaMas7t1oerfoztn0uR_1419216169701Byn1_7";    
	private static String cookieValue="ali_ab=60.186.191.32.1413019689875.3; t=1f7a884768f2acbce1a6df052c6a4781; lzstat_uv=2989152196550654538|2945527@2948565@2043323@3045821@2801066@2945730@2798379@3492151@3005619@3201199; cna=Tua/DFpT2CYCATy6vyBrapXJ; uc3=nk2=AHLOucE%3D&id2=UonYsJWYHUg%3D&vt3=F8dATkfWvLR6zOyWOx0%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; unt=chy33%26center; tracknick=chy33; _cc_=VT5L2FSpdA%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26_ato%3D0%26__ll%3D-1; mt=ci=87_1&cyk=4_0; lgc=chy33; swfstore=28130; v=0; cookie2=1c8d32bbab0b65bf2307fda01700d2b8; _tb_token_=4739733b5abe; pnm_cku822=199UW5TcyMNYQwiAiwTR3tCf0J%2FQnhEcUpkMmQ%3D%7CUm5Ockt3Q3xAeExyS39Cey0%3D%7CU2xMHDJ%2BH2QJZwBxX39Rb1Z4WHYqSy1BJlgiDFoM%7CVGhXd1llXGBUa1dvW2VcaFVpXmNBdEB8RXhGf0R9RHtGeUd5VwE%3D%7CVWldfS0SMg42DCwQJAQqfFh9XWA2AHQZfQdqRidJbQFwAy17LQ%3D%3D%7CVmhIGCIePgMjHysWKAg0CTUNLRElGicHOwYzDi4SJhkkBDgFPAFXAQ%3D%3D%7CV25Tbk5zU2xMcEl1VWtTaUlwJg%3D%3D; uc1=lltime=1419210004&cookie14=UoW2%2Bq7wk8NVpQ%3D%3D&existShop=true&cookie16=UIHiLt3xCS3yM2h4eKHS9lpEOw%3D%3D&cookie21=VT5L2FSpdeCsOSyjpv%2FIyw%3D%3D&tag=3&cookie15=UtASsssmOIJ0bQ%3D%3D; existShop=MTQxOTIxMzE2OQ%3D%3D; sg=39b; cookie1=UUpp%2FR8QxpxAYWAxutojY19Hy0t2hAvTcVejMmYWlHw%3D; unb=18311849; publishItemObj=Ng%3D%3D; _l_g_=Ug%3D%3D; _nk_=chy33; cookie17=UonYsJWYHUg%3D; isg=9DC0B543F8A10B05BC9FE1623B6DF334; whl=-1%260%260%260; lzstat_ss=2976764592_0_1419241959_3201199; linezing_session=lLQMhaaMas7t1oerfoztn0uR_1419216169701Byn1_7";    
	
	public static String tmailSearchUrl="http://192.168.1.102:8079/tmail/search";
	//public static String tmailSearchUrl="http://100.64.51.53:8079/tmail/search";
	public static Integer searchType=1; //1 老屠 2啊宰 3老龚
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
	
	@SuppressWarnings("rawtypes")
	public static String getRmi(String url,String searchHostUrl){
		
		if(httpclient==null)getHttpClient();
		String body=null;
		PostMethod httppost=new PostMethod(searchHostUrl);
		NameValuePair urlNameV = new NameValuePair("url",url);
		httppost.setRequestBody(new NameValuePair[] {urlNameV});
	    try {
			 httpclient.executeMethod(httppost);
			//body=IOUtils.toString(httppost.getResponseBodyAsStream(),"utf-8");
			body=httppost.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("请求连接:"+url+":"+e.getMessage());
			//throw new BusinessException("请求搜索服务器问题,请联系管理员!");
		}finally{
			httppost.releaseConnection();
		}
		
	    return body;
	}
	
	

}	
