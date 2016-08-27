package com.bohusoft.htmlfetch.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.Assert;

import org.junit.Test;

import com.bohusoft.htmlfetch.util.CustomerHttpClient;


public class CustomerHttpClientTest {

	@Test
    public void testGet() {
		String url="http://detail.1688.com/offer/521393888812.html";
		String html=CustomerHttpClient.get(url);
		Assert.assertNotNull(html);
	}
	
	/**
	 * 测试httpclient并发情况
	 * @throws InterruptedException
	 * @throws ExecutionException 
	 */
	@Test
	public void testConcurrent() throws InterruptedException, ExecutionException{
		String[] urisToGet = {
                "http://hc.apache.org/",
                "http://hc.apache.org/httpcomponents-core-ga/",
                "http://hc.apache.org/httpcomponents-client-ga/",
                "http://hc.apache.org/",
                "http://hc.apache.org/httpcomponents-client-ga/"
            };
		ExecutorService exec=Executors.newCachedThreadPool();
		List<Future<String>> futureList=new ArrayList<Future<String>>(4);
		for(int i=0;i<urisToGet.length;i++){
			Callable<String> task=new TaskWithResult(urisToGet[i]);
			futureList.add(exec.submit(task));
		}
		Assert.assertEquals(futureList.get(0).get().length(),futureList.get(3).get().length());
		Assert.assertEquals(futureList.get(2).get().length(),futureList.get(4).get().length());
	}
	static class TaskWithResult  implements Callable<String> {
		private String url=null;
		public TaskWithResult(String url){
			this.url=url;
		}
		 @Override
        public String call() {
			 return CustomerHttpClient.get(url);
			 
		}
    }
}
