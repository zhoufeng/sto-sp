package com.shenma.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

public class StringUtil {
	protected static Logger logger = Logger.getLogger("StringUtil");
	public static String urlDecode(String s,String enc){
		String ret=null;
		try {
			ret=URLDecoder.decode(s, enc);
		} catch (UnsupportedEncodingException e) {
			logger.error("decode解码错误:", e);
			ret="";
		}
		return ret;
	}
}
