package com.graby.store.service.record;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ImportRecordDao;
import com.graby.store.entity.ImportRecord;



/**
 *  ERP导入记录
 * */
@Component
@Transactional
public class ImportRecordService {
	
	@Autowired
	private  ImportRecordDao importRecordDao;
	
	
	/**
	 * 查询最后一个批次导入的信息
	 * @return
	 */
	public List<ImportRecord> findLastBatchImportRecord(){
		return  importRecordDao.findLastBatchImportRecord();
	}

	
	/**
	 * 查询 导入记录
	 * @param params
	 * @return
	 */
	public List<ImportRecord> findImportRecord(int page,int rows,Map<String,Object> params){
		int start=(page-1)*rows;
		int offset=rows;
		params.put("offset", offset);
		params.put("start", start);
		return importRecordDao.findImportRecord(params);
	}
	
	public Long  findImportRecordCount(Map<String,Object> params){
		return importRecordDao.findImportRecordCount(params);
	}

}
