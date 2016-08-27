package com.shenma.top.imagecopy.util.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

public class TwoToOneStrategy implements TopToAliIStrategy {

	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer) {
		Map<String,Object> alispecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj2=null;
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			alispecAttrMapisSpecPicAttrObj=specAttrObj;
		}
		for(Map<String,Object> taobaospecAttrName:taobaoskuList){
			
			if(taobaospecAttrName.containsKey("isSpecPicAttr")){
				taobaospecAttrMapisSpecPicAttrObj=taobaospecAttrName;
			}else{
				taobaospecAttrMapisSpecPicAttrObj2=taobaospecAttrName;
			}
		}
		boolean isSpecPicAttr=(boolean) alispecAttrMapisSpecPicAttrObj.get("isSpecPicAttr");
		if(isSpecPicAttr&&taobaospecAttrMapisSpecPicAttrObj!=null){
			String str="";
			for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaospecAttrMapisSpecPicAttrObj.get("childs")){
					Map<String,Object> sku=new HashMap<String,Object>();
					sku.put("retailPrice", taobaoParmas.get("price").toString());
					sku.put("price", taobaoParmas.get("price").toString());
					sku.put("amountOnSale", 100);
					Map<String,Object> specAttributes=new HashMap<String,Object>();
					sku.put("specAttributes", specAttributes);
					specAttributes.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), tbMap.get("value").toString());
					str+=tbMap.get("value").toString()+"|";
					skuList.add(sku);
			}
			str=str.substring(0, str.length()-1);
			productFeatures.put(taobaospecAttrMapisSpecPicAttrObj.get("fid").toString(), str);
		}else{
			notisSpecPicAttr(productFeatures, specAttrMap, skuList, taobaoParmas, taobaoskuList,alispecAttrMapisSpecPicAttrObj);
		}
		
	}
	private void notisSpecPicAttr(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Map<String,Object> alispecAttrMapisSpecPicAttrObj){
		String str="";
		for(Map<String,Object> tbMap:(List<Map<String,Object>>)taobaoskuList.get(0).get("childs")){
			Map<String,Object> sku=new HashMap<String,Object>();
			sku.put("retailPrice", taobaoParmas.get("price").toString());
			sku.put("price", taobaoParmas.get("price").toString());
			sku.put("amountOnSale", 100);
			Map<String,Object> specAttributes=new HashMap<String,Object>();
			sku.put("specAttributes", specAttributes);
			str+=tbMap.get("value").toString()+"|";
			specAttributes.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), tbMap.get("value").toString());

			skuList.add(sku);
		}
		str=str.substring(0, str.length()-1);
		productFeatures.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), str);
	}
}
