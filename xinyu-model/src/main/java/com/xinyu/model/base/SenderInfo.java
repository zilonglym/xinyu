package com.xinyu.model.base;

import com.xinyu.model.common.Entity;



/**
 * 
 * @author shark_cj
 * @since2017-04-25
 * <pre>
 *   发件人信息          
 * </pre>
 */
public class SenderInfo  extends Entity {
	
    /**
     *FALSE	938900	发件方邮编	zipCode
     */
	private String senderZipCode ;
    /**
     *FALSE	中国	      发件方国家	countryCode
     */
	private  String  senderCountry;
    /**
     *FALSE	四川	发件方省份 国际地址可能没有	province
     */
	private  String  senderProvince;
    /**
     * FALSE	成都	发件方城市	city
     */
	private String  senderCity;
    /**
     * FALSE	金牛	发件方区县	area
     */
	private String  senderArea;
	/**
	 * FALSE	**	发件方镇	town
	 */
	private String senderTown;
    /**
     *TRUE	****	发件方地址	detailAddress
     */
	private String  senderAddress;
    /**
     *FALSE	　	发件方最小区划ID	新增
     */
	private String  senderDivisionId;
	/**
	 *  TRUE	李四	发件方名称  （采购入库放供应商名称）， （销退入库填买家名称）， （调拨入库填写调拨出库仓库联系人名称）	name
	 */
	private String senderName;
    /**
     *  FALSE	139880038**	发件方手机,手机和电话不能同时为空	mobile
     */
	private String  senderMobile; //手机
	
    /**
     * FALSE	028-28200288	发件方电话，手机和电话不能同时为空	tel
     */
	private String  senderPhone; //座机
	
	/**
	 * FALSE	028-28200288	发件方email	email
	 */
	private  String  senderEmail;

	public String getSenderZipCode() {
		return senderZipCode;
	}

	public void setSenderZipCode(String senderZipCode) {
		this.senderZipCode = senderZipCode;
	}

	public String getSenderCountry() {
		return senderCountry;
	}

	public void setSenderCountry(String senderCountry) {
		this.senderCountry = senderCountry;
	}

	public String getSenderProvince() {
		return senderProvince;
	}

	public void setSenderProvince(String senderProvince) {
		this.senderProvince = senderProvince;
	}

	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	public String getSenderArea() {
		return senderArea;
	}

	public void setSenderArea(String senderArea) {
		this.senderArea = senderArea;
	}

	public String getSenderTown() {
		return senderTown;
	}

	public void setSenderTown(String senderTown) {
		this.senderTown = senderTown;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderDivisionId() {
		return senderDivisionId;
	}

	public void setSenderDivisionId(String senderDivisionId) {
		this.senderDivisionId = senderDivisionId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderMobile() {
		return senderMobile;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

}
