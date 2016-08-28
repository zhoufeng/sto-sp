package com.shenma.aliutil.exception;

public class AliReqException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1259873900399184389L;
	
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
