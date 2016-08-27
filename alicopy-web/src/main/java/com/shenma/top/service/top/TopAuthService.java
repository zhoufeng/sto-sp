package com.shenma.top.service.top;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.shenma.common.util.SessionThread;
import com.shenma.taobao.util.TopAccessToken;
import com.shenma.taobao.util.TopConstant;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.taobao.api.internal.util.WebUtils;

@Service
public class TopAuthService {
	protected static Logger logger = Logger.getLogger("AuthService");
	public TopAccessToken saveInfoToSession(String code,HttpServletRequest request){
		String url="https://oauth.taobao.com/token";
	      Map<String,String> props=new HashMap<String,String>();
	      props.put("grant_type","authorization_code");
	      /*测试时，需把test参数换成自己应用对应的值*/
	      props.put("code",code);
	      props.put("client_id",TopConstant.APP_KEY);
	      props.put("client_secret",TopConstant.APP_Secret);
	      props.put("redirect_uri",TopConstant.taobao_redirect_uri);
	      props.put("view","web");
	      TopAccessToken token=null;
	      try{
	    	  String  s=WebUtils.doPost(url, props, 30000, 30000);
	    	  token=JacksonJsonMapper.getInstance().readValue(s,TopAccessToken.class);
	      }catch(IOException e){
	    	  logger.error(e);
	    }
	      request.getSession().setAttribute("topInfo", token);
	      return token;
	}
}
