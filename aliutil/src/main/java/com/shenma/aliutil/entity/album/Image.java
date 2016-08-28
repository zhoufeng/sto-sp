package com.shenma.aliutil.entity.album;

public class Image {
	private String accountId;
	private Long id;
	private String name;
	private String description;
	private String url;
	private String urlMini;
	private String url310x310;
	private String url220x220;
	private String url150x150;
	private String url64x64;
	private String createTime;
	private Integer size;
	private Integer albumId;
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
	 * 图片ID
	 * @return
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 图片名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 图片描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 图片URL(原图)，图片的相对路径（即除去http://server:port部分，如:img/ibank/15/02/60/15026073.jpg）
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 图片URL(缩略图)，图片的相对路径
	 * @return
	 */
	public String getUrlMini() {
		return urlMini;
	}
	public void setUrlMini(String urlMini) {
		this.urlMini = urlMini;
	}
	/**
	 * 图片URL(310x310)
	 * @return
	 */
	public String getUrl310x310() {
		return url310x310;
	}
	public void setUrl310x310(String url310x310) {
		this.url310x310 = url310x310;
	}
	/**
	 * 图片URL(220x220)
	 * @return
	 */
	public String getUrl220x220() {
		return url220x220;
	}
	public void setUrl220x220(String url220x220) {
		this.url220x220 = url220x220;
	}
	/**
	 * 图片URL(150x150)
	 * @return
	 */
	public String getUrl150x150() {
		return url150x150;
	}
	public void setUrl150x150(String url150x150) {
		this.url150x150 = url150x150;
	}
	/**
	 * 图片URL(64x64)
	 * @return
	 */
	public String getUrl64x64() {
		return url64x64;
	}
	public void setUrl64x64(String url64x64) {
		this.url64x64 = url64x64;
	}
	/**
	 * 图片上传时间
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 图片大小，单位为字节
	 * @return
	 */
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	/**
	 * 图片所属相册
	 * @return
	 */
	public Integer getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}
	
}
