package com.xinyu.model.order.enums;

/**
 * @author shark
 * <pre>
 *  入库单状态
 * </pre>
 */
public enum InOrderStateEnum {
	
	//菜鸟推单保存基础状态
	SAVE("SAVE","等待接单"),
	//仓库
	WMS_CONFIRMWAITING("WMS_CONFIRMWAITING","等待入库"),
	//未入库单 按批次  
	WMS_CONFIRMING("WMS_CONFIRMING","部分已上传菜鸟"),
	WMS_CONFIRM_FINISH("WMS_CONFIRM_FINISH","上传菜鸟成功"),
	CANCEL("CANCEL","作废")
	;
	public static InOrderStateEnum getInOrderStateEnum(String key){
		if(key==null|| key.length()==0){
			return InOrderStateEnum.getInOrderStateEnum("SAVE");
		}
		InOrderStateEnum[] ary=InOrderStateEnum.values();
		for(InOrderStateEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InOrderStateEnum.getInOrderStateEnum("SAVE");
	}
	
	private String key;
	
	private String description;

	private InOrderStateEnum(String key,String description){
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
