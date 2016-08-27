package com.shenma.top.imagecopy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SaveTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "save_task")
public class SaveTask implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type;  //1.阿里巴巴   2.淘宝
	private String param;
	private String memberId;
	private Integer staus;
	private Integer batchType;

	// Constructors

	/** default constructor */
	public SaveTask() {
	}

	/** minimal constructor */
	public SaveTask(Integer id, Integer type, Integer staus,Integer batchType) {
		this.id = id;
		this.type = type;
		this.staus = staus;
		this.batchType=batchType;
	}

	/** full constructor */
	public SaveTask(Integer id, Integer type, String param, String memberId,
			Integer staus) {
		this.id = id;
		this.type = type;
		this.param = param;
		this.memberId = memberId;
		this.staus = staus;
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

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * {
	 *    "mqRecordId":1234,
	 *    "selfInfo":{
	 *    	""
	 *    },
	 *    "url":"http://www.163.com",
	 *    "isSavaPic":true
	 * }
	 * 
	 * 
	 * @return
	 */
	@Column(name = "param", length = 1000)
	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name = "member_id", length = 30)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "staus", nullable = false)
	public Integer getStaus() {
		return this.staus;
	}

	public void setStaus(Integer staus) {
		this.staus = staus;
	}
	
	@Column(name = "batch_type", nullable = false)
	public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

}