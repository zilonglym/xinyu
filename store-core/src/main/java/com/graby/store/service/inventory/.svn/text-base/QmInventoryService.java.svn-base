package com.graby.store.service.inventory;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.ShipOrder;

/**
 * 库存
 * @author  shark_cj
 * 
 */
public interface QmInventoryService {
	 Long  existOrderNo(Map<String,Object> params) ;
	 
	 void  addInventory(Long centroId,Long userId, Long itemId ,Long num,String account,String type,String description);

	 void batchAddInventory(ShipOrder order, String type,String description);
	 
	 void batchDelInventory(ShipOrder order, String type,String description);
	 
	 List<Map<String,Object>> inItInventory(List<Map<String,Object>>  param);
	 
	 public void checkInventory(CheckInventory checkInventory);
	 
	 void  deleteOrderNo(Map<String,Object> params);
	 
	 
	 void  adminAddInventory(Long centroId,Long userId, Long itemId ,Long num,String type,String description);
}
