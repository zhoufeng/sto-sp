package com.shenma.aliutil.entity.member;

/**
 * 
 * 会员详细信息
 * @author mircle
 *
 */
public class MemberInfo {

	private String memberId;
	private String status;
	private Boolean isTP;
	private Boolean haveSite;
	private Boolean isPersonalTP;
	private Boolean isEnterpriseTP;
	private Boolean isMarketTP;
	private Boolean isETCTP;
	private Boolean haveDistribution;
	private Boolean isDistribution;
	private Boolean isMobileBind;
	private Boolean isEmailBind;
	private Boolean havePrecharge;
	private Boolean isPrecharge;
	private Boolean isCreditProtection;
	private Boolean isPublishedCompany;
	private Boolean isAlipayBind;
	private String personalFileAddress;
	private String winportAddress;
	private String domainAddress;
	private Integer trustScore;
	private String trustProductAddress;
	private String companyAddress;
	private String certificateStatus;
	private String createTime;
	private String lastLogin;
	private String companyName;
	private String industry;
	private String product;
	private String homepageUrl;
	private String sellerName;
	private String sex;
	private String department;
	private String openJobTitle;
	private String email;
	private String telephone;
	private String fax;
	private String mobilePhone;
	private String addressLocation;
	
	
	/**
	 * 会员ID
	 * @return
	 */
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * 会员状态。Enabled：有效; Disabled：无效
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 会员账户类型。true：诚信通会员；false：免费账户会员
	 * @return
	 */
	public Boolean getIsTP() {
		return isTP;
	}
	public void setIsTP(Boolean isTP) {
		this.isTP = isTP;
	}
	/**
	 * 是否开通了网站。true：已开通；false：未开通
	 * @return
	 */
	public Boolean getHaveSite() {
		return haveSite;
	}

	public void setHaveSite(Boolean haveSite) {
		this.haveSite = haveSite;
	}
	/**
	 * 是否个人诚信通。true：个人诚信通会员；false：非个人诚信通会员
	 * @return
	 */
	public Boolean getIsPersonalTP() {
		return isPersonalTP;
	}
	public void setIsPersonalTP(Boolean isPersonalTP) {
		this.isPersonalTP = isPersonalTP;
	}
	/**
	 * 是否企业诚信通。true：企业诚信通会员；false：非企业诚信通会员
	 * @return
	 */
	public Boolean getIsEnterpriseTP() {
		return isEnterpriseTP;
	}
	public void setIsEnterpriseTP(Boolean isEnterpriseTP) {
		this.isEnterpriseTP = isEnterpriseTP;
	}
	/**
	 * 是否专业市场诚信通会员。true：专业市场诚信通会员；false：非专业市场诚信通会员
	 * @return
	 */
	public Boolean getIsMarketTP() {
		return isMarketTP;
	}
	public void setIsMarketTP(Boolean isMarketTP) {
		this.isMarketTP = isMarketTP;
	}
	/**
	 * 是否ETC会员。true：ETC海外诚信通会员；false：非ETC海外诚信通会员
	 * @return
	 */
	public Boolean getIsETCTP() {
		return isETCTP;
	}
	public void setIsETCTP(Boolean isETCTP) {
		this.isETCTP = isETCTP;
	}
	/**
	 * 会员是否具有开通分销平台资格。true：具有分销平台资格；false：没有分销平台资格
	 * @return
	 */
	public Boolean getHaveDistribution() {
		return haveDistribution;
	}
	public void setHaveDistribution(Boolean haveDistribution) {
		this.haveDistribution = haveDistribution;
	}
	/**
	 * 会员是否已经开通分销平台。true：已开通；false：未开通
	 * @return
	 */
	public Boolean getIsDistribution() {
		return isDistribution;
	}
	public void setIsDistribution(Boolean isDistribution) {
		this.isDistribution = isDistribution;
	}
	/**
	 * 是否绑定了手机登录。true：是；false：否
	 * @return
	 */
	public Boolean getIsMobileBind() {
		return isMobileBind;
	}
	public void setIsMobileBind(Boolean isMobileBind) {
		this.isMobileBind = isMobileBind;
	}
	/**
	 * 是否绑定了Email登录。true：是；false：否
	 * @return
	 */
	public Boolean getIsEmailBind() {
		return isEmailBind;
	}
	public void setIsEmailBind(Boolean isEmailBind) {
		this.isEmailBind = isEmailBind;
	}
	/**
	 * 是否具有预存款的权限。true：具有预存款权限；false：没有预存款权限
	 * @return
	 */
	public Boolean getHavePrecharge() {
		return havePrecharge;
	}
	public void setHavePrecharge(Boolean havePrecharge) {
		this.havePrecharge = havePrecharge;
	}
	/**
	 * 是否已经开通预存款服务。true：已开通；false：未开通
	 * @return
	 */
	public Boolean getIsPrecharge() {
		return isPrecharge;
	}
	public void setIsPrecharge(Boolean isPrecharge) {
		this.isPrecharge = isPrecharge;
	}
	/**
	 * 是否参加诚信保障。true：参加诚信保障；false：没有参加诚信保障
	 * @return
	 */
	public Boolean getIsCreditProtection() {
		return isCreditProtection;
	}
	public void setIsCreditProtection(Boolean isCreditProtection) {
		this.isCreditProtection = isCreditProtection;
	}
	/**
	 * 是否已发布公司库。true：已发布；false：未发布
	 * @return
	 */
	public Boolean getIsPublishedCompany() {
		return isPublishedCompany;
	}
	public void setIsPublishedCompany(Boolean isPublishedCompany) {
		this.isPublishedCompany = isPublishedCompany;
	}
	/**
	 * 是否绑定支付宝账户。true：已经绑定了支付宝；false：没有绑定支付宝
	 * @return
	 */
	public Boolean getIsAlipayBind() {
		return isAlipayBind;
	}
	public void setIsAlipayBind(Boolean isAlipayBind) {
		this.isAlipayBind = isAlipayBind;
	}
	/**
	 * 会员档案地址
	 * @return
	 */
	public String getPersonalFileAddress() {
		return personalFileAddress;
	}
	public void setPersonalFileAddress(String personalFileAddress) {
		this.personalFileAddress = personalFileAddress;
	}
	/**
	 * 商铺地址
	 * @return
	 */
	public String getWinportAddress() {
		return winportAddress;
	}
	public void setWinportAddress(String winportAddress) {
		this.winportAddress = winportAddress;
	}
	/**
	 * 旺铺地址
	 * @return
	 */
	public String getDomainAddress() {
		return domainAddress;
	}
	public void setDomainAddress(String domainAddress) {
		this.domainAddress = domainAddress;
	}
	/**
	 * 诚信通指数。只有TP会员才有效
	 * @return
	 */
	public Integer getTrustScore() {
		return trustScore;
	}
	public void setTrustScore(Integer trustScore) {
		this.trustScore = trustScore;
	}
	/**
	 * 诚信档案地址。只有TP会员才有效
	 * @return
	 */
	public String getTrustProductAddress() {
		return trustProductAddress;
	}
	public void setTrustProductAddress(String trustProductAddress) {
		this.trustProductAddress = trustProductAddress;
	}
	/**
	 * 公司库地址
	 * @return
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * 会员认证状态。
	 * 1、如果该会员没有经过认证返回"认证信息：未经过第三方专业机构认证" 
	 * 2、如果该会员经过了个人身份信息的实名认证返回"个人身份信息：已通过实名认证" 
	 * 3、如果该会员经过了工商注册信息经过了第三方认证机构的认证，则返回"工商注册信息：已通过{第三方认证机构名称}认证"
	 * @return
	 */
	public String getCertificateStatus() {
		return certificateStatus;
	}
	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}
	/**
	 * 注册时间
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 最近登录时间
	 * @return
	 */
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	/**
	 * 公司信息-公司名称
	 * @return
	 */
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 公司信息-主营行业
	 * @return
	 */
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	/**
	 * 公司信息-主营产品
	 * @return
	 */
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * 公司信息-网页地址
	 * @return
	 */
	public String getHomepageUrl() {
		return homepageUrl;
	}
	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}
	/**
	 * 联系信息-姓名
	 * @return
	 */
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	/**
	 * 联系信息-性别
	 * @return
	 */
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 联系信息-部门
	 * @return
	 */
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * 联系信息-职位
	 * @return
	 */
	public String getOpenJobTitle() {
		return openJobTitle;
	}
	public void setOpenJobTitle(String openJobTitle) {
		this.openJobTitle = openJobTitle;
	}
	/**
	 * 联系信息-电子邮箱
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 联系信息-司电话
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 联系信息-传真
	 * @return
	 */
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 联系信息-手机
	 * @return
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * 联系信息-所在地信息
	 * @return
	 */
	public String getAddressLocation() {
		return addressLocation;
	}
	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}
	
}
