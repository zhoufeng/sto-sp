package com.shenma.aliutil.entity.album;

/**
 * 会员信息
 * @author mircle
 *
 */
public class Profile {
	private String accountId;
	private Integer usedSpace;
	private Integer totalSpace;
	private String isFull;
	private double spaceUsage;
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
	 * 已使用空间，单位为字节
	 * @return
	 */
	public Integer getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(Integer usedSpace) {
		this.usedSpace = usedSpace;
	}
	/**
	 * 总空间，单位为字节
	 * @return
	 */
	public Integer getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Integer totalSpace) {
		this.totalSpace = totalSpace;
	}
	/**
	 * 空间是否已满，Y：已满      N：未满
	 * @return
	 */
	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}
	public void setSpaceUsage(double spaceUsage) {
		this.spaceUsage = spaceUsage;
	}

	
	/**
	 * 已使用空间占总空间百分比，精确到小数点后两位。如占用30.31%，返回值为30.31
	 * @return
	 */
	public double getSpaceUsage() {
		return spaceUsage;
	}
	public String getIsFull() {
		return isFull;
	}
	
	
}
