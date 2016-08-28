package com.shenma.aliutil.entity.goods;

/**
 * 产品属性信息
 * @author mircle
 *
 */
public class ProductFeatureInfo {
	private Integer propId;
	private String propName;
	private Integer propValueId;
	private String propValue;
	/**
	 * 属性ID。版本1不支持返回属性ID
	 * @return
	 */
	public Integer getPropId() {
		return propId;
	}
	public void setPropId(Integer propId) {
		this.propId = propId;
	}
	/**
	 * 属性名称
	 * @return
	 */
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	/**
	 * 属性值ID。版本1不支持返回属性值ID
	 * @return
	 */
	public Integer getPropValueId() {
		return propValueId;
	}
	public void setPropValueId(Integer propValueId) {
		this.propValueId = propValueId;
	}
	/**
	 * 属性值
	 * @return
	 */
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
}
