package com.shenma.top.imagecopy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OwnCatInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "own_cat_info")
public class OwnCatInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer topCategoryId;
	private Integer secondCategoryId;
	private Integer thirdCategoryId;
	private Integer tradeYpe;
	private String properties;
	private Integer catsId;
	private String catsName;
	private Boolean applySpu;
	private Boolean supportSkuprice;
	private Boolean supportOnlineTrade;
	private Boolean spuPriceExt;
	private Boolean leaf;
	private Boolean applyRealPrice;
	private Boolean supportMixWholesale;
	private Boolean batchPost;

	// Constructors

	/** default constructor */
	public OwnCatInfo() {
	}

	/** full constructor */
	public OwnCatInfo(Integer topCategoryId, Integer secondCategoryId,
			Integer thirdCategoryId, Integer tradeYpe, String properties,
			Integer catsId, String catsName, Boolean applySpu,
			Boolean supportSkuprice, Boolean supportOnlineTrade,
			Boolean spuPriceExt, Boolean leaf, Boolean applyRealPrice,
			Boolean supportMixWholesale, Boolean batchPost) {
		this.topCategoryId = topCategoryId;
		this.secondCategoryId = secondCategoryId;
		this.thirdCategoryId = thirdCategoryId;
		this.tradeYpe = tradeYpe;
		this.properties = properties;
		this.catsId = catsId;
		this.catsName = catsName;
		this.applySpu = applySpu;
		this.supportSkuprice = supportSkuprice;
		this.supportOnlineTrade = supportOnlineTrade;
		this.spuPriceExt = spuPriceExt;
		this.leaf = leaf;
		this.applyRealPrice = applyRealPrice;
		this.supportMixWholesale = supportMixWholesale;
		this.batchPost = batchPost;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "top_category_id")
	public Integer getTopCategoryId() {
		return this.topCategoryId;
	}

	public void setTopCategoryId(Integer topCategoryId) {
		this.topCategoryId = topCategoryId;
	}

	@Column(name = "second_category_id")
	public Integer getSecondCategoryId() {
		return this.secondCategoryId;
	}

	public void setSecondCategoryId(Integer secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	@Column(name = "third_category_id")
	public Integer getThirdCategoryId() {
		return this.thirdCategoryId;
	}

	public void setThirdCategoryId(Integer thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	@Column(name = "trade_ype")
	public Integer getTradeYpe() {
		return this.tradeYpe;
	}

	public void setTradeYpe(Integer tradeYpe) {
		this.tradeYpe = tradeYpe;
	}

	@Column(name = "properties", length = 16777215)
	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Column(name = "cats_id")
	public Integer getCatsId() {
		return this.catsId;
	}

	public void setCatsId(Integer catsId) {
		this.catsId = catsId;
	}

	@Column(name = "cats_name", length = 20)
	public String getCatsName() {
		return this.catsName;
	}

	public void setCatsName(String catsName) {
		this.catsName = catsName;
	}

	@Column(name = "apply_spu")
	public Boolean getApplySpu() {
		return this.applySpu;
	}

	public void setApplySpu(Boolean applySpu) {
		this.applySpu = applySpu;
	}

	@Column(name = "supportSKUPrice")
	public Boolean getSupportSkuprice() {
		return this.supportSkuprice;
	}

	public void setSupportSkuprice(Boolean supportSkuprice) {
		this.supportSkuprice = supportSkuprice;
	}

	@Column(name = "support_online_trade")
	public Boolean getSupportOnlineTrade() {
		return this.supportOnlineTrade;
	}

	public void setSupportOnlineTrade(Boolean supportOnlineTrade) {
		this.supportOnlineTrade = supportOnlineTrade;
	}

	@Column(name = "spu_price_ext")
	public Boolean getSpuPriceExt() {
		return this.spuPriceExt;
	}

	public void setSpuPriceExt(Boolean spuPriceExt) {
		this.spuPriceExt = spuPriceExt;
	}

	@Column(name = "leaf")
	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "apply_real_price")
	public Boolean getApplyRealPrice() {
		return this.applyRealPrice;
	}

	public void setApplyRealPrice(Boolean applyRealPrice) {
		this.applyRealPrice = applyRealPrice;
	}

	@Column(name = "support_mix_wholesale")
	public Boolean getSupportMixWholesale() {
		return this.supportMixWholesale;
	}

	public void setSupportMixWholesale(Boolean supportMixWholesale) {
		this.supportMixWholesale = supportMixWholesale;
	}

	@Column(name = "batch_post")
	public Boolean getBatchPost() {
		return this.batchPost;
	}

	public void setBatchPost(Boolean batchPost) {
		this.batchPost = batchPost;
	}

}