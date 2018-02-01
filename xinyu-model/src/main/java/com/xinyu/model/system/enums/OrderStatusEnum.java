package com.xinyu.model.system.enums;

public enum OrderStatusEnum {

	WMS_ARRIVALREGISTER("WMS_ARRIVALREGISTER","到货登记"),
	WMS_RECEIVED("WMS_RECEIVED","收货完成（返回）"),
	WMS_ONSALE("WMS_ONSALE","上架完成（返回）"),
	WMS_PICKED("WMS_PICKED","拣货完成"),
	WMS_CHECKED("WMS_CHECKED","复核完成"),
	WMS_PACKAGED("WMS_PACKAGED","打包完成"),
	
	WMS_PICK("WMS_PICK","拣货"),
	WMS_CHECK("WMS_CHECK","复核"),
	WMS_PACKAGE("WMS_PACKAGE","打包"),
	WMS_REJECT("WMS_REJECT","接单失败"),
	WMS_FAILED("WMS_FAILED","订单发货失败"),
	
	WMS_ZC("WMS_ZC","到达中仓"),
	WMS_ACCEPT("WMS_ACCEPT","仓库接单,现在审核操作后状态改成这个"),
	WMS_AUDIT("WMS_AUDIT","仓库审单"),
	WMS_GETNO("WMS_GETNO","仓库取单号"),
	WMS_PRINT("WMS_PRINT","仓库已打印"),		
	WMS_FINASH("WMS_FINASH","完成"),	
	WMS_RETURN("WMS_RETURN","退货"),
	WMS_CANCEL("WMS_CANCEL","取消"),
	WMS_CONFIRM("WMS_CONFIRM","仓库已确认"),
	WMS_DELETED("WMS_DELETED","删除"),
	split("split","测试数据")
	;
	
	private String key;
	private String description;

	private OrderStatusEnum(String key, String description) {
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

	/**
	 * WMS_ ARRIVALREGISTER到货登记
	 * WMS_RECEIVED收货完成（返回）
	 * WMS_ONSALE上架完成（返回）
	 * WMS_PICKED拣货完成
	 * WMS_CHECKED复核完成
	 * WMS_PACKAGED 打包完成（返回）（确认物流宝的语义，修正标准。）
	 * WMS_ACCEPT - 接单（返回）
	 * WMS_PRINT - 打印
	 * WMS_PICK – 拣货
	 * WMS_CHECK – 复核
	 * WMS_PACKAGE - 打包
	 * WMS_REJECT-接单失败
	 * WMS_FAILED-订单发货失败(买家拒签)
	 */
	
	
}
