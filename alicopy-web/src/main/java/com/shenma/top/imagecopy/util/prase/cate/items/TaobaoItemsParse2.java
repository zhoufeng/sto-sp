package com.shenma.top.imagecopy.util.prase.cate.items;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.taobao.api.domain.Item;

/**
 * 例如:http://tianchaotile.taobao.com
 * @author zhoufeng
 * @ClassName TaobaoItemsParse2
 * @Version 1.0.0
 */
public class TaobaoItemsParse2 implements IitemParse {

	@Override
	public TaobaoItemsParseBean getItems(Document doc) {
		TaobaoItemsParseBean bean=new TaobaoItemsParseBean();
		List<Item> list=new ArrayList<Item>();
		Elements elements=doc.select("div.shop-hesper-bd.grid .item");
		for(Element element:elements){
			Item item=new Item();
			Element ele=element.select(".photo a").first();   //商品链接
			String href=ele.attr("href");
			if(href.startsWith("//"))href="http:"+href;
			item.setDetailUrl(href);
			
			Element imgele=element.select(".photo img").first(); //图片地址
			item.setPicUrl(imgele.attr("src"));
			
			Element nameele=element.select(".detail a").first(); //标题
			item.setTitle(nameele.text());
			
			Element priceele=element.select(".attribute .c-price").first(); //价格
			item.setPrice(priceele.text().trim());
			
			Element numeele=element.select(".attribute .sale-num").first(); //已售数量
			if(numeele!=null)item.setNum(Long.valueOf(numeele.text()));
			
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
		Elements elements=document.select("div.shop-hesper-bd.grid .item .photo");
		Element totalsele=document.select(".search-result span").first();
		Integer toatals=Integer.valueOf(totalsele.text().trim());
		return elements.size()>0&&toatals>0;
	}

}
