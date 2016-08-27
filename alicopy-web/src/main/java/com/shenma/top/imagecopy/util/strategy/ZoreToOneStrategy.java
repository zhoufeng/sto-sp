package com.shenma.top.imagecopy.util.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

public class ZoreToOneStrategy implements TopToAliIStrategy{

	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer) {
		Map<String,Object> sku=new HashMap<String,Object>();
		sku.put("retailPrice", taobaoParmas.get("price").toString());
		sku.put("price", taobaoParmas.get("price").toString());
		sku.put("amountOnSale", 99999);
		Map<String,Object> specAttributes=new HashMap<String,Object>();
		sku.put("specAttributes", specAttributes);
		
		//int index=0;
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			if(specAttrObj.get("isNeeded").equals("Y")){
				specAttributes.put(specAttrObj.get("fid").toString(),"123");
				productFeatures.put(specAttrObj.get("fid").toString(), "123");
			}else{
				offer.setSkuTradeSupport(Boolean.FALSE);
				productFeatures.put("7588903", "个");
				offer.setPriceRanges("3:"+taobaoParmas.get("price").toString());
				offer.setAmountOnSale(99999);
			}
		}
		skuList.add(sku);
		
	}

}
