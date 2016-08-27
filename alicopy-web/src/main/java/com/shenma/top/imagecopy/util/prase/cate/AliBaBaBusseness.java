package com.shenma.top.imagecopy.util.prase.cate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.aliutil.util.AliConstant;
import com.shenma.top.imagecopy.util.Constant;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.bean.SearchVoBean;
import com.shenma.top.imagecopy.util.prase.AliBaBaImageParse;
import com.taobao.api.domain.Item;

public class AliBaBaBusseness implements IBussness {

	@Override
	public List<Map<String, Object>> getCate(String url) throws IOException, InterruptedException{
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String offerListUrl=url+"/page/offerlist.htm";
		Document doc = JsonpUtil.getAliCateConnet(offerListUrl);
		//String html=TaobaoCateUtil.getTmail(offerListUrl);
		//Document doc=Jsoup.parse(html);
		
		String uid=doc.select("#feedbackUid").val();
		String menuurl=url+"/event/app/topNav/view.htm?topNavIndex=3&uid="+uid;
		Document menudoc=JsonpUtil.getAliDefaultConnet(menuurl);
		String htmlstr=parse(menudoc.html());
		menudoc=Jsoup.parse(htmlstr);
		System.out.println(htmlstr);
		Elements elements=menudoc.select("li[data-index]");
		//Elements elements=menudoc.select("li.show:not(.category-hide)");
		if(url.indexOf("/page/offerlist")>-1)return list;
		for(Element el:elements){
			Map<String, Object> cat=new HashMap<String, Object>();
			String cateurl=el.select("a").get(1).attr("href");
			String name=el.select("a").get(1).text().trim();
			if(cateurl.startsWith("//"))cateurl="http:"+cateurl;
			cat.put("id",cateurl);
			cat.put("name",name);
			list.add(cat);
			String tempstr=el.select("input[name=subSize]").val();
			Integer subSize=Integer.valueOf(tempstr.substring(0,tempstr.length()-1));
			if(subSize!=0){
				List<Map<String,Object>> sublist=new ArrayList<Map<String,Object>>();
				Elements subelements=menudoc.select("div[class="+el.attr("data-index")+"][sub-category]>ul>li");
				for(Element subel:subelements){
					Map<String, Object> subcat=new HashMap<String, Object>(); 
					if(subel.select("a").size()==0)continue;
					String suburl=subel.select("a").get(1).attr("href");
					String subname=subel.select("a").get(1).text().trim();
					if(suburl.startsWith("//"))suburl="http:"+suburl;
					subcat.put("id",suburl);
					subcat.put("name",subname);
					sublist.add(subcat);
				}
				cat.put("children", sublist);
			}
		}
		return list;
	}

	private String parse(String html){
		html=html.replaceAll("\\\\&quot;", "");
		html=html.replaceAll("\\\\t", "");
		html=html.replaceAll("\\\\n", "");
		html=html.replaceAll("\\\\r", "");
		html=html.replaceAll("&lt;\\\\/a&gt; &lt;\\\\/li&gt;", "");
		html=html.replaceAll("\\\\\"", "");
		html=html.replaceAll("&gt;&gt;&lt;\\\\/a&gt;\\\\/li&gt;", "");
		html=html.replaceAll("&lt;\\\\/a&gt;", "");
		html=html.replaceAll("&lt;\\\\/li&gt;", "");
		html=html.replaceAll("&lt;\\\\/span&gt;", "");
		html=html.replaceAll("&lt;\\\\/div&gt;", "");
		html=html.replaceAll("&lt;\\\\/ul&gt;", "");
		return html;
		
	}
	
	public List<String> getUrls(String url,String cateUrl,Integer totalPages) throws InterruptedException{
		String finalUrl=cateUrl;
		List<String> retList=new ArrayList<String>();
		if(StringUtils.isEmpty(cateUrl)){
			finalUrl=url+"/page/offerlist.htm?sortType=showcase";
		}
		if(!finalUrl.contains("?")){
			finalUrl+="?sortType=showcase";
		}
		String keywords="";
		/*if(StringUtils.isNotEmpty(bean.getKey())){
			url=bean.getUrl()+"/page/offerlist.htm?sortType=showcase";
			keywords="&keywords="+URLEncoder.encode(bean.getKey(),"gb2312");
		}*/
		for(int pageNo=1;pageNo<=totalPages;pageNo++){
			String pageNum="";
			pageNum="&pageNum="+pageNo;
			Thread.sleep(Constant.search_cate_delay_time);
			Document doc = JsonpUtil.getAliCateConnet(finalUrl+keywords+pageNum);
			//String html=TaobaoCateUtil.getTmail(url);
			//Document doc = Jsoup.parse(html);
			Elements elements=doc.select(".wp-offerlist-windows li");
			if(elements.size()==0){
				elements=doc.select(".wp-offerlist-catalogs li");
			}
			for(Element element:elements){
				Element ele=element.select(".image a").first();   //商品链接
				String detailUrl=ele.attr("href");
				if(detailUrl.startsWith("//"))detailUrl="http:"+detailUrl;
				retList.add(detailUrl);
			}
		}
		return retList;
	}
	
	@Override
	public void getItems(SearchVoBean bean) throws IOException, InterruptedException {
		String url=bean.getCateUrl();
		if(StringUtils.isEmpty(url)){
			url=bean.getUrl()+"/page/offerlist.htm?sortType=showcase";
		}
		if(!url.contains("?")){
			url+="?sortType=showcase";
		}
		String keywords="";
		/*if(StringUtils.isNotEmpty(bean.getKey())){
			url=bean.getUrl()+"/page/offerlist.htm?sortType=showcase";
			keywords="&keywords="+URLEncoder.encode(bean.getKey(),"gb2312");
		}*/
		String pageNum="";
		if(bean.getPageNo()!=null){
			pageNum="&pageNum="+bean.getPageNo();
		}
		url+=keywords+pageNum;
		Document doc = JsonpUtil.getAliCateConnet(url);
		//String html=TaobaoCateUtil.getTmail(url);
		//Document doc = Jsoup.parse(html);
		Elements elements=doc.select(".wp-offerlist-windows li");
		if(elements.size()==0){
			otherItems(doc,bean);
		}else{
			List<Item> list=new ArrayList<Item>();
			for(Element element:elements){
				Item item=new Item();
				Element ele=element.select(".image a").first();   //商品链接
				String detailUrl=ele.attr("href");
				if(detailUrl.startsWith("//"))detailUrl="http:"+detailUrl;
				item.setDetailUrl(detailUrl);
				
				Element imgele=element.select(".image img").first(); //图片地址
				item.setPicUrl(imgele.attr("data-lazy-load-src"));
				
				Element nameele=element.select(".title a").first(); //标题
				item.setTitle(nameele.text());
				
				Element priceele=element.select(".price em").first(); //价格
				item.setPrice(priceele.text().trim());
				
				Element numeele=element.select(".booked-count .booked-counts").first(); //已售数量
				if(numeele!=null){
					String numstr=numeele.text();
					if(numstr.endsWith("万")){
						double tempdobule=Double.valueOf(numstr.substring(0,numstr.length()-1))*10000;
						item.setNum(Math.round(tempdobule));
					}else{
						item.setNum(Long.valueOf(numeele.text()));
					}	
				}else{
					item.setNum(new Long(0));
				}
				
				list.add(item);
			}
			
			Element totalspan=doc.select(".offer-count").first();
			if(totalspan!=null){
				bean.setTotals(Integer.valueOf(totalspan.text().trim()));
			}else{
				bean.setTotals(list.size());
			}
			Element pageNospan=doc.select("li.pagination a.current").first();
			if(pageNospan!=null){
				String pageInfo=pageNospan.text().trim();
				bean.setPageNo(Integer.valueOf(pageInfo));
			}else{
				bean.setPageNo(1);
			}
			Element totalpageEle=doc.select(".page-count").first();//总页数
			if(totalpageEle!=null){
				bean.setTotalPages(Integer.valueOf(totalpageEle.text().trim()));
			}else{
				bean.setTotalPages(1);
			}
			bean.setItems(list);
		}
	}
	
	
	private void otherItems(Document doc,SearchVoBean bean){
		Elements elements=doc.select(".wp-offerlist-catalogs li");
		List<Item> list=new ArrayList<Item>();
		for(Element element:elements){
			Item item=new Item();
			Element ele=element.select(".image a").first();   //商品链接
			item.setDetailUrl(ele.attr("href"));
			
			Element imgele=element.select(".image img").first(); //图片地址
			item.setPicUrl(imgele.attr("src"));
			
			Element nameele=element.select(".title a").first(); //标题
			item.setTitle(nameele.text());
			
			Element priceele=element.select(".price em").first(); //价格
			item.setPrice(priceele.text().trim());
			
			Element numeele=element.select(".props .booked-count").first(); //已售数量
			if(numeele!=null){
				item.setNum(Long.valueOf(numeele.text().trim()));
			}else{
				item.setNum(new Long(0));
			}
			
			list.add(item);
		}
		Element totalspan=doc.select(".offer-count").first();
		if(totalspan!=null){
			bean.setTotals(Integer.valueOf(totalspan.text().trim()));
		}else{
			bean.setTotals(list.size());
		}
		Element pageNospan=doc.select("li.pagination a.current").first();
		if(pageNospan!=null){
			String pageInfo=pageNospan.text().trim();
			bean.setPageNo(Integer.valueOf(pageInfo));
		}else{
			bean.setPageNo(1);
		}
		Element totalpageEle=doc.select(".page-count").first();//总页数
		if(totalpageEle!=null){
			bean.setTotalPages(Integer.valueOf(totalpageEle.text().trim()));
		}else{
			bean.setTotalPages(1);
		}
		bean.setItems(list);
	}
	
	public SearchVoBean getItem(String url) throws IOException, InterruptedException{
		SearchVoBean ret=new SearchVoBean();
		List<Item> list=new ArrayList<Item>();
		Document doc=JsonpUtil.getAliDefaultConnet(url);
		String subject=doc.select("#mod-detail-title .d-title").first().text().trim();
		AliBaBaImageParse pa=new AliBaBaImageParse();
		List<ImageVoBean> imaglist=pa.genZhutuImage(doc, url,subject);
		String imageUrl="";
		if(imaglist.size()>0){
			imageUrl=imaglist.get(0).getUrl();
		}
		Item item=new Item();
		item.setDetailUrl(url);
		item.setPicUrl(imageUrl);
		item.setTitle(subject);
		Map<String,Object> c=getIDetailConfig(doc.html());
		if(c.get("refPrice")!=null){
			item.setPrice(c.get("refPrice").toString());
		}
		list.add(item);
		ret.setItems(list);
		ret.setTotals(1);
		ret.setTotalPages(1);
		ret.setPageNo(1);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> getIDetailConfig(String html) throws JsonParseException, JsonMappingException, IOException{
		Pattern pattern = Pattern.compile("(?<=iDetailConfig = )[\\s\\S]*?(?=\\;)");
		Matcher matcher = pattern.matcher(html);
		String str="";
		if(matcher.find()){
			str=matcher.group(0);
		}
		str=str.replaceAll("\\n", "");
		str=str.replaceAll("'", "\"");
		HashMap<String, Object> ret=JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		return ret;
	}
	
}
