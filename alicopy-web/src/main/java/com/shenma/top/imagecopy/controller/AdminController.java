package com.shenma.top.imagecopy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.entity.platform.IsvOrderItemDto;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.PlatformService;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.service.AliBaBaSaveService;
import com.shenma.top.imagecopy.util.AliCateAuto2Util;
import com.shenma.top.imagecopy.util.AliCateListAutoUtil;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.TaobaoCateUtil;
import com.taobao.api.ApiException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Controller
@RequestMapping("/manager")
public class AdminController {
	protected static Logger logger = Logger.getLogger("AdminController");
	@Autowired
	private OwnCatInfoDao ownCatInfoDao;
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private AliBaBaSaveService aliBaBaSaveService;
	
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(@RequestParam("catsId") String catsId) throws IOException, InterruptedException{
		OwnCatInfo bean=ownCatInfoDao.findByCatsId(Integer.valueOf(catsId));
		if(bean!=null){
			String properties=AliCateAuto2Util.getSkuInfo(bean.getTopCategoryId(),bean.getSecondCategoryId(),bean.getThirdCategoryId(),bean.getTradeYpe());
			bean.setProperties(properties);
			ownCatInfoDao.saveAndFlush(bean);
			aliBaBaSaveService.savecateItemBycateId(bean);
			
		}
		return "success";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/updateAll")
	@ResponseBody
	public String updateAll() throws IOException, AliReqException, InterruptedException{
		if(1==1){
		String rootUrl="http://offer.1688.com/offer/asyn/category_selector.json?callback=jQuery172075146440915465_1423106686697&loginCheck=N&dealType=getSubCatInfo&categoryId=0&scene=offer";
		String liststr=AliCateListAutoUtil.get(rootUrl, new HashMap(), "utf-8");
		Map<String,Object> rootdata=JacksonJsonMapper.getInstance().readValue(AliCateListAutoUtil.getData(liststr),HashMap.class);
		List<Map<String,Object>> rootlist=(List<Map<String, Object>>) rootdata.get("data");
		for(Map<String,Object> rootCat:rootlist){
			String sectUrl="http://offer.1688.com/offer/asyn/category_selector.json";
			Map<String,String> params=new HashMap<String,String>();
			params.put("callback", "jQuery172075146440915465_1423106686697");
			params.put("loginCheck", "N");
			params.put("dealType", "getSubCatInfo");
			params.put("scene", "offer");
			params.put("tradeType", rootCat.get("tradeType").toString());
			params.put("categoryId", rootCat.get("id").toString());
			String secStr=AliCateListAutoUtil.get(sectUrl, params, "gbk");
			Map<String,Object> secdata=JacksonJsonMapper.getInstance().readValue(AliCateListAutoUtil.getData(secStr),HashMap.class);
			List<Map<String,Object>> sectlist=(List<Map<String, Object>>) secdata.get("data");
			for(Map<String,Object> secCate:sectlist){//迭代第二类别
				if(secCate.get("isLeaf").toString().equals("true")){
					OwnCatInfo obj=ownCatInfoDao.findByCatsId(Integer.valueOf(secCate.get("id").toString()));
					if(obj==null)obj=new OwnCatInfo();
					obj.setCatsId(Integer.valueOf(secCate.get("id").toString()));
					obj.setCatsName(secCate.get("name").toString());
					obj.setTopCategoryId(Integer.valueOf(rootCat.get("id").toString()));
					obj.setSecondCategoryId(Integer.valueOf(secCate.get("id").toString()));
					obj.setTradeYpe(Integer.valueOf(secCate.get("tradeType").toString()));
					ownCatInfoDao.saveAndFlush(obj);
				}else{
					String thtUrl="http://offer.1688.com/offer/asyn/category_selector.json";
					Map<String,String> thparams=new HashMap<String,String>();
					thparams.put("callback", "jQuery172075146440915465_1423106686697");
					thparams.put("loginCheck", "N");
					thparams.put("dealType", "getSubCatInfo");
					thparams.put("scene", "offer");
					thparams.put("tradeType", secCate.get("tradeType").toString());
					thparams.put("categoryId", secCate.get("id").toString());
					String thStr=AliCateListAutoUtil.get(thtUrl, thparams, "gbk");
					if(thStr==null||thStr.equals("")){
						continue;
					}
					Map<String,Object> thdata=JacksonJsonMapper.getInstance().readValue(AliCateListAutoUtil.getData(thStr),HashMap.class);
					List<Map<String,Object>> thlist=(List<Map<String, Object>>) thdata.get("data");
					for(Map<String,Object> thCate:thlist){//迭代第三类别
						OwnCatInfo obj=ownCatInfoDao.findByCatsId(Integer.valueOf(thCate.get("id").toString()));
						if(obj==null)obj=new OwnCatInfo();
						obj.setCatsId(Integer.valueOf(thCate.get("id").toString()));
						obj.setCatsName(thCate.get("name").toString());
						obj.setTopCategoryId(Integer.valueOf(rootCat.get("id").toString()));
						obj.setSecondCategoryId(Integer.valueOf(secCate.get("id").toString()));
						obj.setThirdCategoryId(Integer.valueOf(thCate.get("id").toString()));
						obj.setTradeYpe(Integer.valueOf(thCate.get("tradeType").toString()));
						ownCatInfoDao.saveAndFlush(obj);
					}
				}
			}
		}
		List<OwnCatInfo> list=ownCatInfoDao.findAll();
		for(OwnCatInfo bean:list){
			if(bean.getId()<424502)continue;
			AliCateAuto2Util.getHttpClient();
			String properties=AliCateAuto2Util.getSkuInfo(bean.getTopCategoryId(),bean.getSecondCategoryId(),bean.getThirdCategoryId(),bean.getTradeYpe());
			System.out.println(properties);
			Map<String,Object> m=JacksonJsonMapper.getInstance().readValue(properties, HashMap.class);
			bean.setProperties(properties);
			ownCatInfoDao.saveAndFlush(bean);
		}
		}
		aliBaBaSaveService.genCatesToDb();
		
		
		return "success";
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/updateAllItem")
	@ResponseBody
	public String updateAllItem() throws IOException, AliReqException, InterruptedException{
		aliBaBaSaveService.genCatesToDb();
		return "success";
	}
	
	/**
	 * 跟新阿里登录的session.
	 * @param catsId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/updateAliSession")
	@ResponseBody
	public String updateAliSession(@RequestParam("cookieValue") String cookieValue,@RequestParam("csrfToken") String csrfToken) throws IOException{
		AliCateAuto2Util.cookieValue=cookieValue;
		AliCateAuto2Util.csrf_token=csrfToken;
		return "success";
	}
	
	
	
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public ModelAndView history(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "alimanager/orderList.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	
	@RequestMapping(value="/changeTmailSearchUrl",method=RequestMethod.GET)
	@ResponseBody
	public String changeTmailSearchUrl(@RequestParam("url") String url ) throws ApiException{
		TaobaoCateUtil.tmailSearchUrl=url;
		return "success";
	}
	
	
	@RequestMapping(value="/getAllOrder")
	@ResponseBody
	public Page<IsvOrderItemDto> getAllOrder(@RequestParam("date") String date,@RequestParam("type") Integer type) throws IOException{
		List<IsvOrderItemDto> retList=null;
		try {
			if(type==0){
				retList=platformService.getAllByDate(date);
			}else if(type==1){
				retList=platformService.getNotFreeAllByDate(date);
			}
		} catch (AliReqException e) {
			logger.error(e);
		}
		Page<IsvOrderItemDto> page=new PageImpl<IsvOrderItemDto>(retList);
		return page;
	}
	
	@RequestMapping(value="/getIp")
	@ResponseBody
	public String getIp(HttpServletRequest request){
		return getIpAddr(request);
	}
    public  String getIpAddr(HttpServletRequest request)  {
        String ip  =  request.getHeader( " x-forwarded-for " );
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " Proxy-Client-IP " );
        } 
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " WL-Proxy-Client-IP " );
        } 
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
           ip  =  request.getRemoteAddr();
       } 
        return  ip;
   }
    
	@RequestMapping(value="/getRealIp")
	@ResponseBody
	public String getRealIp(HttpServletRequest request){
		String ip  =  request.getHeader("X-real-ip");
		TaobaoCateUtil.tmailSearchUrl="http://"+ip+":8079/tmail/search";
		return ip;
	}
	
	@RequestMapping(value="/getServerIp")
	@ResponseBody
	public String getServerIp(HttpServletRequest request){
		return TaobaoCateUtil.tmailSearchUrl;
	}
}
