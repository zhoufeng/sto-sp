package com.shenma.aliutil.entity.goods;

public class OfferImageInfo {
	private Integer offerId;
	private String size310x310URL;
	private String summURL;
	private String size64x64URL;
	private String imageURI;
	private String originalImageURI;
	/**
	 * 产品ID
	 * 隐私:否
	 * @return
	 */
	public Integer getOfferId() {
		return offerId;
	}
	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}
	public String getSize310x310URL() {
		return size310x310URL;
	}
	public void setSize310x310URL(String size310x310url) {
		size310x310URL = size310x310url;
	}
	public String getSummURL() {
		return summURL;
	}
	public void setSummURL(String summURL) {
		this.summURL = summURL;
	}
	public String getSize64x64URL() {
		return size64x64URL;
	}
	public void setSize64x64URL(String size64x64url) {
		size64x64URL = size64x64url;
	}
	public String getImageURI() {
		return imageURI;
	}
	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}
	public String getOriginalImageURI() {
		return originalImageURI;
	}
	public void setOriginalImageURI(String originalImageURI) {
		this.originalImageURI = originalImageURI;
	}
	
}
