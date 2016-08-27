package com.bohusoft.alicopy.parse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.ecxeption.CopyBussinessEnums;
import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.exception.BusinessException;

@Component
public class TaobaoDetailHtmlParse extends BaseDetailHtmlParse implements DetailHtmlParse {

	protected static Logger logger = Logger.getLogger("AlibabaDetailHtmlParse");
	
	@Override
	public DetailHtmlParseBean parse(String url) throws CopyBussinessException {
		DetailHtmlParseBean offer = new DetailHtmlParseBean();
		String html=detailDubboUtil.fetchHtmlDetail(url);
		if(html==null){
			throw new CopyBussinessException(CopyBussinessEnums.HTML_NULL);
		}
		try {
			Document doc =Jsoup.parse(html);
			//sku信息解析
			Map<String,Object> skuInfo=getSkuConfig(doc.html());  
			
			offer.setSubject(parseSubject(doc));//标题
			
			offer.setZhutuList(parseZhutu(doc));//主图
			
			offer.setDesc(parseDesc(doc));//详情
			
			offer.setXiangqinImgList(parseXiangqing(offer.getDesc()));//详情图片
			
			offer.setBaseProperties(parseProperties(doc)); //基本属性
			
			parseSkuList(doc,offer,skuInfo); //销售sku属性
			
			parsePrice(doc,offer); //设置价格方式和价格
			
			parseOtherProperties(doc,offer,skuInfo); //其他属性
			
		} catch (Exception e) {
			logger.error("解析淘宝详情页出错!url:"+url+":"+e.getMessage());
		}
		
		return offer;
	}
	
	/**
	 * 解析标题
	 * @param doc
	 */
	public String  parseSubject(Document doc){
		Element element=doc.select("#J_Title .tb-main-title").first();
		String subject=element.attr("data-title");
		return subject;
	}
	
	
	/**
	 * 解析价格,根据getSkuProps的size值设置
	 * 
	 * 
	 */
	public void parsePrice(Document doc,DetailHtmlParseBean offer){
		if(offer.getSkuProps().size()>0){
			offer.setPriceType(2);
		}else{
			offer.setPriceType(1);
		}
		Element ele=doc.select("input[name=current_price]").first();
		String price=null;
		if(ele!=null){
			price=ele.val();
		}
		offer.setPrice(price);
	}
	
	/**
	 * 解析主图
	 * @param doc
	 * @return
	 * @throws CopyBussinessException
	 */
	public List<String> parseZhutu(Document doc) throws CopyBussinessException{
		Element element=doc.getElementById("J_UlThumb");
		List<String> list=new ArrayList<String>();
		if(element==null)return list;
		Elements images=element.select("img[data-src]");
		for(Element ele:images){
			String imageUrl=ele.attr("data-src");
			if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
			imageUrl=imageUrl.substring(0, imageUrl.lastIndexOf("_"));
			list.add(imageUrl);
		}
		return list;
	}
	
	/**
	 * 解析所有详情图片
	 * @param doc
	 * @return
	 * @throws CopyBussinessException
	 */
	private List<String> parseXiangqing(String desc) throws CopyBussinessException{
		List<String> list=new ArrayList<String>();
		Element desdiv=Jsoup.parse(desc);
		if(desdiv==null)return list;
		Elements images=desdiv.select("img[src]");
		for(Element imageEle:images){
			String url=imageEle.attr("src");
			if(url.endsWith("_.webp")){
				url=url.substring(0, url.length()-6);
			}
			if(url.matches(imgreg)){
				list.add(url);
			}else{
				continue;
			}
			
		}
		return list;
	}
	
	/**
	 * 解析基本属性
	 * @param doc
	 * @return
	 * @throws CopyBussinessException
	 */
	private Map<String,String> parseProperties(Document doc) throws CopyBussinessException{
		Map<String, String> attributesMap = new HashMap<String, String>(); // 基本属性Map
		Elements elements=doc.select("#attributes li");
		if(elements==null)return attributesMap;
		for(Element ele:elements){
			String text=ele.text();
			String title=ele.attr("title").trim();
			String name="";
			if("".equals(title)){ //属性值为空
				name=text.substring(0,text.length()-1);
			}else if(text.indexOf(title)>-1){//属性值存在
				name=text.substring(0,text.lastIndexOf(title)-1);
				if(name.endsWith(":"))name=name.substring(0,name.length()-1);
				title=title.replaceAll(" ", "");
			}else{  //多选
				String[] checkbox=title.split(" ");
				String str="";
				for(String obj:checkbox){
					str+=obj.trim()+"|";
				}
				title=str.substring(0,str.length()-1);
			}
			
			attributesMap.put(name, title);
		}
		return attributesMap;
	}
	
	@SuppressWarnings("unchecked")
	private void parseSkuList(Document doc,DetailHtmlParseBean offer,Map<String,Object> skuInfo) throws JsonParseException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,String> transfMap=new HashMap<String,String>(); //键值映射
		//设置skuProps的值
		List<Map<String,Object>> skuProps=new ArrayList<Map<String,Object>>();
		offer.setSkuProps(skuProps);
		Elements elements=doc.select("#J_isku ul[data-property]");
		for(Element ele:elements){
			Map<String,Object> skuProp=new HashMap<String,Object>();
			skuProps.add(skuProp);
			String prop=ele.attr("data-property");
			skuProp.put("prop", prop);
			List<Map<String,Object>> value=new ArrayList<Map<String,Object>>();
			skuProp.put("value", value);
			Elements lis=ele.select("li");
			for(Element liele:lis){
				String dataValue=liele.attr("data-value");
				String dataText=liele.select("span").first().text();
				transfMap.put(dataValue, dataText);
				Map<String,Object> nameMap=new HashMap<>(4);
				nameMap.put("name", dataText);
				//设置imageUrl
				Element aEle=liele.select("a[style]").first();
				if(aEle!=null){ //颜色有图片的
					String style=aEle.attr("style");
					Pattern pattern = Pattern.compile("(?<=url\\()[\\s\\S]*\\_");
					Matcher matcher = pattern.matcher(style);
					String imageUrl=null;
					if(matcher.find()){
						imageUrl=matcher.group(0);
					}
					imageUrl=imageUrl.trim();
					imageUrl=imageUrl.substring(0, imageUrl.length()-1);
					if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
					nameMap.put("imageUrl", imageUrl);
				}
				value.add(nameMap);
			}
		}
		
		//设置skuMaps
		Map<String,Object> skuMaps=new HashMap<String,Object>();
		Map<String,Object> valItemInfo=(Map<String, Object>) skuInfo.get("valItemInfo");
		Map<String,Object> skuMap=(Map<String, Object>) valItemInfo.get("skuMap");
		for(String key:skuMap.keySet()){
			String[] skukeys=key.substring(1, key.length()-1).split(";"); //格式为 ;21433:129813;1627207:28332;
			String[] skuValues=new String[skukeys.length];//临时变量
			for(int i=0;i<skukeys.length;i++){
				String skuName=transfMap.get(skukeys[i]);
				skuValues[i]=skuName;
			}
			String keyValue=""; //格式为米白色(现货)>L
			for(String skuValue:skuValues){
				keyValue+=skuValue+">";
			}
			keyValue=keyValue.substring(0, keyValue.length()-1);
			
			
			Map<String,Object> skuMapValue=(Map<String, Object>) skuMap.get(key);
			String price=BeanUtils.getProperty(skuMapValue, "price");
			String skuId=BeanUtils.getProperty(skuMapValue, "skuId");
			Map<String,Object> value=new HashMap<String,Object>();
			value.put("price", price);
			value.put("specId", skuId);
			value.put("canBookCount", 999);
			
			//设置skuMaps的值
			skuMaps.put(keyValue, value);
			
		}
		offer.setSkuMap(skuMaps);
		logger.info(skuMaps);
		
	}
	
	
	/**
	 * 
	 * 解析sku属性
	 * @param html
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSkuConfig(String html)
			throws JsonParseException, JsonMappingException, IOException {
		Pattern pattern = Pattern.compile("(?<=Hub.config.set\\('sku',)[\\s\\S]*?\\)\\;");
		//Pattern pattern = Pattern.compile("(?<=valItemInfo      :)[\\s\\S]*?\\)\\;");
		//Pattern pattern = Pattern.compile("(?<=Hub.config.set\\('sku',)[\\s\\S]*?\\)\\;");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str = str.replaceAll("\\n", "");
		//str = str.replaceAll("'", "\"");
		str=str.substring(0, str.length()-2);
		if(str=="")throw new BusinessException("401","请检查您的地址是否有误!");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}

		return ret;
	}
	
	
	/**
	 * 读取详情
	 * @param doc
	 * @return
	 */
	private String parseDesc(Document doc){
		Pattern pattern = Pattern.compile("//dsc.taobaocdn.com.*?(?=\' :)");
		Matcher matcher = pattern.matcher(doc.html());
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		descriptionurl="http:"+descriptionurl;
		Document scriptdoc = JsonpUtil.getAliDefaultConnet(descriptionurl);
		String description=scriptdoc.body().html();
		description=description.replaceAll("\\\\ ", " ");
		description=description.substring(11, description.length()-2);
		return description;
	}
	
	/**
	 * 设置其他的一些属性
	 * @param doc
	 * @param offer
	 * @throws CopyBussinessException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void parseOtherProperties(Document doc,DetailHtmlParseBean offer,Map<String,Object> skuInfo) throws CopyBussinessException{
		Map<String,Object> otherProperties=new HashMap<String,Object>();
		
		/*otherProperties.put("categoryID", detailConfig.get("catid"));
		
		try {
			//价格范围
			if("true".equals(detailConfig.get("isRangePriceSku"))){
				String priceRanges=BeanUtils.getProperty(detailData, "priceRange");
				otherProperties.put("priceRanges", priceRanges);
			}
			//sku是否支持属性
			if(detailConfig.containsKey("isSKUOffer")){
				String skuTradeSupport=(String)PropertyUtils.getProperty(detailConfig, "isSKUOffer");
				otherProperties.put("skuTradeSupport", skuTradeSupport);
			}
			
			//销售方式
			Element xsspan=doc.select(".obj-seller-package span").first();
			if(xsspan!=null){
				String xsstr=xsspan.text();
				String[] xsstrs=xsstr.split("=");
				//销售单位
				otherProperties.put("7588903", xsstrs[0].substring(xsstrs[0].length()-1, xsstrs[0].length()));
				//每销售单位数量
				otherProperties.put("100022873", xsstrs[1].substring(0, xsstrs[1].length()-1));
			}
			
			
		} catch (Exception e) {
			logger.error("解析OtherProperties数据结构出错:"+e.getMessage());
			throw new CopyBussinessException("解析OtherProperties数据结构出错");
		} */
		
		offer.setOtherProperties(otherProperties);
		
	}

	@Override
	public boolean validateUrl(String url) {
		if(StringUtils.isEmpty(url))return false;
		if(url.indexOf("taobao.com")>-1)return true;
		return false;
	}

}
