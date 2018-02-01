package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.AuditArea;
import com.graby.store.express.yunda.YunDaApi;
import com.graby.store.service.base.AuditAreaService;

@RemotingService(serviceInterface = AuditAreaRemote.class, serviceUrl = "/auditArea.call")


public class AuditAreaRemoteImpl implements AuditAreaRemote {
	
	@Autowired
	private AuditAreaService auditAreaService;
	
	
	@Autowired
	private  YunDaApi  yunDaApi;
	
	@Override
	public Map<String,Object> checkAddressApi(Map<String,Map<String,Object>> maps){
		return  yunDaApi.checkYunDaAddressByList(maps);
	}
	
	
	@Override
	public void save(AuditArea auditArea){
		auditAreaService.save(auditArea);
	}

	@Override
	public Map<String, Object> importSfArea(List<Map<String, Object>> params) {
		// TODO Auto-generated method stub
		return auditAreaService.importSfArea(params);
	}

	@Override
	public Map<String, Object> checkArea(long tradeId) {
		// TODO Auto-generated method stub
		return auditAreaService.checkArea(tradeId);
	}

}
