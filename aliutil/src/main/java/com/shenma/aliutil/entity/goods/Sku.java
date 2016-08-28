package com.shenma.aliutil.entity.goods;

import java.util.List;

public class Sku {
	private String value;
	private Long fid;
	private List<SkuChildren> children;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
	public List<SkuChildren> getChildren() {
		return children;
	}
	public void setChildren(List<SkuChildren> children) {
		this.children = children;
	}
	
	
}
