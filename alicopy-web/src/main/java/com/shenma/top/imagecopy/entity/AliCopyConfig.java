package com.shenma.top.imagecopy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AliCopyConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ali_copy_config")
public class AliCopyConfig implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String configName;
	private String userConfig;
	private String memberId;
	private String acountName;
	private Integer defaultStatus;

	// Constructors

	/** default constructor */
	public AliCopyConfig() {
	}

	/** full constructor */
	public AliCopyConfig(String configName, String userConfig, String memberId,
			String acountName, Integer defaultStatus) {
		this.configName = configName;
		this.userConfig = userConfig;
		this.memberId = memberId;
		this.acountName = acountName;
		this.defaultStatus = defaultStatus;
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

	@Column(name = "config_name", length = 20)
	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	@Column(name = "user_config", length = 2000)
	public String getUserConfig() {
		return this.userConfig;
	}

	public void setUserConfig(String userConfig) {
		this.userConfig = userConfig;
	}

	@Column(name = "member_id", length = 50)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "acount_name", length = 20)
	public String getAcountName() {
		return this.acountName;
	}

	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}

	@Column(name = "default_status")
	public Integer getDefaultStatus() {
		return this.defaultStatus;
	}

	public void setDefaultStatus(Integer defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

}