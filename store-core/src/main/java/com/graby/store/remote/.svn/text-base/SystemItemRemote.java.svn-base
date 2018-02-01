package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.SystemItem;

public interface SystemItemRemote {

	SystemItem findSystemItem(int id);
	
	
	List<SystemItem> findSystemItemByParentId(int parentId);
	
	List<SystemItem> findSystemItemByType(String type);
	
	public List<SystemItem> findSystemItemByTypeAndVal(Map<String,Object> params);
	
}
