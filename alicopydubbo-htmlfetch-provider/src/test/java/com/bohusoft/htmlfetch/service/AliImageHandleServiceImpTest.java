package com.bohusoft.htmlfetch.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bohusoft.dubboapi.service.AliImageHandleService;
import com.bohusoft.htmlfetch.util.BaseNoRunTest;

public class AliImageHandleServiceImpTest extends BaseNoRunTest{
	@Reference
	private AliImageHandleService aliImageHandleService;
	@Before
	public void setUp() throws TimeoutException, InterruptedException, MemcachedException, IOException{
		super.setUp();
	}
	
	//@Test
	public void testSaveImage(){
		
		//Map<String,Object> obj = aliImageHandleService.saveImage("https://cbu01.alicdn.com/cms/upload/2015/733/094/2490337_1428245009.jpg", "164181895", "哈哈", "","");
		//System.out.println(obj);
	}
	
	
	public void testSaveImages(){
		String[] urls=new String[]{"https://cbu01.alicdn.com/cms/upload/2015/551/394/2493155_759381252.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/733/094/2490337_1428245009.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/173/684/2486371_1648542067.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/766/074/2470667_1799871007.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/564/784/2487465_2015354421.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/414/574/2475414_16443940.jpg",
				"http://cbu01.alicdn.com/cms/upload/2014/053/290/2092350_1805353437.jpg"
		};
		List<String> urlList=Arrays.asList(urls);
		//aliImageHandleService.saveImages(urlList, "164181895","宝贝一键复制测试标题名称","");
	}
}
