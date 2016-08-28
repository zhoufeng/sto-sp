package com.shenma.aliutil.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MapTypeConverter implements IMyConverter {

	
	public <T> T convert(Class<T> type, Object value) {
		return null;
	}
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public  List  convert(Class<?> type, Object value,Type[] params){
		Type t1=params[0];
		Type t2=params[1];
		Class<?> cls1=(Class<?>) params[0];
		Class<?> cls2=(Class<?>) params[0];
		List ret=new ArrayList();
		List<Map<String,Object>> list = (List<Map<String,Object>>)value;
		for(Map<String,Object> m:list){			
			ret.add(BeanUtil.map2Bean(m,cls1));
		}
		
		return ret;
	}*/

	public <T> T convert(Class<T> type, Object value, Type[] tArgs) {
		Type t1=tArgs[0];
		Type t2=tArgs[1];
		Class<?> cls1=(Class<?>) tArgs[0];
		Class<?> cls2=(Class<?>) tArgs[0];
		List ret=new ArrayList();
		return null;
	}
}
