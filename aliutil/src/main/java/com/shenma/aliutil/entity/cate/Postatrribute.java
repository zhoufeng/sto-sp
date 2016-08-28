package com.shenma.aliutil.entity.cate;

import java.util.List;


/**
 * 类目下所有发布属性信息
 * @author zhoufeng
 *
 */
public class Postatrribute {
	private Integer fid;
	private Integer parentId;
	private String unit;
	private List<FeatureIdValue> featureIdValues;
	private List<Integer> childrenFids;
	private String name;
	private String showType;
	private String isNeeded;
	private Integer order;
	private String fieldFlag;
	private String aspect;
	private String note;
	private String precomment;
	private String defaultValueId;
	private String defaultValue;
	private Boolean isKeyAttr;
	private Boolean isSpecAttr;
	private Boolean isSuggestType;
	private Boolean isSupportDefinedValues;
	private Boolean isSpecExtendedAttr;
	
	

	/**
	 * 产品属性ID
	 * @return
	 */
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	/**
	 * 父属性ID
	 * @return
	 */
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 产品属性单位
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 产品属性枚举值。当‘填写类型’为单选或者多选时，该变量有效。多个枚举值之间使用;号（半角分号）分隔
	 * @return
	 */

	public List<Integer> getChildrenFids() {
		return childrenFids;
	}
	public void setChildrenFids(List<Integer> childrenFids) {
		this.childrenFids = childrenFids;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 填写类型枚举值。 -1: 数字输入框; 0: 文本输入框（input）;1=下拉（list_box）;2=多选（check_box）;3=单选（radio）
	 * @return
	 */
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getIsNeeded() {
		return isNeeded;
	}
	public void setIsNeeded(String isNeeded) {
		this.isNeeded = isNeeded;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getFieldFlag() {
		return fieldFlag;
	}
	public void setFieldFlag(String fieldFlag) {
		this.fieldFlag = fieldFlag;
	}
	public String getAspect() {
		return aspect;
	}
	public void setAspect(String aspect) {
		this.aspect = aspect;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPrecomment() {
		return precomment;
	}
	public void setPrecomment(String precomment) {
		this.precomment = precomment;
	}
	public String getDefaultValueId() {
		return defaultValueId;
	}
	public void setDefaultValueId(String defaultValueId) {
		this.defaultValueId = defaultValueId;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<FeatureIdValue> getFeatureIdValues() {
		return featureIdValues;
	}
	public void setFeatureIdValues(List<FeatureIdValue> featureIdValues) {
		this.featureIdValues = featureIdValues;
	}
	public Boolean getIsKeyAttr() {
		return isKeyAttr;
	}
	public void setIsKeyAttr(Boolean isKeyAttr) {
		this.isKeyAttr = isKeyAttr;
	}
	public Boolean getIsSpecAttr() {
		return isSpecAttr;
	}
	public void setIsSpecAttr(Boolean isSpecAttr) {
		this.isSpecAttr = isSpecAttr;
	}
	public Boolean getIsSuggestType() {
		return isSuggestType;
	}
	public void setIsSuggestType(Boolean isSuggestType) {
		this.isSuggestType = isSuggestType;
	}
	public Boolean getIsSupportDefinedValues() {
		return isSupportDefinedValues;
	}
	public void setIsSupportDefinedValues(Boolean isSupportDefinedValues) {
		this.isSupportDefinedValues = isSupportDefinedValues;
	}
	public Boolean getIsSpecExtendedAttr() {
		return isSpecExtendedAttr;
	}
	public void setIsSpecExtendedAttr(Boolean isSpecExtendedAttr) {
		this.isSpecExtendedAttr = isSpecExtendedAttr;
	}
	
	
	

	
}
