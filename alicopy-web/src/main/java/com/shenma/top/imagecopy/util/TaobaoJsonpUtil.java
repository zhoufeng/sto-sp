package com.shenma.top.imagecopy.util;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.util.reqdubbo.HtmlCateDubboUtil;

@Component
public class TaobaoJsonpUtil implements ApplicationContextAware {
	protected static Logger logger = Logger.getLogger("TaobaoJsonpUtil");
	public static long startTime=0L;
	private static ApplicationContext context;
	private static HtmlCateDubboUtil htmlCateDubboUtil ;
	/*public static Document getTmailCateConnet(String url) throws IOException, InterruptedException{
		synchronized (TaobaoJsonpUtil.class) {
			long endTime=System.currentTimeMillis();
			if(endTime-startTime>=201){
				
			}else{
				Thread.sleep(201-endTime+startTime);
			}
			startTime=endTime;
		}
		String body=TaobaoCateUtil.getTmail(url);
		Document doc =Jsoup.parse(body);
		return doc;
	}*/
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context=applicationContext;
		HtmlCateDubboUtil htmlCateDubboUtil= (HtmlCateDubboUtil) context.getBean("htmlCateDubboUtil");
		this.htmlCateDubboUtil=htmlCateDubboUtil;
	}
	public static Document getTmailCateConnet(String url){
		Document doc = null;
		try {
			String html=htmlCateDubboUtil.fetchHtmlDetail(url);
			doc =Jsoup.parse(html);
		} catch (Exception e) {
			logger.error("请求ali页面出错,url:"+url+":"+e.getMessage());
		}
		return doc;
	}
	
}
