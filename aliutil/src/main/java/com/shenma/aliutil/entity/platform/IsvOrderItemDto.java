package com.shenma.aliutil.entity.platform;

import java.util.Date;

public class IsvOrderItemDto {
	private String bizStatusExt;
	private String memberId;
	private String productName;
	private Date gmtCreate;
	private Date gmtServiceEnd;
	private String bizStatus;
	private Double paymentAmount;
	private Double executePrice;
	public String getBizStatusExt() {
		return bizStatusExt;
	}
	public void setBizStatusExt(String bizStatusExt) {
		this.bizStatusExt = bizStatusExt;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtServiceEnd() {
		return gmtServiceEnd;
	}
	public void setGmtServiceEnd(Date gmtServiceEnd) {
		this.gmtServiceEnd = gmtServiceEnd;
	}
	public String getBizStatus() {
		return bizStatus;
	}
	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Double getExecutePrice() {
		return executePrice;
	}
	public void setExecutePrice(Double executePrice) {
		this.executePrice = executePrice;
	}
	
	
}
