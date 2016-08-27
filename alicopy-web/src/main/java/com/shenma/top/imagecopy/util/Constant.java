package com.shenma.top.imagecopy.util;
import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class Constant implements InitializingBean{
	protected static Logger logger = Logger.getLogger("Constant");
	@Value("#{alicopyConfig['URL_ROOT']}")
	public String URL_ROOT="http://www.kongjishise.com/taobaoweb";
	
	@Value("#{alicopyConfig['search_cate_delay_time']}")
	public static Integer search_cate_delay_time=3000;
	
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void init(){
		System.out.println(1);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	/*@SuppressWarnings("rawtypes")
	private void setCustomerProperties() throws IllegalArgumentException, IllegalAccessException{
		Field [] fields = Constant.class.getDeclaredFields();
		for(Field field:fields){
			Class cls=field.getType();
			field.setAccessible(true);
			if("java.lang.String".equals(cls.getName())){
				String value=(String)CustomerPropertyPlaceholderConfigurer.getContextProperty(field.getName());
				if(value!=null)field.set(cls, value);
			}else if("java.lang.Integer".equals(cls.getName())){
				Integer value=(Integer)CustomerPropertyPlaceholderConfigurer.getContextProperty(field.getName());
				if(value!=null)field.set(cls, value);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void setConstant(Class tranfClass){
		Field [] fields = tranfClass.getDeclaredFields();
		try {
			for(Field field:fields){
				Class cls=field.getType();
				field.setAccessible(true);
				if("java.lang.String".equals(cls.getName())){
					String value=(String)CustomerPropertyPlaceholderConfigurer.getContextProperty(field.getName());
					if(value!=null)field.set(cls, value);
				}else if("java.lang.Integer".equals(cls.getName())){
					Integer value=(Integer)CustomerPropertyPlaceholderConfigurer.getContextProperty(field.getName());
					if(value!=null)field.set(cls, value);
				}
			}
		} catch (Exception e) {
			logger.error("设置properties属性值错误", e);
		}
	}*/
}
