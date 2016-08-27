package com.shenma.top.imagecopy.util.prase.cate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.TaobaoJsonpUtil;
import com.shenma.top.imagecopy.util.bean.SearchVoBean;
import com.taobao.api.domain.Item;

public class TmailBussness implements IBussness {
	protected static Logger logger = Logger.getLogger("TmailBussness");
	
	public Document request(SearchVoBean bean) throws IOException, InterruptedException{
		String url="";
		String pageNo="";	
		if(bean.getPageNo()!=null){
			pageNo="pageNo="+bean.getPageNo()+"&";
		}
		if(bean.getUrl().indexOf("category-")>-1){
			url=bean.getUrl();
		}else if(StringUtils.isEmpty(bean.getCateUrl())){
			url=bean.getUrl()+"/search.htm";
		}else{
			url=bean.getCateUrl();
		}
		if(!"".equals(pageNo)){
			if(url.contains(".htm?")){
				url=url.substring(0, url.indexOf(".htm?")+5)+pageNo+url.substring(url.indexOf(".htm?")+5,url.length());
			}else{
				url+="?"+pageNo;
			}
		}
		Document doc = TaobaoJsonpUtil.getTmailCateConnet(url);
		return doc;
	}
	
/*	public List<Map<String, Object>> getCate(Document doc) throws IOException, InterruptedException {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Elements elements=doc.select(".attr.J_TCate .attrValues ul li a");

		for(Element el:elements){
			Map<String, Object> cat=new HashMap<String, Object>(); 
			String cateurl=el.select("a").first().attr("href");
			if(cateurl.startsWith("//"))cateurl="http:"+cateurl;
			String name=el.select("a").first().text();
			if(StringUtils.isEmpty(name)){
				Element imgel=el.select("img").first();
				if(imgel!=null)name=imgel.attr("alt");
			}
			//System.out.println(name);
			cat.put("id",cateurl.substring(cateurl.indexOf("category-")+9,cateurl.indexOf(".htm")));
			cat.put("name",name);
			cat.put("url",cateurl);
			list.add(cat);
			Elements liele=el.select("ul li");
			if(liele!=null){
				List<Map<String,Object>> sublist=new ArrayList<Map<String,Object>>();
				for(Element subel:liele){
					Map<String, Object> subcat=new HashMap<String, Object>(); 
					String suburl=subel.select("a").first().attr("href");
					String subname=subel.select("a").first().text();
					subcat.put("id",suburl.substring(suburl.indexOf("category-")+9,suburl.indexOf(".htm")));
					subcat.put("name",subname);
					sublist.add(subcat);
				}
				cat.put("children", sublist);
			}
		}
		
		
		return list;
	}*/
	
	public List<Map<String, Object>> getCate(Document doc) throws IOException, InterruptedException {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Elements elements=doc.select("ul.J_TCatsTree.cats-tree.J_TWidget li.cat.fst-cat:not(.float)");
		if(elements.size()==0){
			elements=doc.select("ul#J_Cats li.cat:not(.J_CatHeader)");
		}
		if(elements.size()==0){
			elements=doc.select("ul.J_TAllCatsTree.cats-tree li.cat:not(.J_CatHeader)");
		}
		for(Element el:elements){
			Map<String, Object> cat=new HashMap<String, Object>(); 
			String cateurl=el.select("a").first().attr("href");
			if(cateurl.startsWith("//"))cateurl="http:"+cateurl;
			if(cateurl.indexOf("#")>-1){
				cateurl=cateurl.substring(0,cateurl.indexOf("#"));
			}
			String name=el.select("a").first().text();
			if(StringUtils.isEmpty(name)){
				Element imgel=el.select("img").first();
				if(imgel!=null)name=imgel.attr("alt");
			}
			//System.out.println(name);
			cat.put("id",cateurl.substring(cateurl.indexOf("category-")+9,cateurl.indexOf(".htm")));
			cat.put("name",name);
			cat.put("url", cateurl);
			list.add(cat);
			Elements liele=el.select("ul li");
			if(liele!=null){
				List<Map<String,Object>> sublist=new ArrayList<Map<String,Object>>();
				for(Element subel:liele){
					Map<String, Object> subcat=new HashMap<String, Object>(); 
					String suburl=subel.select("a").first().attr("href");
					if(suburl.startsWith("//"))suburl="http:"+suburl;
					String subname=subel.select("a").first().text();
					subcat.put("id",suburl.substring(suburl.indexOf("category-")+9,suburl.indexOf(".htm")));
					subcat.put("name",subname);
					subcat.put("url", suburl);
					sublist.add(subcat);
				}
				cat.put("children", sublist);
			}
		}

		
		return list;
	}

	public void getItems(SearchVoBean bean,Document doc) throws IOException, InterruptedException {
		parseTaobaoItem(bean, doc);
	}
	
	private String getMid(Document doc){
		String asynSearchUrl="";
		String tempUrl=doc.select("#J_ShopAsynSearchURL").val();
		if(!StringUtils.isEmpty(tempUrl)){
			String parms=tempUrl.substring(tempUrl.indexOf("?")+1,tempUrl.length());
			String[] parmsStr=parms.split("&");
			for(String p:parmsStr){
				if(p.startsWith("mid"))asynSearchUrl+="&"+p;
				//if(p.startsWith("catId"))asynSearchUrl+="&"+p;
			}
		}else{
			Pattern pattern1 = Pattern.compile("(?<=mid%3D).*?(?=)");
			Matcher matcher1 = pattern1.matcher(doc.html());
			String s="";
			if (matcher1.find()) {
				asynSearchUrl ="&mid="+matcher1.group(0);
			}
		}
		return asynSearchUrl;
	}
	/**
	 * 解析淘宝itemsList
	 * @param bean
	 * @param doc
	 * @return
	 * @throws InterruptedException 
	 * @throws UnsupportedEncodingException 
	 */
	private List<Item> parseTaobaoItem(SearchVoBean bean, Document doc) throws InterruptedException, UnsupportedEncodingException {
		List<Item> list=new ArrayList<Item>();
		Elements elements=doc.select("div.J_TItems .item");
		Elements elements2=doc.select("div.comboHd ~ div ");
		int tunum=0;
		int rownum=0;
		if(elements2.size()>0){
			Elements elements3=elements2.get(0).select(".item");
			rownum=elements3.size();
		}
		tunum=elements2.size()*rownum;
		for(int i=0;i<elements.size()-tunum;i++){
			Element element=elements.get(i);
			Item item=new Item();
			Element ele=element.select(".photo a").first();   //商品链接
			String href=ele.attr("href");
			if(href.startsWith("//"))href="http:"+href;
			item.setDetailUrl(href);
			
			Element imgele=element.select(".photo img").first(); //图片地址
			String imageHref=imgele.attr("data-ks-lazyload");
			if(StringUtils.isEmpty(imageHref)){
				imageHref=imgele.attr("src");
			}
			if(imageHref.startsWith("//"))imageHref="http:"+imageHref;
			item.setPicUrl(imageHref);
			
			Element nameele=element.select(".detail a").first(); //标题
			item.setTitle(nameele.text());
			
			Element priceele=element.select(".attribute .c-price").first(); //价格
			item.setPrice(priceele.text().trim());
			
			Element numeele=element.select(".attribute .sale-num").first(); //已售数量
			if(numeele==null){
				item.setNum(0l);
			}else{
				item.setNum(Long.valueOf(numeele.text()));
				
			}
			list.add(item);
		}
		
		Element pageNospan=doc.select(".ui-page-s .ui-page-s-len").first();
		String pageInfo=pageNospan.text().trim();
		Integer pageNo=Integer.valueOf(pageInfo.split("/")[0]);
		Integer totalPages=Integer.valueOf(pageInfo.split("/")[1]);
		bean.setPageNo(pageNo);
		bean.setTotalPages(totalPages);
		bean.setTotals((elements.size()-tunum)*totalPages);
		bean.setItems(list);
		return list;
	}

	@Override
	public SearchVoBean getItem(String url) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getCate(String url) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getItems(SearchVoBean bean) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		
	}

}
