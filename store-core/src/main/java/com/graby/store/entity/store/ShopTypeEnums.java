package com.graby.store.entity.store;

/**
 * 商家店铺类型
 * @author lenovo
 *
 */
public enum ShopTypeEnums {
		TB("TB","淘宝"),
		TMALL("TMALL","天猫"),
		JD("JD","京东"),
		VIP("VIP","唯品会")
	;
	private ShopTypeEnums(String key,String description){
		this.key=key;
		this.description=description;
	}
	private String key;
	
	private String description;

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}
	
	
}
