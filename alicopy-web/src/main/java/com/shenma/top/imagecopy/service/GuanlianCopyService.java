package com.shenma.top.imagecopy.service;

import org.springframework.stereotype.Service;

import com.shenma.top.imagecopy.util.prase.TopTaobaoImageParse;
import com.shenma.top.imagecopy.util.prase.TopTmailImageParse;
import com.shenma.top.imagecopy.util.prase.UrlParse;

@Service
public class GuanlianCopyService {
	public void parseUrl(String url){
		UrlParse parse = null;
		if(url.indexOf("item.taobao.com")>-1){
			parse = new TopTaobaoImageParse();
		}else if(url.indexOf("detail.tmall.com")>-1){
			parse = new TopTmailImageParse();
		}
		
	}
}
