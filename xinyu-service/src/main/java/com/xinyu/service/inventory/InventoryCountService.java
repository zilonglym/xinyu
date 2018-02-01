package com.xinyu.service.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.BaseService;

public interface InventoryCountService extends BaseService{

	List<InventoryCount> findInventoryCountsByPage(Map<String, Object> params, int page, int rows);
	
	List<InventoryCount> findInventoryCountsByList(Map<String, Object> params);

	int getTotal(Map<String, Object> params);

	List<Map<String, Object>> buildListData(List<InventoryCount> inventoryCounts);

	InventoryCount getInventoryCountById(String id);

	List<Map<String, Object>> buildDetailListData(List<InventoryCountReturnOrderItem> orderItems);
	
	void   insertInventoryCount(InventoryCount inventoryCount);

	void updateInventoryCount(InventoryCount inventoryCount);

	/**
	 * 创建盘点单
	 * @param data
	 * @param type 
	 * @return map
	 * */
	Map<String, Object> saveInventoryCount(Map<String, Object> data, String type);

}
