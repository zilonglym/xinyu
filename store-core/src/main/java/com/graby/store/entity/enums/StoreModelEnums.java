package com.graby.store.entity.enums;

/**
 * 功能模块划分枚举
 * @author yangmin
 *
 */
public enum StoreModelEnums {
	TRIAL_TRADE("TRIAL_TRADE","审单"),
	PRINT_TRADE("PRINT_TRADE","打单")
	
	;
	private StoreModelEnums(String key,String title){
		this.key=key;
		this.title=title;
	}
	
	private String key;
	private String title;
	public String getKey() {
		return key;
	}
	public String getTitle() {
		return title;
	}
	
	

}
