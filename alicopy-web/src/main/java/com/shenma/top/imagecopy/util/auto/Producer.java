package com.shenma.top.imagecopy.util.auto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.JsonpUtil;

/**
 * 生产者
 * **/
public class Producer  extends Thread{
	
	private Lock wirte;
	/***
	 * 利用队列存储样品
	 * */
	private BlockingQueue<String> bq;
	private String url;
	public Producer() {
		// TODO Auto-generated constructor stub
	}
	 
	public Producer(BlockingQueue<String> bq,String url,Lock wirte) {
	 
		this.bq = bq;
		this.url=url;
		this.wirte=wirte;
	}

	@Override
	public void run() {
		
		
		try {
			getPages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private void getPages() throws IOException, InterruptedException{
		Document doc=null;
		try {
			doc=Jsoup.connect(url).timeout(9900000).get();
		} catch (Exception e) {
			System.out.println("不能连接:"+url);
			return;
		}
		
		String host=getHost(url);
		Elements links = doc.select("a[href]");
		for(Element ele:links){
			String eleurl=ele.attr("abs:href");
			if(eleurl.endsWith("#"))eleurl=eleurl.substring(0, eleurl.length()-1);
			if(isConnect(eleurl)){
				
				//wirte.lock();
				if(!BlockingQueueTest.urlMap.containsKey(eleurl)){
					if(!StringUtils.isEmpty(eleurl)&&host!=null&&eleurl.startsWith(host)){
						BlockingQueueTest.urlMap.put(eleurl,"false");
					}
				}else if(BlockingQueueTest.urlMap.containsKey(eleurl)&&BlockingQueueTest.urlMap.get(eleurl).equals("false")){
					//System.out.println("put#####"+eleurl+"####"+BlockingQueueTest.urlMap.get(eleurl));
					BlockingQueueTest.urlMap.put(eleurl, "true");
					this.bq.put(eleurl);
				}
				//wirte.unlock();
			}
		}
	}
	
	/**
	  * 功能：检测当前URL是否可连接或是否有效,
	  * 描述：最多连接网络 3 次, 如果 3 次都不成功，视为该地址不可用
	  * @param  urlStr 指定URL网络地址
	  * @return URL
	  */
	 public boolean isConnect(String urlStr) {
		 int counts = 0;
		 boolean retu = false;
		 URL url;
		 int state=0;
		 HttpURLConnection con;
		 if (urlStr == null || urlStr.length() <= 0) {                       
			 return false;                 
		 }
		 while (counts < 1) {
			 long start = 0;
			 try {
				 url = new URL(urlStr);
				 start = System.currentTimeMillis();
				 con = (HttpURLConnection) url.openConnection();
				 state = con.getResponseCode();
				 //System.out.println("请求断开的URL一次需要："+(System.currentTimeMillis()-start)+"毫秒");
				 if (state == 200) {
					 retu = true;
					// System.out.println(urlStr+"--可用");
				 }
				 break;
			 }catch (Exception ex) {
				 counts++; 
				 //System.out.println("请求断开的URL一次需要："+(System.currentTimeMillis()-start)+"毫秒");
				 //System.out.println("连接第 "+counts+" 次，"+urlStr+"--不可用");
				 continue;
			 }
		 }
		 return retu;
	 }
	
	private String getHost(String url){
		Pattern pattern = Pattern.compile("http\\://.*?(?=/)");
		Matcher matcher = pattern.matcher(url);
		String host=null;
		if(matcher.find()){
			host=matcher.group(0);
		}
		return host;
	}
	public static void main(String[] args) {
		ConcurrentHashMap<String, String> map=new ConcurrentHashMap<String, String>();
		map.put("a", "a");
		map.put("a", "b");
		System.out.println(map.size());
	}
}
