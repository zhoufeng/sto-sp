package com.bohusoft.dubboapi.service;

/**
 * 获取html页面接口
 * @author zhoufeng
 *
 */
public interface FetchHtmlService {
	/**
	 * 获得详情页面的接口
	 * @param url
	 * @return
	 */
	public String fetchDetailPage(String url);
}
