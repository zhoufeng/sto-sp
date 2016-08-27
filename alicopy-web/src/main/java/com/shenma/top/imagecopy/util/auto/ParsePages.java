package com.shenma.top.imagecopy.util.auto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.JsonpUtil;

public class ParsePages {
	public static Map<String,Boolean> urlMap=new HashMap<String, Boolean>();
	private  ReadWriteLock lock = new ReentrantReadWriteLock(); 
	private Lock read = lock.readLock(); 
	private Lock wirte=lock.writeLock();
	private void getPages(String url) throws IOException, InterruptedException{
		Document doc=JsonpUtil.getAliDefaultConnet(url);
		String host=getHost(url);
		Elements links = doc.select("a[href]");
		for(Element ele:links){
			String eleurl=ele.attr("abs:href");
			if(eleurl.startsWith(host)){
				wirte.lock();
				if(!urlMap.containsKey(eleurl)){
					urlMap.put(eleurl, Boolean.TRUE);
				}
				wirte.unlock();
			}
		}
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
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ParsePages obj=new ParsePages();
		for(String url:urlMap.keySet()){
			obj.getPages(url);
		}
	}
	
	
}
