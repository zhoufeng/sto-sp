package com.shenma.top.imagecopy.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CollectProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "collect_product")
public class CollectProduct implements java.io.Serializable {

	// Fields

	private Long id;
	private String productName;
	private String productUrl;
	private String productPrice;
	private String catId;
	private Integer sourceType;
	private String sourceName;
	private String imageUrl;
	private String userId;
	private String properties;
	private Date gmtCreate;

	// Constructors

	/** default constructor */
	public CollectProduct() {
	}

	/** full constructor */
	public CollectProduct(String productName, String productUrl,
			String productPrice, String catId, Integer sourceType,
			String sourceName, String imageUrl, String userId,
			String properties, Date gmtCreate) {
		this.productName = productName;
		this.productUrl = productUrl;
		this.productPrice = productPrice;
		this.catId = catId;
		this.sourceType = sourceType;
		this.sourceName = sourceName;
		this.imageUrl = imageUrl;
		this.userId = userId;
		this.properties = properties;
		this.gmtCreate = gmtCreate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "product_name", length = 50)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_url", length = 200)
	public String getProductUrl() {
		return this.productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	@Column(name = "product_price", length = 14)
	public String getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	@Column(name = "cat_id", length = 20)
	public String getCatId() {
		return this.catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	@Column(name = "source_type")
	public Integer getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "source_name", length = 12)
	public String getSourceName() {
		return this.sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Column(name = "image_url", length = 100)
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "user_id", length = 30)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "properties", length = 16777215)
	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_create", length = 10)
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}