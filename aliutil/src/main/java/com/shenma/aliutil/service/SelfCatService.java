package com.shenma.aliutil.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.SelfCat.SellerCatInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.BeanUtil;
import com.shenma.common.util.SessionThread;

@Service
public class SelfCatService {
	
	@Autowired
	private AlibabaRequestService requestService;
	
	
	
	
	/**
	 * 获得所有自定义分类
	 * @return
	 * @throws AliReqException 
	 */
	public Map<String,Object> getAllCate() throws AliReqException{
		Map<String,Object> params=new HashMap<String,Object>();
		AliToken info=(AliToken) SessionThread.get().get(AliConstant.ali_info_name);
		params.put("memberId", info.getMemberId());
		Map<String,Object> ret=requestService.reqAliApp("category.getSelfCatlist", params);
		return ret;
	}
	
	
	/**
	 * 获取阿里巴巴中国网站会员是否已经开启自定义分类功能 
	 * @return
	 * @throws AliReqException
	 */
	@SuppressWarnings({ "unchecked" })
	public boolean hasOpened() throws AliReqException{
		Map<String,Object> params=new HashMap<String,Object>();
		AliToken info=(AliToken) SessionThread.get().get(AliConstant.ali_info_name);
		params.put("memberId", info.getMemberId());
		Map<String,Object> ret=requestService.reqAliApp("offerGroup.hasOpened", params);
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		List<Map<String,Object>> toReturn=(List<Map<String, Object>>) result.get("toReturn");
		Boolean opened=(Boolean) toReturn.get(0).get("isOpened");
		return opened;
	}
	

	/**
	 * 批量添加多个产品到一个自定义分类下 ({"result":{"total":0,"toReturn":[{"OPERATE_SUCCESS":"OPERATE_SUCCESS"}],"success":true}})返回都是成功的
	 * @param offerIds 多个产品id半角分号分隔;
	 * @param groupId 要添加到的自定义分类ID
	 * @throws AliReqException 
	 */
	public void addCategorys(String offerIds,String groupId) throws AliReqException{
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("offerIds", offerIds);
		params.put("groupId", groupId);
		Map<String,Object> ret=requestService.reqAliApp("userCategory.offers.add", params);
		
	}
	/**
	 * 获得所有自定义分类
	 * @return
	 * @throws AliReqException 
	 */
	public List<SellerCatInfo> getAllCatByMemberId() throws AliReqException{
		Map<String,Object> params=new HashMap<String,Object>();
		AliToken info=(AliToken) SessionThread.get().get(AliConstant.ali_info_name);
		params.put("memberId", info.getMemberId());
		Map<String,Object> ret=requestService.reqAliApp("category.getSelfCatlist", params);
		List<SellerCatInfo> list=transfList(ret);
		return list;
	}
	@SuppressWarnings("unchecked")
	private List<SellerCatInfo> transfList(Map<String,Object> ret){
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		Integer total=(Integer) result.get("total");
		if(total==0)return new ArrayList<SellerCatInfo>();
		List<Map<String,Object>> toReturn=(List<Map<String, Object>>) result.get("toReturn");
		List<Map<String,Object>> sellerCats=(List<Map<String, Object>>) toReturn.get(0).get("sellerCats");
		List<SellerCatInfo> list=new ArrayList<SellerCatInfo>();
		for(Map<String, Object> map:sellerCats){
			SellerCatInfo s=BeanUtil.map2Bean(map, SellerCatInfo.class);
			List<Map<String,Object>> child=(List<Map<String, Object>>) map.get("children");
			if(child!=null&&child.size()>0){
				for(Map<String,Object> map2:child){
					SellerCatInfo s2=BeanUtil.map2Bean(map2, SellerCatInfo.class);
					list.add(s2);
				}
			}
			list.add(s);
		}
		return list;
	}
}
