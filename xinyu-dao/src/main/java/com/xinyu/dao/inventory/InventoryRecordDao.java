package com.xinyu.dao.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.inventory.InventoryRecord;

public interface InventoryRecordDao {
	
	void insertInventoryRecord(InventoryRecord inventoryRecord);

	List<InventoryRecord> findInventoryRecordsByList(Map<String, Object> params);

	List<InventoryRecord> findInventoryRecordsByPage(Map<String, Object> params, int page, int rows);

	int getTotal(Map<String, Object> params);

}
