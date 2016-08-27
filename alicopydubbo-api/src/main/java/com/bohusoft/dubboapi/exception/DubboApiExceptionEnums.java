package com.bohusoft.dubboapi.exception;

public enum DubboApiExceptionEnums implements ExceptionEnums{
		
	PIC_SPACE_FULL("120008", "图片空间已满"),
	ALBUM_FULL("120007","单个相册空间已满"),
	COMMON_FAIL("10000","保存出错"),
	URL_NO_FOUND("100", "图片不存在或者已经删除"),
	PIC_MAXSIZE("101","文件超3M"),
	MEMCACHE_REQ_FAIL("102","调用远程memcache出错")
	;
		
		public String code;
		public String message;
		
		private DubboApiExceptionEnums(String code, String message){
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
