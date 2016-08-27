package com.shenma.top.imagecopy.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.util.reqdubbo.HtmlCateDubboUtil;
import com.shenma.top.imagecopy.util.reqdubbo.HtmlDetailDubboUtil;

@Component
public class JsonpUtil implements ApplicationContextAware {
	protected static Logger logger = Logger.getLogger("JsonpUtil");
	public static long startTime=0L;
	private static ApplicationContext context;
	private static HtmlDetailDubboUtil detailDubboUtil;
	private static HtmlCateDubboUtil htmlCateDubboUtil ;
	//public static String cookieValue="userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard_tmp_ck_0=\"YRLVkqQedTzKP6RahWX6RFH54AmU%2B89PttKjXAZ0G%2ByU8mNkZ6cWq8Mcd2L%2FXR%2FdJJrfdNEqRufU3V1oVEN7r4QS1ve5QfCKyoVL%2BX0IECPPjuusphQvTsSu68SLHPbA5GowmnL7WTjCsR60E8sGpzAC4zwWo%2BUdchtjP92feSXNyfKGlLm6VeO312PNDYaj27TXlYKiMO42Um19eNeFYdKH%2BmghULhw6K%2FsqgoH%2B0VvtZ%2B7wfwyHZOxR0GKI3J92Akwia9QbWlbgHq2CuAq1CgUf%2BBs5FKo7qgZCYaqHFWzTIZWWHglUY8wfcV8b%2B%2FXBmpyYgtq8QwaIh13q4TQcKGaCQFlLqwBFEiyL52AI%2BwgE5jTF19ATzodun4ZPaGpTN7b8vb5V0cJyYW2%2F%2BNKG8sa2T2LuHmA0%2B%2BukDQuXn7%2Fm8QXkqEr9iUgeJ0c%2F1HkMs9Jxy5FRz5pwOLVB7xuhC9AZlRT06GdYj8NnZgrsG7rtbO7KPFBASXjoXjfNEhs\";Version=1;Path=/;Domain=.1688.com;Discard_nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard";
	public static String cookieValue="ali_beacon_id=121.41.168.177.1421382629718.065135.1; cna=0YE/DZgoYSACAXkpqLGS0zBs; ali_apache_track=\"c_ms=1|c_mt=3|c_mid=b2b-2484570957|c_lid=%E9%A1%BA%E5%8F%91%E9%93%9C%E4%B8%9A888\"; _cn_slid_=xdSDct4CwD; last_mid=b2b-2484570957; __last_loginid__=\"%E9%A1%BA%E5%8F%91%E9%93%9C%E4%B8%9A888\"; ali_ab=121.41.168.177.1422671083582.3; alicnweb=touch_tb_at%3D1456403370969%7Clastlogonid%3D%25E9%25A1%25BA%25E5%258F%2591%25E9%2593%259C%25E4%25B8%259A888%7Cwork2015_welcome_end%3DY; l=AuPj1wTV3BF/Hh39mJX5vF2TUyyN2Hca; CNZZDATA1253659577=1123984815-1449562186-http%253A%252F%252Falisec.1688.com%252F%7C1456398321; sec=56cef39f120d4ff5445702a9ab7e89cad82d63d9; isg=9BEEF767313871A1275495014EFECD32; JSESSIONID=8L7844uu1-N0DVHzhJqwS7GuCfKA-qgWEjdP-G3mG; _csrf_token=1456403467248; _tmp_ck_0=\"AhAPwLGenDvWkaiBDceLNU9oisaaNE1w2syrkM%2BSwWLjfPitGtAOLU4j%2FmAsNTacIWZCtck1YCCeOI8SmHFHVodpbOr5ovnkdfjegD5oM7AiacO61pkr9kCfXNH3%2F2OKbVaViyxjHfJWqTyqY07vB0nHOoROwUvmbnW2Pa7GhvpHJioHNamL%2FDQ56KwMhXNbrXnLrKXI6Zc%2FqCVE9hRqn8Ts82uI1D2Y5jv%2FB77o1%2FHQv08RzC%2BPc6QT9rDMnU2eZmL37%2FGrHIzRyWkYCcDcMcrTcEgt%2FSKwRZOjAG2akXwJxK6uj5aJWB7Q9QPIFcaCIqtJYM4rK8FcuSnB6wm8Jg%3D%3D\"";
	
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
	/*public static synchronized Document getAliDefaultConnet(String url){
		Document doc = null;
		try {
			long endTime=System.currentTimeMillis();
			if(endTime-startTime>=330){
				
			}else{
				Thread.sleep(330-endTime+startTime);
			}
			startTime=endTime;
			//url+="?smToken=1f12ad24317c46f99740519faf2d7acd";
		
			doc =Jsoup.connect(url).cookies(cookies).timeout(20000).get(); 
		} catch (Exception e) {
			logger.error("请求ali页面出错,url:"+url+":"+e.getMessage());
		}
		return doc;
	}*/
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context=applicationContext;
		HtmlDetailDubboUtil detailDubboUtil= (HtmlDetailDubboUtil) context.getBean("htmlDetailDubboUtil");
		HtmlCateDubboUtil htmlCateDubboUtil= (HtmlCateDubboUtil) context.getBean("htmlCateDubboUtil");
		this.detailDubboUtil=detailDubboUtil;
		this.htmlCateDubboUtil=htmlCateDubboUtil;
	}
	

	
	public static Document getAliDefaultConnet(String url){
		Document doc = null;
		try {
			String html=detailDubboUtil.fetchHtmlDetail(url);
			doc =Jsoup.parse(html);
		} catch (Exception e) {
			logger.error("请求ali页面出错,url:"+url+":"+e.getMessage());
		}
		return doc;
	}
	
	public static Document getAliCateConnet(String url){
		Document doc = null;
		try {
			String html=htmlCateDubboUtil.fetchHtmlDetail(url);
			doc =Jsoup.parse(html);
		} catch (Exception e) {
			logger.error("请求ali页面出错,url:"+url+":"+e.getMessage());
		}
		return doc;
	}
}
