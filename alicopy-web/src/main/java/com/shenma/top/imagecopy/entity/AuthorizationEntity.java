package com.shenma.top.imagecopy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AuthorizationEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "authorization_entity")
public class AuthorizationEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String memberId;
	private String accessToken;
	private String refreshToken;
	private Long expiresIn;
	private Date expiresTime;
	private String addressLocation;
	private String companyName;
	private String sellerName;
	private String sex;
	private String mobilePhone;

	// Constructors

	/** default constructor */
	public AuthorizationEntity() {
	}

	/** full constructor */
	public AuthorizationEntity(String memberId, String accessToken,
			String refreshToken, Long expiresIn, Date expiresTime,
			String addressLocation, String companyName, String sellerName,
			String sex, String mobilePhone) {
		this.memberId = memberId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.expiresTime = expiresTime;
		this.addressLocation = addressLocation;
		this.companyName = companyName;
		this.sellerName = sellerName;
		this.sex = sex;
		this.mobilePhone = mobilePhone;
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

	@Column(name = "member_id", length = 50)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "access_token", length = 50)
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Column(name = "refresh_token")
	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Column(name = "expires_in")
	public Long getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Column(name = "expires_time", length = 8)
	public Date getExpiresTime() {
		return this.expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	@Column(name = "address_location", length = 100)
	public String getAddressLocation() {
		return this.addressLocation;
	}

	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

	@Column(name = "company_name", length = 50)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "seller_name", length = 20)
	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	@Column(name = "sex", length = 5)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "mobilePhone", length = 20)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}