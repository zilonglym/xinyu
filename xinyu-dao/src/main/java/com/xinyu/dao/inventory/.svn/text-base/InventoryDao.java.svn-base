package com.xinyu.dao.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.inventory.Inventory;

public interface InventoryDao {
	
	/**
	 * 添加库存
	 * id
	 * num
	 * @param params
	 */
	void addNumByItemId(Map<String,Object> params);

	void  insertInventory(Inventory inventory);

	List<Map<String, Object>> findInventoryByPage(Map<String, Object> params, int page, int rows);

	List<Inventory> findInventoryByList(Map<String, Object> params);

	Inventory findInventoryByParam(Map<String, Object> params);
	
	void updateInventory(Inventory inventory);
	
	int isExist(Map<String,Object> params);

	Inventory getInventoryById(String id);

	/**
	 * 获取对应库存数量
	 * */
	Map<String, Object> getInventoryNumByParam(Map<String, Object> retMap);

	int getTotal(Map<String, Object> params);


}
