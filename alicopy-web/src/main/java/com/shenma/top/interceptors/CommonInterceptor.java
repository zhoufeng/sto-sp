package com.shenma.top.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shenma.top.imagecopy.util.Constant;


@Controller
public class CommonInterceptor extends HandlerInterceptorAdapter {
	protected static Logger logger = Logger.getLogger("CommonInterceptor");
	
	@Autowired
	private Constant constant;
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(request.getSession().getAttribute("URL_ROOT")==null){
			request.getSession().setAttribute("URL_ROOT", constant.URL_ROOT);
		}
		return true;

	}
}
