package com.shenma.top.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shenma.taobao.util.TopConstant;



@Repository
public class TopLoginInterceptor extends HandlerInterceptorAdapter {
	
	protected static Logger logger = Logger.getLogger("LoginInterceptor");
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		logger.info("开始登陆拦截");
		Object object=request.getSession().getAttribute("");
		if(object==null){
			String customerUrl=request.getRequestURL().toString();
			if(request.getQueryString()!=null&&!"null".equals(request.getQueryString())){
				customerUrl+="?"+request.getQueryString();
			}
			String url="https://oauth.taobao.com/authorize?"+"client_id="+TopConstant.APP_KEY+"&response_type=code"+"&redirect_uri="+TopConstant.taobao_redirect_uri+"&view=web";
			String state=new String(Base64.encodeBase64(customerUrl.getBytes()));
			url+="&state="+state;
			response.sendRedirect(url);
			return false;
		}else{
			return super.preHandle(request, response, handler);
		}
	}
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}
	
	
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
