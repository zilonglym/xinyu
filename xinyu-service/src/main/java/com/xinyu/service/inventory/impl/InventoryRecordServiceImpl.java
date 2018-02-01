package com.xinyu.service.inventory.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.inventory.InventoryRecordDao;
import com.xinyu.model.inventory.InventoryRecord;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.inventory.InventoryRecordService;

/**
 * 库存流水记录业务处理
 * */
@Service("inventoryRecordServiceImpl")
public class InventoryRecordServiceImpl extends BaseServiceImpl implements InventoryRecordService{

	@Autowired
	private InventoryRecordDao inventoryRecordDao;
	
	/**
	 * 分页查询库存流水记录
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<InventoryRecord> findInventoryRecordsByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.inventoryRecordDao.findInventoryRecordsByPage(params,page,rows);
	}

	/**
	 * 计数库存流水记录
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.inventoryRecordDao.getTotal(params);
	}

	/**
	 * 库存流水记录数据重组
	 * @param inventoryCounts
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<InventoryRecord> records) {
		// TODO Auto-generated method stub
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
//		for(InventoryRecord record:records){
//			Map<String, Object> map =  new HashMap<String, Object>();
//			map.put("userName", record.getUser().getSubscriberName());
//			map.put("account", record.getAccount().getUserName());
//			map.put("itemName", record.getItem().getName());
//			map.put("itemCode", record.getItem().getItemCode());
//			map.put("itemColor", record.getItem().getColor());
//			map.put("itemType", record.getItemType().getDescription());
//			map.put("operateType", record.getOperateType().getDescription());
//			map.put("num", record.getNum());
//			map.put("createDate", sf.format(record.getCreateDate()));
//			resultList.add(map);
//		}
		return resultList;
	}

	/**
	 * 查询库存流水记录
	 * @param params
	 * @return list
	 * */
	@Override
	public List<InventoryRecord> findInventoryRecordsByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.inventoryRecordDao.findInventoryRecordsByList(params);
	}

	/**
	 * 生成库存流水记录
	 * */
	@Override
	public void saveInventoryRecord(InventoryRecord record) {
		this.inventoryRecordDao.insertInventoryRecord(record);
	}

}
