package com.shenma.top.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/osslogin")
public class SSOloginController {

	
	@RequestMapping(value = "")
	@ResponseBody
	public String ossLogin(HttpServletRequest req) throws Exception {
		String sesstionId=req.getParameter("s");
		
		return "success";
	}
}
