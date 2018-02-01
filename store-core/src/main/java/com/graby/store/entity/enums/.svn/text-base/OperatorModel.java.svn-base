package com.graby.store.entity.enums;

/**
 * 系统操作模块
 * @author lenovo
 *
 */
public enum OperatorModel {
	LOGIN("LOGIN","登录"),
	TRADE("TRADDE","订单"),
	TRADE_AUDIT("TRADE_AUDIT","审单"),
	TRADE_PRINT("TRADE_PRINT","打单"),
	TRADE_PRINT_AGAIN("TRADE_PRINT_AGAIN","重新打单"),
	TRADE_DEL("TRADE_DEL","删除订单"),
	TRADE_INVALID("TRADE_INVALID","订单作废"),
	TRADE_CANCEL("TRADE_CANCEL","反审"),
	STORAGE("STORAGE","库存"),
	QM_TRADE("QM_TRADE","奇门"),
	QM_TRADE_CONFIRM("QM_TRADE_CONFIRM","奇门发货单确认"),
	CONFIRM("CONFIRM","批量拣货处理"),
	QM_ITEM("QM_ITEM","奇门商品同步"),
	;
	private String key;
	private String description;
	
	private OperatorModel(String key,String description){
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
