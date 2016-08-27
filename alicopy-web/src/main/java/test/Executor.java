package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.shenma.top.imagecopy.util.BaseHttpClient;

public class Executor {
	public static void main(String[] args) throws InterruptedException {

		// 分别给定3种情况的Callable
		
		BaseHttpClient.getHttpClient();
		ExecutorService es = Executors.newFixedThreadPool(2);
		for(int i=1;i<100;i++){
			CallSample temp = new CallSample(0,"第"+i+"个线程");
			es.submit(temp);
		}
		
	}
}