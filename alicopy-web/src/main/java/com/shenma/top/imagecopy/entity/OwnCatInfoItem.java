package com.shenma.top.imagecopy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OwnCatInfoItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "own_cat_info_item")
public class OwnCatInfoItem implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer catId;
	private String pathValues;
	private String properties;

	// Constructors

	/** default constructor */
	public OwnCatInfoItem() {
	}

	/** full constructor */
	public OwnCatInfoItem(Integer catId, String pathValues,
			String properties) {
		this.catId = catId;
		this.pathValues = pathValues;
		this.properties = properties;
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


	@Column(name = "cat_id")
	public Integer getCatId() {
		return this.catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	@Column(name = "path_values", length = 50)
	public String getPathValues() {
		return this.pathValues;
	}

	public void setPathValues(String pathValues) {
		this.pathValues = pathValues;
	}

	@Column(name = "properties", length = 4000)
	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

}