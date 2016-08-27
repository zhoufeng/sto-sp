package com.shenma.top.imagecopy.controller.rmi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class FetchCateHtmlServiceUtil{
	protected static Logger logger = Logger.getLogger("CustomerHttpClient");
	public static long startTime=0L;
	public static long catetartTime=0L;
	private byte[] lock = new byte[0]; // 特殊的instance变量
	private static Map<String,String> cookies=new HashMap<String, String>();
	//public static String cookieValue="userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard_tmp_ck_0=\"YRLVkqQedTzKP6RahWX6RFH54AmU%2B89PttKjXAZ0G%2ByU8mNkZ6cWq8Mcd2L%2FXR%2FdJJrfdNEqRufU3V1oVEN7r4QS1ve5QfCKyoVL%2BX0IECPPjuusphQvTsSu68SLHPbA5GowmnL7WTjCsR60E8sGpzAC4zwWo%2BUdchtjP92feSXNyfKGlLm6VeO312PNDYaj27TXlYKiMO42Um19eNeFYdKH%2BmghULhw6K%2FsqgoH%2B0VvtZ%2B7wfwyHZOxR0GKI3J92Akwia9QbWlbgHq2CuAq1CgUf%2BBs5FKo7qgZCYaqHFWzTIZWWHglUY8wfcV8b%2B%2FXBmpyYgtq8QwaIh13q4TQcKGaCQFlLqwBFEiyL52AI%2BwgE5jTF19ATzodun4ZPaGpTN7b8vb5V0cJyYW2%2F%2BNKG8sa2T2LuHmA0%2B%2BukDQuXn7%2Fm8QXkqEr9iUgeJ0c%2F1HkMs9Jxy5FRz5pwOLVB7xuhC9AZlRT06GdYj8NnZgrsG7rtbO7KPFBASXjoXjfNEhs\";Version=1;Path=/;Domain=.1688.com;Discard_nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard";
	public static String cookieValue="ali_beacon_id=117.136.13.51.1457585905626.827514.9; cna=8exnDyQO7nkCAXWIDTN2nPq1; isg=BDF530B6E2A976B0EA93D927C4E2445A; l=AsfHKJl5p8EghaPp3CcFdvi8d3GRiJuu; alicnweb=touch_tb_at%3D1458606564266%7Clastlogonid%3Dalitestforisv01%7Cshow_inter_tips%3Dfalse; __last_loginid__=alitestforisv01; ali_apache_track=\"c_ms=2|c_mt=3|c_mid=b2b-1623492085|c_lid=alitestforisv01\"; _cn_slid_=\"Yw77U%2Faevo\"; last_mid=b2b-1623492085; __utma=62251820.1445308915.1457746361.1457746361.1457844195.2; __utmz=62251820.1457844195.2.2.utmcsr=view.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/cms/itbu/app/list.html; ali_ab=115.219.33.106.1457795959128.4; ad_prefer=\"2016/03/21 10:51:26\"; h_keys=\"%u8fde%u8863%u88d9\"; JSESSIONID=8L78Dvtu1-emNVsIhHsGBmaBtJv4-E7Wi5gP-U73; _tmp_ck_0=\"zR25iojv9aVoDXnzTTKje92CpG%2BXQF8o9b3O6cuYN9iXuwz60LCAUgc33YL14w45WfZgynBHU1FevKGPopePVq%2FIw65BptWl0jfpndGZDIrYnscYKPC1Y4QhHlUMok85fWawfQvwKPkPmB2tPvwNZC3k0sTqW2FNsJ2dRoGFNlY3K%2FR%2BxAYT2Ft5%2BXVxESMoi1LyouYuzE8cpnXiyRfIYTUwXQJz%2FiPX9uOTOhb3seDucwcwWgwHyBbRlhoU80JDxief4veLtcEESIjpa5gwZQMiOkWBVzTpeYCzkr38aBpKM4Nfo2xLClVVdfpHxx%2B7I8KDy3FE6k80VhFHpFSNEWiI7TrS8K20XhsebLzQpudo6rmgV68WVgy5FM38aTnslatGRDEKmQWtbMyfIFaihTc4n88qt478vcaI6QM3CUMKlRmXLbqAabCWWsY9QCEaS6whGlAVv5VI%2Bk%2FS8CJlFwO181Pu3DkMj0XUf2uK36O8QVIQ5L08dlxgEP8P9KMxrfAApeVonBubi1sfXEIMUAXsNEbU4WiGFCJiOcz4g0MJrP%2BpQIrT1Q%3D%3D\"; __cn_logon__=true; ali_apache_tracktmp=\"c_w_signed=Y\"; _nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\"; tbsnid=p4xdaSmJqpjL95D14QgcKfTdKWJ4YTf04C5UkIjn1is6sOlEpJKl9g%3D%3D; LoginUmid=\"5bKWLcinI%2B2job4ZN69p4qItjQR8EGTsNgTc5BFMqKO%2BkczvUn3C%2FQ%3D%3D\"; userID=\"ErBDlm7903Usue5a710T1MglkS5l96nSeVZxwVRavgo6sOlEpJKl9g%3D%3D\"; userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1458606596175; __cn_logon_id__=alitestforisv01; cn_tmp=\"Z28mC+GqtZ3NJkA74i0LmXwopqGKU5eX6yNpIAx4DSx6XVYZ9mTMMokyj8CZcUy5Mpw8PqPyRGsx1UxxeZdVtrTPdlSZO4hLFX01xCTnnSqJVpmDP4rVmDify7NzfN7bucliMnNxM0S1MpPbzvvkOsne23a1POvEwo09gj68qvk/KcBRL1CagvROwkFdEiOM3ws2FTJnWx9Adbe0BMEm5UCXbgwaG1plLmiXjUadn/mbb8t7E8h4HyD+9VtS9M+K\"; unb=1623492085; _is_show_loginId_change_block_=b2b-1623492085_false; _show_force_unbind_div_=b2b-1623492085_false; _show_sys_unbind_div_=b2b-1623492085_false; _show_user_unbind_div_=b2b-1623492085_false; __rn_alert__=false";
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
	public String fetchDetailPage(String url) {
		String html=null;
		try {
			html=getAliCateDefaultConnet(url).html();
			System.out.println(html);
		} catch (Exception e) {
			logger.error("请求阿里详情页报错:",e);
		}
		return html;
	}
	
	
	
	private Document getAliCateDefaultConnet(String url) throws IOException, InterruptedException{
		Document doc = null;
		synchronized (lock) {
			long endTime=System.currentTimeMillis();
			if(endTime-catetartTime>=1001){
				
			}else{
				Thread.sleep(1001-endTime+catetartTime);
			}
			catetartTime=endTime;
			doc = Jsoup.connect(url).cookies(cookies).timeout(20000).get();
		}
		
		return doc;
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		FetchCateHtmlServiceUtil imp=new FetchCateHtmlServiceUtil();
		Document doc=imp.getAliCateDefaultConnet("https://qysq1688.1688.com/page/offerlist.htm?spm=a261y.7663282.0.0.B7M2dt");
		System.out.println(doc.html());
	}




	
}

