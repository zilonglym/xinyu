package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ImportRecord;

@MyBatisRepository
public interface ImportRecordDao {
	/**
	 * 导入 流水  记录
	 * @param checkOut
	 * */
	public void save(ImportRecord importRecord);
	
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
	public List<ImportRecord> findImportRecord(Map<String,Object> params);
	
	/**
	 * 记录数量
	 * @param params
	 * @return
	 */
	public Long findImportRecordCount(Map<String,Object> params);

}
