package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.trade.ShipOrderBackItem;
import com.xinyu.service.common.BaseService;

public interface ShipOrderBackItemService extends BaseService{

public List<ShipOrderBackItem> getOrderBackItemList(Map<String, Object> params);
	
	public void insertOrderBackItem(ShipOrderBackItem orderBackItem);
	
	public void updateOrderBackItem(ShipOrderBackItem orderBackItem);
	
	public void deleteOrderBackItemById(String id);
	
	public void deleteOrderBackItemByBackId(String orderBackId);
	
}
