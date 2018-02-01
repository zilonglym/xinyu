package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.ShipOrderBackItemDao;
import com.xinyu.model.trade.ShipOrderBackItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ShipOrderBackItemService;

@Service("shipOrderBackItemService")
public class ShipOrderBackItemServiceImpl extends BaseServiceImpl implements ShipOrderBackItemService{

	@Autowired
	private ShipOrderBackItemDao orderBackItemDao;
	
	public List<ShipOrderBackItem> getOrderBackItemList(Map<String, Object> params) {
		return this.orderBackItemDao.getOrderBackItemList(params);
	}

	public void insertOrderBackItem(ShipOrderBackItem orderBackItem) {
		this.orderBackItemDao.insertOrderBackItem(orderBackItem);
	}

	public void updateOrderBackItem(ShipOrderBackItem orderBackItem) {
		this.orderBackItemDao.updateOrderBackItem(orderBackItem);
	}

	public void deleteOrderBackItemById(String id) {
		this.orderBackItemDao.deleteOrderBackItemById(id);
	}

	public void deleteOrderBackItemByBackId(String orderBackId) {
		this.orderBackItemDao.deleteOrderBackItemByBackId(orderBackId);
	}

}
