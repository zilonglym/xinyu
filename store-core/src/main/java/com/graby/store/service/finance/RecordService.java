package com.graby.store.service.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.RecordDao;
import com.graby.store.entity.Record;

/**
 * excel导入记录
 * */
@Component
@Transactional
public class RecordService {
	
	@Autowired
	private RecordDao recordDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<Record> findRecords(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start", start);
		params.put("offset",offset);
		List<Record> records=this.recordDao.findRecords(params);
		return records;
	}
	
	/**
	 * 新增
	 * @param Record
	 * */
	public void insert(Record record) {
		this.recordDao.insert(record);
	}
	
	/**
	 * 更新
	 * @param Record
	 * */
	public void update(Record record) {
		this.recordDao.update(record);
	}
	
	/**
	 * 总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params) {
		return this.recordDao.getTotalResult(params);
	}
	
	/**
	 * 分物流统计发货总数
	 * @param params
	 * @return
	 * */
	public List<Map<String, Object>> getCountByExpressName(Map<String, Object> params) {
		return this.recordDao.getCountByExpressName(params);
	}
	
	/**
	 * 分物流商家统计利润
	 * @param params
	 * @return
	 * */
	public List<Map<String, Object>> getProfitByExpressName(Map<String, Object> params) {
		return this.recordDao.getProfitByExpressName(params);
	}
	
	/**
	 * 分物流统计利润
	 * @param params
	 * @return
	 * */
	public List<Map<String, Object>> getTotalProfitByExpressName(Map<String, Object> params) {
		return  this.recordDao.getTotalProfitByExpressName(params);
	}
	
	/**
	 * excel导入记录列表数据
	 * @param params
	 * @return list
	 * */
	public List<Record> findRecordList(Map<String, Object> params) {
		return this.recordDao.findRecords(params);
	}
	
}
