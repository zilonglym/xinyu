package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrderCancel;

@MyBatisRepository
public interface ShipOrderCancelDao {

	void save(ShipOrderCancel cancel);
	
	List<ShipOrderCancel> selectByList(Map<String,Object> params);

	int selectByCount(Map<String, Object> params);

	List<ShipOrderCancel> getShipOrderCancelByList(Map<String, Object> params);
}
