package com.shenma.top.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenma.top.service.top.TopAuthService;

@Controller
@RequestMapping("/login/top")
public class TopLoginController {
	protected static Logger logger = Logger.getLogger("TopLoginController");
	
	@Autowired
	private TopAuthService authService;
	
	/**
	 * 从淘宝登陆页面返回的url页面处理
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "")
	public String authReturn(HttpServletRequest req) throws Exception {
		logger.debug("Received request to show admin page");
		String code=(String) req.getParameter("code");
		String customerUrl64=(String) req.getParameter("state");
		String customerUrl=new String(Base64.decodeBase64(customerUrl64.getBytes()));
		if(StringUtils.isEmpty(customerUrl)){
			customerUrl="/console";
		}
		authService.saveInfoToSession(code,req);
		return "redirect:"+customerUrl;
	}
	@RequestMapping(value = "/auto")
	public String success(){
		
		return	"login/success";
	}
}
