package com.bohusoft.alicopy.util;

import java.util.Map;

import com.shenma.aliutil.entity.goods.Offer;

public class SaveTempBean {
	private Offer offer;
	private Map<String,Object> selfInfo;
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public Map<String, Object> getSelfInfo() {
		return selfInfo;
	}
	public void setSelfInfo(Map<String, Object> selfInfo) {
		this.selfInfo = selfInfo;
	}
	
	
}
