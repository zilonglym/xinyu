package com.xinyu.check.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.check.dao.ShipOrderDao;
import com.xinyu.check.dao.base.BaseDaoImpl;
import com.xinyu.check.model.ShipOrder;
import com.xinyu.check.model.ShipOrderDetail;
import com.xinyu.check.model.ShipOrderReturn;

/**
 * shipOrderDao实现
 * */
@Repository("shipOrderDaoImpl")
public class ShipOrderDaoImpl extends BaseDaoImpl implements ShipOrderDao {

	private final String statement="com.xinyu.dao.shipOrderDao.";
	
	

	/**
	 * 根据Id查询订单
	 * @param id
	 * @return shipOrder
	 * */
	@Override
	public ShipOrder getShipOrderById(String id) {
		// TODO Auto-generated method stub
		return (ShipOrder) this.selectOne(statement+"getShipOrderById", id);
	}



	@Override
	public List<ShipOrder> getShipOrderByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrder>) this.selectList(statement+"findShipOrderByList", params);
	}



	@Override
	public List<ShipOrderDetail> getShipOrderDetailByOrderId(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrderDetail>) this.selectList(statement+"getShipOrderDetailByOrderId", params);
	}



	@Override
	public List<ShipOrderReturn> getShpOrderReturnByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrderReturn>) this.selectList(statement+"getShipOrderByexpress", params);
	}



	@Override
	public Long getCountByDate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Long) this.selectOne(statement+"getCountByDate", params);
	}



	@Override
	public Long getSuccessCountByDate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Long) this.selectOne(statement+"getSuccessCountByDate", params);
	}



	@Override
	public Long getCheckSuccessCountByDate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Long) this.selectOne(statement+"getCheckSuccessCountByDate", params);
	}



	@Override
	public Long getCheckFailCountByDate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Long) this.selectOne(statement+"getCheckFailCountByDate", params);
	}

	@Override
	public Long getTradeCheck(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Long) this.selectOne(statement+"getTradeCheck", params);
	}



	@Override
	public Map<String, Object> getTradeCheckAndReturn(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) this.selectOne(statement+"getTradeCheckAndReturn", params);
	}

	}
