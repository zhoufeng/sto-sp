package com.shenma.top.imagecopy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shenma.top.imagecopy.entity.MqRecord;

public interface MqRecordDao extends JpaRepository<MqRecord, Integer>, JpaSpecificationExecutor<MqRecord>{
	

}
