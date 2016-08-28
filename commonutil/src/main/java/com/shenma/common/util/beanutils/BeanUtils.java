package com.shenma.common.util.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;


public class BeanUtils {
	protected static Logger logger = Logger.getLogger("BeanUtil");
	 /** 
    *  
    *  
    * Map转换层Bean，使用泛型免去了类型转换的麻烦。 
    * @param <T> 
    * @param map   
    * @param class1 
    * @return
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
}
