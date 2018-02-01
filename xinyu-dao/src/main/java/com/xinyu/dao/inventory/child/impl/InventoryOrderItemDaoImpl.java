package com.xinyu.dao.inventory.child.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.inventory.child.InventoryOrderItemDao;
import com.xinyu.model.inventory.child.InventoryOrderItem;

@Repository("inventoryOrderItemDaoImpl")
public class InventoryOrderItemDaoImpl extends BaseDaoImpl implements InventoryOrderItemDao{
	
	@Override
	public List<InventoryOrderItem> getInventoryOrderItemByList(Map<String, Object> params) {
		return (List<InventoryOrderItem>) super.selectList("com.xinyu.dao.inventory.child.InventoryOrderItemDao.getInventoryOrderItemByList",params);
	}

	@Override
	public void insertInventoryOrderItem(InventoryOrderItem item) {
		super.insert("com.xinyu.dao.inventory.child.InventoryOrderItemDao.insertInventoryOrderItem", item);
	}

}
