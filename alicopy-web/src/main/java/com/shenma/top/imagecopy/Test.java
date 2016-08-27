package com.shenma.top.imagecopy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	
	public static void main(String[] args) throws IOException {
		Long start=System.currentTimeMillis();
		Document doc = Jsoup.connect("http://item.taobao.com/item.htm?spm=a2106.m874.1000384.d11.Iti9gd&id=39452266082&cm_cat=50000671&scm=1029.personal-221.bts1.50000671")
				.timeout(2000).get(); 
		Long end=System.currentTimeMillis();
		System.out.println(end-start);
		String title = doc.title(); 
		Element headElement=doc.head();
		String headHtml=headElement.html();
		Pattern pattern = Pattern.compile("(?<=g_config.dynamicScript\\(\").*(?=\"\\))");
		Matcher matcher = pattern.matcher(headHtml);
		//System.out.println(doc.head().html());
		String url=null;
		if(matcher.find()){
			url=matcher.group(0);
		}
		Document scriptdoc = Jsoup.connect(url).timeout(2000).get(); 
		String description=scriptdoc.body().html();
		//System.out.println(scriptdoc.body().html());
		/*Pattern scriptpattern = Pattern.compile("(var desc=').*");
		Matcher scriptmatcher = scriptpattern.matcher(description);
		String ret=null;
		if(scriptmatcher.find()){
			ret=scriptmatcher.group(0);
		}
		System.out.println(ret);
		*/
		description=description.substring(10, description.length()-2);
		 
		Document descriptiondoc = Jsoup.parse(description);
		System.out.println(descriptiondoc.html());
		Elements elements=descriptiondoc.select("img[src]");
		System.out.println(elements.size());
		for(Element element:elements){
			System.out.println(element.html());
		}
		 /*System.out.println(headElement.childNodes().size());
		 for(Node node:headElement.childNodes()){
			 System.out.println(node.outerHtml());
			 System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
		 }*/
		 //Elements elements=doc.select("");
		// Element div = doc.getElementById("description");// 所有引用 png 图片的元素
		// System.out.println(div.toString());
		/* for(Element element:pngs){
			 System.out.println(element.attr("data-src"));
		 }*/
	}
}
