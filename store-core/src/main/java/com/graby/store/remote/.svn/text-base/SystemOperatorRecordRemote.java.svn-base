package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.enums.OperatorModel;

public interface SystemOperatorRecordRemote {
	
	/**
	 * 新增一条操作记录
	 * @param record
	 */
	void insert(SystemOperatorRecord record);
	/**
	 * 按用户查询操作记录
	 * @param userId
	 * @return
	 */
	List<SystemOperatorRecord> getSystemOperatorRecordByUser(int userId);
	
	/**
	 * 按模块查询操作记录
	 * @param model
	 * @return
	 */
	List<SystemOperatorRecord> getSystemOperatorRecordByModel(OperatorModel model);
	
	/**
	 * 按param分页查询操作记录
	 * @param page,rows
	 * @return
	 */
	List<SystemOperatorRecord> findOperatorRecordsByParams(Map<String, Object> params, int page, int rows);
	
	/**
	 * 按param查询操作记录总数
	 * @param 
	 * @return
	 */
	int getTotalResult(Map<String, Object> params);
	
}

