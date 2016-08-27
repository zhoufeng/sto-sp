package com.shenma.top.imagecopy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shenma.top.imagecopy.entity.SaveTask;

public interface SaveTaskDao extends JpaRepository<SaveTask, Integer>, JpaSpecificationExecutor<SaveTask>{

	
	public List<SaveTask> findByBatchType(Integer batchType);
	
}
