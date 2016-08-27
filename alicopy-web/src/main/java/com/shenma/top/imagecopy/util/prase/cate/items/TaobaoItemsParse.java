package com.shenma.top.imagecopy.util.prase.cate.items;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.taobao.api.domain.Item;

/**
 * 例如:https://04501.taobao.com/
 * @author zhoufeng
 * @ClassName TaobaoItemsParse
 * @Version 1.0.0
 */
public class TaobaoItemsParse implements IitemParse{
	public  TaobaoItemsParseBean  getItems(Document doc){
		TaobaoItemsParseBean bean=new TaobaoItemsParseBean();
		List<Item> list=new ArrayList<Item>();
		Elements elements=doc.select("div.shop-hesper-bd.grid .item");
		for(Element element:elements){
			Item item=new Item();
			Element ele=element.select(".pic a").first();   //商品链接
			String href=ele.attr("href");
			if(href.startsWith("//"))href="http:"+href;
			item.setDetailUrl(href);
			
			Element imgele=element.select(".pic img").first(); //图片地址
			String imgHref=imgele.attr("data-ks-lazyload");
			if(href.startsWith("//"))imgHref="http:"+imgHref;
			item.setPicUrl(imgHref);
			
			Element nameele=element.select(".desc a").first(); //标题
			item.setTitle(nameele.text().trim());
			
			Element priceele=element.select(".price strong").first(); //价格
			item.setPrice(priceele.text().trim());
			
			Element numeele=element.select(".sales-amount em").first(); //已售数量
			if(numeele!=null)item.setNum(Long.valueOf(numeele.text().trim()));
			
			list.add(item);
		}
		Element totalspan=doc.select("div.search-result span").first();
		bean.setTotals(Integer.valueOf(totalspan.text().trim()));
		Element pageNospan=doc.select("div.pagination .page-info").first();
		if(elements.size()!=0){
		String pageInfo=pageNospan.text().trim();
			bean.setPageNo(Integer.valueOf(pageInfo.split("/")[0]));
			bean.setTotalPages(Integer.valueOf(pageInfo.split("/")[1]));
		}else{
			bean.setPageNo(1);
			bean.setTotalPages(1);
		}
		bean.setItems(list);
		return bean;
	}

	@Override
	public boolean valiate(Document document) {
		Elements elements=document.select("div.shop-hesper-bd.grid .item .pic");
		Element totalsele=document.select(".search-result span").first();
		Integer toatals=Integer.valueOf(totalsele.text().trim());
		return elements.size()>0&&toatals>0;
	}
}
