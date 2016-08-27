package com.shenma.top.imagecopy.controller.rmi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.entity.OwnCatInfo;

@Controller
@RequestMapping("/webkitrequest")
public class WebKitRequestController {
	
	@Autowired
	private OwnCatInfoDao ownCatInfoDao;
	
	@RequestMapping(value="/findCateById")
	@ResponseBody 
	public Map<String,Object> getCatInfoByCatId(@RequestParam("catsId") String catsId){
		Map<String,Object> ret=new HashMap<String,Object>(4);
		OwnCatInfo catInfo=ownCatInfoDao.findByCatsId(Integer.valueOf(catsId));
		ret.put("catInfo", catInfo);
		return ret;
	}
}
