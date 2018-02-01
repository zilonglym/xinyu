package com.xinyu.model.order.enums;

/**
 * 出入库单和盘点单据以及商品库存手动修改操作日志
 * */
public enum StockOperateTypeEnum {
	FAIL("FAIL","操作失败"),
	CREATE("CREATE","单据新建"),
	CANCEL("CANCEL","单据取消"),
	RECEIVE("RECEIVE","单据接收"),
	REJECT("REJECT","单据拒收"),
	CONFIRM("CONFIRM","单据上传"),
	EDIT("EDIT","手动修改库存");
	
	public static StockOperateTypeEnum getStockOperateTypeEnum(String key){
		if(key==null|| key.length()==0){
			return StockOperateTypeEnum.getStockOperateTypeEnum("CONFIRM");
		}
		StockOperateTypeEnum[] ary=StockOperateTypeEnum.values();
		for(StockOperateTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return StockOperateTypeEnum.getStockOperateTypeEnum("CONFIRM");
	}
	
	private String key;
	
	private String description;

	private StockOperateTypeEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}
}
