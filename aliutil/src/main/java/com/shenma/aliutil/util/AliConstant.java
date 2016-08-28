package com.shenma.aliutil.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.entity.cate.FeatureIdValue;
import com.shenma.aliutil.entity.cate.Postatrribute;
import com.shenma.aliutil.entity.goods.OfferFeature;
import com.shenma.aliutil.entity.goods.OfferImageInfo;
import com.shenma.aliutil.entity.goods.Sku;
import com.shenma.aliutil.entity.platform.IsvOrderItemDto;
import com.shenma.aliutil.entity.wuliu.DeliveryRateDescn;
import com.shenma.aliutil.entity.wuliu.DeliveryTemplateDescn;
import com.shenma.aliutil.entity.wuliu.SubTemplateDescn;

@Component
public class AliConstant implements InitializingBean {
	
	public final static String ali_info_name="aliInfo";
	
	@Value("#{aliUtilConfig['ali_memcache_url']}")
	public  String ali_memcache_url="127.0.0.1:11211";
	@Value("#{aliUtilConfig['api_url']}")
	public  String api_url = "http://gw.api.taobao.com/router/rest";
	@Value("#{aliUtilConfig['APP_KEY']}")
	public  String APP_KEY = "1013065";
	@Value("#{aliUtilConfig['APP_Secret']}")
	public  String APP_Secret = "lMb9HJfLRyWF";
	@Value("#{aliUtilConfig['redirect_uri']}")
	public  String redirect_uri = "http://www.kongjishise.com:8080/taobaoweb/login/ali";
	@Value("#{aliUtilConfig['image_uri_prefix']}")
	public  String image_uri_prefix = "http://i00.c.aliimg.com/";
	@Value("#{aliUtilConfig['proxy_url']}")
	public  static String proxy_url = "http://imagecopy.kongjishise.com/alicopy/open/api";
	
	public static String[] appKeySecrets={"1014423:GNAxz1PoIdEN"}; //宝贝一键复制,旺铺装修营销,炫酷主图,旺铺装修营销免费版
	
	public static final Map<String,AppInfo> appMap=new HashMap<String,AppInfo>();
	
	static{
		appMap.put("1014423", new AppInfo("1014423", "GNAxz1PoIdEN","宝贝一键复制", "http://imagecopy.kongjishise.com/alicopy"));
		appMap.put("1014364", new AppInfo("1014364", "4OfJ8Ot29sah","旺铺装修营销", "http://picer.kongjishise.com/picer/main/alibaba"));
		appMap.put("1017919", new AppInfo("1017919", "gqOwxsOG58QU","酷炫主图", "http://zt.kongjishise.com/picer/main/alibaba"));
		appMap.put("1018632", new AppInfo("1018632", "XYem8Wghb5W","旺铺装修营销免费版", "http://picerfree.kongjishise.com/picerfree/main/alibaba"));
		appMap.put("1019762", new AppInfo("1019762", "VzCqKtzLqe","酷炫关联", "http://gl.kongjishise.com/glpicer/main/alibaba"));
		appMap.put("1019749", new AppInfo("1019749", "PzLv0fdjUof","酷炫海报", "http://hb.kongjishise.com/hbpicer/main/alibaba"));
	}


	public void afterPropertiesSet() throws Exception {
		//ConvertUtils.register(new MapTypeConverter(),Map.class);
		setConvertUtilsReg();
		
	}
	
	private void setConvertUtilsReg(){
		ConvertUtils.register(new MyTypeConverter(), AliPage.class);
		ConvertUtils.register(new ListTypeConverter(), List.class);
		ConvertUtils.register(new DateConvert(), Date.class);
		
		ConvertUtils.register(new MyTypeConverter(), Postatrribute.class);
		ConvertUtils.register(new MyTypeConverter(), FeatureIdValue.class);
		ConvertUtils.register(new MyTypeConverter(), OfferImageInfo.class);
		ConvertUtils.register(new MyTypeConverter(), OfferFeature.class);
		ConvertUtils.register(new MyTypeConverter(), DeliveryTemplateDescn.class);
		ConvertUtils.register(new MyTypeConverter(), DeliveryRateDescn.class);
		ConvertUtils.register(new MyTypeConverter(), SubTemplateDescn.class);
		ConvertUtils.register(new MyTypeConverter(), IsvOrderItemDto.class);
		
		ConvertUtils.register(new MyTypeConverter(), Sku.class);
	}

}
