package com.xinyu.dao.inventory.child.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.inventory.child.InventoryCountReturnOrderItemDao;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
@Repository("inventoryCountReturnOrderItemDaoImpl")
public class InventoryCountReturnOrderItemDaoImpl extends BaseDaoImpl implements InventoryCountReturnOrderItemDao {
	
	private final String statement = "com.xinyu.dao.inventory.child.InventoryCountReturnOrderItemDao.";
	
	public void saveInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info){
		this.insert(statement+"insertInventoryCountReturnOrderItem",info);
	}
	public void updateInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info){
		this.insert(statement+"updateInventoryCountReturnOrderItem",info);
	}
	public InventoryCountReturnOrderItem getInventoryCountReturnOrderItemById(String id){
		return (InventoryCountReturnOrderItem)this.selectOne(statement+"getInventoryCountReturnOrderItemById",id);
	}
	public List<InventoryCountReturnOrderItem> getInventoryCountReturnOrderItemByList(Map<String, Object> params){
		return (List<InventoryCountReturnOrderItem>)this.selectList(statement+"getInventoryCountReturnOrderItemByList",params);
	}
	
	public List<InventoryCountReturnOrderItem> getInventoryCountReturnOrderItemByInventoryCountId(String inventoryCountId){
		return (List<InventoryCountReturnOrderItem>)this.selectList(statement+"getInventoryCountReturnOrderItemByInventoryCountId",inventoryCountId);
	}
	
}
