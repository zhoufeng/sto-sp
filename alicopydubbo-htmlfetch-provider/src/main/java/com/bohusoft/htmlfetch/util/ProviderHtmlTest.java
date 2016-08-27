package com.bohusoft.htmlfetch.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bohusoft.htmlfetch.service.FetchHtmlServiceImp;


public class ProviderHtmlTest {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		try{
		context.start();
		FetchHtmlServiceImp fetchHtmlServiceImp= (FetchHtmlServiceImp) context.getBean("fetchHtmlServiceImp");
		String url="http://detail.1688.com/offer/521647808949.html";
		String html =fetchHtmlServiceImp.fetchDetailPage(url);
		File file=new File("d:test.txt");
		FileUtils.writeStringToFile(file, html);
		while(true){
			Thread.sleep(10000);
		}
		//System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		}finally{
			context.close();
		}
	}
}
