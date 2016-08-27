package com.shenma.top.imagecopy.util.save;

import java.util.Map;

import org.jsoup.nodes.Document;

import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.LogisticsAddressSearchResponse;

public class SaveBean {
	private ItemAddRequest topitem;
	private ItempropsGetResponse res;
	private LogisticsAddressSearchResponse  AddressRes;
	private Document document;
	private Map<String,String> yanseMap;
	public SaveBean(ItemAddRequest topitem,ItempropsGetResponse res,LogisticsAddressSearchResponse  AddressRes,Document document){
		this.topitem=topitem;
		this.res=res;
		this.AddressRes=AddressRes;
		this.document=document;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public ItemAddRequest getTopitem() {
		return topitem;
	}
	public void setTopitem(ItemAddRequest topitem) {
		this.topitem = topitem;
	}
	public ItempropsGetResponse getRes() {
		return res;
	}
	public void setRes(ItempropsGetResponse res) {
		this.res = res;
	}
	public LogisticsAddressSearchResponse getAddressRes() {
		return AddressRes;
	}
	public void setAddressRes(LogisticsAddressSearchResponse addressRes) {
		AddressRes = addressRes;
	}
	public Map<String, String> getYanseMap() {
		return yanseMap;
	}
	public void setYanseMap(Map<String, String> yanseMap) {
		this.yanseMap = yanseMap;
	}
	
}
