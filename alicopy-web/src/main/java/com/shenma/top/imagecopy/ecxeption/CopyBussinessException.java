package com.shenma.top.imagecopy.ecxeption;

import com.bohusoft.dubboapi.exception.DubboApiExceptionEnums;
import com.bohusoft.dubboapi.exception.ExceptionEnums;

public class CopyBussinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionEnums enums=CopyBussinessEnums.COMMON_FAIL;

	public CopyBussinessException(ExceptionEnums exceptionEnums) {
		super(exceptionEnums.getMessage());
		this.enums=exceptionEnums;
	}

	public CopyBussinessException(String message) {
		super(message);
	}

	public CopyBussinessException(Throwable cause) {
		super(cause);
	}

	public CopyBussinessException(String message, Throwable cause) {
		super((new StringBuilder()).append(message).toString(), cause);
	}
	public CopyBussinessException(ExceptionEnums exceptionEnums, Throwable cause) {
		super((new StringBuilder()).append(exceptionEnums.getMessage()).append(":").toString(), cause);
	}
	
	public ExceptionEnums getEnums(){
		return enums;
	}
}
