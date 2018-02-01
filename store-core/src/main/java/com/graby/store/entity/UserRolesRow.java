package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.graby.store.entity.enums.StoreModelEnums;

/**
 * 用户数据权限
 * @author yangmin
 */

@Entity
@Table(name = "sc_user_roles_row")
public class UserRolesRow implements Serializable {

	private static final long serialVersionUID = 5212596816393442667L;
	
	private Long id;
	
	private Person person;//用户
	
	private UserRoles roles;//商家
	
	private StoreModelEnums modelEnums;
	
	private String remark;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	public StoreModelEnums getModelEnums() {
		return modelEnums;
	}

	public void setModelEnums(StoreModelEnums modelEnums) {
		this.modelEnums = modelEnums;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public UserRoles getRoles() {
		return roles;
	}

	public void setRoles(UserRoles roles) {
		this.roles = roles;
	}
}
