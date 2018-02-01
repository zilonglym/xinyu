package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.local.LocalItem;

@MyBatisRepository
public interface LocalItemDao {
	
	LocalItem getLocalItemById(int id);
	
	void save(LocalItem localItem);
	
	List<LocalItem> getLocalItemList(Map<String, Object> params);
	
	int getLocalItemCount(Map<String, Object> params);
	
	void updateLocalItem(LocalItem localItem);

	void delete(String id);
	
}
