package com.shenma.common.util.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AliDate extends Date{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2948865248586986442L;
	@Override
	 public String toString() {
		SimpleDateFormat formatter; 
	    formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss"); 
	    String ctime = formatter.format(this); 
		 return ctime;
	 }
}
