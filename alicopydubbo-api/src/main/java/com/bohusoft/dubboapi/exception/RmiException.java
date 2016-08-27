package com.bohusoft.dubboapi.exception;

public class RmiException extends Exception{
/**
	 * 
	 */
	private static final long serialVersionUID = -6509259181893059823L;
	
	private String code;
	

	public RmiException(){
		super();
	}
	
	public RmiException(String message){
		super(message);
	}
	
	public RmiException(String code,String message){
		super(message);
		this.code=code;
	}
	
	public RmiException(String code,String message,Throwable cause){
		super(message,cause);
		this.code=code;
	}
	public RmiException(String message,Throwable cause){
		super(message,cause);
	}
	public RmiException(Throwable cause){
		super(cause);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
