package com.xinyu.dao.inventory.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.inventory.InventoryRecordDao;
import com.xinyu.model.inventory.InventoryRecord;



/**
 * @author shark_cj
 * @since 2017-05-02
 * 库存流水DAO
 */
@Repository("inventoryRecordImpl")
public class InventoryRecordImpl   extends BaseDaoImpl implements InventoryRecordDao {
	
	private final String statement = "com.xinyu.dao.inventory.InventoryRecordDao.";

	@Override
	public void insertInventoryRecord(InventoryRecord inventoryRecord) {
		super.insert(this.statement+"insertInventoryRecord", inventoryRecord);
	}

	@Override
	public List<InventoryRecord> findInventoryRecordsByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<InventoryRecord>) super.selectList(this.statement+"findInventoryRecordsByList", params);
	}

	@Override
	public List<InventoryRecord> findInventoryRecordsByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return super.selectList(this.statement+"findInventoryRecordsByList", params, rows, page);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) super.selectOne(this.statement+"getTotal", params);
	}
}
