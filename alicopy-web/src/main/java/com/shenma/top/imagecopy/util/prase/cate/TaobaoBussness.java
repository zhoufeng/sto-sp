package com.shenma.top.imagecopy.util.prase.cate;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.TaobaoJsonpUtil;
import com.shenma.top.imagecopy.util.bean.SearchVoBean;
import com.shenma.top.imagecopy.util.prase.cate.items.IitemParse;
import com.shenma.top.imagecopy.util.prase.cate.items.TaobaoItemsParseBean;

public class TaobaoBussness implements IBussness {

	public Document request(SearchVoBean bean) throws IOException, InterruptedException{
		String url="";
		
		String pageNo="";
		if(bean.getPageNo()!=null){
			pageNo="pageNo="+bean.getPageNo()+"&";
		}
		if(StringUtils.isEmpty(bean.getCateUrl())){
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
		//Document doc = TaobaoCateJsonpUtil.getTaobaoCateConnet("http://meilinjiaju.taobao.com/search.htm?spm=a1z10.3-c.w4002-1777470208.2.XJ1I81&mid=w-1777470208-0&search=y&orderType=hotsell_desc&smToken=f01c0ed069d54f589c79caa511e10155&smSign=frHvb6r3NJWVGzbI5EYBag%3D%3D&csy=1&pv=1627207:3232483");
		return doc;
	}
	
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
	
	@Override
	public List<Map<String, Object>> getCate(String url) throws IOException, InterruptedException {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Document doc = JsonpUtil.getAliDefaultConnet(url);
		Elements elements=doc.select("ul.J_TCatsTree.cats-tree.J_TWidget li.cat.fst-cat:not(.float)");
		if(elements.size()==0){
			elements=doc.select("ul#J_Cats li.cat:not(.J_CatHeader)");
		}
		for(Element el:elements){
			Map<String, Object> cat=new HashMap<String, Object>(); 
			String cateurl=el.select("a").first().attr("href");
			String name=el.select("a").first().text();
			if(StringUtils.isEmpty(name)){
				Element imgel=el.select("img").first();
				if(imgel!=null)name=imgel.attr("alt");
			}
			//System.out.println(name);
			cat.put("id",cateurl.substring(cateurl.indexOf("category-")+9,cateurl.indexOf(".htm")));
			cat.put("name",name);
			list.add(cat);
			Elements liele=el.select("ul li");
			if(liele!=null){
				List<Map<String,Object>> sublist=new ArrayList<Map<String,Object>>();
				for(Element subel:liele){
					Map<String, Object> subcat=new HashMap<String, Object>(); 
					String suburl=subel.select("a").first().attr("href");
					String subname=subel.select("a").first().text();
					//subcat.put("id",suburl.substring(suburl.indexOf("category-")+9,suburl.indexOf(".htm")));
					subcat.put("id",suburl);
					subcat.put("name",subname);
					sublist.add(subcat);
				}
				cat.put("children", sublist);
			}
		}
		
		
		return list;
	}

	/**
	 * 解析商品列表
	 * @param bean
	 * @param doc
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void getItems(SearchVoBean bean,Document doc) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException {
		IitemParse iitemParse=TaobaoItemsParseManager.getInstance(doc);
		TaobaoItemsParseBean parsebean=iitemParse.getItems(doc);
		org.springframework.beans.BeanUtils.copyProperties(parsebean, bean);
	}
	
	

	@Override
	public SearchVoBean getItem(String url) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getItems(SearchVoBean bean) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		
	}

}
