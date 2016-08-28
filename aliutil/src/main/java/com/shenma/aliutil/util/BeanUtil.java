package com.shenma.aliutil.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.shenma.aliutil.exception.AliReqException;

public class BeanUtil {
	protected static Logger logger = Logger.getLogger("BeanUtil");
	 /** 
     *  
     *  
     * Map转换层Bean，使用泛型免去了类型转换的麻烦。 
     * @param <T> 
     * @param map   
     * @param class1 
     * @return 
	 * @throws AliReqException 
     */  
    public static <T> T map2Bean(Map<String, Object> map, Class<T> class1)  { 
        T bean = null;  
        try {  
            bean = class1.newInstance();  
            BeanUtilsBean.getInstance().populate(bean, map);
        } catch (InstantiationException e) {  
        	logger.error(e);
        } catch (IllegalAccessException e) {  
        	logger.error(e);
        } catch (InvocationTargetException e) {  
        	logger.error(e);
        }  
        return bean;  
    }  
    

    
    
    public static <T> Page<T> transfAliRequest(Map<String, Object> ret, Class<T> class1)  { 
    	Map<String,Object> result=(Map<String,Object>)ret.get("result");
    	List<Map<String,Object>> toReturnList=(List<Map<String,Object>>)result.get("toReturn");
    	List<T> tList=new ArrayList<T>();
    	for(Map<String,Object> map:toReturnList){
    		T t=map2Bean(map, class1);
    		tList.add(t);
    	}
    	Page<T> r=new PageImpl<T>(tList);
    	return r;
    }
    
    @SuppressWarnings("unchecked")
	public static <T> List<T> transf(Map<String,Object> ret,Class<T> T){
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		List<Map<String,Object>> toReturn=(List<Map<String, Object>>) result.get("toReturn");
		List<T> r=new ArrayList<T>();
		for(Map<String,Object> map:toReturn){
			T o=BeanUtil.map2Bean(map, T);
			r.add(o);
		}
		return r;
	}
}
