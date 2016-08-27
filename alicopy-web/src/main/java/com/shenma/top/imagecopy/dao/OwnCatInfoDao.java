package com.shenma.top.imagecopy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shenma.top.imagecopy.entity.OwnCatInfo;

public interface OwnCatInfoDao extends JpaRepository<OwnCatInfo, Integer>, JpaSpecificationExecutor<OwnCatInfo>{
	public OwnCatInfo findByCatsId(Integer catsId);
}
