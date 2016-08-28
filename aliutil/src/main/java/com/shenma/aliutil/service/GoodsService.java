package com.shenma.aliutil.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.goods.OfferDetailInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.AliPage;
import com.shenma.aliutil.util.BeanUtil;

@Service
public class GoodsService {
	
	/**
	 * 
	 * @return
	 * @see 1http://open.1688.com/doc/api/cn/api.htm?ns=cn.alibaba.open&n=offer.getAllOfferList&v=1
	 */
	@Autowired
	private AlibabaRequestService requestService;
	
	/**
	 * 获取阿里巴巴中国网站会员所有的产品。与“获取当前会话站会员已发布的产品”区别仅在于returnFields多一个offerStatus字段,参数结构:message:offerDetailInfo
	 * @param params
	 * @return
	 * @throws AliReqException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public AliPage getAllOfferList(Map<String,Object> params) throws AliReqException{
		
		Map<String,Object> ret=requestService.reqAliApp("offer.getAllOfferList", params);
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		AliPage vo=BeanUtil.map2Bean(result,AliPage.class);
		return vo;
	}
	/**
	 * 获取当前会话会员的已发布产品信息列表
	 * @param params
	 * @return
	 * @throws AliReqException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public AliPage getPublishOfferList(Map<String,Object> params) throws AliReqException{
		
		Map<String,Object> ret=requestService.reqAliApp("offer.getPublishOfferList", params);
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		AliPage vo=BeanUtil.map2Bean(result,AliPage.class);
		return vo;
	}
	
	/**
	 * 获取单个产品信息
	 * @param params
	 * @return
	 * @throws AliReqException
	 */
	@SuppressWarnings("unchecked")
	public OfferDetailInfo getOffer(Map<String,Object> params) throws AliReqException{

		Map<String,Object> ret=requestService.reqAliApp("offer.get", params);
		AliPage vo=BeanUtil.map2Bean((Map<String, Object>) ret.get("result"),AliPage.class);
		List<OfferDetailInfo> list=vo.getToReturn();
		return list.size()>0?list.get(0):null;
	}
	
	public OfferDetailInfo getOffer(String offerId) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offerId", new Long(offerId));
		params.put("returnFields","privateProperties,tradeType,postCategryId,offerStatus,imageList,productFeatureList,tradingType,skuArray,skuPics,privateProperties");
		return getOffer(params);
	}
	
	public OfferDetailInfo getOfferOnlyDesc(String offerId) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offerId", new Long(offerId));
		params.put("returnFields","details");
		return getOffer(params);
	}
	
	@SuppressWarnings("unchecked")
	public Long newOffer(String offerStr) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offer", offerStr);
		Map<String,Object> ret=requestService.reqAliApp("offer.new", params);
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		List<String> list=(List<String>) result.get("toReturn");
		return new Long(list.get(0));
	}
	
	/**
	 * 批量修改标题
	 * @param params
	 * @return
	 * @throws AliReqException
	 */
	public Map<String,Object> batchTitle(Map<String,Object> params) throws AliReqException{
		return requestService.reqAliApp("offers.modify", params);
	}
	
	
	/**
	 * 增量修改
	 * @param offer
	 * @return
	 * @throws AliReqException
	 */
	public Map<String,Object> modifyIncrement(String offer) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offer", offer);
		return requestService.reqAliApp("offer.modify.increment", params);
	}
	/**
	 * 商品过期
	 * @param offerIds
	 * @return
	 * @throws AliReqException
	 */
	public Map<String,Object> expire(String offerIds) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offerIds", offerIds);
		return requestService.reqAliApp("offer.expire", params);
	}
	
	public List<OfferDetailInfo> search(Map<String,Object> params) throws AliReqException{
		AliPage vo=searchPage(params);
		List<OfferDetailInfo> result=vo.getToReturn();
		return result;
	}
	public AliPage searchPage(Map<String,Object> params) throws AliReqException{
		Map<String,Object> ret=requestService.reqAliApp("offer.search", params);
		AliPage vo=BeanUtil.map2Bean((Map<String, Object>) ret.get("result"),AliPage.class);
		return vo;
	}
	
	public boolean delOffer(String offerId) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offerIds", offerId);
		Map<String,Object> ret=requestService.reqAliApp("offer.delete", params);
		Map<String,Object> result=(Map<String, Object>) ret.get("result");
		List<Map<String,Object>> toReturn=(List<Map<String, Object>>) result.get("toReturn");
		boolean isSuccess=(Boolean) toReturn.get(0).get("isSuccess");
		String failure=(String) toReturn.get(0).get("failure");
		if(failure!=null)throw new AliReqException("600", failure);
		return isSuccess;
	}
	
	/**
	 * 商品重发
	 * @return
	 * @throws AliReqException 
	 */
	public Integer repostOffer(List<Long> offerIds) throws AliReqException{
		String ids=StringUtils.join(offerIds,",");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("offerIds", ids);
		Map<String,Object> ret=requestService.reqAliApp("offer.delete", params);
		return 1;
	}
	public static void main(String[] args) {
		Object a=null;
		String b=(String) a;
		System.out.println(b);
	}
}
