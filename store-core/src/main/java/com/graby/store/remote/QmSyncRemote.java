package com.graby.store.remote;

import java.util.Map;

public interface QmSyncRemote {
	/**
	 * 商品同步
	 * @param xmlStr
	 * @return
	 */
	String itemSync(String xmlStr)throws Exception;
	
	/**
	 * 商品批量同步
	 * @param xmlStr
	 * @return
	 */
	String itemBatchSync(String xmlStr)throws Exception;
	
	/**
	 * 组合商品
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String combineitem(String xmlStr) throws Exception;
	/**
	 * 入库单创建
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String entryorder(String xmlStr) throws Exception;
	/**
	 * 退货入库单创建
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String returnorder(String xmlStr) throws Exception;
	/**
	 * 出库单创建 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String stockout(String xmlStr) throws Exception;
	
	/**
	 * 发货单创建
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String deliveryorder(String xmlStr)throws Exception;
	/**
	 * 发货单查询接口
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String deliveryQuery(String xmlStr) throws Exception;
	/**
	 * 订单查询接口
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String orderprocessQuery(String xmlStr)throws Exception;
	/**
	 * 订单取消接口
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String orderCancel(String xmlStr) throws Exception;
	
	/**
	 * 库存查询
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String inventoryQuery(String xmlStr) throws Exception;
	/**
	 * 仓内加工单
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	String storeprocessCreate(String xmlStr) throws Exception;
	
	/**
	 * 提交奇门方法
	 * @param xmlStr
	 * @param method
	 * @return
	 */
	public String submitQm(String xmlStr,String method,String customerId);
	/**
	 * 发货单缺货通知接口
	 * @param jsonStr
	 * @return
	 */
	public String itemlackReport(String jsonStr);
	
	String entryOrderConfirm(Map<String,Object> map) throws Exception;
	
    String stockoutConfirm(Map<String,Object> map) throws Exception ;
    
    String returnorderConfirm(Map<String,Object> map) throws Exception ;
    
    void createTradeByShipOrder(Long shipOrderId);
}
