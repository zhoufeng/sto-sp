package com.shenma.aliutil.entity.goods;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OfferDetailInfo {
	private Map<Integer,List<Map<String,String>>> skuPics;
	private Boolean isPrivateOffer;
	private Boolean isPriceAuthOffer;
	private Boolean isPicAuthOffer;
	private Long offerId;
	private Boolean isPrivate;
	private String detailsUrl;
	private String type;
	private Integer tradeType;
	private Integer postCategryId;
	private String offerStatus;
	private String memberId;
	private String subject;
	private String details;
	private String qualityLevel;
	private List<OfferImageInfo> imageList;
	private List<ProductFeatureInfo> productFeatureList;
	private Boolean isOfferSupportOnlineTrade;
	private String tradingType;
	private Boolean isSupportMix;
	private String unit;
	private String priceUnit;
	private Integer amount;
	private Integer amountOnSale;
	private Integer saledCount;
	private double retailPrice;
	private double unitPrice;
	private List<PriceRangeInfo> priceRanges;
	private Integer termOfferProcess;
	private Integer freightTemplateId;
	private Integer sendGoodsId;
	private Integer productUnitWeight;
	private Integer freightType;
	private Boolean isSkuOffer;
	private Boolean isSkuTradeSupported;
	private List<Sku> skuArray;
	private String gmtCreate;
	private Date gmtModified;
	private String gmtLastRepost;
	private String gmtApproved;
	private String gmtExpire;
	/**
	 * 是否有私密信息
	 * @return
	 */
	public Boolean getIsPrivateOffer() {
		return isPrivateOffer;
	}
	public void setIsPrivateOffer(Boolean isPrivateOffer) {
		this.isPrivateOffer = isPrivateOffer;
	}
	/**
	 * 是否价格私密
	 * @return
	 */
	public Boolean getIsPriceAuthOffer() {
		return isPriceAuthOffer;
	}
	public void setIsPriceAuthOffer(Boolean isPriceAuthOffer) {
		this.isPriceAuthOffer = isPriceAuthOffer;
	}
	/**
	 * 是否图片私密
	 * @return
	 */
	public Boolean getIsPicAuthOffer() {
		return isPicAuthOffer;
	}
	public void setIsPicAuthOffer(Boolean isPicAuthOffer) {
		this.isPicAuthOffer = isPicAuthOffer;
	}
	/**
	 * 商品ID
	 * @return
	 */
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	/**
	 * 是否为私密offer的标志位。true：私密产品 false：普通产品
	 * @return
	 * 
	 */
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	/**
	 * 商品详情地址
	 * @return
	 */
	public String getDetailsUrl() {
		return detailsUrl;
	}
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	/**
	 * 商品类型。Sale：供应信息，Buy：求购信息
	 * @return
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 贸易类型。1：产品，2：加工，3：代理，4：合作，5：商务服务
	 * @return
	 */
	public Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * 所属叶子类目ID
	 * @return
	 */
	public Integer getPostCategryId() {
		return postCategryId;
	}
	public void setPostCategryId(Integer postCategryId) {
		this.postCategryId = postCategryId;
	}
	/**
	 * 状态。auditing：审核中；online：已上网；FailAudited：审核未通过；outdated：已过期；member delete(d)：用户删除；delete：审核删除
	 * @return
	 */
	public String getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
	/**
	 * 卖家会员ID
	 * @return
	 */
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * 商品标题
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 详情说明
	 * @return
	 */
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * 商品信息质量星级( 取值为1到5)。1：一星；2：二星；3：三星；4：四星；5：五星
	 * @return
	 */
	public String getQualityLevel() {
		return qualityLevel;
	}
	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}
	/**
	 * 商品图片列表
	 * @return
	 */
	public void setImageList(List<OfferImageInfo> imageList) {
		this.imageList = imageList;
	}
	public void setProductFeatureList(List<ProductFeatureInfo> productFeatureList) {
		this.productFeatureList = productFeatureList;
	}

	/**
	 * 商品属性信息
	 * @return
	 */
	public List<ProductFeatureInfo> getProductFeatureList() {
		return productFeatureList;
	}
	public List<OfferImageInfo> getImageList() {
		return imageList;
	}
	/**
	 * 是否支持网上交易。首先需要类目支持，如果类目支持，需要有价格，供货总量，最小起订量。true：支持网上订购；false：不支持网上订购
	 * @return
	 */
	public Boolean getIsOfferSupportOnlineTrade() {
		return isOfferSupportOnlineTrade;
	}
	public void setIsOfferSupportOnlineTrade(Boolean isOfferSupportOnlineTrade) {
		this.isOfferSupportOnlineTrade = isOfferSupportOnlineTrade;
	}
	
	/**
	 * 支持的交易方式。当isOfferSupportOnlineTrade为true的时候本字段有效：Escrow:支付宝担保交易； PreCharge：支付宝预存款交易；ForexPay：支付宝境外支付交易；多种交易方式间通过;分隔。
	 * @return
	 */
	public String getTradingType() {
		return tradingType;
	}
	public void setTradingType(String tradingType) {
		this.tradingType = tradingType;
	}
	/**
	 * 是否支持混批。true：支持混批；false：不支持混批
	 * @return
	 */
	public Boolean getIsSupportMix() {
		return isSupportMix;
	}
	public void setIsSupportMix(Boolean isSupportMix) {
		this.isSupportMix = isSupportMix;
	}
	/**
	 * 计量单位
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 交易币种
	 * @return
	 */
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	/**
	 * 供货量
	 * @return
	 */
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * 可售数量
	 * @return
	 */
	public Integer getAmountOnSale() {
		return amountOnSale;
	}
	public void setAmountOnSale(Integer amountOnSale) {
		this.amountOnSale = amountOnSale;
	}
	/**
	 * 已销售量
	 * @return
	 */
	public Integer getSaledCount() {
		return saledCount;
	}
	public void setSaledCount(Integer saledCount) {
		this.saledCount = saledCount;
	}
	/**
	 * 建议零售价
	 * @return
	 */
	public double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	/**
	 * 单价
	 * @return
	 */
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * 价格区间
	 * @return
	 */
	public List<PriceRangeInfo> getPriceRanges() {
		return priceRanges;
	}
	public void setPriceRanges(List<PriceRangeInfo> priceRanges) {
		this.priceRanges = priceRanges;
	}
	/**
	 * 有效期(单位：天)
	 * @return
	 */
	public Integer getTermOfferProcess() {
		return termOfferProcess;
	}
	public void setTermOfferProcess(Integer termOfferProcess) {
		this.termOfferProcess = termOfferProcess;
	}
	/**
	 * 物流模板id
	 * @return
	 */
	public Integer getFreightTemplateId() {
		return freightTemplateId;
	}
	public void setFreightTemplateId(Integer freightTemplateId) {
		this.freightTemplateId = freightTemplateId;
	}
	/**
	 * 发货地址id
	 * @return
	 */
	public Integer getSendGoodsId() {
		return sendGoodsId;
	}
	public void setSendGoodsId(Integer sendGoodsId) {
		this.sendGoodsId = sendGoodsId;
	}
	/**
	 * 单位重量
	 * @return
	 */
	public Integer getProductUnitWeight() {
		return productUnitWeight;
	}
	public void setProductUnitWeight(Integer productUnitWeight) {
		this.productUnitWeight = productUnitWeight;
	}
	/**
	 * T:运费模板 D：运费说明 F：卖家承担运费
	 * @return
	 */
	public Integer getFreightType() {
		return freightType;
	}
	public void setFreightType(Integer freightType) {
		this.freightType = freightType;
	}
	/**
	 * 是否为SKU商品
	 * @return
	 */
	public Boolean getIsSkuOffer() {
		return isSkuOffer;
	}
	public void setIsSkuOffer(Boolean isSkuOffer) {
		this.isSkuOffer = isSkuOffer;
	}
	/**
	 * 是否支持按照规格报价
	 * @return
	 */
	public Boolean getIsSkuTradeSupported() {
		return isSkuTradeSupported;
	}
	public void setIsSkuTradeSupported(Boolean isSkuTradeSupported) {
		this.isSkuTradeSupported = isSkuTradeSupported;
	}
	/**
	 * SKU规格属性信息{fid:value}当有多个值时用"#"联接
	 * @return
	 */
	public List<Sku> getSkuArray() {
		return skuArray;
	}
	public void setSkuArray(List<Sku> skuArray) {
		this.skuArray = skuArray;
	}

	/**
	 * 创建日期
	 * @return
	 */
	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 最近修改时间
	 * @return
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 最近重发时间
	 * @return
	 */
	public String getGmtLastRepost() {
		return gmtLastRepost;
	}


	public void setGmtLastRepost(String gmtLastRepost) {
		this.gmtLastRepost = gmtLastRepost;
	}
	/**
	 * 审核通过时间
	 * @return
	 */
	public String getGmtApproved() {
		return gmtApproved;
	}
	public void setGmtApproved(String gmtApproved) {
		this.gmtApproved = gmtApproved;
	}
	/**
	 * 过期日期
	 * @return
	 */
	public String getGmtExpire() {
		return gmtExpire;
	}
	public void setGmtExpire(String gmtExpire) {
		this.gmtExpire = gmtExpire;
	}
	public Map<Integer, List<Map<String, String>>> getSkuPics() {
		return skuPics;
	}
	public void setSkuPics(Map<Integer, List<Map<String, String>>> skuPics) {
		this.skuPics = skuPics;
	}
	
	
	

 	
}
