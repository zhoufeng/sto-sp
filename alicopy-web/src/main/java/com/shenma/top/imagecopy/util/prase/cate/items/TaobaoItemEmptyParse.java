package com.shenma.top.imagecopy.util.prase.cate.items;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TaobaoItemEmptyParse implements IitemParse {

	@Override
	public TaobaoItemsParseBean getItems(Document document) {
		TaobaoItemsParseBean bean=new TaobaoItemsParseBean();
		return bean;
	}

	@Override
	public boolean valiate(Document document) {
		Element totalsele=document.select("div.search-result span").first();
		Integer toatals=Integer.valueOf(totalsele.text().trim());
		return toatals==0;
	}

}
