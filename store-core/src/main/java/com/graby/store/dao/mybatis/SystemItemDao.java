package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.SystemItem;

@MyBatisRepository
public interface SystemItemDao {
	
	public SystemItem findSystemItem(int id);
	
	
	public List<SystemItem> findSystemItemByParentId(int parentId);
	
	public List<SystemItem> findSystemItemByType(String type);
	
	public List<SystemItem> findSystemItemByTypeAndVal(Map<String,Object> params);
	
}
