package com.shenma.top.imagecopy.util.prase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.shenma.top.imagecopy.util.bean.ImageVoBean;

public class BaseImageParse {
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
