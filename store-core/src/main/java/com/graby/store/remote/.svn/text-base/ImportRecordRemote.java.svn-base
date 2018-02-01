package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.ImportRecord;


/**
 * 导入remote
 * serviceUrl = "/importRecordRemote.call"
 */
public interface ImportRecordRemote {

	
	/**
	 * 查询最后一个批次导入的信息
	 * @return
	 */
	public List<ImportRecord> findLastBatchImportRecord();

	
	/**
	 * 查询 导入记录
	 * @param params
	 * @return
	 */
	public List<ImportRecord> findImportRecord(int page,int rows,Map<String,Object> params);
	
	
	public Long  findImportRecordCount(Map<String,Object> params);
}
