package com.xinyu.service.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.inventory.InventoryRecord;
import com.xinyu.service.common.BaseService;

/**
 * 库存流水记录业务处理接口
 * */
public interface InventoryRecordService extends BaseService{

	/**
	 * 分页查询库存流水记录
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<InventoryRecord> findInventoryRecordsByPage(Map<String, Object> params, int page, int rows);

	/**
	 * 计数库存流水记录
	 * @param params
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);

	/**
	 * 库存流水记录数据重组
	 * @param inventoryCounts
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<InventoryRecord> records);
	
	/**
	 * 查询库存流水记录
	 * @param params
	 * @return list
	 * */
	List<InventoryRecord> findInventoryRecordsByList(Map<String, Object> params);

	/**
	 * 生成库存流水记录
	 * */
	void saveInventoryRecord(InventoryRecord record);

}
