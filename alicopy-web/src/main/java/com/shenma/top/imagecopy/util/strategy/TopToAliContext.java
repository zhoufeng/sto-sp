package com.shenma.top.imagecopy.util.strategy;

import java.util.List;
import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

public class TopToAliContext {
	 private TopToAliIStrategy strategy;
	    //构造函数，要你使用哪个妙计  
	    public TopToAliContext(TopToAliIStrategy strategy){  
	        this.strategy = strategy;
	    }  
	      
	    public void operate(Map<String, Map<String, Object>> specAttrMap,
				Map<String, Object> taobaoParmas,
				List<Map<String, Object>> taobaoskuList,Offer offer){  
	    	
	        this.strategy.operate(offer.getProductFeatures(),specAttrMap,offer.getSkuList(),taobaoParmas,taobaoskuList,offer);  
	    }  
}
