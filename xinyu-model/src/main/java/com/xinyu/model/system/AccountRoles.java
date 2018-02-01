package com.xinyu.model.system;

import com.xinyu.model.common.Entity;


/**
 * 用户角色对象，只存放角色的基础描述
 */

public class AccountRoles extends Entity {

	
	private String name;//权限名
	
	private String remark;//描述
	
	
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

}
