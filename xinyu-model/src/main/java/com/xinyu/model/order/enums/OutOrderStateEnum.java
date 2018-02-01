package com.xinyu.model.order.enums;


/**
 * @author shark_cj
 * 出库单据状态
 */
public enum OutOrderStateEnum {
	SAVE("SAVE","本地保存"),
	//仓库
	WMS_CONFIRMWAITING("WMS_CONFIRMWAITING","等待出库"),
	//未入库单 按批次  确认预留状态，暂不使用
	WMS_CONFIRMING("WMS_CONFIRMING","批次上传中"),
	WMS_CONFIRM_FINISH("WMS_CONFIRM_FINISH","上传菜鸟成功"),
	CANCEL("CANCEL","作废")
	;
	public static OutOrderStateEnum getOutOrderStateEnum(String key){
		if(key==null|| key.length()==0){
			return OutOrderStateEnum.getOutOrderStateEnum("SAVE");
		}
		OutOrderStateEnum[] ary=OutOrderStateEnum.values();
		for(OutOrderStateEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return OutOrderStateEnum.getOutOrderStateEnum("SAVE");
	}
	
	private String key;
	
	private String description;

	private OutOrderStateEnum(String key,String description){
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
