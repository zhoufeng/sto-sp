package com.bohusoft.htmlfetch.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class CustomerHttpClient {
	protected static Logger logger = Logger.getLogger("CustomerHttpClient");
	public static long startTime=0L;
	private static final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private static  CloseableHttpClient httpclient=null;
	private static final String userAgent="Mozilla/5.0 (Windows NT 6.3; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0";
	static {
		cm.setMaxTotal(1);
		httpclient = HttpClients.custom().setUserAgent(userAgent).setConnectionManager(cm).build();	
	}
	
	/**
	 * 根据url返回html页面代码
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static synchronized String get(String url){
		long endTime=System.currentTimeMillis();
		if(endTime-startTime>=301){
			
		}else{
			try {
				Thread.sleep(301-endTime+startTime);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
		startTime=endTime;
		HttpGet httpget = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		String html=null;
		try {
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            try {
            	logger.debug(url + " - get executed");
                // get the response body as an array of bytes
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html=EntityUtils.toString(entity, "gbk");
                    logger.debug(html);
                }else{
                	throw new Exception(url+":请求该地址,返回为空!");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
        	logger.error(url + " - error: " + e);
        }
		return html;
	}
	
	public static void main(String[] args) {
		//IOUtils.toByteArray(url)
	}

}
