package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;

@MyBatisRepository
public interface CheckInventoryDao {
	
	
	public void save(CheckInventory checkInventory);
	
	public CheckInventory getCheckInventoryById(Long id);
	
	public void saveDetail(CheckInventoryDetail checkInventoryDetail);
	
	public List<CheckInventory> getCheckInventory(Map<String,Object> params);
	/**
	 * 单据明细id查询
	 * */
	public List<CheckInventoryDetail> checkInventoryDetailbyId(Map<String, Object> params);
	
	public void updateCheckInventoryAudit(Map<String,Object> params);
	
}
