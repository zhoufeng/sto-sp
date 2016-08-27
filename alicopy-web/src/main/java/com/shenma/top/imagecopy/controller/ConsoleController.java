package com.shenma.top.imagecopy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.taobao.api.ApiException;

@Controller
@RequestMapping("/console")
public class ConsoleController {
	
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	@Autowired
	private AliConstant aliConstant;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView indexGet(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "top/index.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	/**
	 * 功能描述:登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "top/index.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	
	/**
	 * 功能描述:登出
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loginout",method=RequestMethod.GET)
	public String loginout(HttpServletRequest request){
		request.getSession().removeAttribute(AliConstant.ali_info_name);
		//AliToken aliToken=(AliToken) request.getSession().getAttribute(AliConstant.ali_info_name);
		//memCachedUtil.delete(aliConstant.APP_KEY+"_"+aliToken.getMemberId());
		return "redirect:/console";
	}
	
	@RequestMapping(value="test",method=RequestMethod.GET)
	public ModelAndView test(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "copy/productIndex.jsp");
		return new ModelAndView("aceadmin/test",model);
	}
}
