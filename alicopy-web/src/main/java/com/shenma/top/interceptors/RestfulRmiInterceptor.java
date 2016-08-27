package com.shenma.top.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RestfulRmiInterceptor  extends HandlerInterceptorAdapter{
	protected static Logger logger = Logger.getLogger("RestfulRmiInterceptor");

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		/*if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest")) {
			// response.setHeader("REQUIRES_AUTH",
			// request.getRequestURL().toString()+"?"+request.getQueryString());//在响应头设置session状态
			response.setContentType("text/json; charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("{code:666}");
			out.flush();
			out.close();
			return false;
		}*/
		return true;

	}
}
