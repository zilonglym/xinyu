package com.xinyu.dao.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.inventory.InventoryCount;

public interface InventoryCountDao {
	
	void insertInventoryCount(InventoryCount inventoryCount);

	List<InventoryCount> findInventoryCountsByPage(Map<String, Object> params, int page, int rows);

	List<InventoryCount> findInventoryCountsByList(Map<String, Object> params);

	InventoryCount getInventoryCountById(String id);

	void updateInventoryCount(InventoryCount inventoryCount);

	int getTotal(Map<String, Object> params); 
}
