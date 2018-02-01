package com.xinyu.dao.inventory.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.inventory.InventoryCountDao;
import com.xinyu.model.inventory.Inventory;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
import com.xinyu.model.inventory.enums.InventoryCountStateEnum;


/**
 * @author shark_cj
 * @since  2017-05-02
 *  库存盘点
 */
@Repository("inventoryCountDaoImpl")
public class InventoryCountDaoImpl extends BaseDaoImpl implements InventoryCountDao {

	private final String statement = "com.xinyu.dao.inventory.InventoryCountDao.";
	
	private final String statementDetail = "com.xinyu.dao.inventory.child.InventoryCountReturnOrderItemDao.";

	@Override
	public void insertInventoryCount(InventoryCount inventoryCount) {
		//如果未设置单据状态,默认状态 为 ：本地保存
		if(inventoryCount.getState()==null){
			inventoryCount.setState(InventoryCountStateEnum.getInventoryCountStateEnum("SAVE"));
		}
		super.insert(this.statement+"insertInventoryCount", inventoryCount);
		for(InventoryCountReturnOrderItem orderItem:inventoryCount.getItemList()){
			super.insert(this.statementDetail+"insertInventoryCountReturnOrderItem", orderItem);
		}
	}

	@Override
	public List<InventoryCount> findInventoryCountsByPage(Map<String, Object> params, int page, int rows) {
		return super.selectList(this.statement+"findInventoryCountsByList", params, rows, page);
	}

	@Override
	public List<InventoryCount> findInventoryCountsByList(Map<String, Object> params) {
		return (List<InventoryCount>) super.selectList(this.statement+"findInventoryCountsByList", params);
	}

	@Override
	public InventoryCount getInventoryCountById(String id) {
		InventoryCount  inventoryCount  =(InventoryCount) super.selectOne(this.statement+"getInventoryCountById", id);
		List<InventoryCountReturnOrderItem> items  =(List<InventoryCountReturnOrderItem>)this.selectList(statementDetail+"getInventoryCountReturnOrderItemByInventoryCountId",id);
		inventoryCount.setItemList(items);
		return inventoryCount;
	}

	@Override
	public void updateInventoryCount(InventoryCount inventoryCount) {
		super.update(this.statement+"updateInventoryCount", inventoryCount);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getTotal", params);
	}
}
