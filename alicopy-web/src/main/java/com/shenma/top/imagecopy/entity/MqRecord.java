package com.shenma.top.imagecopy.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MqRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mq_record")
public class MqRecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private Long taskNum;
	private Integer taskCount;
	private Integer taskOfferCount;
	private List<String> urlList;
	
	
	// Constructors

	/** default constructor */
	public MqRecord() {
	}

	/** full constructor */
	public MqRecord(String userId, Long taskNum,Integer taskCount,Integer taskOfferCount) {
		this.userId = userId;
		this.taskNum = taskNum;
		this.taskCount=taskCount;
		this.taskOfferCount=taskOfferCount;
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

	@Column(name = "task_num")
	public Long getTaskNum() {
		return this.taskNum;
	}

	public void setTaskNum(Long taskNum) {
		this.taskNum = taskNum;
	}
	@Transient
	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	@Column(name = "task_count")
	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}
	@Column(name = "task_offer_count")
	public Integer getTaskOfferCount() {
		return taskOfferCount;
	}

	public void setTaskOfferCount(Integer taskOfferCount) {
		this.taskOfferCount = taskOfferCount;
	}


	
	
}