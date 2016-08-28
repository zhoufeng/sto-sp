package com.shenma.aliutil.entity.cate;

import java.util.List;

/**
 * 发布类目信息
 * @author zhoufeng
 *
 */
public class OffercatsInfo {
	private Integer catsId;
	private String catsName;
	private Integer tradeType;
	private boolean leaf;
	private String catsDescription;
	private List<ParentCatInfo> parentCats;
	private Integer order;
	private Integer parentCatsId;
	/**
	 * 类目ID
	 * @return
	 */
	public Integer getCatsId() {
		return catsId;
	}
	public void setCatsId(Integer catsId) {
		this.catsId = catsId;
	}
	/**
	 * 类目名称
	 * @return
	 */
	public String getCatsName() {
		return catsName;
	}
	public void setCatsName(String catsName) {
		this.catsName = catsName;
	}
	/**
	 * 发布类型。1=product=产品，2=process=加工，3=agent=代理，4=cooperation=合作，5=business=商务服务
	 * @return
	 */
	public Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * 是否叶子类目
	 * @return
	 */
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * 类目描述
	 * @return
	 */
	public String getCatsDescription() {
		return catsDescription;
	}
	public void setCatsDescription(String catsDescription) {
		this.catsDescription = catsDescription;
	}
	/**
	 * 父类目数组，包含order与parentCatsId两个元素
	 * @return
	 */
	public List<ParentCatInfo> getParentCats() {
		return parentCats;
	}
	public void setParentCats(List<ParentCatInfo> parentCats) {
		this.parentCats = parentCats;
	}
	/**
	 * 父类目排序号
	 * @return
	 */
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 父类目ID
	 * @return
	 */
	public Integer getParentCatsId() {
		return parentCatsId;
	}
	public void setParentCatsId(Integer parentCatsId) {
		this.parentCatsId = parentCatsId;
	}
	
	
}
