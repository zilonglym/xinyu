package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.ShipOrderBackDao;
import com.xinyu.model.trade.ShipOrderBack;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ShipOrderBackService;

@Service("shipOrderBackService")
public class ShipOrderBackServiceImpl extends BaseServiceImpl implements ShipOrderBackService{

	@Autowired
	private ShipOrderBackDao orderBackDao;
	
	public ShipOrderBack getShipOrderBackByParams(Map<String, Object> params) {
		return this.orderBackDao.getShipOrderBackByParams(params);
	}

	public List<ShipOrderBack> getShipOrderBackList(Map<String, Object> params) {
		return this.orderBackDao.getShipOrderBackList(params);
	}

	public List<ShipOrderBack> getShipOrderBackListByPage(Map<String, Object> params, int page, int rows) {
		int pageNum = (page-1)*rows;
		int pageSize = rows;
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		List<ShipOrderBack> orderBacks = this.orderBackDao.getShipOrderBackList(params);
		return orderBacks;
	}

	public int getTotal(Map<String, Object> params) {
		return this.orderBackDao.getTotal(params);
	}

	public void insertShipOrderBack(ShipOrderBack orderBack) {
		this.orderBackDao.insertShipOrderBack(orderBack);
	}

	public void updateShipOrderBack(ShipOrderBack orderBack) {
		this.orderBackDao.updateShipOrderBack(orderBack);
	}

	public void deleteShipOrderBackById(String id) {
		this.orderBackDao.deleteShipOrderBackById(id);
	}

	@Override
	public List<Map<String, Object>> getOrderBackMapList(Map<String, Object> p) {
		return this.orderBackDao.getOrderBackMapList(p);
	}
	
	@Override
	public List<Map<String, Object>> findItemCount(Map<String, Object> p) {
		return this.orderBackDao.findItemCount(p);
	}

}
