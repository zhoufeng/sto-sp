package test;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.jsoup.nodes.Document;

import com.shenma.top.imagecopy.util.BaseHttpClient;
import com.shenma.top.imagecopy.util.JsonpUtil;

public class CallSample implements Callable<String> {
	private ThreadLocal<Integer> count=new ThreadLocal<Integer>();
	private static int aInt;
	private static String name;

	public CallSample(int aInt,String name) {
		this.name=name;
		this.aInt = aInt;
	}

	public String call() throws Exception {
		String url="http://detail.1688.com/offer/1021801184.html";
		for(int i=0;i<10;i++){
			aInt++;
			Document doc = JsonpUtil.getAliDefaultConnet(url);
			System.out.println(Thread.currentThread().getName()+":"+aInt);
			Thread.sleep(300);
		}
		return "test"+aInt;
	}
}