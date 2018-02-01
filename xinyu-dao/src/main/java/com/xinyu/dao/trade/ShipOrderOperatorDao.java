package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.ShipOrderOperator;



public interface ShipOrderOperatorDao extends BaseDao {
	
	public void saveShipOrderOperator(ShipOrderOperator info);

	public void updateShipOrderOperator(ShipOrderOperator info);

	public ShipOrderOperator getShipOrderOperatorById(String id);

	public List<ShipOrderOperator> getShipOrderOperatorByList(Map<String, Object> params);

	public List<ShipOrderOperator> getShipOrderOperatorByPage(Map<String, Object> params, int page, int rows);
}
