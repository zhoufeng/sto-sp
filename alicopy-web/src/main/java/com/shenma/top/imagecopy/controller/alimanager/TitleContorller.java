package com.shenma.top.imagecopy.controller.alimanager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.entity.goods.OfferDetailInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.GoodsService;
import com.shenma.aliutil.service.SelfCatService;
import com.shenma.aliutil.util.AliPage;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.taobao.api.ApiException;


@Controller
@RequestMapping("/alimanager/title")
public class TitleContorller {
	protected static Logger logger = Logger.getLogger("TitleContorller");
	@Autowired
	private SelfCatService selfCatService;
	
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws ApiException, AliReqException, JsonGenerationException, JsonMappingException, IOException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "alimanager/title.jsp");
		Map<String,Object> cates=selfCatService.getAllCate();
		String catesStr=JacksonJsonMapper.getInstance().writeValueAsString(cates);
		model.put("cates", catesStr);
		return new ModelAndView("aceadmin/indexcopy",model);
	}
	
	
	@RequestMapping(value="/searchproducts")
	@ResponseBody
	public AliPage searchproducts(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,@RequestParam("sellerCids") String sellerCids) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, AliReqException{
		Map<String,Object> params=new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(sellerCids))params.put("groupIds", ""+sellerCids);
		params.put("type", "ALL");
		params.put("page", pageNo);
		params.put("pageSize", pageSize);
		params.put("returnFields", new String[]{"offerId","imageList","subject"});
		AliPage alipage=goodsService.getPublishOfferList(params);
		for(OfferDetailInfo o:alipage.getToReturn()){
			if(o.getImageList()!=null&&o.getImageList().size()>0)o.setPriceUnit(o.getImageList().get(0).getSize64x64URL());
			o.setDetailsUrl("http://detail.1688.com/offer/"+o.getOfferId()+".html");
		}
		return alipage;
	} 
	
	@RequestMapping(value="/saveByIds")
	@ResponseBody
	public String svaeByIds(@RequestBody Map<String,Object> variables) throws ApiException{
		List<Map<String,Object>> list=(List<Map<String, Object>>) variables.get("urlList");
		StringBuffer offers=new StringBuffer();
		StringBuffer subjects=new StringBuffer();
		for(Map<String,Object> obj:list){
			Long offerId=(Long) obj.get("offerId");
			offers.append(offerId).append(";");
			String subject=(String) obj.get("subject");
			subjects.append(subject).append(";");
		}
		Map<String,Object> params=new HashMap<String,Object>();
		String offerIds=offers.toString();
		params.put("offerIds", offerIds.substring(0, offerIds.length()-1));
		params.put("subjects", subjects.substring(0, subjects.length()-1));
		params.put("isModify", true);
		Map<String,Object> ret=null;
		try {
			ret=goodsService.batchTitle(params);
		} catch (AliReqException e) {
			logger.error(e);
		}
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		Boolean success=(Boolean) result.get("success");
		return success.toString();
		
	}
	
}
