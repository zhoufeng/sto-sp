package com.shenma.aliutil.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.member.MemberInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.BeanUtil;

@Service
public class MemberService {
	@Autowired
	private AlibabaRequestService requestService;
	
	public MemberInfo getMemberInfo(String memberId) throws AliReqException{
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("memberId", memberId);
		Map<String,Object> ret=requestService.reqAliApp("member.get", params);
		Page<MemberInfo> page=BeanUtil.transfAliRequest(ret, MemberInfo.class);
		return page.getContent().get(0);
	}
}
