package com.shenma.aliutil.entity.wuliu;

import java.util.List;

public class SubTemplateDescn {
	private String chargeTypeName;
	private Integer chargeType;
	private Integer serviceChargeType;
	private String subTemplateName;
	private String serviceType;//服务类型：0:快递；1:货运；2:COD
	private Integer rateSourceType;
	private List<DeliveryRateDescn> rates;
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	public Integer getServiceChargeType() {
		return serviceChargeType;
	}
	public void setServiceChargeType(Integer serviceChargeType) {
		this.serviceChargeType = serviceChargeType;
	}
	public String getSubTemplateName() {
		return subTemplateName;
	}
	public void setSubTemplateName(String subTemplateName) {
		this.subTemplateName = subTemplateName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Integer getRateSourceType() {
		return rateSourceType;
	}
	public void setRateSourceType(Integer rateSourceType) {
		this.rateSourceType = rateSourceType;
	}
	public List<DeliveryRateDescn> getRates() {
		return rates;
	}
	public void setRates(List<DeliveryRateDescn> rates) {
		this.rates = rates;
	}
	
	
}
