package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.ShipOrder;

/**
 * 库存服务
 * serviceUrl = "/inventory.call"
 */
public interface InventoryRemote {
	
	
	/**
	 * 条码 扫描是否已 出库
	 * @param params
	 * @return
	 */
	Long  existOrderNo(Map<String,Object> params);
	/**
	 * 按科目统计库存值
	 * @param centroId
	 * @param itemId
	 * @param account
	 * @return
	 */
	public Long getValue(Long centroId, Long itemId, String account);
	
 void  adminAddInventory(Long centroId,Long userId, Long itemId ,Long num,String type,String description);

	/**
	 * 库存统计
	 * @param centroId
	 * @param userId
	 * @return
	 */
	List<Map<String, Long>> stat(Long centroId, Long userId); 
	
	 void  addInventory(Long centroId,Long userId, Long itemId ,Long num,String account,String type,String description);

	 void batchAddInventory(ShipOrder order, String type,String description);
	 
	 void batchDelInventory(ShipOrder order, String type,String description);
	 
	 
	List<Map<String, Object>> inItInventory(List<Map<String, Object>> param);

	List<Map<String, Object>> statPage(int page, int rows,Map<String, Object> params);

	Long statPageCount(Map<String, Object> params);
	
	void updateUserNum(Long centroId, Long itemId, String account, long usenum);
	
}