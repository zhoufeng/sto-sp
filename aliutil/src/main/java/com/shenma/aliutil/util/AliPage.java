package com.shenma.aliutil.util;

import java.util.List;

import com.shenma.aliutil.entity.goods.OfferDetailInfo;

public class AliPage {
	private Integer total;
	private List<OfferDetailInfo> toReturn;
	private Boolean success;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<OfferDetailInfo> getToReturn() {
		return toReturn;
	}
	public void setToReturn(List<OfferDetailInfo> toReturn) {
		this.toReturn = toReturn;
	}

	
	
}
