package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;

/**
 * 库存服务
 * serviceUrl = "/checkInventory.call"
 */
public interface CheckInventoryRemote {
	
	
	  void insert(CheckInventory  checkInventory);
	  
	  CheckInventory getCheckInventoryById(Long  id);
		
	  List<CheckInventory> getCheckInventory(Map<String,Object> params);
		
	  List<CheckInventoryDetail> checkInventoryDetailbyId(Map<String,Object> params);
	  
	  void updateCheckInventoryAudit(Map<String,Object> params);
}