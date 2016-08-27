package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.shenma.top.imagecopy.util.JsonpUtil;

@Service
public class TmailSaveService {
	private Map<String,Object> item=new HashMap<String, Object>();
	private Document document=null;
	public void save(String url) throws IOException, InterruptedException{
		document=JsonpUtil.getAliDefaultConnet(url);
	}
	
	private void setAttr(){
		Elements elements=document.select("#J_AttrUL li");
		for(Element element:elements){
			String[] strs=element.text().split(":");
		}
	}
	
	/**
	 * 功能:颜色
	 */
	private void setColorAttr(){
		Elements elements=document.select("div.tb-key .tb-skin .tb-sku");
		for(Element element:elements){
			Element d1=element.select(".tb-metatit").first();
			if("颜色".equals(d1.text().trim())){
				Elements yansele=element.select(".tb-metatit");
			}
			String[] strs=element.text().split(":");
		}
	}
	public static void main(String[] args) throws IOException {
		TmailSaveService service=new TmailSaveService();
		//service.save("http://detail.tmall.com/item.htm?spm=a1z10.4.w5003-6572540281.1.1G5m5s&id=36298721028&tracelog=fromnonactive&scene=taobao_shop");
		service.setColorAttr();
	}
}
