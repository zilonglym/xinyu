package com.xinyu.dao.trade;

import java.util.List;
import java.util.Map;
import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.trade.ShipOrderStop;

public interface ShipOrderStopDao extends BaseDao {
	public void saveShipOrderStop(ShipOrderStop info);

	public void updateShipOrderStop(ShipOrderStop info);

	public ShipOrderStop getShipOrderStopById(String id);

	public List<ShipOrderStop> getShipOrderStopByList(Map<String, Object> params);

	public List<ShipOrderStop> getShipOrderStopByPage(Map<String, Object> params, int page, int rows);

	public int getTotal(Map<String, Object> params);

}
