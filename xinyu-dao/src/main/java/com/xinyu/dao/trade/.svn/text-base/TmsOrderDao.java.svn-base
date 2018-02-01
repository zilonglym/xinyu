package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;

public interface TmsOrderDao extends BaseDao {

	
	public void insertTmsOrder(TmsOrder order);
	
	public void insertTmsOrderItem(List<TmsOrderItem> orderItemList);
	
	public List<TmsOrder> getTmsOrderByList(Map<String,Object> params);
	
	public List<TmsOrderItem> getTmsOrderItemByList(Map<String,Object> params);
	
	public void updateTmsOrder(TmsOrder tmsOrder);

	public TmsOrder getTmsOrderById(String id);

	public void updateTmsOrderItem(TmsOrderItem shipOrderDetail);
	
	public void deleteTmsOrderItembyId(String id);

	public int getTotalByOrderId(String orderId);
}
