package com.shenma.top.imagecopy.util.image;

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.bohusoft.dubboapi.entity.ImageBean;

public class ImageSaveManager {
	protected static Logger logger = Logger.getLogger("ImageSaveManager");
	
	private static final ExecutorService reqImageThreadPool = Executors.newFixedThreadPool(6);
	
	public static ImageBean reqImage(String url) throws InterruptedException, ExecutionException{
		ImageTask task=new ImageTask(url);
		Future<ImageBean> ret=reqImageThreadPool.submit(task);
		return ret.get();
	}
	
	public static List<ImageBean> reqImages(List<String> urlList) throws InterruptedException, ExecutionException{
		List<ImageTask> tasks=new ArrayList<ImageTask>(urlList.size());
		for(String url:urlList){
			ImageTask task=new ImageTask(url);
			tasks.add(task);
		}
		List<Future<ImageBean>> ret=reqImageThreadPool.invokeAll(tasks);
		List<ImageBean> retList=new ArrayList<ImageBean>(ret.size());
		for(Future<ImageBean> fu:ret){
			retList.add(fu.get());
		}
		return retList;
	}
	
	
	static class ImageTask  implements Callable<ImageBean> {
		private String url=null;
		public ImageTask(String url){
			this.url=url;
		}
		 @Override
        public ImageBean call() {
			 byte[] data=readUrlImage(url);
			 ImageBean bean=new ImageBean(url, data);
			 return bean;
			 
		}
    }
	
	
	
	
	/**
	 * 请求图片,设置连接主机的超时时间（单位：5秒）,从主机读取数据的超时时间（单位：12秒）  
	 * @param urlstr
	 * @return
	 */
	@SuppressWarnings("unused")
	private static byte[] readUrlImage(String urlstr){
    	URLConnection urlConn=null;
    	byte[] ret=null;
    	try {
	    	URL url = new URL(urlstr);
	    	urlConn=url.openConnection();
	    	urlConn.setConnectTimeout(6000);
	    	urlConn.setReadTimeout(20000);
	    	//int size =urlConn.getContentLength();
			//不超时
			//con.setConnectTimeout(30000);
			
			//不允许缓存
	    	ret=IOUtils.toByteArray(urlConn);
    	} catch (FileNotFoundException e) {
    		logger.error("请求图片不存在:"+urlstr, e);
		}catch (Exception e) {
    		logger.error("请求图片下载出错:"+urlstr, e);
		}finally{
			if (urlConn instanceof HttpURLConnection)
			      ((HttpURLConnection)urlConn).disconnect();
			urlConn=null;
		}
    	return ret;
    }
}
