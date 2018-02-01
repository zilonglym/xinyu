package com.xinyu.service.trade.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.dao.trade.ShipOrderStopDao;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ShipOrderStopService;



@Repository("ShipOrderStopServiceImpl")
public class ShipOrderStopServiceImpl extends BaseServiceImpl implements ShipOrderStopService {
	@Autowired
	private ShipOrderStopDao service;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ShipOrderDao orderDao;
	
	@Autowired
	private ReceiverInfoDao receiverInfoDao;
	
	
	

	public void saveShipOrderStop(ShipOrderStop info) {
		this.service.saveShipOrderStop(info);
	}

	public void updateShipOrderStop(ShipOrderStop info) {
		this.service.updateShipOrderStop(info);
	}

	public ShipOrderStop getShipOrderStopById(String id) {
		return this.service.getShipOrderStopById(id);
	}

	public List<ShipOrderStop> getShipOrderStopByList(Map<String, Object> params) {
		return this.service.getShipOrderStopByList(params);
	}

	@Override
	public List<ShipOrderStop> getShipOrderStopByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.service.getShipOrderStopByPage(params,page,rows);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.service.getTotal(params);
	}

	@Override
	public List<Map<String, Object>> buildOrderStop(List<ShipOrderStop> orderStops) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for (ShipOrderStop orderStop:orderStops) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createDate", sf.format(orderStop.getCreateTime()));
			User user = this.userDao.getUserById(orderStop.getUserId());
			map.put("userName", user.getSubscriberName());
			ShipOrder shipOrder = this.orderDao.getShipOrderById(orderStop.getOrderId());
			map.put("tbNumber", shipOrder.getErpOrderCode());
			map.put("company", shipOrder.getTmsServiceName());
			map.put("orderNo", orderStop.getExpressOrderno());
			ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
			map.put("receiverName", receiverInfo.getReceiverName());
			map.put("items", shipOrder.getItems());
			map.put("description", orderStop.getDescription());
			results.add(map);
		}	
		return results;
	}
}
