package com.bohusoft.alicopy.test.asynsave;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohusoft.alicopy.test.common.BaseJUnit4Test;
import com.shenma.top.imagecopy.service.AliForeBackSevice;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;

public class SaveAliForeBackServiceTest extends BaseJUnit4Test{
	
	@Autowired
	private AliForeBackSevice aliForeBackSevice;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveAliItem() throws JsonParseException, JsonMappingException, IOException, InterruptedException{
		String[] urls=new String[]{"http://detail.1688.com/offer/525173062393.html",
				"http://detail.1688.com/offer/525125853026.html?spm=a261y.7663282.0.0.AUALNt",
				"http://detail.1688.com/offer/525123757274.html?spm=a261y.7663282.0.0.AUALNt"};
		String varstr="{\"freightType\":\"D\",\"sendGoodsAddressId\":\"5458348\",\"priceType\":\"a\",\"mixWholeSale\":false,\"pictureAuthOffer\":false,\"priceAuthOffer\":false,\"otherHref\":false,\"expireed\":false,\"titleReplace\":false,\"titleDelete\":false,\"contentReplace\":false,\"contentDelete\":false,\"customerCate\":false,\"ignoreType\":false,\"ignoreTypeVal\":\"H\",\"extra\":{},\"url\":\"http://detail.1688.com/offer/521647808949.html\",\"picStatus\":true}";
		Map<String,Object> variables=JacksonJsonMapper.getInstance().readValue(varstr,HashMap.class);
		aliForeBackSevice.saveAliItem(variables,false);
		while(true){
			Thread.sleep(10000);
		}
		
	}
	
	
}
