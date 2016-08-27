package com.shenma.top.imagecopy.util.prase.cate;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import com.shenma.top.imagecopy.util.exception.BusinessException;
import com.shenma.top.imagecopy.util.prase.cate.items.IitemParse;
import com.shenma.top.imagecopy.util.prase.cate.items.TaobaoItemEmptyParse;
import com.shenma.top.imagecopy.util.prase.cate.items.TaobaoItemsParse;
import com.shenma.top.imagecopy.util.prase.cate.items.TaobaoItemsParse2;

public class TaobaoItemsParseManager {
	private static List<IitemParse> parseList=new ArrayList<IitemParse>(4);
	static {
		parseList.add(new TaobaoItemsParse());
		parseList.add(new TaobaoItemsParse2());
	}
	public static IitemParse getInstance(Document doc){
		TaobaoItemEmptyParse bean=new TaobaoItemEmptyParse();
		if(bean.valiate(doc))return bean;
		for(IitemParse parse:parseList){
			if(parse.valiate(doc))return parse;
		}
		throw new BusinessException("目前还不能解析该店铺!请联系客服,以尽快修复!");
	}
	
	public static void add(IitemParse iitemParse){
		parseList.add(iitemParse);
	}
}
