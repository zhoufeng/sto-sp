package com.shenma.top.imagecopy.util;

public enum DevelopModelEnum {
	serverModel("server"),localhostModel("localhost");
	
	private DevelopModelEnum(String modelType){
		this.modelType=modelType;
	}
	private String modelType;
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
}
