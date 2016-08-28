package com.shenma.aliutil.util;

import java.util.Map;

import org.apache.commons.beanutils.Converter;

public class MyTypeConverter implements Converter{

	@SuppressWarnings("unchecked")
	public <T> T convert(Class<T> type, Object value) {
		Map<String,Object> value2 = (Map<String,Object>)value;
		T ret=BeanUtil.map2Bean(value2, type);
		return ret;
	}

}
