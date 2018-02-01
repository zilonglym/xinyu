package com.xinyu.model.system;

import java.util.Date;

import com.xinyu.model.common.Entity;
/**
 * 帐号表
 * @author yangmin
 *
 */
public class Account extends Entity {

	private static final long serialVersionUID = -5055007960586654380L;
	
	private String userName;//用户名
	
	private String password;//密码
	
	private String shortName;//简称
	
	private String mobile;
	
	private String phone;
	
	private String email;
	
	private Date createTime;//创建日期
	public Account(){
		super();
	}
	public Account(String id){
		super.id=id;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
