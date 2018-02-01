package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.AuditArea;


@MyBatisRepository
public interface AuditAreaDao {
	
	void save(AuditArea  auditArea);
	
	List<AuditArea> findAuditArea(Map<String,Object> params);

}
