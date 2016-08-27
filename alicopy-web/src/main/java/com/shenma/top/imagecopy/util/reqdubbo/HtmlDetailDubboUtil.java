package com.shenma.top.imagecopy.util.reqdubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bohusoft.dubboapi.service.FetchHtmlService;

@Component
public class HtmlDetailDubboUtil {
	
	@Autowired
	private FetchHtmlService fetchHtmlService;
	
	public String fetchHtmlDetail(String url){
		String ret=fetchHtmlService.fetchDetailPage(url);
		return ret;
	}
}
