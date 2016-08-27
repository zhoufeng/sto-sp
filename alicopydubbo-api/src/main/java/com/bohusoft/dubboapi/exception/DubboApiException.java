package com.bohusoft.dubboapi.exception;

public class DubboApiException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1745871389157294647L;
	private ExceptionEnums enums=DubboApiExceptionEnums.COMMON_FAIL;

	public DubboApiException(ExceptionEnums exceptionEnums) {
		super(exceptionEnums.getMessage());
		this.enums=exceptionEnums;
	}

	public DubboApiException(String message) {
		super(message);
	}

	public DubboApiException(Throwable cause) {
		super(cause);
	}

	public DubboApiException(String message, Throwable cause) {
		super((new StringBuilder()).append(message).toString(), cause);
	}
	public DubboApiException(ExceptionEnums exceptionEnums, Throwable cause) {
		super((new StringBuilder()).append(exceptionEnums.getMessage()).append(":").toString(), cause);
	}
	
	public ExceptionEnums getEnums(){
		return enums;
	}
}
