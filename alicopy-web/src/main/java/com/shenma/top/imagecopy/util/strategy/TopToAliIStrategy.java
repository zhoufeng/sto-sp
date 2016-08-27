package com.shenma.top.imagecopy.util.strategy;

import java.util.List;
import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

/**
 * 
 * @author zhouf 淘宝复制阿里的策略接口
 */
public interface TopToAliIStrategy {
	// 每个锦囊妙计都是一个可执行的算法。
	public void operate(Map<String, String> productFeatures,
			Map<String, Map<String, Object>> specAttrMap,
			List<Map<String, Object>> skuList,
			Map<String, Object> taobaoParmas,
			List<Map<String, Object>> taobaoskuList,Offer offer);

}
