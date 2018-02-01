package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.trade.ShipOrderGroupDao;
import com.xinyu.model.trade.ShipOrderGroup;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ShipOrderGroupService;

@Service("shipOrderGroupService")
public class ShipOrderGroupServiceImpl extends BaseServiceImpl implements ShipOrderGroupService {

	@Autowired
	private ShipOrderGroupDao groupDao;
	
	@Override
	public void insertShipOrderGroup(ShipOrderGroup group) {
		// TODO Auto-generated method stub
		this.groupDao.insertShipOrderGroup(group);
	}

	@Override
	public List<ShipOrderGroup> getShipOrderGroupByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.groupDao.getShipOrderGroupByList(params);
	}

}
