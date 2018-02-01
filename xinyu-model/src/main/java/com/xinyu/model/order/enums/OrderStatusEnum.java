package com.xinyu.model.order.enums;

/**
 * @author shark_cj
 * @since  2017-05-23
 * <pre>
 * 订单状态:
 * WMS_REJECT-拒单
 * WMS_RECEIVED-收货完成
 * WMS_PRINT-打印
 * WMS_PICK-拣货
 * WMS_PACKAGE-打包
 * WMS_ONSALE-上架完成
 * WMS_LACK-缺货
 * WMS_ERROR
 * WMS_CLEARANCE-清关
 * WMS_CHECK-复核

 * WMS_ACCEPT-接单
 * WMS_ORDER_CANCELED-订单已取消
 * WMS_TALLING -理货中
 * WMS_TALLIED -理货完成
 * 注意：
 * 保税行业必须回传打包，否则不允许出库
 * 入库回传:
 * WMS_ACCEPT-接单
 * WMS_REJECT-拒单(一般入库单不会拒单)
 * WMS_ARRIVE-货到仓库
 * WMS_TALLING -理货中
 * WMS_TALLIED -理货完成
 * 
 * 出库回传:
 * WMS_ACCEPT-接单
 * WMS_REJECT-拒单
 * 
 * 发货回传:
 * WMS_ACCEPT-接单
 * WMS_REJECT-拒单
 * WMS_PICK-拣货
 * WMS_CHECK-复核
 * WMS_PACKAGE-打包
 * WMS_PRINT-打印
 * </pre>
 *
 */
public enum OrderStatusEnum {
	//start  入库
	WMS_ACCEPT("WMS_ACCEPT","接单"),
	WMS_REJECT("WMS_REJECT","拒单(一般入库单不会拒单)"),
	WMS_ARRIVE("WMS_ARRIVE","货到仓库"),
	WMS_TALLING("WMS_TALLING","理货中"),
	WMS_TALLIED("WMS_TALLING","理货完成"),
	
	RESTS("100","测试用")
	;
	public static OrderStatusEnum getOrderStatusEnum(String key){
		if(key==null|| key.length()==0){
			return OrderStatusEnum.getOrderStatusEnum("100");
		}
		OrderStatusEnum[] ary=OrderStatusEnum.values();
		for(OrderStatusEnum obj:ary){
			if(obj.getKey().equals(key)){
				return obj;
			}
		}
		return OrderStatusEnum.getOrderStatusEnum("100");
	}
	
	private String key;
	
	private String description;

	private OrderStatusEnum(String key,String description){
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
