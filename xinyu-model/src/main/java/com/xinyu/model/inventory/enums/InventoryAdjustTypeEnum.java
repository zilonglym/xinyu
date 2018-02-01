package com.xinyu.model.inventory.enums;

/**
 * 库存调整单据类型
 * */
public enum InventoryAdjustTypeEnum {

	FORWORDDEFECTIVE("0","正转残"),
	FORWORDNORMAL("1","残转正");
	
	public static InventoryAdjustTypeEnum getInventoryAdjustTypeEnum(String key){
		if(key==null|| key.length()==0){
			return InventoryAdjustTypeEnum.getInventoryAdjustTypeEnum("FORWORDNORMAL");
		}
		InventoryAdjustTypeEnum[] ary=InventoryAdjustTypeEnum.values();
		for(InventoryAdjustTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InventoryAdjustTypeEnum.getInventoryAdjustTypeEnum("FORWORDNORMAL");
	}
	
	private String key;
	
	private String description;

	private InventoryAdjustTypeEnum(String key, String description) {
		this.key = key;
		this.description = description;
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
