package com.shenma.aliutil.entity.cate;

import java.util.List;

public class CatInfo {
	private Integer catsId;
	private String catsName;
	private String catsDescription;
	private boolean leaf;
	private Integer tradeType;
	private List<ParentCatInfo> parentCats;
	private boolean applySPU;
	private boolean supportOnlineTrade;
	private boolean supportSKUPrice;
	private boolean supportMixWholesale;
	private boolean batchPost;
	private boolean applyRealPrice;
	
	public Integer getCatsId() {
		return catsId;
	}
	public void setCatsId(Integer catsId) {
		this.catsId = catsId;
	}
	public String getCatsName() {
		return catsName;
	}
	public void setCatsName(String catsName) {
		this.catsName = catsName;
	}
	public String getCatsDescription() {
		return catsDescription;
	}
	public void setCatsDescription(String catsDescription) {
		this.catsDescription = catsDescription;
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	public List<ParentCatInfo> getParentCats() {
		return parentCats;
	}
	public void setParentCats(List<ParentCatInfo> parentCats) {
		this.parentCats = parentCats;
	}
	public boolean isApplySPU() {
		return applySPU;
	}
	public void setApplySPU(boolean applySPU) {
		this.applySPU = applySPU;
	}

	public boolean isSupportMixWholesale() {
		return supportMixWholesale;
	}
	public void setSupportMixWholesale(boolean supportMixWholesale) {
		this.supportMixWholesale = supportMixWholesale;
	}
	public boolean isSupportOnlineTrade() {
		return supportOnlineTrade;
	}
	public void setSupportOnlineTrade(boolean supportOnlineTrade) {
		this.supportOnlineTrade = supportOnlineTrade;
	}
	public boolean isSupportSKUPrice() {
		return supportSKUPrice;
	}
	public void setSupportSKUPrice(boolean supportSKUPrice) {
		this.supportSKUPrice = supportSKUPrice;
	}
	public boolean isBatchPost() {
		return batchPost;
	}
	public void setBatchPost(boolean batchPost) {
		this.batchPost = batchPost;
	}
	public boolean isApplyRealPrice() {
		return applyRealPrice;
	}
	public void setApplyRealPrice(boolean applyRealPrice) {
		this.applyRealPrice = applyRealPrice;
	}
	
	
	
}
