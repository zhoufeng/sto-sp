package com.shenma.top.interceptors;

import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



import com.shenma.aliutil.sdk.ali.auth.HostedAuthService;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.service.AlibabaRequestService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.top.imagecopy.util.ProxyLocalRequestUtil;


@Controller
public class AliLoginInterceptor extends HandlerInterceptorAdapter {
	
	protected static Logger logger = Logger.getLogger("LoginInterceptor");
	
	@Autowired
	private HostedAuthService hostedAuthService;
	
	@Autowired
	private AlibabaRequestService alibabaRequestService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		logger.info("开始登陆拦截");
		Object object=request.getSession().getAttribute(AliConstant.ali_info_name);
		if(object==null){
			//如果是ajax请求响应头会有，x-requested-with；
			 if (request.getHeader("x-requested-with") != null&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				//response.setHeader("REQUIRES_AUTH", request.getRequestURL().toString()+"?"+request.getQueryString());//在响应头设置session状态  
				 String url="";
				 if(request.getParameter("otherId")!=null){//其他项目调用的时候回其他项目首页地址.
						String appKey=request.getParameter("otherId");
						String redirectUri=AliConstant.appMap.get(appKey).getRedirectUri();
						url=redirectUri.substring(0,redirectUri.indexOf(".com"+4));
				 }
				 response.setContentType("text/json; charset=UTF-8");  
				 response.setCharacterEncoding("utf-8");
				 PrintWriter out = response.getWriter();
				 out.write("{\"code\":666,\"loginUrl\":\""+url+"\"}");
				 out.flush();
				 out.close();
                 return false;  
			 }
			String customerUrl=request.getRequestURL().toString();
			if(request.getQueryString()!=null&&!"null".equals(request.getQueryString())){
				customerUrl+="?"+request.getQueryString();
			}
			//String customerUrl64=new String(Base64.encodeBase64(customerUrl.getBytes()));
			if(request.getParameter("otherId")!=null){//其他项目调用的时候回其他项目首页地址.
				String appKey=request.getParameter("otherId");
				 String url=HostedAuthService.getLoginUrl(appKey,"");
				 response.sendRedirect(url);
				 return false;
			}
			String customerUrl64="";
			String url=hostedAuthService.getLoginUrl(customerUrl64);
			//String url=ProxyLocalRequestUtil.getLoginUlr(hostedAuthService, customerUrl64);
			//setCookie(request, response);
			response.sendRedirect(url);
			return false;
		}else{
			AliToken info=(AliToken) object;
			//alibabaRequestService.refreshTokenWithEffectiveness(info);
			SessionUtil.setAliSession(info);
			return super.preHandle(request, response, handler);
		}
	}
	
	private void setCookie(HttpServletRequest request,HttpServletResponse response){
		String JSESSIONID=null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (int i = 0; i < cookies.length; i++) {
	            Cookie c = cookies[i];
	            if (c.getName().equals("JSESSIONID")) {
	            	JSESSIONID = c.getValue();
	            }
	        }
	    }
	    
	    /*
	     * 如果没有发现guid cookie ,生成新的guid,并写cookie
	    */
	    if (JSESSIONID == null) {
	    	JSESSIONID=UUID.randomUUID().toString();
	        Cookie guidcookie = new Cookie("JSESSIONID", JSESSIONID);
	        guidcookie.setMaxAge(-1);
	        guidcookie.setDomain(".kongjishise.com");
	        response.addCookie(guidcookie);
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
