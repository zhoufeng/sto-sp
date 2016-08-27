package com.shenma.top.imagecopy.ecxeption;

import com.bohusoft.dubboapi.exception.ExceptionEnums;


public enum CopyBussinessEnums implements ExceptionEnums{
	HTML_NULL("101","html为空值"),
	COMMON_FAIL("10000","服务器出错"),
	MEMCACHE_REQ_FAIL("102","调用远程memcache出错"),
	URL_UNVALIDATE("103","地址不正确,请确认地址是否正确")
	;
		
		public String code;
		public String message;
		
		private CopyBussinessEnums(String code, String message){
			this.code = code;
			this.message = message;
		}
		

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getMessage() {
			return message;
		}



}
