package com.bohusoft.alicopy.test.parse;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bohusoft.alicopy.parse.DetailHtmlParse;
import com.bohusoft.alicopy.parse.DetailHtmlParseBean;
import com.bohusoft.alicopy.test.common.BaseJUnit4Test;
import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.fetchhtml.FetchHtmlUtil;

public class AlibabaDetailHtmlParseTest extends BaseJUnit4Test{
	
	@Autowired
	private DetailHtmlParse alibabaDetailHtmlParse;
	
	@Autowired
	private FetchHtmlUtil fetchHtmlUtil;
	
	@Test
	public void testParse() throws CopyBussinessException, JsonGenerationException, JsonMappingException, IOException{
		String url="http://detail.1688.com/offer/521647808949.html";
		DetailHtmlParseBean bean = alibabaDetailHtmlParse.parse(url);
		Assert.assertNotNull(bean);
		String beanstr=JacksonJsonMapper.getInstance().writeValueAsString(bean);
		System.out.println(beanstr);
		Assert.assertEquals("2015秋装新款撞色棉麻印花长袖连衣裙亚麻中长裙女8531010095", bean.getSubject());
	}
	
	
}
