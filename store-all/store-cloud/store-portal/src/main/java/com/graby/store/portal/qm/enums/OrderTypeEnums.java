package com.graby.store.portal.qm.enums;
/**
 * 单据类型枚举
 * @author yangmin
 *
 */
public enum OrderTypeEnums {
	JYCK("JYCK","一般交易出库单"),
	HHCK("HHCK","换货出库"),
	BFCK("BFCK","补发出库"),
	PTCK("PTCK","普通出库单"),
	DBCK("DBCK","调拨出库"),
	QTCK("QTCK","其他出库"),
	SCRK("SCRK","生产入库"),
	LYRK("LYRK","领用入库"),
	CCRK("CCRK","残次品入库"),
	CGRK("CGRK","采购入库"),
	DBRK("DBRK","调拨入库"),
	QTRK("QTRK","其他入库"),
	XTRK("XTRK","销退入库"),
	HHRK("HHRK","换货入库"),
	CNJG("CNJG","仓内加工单");
	//  JYCK= 一般交易出库单，HHCK= 换货出库 ，BFCK= 补发出库 PTCK=普通出库单，
	//DBCK=调拨出库 ，QTCK=其他出库， SCRK=生产入库，LYRK=领用入库，CCRK=残次品入库，
	//CGRK=采购入库 ，
	//DBRK= 调拨入库 ，QTRK= 其他入库 ，XTRK= 销退入库 HHRK= 换货入库 CNJG= 仓内加工单
	private String key;
	private String description;
	private OrderTypeEnums(String key,String description){
		this.key=key;
		this.description=description;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
