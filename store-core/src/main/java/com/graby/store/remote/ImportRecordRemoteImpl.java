package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ImportRecord;
import com.graby.store.service.record.ImportRecordService;


@RemotingService(serviceInterface = ImportRecordRemote.class, serviceUrl = "/importRecord.call")
public class ImportRecordRemoteImpl implements ImportRecordRemote {
	@Autowired
	private   ImportRecordService  importRecordService;
	
	
	@Override
	public List<ImportRecord> findLastBatchImportRecord() {
		// TODO Auto-generated method stub
		return importRecordService.findLastBatchImportRecord();
	}

	@Override
	public List<ImportRecord> findImportRecord(int page, int rows,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return importRecordService.findImportRecord(page, rows, params);
	}
	
	
	@Override
	public Long  findImportRecordCount(Map<String,Object> params){
		return importRecordService.findImportRecordCount(params);
		
	}

}
