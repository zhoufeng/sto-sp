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

import com.shenma.top.imagecopy.util.JsonpCateUtil;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.TaobaoJsonpUtil;
import com.shenma.top.imagecopy.util.UrlParseUtil;
import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;

public class TopTaobaoImageParse extends BaseImageParse implements UrlParse {
	protected static Logger logger = Logger.getLogger("TopImageParse");
	private String title="";//宝贝标题
	@Override
	public ImageBean<ImageVoBean> parseImages(String url) {
		ImageBean<ImageVoBean> bean=new ImageBean<ImageVoBean>();
		try {
			Document doc = JsonpUtil.getAliDefaultConnet(url);
			title=UrlParseUtil.parseTitle(doc);
			//处理主图
			List<ImageVoBean> zhutuList=genZhutuImage(doc);
			
			//处理颜色分类
			List<ImageVoBean> yanseList=genYanseImage(doc);

			
			//处理详情
			List<ImageVoBean> xiangqinList=genXiangqinImage(doc);
			removeDuplicateWithOrder(xiangqinList);
			
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
	
	/**
	 * 功能描述,处理颜色分类图片
	 * @return
	 */
	public List<ImageVoBean> genYanseImage(Document doc){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Elements images=doc.select("li[data-value]");
		for(Element ele:images){
			Element aEle=ele.select("a[style]").first();
			if(aEle==null)continue; //颜色没有图片的
			String style=aEle.attr("style");
			
			Pattern pattern = Pattern.compile("(?<=url\\()[\\s\\S]*\\.jpg");
			Matcher matcher = pattern.matcher(style);
			String imageUrl=null;
			if(matcher.find()){
				imageUrl=matcher.group(0);
			}
			imageUrl=imageUrl.trim();
			if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
			imageUrl=imageUrl.substring(0,imageUrl.lastIndexOf("_"));
			//imageUrl=lastChraraset(imageUrl);
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
	public List<ImageVoBean> genZhutuImage(Document doc){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element element=doc.getElementById("J_UlThumb");
		Elements images=element.select("img[data-src]");
		for(Element ele:images){
			String imageUrl=ele.attr("data-src");
			if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
			//imageUrl=imageUrl.substring(0, imageUrl.indexOf(".jpg")+4);
			imageUrl=imageUrl.substring(0, imageUrl.lastIndexOf("_"));
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
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unused")
	public List<ImageVoBean> genXiangqinImage(Document doc) throws IOException, InterruptedException{
		Pattern pattern = Pattern.compile("(?<=g_config.dynamicScript\\(\").*(?=\"\\))");
		Matcher matcher = pattern.matcher(doc.html());
		
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		//Document scriptdoc =JsonpUtil.getAliDefaultConnet(descriptionurl);
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		String description=gendescTaobao(doc);
		description=description.substring(10, description.length()-2);
		Document descriptiondoc = Jsoup.parse(description);
		Elements images=descriptiondoc.select("img[src]");
		
		for(Element ele:images){
			String url=ele.attr("src");
			if(url.endsWith("gif")){
				continue;
			}
			url=url.trim();
			ImageVoBean vb=new ImageVoBean();
			vb.setUrl(url);
			vb.setType(3);
			vb.setName("详情_"+title);
			list.add(vb);
		}
		return list;
	}
	
	public String gendescTaobao(Document doc) throws IOException, InterruptedException{
		Pattern pattern = Pattern.compile("//dsc.taobaocdn.com.*?(?=\' :)");
		
		Matcher matcher = pattern.matcher(doc.html());

		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		descriptionurl="http:"+descriptionurl;
		Document scriptdoc = JsonpUtil.getAliDefaultConnet(descriptionurl);
		String description=scriptdoc.body().html();
		description=description.replaceAll("\\\\ ", " ");
		description=description.substring(11, description.length()-2);
		return description;
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
	public static void main(String[] args) {
		TopTaobaoImageParse t=new TopTaobaoImageParse();
		String url="https://item.taobao.com/item.htm?id=521290858413";
		Document doc=JsonpCateUtil.getAliDefaultConnet(url);
		t.genZhutuImage(doc);
		//System.out.println(t.lastChraraset("http://img02.taobaocdn.com/bao/uploaded/i2/806150520/TB2dHw5XVXXXXXeXpXXXXXXXXXX_!!806150520._30x30.jpg"));
	}
}
