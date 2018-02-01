package com.xinyu.model.base.enums;

/**
 * 商品类型
 * @author yangmin
 * 2017年4月24日
 *
 */
public enum ItemTypeEnum {
	//NORMAL-普通商品、 PACKING_MATERIALS-包材、 CONSUMPTIVE_MATERIALS-耗材
	NORMAL("NORMAL","普通商品"),
	PACKING_MATERIALS("PACKING_MATERIALS","包材"),
	CONSUMPTIVE_MATERIALS("CONSUMPTIVE_MATERIALS","耗材"),
	GIFT("GIFT","赠品"),
	QMZC("ZC","奇门正常商品")
	;
	private String key;
	private String description;
	private ItemTypeEnum(String key,String description){
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
