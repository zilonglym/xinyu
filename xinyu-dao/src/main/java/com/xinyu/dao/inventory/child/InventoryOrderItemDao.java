package com.xinyu.dao.inventory.child;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.inventory.child.InventoryOrderItem;

public interface InventoryOrderItemDao extends BaseDao{

	List<InventoryOrderItem> getInventoryOrderItemByList(Map<String, Object> params);

	void insertInventoryOrderItem(InventoryOrderItem item);

}
