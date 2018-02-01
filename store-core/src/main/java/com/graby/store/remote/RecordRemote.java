package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.Record;

/**
 * excel表格导入记录明细
 * */
public interface RecordRemote {
	
	/**
	 * excel导入记录列表数据分页显示
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<Record> findRecords(Map<String, Object> params,int page,int rows);
	
	/**
	 * excel导入记录保存
	 * @param Record 
	 * */
	void insert(Record record);
	
	/**
	 * 更新excel导入记录
	 * @param Record 
	 * */
	void update(Record record);
	
	/**
	 * excel导入记录总记录数
	 * @param params
	 * @return int
	 * */
	int getTotalResult(Map<String, Object> params);
	
	/**
	 * 物流发货统计
	 * @param params
	 * @return list
	 * */
	List<Map<String, Object>> getCountByExpressName(Map<String, Object> params);
	
	/**
	 * 物流发货利润明细统计
	 * @param params
	 * @return list
	 * */
	List<Map<String, Object>> getProfitByExpressName(Map<String, Object> params);
	
	/**
	 *物流发货总利润统计
	 *@param params
	 *@return list
	 * */
	List<Map<String, Object>> getTotalProfitByExpressName(Map<String, Object> params);

	/**
	 * excel导入记录列表数据
	 * @param params
	 * @return list
	 * */
	List<Record> findRecordList(Map<String, Object> params);

}
