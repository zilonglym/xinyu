package com.xinyu.task.dao.base;

/**
 * 不同的城市采用不同的同步qfang的形�?
 * @author Administrator
 *
 */
public enum QfangModeEnum {
	
	O2O("020"),
	PORT("端口");

	private String description ;
	
	private QfangModeEnum(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
