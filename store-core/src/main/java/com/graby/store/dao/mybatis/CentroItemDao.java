package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.CentroItem;

@MyBatisRepository
public interface CentroItemDao {
	
	List<CentroItem> findCentroItems(Map<String, Object> params);
	
	void saveCentroItem(CentroItem centroItem);
	
	void updateCentroItemQuantity(Map<String, Object> params);
}
