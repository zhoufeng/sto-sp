package com.shenma.top.imagecopy.util.reqdubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bohusoft.dubboapi.service.FetchCateHtmlService;
import com.shenma.top.imagecopy.util.TaobaoCateUtil;

@Component
public class HtmlCateDubboUtil {
	@Autowired
	private FetchCateHtmlService fetchCateHtmlService;
	
	public String fetchHtmlDetail(String url){
		String ret=fetchCateHtmlService.fetchDetailPage(url,TaobaoCateUtil.tmailSearchUrl);
		return ret;
	}
}
