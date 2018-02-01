package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrderReturn;


@MyBatisRepository
public interface ShipOrderReturnDao {
	
	public List<ShipOrderReturn> findShipOrderByexpress(Map<String,Object> params);
	
	public void save(ShipOrderReturn orderReturn);
	
	
}
