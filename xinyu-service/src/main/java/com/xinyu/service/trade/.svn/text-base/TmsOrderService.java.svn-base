package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.service.common.BaseService;

public interface TmsOrderService extends BaseService {

	
	public void insertTmsOrder(TmsOrder order);
	
	public void insertTmsOrderItem(List<TmsOrderItem> orderItemList);
	
	public List<TmsOrder> getTmsOrderByList(Map<String,Object> params);
	
	public List<TmsOrderItem> getTmsOrderItemByList(Map<String,Object> params);
	
	public TmsOrder getTmsOrderById(String id);
	
	public void updateTmsOrder(TmsOrder tmsOrder);

	public int getTotalByOrderId(String id);
	
}
