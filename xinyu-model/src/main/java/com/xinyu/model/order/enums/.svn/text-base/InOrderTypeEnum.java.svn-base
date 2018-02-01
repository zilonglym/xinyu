package com.xinyu.model.order.enums;

/**
 * @author shark_cj
 * @since  2017-04.27
 * <pre>
 * 302	调拨入库单
 * 501	销退入库单
 * 601	采购入库单
 * 904	普通入库单
 * 306  B2B入库单
 * 604	B2B干线退货入库
 * 704	库存状态调整入库
 * <pre>
 *   需适配菜鸟库存类型，只有退货入库
 *   RESTS("100","测试用")
 */
public enum InOrderTypeEnum {
	
	TRANSFERGRN("302","调拨入库单"),
	SALEGRN("501","销退入库单"),
	PURCHASEGRN("601","采购入库单"),
	NORMALGRN("904","普通入库单"),
	B2BGRN("306","B2B入库单"),
	B2BRETURNGRN("604","B2B干线退货入库"),
	INVENTORYGRN("704","库存状态调整入库"),
	RESTS("100","测试用"),
	QMENTRY("1001","奇门入库单"),
	QMRETURN("1002","奇门退货入库单")
	;
	
	public static InOrderTypeEnum getOrderTypeEnum(String key){
		if(key==null|| key.length()==0){
			return InOrderTypeEnum.getOrderTypeEnum("100");
		}
		InOrderTypeEnum[] ary=InOrderTypeEnum.values();
		for(InOrderTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InOrderTypeEnum.getOrderTypeEnum("100");
	}
	
	private String key;
	
	private String description;
	
	private InOrderTypeEnum(String key,String description){
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
