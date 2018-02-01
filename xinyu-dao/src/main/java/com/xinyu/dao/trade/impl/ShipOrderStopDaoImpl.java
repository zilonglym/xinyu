package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderStopDao;
import com.xinyu.model.trade.ShipOrderStop;

@Repository("ShipOrderStopDaoImpl")
public class ShipOrderStopDaoImpl extends BaseDaoImpl implements ShipOrderStopDao {
	public void saveShipOrderStop(ShipOrderStop info) {
		this.insert("com.xinyu.dao.shipOrderStopDao.insertShipOrderStop", info);
	}

	public void updateShipOrderStop(ShipOrderStop info) {
		this.insert("com.xinyu.dao.shipOrderStopDao.updateShipOrderStop", info);
	}

	public ShipOrderStop getShipOrderStopById(String id) {
		return (ShipOrderStop) this.selectOne("com.xinyu.dao.shipOrderStopDao.getShipOrderStopById", id);
	}

	public List<ShipOrderStop> getShipOrderStopByList(Map<String, Object> params) {
		return (List<ShipOrderStop>) this.selectList("com.xinyu.dao.shipOrderStopDao.getShipOrderStopByList", params);
	}

	@Override
	public List<ShipOrderStop> getShipOrderStopByPage(Map<String, Object> params, int page, int rows) {
		return this.selectList("com.xinyu.dao.shipOrderStopDao.getShipOrderStopByList", params, rows, page);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) this.selectOne("com.xinyu.dao.shipOrderStopDao.getTotal", params);
	}
}
