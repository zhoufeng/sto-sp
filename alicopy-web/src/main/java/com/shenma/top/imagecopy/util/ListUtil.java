package com.shenma.top.imagecopy.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {
	public static List<String> filterList(List<String> list){
		Map<String,Object> map=new HashMap<String,Object>();
		for(String url:list){
			map.put(url, url);
		}
		return Arrays.asList((String[])map.keySet().toArray());
	}
}
