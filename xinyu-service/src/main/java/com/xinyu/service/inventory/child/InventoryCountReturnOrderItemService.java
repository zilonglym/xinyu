package com.xinyu.service.inventory.child;
import java.util.List;
import java.util.Map;
import com.xinyu.service.common.BaseService;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
public interface InventoryCountReturnOrderItemService extends BaseService {
	 public void saveInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info);
	 public void updateInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info);
	 public InventoryCountReturnOrderItem getInventoryCountReturnOrderItemById(String id);
	 public List<InventoryCountReturnOrderItem> getInventoryCountReturnOrderItemByList(Map<String, Object> params);
	
	 public List<Map<String, Object>> buildListData(List<InventoryCountReturnOrderItem> orderItems);
}
