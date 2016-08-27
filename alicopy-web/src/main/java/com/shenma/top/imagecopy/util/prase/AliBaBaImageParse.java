package com.shenma.top.imagecopy.util.prase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.openapi.client.util.JsonMapper;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.UrlParseUtil;
import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;

public class AliBaBaImageParse extends BaseImageParse  implements UrlParse{
	protected static Logger logger = Logger.getLogger("AliBaBaImageParse");
	public static String imgreg="(?i).+?\\.(jpg|jpeg|gif|bmp|png)";
	@Override
	public ImageBean<ImageVoBean> parseImages(String url) {
		ImageBean<ImageVoBean> bean=new ImageBean<ImageVoBean>();
		String title="";//宝贝标题
		try {
			Document doc = JsonpUtil.getAliDefaultConnet(url);
			title=parseTitle(doc);
			//处理主图
			List<ImageVoBean> zhutuList=genZhutuImage(doc,url,title);
			
			//处理颜色分类
			List<ImageVoBean> yanseList=genYanseImage(doc,url,title);
			
			//处理详情
			List<ImageVoBean> xiangqinList=genXiangqinImage(doc, url,title);
			removeDuplicateWithOrder(xiangqinList);
			
			bean.getImages().addAll(zhutuList);
			bean.getImages().addAll(yanseList);
			bean.getImages().addAll(xiangqinList);
		} catch (Exception e) {
			logger.error("解析url出错",e);
			bean.setErrorCode(1);
			bean.setErrorMsg("服务器访问人数多,请稍后访问");
		}
		return bean;
	}
	private String parseTitle(Document doc){
		Element element=doc.select("h1[class=d-title]").first();
		return element.text();
	}
	/**
	 * 功能描述,处理颜色分类图片
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private List<ImageVoBean> genYanseImage(Document doc,String initialUrl,String title) throws JsonParseException, JsonMappingException, IOException{
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Elements images=doc.select(".obj-leading li div ");
		for(Element ele:images){
			if(!ele.hasAttr("data-imgs"))continue;
			String dataImgs=ele.attr("data-imgs");
			Map<String,Object> dataImgsObj=JacksonJsonMapper.getInstance().readValue(dataImgs, Map.class);
			String imageUrl=dataImgsObj.get("original").toString();
			System.out.println(imageUrl);
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
	public List<ImageVoBean> genZhutuImage(Document doc,String initialUrl,String title) throws JsonParseException, JsonMappingException, IOException{
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element element=doc.getElementById("dt-tab");
		if(element==null)return list;
		Elements images=element.select("li[data-imgs]");
		for(Element ele:images){
			String url=ele.attr("data-imgs");
			Map<String,Object> map=JacksonJsonMapper.getInstance().readValue(url, HashMap.class);
			String imageUrl=map.get("original").toString();
			ImageVoBean vb=new ImageVoBean();
			vb.setUrl(imageUrl);
			vb.setType(1);
			vb.setName("主图_"+title);
			list.add(vb);
		}
		return list;
	}
	
	/**
	 * 功能描述,处理详情图片
	 * @return
	 * @throws IOException 
	 */
	public List<ImageVoBean> genXiangqinImage(Document doc,String initialUrl,String title) throws IOException{
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element desdiv=doc.getElementById("de-description-detail");

		Elements images=desdiv.select("img[src]");
		
		for(Element ele:images){
			String url=ele.attr("src");
			String imageUrl=UrlParseUtil.formatUrl(initialUrl,url);
			ImageVoBean vb=new ImageVoBean();
			if(url.endsWith("_.webp")){
				imageUrl=imageUrl.substring(0, imageUrl.length()-6);
			}
			if(imageUrl.matches(imgreg)){
				vb.setUrl(imageUrl);
				vb.setType(3);
				vb.setName("详情_"+title);
				list.add(vb);
			}else{
				continue;
			}
			
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
