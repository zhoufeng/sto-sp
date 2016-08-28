package com.shenma.common.util;


import java.util.Map;

public class SessionThread {
	private static ThreadLocal<Map<String,Object>> t=new ThreadLocal<Map<String,Object>>(); 
	 public static Map<String,Object> get(){
	      return t.get();
	  }
	  public static void set(Map<String,Object> object){
	    t.set(object);
	  } 
}
