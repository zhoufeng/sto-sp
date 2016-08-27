package com.shenma.top.imagecopy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.goods.OfferDetailInfo;
import com.shenma.aliutil.entity.goods.OfferImageInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.GoodsService;
import com.shenma.aliutil.util.AliPage;


@Service
public class LitterFunService {
	@Autowired
	private GoodsService goodsService;
	/**
	 * 获得所有商品重复的列表
	 * @return
	 * @throws AliReqException
	 */
	public Map<Long,OfferDetailInfo> findRepProduct() throws AliReqException{
		Map<Long,OfferDetailInfo> ret=new HashMap<Long,OfferDetailInfo>();
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("type", "ALL");
		params.put("page",1);
		params.put("pageSize",50);
		params.put("returnFields", new String[]{"offerId","subject","imageList","price"});
		AliPage aliPage=goodsService.getPublishOfferList(params);
		Map<String,OfferDetailInfo> temp=new HashMap<String,OfferDetailInfo>(); 
		changleData(ret, aliPage, temp);
		Integer totalPage=aliPage.getTotal()/50+1;
		for(int i=2;i<=totalPage;i++){
			params.put("page",i);
			aliPage=goodsService.getAllOfferList(params);
			changleData(ret, aliPage, temp);
		}

		for(Long id:ret.keySet()){
			OfferDetailInfo info=ret.get(id);
			String detailsUrl="http://detail.1688.com/offer/"+info.getOfferId().toString()+".html";
			if(info.getImageList()!=null&&info.getImageList().size()>0){
				String imageUrl=info.getImageList().get(0).getSize64x64URL();
				info.setType(imageUrl);
			}
			info.setDetailsUrl(detailsUrl);
		}
		return ret;
	}
	private void changleData(Map<Long, OfferDetailInfo> ret, AliPage aliPage,
			Map<String, OfferDetailInfo> temp) {
		for(OfferDetailInfo info:aliPage.getToReturn()){

			if(!temp.containsKey(info.getSubject())){
				temp.put(info.getSubject(),info);
			}else{
				ret.put(info.getOfferId(), info);
				OfferDetailInfo tempInfo=temp.get(info.getSubject());
				ret.put(tempInfo.getOfferId(), tempInfo);
			}
		}
	}
	
	public boolean delOffer(String offerId) throws AliReqException{
		return goodsService.delOffer(offerId);
	}
}
