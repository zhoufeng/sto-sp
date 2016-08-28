package com.shenma.aliutil.entity.wuliu;

import java.util.List;

public class DeliveryTemplateDescn {
	private Long templateId;
	private String templateName;
	private String fromProvinceName;
	private String fromCityName;
	private String fromCountyName;
	private String descn;
	private Integer[] serviceTypes; //包含服务类型，0为：快递，1为：货运，2为：COD
	private List<SubTemplateDescn> subTemplateDescnList;
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getFromProvinceName() {
		return fromProvinceName;
	}
	public void setFromProvinceName(String fromProvinceName) {
		this.fromProvinceName = fromProvinceName;
	}
	public String getFromCityName() {
		return fromCityName;
	}
	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}
	public String getFromCountyName() {
		return fromCountyName;
	}
	public void setFromCountyName(String fromCountyName) {
		this.fromCountyName = fromCountyName;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Integer[] getServiceTypes() {
		return serviceTypes;
	}
	public void setServiceTypes(Integer[] serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	public List<SubTemplateDescn> getSubTemplateDescnList() {
		return subTemplateDescnList;
	}
	public void setSubTemplateDescnList(List<SubTemplateDescn> subTemplateDescnList) {
		this.subTemplateDescnList = subTemplateDescnList;
	}
	
}
