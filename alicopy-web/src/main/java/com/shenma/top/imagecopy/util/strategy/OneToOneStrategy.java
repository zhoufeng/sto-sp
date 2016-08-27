package com.shenma.top.imagecopy.util.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shenma.aliutil.entity.goods.Offer;

public class OneToOneStrategy implements TopToAliIStrategy {
	protected static Logger logger = Logger.getLogger("TwoTwoStrategy");
	@SuppressWarnings("unchecked")
	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer) {
		Map<String,Object> alispecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj=null;
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			alispecAttrMapisSpecPicAttrObj=specAttrObj;
		}
		for(Map<String,Object> taobaospecAttrName:taobaoskuList){
			taobaospecAttrMapisSpecPicAttrObj=taobaospecAttrName;
		}
		for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
			Map<String,Object> sku=getskuPrice(tbMap.get("value").toString(), taobaoParmas);
			
			Map<String,Object> specAttributes=new HashMap<String,Object>();
			sku.put("specAttributes", specAttributes);
			specAttributes.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), tbMap.get("value").toString());
			skuList.add(sku);
		}
		String str="";
		for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
			str+=tbMap.get("value").toString()+"|";
		}
		str=str.substring(0, str.length()-1);
		productFeatures.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), str);

	}
	private Map<String,Object> getskuPrice(String key1,Map<String,Object> taobaoParmas){
		Map<String,Object> skuObj=new HashMap<String,Object>();
		Map<String,Object> skuInfo=(Map<String, Object>) taobaoParmas.get("skuInfo");
		Map<String,Object> skuMap=null;
		if(skuInfo.containsKey("skuMaps")){
			skuMap=(Map<String, Object>) skuInfo.get("skuMaps");
		}else{
			skuObj.put("price", taobaoParmas.get("price").toString());
			skuObj.put("retailPrice", taobaoParmas.get("price").toString());
			skuObj.put("amountOnSale", 999);
			return skuObj;
		}	
		try {
			if(skuMap.containsKey(key1)){
				skuObj=(Map<String, Object>) skuMap.get(key1);
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

}
