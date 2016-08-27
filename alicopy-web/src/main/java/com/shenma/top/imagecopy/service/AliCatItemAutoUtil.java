package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.top.imagecopy.dao.OwnCatInfoItemDao;
import com.shenma.top.imagecopy.entity.OwnCatInfoItem;
import com.shenma.top.imagecopy.util.BaseHttpClient;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;

@Component
public class AliCatItemAutoUtil extends BaseHttpClient {
	public static long startTime=System.currentTimeMillis();
	@Autowired
	private OwnCatInfoItemDao ownCatInfoItemDao;
	public  List<Map<String,Object>> getItem(String catId,String pathValues) throws JsonParseException, JsonMappingException, IOException{
		OwnCatInfoItem item=ownCatInfoItemDao.findByCatIdAndPathValues(Integer.valueOf(catId), pathValues);
		String str=null;
		if(item==null){
			String url="http://spu.1688.com/spu/ajax/getLevelInfoByPath.htm";
			Map<String,String> params=new HashMap<String,String>();
			params.put("callback", "jQuery17204121322811517034_1421650460559");
			params.put("catId", catId);
			params.put("pathValues", pathValues);
			String ret=get(url, params,"GBK");
			str=getData(ret);
			try {
				item=new OwnCatInfoItem();
				item.setCatId(Integer.valueOf(catId));
				item.setPathValues(pathValues);
				item.setProperties(str);
				ownCatInfoItemDao.saveAndFlush(item);
				Thread.sleep(500);
			} catch (Exception e) {
				
			}
		}else{
			str=item.getProperties();
		}
		
		HashMap<String, Object> retMap=JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		if(retMap==null)return new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> data=(List<Map<String, Object>>) retMap.get("data");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized List<Map<String,Object>>  getItemByUrl(String catId,String pathValues) throws JsonParseException, JsonMappingException, IOException, InterruptedException{
		long endTime=System.currentTimeMillis();
		if(endTime-startTime>500){
			
		}else{
			Thread.sleep(500-endTime+startTime);
		}
		startTime=endTime;
		String url="http://spu.1688.com/spu/ajax/getLevelInfoByPath.htm";
		Map<String,String> params=new HashMap<String,String>();
		params.put("callback", "jQuery17204121322811517034_1421650460559");
		params.put("catId", catId);
		params.put("pathValues", pathValues);
		String ret=get(url, params,"GBK");
		OwnCatInfoItem item=ownCatInfoItemDao.findByCatIdAndPathValues(Integer.valueOf(catId), pathValues);
		if(item==null)item=new OwnCatInfoItem();
		String str=getData(ret);
		try {
			item.setCatId(Integer.valueOf(catId));
			item.setPathValues(pathValues);
			item.setProperties(str);
			ownCatInfoItemDao.saveAndFlush(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, Object> retMap=JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		if(retMap==null)return new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> data=(List<Map<String, Object>>) retMap.get("data");
		return data;
	}
	
}
