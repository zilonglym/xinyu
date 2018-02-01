package com.xinyu.model.order.enums;


/**
 * 
 * @author shark_cj
 * @since 2017-04-27
 *  <pre>
 *  适用如下场景:
 *  退货入库:
 *  8:退换货
 *  9:上门服务
 *  13: 退货收取发票
 *  31：退货入库
 *  36：退货时同时换货
 *  其他单据忽略,
 *  orderFlag 需适配菜鸟 
 *  RESTS测试用  100
 *  </pre>
 */
public enum OrderFlagEnum {
 
	QUITCHANGE("8","退换货"),
	DOORTODOOR("9","上门服务"),
	RETURNRECEIPT("13","退货收取发票"),
	RETURNGRN("31","退货入库"),
	QUITANDCHANGE("36","退货时同时换货"),
	RESTS("100","测试用")
	;
	
	public static OrderFlagEnum getOrderFlagEnum(String key){
		if(key==null|| key.length()==0){
			return OrderFlagEnum.getOrderFlagEnum("100");
		}
		OrderFlagEnum[] ary=OrderFlagEnum.values();
		for(OrderFlagEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return OrderFlagEnum.getOrderFlagEnum("100");
	}
	
	private String key;
	
	private String description;
	
	private OrderFlagEnum(String key,String description){
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
