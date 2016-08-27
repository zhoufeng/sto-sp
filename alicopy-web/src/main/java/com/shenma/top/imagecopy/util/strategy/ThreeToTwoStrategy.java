package com.shenma.top.imagecopy.util.strategy;

import java.util.List;
import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

public class ThreeToTwoStrategy implements TopToAliIStrategy {

	@Override
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList, Offer offer) {
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
