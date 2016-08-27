package com.shenma.top.imagecopy.util.prase;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.converter.json.JacksonObjectMapperFactoryBean;

import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.UrlParseUtil;
import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;

public class PaiPaiImageParse implements UrlParse {

	protected static Logger logger = Logger.getLogger("JingDongImageParse");
	private String title="";//宝贝标题
	@Override
	public ImageBean<ImageVoBean> parseImages(String url) {
		ImageBean<ImageVoBean> bean=new ImageBean<ImageVoBean>();
		try {
			Document doc = JsonpUtil.getAliDefaultConnet(url);
			title=parseTitle(doc);
			//处理主图
			List<ImageVoBean> zhutuList=genZhutuImage(doc,url);
			
			//处理颜色分类
			List<ImageVoBean> yanseList=genYanseImage(doc,url);
			
			//处理详情
			List<ImageVoBean> xiangqinList=genXiangqinImage(doc, url);
			
			bean.getImages().addAll(zhutuList);
			bean.getImages().addAll(xiangqinList);
			bean.getImages().addAll(yanseList);
		} catch (Exception e) {
			logger.error("解析url出错",e);
			bean.setErrorCode(1);
			bean.setErrorMsg("服务器访问人数多,请稍后访问");
		}
		return bean;
	}
	private String parseTitle(Document doc){
		Element element=doc.select("div[class=pp_prop_tit] h1").first();
		return element.text();
	}
	/**
	 * 功能描述,处理颜色分类图片
	 * @return
	 */
	private List<ImageVoBean> genYanseImage(Document doc,String initialUrl){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Elements images=doc.select("div[class=pp_prop_attr] li");
		for(Element ele:images){
			String url=ele.attr("info-img");
			if(url==null)continue; //颜色没有图片的
			
			String imageUrl=UrlParseUtil.formatUrl(initialUrl,url);
			ImageVoBean vb=new ImageVoBean();
			vb.setUrl(imageUrl);
			vb.setType(2);
			vb.setName("颜色分类_"+title);
			list.add(vb);
		}
		return list;
	}
	
	
	/**
	 * 功能描述,处理主图图片
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	private List<ImageVoBean> genZhutuImage(Document doc,String initialUrl) throws JsonParseException, JsonMappingException, IOException{
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Pattern pattern = Pattern.compile("(?<=picList\\:\\[).*?\\]");
		Matcher matcher = pattern.matcher(doc.html());
		String zhutuStr=null;
		if(matcher.find()){
			zhutuStr=matcher.group(0);
		};
		List zhutulist=JacksonJsonMapper.getInstance().readValue(zhutuStr, List.class);
		for(Object url:zhutulist){
			if(StringUtils.isEmpty(url.toString()))continue;
			ImageVoBean vb=new ImageVoBean();
			vb.setUrl(url.toString());
			vb.setType(1);
			vb.setName("主图_"+title);
			list.add(vb);
		}

		return list;
	}
	
	private String parseUrl(String imageUrl){
		Integer lastIndex=imageUrl.lastIndexOf(".");
		String lastStr=imageUrl.substring(0, lastIndex);
		Integer lastSecondIndex=lastStr.lastIndexOf(".");
		String lastSecondStr=lastStr.substring(0, lastSecondIndex);
		String firstUrl=lastSecondStr+imageUrl.substring(lastIndex,imageUrl.length());
		return firstUrl;
	}
	/**
	 * 功能描述,处理详情图片
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private List<ImageVoBean> genXiangqinImage(Document doc,String initialUrl) throws IOException{
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element desdiv=doc.getElementById("comSelfContent");
		Elements images=desdiv.select("img[src]");
		
		for(Element ele:images){
			String url=ele.attr("src");
			String imageUrl=UrlParseUtil.formatUrl(initialUrl,url);
			ImageVoBean vb=new ImageVoBean();
			if(url.endsWith("_.webp")){
				imageUrl=imageUrl.substring(0, imageUrl.length()-6);
			}
			vb.setUrl(imageUrl);
			vb.setType(3);
			vb.setName("详情_"+title);
			list.add(vb);
		}
		return list;
	}
	
	
	/**
	 * 处理图片后缀
	 */
	private String lastChraraset(String imageUrl){
		Pattern pattern = Pattern.compile("http.*(?=_.*?\\.jpg)");
		Matcher matcher = pattern.matcher(imageUrl);
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		return descriptionurl;
	}


}
