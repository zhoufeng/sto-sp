package com.shenma.aliutil.entity.goods;

/**
 * 价格区间
 * @author mircle
 *
 */
public class PriceRangeInfo {
	private Integer range;
	private double convertPrice;
	private double price;
	/**
	 * 最小起订量。
	 * @return
	 */
	public Integer getRange() {
		return range;
	}
	public void setRange(Integer range) {
		this.range = range;
	}
	public double getConvertPrice() {
		return convertPrice;
	}
	public void setConvertPrice(double convertPrice) {
		this.convertPrice = convertPrice;
	}
	/**
	 * 价格。商品批发价格
	 * @return
	 */
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
