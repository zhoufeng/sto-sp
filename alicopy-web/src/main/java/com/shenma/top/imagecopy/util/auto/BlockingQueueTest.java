package com.shenma.top.imagecopy.util.auto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 生产者，消费者测试
 * 
 * **/
public class BlockingQueueTest {
	public static Map<String,String> urlMap=new ConcurrentHashMap<String, String>();
	private  static List<String> fileList=new ArrayList<String>();
	private static String host;
	public static  ReadWriteLock lock = new ReentrantReadWriteLock(); 
	public static Lock wirte=lock.writeLock();
	public static void main(String[] args) throws InterruptedException, IOException {
		String url="http://www.orangesh.com/home/cn/index";
		urlMap.put(url, "false");
		host=getHost(url);
		int num=0;
		//创建一个容量为1的队列
		BlockingQueue<String> bq=new ArrayBlockingQueue<>(6);
		
		//启动一个消费者线程
		new Consumer(bq).start();
		//启动生产者线程
		while (true) {
			Thread.sleep(2000);  
			//wirte.lock();
			for(String key:urlMap.keySet()){
				if(urlMap.get(key).equals("false")){
					new Producer(bq,key,wirte).start();
				}
			}
			//wirte.unlock();
			if(bq.size()==0){
				num++;
			}else{
				num=0;
			}
			if(num==20)reply();
			
		}		
		
	}
	
	public static void reply() throws IOException{
		refreshFileList(ParseOnePage.dirPath);
		for(String fileName:fileList){
			readFile(fileName);
		}
	}
	
	private static void readFile(String pathname) throws IOException{
		File file=new File(pathname);
		String str=FileUtils.readFileToString(file, "utf-8");
		Document doc=Jsoup.parse(str);
		changeCss(doc);
		FileUtils.write(file, doc.html());
	}
	private static void changeCss(Document document){
		Elements imports = document.select("link[href]");
		for(Element element:imports){
			String url=element.attr("href");
			if(url.contains(".css")){
				url=url.substring(0,url.indexOf(".css")+4);
			}else{
				continue;
			}
			if(url.startsWith("/")){
				url=host+url;
			}else if(url.startsWith("http")){
				
			}else{
				url=host+"/"+url;
			}
			if(ParseOnePage.cssjsMap.containsKey(url)){
				String tempurl=url.replace(host, "");
				element.attr("href", tempurl.substring(1,tempurl.length()));
			}
		}
	}
	
	
	private static String getHost(String url){
		Pattern pattern = Pattern.compile("http\\://.*?(?=/)");
		Matcher matcher = pattern.matcher(url);
		String host=null;
		if(matcher.find()){
			host=matcher.group(0);
		}
		return host;
	}
	
   public static void refreshFileList(String strPath) { 
        File dir = new File(strPath); 
        File[] files = dir.listFiles();   
        if (files == null) 
            return; 
        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
                refreshFileList(files[i].getAbsolutePath()); 
            } else { 
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                if(strFileName.endsWith(".html"))fileList.add(files[i].getAbsolutePath());                    
            } 
        } 
    }
}

