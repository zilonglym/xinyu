package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Record;
import com.graby.store.service.finance.RecordService;

/**
 * excel表格导入记录明细
 * */
@RemotingService(serviceInterface = RecordRemote.class, serviceUrl = "/record.call")
public class RecordRemoteImpl implements RecordRemote{
	
	@Autowired
	private RecordService recordService;
	
	/**
	 * excel导入记录列表数据分页显示
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Record> findRecords(Map<String, Object> params,int page,int rows) {
		return this.recordService.findRecords(params,page,rows);
	}

	/**
	 * excel导入记录保存
	 * @param Record
	 * */
	@Override
	public void insert(Record record) {
		this.recordService.insert(record);
	}
	
	/**
	 * 更新excel导入记录
	 * @param Record
	 * */
	@Override
	public void update(Record record) {
		this.recordService.update(record);
	}
	
	/**
	 * excel导入记录总记录数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.recordService.getTotalResult(params);
	}
	
	/**
	 * 物流发货统计
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> getCountByExpressName(Map<String, Object> params) {
		return this.recordService.getCountByExpressName(params);
	}
	
	/**
	 * 物流发货利润明细统计
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> getProfitByExpressName(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.recordService.getProfitByExpressName(params);
	}
	
	/**
	 *物流发货总利润统计
	 *@param params
	 *@return list
	 * */
	@Override
	public List<Map<String, Object>> getTotalProfitByExpressName(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.recordService.getTotalProfitByExpressName(params);
	}
	
	/**
	 * excel导入记录列表数据
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Record> findRecordList(Map<String, Object> params) {
		return this.recordService.findRecordList(params);
	}

}
