package com.bohusoft.htmlfetch.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bohusoft.dubboapi.service.FetchCateHtmlService;
import com.bohusoft.htmlfetch.util.FetchAliCateHtmlUtil;
import com.bohusoft.htmlfetch.util.FetchTaobaoCateHtmlUtil;

@Service
public class FetchCateHtmlServiceImp implements FetchCateHtmlService {
	protected static Logger logger = Logger.getLogger("FetchCateHtmlServiceImp");
	
	@Value("#{aliUtilConfig['ali_catehtml_search_type']}")
	private Integer aliSearchType=2;
	
	@Value("#{aliUtilConfig['ali_catehtml_time']}")
	private Integer aliSearchTime=1500;
	
	@Value("#{aliUtilConfig['ali_catehtml_url']}")
	private String aliSearchUrl="http://localhost:8079/cate/search";
	
	public String fetchDetailPage(String url,String searchHostUrl) {
		//String searchHostUrl="http://mircle123.gicp.net:8079/tmail/search";
		//setIpSevice(searchHostUrl);
		String html=null;
		if(url.indexOf("1688.com")>-1){
			html=FetchAliCateHtmlUtil.fetchDetailPage(url, aliSearchUrl, aliSearchType, aliSearchTime);
		}else{
			try {
				html=FetchTaobaoCateHtmlUtil.fetchDetailHtml(url, aliSearchUrl, aliSearchTime);
			} catch (InterruptedException e) {
				logger.error("请求淘宝搜索出错:"+url,e);
			}
		}
		return html;
	}
	
	private void setIpSevice(String searchHostUrl){
		try {
			Jsoup.connect("http://localhost:8079/manager/updateip").data("url", searchHostUrl).get();
		} catch (IOException e) {
			logger.error("更新失败",e);
		}
	}
	
	
	
	
	




	
}

