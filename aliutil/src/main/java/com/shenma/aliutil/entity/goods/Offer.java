package com.shenma.aliutil.entity.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Offer {
	private Map<String,Object> skuPics;
	private Integer bizType;
	private Boolean supportOnlineTrade;
	private Boolean pictureAuthOffer;
	private Boolean priceAuthOffer;
	private Boolean skuTradeSupport;
	private Boolean mixWholeSale;
	private Long offerId;
	private String priceRanges;
	private Integer amountOnSale;
	private Map<String,String> productFeatures=new HashMap<String, String>();
	private List<String> userCategorys;
	private Integer categoryID;
	private Integer periodOfValidity;
	private String offerDetail;
	private String subject;
	private List<String> imageUriList;
	private String freightType;
	private String sendGoodsAddressId;
	private Long freightTemplateId;
	private String offerWeight;
	private List<Map<String,Object>> skuList;
	private String unit;


	public Map<String, Object> getSkuPics() {
		return skuPics;
	}
	public void setSkuPics(Map<String, Object> skuPics) {
		this.skuPics = skuPics;
	}
	public Integer getBizType() {
		return bizType;
	}
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	public Boolean getSupportOnlineTrade() {
		return supportOnlineTrade;
	}
	public void setSupportOnlineTrade(Boolean supportOnlineTrade) {
		this.supportOnlineTrade = supportOnlineTrade;
	}
	public Boolean getPictureAuthOffer() {
		return pictureAuthOffer;
	}
	public void setPictureAuthOffer(Boolean pictureAuthOffer) {
		this.pictureAuthOffer = pictureAuthOffer;
	}
	public Boolean getPriceAuthOffer() {
		return priceAuthOffer;
	}
	public void setPriceAuthOffer(Boolean priceAuthOffer) {
		this.priceAuthOffer = priceAuthOffer;
	}
	public Boolean getSkuTradeSupport() {
		return skuTradeSupport;
	}
	public void setSkuTradeSupport(Boolean skuTradeSupport) {
		this.skuTradeSupport = skuTradeSupport;
	}
	public Boolean getMixWholeSale() {
		return mixWholeSale;
	}
	public void setMixWholeSale(Boolean mixWholeSale) {
		this.mixWholeSale = mixWholeSale;
	}
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	public String getPriceRanges() {
		return priceRanges;
	}
	public void setPriceRanges(String priceRanges) {
		this.priceRanges = priceRanges;
	}
	public Integer getAmountOnSale() {
		return amountOnSale;
	}
	public void setAmountOnSale(Integer amountOnSale) {
		this.amountOnSale = amountOnSale;
	}
	
	
	public Map<String, String> getProductFeatures() {
		return productFeatures;
	}
	public void setProductFeatures(Map<String, String> productFeatures) {
		this.productFeatures = productFeatures;
	}
	
	
	
	public List<String> getUserCategorys() {
		return userCategorys;
	}
	public void setUserCategorys(List<String> userCategorys) {
		this.userCategorys = userCategorys;
	}
	public Integer getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}
	public Integer getPeriodOfValidity() {
		return periodOfValidity;
	}
	public void setPeriodOfValidity(Integer periodOfValidity) {
		this.periodOfValidity = periodOfValidity;
	}
	public String getOfferDetail() {
		return offerDetail;
	}
	public void setOfferDetail(String offerDetail) {
		this.offerDetail = offerDetail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

	public List<String> getImageUriList() {
		return imageUriList;
	}
	public void setImageUriList(List<String> imageUriList) {
		this.imageUriList = imageUriList;
	}
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	
	public Long getFreightTemplateId() {
		return freightTemplateId;
	}
	public void setFreightTemplateId(Long freightTemplateId) {
		this.freightTemplateId = freightTemplateId;
	}

	public String getSendGoodsAddressId() {
		return sendGoodsAddressId;
	}
	public void setSendGoodsAddressId(String sendGoodsAddressId) {
		this.sendGoodsAddressId = sendGoodsAddressId;
	}
	public String getOfferWeight() {
		return offerWeight;
	}
	public void setOfferWeight(String offerWeight) {
		this.offerWeight = offerWeight;
	}
	public List<Map<String,Object>> getSkuList() {
		return skuList;
	}
	public void setSkuList(List<Map<String,Object>> skuList) {
		this.skuList = skuList;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
