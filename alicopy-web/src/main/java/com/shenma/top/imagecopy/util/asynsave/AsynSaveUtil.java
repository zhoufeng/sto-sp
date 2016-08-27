package com.shenma.top.imagecopy.util.asynsave;

import java.util.List;
import java.util.concurrent.FutureTask;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.dao.SaveTaskDao;
import com.shenma.top.imagecopy.entity.SaveTask;

/**
 * 异步请求
 * @author zhoufeng
 * @ClassName AsynSaveUtil
 * @Version 1.0.0
 */
@Component
public class AsynSaveUtil {
	protected static Logger logger = Logger.getLogger("AsynSaveUtil");
	@Autowired
	private AsynSaveProvider asynSaveProvider;
	
	@Autowired
	private AsynBatchSaveProvider asynBatchSaveProvider;
	
	@Autowired
	private SaveTaskDao saveTaskDao;

	@PostConstruct
	public void init() throws Exception {
		FutureTask<String> oneTask = new FutureTask<String>(asynSaveProvider);
		Thread oneThread = new Thread(oneTask);
		oneThread.start();
		//将数据库所有的task任务加入到任务列表
		List<SaveTask> taskList=saveTaskDao.findByBatchType(0);
		for(SaveTask saveTask:taskList){
			offerTask(saveTask);
		}
		
		FutureTask<String> batchTask = new FutureTask<String>(asynBatchSaveProvider);
		Thread batchThread = new Thread(batchTask);
		batchThread.start();
		//将数据库所有的task任务加入到任务列表
		List<SaveTask> batchTaskList=saveTaskDao.findByBatchType(1);
		for(SaveTask saveTask:batchTaskList){
			offerTask(saveTask);
		}
	}
	
	public void offerTask(SaveTask task){
		if(task.getBatchType()==0){
			asynSaveProvider.putTask(task);
		}else{
			asynBatchSaveProvider.putTask(task);
		}
	}
	public Integer getQueueSize(boolean isBatch){
		return isBatch?asynBatchSaveProvider.getSize():asynSaveProvider.getSize();
	}
}
