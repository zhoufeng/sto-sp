package com.shenma.top.imagecopy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.util.SessionUtil;
import com.shenma.taobao.service.product.ItemsService;
import com.shenma.taobao.util.TopAccessToken;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemsOnsaleGetResponse;

@Controller
@RequestMapping("/top/opt")
public class TopOprationController {
	protected static Logger logger = Logger.getLogger("TopOprationController");
	@Autowired
	private ItemsService  itemsService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "top/list.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public List<Item> list(HttpServletRequest request) throws ApiException{
		List<Item> list=itemsService.list();
		return list;
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public ModelAndView test(){
		return new ModelAndView("test/test");
	}
	
	/**
	 * 出售中的商品
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value="/onsale/item",method=RequestMethod.GET)
	@ResponseBody
	public TaobaoResponse onsale(HttpServletRequest request) throws ApiException{
		Long pageNo=Long.valueOf(request.getParameter("pageNo")); 
		String q=request.getParameter("q");
		//Long pageSize=Long.valueOf(request.getParameter("pageSize")); 
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("pageNo", pageNo);
		params.put("pageSize", new Long(10));
		if(!StringUtils.isEmpty(q))params.put("q", q);
		TopAccessToken accessToken=(TopAccessToken) null;
		params.put("session",accessToken.getAccess_token());
		ItemsOnsaleGetResponse response=itemsService.onsale(params);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("session",accessToken.getAccess_token() );
		if(response.getItems()!=null){
			for(Item item:response.getItems()){
				

				/*map.put("numIid", item.getNumIid());
				map.put("fields","detail_url,item_img");
				ItemGetResponse itemRes=itemService.findByOne(map);
				item.setDetailUrl(itemRes.getItem().getDetailUrl());*/
				}
			}
		return response;
	}
	
	/**
	 * 仓库中的商品
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value="/inventory/item")
	@ResponseBody
	public TaobaoResponse inventory(HttpServletRequest request) throws ApiException{
		Long pageNo=Long.valueOf(request.getParameter("pageNo")); 
		//Long pageSize=Long.valueOf(request.getParameter("pageSize")); 
		String q=request.getParameter("q");
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("pageNo", pageNo);
		params.put("pageSize", new Long(10));
		if(!StringUtils.isEmpty(q))params.put("q", q);
		TopAccessToken accessToken=(TopAccessToken) null;
		params.put("session",accessToken.getAccess_token() );
		TaobaoResponse response=itemsService.inventory(params);
		return response;
	}
}
