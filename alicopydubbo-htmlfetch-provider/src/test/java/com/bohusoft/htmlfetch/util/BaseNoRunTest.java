package com.bohusoft.htmlfetch.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;


//@RunWith(value = SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value="classpath:applicationContext-test.xml")
public class BaseNoRunTest{
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
 
	@Before
    public void setUp() throws TimeoutException, InterruptedException, MemcachedException, IOException {

		 AliToken info=memCachedUtil.get("1014423_b2b-2484570957");
		 SessionUtil.setAliSession(info);
	}
	
}
