package com.bohusoft.dubboapi.service;

import java.util.List;
import java.util.Map;

import com.shenma.aliutil.service.AliToken;

/**
 * 阿里巴巴图片保存远程调用类
 * @author zhoufeng
 *
 */

public interface AliImageHandleService {
	/**
	 * 保存图片列表
	 * @param urlList
	 * @return {@link Map} e.g. {"resultList":[{"url:"","newUrl":"","error":""}]}
	 */
	public Map<String,Object> saveImages(List<String> urlList,String albumId,String name,AliToken aliToken);
	/**
	 * 保存单张图片
	 * @param url
	 * @return {@link Map} e.g. {"newUrl":"","error":""}
	 */
	public Map<String,Object> saveImage(String url,String albumId,String name,String description,AliToken aliToken);
}
