package com.xinyu.check.model;

import java.io.Serializable;

/**
 * 仓库中心(湘潭中心/..)
 * 
 * @author huabiao.mahb
 */
public class Centro  implements Serializable{

	private static final long serialVersionUID = 7855809701869500049L;
	
	private Long id;
	//编码
	private String code;
	// 标题
	private String name;
	// 仓库地址
	private String address;
	//顺丰发货地址
	private String sfAddress;
	
	// 联系人
	private String person;
	//仓库所在省
	private String province;
	//仓库所在市 
	private String city;
	//仓库所在地区
	private String area;
	// 联系电话
	private String phone;
	private String account;//关联帐号
	
	private Centro parent; //父仓库
	
	private int root;  //是否一级仓库
	
	private String cityStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Centro getParent() {
		return parent;
	}

	public void setParent(Centro parent) {
		this.parent = parent;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public String getSfAddress() {
		return sfAddress;
	}

	public void setSfAddress(String sfAddress) {
		this.sfAddress = sfAddress;
	}
	
}
