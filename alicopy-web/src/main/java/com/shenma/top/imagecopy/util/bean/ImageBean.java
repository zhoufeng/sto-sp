package com.shenma.top.imagecopy.util.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageBean<T> implements Serializable{

	private static final long serialVersionUID = -2935702475460834223L;
	private Integer errorCode;
	private String errorMsg;
	private List<T> images=new ArrayList<T>();

	public List<T> getImages() {
		return images;
	}

	public void setImages(List<T> images) {
		this.images = images;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
