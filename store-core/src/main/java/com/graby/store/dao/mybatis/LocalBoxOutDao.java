package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.local.LocalBoxOut;


@MyBatisRepository
public interface LocalBoxOutDao {
	
	void save(LocalBoxOut  localBoxOut);
	
	List<LocalBoxOut> getLocalBoxOut(Map<String,Object> params);
	
	void updateLocalBoxOut(Map<String,Object> params);
	
	List<Map<String,Object>> getRows();
	
}
