package com.xinyu.model.system.enums;

/**
 * 系统参数设置
 * */
public enum SystemItemTypeEnums {
	LOGISTICS("LOGISTICS","物流公司"),
	WAYBILL("waybill","已订购物流公司服务"),
	CHECK_TRADE("CHECK_TRADE","开启出库验货功能"),
	CHECK_COMP("CHECK_COMP","物流公司规则"),
	TEMPLATE("template","云打印物流公司模板URL"),
	AUDIT_CODE("AUDIT_CODE","审单时items用code"),
	AUDIT_SKU("AUDIT_SKU","审单时items不显示sku"),
	AUDIT_REMARK("AUDIT_REMARK","审单时Items显示备注"),
	SESSIONKEY("sessionKey","奇门sessionKey"),
	QM_APPKEY("qm_appKey","奇门appKey"),
	QM_SECRETKEY("qm_secretKey","奇门secretKey"),
	QM_URL("qm_url","奇门url");
	
	private String key;
	private String description;
	
	private SystemItemTypeEnums(String key,String description){
		this.key=key;
		this.description=description;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}
}
