package com.shenma.top.imagecopy.controller.rmi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shenma.top.imagecopy.dao.CollectProductDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.entity.CollectProduct;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.service.GlobalService;
import com.shenma.top.imagecopy.util.CustomObjectMapper;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;

@Controller
@RequestMapping("/globalcopy/api/collectProducts")
public class GobalCopyController {
	protected static Logger logger = Logger.getLogger("GobalCopyController");
	@Autowired
	private GlobalService globalService;
	@Autowired
	private OwnCatInfoDao catInfoDao;
	@SuppressWarnings("unchecked")
	@RequestMapping(value="",method=RequestMethod.POST)
	@ResponseBody 
	public Map<String,Object> save(@RequestBody Map<String,Object> variables) throws IllegalAccessException, InvocationTargetException{
		Map<String, Object> ret=new HashMap<String, Object>();
		CollectProduct entity=new CollectProduct();
		BeanUtils.populate(entity, variables);
		try {
			entity=globalService.saveCollectProduct(entity);
			ret.put("entity", entity);
		} catch (Exception e) {
			ret.put("errorMsg", "保存失败");
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="",method=RequestMethod.GET)
	@ResponseBody 
	public Map<String,Object> list(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize) throws IllegalAccessException, InvocationTargetException{
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		Page<CollectProduct> page=globalService.listCollectProducts(pageNo, pageSize, params);
		ret.put("page", page);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	@ResponseBody 
	public String delete(@PathVariable("id") Long id) throws IllegalAccessException, InvocationTargetException{
		try {
			globalService.deleteById(id);
		} catch (Exception e) {
			logger.error("删除CollectProduct的id为"+id+"失败");
		}
		return "success";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/batchDelete",method=RequestMethod.POST)
	@ResponseBody 
	public String batchDelete(@RequestBody Map<String,Object> variables) throws IllegalAccessException, InvocationTargetException{
		List<Integer> ids=(List<Integer>) variables.get("ids");
		List<Long> idsParm=new ArrayList<Long>();
		for(Integer id:ids){
			idsParm.add(Long.valueOf(id.toString()));
		}
		try {
			globalService.batchDelete(idsParm);
		} catch (Exception e) {
			logger.error("删除CollectProduct的id为"+ids+"失败");
		}
		return "success";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findCateByCateId",method=RequestMethod.GET)
	@ResponseBody 
	public String findCateByCateId(@RequestParam("catId") Integer	catId) throws IllegalAccessException, InvocationTargetException{
		OwnCatInfo catInfo=null;
		try {
			catInfo=catInfoDao.findByCatsId(catId);
		} catch (Exception e) {
			logger.error("查找类别失败!");
		}
		return catInfo==null?"":catInfo.getCatsName();
	}
}
