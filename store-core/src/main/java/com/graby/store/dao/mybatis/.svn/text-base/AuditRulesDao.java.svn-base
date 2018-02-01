package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.AuditRules;


@MyBatisRepository
public interface AuditRulesDao {
	
	void save(AuditRules  auditRules);
	
	List<AuditRules> findAuditRules(Map<String,Object> param);
	
	List<AuditRules>  findAuditRulesList(Map<String,Object> param);
	
	AuditRules findAuditRulesbyId(String id);
	
	void updateAuditRules(AuditRules auditRules);

}
