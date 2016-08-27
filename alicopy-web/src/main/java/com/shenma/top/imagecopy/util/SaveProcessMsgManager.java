package com.shenma.top.imagecopy.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SaveProcessMsgManager {
	private  static final ConcurrentMap<String, Integer> countMap=new ConcurrentHashMap<String, Integer>();
	private  static final ConcurrentMap<String, Integer> totalMap=new ConcurrentHashMap<String, Integer>();
	
	public static Integer get(String memberId){
		return countMap.get(memberId)!=null?countMap.get(memberId):0;
	}
	
	public static Integer getTotal(String memberId){
		return totalMap.get(memberId)!=null?totalMap.get(memberId):0;
	}
	
	public static void setTotal(String memberId,Integer total){
		totalMap.put(memberId, total);
	}
	
	public static void remove(String memberId){
		countMap.remove(memberId);
		totalMap.remove(memberId);
	}
	
	public static void increase(String memberId){
		Integer oldValue, newValue;
	    while (true) {
	        oldValue = countMap.get(memberId);
	        if (oldValue == null) {
	            // Add the word firstly, initial the value as 1
	            newValue = 1;
	            if (countMap.putIfAbsent(memberId, newValue) == null) {
	                break;
	            }
	        } else {
	            newValue = oldValue + 1;
	            if (countMap.replace(memberId, oldValue, newValue)) {
	                break;
	            }
	        }
	    }
	   
	}
}
