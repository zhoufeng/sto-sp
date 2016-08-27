package com.shenma.top.imagecopy.util.fetchhtml;

import org.springframework.stereotype.Component;

@Component
public class JsoupReqFetchHtmlUtil extends FetchHtmlUtil implements FetchHtmlStrategy{

	@Override
	public String getDetailHtml(String url) {
		//return getDetailHtmls(url);
		return "";
	}
	@Override
	public String getSearchHtml(String url) {
		// TODO Auto-generated method stub
		return null;
	}

}
