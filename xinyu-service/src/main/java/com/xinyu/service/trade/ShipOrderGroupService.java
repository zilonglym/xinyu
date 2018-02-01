package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.trade.ShipOrderGroup;
import com.xinyu.service.common.BaseService;

public interface ShipOrderGroupService extends BaseService {
	
	public void insertShipOrderGroup(ShipOrderGroup group);
	
	public List<ShipOrderGroup> getShipOrderGroupByList(Map<String,Object> params);
	

}
