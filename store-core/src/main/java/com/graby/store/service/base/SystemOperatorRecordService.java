package com.graby.store.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.SystemOperatorRecordDao;
import com.graby.store.entity.Person;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.enums.OperatorModel;

/**
 * 员工信息
 * */
@Component
public class SystemOperatorRecordService {
	
	@Autowired
	private SystemOperatorRecordDao systemDao;
	
	/**
	 * 新增一条操作记录
	 * @param record
	 */
	public void insert(SystemOperatorRecord record){
		this.systemDao.insert(record);
	}
	/**
	 * 按用户查询操作记录
	 * @param userId
	 * @return
	 */
	public List<SystemOperatorRecord> getSystemOperatorRecordByUser(int userId){
		return this.systemDao.getSystemOperatorRecordByUser(userId);
	}
	
	/**
	 * 按模块查询操作记录
	 * @param model
	 * @return
	 */
	public List<SystemOperatorRecord> getSystemOperatorRecordByModel(OperatorModel model){
		return this.systemDao.getSystemOperatorRecordByModel(model);
	}
	
	public List<SystemOperatorRecord> findOperatorRecordsByParams(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start", start);
		params.put("offset", offset);
		List<SystemOperatorRecord> records=this.systemDao.findOperatorRecordsByParams(params);
		return records;
	}
	
	public int getTotalResult(Map<String, Object> params) {
		return this.systemDao.getTotalResult(params);
	}
	
}
