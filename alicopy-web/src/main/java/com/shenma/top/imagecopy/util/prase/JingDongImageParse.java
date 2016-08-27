package com.shenma.top.imagecopy.util.prase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.UrlParseUtil;
import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;

public class JingDongImageParse implements UrlParse {

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
			//List<ImageVoBean> yanseList=genYanseImage(doc,url);
			
			//处理详情
			List<ImageVoBean> xiangqinList=genXiangqinImage(doc, url);
			
			bean.getImages().addAll(zhutuList);
			bean.getImages().addAll(xiangqinList);
		} catch (Exception e) {
			logger.error("解析url出错",e);
			bean.setErrorCode(1);
			bean.setErrorMsg("服务器访问人数多,请稍后访问");
		}
		return bean;
	}
	private String parseTitle(Document doc){
		Element element=doc.select("div[id=product-intro] h1").first();
		return element.text();
	}
	/**
	 * 功能描述,处理颜色分类图片
	 * @return
	 */
	private List<ImageVoBean> genYanseImage(Document doc,String initialUrl){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Elements images=doc.select("li[data-value][title]");
		for(Element ele:images){
			Element aEle=ele.select("a[style]").first();
			if(aEle==null)continue; //颜色没有图片的
			String style=aEle.attr("style");
			
			Pattern pattern = Pattern.compile("http.*\\.jpg");
			Matcher matcher = pattern.matcher(style);
			String descriptionurl=null;
			if(matcher.find()){
				descriptionurl=matcher.group(0);
			}
			
			String imageUrl=UrlParseUtil.formatUrl(initialUrl,descriptionurl);
			imageUrl=lastChraraset(imageUrl);
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
	 */
	private List<ImageVoBean> genZhutuImage(Document doc,String initialUrl){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element element=doc.getElementById("spec-list");
		Elements images=element.select("img[data-url]");
		for(Element ele:images){
			String url=ele.attr("data-url");
			String imageUrl="http://img11.360buyimg.com/vc/"+url;
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
	@SuppressWarnings("unused")
	private List<ImageVoBean> genXiangqinImage(Document doc,String initialUrl) throws IOException{
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element desdiv=doc.select(".detail-content").first();
		Elements images=desdiv.select("img[data-lazyload]");
		
		for(Element ele:images){
			String url=ele.attr("data-lazyload");
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
