package com.shenma.top.imagecopy.util.bean;

import java.io.Serializable;

public class ImageVoBean implements Serializable{

	private static final long serialVersionUID = -2834048570247533050L;
	public String url;
	public String name;
	public Integer type; //主图:1;颜色:2;详情:3
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		 if (!(o instanceof ImageVoBean))return false;
		 ImageVoBean ib = (ImageVoBean) o;
		 return getUrl().equals(ib.getUrl());
	}
	public int hashCode() {
       int result = 17;
       result = 37 * result +url.hashCode();
       return result;
	}
}
