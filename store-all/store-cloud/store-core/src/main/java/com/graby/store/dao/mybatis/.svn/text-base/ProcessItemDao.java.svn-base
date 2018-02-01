package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ProcessItem;

@MyBatisRepository
public interface ProcessItemDao {
	
	List<ProcessItem> selectByExample(Map<String,Object> params);
	
	ProcessItem selectById(Integer id);
	
	void deleteByPrimaryKey(Integer id);
	
	void insert(ProcessItem item);
	

}
