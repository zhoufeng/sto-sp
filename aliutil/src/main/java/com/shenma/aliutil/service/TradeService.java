package com.shenma.aliutil.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.trade.DeliveryAddress;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.BeanUtil;
import com.shenma.common.util.SessionThread;

/**
 * 交易包的接口
 * @author zhoufeng
 *
 */
@Service
public class TradeService {
	@Autowired
	private AlibabaRequestService requestService;
	@SuppressWarnings("unchecked")
	public List<DeliveryAddress> freightSendGoodsAddressList() throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("returnFields", "deliveryAddressId,updateTime,isCommonUse,address,location");
		AliToken info=(AliToken) SessionThread.get().get(AliConstant.ali_info_name);
		params.put("memberId", info.getMemberId());
		Map<String,Object> myRet=requestService.reqAliApp("trade.freight.sendGoodsAddressList.get", params);
		Map<String,Object> result=(Map<String, Object>) myRet.get("result");
		List<Map<String,Object>> toReturn=(List<Map<String, Object>>) result.get("toReturn");
		List<DeliveryAddress> myList=new ArrayList<DeliveryAddress>();
		for(Map<String,Object> map:toReturn){
			DeliveryAddress o=BeanUtil.map2Bean(map, DeliveryAddress.class);
			myList.add(o);
		}
		return myList;
	}

}
