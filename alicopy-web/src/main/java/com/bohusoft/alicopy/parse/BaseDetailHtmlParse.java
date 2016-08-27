package com.bohusoft.alicopy.parse;

import org.springframework.beans.factory.annotation.Autowired;

import com.shenma.top.imagecopy.util.reqdubbo.HtmlDetailDubboUtil;

public abstract class BaseDetailHtmlParse {
	public static String imgreg="(?i).+?\\.(jpg|jpeg|gif|bmp|png)";
	@Autowired
	protected HtmlDetailDubboUtil detailDubboUtil;
	

}
