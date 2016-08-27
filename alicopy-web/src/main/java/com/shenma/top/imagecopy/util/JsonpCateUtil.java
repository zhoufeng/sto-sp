package com.shenma.top.imagecopy.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsonpCateUtil {
	protected static Logger logger = Logger.getLogger("JsonpCateUtil");
	public static long startTime=0L;
	public static String cookieValue="userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard_tmp_ck_0=\"YRLVkqQedTzKP6RahWX6RFH54AmU%2B89PttKjXAZ0G%2ByU8mNkZ6cWq8Mcd2L%2FXR%2FdJJrfdNEqRufU3V1oVEN7r4QS1ve5QfCKyoVL%2BX0IECPPjuusphQvTsSu68SLHPbA5GowmnL7WTjCsR60E8sGpzAC4zwWo%2BUdchtjP92feSXNyfKGlLm6VeO312PNDYaj27TXlYKiMO42Um19eNeFYdKH%2BmghULhw6K%2FsqgoH%2B0VvtZ%2B7wfwyHZOxR0GKI3J92Akwia9QbWlbgHq2CuAq1CgUf%2BBs5FKo7qgZCYaqHFWzTIZWWHglUY8wfcV8b%2B%2FXBmpyYgtq8QwaIh13q4TQcKGaCQFlLqwBFEiyL52AI%2BwgE5jTF19ATzodun4ZPaGpTN7b8vb5V0cJyYW2%2F%2BNKG8sa2T2LuHmA0%2B%2BukDQuXn7%2Fm8QXkqEr9iUgeJ0c%2F1HkMs9Jxy5FRz5pwOLVB7xuhC9AZlRT06GdYj8NnZgrsG7rtbO7KPFBASXjoXjfNEhs\";Version=1;Path=/;Domain=.1688.com;Discard_nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard";
	//public static String cookieValue="	ali_beacon_id=121.235.245.110.1440829590888.710740.7; isg=102F923D8EFEF30978413AA7A004A74F; l=AubmT/IJpaKJWhUPznzUx4AuFlZoxyqB; cna=lj5oDigy1QsCAXrr9W7RVdWe; ali_ab=115.197.184.89.1440912718303.2; ali_apache_track=\"c_ms=2|c_mt=3|c_mid=b2b-1623492085|c_lid=alitestforisv01\"; _cn_slid_=\"Yw77U%2Faevo\"; __last_loginid__=alitestforisv01; alicnweb=touch_tb_at%3D1455330541810%7Clastlogonid%3Dalitestforisv01%7Cshow_inter_tips%3Dfalse; ad_prefer=\"2016/01/08 21:44:34\"; h_keys=\"%u5218%u548c%u5e73#1305252#%u9ad8%u7aef%u871c%u6a59%u8272%u6c34%u8c82%u7ed2%u6bdb%u8863%u52a0%u539a%u53cc%u5c42%u534a%u9ad8%u9886%u8c82%u7ed2%u886b%u4e2d%u957f%u6b3e%u5bbd%u677e%u5f00%u53c9%u6bdb%u8863%u5973#%u9488%u7ec7%u886b#%u6587%u80f8#%u4fdd%u6e29%u76d2#%u7fbd%u7ed2%u670d#%u7ae5%u8f66#%u7eaf%u68c9%u6bdb%u5dfe#%u7529%u8102%u673a\"; __utma=62251820.1455606200.1441097959.1452647969.1453184421.29; __utmz=62251820.1453184421.29.23.utmcsr=view.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/cms/itbu/app/list.html; last_mid=b2b-1623492085; JSESSIONID=8L78B8bv1-WB3Vovz04XnTLNcIXE-XFccYcP-9NnE; _csrf_token=1455330536142; _tmp_ck_0=\"GDnm0JftrAB0AgH%2BT9ti15XPDsWkMqb89b6j2014QcVqGczsSu%2FNtfzLSZVq2HmanBgBvfR7qWOTBF4pto9yIx5F98RUMz2Ec6uaGBrWH1X2IkicRq54mdFAu%2FwzE1ONdI%2Bhp68LMFVspeaf%2FJEslAJkyAattVDTAJO1b8G1aQEJlkjVdOJCv0DU9broZXQynIokM%2BrUicdkub9QzC477ecAY%2BU0C0gkBB1KKWns%2F%2BB%2FMUT9kIPdqWR6TSetXXtxwRcEa6AnyIIsMe8v7hElxPNnX85gizX2EAgIvNolnLKgNCiTZxgloRn1NGHQ7DNU5mgD6xta%2BzvrH7I1EHIduA%3D%3D\"";
	
	private static Map<String,String> cookies=new HashMap<String, String>();
	static{
		//String[] arr=AliCateAuto2Util.cookieValue.split(";");
		String[] arr=cookieValue.split(";");
		for(String cookiestr:arr){
			String[] cookiearr=cookiestr.split("=");
			if(cookiearr.length==2){
				cookies.put(cookiearr[0], cookiearr[1]);
			}else{
				cookies.put(cookiearr[0], "");
			}
			
		}
	}
	public static synchronized Document getAliDefaultConnet(String url){
		Document doc = null;
		try {
			long endTime=System.currentTimeMillis();
			if(endTime-startTime>=1100){
				
			}else{
				Thread.sleep(1100-endTime+startTime);
			}
			startTime=endTime;
			//url+="?smToken=1f12ad24317c46f99740519faf2d7acd";
		
			doc =Jsoup.connect(url).cookies(cookies).timeout(20000).get(); 
		} catch (Exception e) {
			logger.error("请求ali页面出错,url:"+url+":"+e.getMessage());
		}
		return doc;
	}
	
}
