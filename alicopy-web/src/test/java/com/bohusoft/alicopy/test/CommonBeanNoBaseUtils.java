package com.bohusoft.alicopy.test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

public class CommonBeanNoBaseUtils {
	@SuppressWarnings({ "unused", "unchecked" })
	@Test
	public void testPro() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> submap=new HashMap<String,Object>();
		submap.put("url", "cctv");
		map.put("images", submap);
		String a=BeanUtils.getProperty(map, "images.urls");
		Map<String,Object> b=(Map<String, Object>) PropertyUtils.getProperty(map, "images");
		System.out.println(b);
	}
}
