package com.shenma.top.imagecopy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.prase.AliBaBaImageParse;
import com.shenma.top.imagecopy.util.prase.CommonImageParse;
import com.shenma.top.imagecopy.util.prase.JingDongImageParse;
import com.shenma.top.imagecopy.util.prase.PaiPaiImageParse;
import com.shenma.top.imagecopy.util.prase.TopTaobaoImageParse;
import com.shenma.top.imagecopy.util.prase.TopTmailImageParse;
import com.shenma.top.imagecopy.util.prase.UrlParse;
import com.shenma.top.imagecopy.util.prase.WeiPinHuiImageParse;

@Service
public class CommonImageService {

	public ImageBean<ImageVoBean> parseUrl(String url) {
		UrlParse parse = getUrlParse(url);
		ImageBean<ImageVoBean> ret = parse.parseImages(url);
		//removeDuplicateWithOrder(ret.getImages());// 过滤那些重复的image
		return ret;
	}

	public static UrlParse getUrlParse(String url) {
		UrlParse parse = null;
		if (url.indexOf("item.taobao.com")>-1) {
			parse = new TopTaobaoImageParse();
		} else if (url.indexOf("detail.tmall.com")>-1) {
			parse = new TopTmailImageParse();
		} else if (url.indexOf("detail.1688.com")>-1) {
			parse = new AliBaBaImageParse();
		} else if (url.indexOf("item.jd.com")>-1) {
			parse = new JingDongImageParse();
		} else if (url.startsWith("http://auction1.paipai.com")) {
			parse = new PaiPaiImageParse();
		} else if (url.startsWith("http://www.vip.com/detail")) {
			parse = new WeiPinHuiImageParse();
		} else {
			parse = new CommonImageParse();
		}
		return parse;
	}

	// 删除ArrayList中重复元素，保持顺序 1 2 3 4 5 6 7 8 9
	public static void removeDuplicateWithOrder(List<ImageVoBean> list) {
		Set<ImageVoBean> set = new HashSet<ImageVoBean>();
		List<ImageVoBean> newList = new ArrayList<ImageVoBean>();
		for (Iterator<ImageVoBean> iter = list.iterator(); iter.hasNext();) {
			ImageVoBean element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
	}


}
