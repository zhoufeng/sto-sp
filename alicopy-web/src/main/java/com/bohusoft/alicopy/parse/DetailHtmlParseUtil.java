package com.bohusoft.alicopy.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.ecxeption.CopyBussinessEnums;
import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;

@Component
public class DetailHtmlParseUtil {
	
	@Autowired
	private AlibabaDetailHtmlParse alibabaDetailHtmlParse;
	
	@Autowired
	private TaobaoDetailHtmlParse taobaoDetailHtmlParse;
	
	@Autowired
	private TmallDetailHtmlParse tmallDetailHtmlParse;
	
	private List<DetailHtmlParse> parseList=new ArrayList<DetailHtmlParse>();
	
	
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void init() throws IOException{
		parseList.add(alibabaDetailHtmlParse);
		parseList.add(taobaoDetailHtmlParse);
		parseList.add(tmallDetailHtmlParse);
	}
	
	public DetailHtmlParseBean parseByUrl(String url) throws CopyBussinessException{
		DetailHtmlParse parse=getParseImp(url);
		if(parse==null){
			throw new CopyBussinessException(CopyBussinessEnums.URL_UNVALIDATE);
		}
		return parse.parse(url);
	}
	
	private DetailHtmlParse getParseImp(String url){
		for(DetailHtmlParse parse:parseList){
			if(parse.validateUrl(url))return parse;
		}
		return null;
	}
}
