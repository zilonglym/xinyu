package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AuditAreaDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.AuditArea;

/**
 * 顺丰地址信息处理Dao接口实现
 * */
@Repository("auditAreaDaoImpl")
public class AuditAreaDaoImpl extends BaseDaoImpl implements AuditAreaDao{

	private final String statement = "com.xinyu.dao.base.AuditAreaDao.";
	
	/**
	 * 顺丰地址信息条件查询
	 * @param params
	 * @return list
	 * */
	@Override
	public List<AuditArea> findAuditAreaByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<AuditArea>) this.selectList(this.statement+"findAuditAreaByList", params);
	}
	
	/**
	 * 新增顺丰地址信息
	 * @param auditArea
	 * */
	@Override
	public void insertAuditArea(AuditArea auditArea) {
		this.insert(this.statement+"insertAuditArea", auditArea);
	}

}
