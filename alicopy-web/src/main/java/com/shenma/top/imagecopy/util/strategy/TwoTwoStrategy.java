package com.shenma.top.imagecopy.util.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;

public class TwoTwoStrategy implements TopToAliIStrategy{
	protected static Logger logger = Logger.getLogger("TwoTwoStrategy");
	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer) {
		Map<String,Object> alispecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> alispecAttrMapisSpecPicAttrObj2=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj2=null;
		String str="";
		String str2="";
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			boolean isSpecPicAttr=(boolean) specAttrObj.get("isSpecPicAttr");
			if(isSpecPicAttr==true){
				alispecAttrMapisSpecPicAttrObj=specAttrObj;
			}else{
				alispecAttrMapisSpecPicAttrObj2=specAttrObj;
			}
		}
		for(Map<String,Object> taobaospecAttrName:taobaoskuList){
			
			if(taobaospecAttrName.containsKey("isSpecPicAttr")){
				taobaospecAttrMapisSpecPicAttrObj=taobaospecAttrName;
			}else{
				taobaospecAttrMapisSpecPicAttrObj2=taobaospecAttrName;
			}
		}
		if(alispecAttrMapisSpecPicAttrObj!=null&&taobaospecAttrMapisSpecPicAttrObj!=null){
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
				for(Map<String,Object> tbMap2:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj2.get("childs")){
					Map<String,Object> sku=getskuPrice(tbMap.get("value").toString(), tbMap2.get("value").toString(), taobaoParmas);
					
	
					Map<String,Object> specAttributes=new HashMap<String,Object>();
					sku.put("specAttributes", specAttributes);
					specAttributes.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), tbMap.get("value").toString());
					specAttributes.put(alispecAttrMapisSpecPicAttrObj2.get("fid").toString(), tbMap2.get("value").toString());
					skuList.add(sku);
				}
			}
			
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
				str+=tbMap.get("value").toString()+"|";
			}
			str=str.substring(0, str.length()-1);
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj2.get("childs")){
				str2+=tbMap.get("value").toString()+"|";
			}
			str2=str2.substring(0, str2.length()-1);
			productFeatures.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), str);
			productFeatures.put(alispecAttrMapisSpecPicAttrObj2.get("fid").toString(), str2);
		}else{//2个都没有pic的值该如何
			radom(productFeatures, specAttrMap, skuList, taobaoParmas, taobaoskuList);
		}
		
	}
	
	
	private Map<String,Object> getskuPrice(String key1,String key2,Map<String,Object> taobaoParmas){
		Map<String,Object> skuObj=new HashMap<String,Object>();
		Map<String,Object> skuInfo=(Map<String, Object>) taobaoParmas.get("skuInfo");
		Map<String,Object> skuMap=null;
		if(skuInfo.containsKey("skuMaps")){
			skuMap=(Map<String, Object>) skuInfo.get("skuMaps");
		}else{
			return skuObj;
		}
		try {
			if(skuMap.containsKey(key1+">"+key2)){
				skuObj=(Map<String, Object>) skuMap.get(key1+">"+key2);
			}else if(skuMap.containsKey(key2+">"+key1)){
				skuObj=(Map<String, Object>) skuMap.get(key2+">"+key1);
			}else {
				skuObj.put("price", taobaoParmas.get("price").toString());
				skuObj.put("retailPrice", taobaoParmas.get("price").toString());
				skuObj.put("amountOnSale", 999);
			}
		} catch (Exception e) {
			logger.error("解析sku属性失败");
		}
		return skuObj;
	}
	
	/**
	 * 2个都没有pic的值该如何
	 * @param productFeatures
	 * @param specAttrMap
	 * @param skuList
	 * @param taobaoParmas
	 * @param taobaoskuList
	 */
	private void radom(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList){
		
		Map<String,Object> alispecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> alispecAttrMapisSpecPicAttrObj2=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj=taobaoskuList.get(0);
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj2=taobaoskuList.get(1);
		String str="";
		String str2="";
		List<Map<String,Object>> specAttrMapList=new ArrayList<Map<String,Object>>(2);
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			specAttrMapList.add(specAttrObj);
		}
		alispecAttrMapisSpecPicAttrObj=specAttrMapList.get(0);
		alispecAttrMapisSpecPicAttrObj2=specAttrMapList.get(1);
		
		for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
			for(Map<String,Object> tbMap2:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj2.get("childs")){
				Map<String,Object> sku=getskuPrice(tbMap.get("value").toString(), tbMap2.get("value").toString(), taobaoParmas);
				Map<String,Object> specAttributes=new HashMap<String,Object>();
				sku.put("specAttributes", specAttributes);
				specAttributes.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), tbMap.get("value").toString());
				specAttributes.put(alispecAttrMapisSpecPicAttrObj2.get("fid").toString(), tbMap2.get("value").toString());
				skuList.add(sku);
			}
			
		}
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
				str+=tbMap.get("value").toString()+"|";
			}
			str=str.substring(0, str.length()-1);
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj2.get("childs")){
				str2+=tbMap.get("value").toString()+"|";
			}
			str2=str2.substring(0, str2.length()-1);
			productFeatures.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), str);
			productFeatures.put(alispecAttrMapisSpecPicAttrObj2.get("fid").toString(), str2);
		
	}

}
