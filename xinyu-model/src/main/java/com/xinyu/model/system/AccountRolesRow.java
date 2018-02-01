package com.xinyu.model.system;


import com.xinyu.model.common.Entity;
import com.xinyu.model.system.enums.StoreModelEnums;

/**
 * 用户角色与菜单项关联对象
 * 一个用户可以有多个角色，每次查询菜单功能通过用户ID与角色ID来查询所属的菜单项
 */

public class AccountRolesRow extends Entity {
	
	
	private AccountRoles roles;//角色
	
	private AccountMenu menu;
	
	private String remark;


	public AccountMenu getMenu() {
		return menu;
	}


	public void setMenu(AccountMenu menu) {
		this.menu = menu;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	public AccountRoles getRoles() {
		return roles;
	}

	public void setRoles(AccountRoles roles) {
		this.roles = roles;
	}



}
