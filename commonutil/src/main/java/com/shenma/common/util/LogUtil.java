package com.shenma.common.util;

import org.apache.log4j.Logger;


public class LogUtil {
	private static Logger logger = Logger.getRootLogger();
	public static void log(String message){
		logger.info(message);
	}
}
