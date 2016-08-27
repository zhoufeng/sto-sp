package com.shenma.top.imagecopy.util.bean;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.taobao.api.domain.Item;

public class SearchVoBean extends CopyResponse{
	private String shopName;
	private Integer shopType; //阿里巴巴 ：1    淘宝：2   天猫：3
	private String parentCate;
	private String url;
	private Integer pageNo;
	private Integer pageSize;
	private List<Item> items;
	private Integer totals;
	private Integer totalPages;
	private List<Map<String,Object>> cateItems;
	private String searchUrl;
	private String hostUrl;
	private String cateUrl;
	

	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getShopType() {
		return shopType;
	}
	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}
	public Integer getTotals() {
		return totals;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public List<Map<String, Object>> getCateItems() {
		return cateItems;
	}
	public void setCateItems(List<Map<String, Object>> cateItems) {
		this.cateItems = cateItems;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentCate() {
		return parentCate;
	}
	public void setParentCate(String parentCate) {
		this.parentCate = parentCate;
	}
	public String getHostUrl() {
		return hostUrl;
	}
	public String getCateUrl() {
		return cateUrl;
	}
	public void setCateUrl(String cateUrl) {
		this.cateUrl = cateUrl;
	}

	
}
