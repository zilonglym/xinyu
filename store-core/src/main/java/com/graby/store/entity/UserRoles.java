package com.graby.store.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Index;

import com.google.common.collect.ImmutableList;

/**
 * 用户角色权限
 * @author huabiao.mahb
 */

@Entity
@Table(name = "sc_user_roles")
public class UserRoles implements Serializable {

	private static final long serialVersionUID = -8507187887053476915L;
	
	private Long id;
	
	private User user;
	
	private String name;//权限名
	
	private String remark;//描述
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public void setId(Long id) {
		this.id = id;
	}

}
