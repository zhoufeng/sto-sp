package com.bohusoft.alicopy.parse;

import java.util.List;
import java.util.Map;

/**
 * 详情解析vo
 * 数据格式
 * {
 * "subject":"2015年最新款茶壶",
 * "zhutuList":["http://xxx.jpg","http://xxx3.jpg"],
 * "skuImgList:["http://xxx.jpg","http://xxx3.jpg"]
 * "xiangqinImgList":["http://xxx.jpg","http://xxx3.jpg"],
 * "desc:"具体的详情",
 * "price":"1,54.00:10:50:35,48"; //或者直接填
 * "skuProps":[{"prop":"颜色","value":[{"name":"黑色","imageUrl":"http://xx.jpg"},{"name":"花色"}]}, {"prop":"尺码","value":[{"name":"S"},{"name":"M"},{"name":"L"},{"name":"XL"},{"name":"XXL"}]}],
 * "skuMap":{"花色>L":{"canBookCount":1936,"saleCount":44,"specId":"2dc061e28323b25af3bce4459199c8e4"},"花色>M":{"canBookCount":1938,"saleCount":49,"specId":"305feeaa1899a0a2c1ade61a640f31b1"}
 * "baseproperties":{"建议零售价":"94","是否库存":"是"},
 * "otherProperties":{
 *     "skuTradeSupport":true,
 *     "beginAmount":1
 * }
 * }
 * @author zhoufeng
 * @ClassName DetailHtmlParseBean
 * @Version 1.0.0
 */
public class DetailHtmlParseBean {
	private String subject;
	private List<String> zhutuList;
	private List<String> xiangqinImgList;
	private String desc;
	private List<Map<String,Object>> skuProps;
	private Map<String,Object> skuMap;
	private Map<String,String> baseproperties;
	private Map<String,Object> otherProperties;
	private String price;
	private Integer priceType; // 1.按数量报价      2.按规格报价  
	private Integer catsId; 
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getZhutuList() {
		return zhutuList;
	}
	public void setZhutuList(List<String> zhutuList) {
		this.zhutuList = zhutuList;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Map<String, String> getBaseproperties() {
		return baseproperties;
	}
	public void setBaseProperties(Map<String, String> properties) {
		this.baseproperties = properties;
	}
	public Map<String, Object> getOtherProperties() {
		return otherProperties;
	}
	public void setOtherProperties(Map<String, Object> otherProperties) {
		this.otherProperties = otherProperties;
	}
	public void setBaseproperties(Map<String, String> baseproperties) {
		this.baseproperties = baseproperties;
	}
	public List<Map<String, Object>> getSkuProps() {
		return skuProps;
	}
	public void setSkuProps(List<Map<String, Object>> skuProps) {
		this.skuProps = skuProps;
	}
	public Map<String, Object> getSkuMap() {
		return skuMap;
	}
	public void setSkuMap(Map<String, Object> skuMap) {
		this.skuMap = skuMap;
	}
	public List<String> getXiangqinImgList() {
		return xiangqinImgList;
	}
	public void setXiangqinImgList(List<String> xiangqinImgList) {
		this.xiangqinImgList = xiangqinImgList;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getPriceType() {
		return priceType;
	}
	/**
	 * 1.按数量报价      2.按规格报价  
	 * @param priceType
	 */
	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}
	public Integer getCatsId() {
		return catsId;
	}
	public void setCatsId(Integer catsId) {
		this.catsId = catsId;
	}

	
}
