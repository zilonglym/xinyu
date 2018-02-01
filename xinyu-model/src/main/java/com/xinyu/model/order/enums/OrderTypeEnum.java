package com.xinyu.model.order.enums;


/**
 * @author shark_cj
 * @since 2017-05-23
 *  <pre>
 *  仓库订单类型：
 *  201	交易出库单
 *	501	销退入库单
 *	502	换货出库单
 *	301	调拨出库单
 *	302	调拨入库单
 *	601	采购入库单
 *	901	普通出库单
 *	904	普通入库单
 *	604	B2B干线退货入库
 *	306	B2B入库单
 *	305	B2B出库单
 *	703	库存状态调整出库
 *	704	库存状态调整入库
 * </pre>
 */
public enum OrderTypeEnum {
	SHIPORDER("201","交易出库单"),
	TRANSFERGRN("302","调拨入库单"),
	TRANSFERSSO("301","调拨出库单"),
	
	SALEGRN("501","销退入库单"),
	CHANGERN("502","换货出库单"),
	
	B2BGRN("306","B2B入库单"),
	B2BSO("305","B2B出库单"),

	ADJUSTSO("703","库存状态调整出库"),
	INVENTORYGRN("704","库存状态调整入库"),
	
	B2BRETURNGRN("604","B2B干线退货入库"),
	PURCHASEGRN("601","采购入库单"),
	
	NORMALSO("901","普通出库单 (如货主拉走一部分货)"),
	NORMALGRN("904","普通入库单"),
	ELECTRICINVENTORYSO("701","(大家电)盘点出库"),
	RESTS("100","测试用")
	;
	public static OrderTypeEnum getOrderTypeEnum(String key){
		if(key==null|| key.length()==0){
			return OrderTypeEnum.getOrderTypeEnum("100");
		}
		OrderTypeEnum[] ary=OrderTypeEnum.values();
		for(OrderTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return OrderTypeEnum.getOrderTypeEnum("100");
	}
	
	private String key;
	
	private String description;

	private OrderTypeEnum(String key,String description){
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
