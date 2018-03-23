package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrder;

/**
 * 报表统计
 * */
@MyBatisRepository
public interface ReportDao {

	/**
	 * 查询用户卖出的商品统计
	 * 返回
	 * itemName:商品名称
	 * itemCount:售出总数
	 * 
	 * parameters
	 * @param userId 用户ID
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return list
	 */
	List<Map<String, Object>> sumUserSellouts(Map<String, Object> parameters);
	
	/**
	 * 查询用户发货单
	 * @param parameters
	 * @return list
	 */
	List<ShipOrder> findOrderSellout(Map<String, Object> parameters);
	
	/**
	 * 查询用户发货单总数
	 * @param parameters
	 * @return long
	 */
	long findOrderSelloutCount(Map<String, Object> parameters);
	
	
	/**
	 * 统计商家发货单
	 * @param parameters
	 *  userId 
	 *  startDate
	 *  endDate
	 * @return list
	 */
	List<Map<String, Object>> shipCount(Map<String, Object> parameters);

	List<Map<String, Object>> orderCount(Map<String, Object> params);

	
	/**
	 * 订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	Long findShipOrderNumByParams(Map<String, Object> params);
	
	/**
	 * 出库订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	Long findCheckOutNumByParams(Map<String, Object> params);
	
	/**
	 * 未出库或出库失败订单(马)
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<Map<String, Object>> findUnfinishOrder(Map<String, Object> params);
	
	/**
	 * 未出库统计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findNotExist(Map<String, Object> params);

}
