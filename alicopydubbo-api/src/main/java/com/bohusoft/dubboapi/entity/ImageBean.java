package com.bohusoft.dubboapi.entity;

public class ImageBean {
	private String url;
	private byte[] data;
	
	public ImageBean(String url,byte[] data){
		this.url=url;
		this.data=data;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
}
