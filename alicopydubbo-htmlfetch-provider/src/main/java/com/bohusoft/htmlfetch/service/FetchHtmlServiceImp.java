package com.bohusoft.htmlfetch.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.bohusoft.dubboapi.service.FetchHtmlService;
import com.bohusoft.htmlfetch.util.HttpClientUtil;

@Service
public class FetchHtmlServiceImp implements FetchHtmlService {
	protected static Logger logger = Logger.getLogger("FetchHtmlServiceImp");
	public static long startTime=0L;
	public static long catetartTime=0L;
	private static Map<String,String> cookies=new HashMap<String, String>();
	public static String cookieValue="ali_beacon_id=121.41.168.177.1421382629718.065135.1; cna=0YE/DZgoYSACAXkpqLGS0zBs; ali_apache_track=\"c_ms=1|c_mt=3|c_mid=b2b-2484570957|c_lid=%E9%A1%BA%E5%8F%91%E9%93%9C%E4%B8%9A888\"; _cn_slid_=xdSDct4CwD; last_mid=b2b-2484570957; __last_loginid__=\"%E9%A1%BA%E5%8F%91%E9%93%9C%E4%B8%9A888\"; ali_ab=121.41.168.177.1422671083582.3; alicnweb=touch_tb_at%3D1456403370969%7Clastlogonid%3D%25E9%25A1%25BA%25E5%258F%2591%25E9%2593%259C%25E4%25B8%259A888%7Cwork2015_welcome_end%3DY; l=AuPj1wTV3BF/Hh39mJX5vF2TUyyN2Hca; CNZZDATA1253659577=1123984815-1449562186-http%253A%252F%252Falisec.1688.com%252F%7C1456398321; sec=56cef39f120d4ff5445702a9ab7e89cad82d63d9; isg=9BEEF767313871A1275495014EFECD32; JSESSIONID=8L7844uu1-N0DVHzhJqwS7GuCfKA-qgWEjdP-G3mG; _csrf_token=1456403467248; _tmp_ck_0=\"AhAPwLGenDvWkaiBDceLNU9oisaaNE1w2syrkM%2BSwWLjfPitGtAOLU4j%2FmAsNTacIWZCtck1YCCeOI8SmHFHVodpbOr5ovnkdfjegD5oM7AiacO61pkr9kCfXNH3%2F2OKbVaViyxjHfJWqTyqY07vB0nHOoROwUvmbnW2Pa7GhvpHJioHNamL%2FDQ56KwMhXNbrXnLrKXI6Zc%2FqCVE9hRqn8Ts82uI1D2Y5jv%2FB77o1%2FHQv08RzC%2BPc6QT9rDMnU2eZmL37%2FGrHIzRyWkYCcDcMcrTcEgt%2FSKwRZOjAG2akXwJxK6uj5aJWB7Q9QPIFcaCIqtJYM4rK8FcuSnB6wm8Jg%3D%3D\"";
	
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
			html=getAliDefaultConnet(url).html();
			//html=BaseHttpClient.get(url, "gbk");
			//html=HttpClientUtil.getDetailHtml(url);
		} catch (Exception e) {
			logger.error("请求阿里详情页报错:",e);
		}
		return html;
	}
	
	
	public static synchronized Document getAliDefaultConnet(String url) throws IOException, InterruptedException{
		long endTime=System.currentTimeMillis();
		if(endTime-startTime>=450){
			
		}else{
			Thread.sleep(450-endTime+startTime);
		}
		startTime=endTime;
		Document doc = Jsoup.connect(url).timeout(20000).get();
		return doc;
	}
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Document doc=getAliDefaultConnet("http://detail.1688.com/offer/40463841126.html");
		System.out.println(doc.html());
	}



	
}
