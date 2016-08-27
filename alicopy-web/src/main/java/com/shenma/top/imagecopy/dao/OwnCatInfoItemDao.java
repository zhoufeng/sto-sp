package com.shenma.top.imagecopy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shenma.top.imagecopy.entity.OwnCatInfoItem;


public interface OwnCatInfoItemDao extends JpaRepository<OwnCatInfoItem, Integer>, JpaSpecificationExecutor<OwnCatInfoItem>{

	public OwnCatInfoItem findByCatIdAndPathValues(Integer catId,String pathValues);
	
}
