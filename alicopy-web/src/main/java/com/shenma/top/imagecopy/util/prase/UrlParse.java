package com.shenma.top.imagecopy.util.prase;

import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;

public interface UrlParse {
	/**
	 * 功能描述:根据url解析所有图片
	 * @param url
	 * @return
	 */
	public ImageBean<ImageVoBean> parseImages(String url);
}
