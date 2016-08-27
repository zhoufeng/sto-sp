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
import org.apache.commons.beanutils.PropertyUtils;
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
import com.shenma.top.imagecopy.util.exception.BusinessException;

@Component
public class AlibabaDetailHtmlParse extends BaseDetailHtmlParse implements DetailHtmlParse {
	protected static Logger logger = Logger.getLogger("AlibabaDetailHtmlParse");
	
	@Override
	public DetailHtmlParseBean parse(String url) throws CopyBussinessException {
		DetailHtmlParseBean offer = new DetailHtmlParseBean();
		String html=detailDubboUtil.fetchHtmlDetail(url);
		if(html==null){
			throw new CopyBussinessException(CopyBussinessEnums.HTML_NULL);
		}
		Document doc =Jsoup.parse(html);
		Map<String, Object> detailConfig = getIDetailConfig(doc.html());
		Map<String, Object> detailDataSku = getIDetailData(doc.html());
		
		offer.setSubject(parseSubject(doc));//标题
		
		offer.setZhutuList(parseZhutu(doc));//主图
		
		parsePirce(detailDataSku, offer);  //设置价格方式和价格
		
		offer.setXiangqinImgList(parseXiangqing(doc));//详情
		
		offer.setDesc(parseDesc(doc));//详情
		
		offer.setBaseProperties(parseProperties(doc)); //基本属性
		
		parseSkuList(doc,detailConfig,detailDataSku,offer); //销售sku属性
		
		parseOtherProperties(doc,detailConfig,detailDataSku,offer); //其他属性
		
		
		return offer;
	}
	
	/**
	 * 解析标题
	 * @param doc
	 */
	public String  parseSubject(Document doc){
		String subject = doc.select("h1.d-title").first().text();
		return subject;
	}
	
	/**
	 * 解析价格,按数量方式的话,有价格.  按规格的话,没有
	 * @param detailDataSku
	 * @param offer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void parsePirce(Map<String, Object> detailDataSku,DetailHtmlParseBean offer){
		String priceRangeStr = "";
		if(detailDataSku.containsKey("priceRange")){
			offer.setPriceType(1);
			List<List<Object>> priceRange = (List<List<Object>>) detailDataSku.get("priceRange");
			for (List<Object> pricelist : priceRange) {
				priceRangeStr += pricelist.get(0).toString() + ":"
						+ pricelist.get(1).toString() + "`";
			}
			priceRangeStr = priceRangeStr.substring(0,priceRangeStr.length() - 1);
			offer.setPrice(priceRangeStr);
		}else{
			offer.setPriceType(2);
		}
	}
	
	/**
	 * 解析主图
	 * @param doc
	 * @return
	 * @throws CopyBussinessException
	 */
	public List<String> parseZhutu(Document doc) throws CopyBussinessException{
		Element element=doc.getElementById("dt-tab");
		List<String> list=new ArrayList<String>();
		if(element==null)return list;
		Elements images=element.select("li[data-imgs]");
		for(Element ele:images){
			String url=ele.attr("data-imgs");
			Map<String, Object> map;
			try {
				map = JacksonJsonMapper.getInstance().readValue(url, HashMap.class);
			} catch (Exception e) {
				throw new CopyBussinessException("解析主图地址出错");
			}
			String imageUrl=map.get("original").toString();
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
	private List<String> parseXiangqing(Document doc) throws CopyBussinessException{
		List<String> list=new ArrayList<String>();
		Element desdiv=doc.getElementById("de-description-detail");
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
	 * 解析销售属性
	 * @param doc
	 * @return
	 * @throws CopyBussinessException
	 */
	private Map<String,String> parseProperties(Document doc) throws CopyBussinessException{
		Element attributes = doc.select("#mod-detail-attributes").first();
		Map<String, String> attributesMap = new HashMap<String, String>(); // 基本属性Map
		if (attributes != null) {
			Elements feautres = attributes.select("td.de-feature");
			Elements fvalues = attributes.select("td.de-value");
			if (fvalues != null && fvalues.size() > 0) { // 获得基本属性2种方式,目前不清楚有什么条件
				for (int i = 0; i < fvalues.size(); i++) {
					String name = feautres.get(i).text();
					String fValue = fvalues.get(i).text();
					attributesMap.put(name, fValue);
				}
			} else {
				for (Element feautre : feautres) {
					String nameValue = feautre.text();
					if (!StringUtils.isEmpty(nameValue)) {
						String[] nameValueArr = nameValue.split("：");
						String name = nameValueArr[0].trim();
						String fValue = nameValueArr[1].trim();
						attributesMap.put(name, fValue);
					}
				}
			}
		}
		return attributesMap;
	}
	
	@SuppressWarnings("unchecked")
	private void parseSkuList(Document doc,Map<String, Object> detailConfig,Map<String, Object> detailData,DetailHtmlParseBean offer){
		if(detailData.containsKey("skuProps")){
			List<Map<String,Object>> skuProps=(List<Map<String, Object>>) detailData.get("skuProps");
			offer.setSkuProps(skuProps);
		}
		if(detailData.containsKey("skuMap")){
			Map<String,Object> skuMap=(Map<String, Object>) detailData.get("skuMap");
			offer.setSkuMap(skuMap);
		}
		
		if(offer.getSkuProps()!=null){
			List<Map<String,Object>> skuProps=(List<Map<String, Object>>) detailData.get("skuProps");
			skuProps.get(0).get("");
		}
		
	}
	
	/**
	 * 读取详情
	 * @param doc
	 * @return
	 */
	private String parseDesc(Document doc){
		Element ele = doc.select("#de-description-detail").first();
		String desc = ele.html();
		return desc;
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
	private void parseOtherProperties(Document doc,Map<String, Object> detailConfig,Map<String, Object> detailData,DetailHtmlParseBean offer) throws CopyBussinessException{
		Map<String,Object> otherProperties=new HashMap<String,Object>();
		
		otherProperties.put("categoryID", detailConfig.get("catid"));
		
		
		try {
			String beginAmount=BeanUtils.getProperty(detailConfig, "beginAmount");
			if(beginAmount!=null){
				otherProperties.put("beginAmount", beginAmount); //beginAmount的值
			}
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
		} 
		
		offer.setOtherProperties(otherProperties);
		
	}
	
	/**
	 * 获得一该商品商品的详细信息如catsId等
	 * 
	 * @param html
	 * @return
	 * @throws CopyBussinessException 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getIDetailConfig(String html) throws CopyBussinessException {
		Pattern pattern = Pattern.compile("(?<=iDetailConfig = )[\\s\\S]*?\\}\\;");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str = str.replaceAll("\\n", "");
		//str = str.replaceAll(" ", "");
		if(str=="")throw new BusinessException("401","请检查您的地址是否有误!");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			try {
				ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
			} catch (Exception e) {
				logger.error("解析iDetailConfig数据结构出错:"+e.getMessage());
				throw new CopyBussinessException("解析iDetailConfig数据结构出错");
			}
		}
		return ret;
	}

	/**
	 * 获得商品的基本属性,颜色及尺寸等
	 * 
	 * @param html
	 * @param doc
	 * @return
	 * @throws CopyBussinessException 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getIDetailData(String html) throws CopyBussinessException {
		Pattern pattern = Pattern.compile("(?<=iDetailData = )[\\s\\S]*?(?=\\}\\;)");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str += "}";
		str = str.replaceAll("\\n", "");
		str = str.replaceAll("\\t", "");
		str = str.replaceAll("&gt;", ">");
		//str = str.replaceAll(" ", "");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			try {
				ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
			} catch (Exception e) {
				logger.error("解析iDetailData数据结构出错:"+e.getMessage());
				throw new CopyBussinessException("解析iDetailData数据结构出错");
			}
		}
		ret = (HashMap<String, Object>) ret.get("sku");
		return ret;
	}

	@Override
	public boolean validateUrl(String url) {
		if(StringUtils.isEmpty(url))return false;
		if(url.indexOf("1688.com")>-1)return true;
		return false;
	}
	
}
