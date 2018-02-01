package com.graby.store.entity.enums;

/**
 * 入库单状态枚举
 * @author lenovo
 *
 */
public enum StorageStatusEnums {
	
	ENTRY_CHECK_FINISH("ENTRY_CHECK_FINISH","盘点入库成功"),
	ENTRY_FINISH("ENTRY_FINISH","入库成功"),
	ENTRY_DELIVERY("ENTRY_DELIVERY","出库单"),
	ENTRY_WAIT_SELLER_SEND("ENTRY_WAIT_SELLER_SEND","")
	;
	
	private StorageStatusEnums(String key,String description){
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
