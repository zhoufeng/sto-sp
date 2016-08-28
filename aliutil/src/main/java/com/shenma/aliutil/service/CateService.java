package com.shenma.aliutil.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.cate.CatInfo;
import com.shenma.aliutil.entity.cate.Postatrribute;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.BeanUtil;

@Service
public class CateService {
	@Autowired 
	private AlibabaRequestService requestService;;
	public Page<Postatrribute> offerPostFeaturesGet(String categoryID) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("categoryID", new Long(categoryID));
		Map<String,Object> ret=requestService.reqAliApp("offerPostFeatures.get", params);
		Page<Postatrribute> customList=BeanUtil.transfAliRequest(ret, Postatrribute.class);
		return customList;
	}
	
	public List<CatInfo> getCatListByParentId(String parentCategoryID) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("parentCategoryID", new Long(parentCategoryID));
		params.put("getAllChildren", "T");
		Map<String,Object> ret=requestService.reqAliApp("category.getCatListByParentId", params);
		List<CatInfo> customList=BeanUtil.transf(ret, CatInfo.class);
		return customList;
	}
}
