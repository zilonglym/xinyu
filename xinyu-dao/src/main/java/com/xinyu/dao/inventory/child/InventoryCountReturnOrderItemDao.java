package com.xinyu.dao.inventory.child;
import java.util.List;
import java.util.Map;
import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;

public interface InventoryCountReturnOrderItemDao extends BaseDao {
	
	 public void saveInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info);
	 
	 public void updateInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info);
	 
	 public InventoryCountReturnOrderItem getInventoryCountReturnOrderItemById(String id);
	 
	 public List<InventoryCountReturnOrderItem> getInventoryCountReturnOrderItemByList(Map<String, Object> params);
	 
	 public List<InventoryCountReturnOrderItem> getInventoryCountReturnOrderItemByInventoryCountId(String inventoryCountId);
}
