package com.shenma.aliutil.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.wuliu.DeliveryTemplateDescn;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.BeanUtil;

@Service
public class WuliuService {
	@Autowired 
	private AlibabaRequestService requestService;;
	public List<DeliveryTemplateDescn> getAllDeliveryTemplateDescn() throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		Map<String,Object> myRet=requestService.reqAliApp("e56.delivery.template.list", params);
		List<Map<String,Object>> toReturn=(List<Map<String, Object>>) myRet.get("result");
		List<DeliveryTemplateDescn> myList=new ArrayList<DeliveryTemplateDescn>();
		for(Map<String,Object> map:toReturn){
			DeliveryTemplateDescn o=BeanUtil.map2Bean(map, DeliveryTemplateDescn.class);
			myList.add(o);
		}
		return myList;
	}
}
