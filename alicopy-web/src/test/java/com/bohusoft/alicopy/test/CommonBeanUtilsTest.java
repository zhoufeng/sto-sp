package com.bohusoft.alicopy.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bohusoft.alicopy.parse.DetailHtmlParse;
import com.bohusoft.alicopy.test.common.BaseJUnit4Test;
import com.shenma.top.imagecopy.util.JsonpUtil;


public class CommonBeanUtilsTest extends BaseJUnit4Test{
	
	@Autowired
	private DetailHtmlParse alibabaDetailHtmlParse;
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Test
	public void testPro() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> submap=new HashMap<String,Object>();
		submap.put("url", "cctv");
		map.put("images", submap);
		String a=BeanUtils.getProperty(map, "images.url");
		Map<String,Object> b=(Map<String, Object>) PropertyUtils.getProperty(map, "images");
		System.out.println(b);
	}
	
	@Test
	public void testDe(){
		String url="https://item.taobao.com/item.htm?id=44050405059";
		String html=JsonpUtil.getAliDefaultConnet(url).html();
		System.out.println(html);
	}
}
