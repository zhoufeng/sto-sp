package com.shenma.top.imagecopy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.entity.goods.OfferDetailInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.top.imagecopy.service.LitterFunService;

@Controller
@RequestMapping("/litterfun")
public class LitterFunctionController {
	protected static Logger logger = Logger.getLogger("LitterFunctionController");
	@Autowired
	private LitterFunService funService;
	/**
	 * 删除重复宝贝首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delRepIndex",method=RequestMethod.GET)
	public ModelAndView delRepIndex(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "litterfun/delrep_index.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	
	@RequestMapping(value="/searchRepProduct")
	@ResponseBody
	public Map<String,Object> searchRepProduct() throws IOException, InterruptedException{
		Map<String,Object> ret=new HashMap<String,Object>();
		List<OfferDetailInfo> items=new ArrayList<OfferDetailInfo>();
		ret.put("items", items);
		try {
			Map<Long, OfferDetailInfo> m=funService.findRepProduct();
			for(Long id:m.keySet()){
				items.add(m.get(id));
			}
			Collections.sort(items, new Comparator<OfferDetailInfo>() {

				@Override
				public int compare(OfferDetailInfo o1, OfferDetailInfo o2) {
					String o1Subject=o1.getSubject();
					String o2Subject=o2.getSubject();
					return o1Subject.compareTo(o2Subject);
				}
				
			});
		} catch (AliReqException e) {
			logger.error("查询重复宝贝出错",e);
			ret.put("error", "查询重复宝贝出错,请联系客服!");
		}
		return ret;
	}
	
	/**
	 * 删除重复商品
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value="/delRepProduct")
	@ResponseBody
	public Map<String,Object> delRepProduct(@RequestParam("offerId") String offerId) throws IOException, InterruptedException{
		Map<String,Object> ret=new HashMap<String,Object>();
		try {
			boolean status=funService.delOffer(offerId);
			ret.put("error", "删除宝贝不成功");
		} catch (AliReqException e) {
			logger.error("删除重复宝贝出错",e);
			if("600".equals(e.getCode())){
				ret.put("error", e.getMessage());
			}
			ret.put("error", "删除重复宝贝出错,请联系客服!");
		}
		return ret;
	}
}

