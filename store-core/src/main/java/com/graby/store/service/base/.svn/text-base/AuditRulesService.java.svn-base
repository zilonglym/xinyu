package com.graby.store.service.base;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.AuditRulesDao;
import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.AuditRules;

@Component
@Transactional
public class AuditRulesService {
	private static Logger logger = LoggerFactory.getLogger(AuditRulesService.class);
	
	@Autowired
	private AuditRulesDao auditRulesDao;
	
	public void save(AuditRules auditRules){
		auditRulesDao.save(auditRules);
	}
	
	public List<AuditRules>  getAuditRules(Map<String,Object> param){
		return auditRulesDao.findAuditRules(param);
	}
	
	public List<AuditRules>  findAuditRulesList(Map<String,Object> param){
		return auditRulesDao.findAuditRulesList(param);
	}
	
	public AuditRules findAuditRulesbyId(String id){
		return this.auditRulesDao.findAuditRulesbyId(id);
	}
	
	public void updateAuditRules(AuditRules auditRules){
		this.auditRulesDao.updateAuditRules(auditRules);
	}
	
}
