package com.graby.store.entity.store;

/**
 * 用户类型枚举
 * @author lenovo
 *
 */
public enum UserTypeEnums {
	NORMAL("NORMAL","标准"),
	ADVANCED("ADVANCED","高级"),
	VIP("VIP","VIP")
	;
	private UserTypeEnums(String key,String description){
		this.key=key;
		this.description=description;
	}
	private String key;
	private String description;
	public String getKey() {
		return key;
	}
	public String getDescription() {
		return description;
	}
	
	
}
