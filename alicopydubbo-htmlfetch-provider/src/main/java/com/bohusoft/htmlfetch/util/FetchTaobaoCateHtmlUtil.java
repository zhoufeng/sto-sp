package com.bohusoft.htmlfetch.util;

import org.apache.log4j.Logger;

public class FetchTaobaoCateHtmlUtil {
	protected static Logger logger = Logger.getLogger("FetchTaobaoCateHtmlUtil");
	public static long startTime=0L;
	public static long catetartTime=0L;
	private static byte[] lock = new byte[0]; // 特殊的instance变量
	
	
	public static String fetchDetailHtml(String url,String searchHostUrl,Integer time) throws InterruptedException{
		synchronized (lock) {
			long endTime=System.currentTimeMillis();
			if(endTime-catetartTime>=time){
			}else{
				Thread.sleep(time-endTime+catetartTime);
			}
			catetartTime=endTime;
		}
		return RmiQiliCateHtmlUtil.getRmi(url,searchHostUrl);	
	}
}
