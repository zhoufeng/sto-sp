package com.bohusoft.aliapi.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bohusoft.aliapi.service.AliApiServiceImp;

public class Provider {
	protected static final Logger logger = Logger.getLogger("Provider");
	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml" });
		try{
			context.start();
			
			if(args.length>0){
				AliApiServiceImp aliApiServiceImp=(AliApiServiceImp) context.getBean("aliApiServiceImp");
				Map<String,Object> params=new HashMap<String, Object>(3);
				params.put("appKey", "1014423");
				//params.put("memberId","b2b-2484570957");
				params.put("gmtCreate","20150604240000000+0800");
				Map<String,Object> ret=aliApiServiceImp.reqAliApi("app.order.get", params, "1014423_b2b-2484570957");
				ObjectMapper objectMapper = new ObjectMapper();
				String retstr=objectMapper.writeValueAsString(ret);
				logger.debug(retstr);
				logger.error(retstr+":123");
			}			
			System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		}finally{
			context.close();
		}
	}
}
