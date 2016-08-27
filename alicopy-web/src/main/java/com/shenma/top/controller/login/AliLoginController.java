package com.shenma.top.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.service.AlibabaRequestService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.top.imagecopy.dao.AuthorizationEntityDao;


@Controller
@RequestMapping("/login/ali")
public class AliLoginController {
	protected static Logger logger = Logger.getLogger("AliLoginController");
	@Autowired
	private AlibabaRequestService alibabaRequestService;
	
	
	@Autowired
	private AuthorizationEntityDao authorizationEntityDao;
	/**
	 * 从阿里巴巴登陆页面返回的url页面处理
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "")
	public String authReturn(HttpServletRequest req) throws Exception {
		String code=(String) req.getParameter("code");
		String customerUrl="/home";
		if(code!=null){
			try {
				AliToken aliToken=alibabaRequestService.saveInfoToSession(code);
				req.getSession().setAttribute(AliConstant.ali_info_name, aliToken);
			} catch (Exception e) {
				logger.error(e);
				return "/exception/aliloginerror";
			}
		}else{
			return "redirect:"+customerUrl;
		}
		return "redirect:"+customerUrl; 
		
	}
	
	@RequestMapping(value = "/auto")
	public String success(){
		
		return	"login/success";
	}
}
