package com.bohusoft.alicopy.test.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohusoft.alicopy.test.common.BaseJUnit4Test;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.fetchhtml.FetchHtmlUtil;
import com.shenma.top.imagecopy.util.fetchhtml.FetchParamsBean;

public class FetchHtmlUtilTest extends BaseJUnit4Test {
	//public static String cookieValue="	ali_beacon_id=121.235.245.110.1440829590888.710740.7; isg=102F923D8EFEF30978413AA7A004A74F; l=AubmT/IJpaKJWhUPznzUx4AuFlZoxyqB; cna=lj5oDigy1QsCAXrr9W7RVdWe; ali_ab=115.197.184.89.1440912718303.2; ali_apache_track=\"c_ms=2|c_mt=3|c_mid=b2b-1623492085|c_lid=alitestforisv01\"; _cn_slid_=\"Yw77U%2Faevo\"; __last_loginid__=alitestforisv01; alicnweb=touch_tb_at%3D1455330541810%7Clastlogonid%3Dalitestforisv01%7Cshow_inter_tips%3Dfalse; ad_prefer=\"2016/01/08 21:44:34\"; h_keys=\"%u5218%u548c%u5e73#1305252#%u9ad8%u7aef%u871c%u6a59%u8272%u6c34%u8c82%u7ed2%u6bdb%u8863%u52a0%u539a%u53cc%u5c42%u534a%u9ad8%u9886%u8c82%u7ed2%u886b%u4e2d%u957f%u6b3e%u5bbd%u677e%u5f00%u53c9%u6bdb%u8863%u5973#%u9488%u7ec7%u886b#%u6587%u80f8#%u4fdd%u6e29%u76d2#%u7fbd%u7ed2%u670d#%u7ae5%u8f66#%u7eaf%u68c9%u6bdb%u5dfe#%u7529%u8102%u673a\"; __utma=62251820.1455606200.1441097959.1452647969.1453184421.29; __utmz=62251820.1453184421.29.23.utmcsr=view.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/cms/itbu/app/list.html; last_mid=b2b-1623492085; JSESSIONID=8L78B8bv1-WB3Vovz04XnTLNcIXE-XFccYcP-9NnE; _csrf_token=1455330536142; _tmp_ck_0=\"GDnm0JftrAB0AgH%2BT9ti15XPDsWkMqb89b6j2014QcVqGczsSu%2FNtfzLSZVq2HmanBgBvfR7qWOTBF4pto9yIx5F98RUMz2Ec6uaGBrWH1X2IkicRq54mdFAu%2FwzE1ONdI%2Bhp68LMFVspeaf%2FJEslAJkyAattVDTAJO1b8G1aQEJlkjVdOJCv0DU9broZXQynIokM%2BrUicdkub9QzC477ecAY%2BU0C0gkBB1KKWns%2F%2BB%2FMUT9kIPdqWR6TSetXXtxwRcEa6AnyIIsMe8v7hElxPNnX85gizX2EAgIvNolnLKgNCiTZxgloRn1NGHQ7DNU5mgD6xta%2BzvrH7I1EHIduA%3D%3D\"";
	public static String cookieValue="	ali_beacon_id=117.136.13.51.1457585905626.827514.9; cna=8exnDyQO7nkCAXWIDTN2nPq1; isg=940EA2A26BD74652E3C9F18D2C1F49CE; l=AkxMHEiLjABnTBhYAz7elAWCPKB-9PAv; alicnweb=touch_tb_at%3D1458783787742%7Clastlogonid%3Dalitestforisv01%7Cshow_inter_tips%3Dfalse; __last_loginid__=alitestforisv01; ali_apache_track=\"c_ms=2|c_mt=3|c_mid=b2b-1623492085|c_lid=alitestforisv01\"; _cn_slid_=\"Yw77U%2Faevo\"; last_mid=b2b-1623492085; __utma=62251820.1445308915.1457746361.1458715322.1458723779.5; __utmz=62251820.1458715322.4.4.utmcsr=login.1688.com|utmccn=(referral)|utmcmd=referral|utmcct=/member/marketSigninJump.htm; ali_ab=115.219.33.106.1457795959128.4; ad_prefer=\"2016/03/22 21:58:20\"; h_keys=\"%u7eaf%u68c9T%u6064#%u8fde%u8863%u88d9\"; JSESSIONID=8L78mSbv1-fiNV8uykM5gCC9FEwA-OYpJKgP-uzb5; _tmp_ck_0=\"a1EzlFhlV1hD6Zm3cWF9hxcVGtxQ%2BjySDr9%2FLchIkh2pJIwS8xiWcsw9Iek5rqzw0jt23mLsLb0dbDZ6Dzmst5es%2FiJ52TA48J08EQRDnyDETPYYESkJNgrc0kzWSl%2B5%2BUfi0XwUVFodOlWFLwduC4FyRA8KBnk3R1%2Flel6dSBpqnKgKO9nGxqyesON9gJYQgaR4GNhr9ibK7mM5slEw8KjwvmWUmkw4Dw3mPzYprdZeZB4cm%2Fi0pougY%2B%2BeDN8P8Tw2hSfM%2FHaJxqHplap7OK7%2FTr7r8AXgVpCC692E%2BdBt%2FcR2j1vB273CgLsIpiOES6uPeyqmPpj4WWmlfpH%2FDZ4UYEqBR14kM3jRX7nPvYINwkwxX3QJP5jyM9y5V2zSQsgZBNw9eC23JdO0ybCtNWx8riFD9%2Fez8ljGe2AWzaw8sXwW4BtX%2B4UrhVcWrG6ti8Mctv%2BC%2Bqn22ICDiIlLcyIasf4nB%2FqwjF%2BbXOHbyy9O8wE6VRdh17WtvcLZeImlXi11uBV%2FoahwZGAUudumTd6YSMoAn2MmiN7GRh93hRI7DvTNamk%2Bgw%3D%3D\"; __cn_logon__=true; __cn_logon_id__=alitestforisv01; ali_apache_tracktmp=\"c_w_signed=Y\"; cn_tmp=\"Z28mC+GqtZ3NJkA74i0LmXwopqGKU5eX6yNpIAx4DSx6XVYZ9mTMMokyj8CZcUy5Mpw8PqPyRGtZuErsqbv7hUwUrq32K08M8g4+IIJXekhi70pjSDxKE6MHbVZpuH90up+ePkfeO1R3td5/A6V471Ty+QcnnxpHX7Fl2XlKpDlahJCsYsAw+AYm5F0KTAoaXOZTCPiQ+3WaWJqgdNIaNM5lUjcIBRqQF6xnLu6xJxUeTPDm/Kb/icjiZZIs3cGG\"; _nk_=\"LqvxMzrmw3PVQ3A%2BS4htCA%3D%3D\"; tbsnid=C3D60OguuYmmGjdqA1js0bNtc5ZozCOArtmluODD3sY6sOlEpJKl9g%3D%3D; LoginUmid=\"5bKWLcinI%2B2job4ZN69p4qItjQR8EGTsNgTc5BFMqKO%2BkczvUn3C%2FQ%3D%3D\"; userID=\"ErBDlm7903Usue5a710T1MglkS5l96nSeVZxwVRavgo6sOlEpJKl9g%3D%3D\"; unb=1623492085; userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\"; login=\"kFeyVBJLQQI%3D\"; _csrf_token=1458783730810; _is_show_loginId_change_block_=b2b-1623492085_false; _show_force_unbind_div_=b2b-1623492085_false; _show_sys_unbind_div_=b2b-1623492085_false; _show_user_unbind_div_=b2b-1623492085_false; __rn_alert__=false";
	//public static String cookieValue="userIDNum=\"SMpaEUPYMU5DvHK82Tv9Og%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard_tmp_ck_0=\"Z1OqnYNIUhNo8dB2P1Py0nY1GcEU26vPOYqKAh6LXMpnq7KpJGfsQJHkt%2BJeNih6AjseOVTtjvn52lkqhMi6YIlJ%2Bc0%2BkqbCvLe%2FWuAI5xzKGMnF0jolrHD0rWGAkdFRLclxRdC5ysGn3LUrWPIBLputpc85RaNqCKs1hRzUFDLz%2F8X6Clz5syR8O%2FwHw8RoIQrtHE%2BVnqQoCrxIvOk4efX74aYhMyhsOZeJYv3BSzWdlKQ5xIKTW9OXE5AKfeA6IDIYFYdr6Q8l18e6PDZkzsWPiSF%2FtVuAsnwJOE7VUWQaRPOUuzaAsvqXMjNw6YFim9XlKY9cOif%2FncfhOhpLq25J8o7Ci1S7g%2FRktKDW7MAuCOfk%2BFyfTHL7vLmmIWQY2nsdzkMtzfeQX%2FqX6%2FWT79270GeDLCizgiFdqmwmZaDDxH25AUOAY1S82dhm%2FtjOK4aA0pFEiDvqQ%2F10M3Lm7NP4f3xGDbBbX5E39e5xwtKuIYi40%2BHGk0S8XA%2BdSHv4ZCGIUGOF001CAQ1cCzM5%2FI01tMXY3jbGRHkVVKC4andtJh4ZPAUkYQ%3D%3D\";Version=1;Path=/;Domain=.1688.com;Discard";
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
	@Autowired
	private FetchHtmlUtil fetchHtmlUtil;
	
	private FetchParamsBean paramsBean;
	
	@Before
	public void setConfig(){
		paramsBean=fetchHtmlUtil.getParamsBean();
	}
	
	@Test
	public void testGetDetailHtmlForAlibaba() throws IOException, InterruptedException{		
		String url="http://detail.1688.com/offer/521647808949.html";
		String html=fetchHtmlUtil.getDetailHtml(url);
		String validateStr="亲，慢慢来，请先坐下来喝口水";
		Assert.assertFalse("访问被拦截!", html.indexOf(validateStr)>-1);
	}
	@Test
	public void testGetDetailHtmlForTaobao() throws IOException, InterruptedException{		
		String url="https://item.taobao.com/item.htm?id=521448644215";
		String html=fetchHtmlUtil.getDetailHtml(url);
		Document doc=Jsoup.parse(html);
		String title=doc.title();
		Assert.assertTrue(title.endsWith("-淘宝网"));
	}
	@Test
	public void testGetDetailHtmlForTmail() throws IOException, InterruptedException{		
		String url="https://detail.tmall.com/item.htm?id=45688799213";
		String html=fetchHtmlUtil.getDetailHtml(url);
		Document doc=Jsoup.parse(html);
		String title=doc.title();
		Assert.assertTrue(title.endsWith("天猫"));
	}
	public static void main(String[] args) throws IOException {
		//String url="https://item.taobao.com/item.htm?id=527513921822";
		String url="https://tianchaotile.taobao.com/search.htm";
		//String url="https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.6.3NHUm2&id=44467406204&skuId=86056295862&areaId=310100&cat_id=50025145&rn=d577b459c98b888fbff0b2f105429d5a&user_id=420567757&is_b=1";
		//String url="https://mdskip.taobao.com/core/initItemDetail.htm?cachedTimestamp=1458165523777&addressLevel=2&isUseInventoryCenter=false&cartEnable=true&isSecKill=false&isRegionLevel=false&tryBeforeBuy=false&sellerPreview=false&household=false&showShopProm=false&progressiveSupport=false&itemId=44467406204&queryMemberRight=true&isAreaSell=false&isApparel=true&offlineShop=false&service3C=false&tmallBuySupport=true&isForbidBuyItem=false&callback=setMdskip&timestamp=1458173275473&isg=AtXVC2qy2PrR8Plj1QnXFS/0xduP4Ink&areaId=310100&cat_id=50025145&ref=https%3A%2F%2Flist.tmall.com%2Fsearch_product.htm%3Fspm%3Da21bo.7724922.8452-tmallHotWords.1.b1HvFZ%26sourceId%3Dtb.index%26commend%3Dall%26q%3D%25E8%25BF%259E%25E8%25A1%25A3%25E8%25A3%2599%26from%3Dtbmain_1.0_hq";
		String html =Jsoup.connect(url).cookies(cookies).referrer("https://shop1348592820153.1688.com/")
				.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0").ignoreContentType(true)
				.timeout(20000).get().html(); 
		//String validateStr="亲，慢慢来，请先坐下来喝口水";
		System.out.println(html);		
		String validateStr="对不起，系统繁忙，请提交验证码后继续";
		if(html.indexOf(validateStr)>-1){
			System.out.println("您已经被拦截了！！！！！！！！！！");
		}
		Assert.assertFalse("访问被拦截!", html.indexOf(validateStr)>-1);
	}
}
