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

public class TaobaoDetailHtmlParseTest extends BaseJUnit4Test {
	@Autowired
	private DetailHtmlParse taobaoDetailHtmlParse;
	
	@Autowired
	private FetchHtmlUtil fetchHtmlUtil;
	
	@Test
	public void testParse() throws CopyBussinessException, JsonGenerationException, JsonMappingException, IOException{
		String url="https://item.taobao.com/item.htm?id=44050405059";
		DetailHtmlParseBean bean = taobaoDetailHtmlParse.parse(url);
		Assert.assertNotNull(bean);
		String beanstr=JacksonJsonMapper.getInstance().writeValueAsString(bean);
		System.out.println(beanstr);
		Assert.assertEquals("蚕丝被冬被芯100%桑蚕丝 春秋冬季加厚被子 单人双人四季被棉被-淘宝网", bean.getSubject());
	}
	
}
