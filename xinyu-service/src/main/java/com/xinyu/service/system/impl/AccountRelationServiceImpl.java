package com.xinyu.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountRelationDao;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.AccountRelationService;

/**
 * 账号人员关联
 * */
@Repository("accountRelationServiceImpl")
public class AccountRelationServiceImpl extends BaseServiceImpl implements  AccountRelationService{

	@Autowired
	private AccountRelationDao relationDao;
	
	/**
	 * 根据人员查关联账号
	 * @param id
	 * @return AccountRelation
	 * */
	@Override
	public AccountRelation findAccountRlationByPersonId(String id) {
		return this.relationDao.findAccountRlationByPersonId(id);
	}

	@Override
	public AccountRelation getAccountRelationById(String id) {
		return this.relationDao.getAccountRelationById(id);
	}
	
}
