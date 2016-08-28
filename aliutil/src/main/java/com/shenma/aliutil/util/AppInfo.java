package com.shenma.aliutil.util;

public class AppInfo {
	private String appKey;
	private String appSecret;
	private String appName;
	private String redirectUri;
	
	public AppInfo(String appKey,String appSecret,String appName,String redirectUri){
		this.appKey=appKey;
		this.appSecret=appSecret;
		this.appName=appName;
		this.redirectUri=redirectUri;
	}
	public AppInfo(){
		
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

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}


	
	
}
