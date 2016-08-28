/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.alibaba.openapi.client.auth;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationToken implements Serializable {
	private static final long serialVersionUID = -7582222338466164367L;
	private String access_token;
	private String refresh_token;
	private long expires_in;
	private Date expires_time;
	private Date refresh_token_timeout;
	private String resource_owner;
	private String uid;
	private long aliId;
	private String memberId;

	public AuthorizationToken() {
	}

	public AuthorizationToken(String accessToken, long accessTokenTimeout,
			String resourceOwnerId, long aliId,String memberId) {
		this(accessToken, accessTokenTimeout, null, null, resourceOwnerId,
				null, aliId,memberId);
	}

	public AuthorizationToken(String accessToken, long accessTokenTimeout,
			String resourceOwnerId, String uid, long aliId,String memberId) {
		this(accessToken, accessTokenTimeout, null, null, resourceOwnerId, uid,
				aliId,memberId);
	}

	public AuthorizationToken(String accessToken, long accessTokenTimeout,
			String refreshToken, Date refreshTokenTimeout,
			String resourceOwnerId, long aliId,String memberId) {
		this(accessToken, accessTokenTimeout, refreshToken,
				refreshTokenTimeout, resourceOwnerId, null, aliId,memberId);
	}

	public AuthorizationToken(String accessToken, long accessTokenTimeout,
			String refreshToken, Date refreshTokenTimeout,
			String resourceOwnerId, String uid, long aliId,String memberId) {
		this.access_token = accessToken;
		this.expires_in = accessTokenTimeout;
		this.refresh_token = refreshToken;
		this.refresh_token_timeout = refreshTokenTimeout;
		this.resource_owner = resourceOwnerId;
		this.uid = uid;
		this.aliId = aliId;
		this.memberId=memberId;
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public long getExpires_in() {
		return this.expires_in;
	}

	public String getRefresh_token() {
		return this.refresh_token;
	}



	public String getResource_owner() {
		return this.resource_owner;
	}

	public String getUid() {
		return this.uid;
	}

	public long getAliId() {
		return this.aliId;
	}

	public void setAccess_token(String accessToken) {
		this.access_token = accessToken;
	}

	public void setRefresh_token(String refreshToken) {
		this.refresh_token = refreshToken;
	}

	public void setExpires_in(long accessTokenTimeout) {
		this.expires_in = accessTokenTimeout;
		Calendar cal = Calendar.getInstance();
		cal.add(13, (int) this.expires_in);
		this.expires_time = cal.getTime();
	}



	public void setResource_owner(String resourceOwnerId) {
		this.resource_owner = resourceOwnerId;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setAliId(long aliId) {
		this.aliId = aliId;
	}

	public Date getExpires_time() {
		return this.expires_time;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getRefresh_token_timeout() {
		return refresh_token_timeout;
	}

	public void setRefresh_token_timeout(Date refresh_token_timeout) {
		this.refresh_token_timeout = refresh_token_timeout;
	}

	public void setExpires_time(Date expires_time) {
		this.expires_time = expires_time;
	}
	
	
	
}