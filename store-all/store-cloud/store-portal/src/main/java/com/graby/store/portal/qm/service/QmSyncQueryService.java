package com.graby.store.portal.qm.service;

/**
 * 奇门同步接口,查询方面
 * @author 杨敏
 *
 */
public interface QmSyncQueryService {
	
	/**
	 * 发货单查询接口
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String deliveryorderQuery(String xmlStr) throws Exception;
	
	/**
	 * 订单流水查询接口
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String orderprocessQuery(String xmlStr) throws Exception;
}
