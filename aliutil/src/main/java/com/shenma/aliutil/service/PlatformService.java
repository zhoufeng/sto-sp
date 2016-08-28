package com.shenma.aliutil.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.platform.IsvOrderItemDto;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.BeanUtil;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.common.util.DateUtil;

@Service
public class PlatformService {
	@Autowired
	private AlibabaRequestService requestService;
	
	@SuppressWarnings("unchecked")
	public IsvOrderItemDto getByMemberId() throws AliReqException, ParseException{
		Map<String,Object> params=new HashMap<String, Object>(3);
		params.put("appKey", SessionUtil.getAliSession().getAppKey());
		params.put("memberId",SessionUtil.getAliSession().getMemberId());
		IsvOrderItemDto itemDto=null;
		String date=DateUtil.getCurrentDate().substring(0,8);
		outer:for(int i=0;i<12;i++){
			params.put("gmtCreate", DateUtil.addMonth(date, -i)+"240000000+0800");
			Map<String,Object> ret=requestService.reqAliApp("app.order.get", params);
			List<Map<String,Object>> list= (List<Map<String, Object>>) ret.get("returnValue");
			if(list.size()>0){
				for(int j=0;j<list.size();j++){
				itemDto=BeanUtil.map2Bean(list.get(j), IsvOrderItemDto.class);
					if("S".equals(itemDto.getBizStatus())&&itemDto.getGmtServiceEnd()!=null){
						break;
					}
				}
			
			}
		}
	
		return itemDto;
		
	}
	/**
	 * ISV获取自己名下的应用最近一个月的订购的订单信息列表。 
	 * @param date
	 * @return
	 * @throws AliReqException
	 */
	@SuppressWarnings("unchecked")
	private List<IsvOrderItemDto> getAll(String date,Integer startIndex) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>(4);
		params.put("appKey", SessionUtil.getAliSession().getAppKey());
		params.put("startIndex", startIndex);
		params.put("pageSize", 50);
		params.put("gmtCreate", date+"240000000+0800");
		Map<String,Object> ret=requestService.reqAliApp("app.order.get", params);
		List<Map<String,Object>> list= (List<Map<String, Object>>) ret.get("returnValue");
		List<IsvOrderItemDto> retlist=new ArrayList<IsvOrderItemDto>();
		for(Map<String,Object> map:list){
			IsvOrderItemDto itemDto=BeanUtil.map2Bean(map, IsvOrderItemDto.class);
			retlist.add(itemDto);
		}
		return retlist;
	}
	
	/**
	 * ISV获取自己名下的应用最近一个月的所有订单订购的订单信息列表。 
	 * @param date
	 * @return
	 * @throws AliReqException
	 */
	public List<IsvOrderItemDto> getAllByDate(String date) throws AliReqException{
		List<IsvOrderItemDto> retlist=new ArrayList<IsvOrderItemDto>();
		for(int i=1;i<2000;i++){
			List<IsvOrderItemDto> templist=getAll(date,i);
			if(templist.size()==0){
				break;
			}else{
				retlist.addAll(templist);
			}
		}
		return retlist;
	}
	
	
	/**
	 * ISV获取自己名下的应用最近一个月的付费订单订购的订单信息列表。 
	 * @param date
	 * @return
	 * @throws AliReqException
	 */
	public List<IsvOrderItemDto> getNotFreeAllByDate(String date) throws AliReqException{
		List<IsvOrderItemDto> retlist=new ArrayList<IsvOrderItemDto>();
		for(int i=1;i<2000;i++){
			List<IsvOrderItemDto> templist=getNotFreeAll(date,i);
			if(templist==null){
				break;
			}else{
				retlist.addAll(templist);
			}
		}
		return retlist;
	}
	/**
	 * ISV获取自己名下的应用最近一个月的订购的订单信息列表。 
	 * @param date
	 * @return
	 * @throws AliReqException
	 */
	@SuppressWarnings("unchecked")
	private List<IsvOrderItemDto> getNotFreeAll(String date,Integer startIndex) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>(4);
		params.put("appKey", SessionUtil.getAliSession().getAppKey());
		params.put("startIndex", startIndex);
		params.put("pageSize", 50);
		params.put("gmtCreate", date+"240000000+0800");
		Map<String,Object> ret=requestService.reqAliApp("app.order.get", params);
		List<Map<String,Object>> list= (List<Map<String, Object>>) ret.get("returnValue");
		List<IsvOrderItemDto> retlist=new ArrayList<IsvOrderItemDto>();
		if(list.size()==0)return null;
		for(Map<String,Object> map:list){
			IsvOrderItemDto itemDto=BeanUtil.map2Bean(map, IsvOrderItemDto.class);
			if(itemDto.getPaymentAmount()>0d)retlist.add(itemDto);
		}
		return retlist;
	}
	
}
