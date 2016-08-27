package com.shenma.tiger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.service.GoodsService;

@Service
public class RepostService {
	@Autowired
	private GoodsService goodsService;
	
	public Integer repostOffers(){
		
		return 1;
	}
}
