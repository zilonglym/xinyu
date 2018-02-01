package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.AuditRules;
import com.graby.store.service.base.AuditRulesService;

@RemotingService(serviceInterface = AuditRulesRemote.class, serviceUrl = "/auditRules.call")


public class AuditRulesRemoteImpl implements AuditRulesRemote {
	
	@Autowired
	private AuditRulesService auditRulesService;
	
	
	
	public void save(AuditRules auditRules){
		auditRulesService.save(auditRules);
	}
	
	public List<AuditRules>  getAuditRulesList(Map<String,Object> param){
		return auditRulesService.getAuditRules(param);
	}

	@Override
	public AuditRules findAuditRulesbyId(String id) {
		// TODO Auto-generated method stub
		return this.auditRulesService.findAuditRulesbyId(id);
	}

	@Override
	public void updateAuditRules(AuditRules auditRules) {
		// TODO Auto-generated method stub
		this.auditRulesService.updateAuditRules(auditRules);
	}
	

}
