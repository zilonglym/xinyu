package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.ShipOrder;

public interface ReportRemote {

	/**
	 * 统计发货单
	 * @param parameters
	 * @return
	 */
	public List<Map<String, Object>> shipCount(Map<String, Object> parameters);
	
	/**
	 * 卖出商品统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> sumUserSellouts(long userId, String startDate, String endDate);
	
	/**
	 * 查询用户发货单
	 * @param parameters
	 * @return
	 */
	public List<ShipOrder> findOrderSellout(Map<String, Object> parameters);
}
