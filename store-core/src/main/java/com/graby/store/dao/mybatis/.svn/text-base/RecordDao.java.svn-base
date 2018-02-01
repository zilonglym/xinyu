package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Record;

/**
 * excel表格导入明细
 * */
@MyBatisRepository
public interface RecordDao {
	
	/**
	 * excel导入记录列表数据显示
	 * @param params
	 * @return list
	 * */
	public List<Record> findRecords(Map<String, Object> params);
	
	/**
	 * excel导入记录总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params);
	
	/**
	 * 更新excel导入记录
	 * @param Record 
	 * */
	public void update(Record record);
	
	/**
	 * excel导入记录保存
	 * @param Record 
	 * */
	public void insert(Record record);
	
	/**
	 * 物流发货统计
	 * @param params
	 * @return list
	 * */
	public List<Map<String, Object>> getCountByExpressName(Map<String, Object> params);
	
	/**
	 * 物流发货利润明细统计
	 * @param params
	 * @return list
	 * */
	public List<Map<String, Object>> getProfitByExpressName(Map<String, Object> params);
	
	/**
	 *物流发货总利润统计
	 *@param params
	 *@return list
	 * */
	public List<Map<String, Object>> getTotalProfitByExpressName(Map<String, Object> params);
}
