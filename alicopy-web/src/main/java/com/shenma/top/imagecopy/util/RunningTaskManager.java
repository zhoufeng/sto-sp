package com.shenma.top.imagecopy.util;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.shenma.top.imagecopy.util.bean.RunningTask;

/**
 * 用户保存进度管理类
 * @author zhoufeng
 * @ClassName RunningTaskManager
 * @Version 1.0.0
 */
public class RunningTaskManager {
	private  static final ConcurrentMap<String, RunningTask> taskMap=new ConcurrentHashMap<String, RunningTask>();
	
	public static RunningTask addTask(Integer totals){
		RunningTask task=new RunningTask();
		task.setId(UUID.randomUUID().toString());
		task.setTotals(totals);
		task.setCurrentNum(0);
		task.setStartTime(new Date());
		taskMap.put(task.getId(), task);
		return task;
	}
	
	public static void delTask(String id){
		taskMap.remove(id);
	}
	
	public static RunningTask stepTask(String id,Integer step){
		RunningTask task=taskMap.get(id);
		task.setCurrentNum(task.getCurrentNum()+step);
		return task;
	}
	
}
