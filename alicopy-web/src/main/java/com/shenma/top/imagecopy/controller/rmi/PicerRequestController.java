package com.shenma.top.imagecopy.controller.rmi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.service.AlibabaRequestService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.taobao.api.ApiException;

@Controller
@RequestMapping("/picerrequest")
public class PicerRequestController {
	@Autowired
	private MemCachedUtil memCachedUtil;
	
	@Autowired
	private AlibabaRequestService alibabaRequestService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(@RequestParam("accessToken") String accessToken,@RequestParam("memberId") String memberId,@RequestParam("appkey") String appkey,HttpServletRequest request) throws TimeoutException, InterruptedException, MemcachedException, IOException{
		AliToken aliToken=memCachedUtil.get(appkey+"_"+memberId);
		//alibabaRequestService.refreshTokenWithEffectiveness(aliToken);
		if(aliToken.getAccessToken().equals(accessToken)){
			SessionUtil.setAliSession(aliToken);   //设置到threadLocal里面
			request.getSession().setAttribute(AliConstant.ali_info_name,aliToken);//aliToken设置到session中
		}
		return "redirect:/picerrequest/index";
	}
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView indexGet(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "index.jsp");
		return new ModelAndView("aceadmin/picerrequest/picerIndex",model);
	}
	
	@RequestMapping(value="/alicopy",method=RequestMethod.GET)
	public ModelAndView alicopy(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "../copy/oneproduct.jsp");
		return new ModelAndView("aceadmin/picerrequest/picerIndex",model);
	}
	
	@RequestMapping(value="/imagecopy",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "../copy/imageIndex.jsp");
		return new ModelAndView("aceadmin/picerrequest/picerIndex",model);
	}
	
	/**
	 * 淘宝复制到阿里index页面请求
	 * @param request
	 * @param response
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value="/taobaocopy",method=RequestMethod.GET)
	public ModelAndView taobaocopy(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "../copy/taobao/oneproduct.jsp");
		return new ModelAndView("aceadmin/picerrequest/picerIndex",model);
	}
	
	@RequestMapping(value="/history",method=RequestMethod.GET)
	public ModelAndView history(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "../copy/history.jsp");
		return new ModelAndView("aceadmin/picerrequest/picerIndex",model);
	}
	
}
