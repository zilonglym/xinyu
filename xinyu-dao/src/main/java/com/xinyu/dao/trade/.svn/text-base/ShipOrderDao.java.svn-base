package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.WmsConsignOrderItem;

/**
 * shipOrderDao接口
 * */
public interface ShipOrderDao extends BaseDao {

	/**
	 * 新建订单
	 * @param shipOrder
	 * */
	public void insertShipOrder(ShipOrder shipOrder);
	
	/**
	 * 更新订单
	 * @param shipOrder
	 * */
	public void updateShipOrder(ShipOrder shipOrder);
	
	/**
	 * 根据Id查询订单
	 * @param id
	 * @return shipOrder
	 * */
	public ShipOrder getShipOrderById(String id);
	
	/**
	 * 根据Id查询历史订单
	 * @param id
	 * @return shipOrder
	 * */
	public ShipOrder getOldShipOrderById(String id);
	
	/**
	 * 条件查询shipOrder数据（非分页）
	 * @param params
	 * @return list
	 * */
	public List<ShipOrder> getShipOrderByList(Map<String,Object> params);
	
	/**
	 * 条件查询shipOrder表所有数据
	 * @param params
	 * @return
	 */
	public List<ShipOrder> getShipOrderListByALl(Map<String,Object> params);

	public int getShipOrderListByAllCount(Map<String,Object> params);
	/**
	 * 条件查询shipOrder数据（分页）
	 * @param params
	 * @param pageNump
	 * @param pageSize
	 * @return list
	 * */
	public List<ShipOrder> findShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize);

	/**
	 * 订单删除
	 * @param id
	 * */
	public void deleteShipOrder(String id);

	public void updateShipOrderStatus(Map<String, Object> params);

	public void updateShipOrderByWaybill(Map<String,String> params);

	public int getShipOrderTotal(Map<String, Object> params);
	
	public int isHaveOrder(Map<String,Object> params);

	public void updateExpressOrderNo(Map<String, Object> params);

	public List<Map<String, Object>> getShipOrderItemByWorkGroup(Map<String, Object> params);

	public ShipOrder getShipOrderByParams(Map<String, Object> params);

	List<ShipOrder> findOLdShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize);

	int getOldShipOrderTotal(Map<String, Object> params);

	public List<ShipOrder> findTmsNotExsist(Map<String, Object> params);

	public List<Map<String, Object>> findStoreOrderList(Map<String, Object> params);

}
