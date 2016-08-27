package com.bohusoft.alicopy.parse;

import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;

public interface DetailHtmlParse {
	/**
	 * 解析url获得最后的保存的offer对象
	 * @param url
	 * @return
	 */
	public DetailHtmlParseBean parse(String url) throws CopyBussinessException;
	
	/**
	 * 验证url是否是该类解析.
	 * @param url
	 * @return
	 */
	public boolean validateUrl(String url);
}
