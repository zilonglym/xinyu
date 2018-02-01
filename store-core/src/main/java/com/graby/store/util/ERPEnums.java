package com.graby.store.util;

/**
 * ERP系统枚举
 * @author shark
 *
 */
public enum ERPEnums {
	FURUN("FUNRUN","富润"),
	WANGQUBAO("WANGQUBAO","网渠宝")
	;
	
	private ERPEnums(String key,String description){
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
