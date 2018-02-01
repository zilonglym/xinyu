package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.ShipOrderBack;

public interface ShipOrderBackDao extends BaseDao{
	
	public ShipOrderBack getShipOrderBackByParams(Map<String, Object> params);
	
	public List<ShipOrderBack> getShipOrderBackList(Map<String, Object> params);
	
	public int getTotal(Map<String, Object> params); 
	
	public void insertShipOrderBack(ShipOrderBack orderBack);
	
	public void updateShipOrderBack(ShipOrderBack orderBack);
	
	public void deleteShipOrderBackById(String id);
	
}
