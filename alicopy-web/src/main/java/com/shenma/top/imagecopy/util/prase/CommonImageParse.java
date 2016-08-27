package com.shenma.top.imagecopy.util.prase;

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

public class CommonImageParse implements UrlParse {
	protected static Logger logger = Logger.getLogger("CommonImageParse");
	/* 
	 * (non-Javadoc)
	 * @see com.shenma.top.imagecopy.util.prase.UrlParse#parseImages(java.lang.String)
	 */
	@Override
	public  ImageBean<ImageVoBean> parseImages(String url) {
		ImageBean<ImageVoBean> bean=new ImageBean<ImageVoBean>();
		try {
			Document doc =JsonpUtil.getAliDefaultConnet(url);
			Elements elements=doc.select("img[src]");
			for(Element element:elements){
				String imageUrl=UrlParseUtil.formatUrl(url,element.attr("src"));
				ImageVoBean vb=new ImageVoBean();
				vb.setUrl(imageUrl);
				vb.setName("图片");
				bean.getImages().add(vb);
			}
			//background图片
			Pattern pattern = Pattern.compile("(?<=url\\().*?(?=\\))");
			Matcher matcher = pattern.matcher(doc.html());
			while(matcher.find()){
				String imageUrl=matcher.group(0);
				imageUrl=UrlParseUtil.formatUrl(url,imageUrl);
				ImageVoBean vb=new ImageVoBean();
				vb.setUrl(imageUrl);
				vb.setName("图片");
				bean.getImages().add(vb);
			}

			
			
			
		} catch (Exception e) {
			logger.error("解析url出错",e);
			bean.setErrorCode(1);
			bean.setErrorMsg("服务器访问人数多,请稍后访问");
		}
		return bean;
	}
	
	
	public static void main(String[] args) {
		CommonImageParse t=new CommonImageParse();
		//t.parseImages("http://www.shuaishou.com/help/3870.html");
		System.out.println("abcdef".substring(0, "abcdef".indexOf("e")));
	}

}
