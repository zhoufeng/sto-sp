package com.shenma.top.imagecopy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shenma.top.imagecopy.entity.CollectProduct;

public interface CollectProductDao extends JpaRepository<CollectProduct, Long>, JpaSpecificationExecutor<CollectProduct>{

}
