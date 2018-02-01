package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.ShipOrderBackItem;

public interface ShipOrderBackItemDao extends BaseDao{

	public List<ShipOrderBackItem> getOrderBackItemList(Map<String, Object> params);
	
	public void insertOrderBackItem(ShipOrderBackItem orderBackItem);
	
	public void updateOrderBackItem(ShipOrderBackItem orderBackItem);
	
	public void deleteOrderBackItemById(String id);
	
	public void deleteOrderBackItemByBackId(String orderBackId);
}
