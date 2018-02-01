package com.xinyu.dao.inventory.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.inventory.InventoryDao;
import com.xinyu.model.inventory.Inventory;


/**
 * @author shark_cj
 * @since  2017 -05-02
 * 实际库存DAO
 */
@Repository("inventoryDaoImpl")
public class InventoryDaoImpl  extends BaseDaoImpl  implements InventoryDao {

	private final String statement = "com.xinyu.dao.inventory.InventoryDao.";

	@Override
	public void addNumByItemId(Map<String,Object> params) {
		super.update(this.statement+"addNumByItemId", params);
	}
	
	@Override
	public void insertInventory(Inventory inventory) {
		super.insert(this.statement+"insertInventory", inventory);
	}

	@Override
	public List<Map<String, Object>> findInventoryByPage(Map<String, Object> params, int page, int rows) {
		return super.selectList(this.statement+"findInventoryByList", params, rows, page);
	}

	@Override
	public List<Inventory> findInventoryByList(Map<String, Object> params) {
		return (List<Inventory>) super.selectList(this.statement+"findInventoryByList", params);
	}

	@Override
	public void updateInventory(Inventory inventory) {
		super.update(this.statement+"updateInventory", inventory);
	}

	@Override
	public int isExist(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"isExist", params);
	}

	@Override
	public Inventory getInventoryById(String id) {
		return (Inventory) super.selectOne(this.statement+"getInventoryById", id);
	}

	@Override
	public Inventory findInventoryByParam(Map<String, Object> params) {
		return (Inventory) super.selectOne(this.statement+"getInventoryByParam", params);
	}

	@Override
	public Map<String, Object> getInventoryNumByParam(Map<String, Object> retMap) {
		return (Map<String, Object>) super.selectOne(this.statement+"getInventoryNumByParam", retMap);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getTotal", params);
	}

}
