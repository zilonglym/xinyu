package com.xinyu.model.inventory.enums;


/**
 * 库存调整单 状态
 * @author shark_cj
 * @since  2017-06-20
 */
public enum InventoryAdjustStateEnum {
	SAVE("SAVE","本地保存"),
	WMS_UP_FAIL("WMS_UP_FAIL","上传菜鸟失败"),
	WMS_UP_FINISH("WMS_UP_FINISH","上传菜鸟成功"),
	CANCEL("CANCEL","作废")
	;
	public static InventoryAdjustStateEnum getInventoryAdjustStateEnum(String key){
		if(key==null|| key.length()==0){
			return InventoryAdjustStateEnum.getInventoryAdjustStateEnum("SAVE");
		}
		InventoryAdjustStateEnum[] ary=InventoryAdjustStateEnum.values();
		for(InventoryAdjustStateEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InventoryAdjustStateEnum.getInventoryAdjustStateEnum("SAVE");
	}
	
	private String key;
	
	private String description;

	private InventoryAdjustStateEnum(String key,String description){
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
