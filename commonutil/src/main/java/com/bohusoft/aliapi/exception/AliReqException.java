package com.bohusoft.aliapi.exception;

public class AliReqException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1259873900399184389L;
	
	public static final String PIC_MAXSIZE = "120009";  //上传图片过大
	
	public static final String PIC_SPACE_FULL = "120008"; //图片过满.
	
	public static final String ALBUM_FULL = "120007"; //图片过满.
	
	public static final String UPLOAD_FAIL = "090008"; //图片过满.
	
	public static final String COMM_SEARCH_FAIL = "1000"; //通用错误;
	
	public static final String ALI_REQ_FAIL = "1001"; //请求阿里聚石塔api错误
	
	private String code;
	

	public AliReqException(){
		super();
	}
	
	public AliReqException(String message){
		super(message);
	}
	
	public AliReqException(String code,String message){
		super(message);
		this.code=code;
	}
	
	public AliReqException(String code,String message,Throwable cause){
		super(message,cause);
		this.code=code;
	}
	public AliReqException(String message,Throwable cause){
		super(message,cause);
	}
	public AliReqException(Throwable cause){
		super(cause);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
