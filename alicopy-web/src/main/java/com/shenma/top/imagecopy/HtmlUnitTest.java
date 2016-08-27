package com.shenma.top.imagecopy;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitTest {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
	    final HtmlPage page = webClient.getPage("http://item.taobao.com/item.htm?spm=a2106.m874.1000384.d11.Iti9gd&id=39452266082&cm_cat=50000671&scm=1029.personal-221.bts1.50000671");
	    
	    String body=page.asXml();
	    System.out.println(body);
	}
}
