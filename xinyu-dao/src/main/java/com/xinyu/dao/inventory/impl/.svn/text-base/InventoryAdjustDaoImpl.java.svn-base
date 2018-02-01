package com.xinyu.dao.inventory.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.inventory.InventoryAdjustDao;
import com.xinyu.model.inventory.InventoryAdjustUpload;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.child.InventoryOrderItem;
import com.xinyu.model.inventory.enums.InventoryAdjustStateEnum;
import com.xinyu.model.inventory.enums.InventoryCountStateEnum;



/**
 * @author shark_cj
 * @since  2017-06-20
 * 库存调整单
 */
@Repository("inventoryAdjustDaoImpl")
public class InventoryAdjustDaoImpl extends BaseDaoImpl implements InventoryAdjustDao {
	
	private final String statement = "com.xinyu.dao.inventory.InventoryAdjustUploadDao.";
	
	private final String statementDetail = "com.xinyu.dao.inventory.child.InventoryOrderItemDao.";

	
	@Override
	public  void  insertInventoryAdjustUpload(InventoryAdjustUpload  inventoryAdjust){
		super.insert(this.statement+"insertInventoryAdjustUpload", inventoryAdjust);
	}
	
	@Override
	public void updateInventoryAdjustUpload(InventoryAdjustUpload  inventoryAdjust) {
		super.update(this.statement+"updateInventoryAdjustUpload", inventoryAdjust);
	}

	@Override
	public InventoryAdjustUpload getInventoryAdjustUploadById(String adjustId) {
		return (InventoryAdjustUpload) super.selectOne(this.statement+"getInventoryAdjustUploadById", adjustId);
	}

	@Override
	public List<InventoryAdjustUpload> getInventoryAdjustUploadListByParams(Map<String, Object> params) {
		return (List<InventoryAdjustUpload>) super.selectList(this.statement+"getInventoryAdjustUploadListByParams", params);
	}

	@Override
	public int getToTal(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getToTal", params);
	}
	
	
}
