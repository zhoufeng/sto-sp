package com.shenma.top.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.memcache.MemCachedUtil;

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
