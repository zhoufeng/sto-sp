package com.shenma.top.imagecopy.util.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.shenma.aliutil.entity.goods.Offer;

public class OneToTwoStrategy implements TopToAliIStrategy {

	@SuppressWarnings("unchecked")
	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer) {
		Map<String,Object> alispecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> alispecAttrMapisSpecPicAttrObj2=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj=taobaoskuList.get(0);
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
		
		if(taobaospecAttrMapisSpecPicAttrObj.containsKey("isSpecPicAttr")&&alispecAttrMapisSpecPicAttrObj!=null){
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
					Map<String,Object> sku=new HashMap<String,Object>();
					sku.put("retailPrice", taobaoParmas.get("price").toString());
					sku.put("price", taobaoParmas.get("price").toString());
					sku.put("amountOnSale", 100);
					Map<String,Object> specAttributes=new HashMap<String,Object>();
					sku.put("specAttributes", specAttributes);
					specAttributes.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), tbMap.get("value").toString());
					
					if(alispecAttrMapisSpecPicAttrObj2.get("isNeeded").equals("Y")){
						specAttributes.put(alispecAttrMapisSpecPicAttrObj2.get("fid").toString(), "123");
					}
					skuList.add(sku);
			}
			
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
				str+=tbMap.get("value").toString()+"|";
			}
			str=str.substring(0, str.length()-1);

			str2="123";
			productFeatures.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), str);
			productFeatures.put(alispecAttrMapisSpecPicAttrObj2.get("fid").toString(), str2);
		}else {
			List<Map<String,Object>> tempList=new ArrayList<Map<String,Object>>();
			for(String specAttrName:specAttrMap.keySet()){
				Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
				tempList.add(specAttrObj);
			}
			boolean normal=true;
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
				Map<String,Object> sku=new HashMap<String,Object>();
				sku.put("retailPrice", taobaoParmas.get("price").toString());
				sku.put("price", taobaoParmas.get("price").toString());
				sku.put("amountOnSale", 100);
				Map<String,Object> specAttributes=new HashMap<String,Object>();
				sku.put("specAttributes", specAttributes);
				String fname=tempList.get(0).get("name").toString();
				if(fname.indexOf("颜色")>-1){
					specAttributes.put(tempList.get(0).get("fid").toString(), tbMap.get("value").toString());
					specAttributes.put(tempList.get(1).get("fid").toString(),"123");
				}else{
					normal=false;
					specAttributes.put(tempList.get(1).get("fid").toString(), tbMap.get("value").toString());
					specAttributes.put(tempList.get(0).get("fid").toString(),"123");
				}
				skuList.add(sku);
			}
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
				str+=tbMap.get("value").toString()+"|";
			}
			str=str.substring(0, str.length()-1);
			str2="123";
			if(normal){
				productFeatures.put(tempList.get(0).get("fid").toString(), str);
				productFeatures.put(tempList.get(1).get("fid").toString(), str2);
			}else{
				productFeatures.put(tempList.get(1).get("fid").toString(), str);
				productFeatures.put(tempList.get(0).get("fid").toString(), str2);
			}
			
		}
		
		
	}

}
