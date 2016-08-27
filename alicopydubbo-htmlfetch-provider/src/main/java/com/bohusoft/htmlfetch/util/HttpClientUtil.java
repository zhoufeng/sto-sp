package com.bohusoft.htmlfetch.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
	

	
	
	public static String getDetailHtml(String url) throws URISyntaxException, ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpGet get = new HttpGet();
		get.setURI(new URI("http://detail.1688.com/offer/40463841126.html"));
		CloseableHttpResponse res=httpClient.execute(get);
		
		 //输出网页源码          
        String result = EntityUtils.toString(res.getEntity(), "gbk"); 
        return result;
	}
	
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpGet get = new HttpGet();
		get.setURI(new URI("http://detail.1688.com/offer/40463841126.html"));
		CloseableHttpResponse res=httpClient.execute(get);
		
		 //输出网页源码          
        String result = EntityUtils.toString(res.getEntity(), "gbk"); 
        System.out.println(result);
	}
}
