package com.graby.store.portal.qm.service;

public interface QmSyncService {

	/**
	 * 商品同步
	 * @param xmlStr
	 * @return
	 */
	String itemSync(String xmlStr)throws Exception;
	
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
	
	
}
