package com.xinyu.model.order.enums;


/**
 * @author shark_cj
 * @since 2017-04-28
 *  <pre>
 *  301  调拨出库单	
 *  901 普通出库单 (如货主拉走一部分货)
 *  305 B2B出库单
 *  703	库存状态调整出库
 *  701	(大家电)盘点出库
 *  
 *  需适配菜鸟单据类型。
 *	奇门定义：出库单类型
 *	PTCK=普通出库单;
 *  DBCK=调拨出库;
 *  B2BCK=B2B出库;
 *  QTCK=其他出库;
 *  CGTH=采购退货出库单;
 *  XNCK=虚拟出库单
 *  RESTS("100","测试用")
 *  </pre>
 */

public enum OutOrderTypeEnum {
	TRANSFERSSO("301","调拨出库单"),
	NORMALSO("901","普通出库单 (如货主拉走一部分货)"),
	B2BSO("305","B2B出库单"),
	ADJUSTSO("703","库存状态调整出库"),
	ELECTRICINVENTORYSO("701","(大家电)盘点出库"),
	RESTS("100","测试用"),
	QMSEND("1000","奇门普通出库单")
	;
	
	
	public static OutOrderTypeEnum getOrderTypeEnum(String key){
		if(key==null|| key.length()==0){
			return OutOrderTypeEnum.getOrderTypeEnum("100");
		}
		OutOrderTypeEnum[] ary=OutOrderTypeEnum.values();
		for(OutOrderTypeEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return OutOrderTypeEnum.getOrderTypeEnum("100");
	}
	
	private String key;
	
	private String description;
	
	private OutOrderTypeEnum(String key,String description){
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
