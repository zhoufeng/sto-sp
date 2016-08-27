package com.shenma.top.imagecopy.util.prase.guanlian;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TopTmailGuanlianParse implements GuanlianParse {

	@Override
	public List<String> parseImages(String url) throws IOException {
		Document doc = Jsoup.connect(url).get(); 
		Pattern pattern = Pattern.compile("(?<=g_config.dynamicScript\\(\").*(?=\"\\))");
		Matcher matcher = pattern.matcher(doc.html());
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		Document scriptdoc = Jsoup.connect(descriptionurl).get(); 
		
		Elements elements=scriptdoc.children();
		for(Element element:elements){
			System.out.println(element.html());
		}
		
		return null;
	}
	public static void main(String[] args) throws IOException {
		TopTmailGuanlianParse t=new TopTmailGuanlianParse();
		String url="http://item.taobao.com/item.htm?spm=a230r.1.14.227.jSzH1R&id=40767196990&ns=1&_u=fbh46ce005#detail";
		t.parseImages(url);
	}

}
