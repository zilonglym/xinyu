package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.StoreProcessItem;

@MyBatisRepository
public interface ProcessItemDao {
	
	List<StoreProcessItem> selectByExample(Map<String,Object> params);
	
	List<StoreProcessItem> selectByparentId(Integer parentId);
	
	void deleteByPrimaryKey(Integer id);
	
	void insert(StoreProcessItem item);
	

}
