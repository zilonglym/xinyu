package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderBackDao;
import com.xinyu.model.trade.ShipOrderBack;

@Repository("shipOrderBackDaoImpl")
public class ShipOrderBackDaoImpl extends BaseDaoImpl implements ShipOrderBackDao{

	private final String statement = "com.xinyu.dao.trade.ShipOrderBackDao.";
	
	public ShipOrderBack getShipOrderBackByParams(Map<String, Object> params) {
		return (ShipOrderBack) super.selectOne(this.statement+"getShipOrderBackByParams", params);
	}

	public List<ShipOrderBack> getShipOrderBackList(Map<String, Object> params) {
		return (List<ShipOrderBack>) super.selectList(this.statement+"getShipOrderBackList", params);
	}

	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getTotal", params);
	}

	public void insertShipOrderBack(ShipOrderBack orderBack) {
		super.insert(this.statement+"insertShipOrderBack", orderBack);
	}

	public void updateShipOrderBack(ShipOrderBack orderBack) {
		super.update(this.statement+"updateShipOrderBack", orderBack);
	}

	public void deleteShipOrderBackById(String id) {
		super.delete(this.statement+"deleteShipOrderBackById", id);
	}

	@Override
	public List<Map<String, Object>> getOrderBackMapList(Map<String, Object> p) {
		return (List<Map<String, Object>>) super.selectList(this.statement+"getOrderBackMapList", p);
	}
	
	@Override
	public List<Map<String, Object>> findItemCount(Map<String, Object> p) {
		return (List<Map<String, Object>>) super.selectList(this.statement+"findItemCount", p);
	}

}
