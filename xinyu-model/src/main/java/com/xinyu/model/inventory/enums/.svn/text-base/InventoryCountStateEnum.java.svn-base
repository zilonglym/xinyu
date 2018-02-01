package com.xinyu.model.inventory.enums;


/**
 * @author shark_cj
 * <pre>
 *  库存盘点单 状态
 * </pre>
 */
public enum InventoryCountStateEnum {
	
	SAVE("SAVE","本地保存"),
	WMS_UP_FAIL("WMS_UP_FAIL","上传菜鸟失败"),
	WMS_UP_FINISH("WMS_UP_FINISH","上传菜鸟成功"),
	CANCEL("CANCEL","作废")
	
	;
	public static InventoryCountStateEnum getInventoryCountStateEnum(String key){
		if(key==null|| key.length()==0){
			return InventoryCountStateEnum.getInventoryCountStateEnum("SAVE");
		}
		InventoryCountStateEnum[] ary=InventoryCountStateEnum.values();
		for(InventoryCountStateEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InventoryCountStateEnum.getInventoryCountStateEnum("SAVE");
	}
	
	private String key;
	
	private String description;

	private InventoryCountStateEnum(String key,String description){
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
