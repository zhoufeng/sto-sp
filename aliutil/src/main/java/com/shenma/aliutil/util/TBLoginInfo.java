package com.shenma.aliutil.util;

import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.shenma.aliutil.entity.member.MemberInfo;

public class TBLoginInfo {
	private MemberInfo memberInfo; //用户信息
	private AuthorizationToken authorizationToken; //权限token
	private String code; //返回code
	public MemberInfo getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}
	public AuthorizationToken getAuthorizationToken() {
		return authorizationToken;
	}
	public void setAuthorizationToken(AuthorizationToken authorizationToken) {
		this.authorizationToken = authorizationToken;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
