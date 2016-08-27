package com.shenma.top.imagecopy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView indexGet(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "top/index.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView homeGet(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "top/index.jsp");
		return new ModelAndView("aceadmin/home",model);
	}
}
