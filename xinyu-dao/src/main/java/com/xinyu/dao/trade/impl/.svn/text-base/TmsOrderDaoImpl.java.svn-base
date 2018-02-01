package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.TmsOrderDao;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;

@Repository("tmsOrderDaoImpl")
public class TmsOrderDaoImpl extends BaseDaoImpl implements TmsOrderDao {

	private final String statement="com.xinyu.dao.tmsOrderDao.";
	private final String statement_item="com.xinyu.dao.tmsOrderItemDao.";
	
	@Override
	public void insertTmsOrder(TmsOrder order) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertTmsOrder", order);
	}

	@Override
	public void insertTmsOrderItem(List<TmsOrderItem> orderItemList) {
		// TODO Auto-generated method stub
		for(int i=0;orderItemList!=null && i<orderItemList.size();i++){
			this.insert(statement_item+"insertTmsOrderItem", orderItemList.get(i));
		}
	}

	@Override
	public List<TmsOrder> getTmsOrderByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<TmsOrder>) this.selectList(statement+"getTmsOrderByList", params);
	}

	@Override
	public List<TmsOrderItem> getTmsOrderItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<TmsOrderItem>) this.selectList(statement_item+"getTmsOrderItemByList",params);
	}

	@Override
	public void updateTmsOrder(TmsOrder tmsOrder) {
		// TODO Auto-generated method stub
		this.update(statement+"updateTmsOrder", tmsOrder);
	}

	@Override
	public TmsOrder getTmsOrderById(String id) {
		// TODO Auto-generated method stub
		return (TmsOrder) this.selectOne(statement+"getTmsOrderById", id);
	}

	@Override
	public void updateTmsOrderItem(TmsOrderItem shipOrderDetail) {
		// TODO Auto-generated method stub
		this.update(statement_item+"updateTmsOrderItem", shipOrderDetail);
	}

	@Override
	public void deleteTmsOrderItembyId(String id) {
		// TODO Auto-generated method stub
		this.delete(statement_item+"deleteTmsOrderItemById", id);
	}

	@Override
	public int getTotalByOrderId(String orderId) {
		return (Integer) this.selectOne(statement+"getTotalByOrderId", orderId);
	}

}
