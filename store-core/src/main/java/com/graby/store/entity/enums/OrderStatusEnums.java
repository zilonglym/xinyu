package com.graby.store.entity.enums;

/**
 * 订单状态
 * @author yangmin
 * 2017年10月10日
 *
 */
public enum OrderStatusEnums {
	WMS_ACCEPT("WMS_ACCEPT","仓库接单,现在审核操作后状态改成这个"),
	WMS_AUDIT("WMS_AUDIT","仓库审单"),	
	WMS_GETNO("WMS_GETNO","仓库取单号"),
	WMS_PRINT("WMS_PRINT","仓库已打印"),	
	WMS_CONFIRM("WMS_CONFIRM","仓库已确认"),	
	WMS_FINASH("WMS_FINASH","完成");
	
	private OrderStatusEnums(String key,String description){
		this.key=key;
		this.description=description;
	}
	private String key;
	private String description;
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
