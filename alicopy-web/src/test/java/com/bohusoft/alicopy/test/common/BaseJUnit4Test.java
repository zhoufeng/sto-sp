package com.bohusoft.alicopy.test.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/config/spring/spring.xml"})
public class BaseJUnit4Test {
	 
	@Autowired
	protected MemCachedUtil cachedUtil;
	
	@Autowired
	protected AliConstant aliConstant;
	
	@Before
	public void setContation(){
		AliToken aliToken=cachedUtil.getByAppkeyAndMemberId(aliConstant.APP_KEY, "b2b-1623492085");
		SessionUtil.setAliSession(aliToken);
	}
}
