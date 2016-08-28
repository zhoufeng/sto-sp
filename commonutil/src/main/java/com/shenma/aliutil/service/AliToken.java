package com.shenma.aliutil.service;

import java.io.Serializable;

public class AliToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8588948098219592374L;
	private String accessToken;
	private String refreshToken;
	private String memberId;
	private String sellerName;
	private long expiresTime;
	private String appKey;
	private String appSecret;
	private String resourceOwner;
	private Integer userLevel; //1.包月    2.包季    3.半年   4包年
	private Integer leftDay; //剩余天数
	private boolean isOver;  // 过期
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public boolean isOver() {
		return isOver;
	}
	
	
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	public String getResourceOwner() {
		return resourceOwner;
	}
	public void setResourceOwner(String resourceOwner) {
		this.resourceOwner = resourceOwner;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public Integer getLeftDay() {
		return leftDay;
	}
	public void setLeftDay(Integer leftDay) {
		this.leftDay = leftDay;
	}
	public long getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}

	
	
}
