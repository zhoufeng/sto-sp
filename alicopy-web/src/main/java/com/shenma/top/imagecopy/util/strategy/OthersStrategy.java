package com.shenma.top.imagecopy.util.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

public class OthersStrategy implements TopToAliIStrategy {

	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer) {
		/*if(specAttrMap.size()==0){
			oneMethod(productFeatures, specAttrMap, skuList, taobaoParmas, taobaoskuList);
		}*/
		if(taobaoskuList.size()==3&&specAttrMap.size()==2){
			threeMothod(productFeatures, specAttrMap, skuList, taobaoParmas, taobaoskuList,offer);
			return;
		}
		//0 : 2
		Map<String,Object> sku=new HashMap<String,Object>();
		sku.put("retailPrice", taobaoParmas.get("price").toString());
		sku.put("price", taobaoParmas.get("price").toString());
		sku.put("amountOnSale", 99999);
		Map<String,Object> specAttributes=new HashMap<String,Object>();
		sku.put("specAttributes", specAttributes);
		
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			if(specAttrObj.get("isNeeded").equals("Y")){
				specAttributes.put(specAttrObj.get("fid").toString(),"123");
				productFeatures.put(specAttrObj.get("fid").toString(), "123");
			}
		}
		if(specAttrMap.size()==0){
			offer.setSkuTradeSupport(Boolean.FALSE);
			productFeatures.put("7588903", "个");
			offer.setPriceRanges("3:"+taobaoParmas.get("price").toString());
			offer.setAmountOnSale(9999);
		}
	}

	private void threeMothod(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer){
		for(int i=0;i<taobaoskuList.size();i++){
			if(!taobaoskuList.get(i).containsKey("isSpecPicAttr")){
				taobaoskuList.remove(i);
				break;
			}
		}
		TopToAliContext context=new TopToAliContext(new TwoTwoStrategy());
		context.operate( specAttrMap, taobaoParmas, taobaoskuList,offer);
	}
}
