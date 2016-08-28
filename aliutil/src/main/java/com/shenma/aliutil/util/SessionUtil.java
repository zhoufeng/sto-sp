package com.shenma.aliutil.util;

import java.util.HashMap;
import java.util.Map;

import com.shenma.aliutil.service.AliToken;
import com.shenma.common.util.SessionThread;

public class SessionUtil {
	
	public static AliToken getAliSession(){
		Map<String,Object> t=SessionThread.get();
		if(t==null)return null;
		return (AliToken) t.get(AliConstant.ali_info_name);
	}
	
	public static void setAliSession(AliToken info){
		Map<String,Object> t=SessionThread.get();
		if(t==null)t=new HashMap<String, Object>(2);
		t.put(AliConstant.ali_info_name, info);
		SessionThread.set(t);
	}
	
}
