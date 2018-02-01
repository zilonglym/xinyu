package com.xinyu.model.inventory.enums;


/**
 * @author shark_cj
 * @since  2017-04-30
 * <pre>
 *  默认: 
 *  订单类型:
 * </pre>
 *
 */
public enum InventoryOrderTypeEnum {

	TRANSFERGRN("302","调拨入库单"),
	SALEGRN("501","销退入库单"),
	PURCHASEGRN("601","采购入库单"),
	NORMALGRN("904","普通入库单"),
	B2BGRN("306","B2B入库单"),
	B2BRETURNGRN("604","B2B干线退货入库"),
	INVENTORYGRN("704","库存状态调整入库"),
	QMENTRY("1001","奇门入库单"),
	QMRETURN("1002","奇门退货入库单"),
	
	TRADESO("1003","交易订单出库"),
	TRANSFERSSO("301","调拨出库单"),
	NORMALSO("901","普通出库单 (如货主拉走一部分货)"),
	B2BSO("305","B2B出库单"),
	ADJUSTSO("703","库存状态调整出库"),
//	ELECTRICINVENTORYSO("701","(大家电)盘点出库"),
	QMSEND("1000","奇门普通出库单"),
	
	INVENTORYLOSSES("701","盘点出库（盘亏）"),
	INVENTORYSURPLUS("702","盘点入库（盘盈）"),
	
	INVENTORYADJUST("1102","库存调整单"),
	
	RESTS("100","当KEY无法匹配的时候,测试用")
	;
	public static InventoryOrderTypeEnum getInventoryOrderTypeEnum(String key){
		if(key==null|| key.length()==0){
			return InventoryOrderTypeEnum.getInventoryOrderTypeEnum("100");
		}
		InventoryOrderTypeEnum[] ary=InventoryOrderTypeEnum.values();
		for(InventoryOrderTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return InventoryOrderTypeEnum.getInventoryOrderTypeEnum("100");
	}
	
	private String key;
	
	private String description;
	
	private InventoryOrderTypeEnum(String key,String description){
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
