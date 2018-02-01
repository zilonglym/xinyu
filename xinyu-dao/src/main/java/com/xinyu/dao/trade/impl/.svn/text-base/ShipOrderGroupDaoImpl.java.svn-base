package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderGroupDao;
import com.xinyu.model.trade.ShipOrderGroup;

@Repository("shipOrderGroupDaoImpl")
public class ShipOrderGroupDaoImpl extends BaseDaoImpl implements ShipOrderGroupDao {

	@Override
	public void insertShipOrderGroup(ShipOrderGroup group) {
		// TODO Auto-generated method stub

		this.insert("com.xinyu.dao.shipOrderGroupDao.insertShipOrderGroup", group);
	}

	@Override
	public List<ShipOrderGroup> getShipOrderGroupByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrderGroup>) this.selectList("com.xinyu.dao.shipOrderGroupDao.getShipOrderGroupByList", params);
	}

	@Override
	public void insertShipOrderGroupList(List<ShipOrderGroup> groupList) {
		// TODO Auto-generated method stub
		for(int i=0;groupList!=null && i<groupList.size();i++){
			this.insertShipOrderGroup(groupList.get(i));
		}
	}

}
