package com.shenma.top.imagecopy.util.prase.cate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.shenma.top.imagecopy.util.bean.SearchVoBean;

public interface IBussness {
	public List<Map<String,Object>> getCate(String url) throws IOException,InterruptedException;
	
	public void getItems(SearchVoBean bean)throws IOException,InterruptedException;
	
	public SearchVoBean getItem(String url) throws IOException,InterruptedException;
}
