package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderOperatorDao;
import com.xinyu.model.trade.ShipOrderOperator;

/**
 * 订单操作日志
 * */
@Repository("shipOrderOperatorDaoImpl")
public class ShipOrderOperatorDaoImpl extends BaseDaoImpl implements ShipOrderOperatorDao {
	
	private final String statement = "com.xinyu.dao.trade.ShipOrderOperatorDao.";
	
	public void saveShipOrderOperator(ShipOrderOperator info) {
		this.insert(this.statement+"insertShipOrderOperator", info);
	}

	public void updateShipOrderOperator(ShipOrderOperator info) {
		this.insert(this.statement+"updateShipOrderOperator", info);
	}

	public ShipOrderOperator getShipOrderOperatorById(String id) {
		return (ShipOrderOperator) this.selectOne(this.statement+"selectShipOrderOperatorById", id);
	}

	public List<ShipOrderOperator> getShipOrderOperatorByList(Map<String, Object> params) {
		return (List<ShipOrderOperator>) this.selectList(this.statement+"getShipOrderOperatorByList", params);
	}

	@Override
	public List<ShipOrderOperator> getShipOrderOperatorByPage(Map<String, Object> params, int page, int rows) {
		return this.selectList(this.statement+"getShipOrderOperatorByList", params, rows, page);
	}
}
