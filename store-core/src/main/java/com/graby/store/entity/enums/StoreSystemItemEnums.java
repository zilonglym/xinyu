package com.graby.store.entity.enums;

/**
 * 系统数据类型枚举
 * @author lenovo
 *
 */
public enum StoreSystemItemEnums {
	LOGISTICS("LOGISTICS","快递信息"),
	SESSIONKEY("sessionKey","淘宝session通行证"),
	WAYBILL("waybill","快递发货模版种类"),
	CHECK_COMP("CHECK_COMP","快递公司检验"),
	BATCHQUANTITY("batchquantity","批次数量")
;
	
	private String key;
	private String description;
	
	private StoreSystemItemEnums(String key,String description){
		this.key=key;
		this.description=description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
