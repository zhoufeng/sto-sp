package com.shenma.top.imagecopy.util.scheduling;

import java.util.HashMap;
import java.util.Map;

import com.shenma.top.imagecopy.util.AliCateAuto2Util;
 

public class KeepingAliHref {
	protected void execute()  {
		try {
			String ret=AliCateAuto2Util.getSkuInfo(122916001,121828001,121830001,1);
			System.out.println(ret);
		} catch (InterruptedException e) {
			System.out.println("链接出错啦!");
		}
		
	}

}
