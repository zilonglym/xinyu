package com.xinyu.check.dao;

import java.util.List;
import java.util.Map;

import com.xinyu.check.dao.base.BaseDao;
import com.xinyu.check.model.ShipOrder;
import com.xinyu.check.model.ShipOrderDetail;
import com.xinyu.check.model.ShipOrderReturn;

/**
 * shipOrderDao接口
 */
public interface ShipOrderDao extends BaseDao {

	/**
	 * 根据Id查询订单
	 * 
	 * @param id
	 * @return shipOrder
	 */
	public ShipOrder getShipOrderById(String id);

	public List<ShipOrder> getShipOrderByList(Map<String, Object> params);

	public List<ShipOrderDetail> getShipOrderDetailByOrderId(Map<String, Object> params);

	public List<ShipOrderReturn> getShpOrderReturnByList(Map<String, Object> params);

	public Long getCountByDate(Map<String, Object> params);

	public Long getSuccessCountByDate(Map<String, Object> params);

	public Long getCheckSuccessCountByDate(Map<String, Object> params);

	public Long getCheckFailCountByDate(Map<String, Object> params);
	
	/**
	 * 判断验货
	 * @param params
	 * @return
	 */
	public Long getTradeCheck(Map<String,Object> params);
	
	/**
	 * 判断是否有退货与重复发货
	 * @param params
	 * @return
	 */
	public Map<String,Object> getTradeCheckAndReturn(Map<String,Object> params);
}
