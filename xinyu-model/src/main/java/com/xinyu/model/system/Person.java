package com.xinyu.model.system;
import java.util.Date;

import com.xinyu.model.common.Entity;


/**
 * 人员信息
 * @author yangmin
 */

public class Person extends Entity {

	private String name;
	
	private String sex;
	
	private String age;
	
	private String roles;
	
	private String phone;
	
	private String idCard;
	
	private Date inputDate;
	
	private Long centroId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	public Long getCentroId() {
		return centroId;
	}

	public void setCentroId(Long centroId) {
		this.centroId = centroId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
