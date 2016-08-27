package com.shenma.tiger.controller.autoplan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tiger/autoplan")
public class AutoPlanController {
	/**
	 * 自动重发设置页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView indexGet(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "autoplan/index.jsp");
		return new ModelAndView("tiger/index",model);
	}
	
	/**
	 * 自动重发列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "autoplan/list.jsp");
		return new ModelAndView("tiger/index",model);
	}
	/**
	 * 自动重发列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/history",method=RequestMethod.GET)
	public ModelAndView history(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "autoplan/history.jsp");
		return new ModelAndView("tiger/index",model);
	}
}
