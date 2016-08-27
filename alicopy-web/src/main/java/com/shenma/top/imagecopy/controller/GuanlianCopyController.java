package com.shenma.top.imagecopy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.ApiException;

@Controller
@RequestMapping("/top/guanliancopy")
public class GuanlianCopyController {

	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "copy/guanlianIndex.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
}
