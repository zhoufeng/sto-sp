package com.shenma.top.imagecopy.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.shenma.top.imagecopy.util.exception.BusinessException;

public class TaobaoCateUtil {
	protected static Logger logger = Logger.getLogger("HttpClientAliUtil");
	private static HttpClient httpclient;
	//private static String cookieValue="ali_ab=60.186.191.32.1413019689875.3; t=1f7a884768f2acbce1a6df052c6a4781; lzstat_uv=2989152196550654538|2945527@2948565@2043323@3045821@2801066@2945730@2798379@3492151@3005619@3201199; cna=Tua/DFpT2CYCATy6vyBrapXJ; uc3=nk2=AHLOucE%3D&id2=UonYsJWYHUg%3D&vt3=F8dATkfWvLR6zOyWOx0%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; unt=chy33%26center; tracknick=chy33; _cc_=VT5L2FSpdA%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26_ato%3D0%26__ll%3D-1; mt=ci=87_1&cyk=4_0; lgc=chy33; swfstore=28130; v=0; cookie2=1c8d32bbab0b65bf2307fda01700d2b8; _tb_token_=4739733b5abe; pnm_cku822=199UW5TcyMNYQwiAiwTR3tCf0J%2FQnhEcUpkMmQ%3D%7CUm5Ockt3Q3xAeExyS39Cey0%3D%7CU2xMHDJ%2BH2QJZwBxX39Rb1Z4WHYqSy1BJlgiDFoM%7CVGhXd1llXGBUa1dvW2VcaFVpXmNBdEB8RXhGf0R9RHtGeUd5VwE%3D%7CVWldfS0SMg42DCwQJAQqfFh9XWA2AHQZfQdqRidJbQFwAy17LQ%3D%3D%7CVmhIGCIePgMjHysWKAg0CTUNLRElGicHOwYzDi4SJhkkBDgFPAFXAQ%3D%3D%7CV25Tbk5zU2xMcEl1VWtTaUlwJg%3D%3D; uc1=lltime=1419210004&cookie14=UoW2%2Bq7wk8NVpQ%3D%3D&existShop=true&cookie16=UIHiLt3xCS3yM2h4eKHS9lpEOw%3D%3D&cookie21=VT5L2FSpdeCsOSyjpv%2FIyw%3D%3D&tag=3&cookie15=UtASsssmOIJ0bQ%3D%3D; existShop=MTQxOTIxMzE2OQ%3D%3D; sg=39b; cookie1=UUpp%2FR8QxpxAYWAxutojY19Hy0t2hAvTcVejMmYWlHw%3D; unb=18311849; publishItemObj=Ng%3D%3D; _l_g_=Ug%3D%3D; _nk_=chy33; cookie17=UonYsJWYHUg%3D; isg=9DC0B543F8A10B05BC9FE1623B6DF334; whl=-1%260%260%260; lzstat_ss=2976764592_0_1419241959_3201199; linezing_session=lLQMhaaMas7t1oerfoztn0uR_1419216169701Byn1_7";    
	private static String cookieValue="ali_ab=60.186.191.32.1413019689875.3; t=1f7a884768f2acbce1a6df052c6a4781; lzstat_uv=2989152196550654538|2945527@2948565@2043323@3045821@2801066@2945730@2798379@3492151@3005619@3201199; cna=Tua/DFpT2CYCATy6vyBrapXJ; uc3=nk2=AHLOucE%3D&id2=UonYsJWYHUg%3D&vt3=F8dATkfWvLR6zOyWOx0%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; unt=chy33%26center; tracknick=chy33; _cc_=VT5L2FSpdA%3D%3D; tg=0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26_ato%3D0%26__ll%3D-1; mt=ci=87_1&cyk=4_0; lgc=chy33; swfstore=28130; v=0; cookie2=1c8d32bbab0b65bf2307fda01700d2b8; _tb_token_=4739733b5abe; pnm_cku822=199UW5TcyMNYQwiAiwTR3tCf0J%2FQnhEcUpkMmQ%3D%7CUm5Ockt3Q3xAeExyS39Cey0%3D%7CU2xMHDJ%2BH2QJZwBxX39Rb1Z4WHYqSy1BJlgiDFoM%7CVGhXd1llXGBUa1dvW2VcaFVpXmNBdEB8RXhGf0R9RHtGeUd5VwE%3D%7CVWldfS0SMg42DCwQJAQqfFh9XWA2AHQZfQdqRidJbQFwAy17LQ%3D%3D%7CVmhIGCIePgMjHysWKAg0CTUNLRElGicHOwYzDi4SJhkkBDgFPAFXAQ%3D%3D%7CV25Tbk5zU2xMcEl1VWtTaUlwJg%3D%3D; uc1=lltime=1419210004&cookie14=UoW2%2Bq7wk8NVpQ%3D%3D&existShop=true&cookie16=UIHiLt3xCS3yM2h4eKHS9lpEOw%3D%3D&cookie21=VT5L2FSpdeCsOSyjpv%2FIyw%3D%3D&tag=3&cookie15=UtASsssmOIJ0bQ%3D%3D; existShop=MTQxOTIxMzE2OQ%3D%3D; sg=39b; cookie1=UUpp%2FR8QxpxAYWAxutojY19Hy0t2hAvTcVejMmYWlHw%3D; unb=18311849; publishItemObj=Ng%3D%3D; _l_g_=Ug%3D%3D; _nk_=chy33; cookie17=UonYsJWYHUg%3D; isg=9DC0B543F8A10B05BC9FE1623B6DF334; whl=-1%260%260%260; lzstat_ss=2976764592_0_1419241959_3201199; linezing_session=lLQMhaaMas7t1oerfoztn0uR_1419216169701Byn1_7";    
	
	public static String tmailSearchUrl="http://mircle123.gicp.net:8079/tmail/search";
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
	public static String getTmail(String url){
		if(httpclient==null)getHttpClient();
		String body=null;
		if(searchType==3){
			url=tmailSearchUrl+"?url="+url;
			url=url.replaceAll("pageNo", "pageNum");
			GetMethod httpget = new GetMethod(url);
		    try {
				int result = httpclient.executeMethod(httpget);
				body=IOUtils.toString(httpget.getResponseBodyAsStream(),"utf-8");
				HashMap ret=JacksonJsonMapper.getInstance().readValue(body,HashMap.class);
				Map<String,Object> respone=(Map<String, Object>) ret.get("fetch_page_response");
				Map<String,Object> job=(Map<String, Object>) respone.get("job");
				body=(String) job.get("content");
			} catch (Exception e) {
				logger.error("请求连接:"+url+":"+e.getMessage());
			}finally{
				httpget.releaseConnection();
			}
		}else{
			/*url=tmailSearchUrl+"?url="+url;
			GetMethod httpget = new GetMethod(url);
		    try {
				int result = httpclient.executeMethod(httpget);
				body=IOUtils.toString(httpget.getResponseBodyAsStream(),"utf-8");
			} catch (Exception e) {
				logger.error("请求连接:"+url+":"+e.getMessage());
			} finally{
				httpget.releaseConnection();
			}*/

			PostMethod httppost=new PostMethod(tmailSearchUrl);
			NameValuePair urlNameV = new NameValuePair("url",url);
			httppost.setRequestBody(new NameValuePair[] {urlNameV});
		    try {
				 httpclient.executeMethod(httppost);
				//body=IOUtils.toString(httppost.getResponseBodyAsStream(),"utf-8");
				body=httppost.getResponseBodyAsString();
			} catch (Exception e) {
				logger.error("请求连接:"+url+":"+e.getMessage());
				throw new BusinessException("请求搜索服务器问题,请联系管理员!");
			}finally{
				httppost.releaseConnection();
			}
		}
	    return body;
	}
	
	public static String reqNWJS(String url,String tmailSearchUrl){
		if(httpclient==null)getHttpClient();
		String body=null;
		PostMethod httppost=new PostMethod(tmailSearchUrl);
		NameValuePair urlNameV = new NameValuePair("url",url);
		httppost.setRequestBody(new NameValuePair[] {urlNameV});
	    try {
			 httpclient.executeMethod(httppost);
			//body=IOUtils.toString(httppost.getResponseBodyAsStream(),"utf-8");
			body=httppost.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("请求连接:"+url+":"+e.getMessage());
		}finally{
			httppost.releaseConnection();
		}
	    return body;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url="http://detail.1688.com/offer/521647808949.html";
		String urld=URLEncoder.encode(url, "utf-8");
		String te=getTmail(url);
		System.out.println(te);
	}
	/**
	 * 获得csrf_token
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getCsrfToken(){
		String cateURL="http://offer.1688.com/offer/post/choose_category.htm?operator=new";
		String token=null;
		GetMethod httpget = new GetMethod(cateURL);
	    try {
			int result = httpclient.executeMethod(httpget);
			String body=httpget.getResponseBodyAsString();
			Document doc= Jsoup.parse(body);
			String data=doc.select("div#doc[data-doc-config]").first().attr("data-doc-config");
			HashMap ret=JacksonJsonMapper.getInstance().readValue(data,HashMap.class);
			token=ret.get("_csrf_token").toString();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpget.releaseConnection();
		}
	    return token;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> getSkuInfo(String topCategoryId,String secondCategoryId){
		String cateURL="http://offer.1688.com/offer/post/fill_product_info.htm";
		PostMethod httppost=new PostMethod(cateURL);
		NameValuePair token   = new NameValuePair("_csrf_token", getCsrfToken());
        NameValuePair catType      = new NameValuePair("catType", "0");
        NameValuePair currentPage   = new NameValuePair("currentPage", "chooseCategory");
        NameValuePair fromWhere = new NameValuePair("fromWhere", "normal");
        NameValuePair operator = new NameValuePair("operator", "new");
        NameValuePair price = new NameValuePair("price", "0.0");
        NameValuePair topCategoryIdNP = new NameValuePair("topCategoryId", topCategoryId);
        NameValuePair secondCategoryIdNP = new NameValuePair("secondCategoryId", secondCategoryId);
        NameValuePair tradeType = new NameValuePair("tradeType", "1");
        httppost.setRequestBody(new NameValuePair[] {token, catType, currentPage, fromWhere,operator,price,topCategoryIdNP,secondCategoryIdNP,tradeType});
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
        HashMap<String, Object> ret=null;
		try {
			ret = JacksonJsonMapper.getInstance().readValue(properties,HashMap.class);
		} catch (Exception e) {
			logger.error(e);
		}
        return ret;
	}
	
	/**
	 * 根据类别id获得分类
	 * @param cateId
	 */
	public static void getCatInfo(Integer cateId){
		//http://spu.1688.com/spu/ajax/getLevelInfoByPath.htm?callback=jQuery1720699341810415104_1415431811432&catId=1034162&pathValues=100000691%3A46874
		String cateURL="http://offer.1688.com/offer/asyn/category_selector.json?loginCheck=N&dealType=getSubCatInfo&categoryId="+cateId+"&scene=offer&tradeType=1";
		GetMethod httpget = new GetMethod(cateURL);
	    try {
			int result = httpclient.executeMethod(httpget);
			String body=httpget.getResponseBodyAsString();
			System.out.println(body);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpget.releaseConnection();
		}
	}

}	
