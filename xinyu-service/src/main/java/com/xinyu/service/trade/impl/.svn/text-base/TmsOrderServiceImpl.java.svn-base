package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.TmsOrderDao;
import com.xinyu.model.common.Pagination;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.TmsOrderService;

@Service("tmsOrderService")
public class TmsOrderServiceImpl extends BaseServiceImpl implements TmsOrderService {

	@Autowired
	private TmsOrderDao tmsOrderDao;
	
	
	public void insertTmsOrder(TmsOrder order) {
		// TODO Auto-generated method stub
		this.tmsOrderDao.insertTmsOrder(order);
	}

	
	public void insertTmsOrderItem(List<TmsOrderItem> orderItemList) {
		// TODO Auto-generated method stub
		this.tmsOrderDao.insertTmsOrderItem(orderItemList);
	}

	
	public List<TmsOrder> getTmsOrderByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.tmsOrderDao.getTmsOrderByList(params);
	}

	
	public List<TmsOrderItem> getTmsOrderItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.tmsOrderDao.getTmsOrderItemByList(params);
	}

	
	public TmsOrder getTmsOrderById(String id) {
		// TODO Auto-generated method stub
		return this.tmsOrderDao.getTmsOrderById(id);
	}

	
	public void updateTmsOrder(TmsOrder tmsOrder) {
		// TODO Auto-generated method stub
		this.tmsOrderDao.updateTmsOrder(tmsOrder);
	}


	public int getTotalByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return this.tmsOrderDao.getTotalByOrderId(orderId);
//		return 0;
	}
}
