package com.shenma.aliutil.entity.album;

/**
 * 相册实体
 * @author mircle
 *
 */
public class Album {
	private String accountId;
	private String id;
	private String type;
	private String name;
	private String description;
	private String createDate;
	private Integer imageCount;
	private String coverPicUrl;
	private Integer coverPicId;
	
	/**
	 * 会员ID
	 * @return
	 */
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * 相册ID
	 * @return
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 相册类型.CUSTOM-自定义相册 MY-我的相册 OFF-下架相册
	 * @return
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 相册名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 相册描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 相册创建时间,格式为“yyyy-MM-dd”
	 * @return
	 */
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * 相册中图片数量
	 * @return
	 */
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	/**
	 * 相册封面图片URL
	 * @return
	 */
	public String getCoverPicUrl() {
		return coverPicUrl;
	}
	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}
	/**
	 * 相册封面图片ID
	 * @return
	 */
	public Integer getCoverPicId() {
		return coverPicId;
	}
	public void setCoverPicId(Integer coverPicId) {
		this.coverPicId = coverPicId;
	}
	
}
