package com.shenma.top.imagecopy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UrlParseUtil {
	public static String formatUrl(String initialUrl,String url){
		url=url.trim();
		String retUrl=url;
		String host=getHost(initialUrl);
		if(url.startsWith("//")){
			retUrl="http:"+url;
		}else if(url.startsWith("/")){
			retUrl=host+url.substring(1);
		}else if(url.startsWith("..")){
			initialUrl=initialUrl.substring(0,url.lastIndexOf("/"));
			initialUrl=initialUrl.substring(0,url.lastIndexOf("/"));
			retUrl=host+url.substring(3);
		}else if(url.startsWith("http")){
			
		}else{
			String temp=initialUrl.substring(0,initialUrl.lastIndexOf("/")+1);
			retUrl=temp+url;
		}
		return retUrl;
	}
	
	private static String  getHost(String url){
		Pattern pattern = Pattern.compile("^http://.*?/");
		Matcher matcher = pattern.matcher(url);
		if(matcher.find()){
			url=matcher.group(0);
		}
		return url;
	}
	
	public static String parseTitle(Document doc){
		Element e=doc.head();
		Element Element=e.select("meta[name=keywords]").first();
		String title=Element.attr("content");
		return title;
	}

}
