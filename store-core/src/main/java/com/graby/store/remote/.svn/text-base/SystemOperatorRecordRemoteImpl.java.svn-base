package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.service.base.SystemOperatorRecordService;

@RemotingService(serviceInterface = SystemOperatorRecordRemote.class, serviceUrl = "/system.call")
public class SystemOperatorRecordRemoteImpl implements SystemOperatorRecordRemote {
	
	@Autowired
	private SystemOperatorRecordService systemService;

	@Override
	public void insert(SystemOperatorRecord record) {
		// TODO Auto-generated method stub
		this.systemService.insert(record);
	}

	@Override
	public List<SystemOperatorRecord> getSystemOperatorRecordByUser(int userId) {
		// TODO Auto-generated method stub
		return this.systemService.getSystemOperatorRecordByUser(userId);
	}

	@Override
	public List<SystemOperatorRecord> getSystemOperatorRecordByModel(OperatorModel model) {
		// TODO Auto-generated method stub
		return this.systemService.getSystemOperatorRecordByModel(model);
	}

	@Override
	public List<SystemOperatorRecord> findOperatorRecordsByParams(Map<String, Object> params, int page, int rows) {
		return this.systemService.findOperatorRecordsByParams(params,page,rows);
	}

	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.systemService.getTotalResult(params);
	}

}
