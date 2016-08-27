package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.bean.SearchVoBean;
import com.shenma.top.imagecopy.util.prase.cate.AliBaBaBusseness;
import com.shenma.top.imagecopy.util.prase.cate.IBussness;
import com.shenma.top.imagecopy.util.prase.cate.TaobaoBussness;
import com.shenma.top.imagecopy.util.prase.cate.TmailBussness;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;

@Service
public class ProductCopyService {
	protected static Logger logger = Logger.getLogger("ProductCopyService");
	
	/**
	 * 查询items
	 * @param bean
	 * @return
	 * @throws ApiException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unused")
	public void searchByBean(SearchVoBean bean) throws IOException, ApiException, InterruptedException, IllegalAccessException, InvocationTargetException{
		String url=bean.getUrl();
		if(StringUtils.isEmpty(url)){
			return;
		}
		String indexUr = url.substring(0, url.indexOf(".com") + 4);
		bean.setUrl(indexUr);
		IBussness bussness=null;
		if(url.contains("1688.com")){
			if (url.contains("page/offerlist")) {
				String cateUrl = url.substring(0, url.indexOf(".htm") + 4);
				bean.setCateUrl(cateUrl);
			}
			bean.setShopType(1);
			bussness=new AliBaBaBusseness();
			if(StringUtils.isEmpty(bean.getCateUrl())){
				List<Map<String,Object>> cateList=bussness.getCate(bean.getUrl());
				bean.setCateItems(cateList);
			}
			bussness.getItems(bean);
		}else if(url.contains("taobao.com")){
			if (url.contains("category-")) {
				//String cateUrl = url.substring(0, url.indexOf(".htm") + 4);
				bean.setCateUrl(url);
			}
			bean.setShopType(2);
			TaobaoBussness tb=new TaobaoBussness();
			Document doc=tb.request(bean);
			if(StringUtils.isEmpty(bean.getCateUrl())){
				List<Map<String,Object>> cateList=tb.getCate(doc);
				bean.setCateItems(cateList);
			}
			tb.getItems(bean,doc);
		}else if(url.contains("tmall.com")){
			 if (url.indexOf("category-") > -1) {
				 //String cateUrl = url.substring(0, url.indexOf(".htm") + 4);
				 bean.setCateUrl(url);
			}
			bean.setShopType(3);
			TmailBussness tb=new TmailBussness();
			Document doc=tb.request(bean);
			if(StringUtils.isEmpty(bean.getCateUrl())){
				List<Map<String,Object>> cateList=tb.getCate(doc);
				bean.setCateItems(cateList);
			}

			tb.getItems(bean,doc);
		}
		
	}
	
	public SearchVoBean searchOneItem(String url) throws IOException, InterruptedException{
		if(StringUtils.isEmpty(url)){
			return new SearchVoBean();
		}
		if(url.endsWith("/"))url=url.substring(0,url.length()-1);
		IBussness bussness=null;
		if(url.contains("1688.com")){
			bussness=new AliBaBaBusseness();
		}else if(url.contains("taobao.com")){
		}else if(url.contains("tmall.com")){
		}else{	
		}
		SearchVoBean ret=bussness.getItem(url);
		return ret;
	}
	/**
	 * @param shopUrl
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public List<Map<String,Object>> searchCate(String shopUrl) throws IOException, InterruptedException{
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Document doc = JsonpUtil.getAliDefaultConnet(shopUrl);
		Elements elements=doc.select("ul.J_TCatsTree.cats-tree.J_TWidget li.cat.fst-cat:not(.float)");
		for(Element el:elements){
			Map<String, Object> cat=new HashMap<String, Object>(); 
			String url=el.select("a").first().attr("href");
			String name=el.select("a").first().text();
			cat.put("id",url);
			cat.put("name",name);
			list.add(cat);
			Elements liele=el.select("ul li");
			if(liele!=null){
				List<Map<String,Object>> sublist=new ArrayList<Map<String,Object>>();
				for(Element subel:liele){
					Map<String, Object> subcat=new HashMap<String, Object>(); 
					String suburl=subel.select("a").first().attr("href");
					String subname=subel.select("a").first().text();
					subcat.put("id",suburl);
					subcat.put("name",subname);
					sublist.add(subcat);
				}
				cat.put("children", sublist);
			}
		}
		return list;
	}
	
	public List<Item> searchTaobaoItems(SearchVoBean bean) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException{
		IBussness bussness=null;
		if(bean.getShopType()==1){
			bussness=new AliBaBaBusseness();
			bussness.getItems(bean);
		}else if(bean.getShopType()==2){
			TaobaoBussness tb=new TaobaoBussness();
			Document doc=tb.request(bean);
			tb.getItems(bean, doc);
		}else if(bean.getShopType()==3){
			TmailBussness tb=new TmailBussness();
			Document doc=tb.request(bean);
			tb.getItems(bean, doc);
		}
		
		return bean.getItems();
	}
	
	public void searchTaobaoItem(String url) throws IOException{
		
		
	}
	/**
	 * 解析淘宝itemsList
	 * @param bean
	 * @param doc
	 * @return
	 */
	private List<Item> parseTaobaoItem(SearchVoBean bean, Document doc) {
		List<Item> list=new ArrayList<Item>();
		Elements elements=doc.select(".shop-hesper-bd.grid .item3line1 .item");
		for(Element element:elements){
			Item item=new Item();
			Element ele=element.select(".photo a").first();   //商品链接
			item.setDetailUrl(ele.attr("href"));
			Element imgele=element.select(".photo img").first(); //图片地址
			item.setPicUrl(imgele.attr("src"));
			Element nameele=element.select(".detail a").first(); //标题
			item.setTitle(nameele.text());
			
			Element priceele=element.select(".attribute .c-price").first(); //价格
			item.setPrice(priceele.text().trim());
			
			Element numeele=element.select(".attribute .sale-num").first(); //已售数量
			item.setNum(Long.valueOf(numeele.text()));
			
			list.add(item);
		}
		
		Element totalspan=doc.select("div.search-result span").first();
		logger.info("测试:"+doc.html());
		bean.setTotals(Integer.valueOf(totalspan.text().trim()));
		bean.setItems(list);
		return list;
	}
	

	private void searchShopUrl(SearchVoBean bean) throws IOException, ApiException, InterruptedException{
		String searchUrl="http://s.taobao.com/search?&app=shopsearch&q="+URLEncoder.encode(bean.getShopName(),"gb2312");
		Document doc = JsonpUtil.getAliDefaultConnet(searchUrl);
		Element element=doc.select("div.item-not-found").first();
		if(element!=null)throw new ApiException("3", "不存在该店铺");
		Element countele=doc.select("span.shop-count b").first();
		if("1".equals(countele.text())){
			Element firstele=doc.select("span.shop-info-list a").first();
			String url=firstele.attr("href");
			bean.setUrl(url);
			Element imgele=doc.select(".list-info a.mall-icon").first();
			//String img=imgele.attr("src");
			//String tmailImg="http://img01.taobaocdn.com/tps/i1/T1_GC6XmXaXXXXXXXX-70-70.gif_70x70.jpg";
			if(imgele!=null){
				bean.setShopType(2);
			}else{
				bean.setShopType(1);
			}
			
		}else{
			throw new ApiException("3", "不能准确找到该店铺");
		}
		
	}
	public static void main(String[] args) throws IOException, ApiException, InterruptedException, IllegalAccessException, InvocationTargetException { 
		ProductCopyService t=new ProductCopyService();
		SearchVoBean bean=new SearchVoBean();
		bean.setShopName("爱婴宝恒旗舰店");
		t.searchShopUrl(bean);
		t.searchTaobaoItems(bean);
	}
}
