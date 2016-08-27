package com.shenma.top.imagecopy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MqRecordItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mq_record_item")
public class MqRecordItem implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer recordId;
	private String userId;
	private String zhutuImage;
	private String title;
	private String url;
	private Integer status;   //0 正在复制    1.复制成功   2.复制失败   10.正在排队复制
	private String offerId;
	private String errorMsg;
	private Integer picStatus;
	private Date createTime;
	private Date endTime;
	private String userName;
	private String acountName;
	private String oldOfferId;
	private Integer delStatus;
	

	// Constructors

	/** default constructor */
	public MqRecordItem() {
	}

	/** minimal constructor */
	public MqRecordItem(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public MqRecordItem(Integer id, String userId, String url) {
		this.id = id;
		this.userId = userId;
		this.url = url;
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

	@Column(name = "user_id", length = 30)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "record_id")
	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	@Column(name = "error_msg")
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@Column(name = "pic_status")
	public Integer getPicStatus() {
		return picStatus;
	}

	public void setPicStatus(Integer picStatus) {
		this.picStatus = picStatus;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "offer_id")
	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "old_offer_id")
	public String getOldOfferId() {
		return oldOfferId;
	}

	public void setOldOfferId(String oldOfferId) {
		this.oldOfferId = oldOfferId;
	}
	@Column(name = "del_status")
	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	@Column(name = "acount_name")
	public String getAcountName() {
		return acountName;
	}

	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}
	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "zhutu_image")
	public String getZhutuImage() {
		return zhutuImage;
	}

	public void setZhutuImage(String zhutuImage) {
		this.zhutuImage = zhutuImage;
	}

	
}