package com.bohusoft.htmlfetch.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FetchAliCateHtmlUtil {
	protected static Logger logger = Logger.getLogger("FetchAliCateHtmlUtil");
	public static long startTime=0L;
	public static long catetartTime=0L;
	private static byte[] lock = new byte[0]; // 特殊的instance变量
	private static Map<String,String> cookies=new HashMap<String, String>();
	//public static String cookieValue="userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard_tmp_ck_0=\"YRLVkqQedTzKP6RahWX6RFH54AmU%2B89PttKjXAZ0G%2ByU8mNkZ6cWq8Mcd2L%2FXR%2FdJJrfdNEqRufU3V1oVEN7r4QS1ve5QfCKyoVL%2BX0IECPPjuusphQvTsSu68SLHPbA5GowmnL7WTjCsR60E8sGpzAC4zwWo%2BUdchtjP92feSXNyfKGlLm6VeO312PNDYaj27TXlYKiMO42Um19eNeFYdKH%2BmghULhw6K%2FsqgoH%2B0VvtZ%2B7wfwyHZOxR0GKI3J92Akwia9QbWlbgHq2CuAq1CgUf%2BBs5FKo7qgZCYaqHFWzTIZWWHglUY8wfcV8b%2B%2FXBmpyYgtq8QwaIh13q4TQcKGaCQFlLqwBFEiyL52AI%2BwgE5jTF19ATzodun4ZPaGpTN7b8vb5V0cJyYW2%2F%2BNKG8sa2T2LuHmA0%2B%2BukDQuXn7%2Fm8QXkqEr9iUgeJ0c%2F1HkMs9Jxy5FRz5pwOLVB7xuhC9AZlRT06GdYj8NnZgrsG7rtbO7KPFBASXjoXjfNEhs\";Version=1;Path=/;Domain=.1688.com;Discard_nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard";
	public static String cookieValue="	ali_beacon_id=117.136.13.51.1457585905626.827514.9; cna=8exnDyQO7nkCAXWIDTN2nPq1; isg=940EA2A26BD74652E3C9F18D2C1F49CE; l=AkxMHEiLjABnTBhYAz7elAWCPKB-9PAv; alicnweb=touch_tb_at%3D1458783787742%7Clastlogonid%3Dalitestforisv01%7Cshow_inter_tips%3Dfalse; __last_loginid__=alitestforisv01; ali_apache_track=\"c_ms=2|c_mt=3|c_mid=b2b-1623492085|c_lid=alitestforisv01\"; _cn_slid_=\"Yw77U%2Faevo\"; last_mid=b2b-1623492085; __utma=62251820.1445308915.1457746361.1458715322.1458723779.5; __utmz=62251820.1458715322.4.4.utmcsr=login.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/member/marketSigninJump.htm; ali_ab=115.219.33.106.1457795959128.4; ad_prefer=\"2016/03/22 21:58:20\"; h_keys=\"%u7eaf%u68c9T%u6064#%u8fde%u8863%u88d9\"; JSESSIONID=8L78mSbv1-fiNV8uykM5gCC9FEwA-OYpJKgP-uzb5; _tmp_ck_0=\"a1EzlFhlV1hD6Zm3cWF9hxcVGtxQ%2BjySDr9%2FLchIkh2pJIwS8xiWcsw9Iek5rqzw0jt23mLsLb0dbDZ6Dzmst5es%2FiJ52TA48J08EQRDnyDETPYYESkJNgrc0kzWSl%2B5%2BUfi0XwUVFodOlWFLwduC4FyRA8KBnk3R1%2Flel6dSBpqnKgKO9nGxqyesON9gJYQgaR4GNhr9ibK7mM5slEw8KjwvmWUmkw4Dw3mPzYprdZeZB4cm%2Fi0pougY%2B%2BeDN8P8Tw2hSfM%2FHaJxqHplap7OK7%2FTr7r8AXgVpCC692E%2BdBt%2FcR2j1vB273CgLsIpiOES6uPeyqmPpj4WWmlfpH%2FDZ4UYEqBR14kM3jRX7nPvYINwkwxX3QJP5jyM9y5V2zSQsgZBNw9eC23JdO0ybCtNWx8riFD9%2Fez8ljGe2AWzaw8sXwW4BtX%2B4UrhVcWrG6ti8Mctv%2BC%2Bqn22ICDiIlLcyIasf4nB%2FqwjF%2BbXOHbyy9O8wE6VRdh17WtvcLZeImlXi11uBV%2FoahwZGAUudumTd6YSMoAn2MmiN7GRh93hRI7DvTNamk%2Bgw%3D%3D\"; __cn_logon__=true; __cn_logon_id__=alitestforisv01; ali_apache_tracktmp=\"c_w_signed=Y\"; cn_tmp=\"Z28mC+GqtZ3NJkA74i0LmXwopqGKU5eX6yNpIAx4DSx6XVYZ9mTMMokyj8CZcUy5Mpw8PqPyRGtZuErsqbv7hUwUrq32K08M8g4+IIJXekhi70pjSDxKE6MHbVZpuH90up+ePkfeO1R3td5/A6V471Ty+QcnnxpHX7Fl2XlKpDlahJCsYsAw+AYm5F0KTAoaXOZTCPiQ+3WaWJqgdNIaNM5lUjcIBRqQF6xnLu6xJxUeTPDm/Kb/icjiZZIs3cGG\"; _nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\"; tbsnid=C3D60OguuYmmGjdqA1js0bNtc5ZozCOArtmluODD3sY6sOlEpJKl9g%3D%3D; LoginUmid=\"5bKWLcinI%2B2job4ZN69p4qItjQR8EGTsNgTc5BFMqKO%2BkczvUn3C%2FQ%3D%3D\"; userID=\"ErBDlm7903Usue5a710T1MglkS5l96nSeVZxwVRavgo6sOlEpJKl9g%3D%3D\"; unb=1623492085; userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1458783730810; _is_show_loginId_change_block_=b2b-1623492085_false; _show_force_unbind_div_=b2b-1623492085_false; _show_sys_unbind_div_=b2b-1623492085_false; _show_user_unbind_div_=b2b-1623492085_false; __rn_alert__=false";
	
	static{
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
	/**
	 * 阿里巴巴搜索请求
	 * @param url
	 * @param searchHostUrl
	 * @return
	 * @throws InterruptedException
	 */
	private static String getRmiQiliCateHtml(String url,String searchHostUrl,Integer time) throws InterruptedException{
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
	
	public static String fetchDetailPage(String url,String searchHostUrl,Integer aliSearchType,Integer time) {
		String html=null;
		try {
			if(aliSearchType==1){
				html=getAliCateDefaultConnet(url,time).html();
			}else{
				html=getRmiQiliCateHtml(url, searchHostUrl,time);		
			}
		} catch (Exception e) {
			logger.error("请求阿里列表页报错:"+url,e);
		}
		return html;
	}
	public static Document getAliCateDefaultConnet(String url,Integer time) throws IOException, InterruptedException{
		Document doc = null;
		synchronized (lock) {
			long endTime=System.currentTimeMillis();
			if(endTime-catetartTime>=time){
				
			}else{
				Thread.sleep(time-endTime+catetartTime);
			}
			catetartTime=endTime;
			doc = Jsoup.connect(url).cookies(cookies).timeout(20000).get();
		}
		
		return doc;
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		String url="https://warmbook.1688.com/page/offerlist.htm";
		String html=FetchAliCateHtmlUtil.getAliCateDefaultConnet(url,2000).html();
	}
}
