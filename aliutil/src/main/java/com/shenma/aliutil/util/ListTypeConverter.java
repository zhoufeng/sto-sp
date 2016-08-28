package com.shenma.aliutil.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.Converter;

import com.shenma.aliutil.entity.goods.OfferImageInfo;

public class ListTypeConverter  implements Converter{

	
	public <T> T convert(Class<T> type, Object value) {
		T ret=null;
		//type.newInstance().getDeclaredField("").getGenericType();
		Map<String,Object> value2 = (Map<String,Object>)value;
		if(type.getName().equals(OfferImageInfo.class.getName())){
			ret=BeanUtil.map2Bean(value2, type);
			
		}
		if(type.getName().equals(AliPage.class.getName())){
			ret=BeanUtil.map2Bean(value2, type);
		}
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  List  convert(Class<?> type, Object value,Type[] params){
		Class t=(Class)params[0];
		List ret=new ArrayList();
		if(params[0].toString().contains("java.lang.Integer")){
			List<Integer> list = (List<Integer>)value;
			for(Integer m:list){
				ret.add(m);
			}
		}else if(params[0].toString().contains("java.lang.String")){
			List<String> list = (List<String>)value;
			for(String m:list){
				ret.add(m);
			}
		}else{
			List<Map<String,Object>> list = (List<Map<String,Object>>)value;
			for(Map<String,Object> m:list){			
				ret.add(BeanUtil.map2Bean(m,t));
			}
		}
		return ret;
	}

}
