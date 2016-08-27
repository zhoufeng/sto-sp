package com.shenma.top.imagecopy.util.asynsave;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.entity.SaveTask;

@Component
public class AsynBatchSaveProvider extends BaseAsynSaveProvider implements
		Callable<String> {
	protected static Logger logger = Logger.getLogger("AsynBatchSaveProvider");
	private BlockingQueue<SaveTask> basket = new LinkedBlockingDeque<SaveTask>();
	@Override
	public String call() throws Exception {
		while(true){
			SaveTask task=basket.take();
			runSaveTask(task);
		}
	}
	
	/**
	 * 默认加载方式,FIFO顺序
	 * @param task
	 */
	public void putTask(SaveTask task){
		basket.offer(task);
	}
	
	public Integer getSize(){
		return basket.size();
	}

	
	
}
