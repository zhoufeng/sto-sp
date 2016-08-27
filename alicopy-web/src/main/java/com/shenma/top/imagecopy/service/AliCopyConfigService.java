package com.shenma.top.imagecopy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.util.SessionUtil;
import com.shenma.top.imagecopy.dao.AliCopyConfigDao;
import com.shenma.top.imagecopy.entity.AliCopyConfig;

@Service
public class AliCopyConfigService {
	
	@Autowired
	private AliCopyConfigDao aliCopyConfigDao;
	
	public AliCopyConfig saveOrUpdate(String userConfig){
		String memberId=SessionUtil.getAliSession().getMemberId();
		AliCopyConfig entity=aliCopyConfigDao.findByMemberId(memberId);
		if(entity==null){
			entity=new AliCopyConfig();
			String resourceOwner=SessionUtil.getAliSession().getResourceOwner();
			entity.setUserConfig(userConfig);
			entity.setMemberId(memberId);
			entity.setAcountName(resourceOwner);
		}else{
			entity.setUserConfig(userConfig);
		}
		entity=aliCopyConfigDao.saveAndFlush(entity);
		return entity;
	}
	
	public AliCopyConfig findOne(){
		String memberId=SessionUtil.getAliSession().getMemberId();
		AliCopyConfig entity=aliCopyConfigDao.findByMemberId(memberId);
		return entity;
	}
}
