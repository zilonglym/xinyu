package com.graby.store.admin.web;

/**
 * 缓存数据常量
 * @author yangmin
 * 2017年9月20日
 *
 */
public interface RdsConstants {

	public int SECONDS=1000*60*60*24*10;
	
	public String ORDER_ACCEPT="ORDER_ACCEPT";
	
	public String ORDER_REJECT="ORDER_REJECT";
	
	public  String CHECK_NO_ORDER="CHECK_NO_ORDER";//订单不存在
	
	public  String CHECK_NO_ORDER_QM="CHECK_NO_ORDER_QM";//订单不存在
	
	
	public String ITEM="Item";
	/**
	 * mobile 信息
	 * *
	 * */
	public String MOBILE_ITEM="mobile_item_utf8";
	
	public String MOBILE_ORDER_ITEM="mobile_order_item";//订单商品数据
	
	public String MOBILE_ORDER_ITEM_CAINIAO="mobile_order_item_cainiao";//订单商品数据
}
