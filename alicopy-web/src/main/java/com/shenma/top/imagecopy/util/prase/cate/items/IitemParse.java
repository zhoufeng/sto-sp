package com.shenma.top.imagecopy.util.prase.cate.items;

import org.jsoup.nodes.Document;

public interface IitemParse {
	public  TaobaoItemsParseBean  getItems(Document document);
	
	public boolean valiate(Document document);
}
