package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountRelationDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.AccountRelation;

/**
 * 账号人员关联
 * */
@Repository("accountRelationDaoImpl")
public class AccountRelationDaoImpl extends BaseDaoImpl implements AccountRelationDao{

	private final String statement = "com.xinyu.dao.base.AccountRelationDao.";
	
	/**
	 * 根据人员查关联账号
	 * @param id
	 * @return AccountRelation
	 * */
	@Override
	public AccountRelation findAccountRlationByPersonId(String objectId) {
		return (AccountRelation) super.selectOne(this.statement+"findAccountRlationByPersonId", objectId);
	}

	@Override
	public AccountRelation getAccountRelationById(String id) {
		return (AccountRelation) super.selectOne(this.statement+"getAccountRelationById", id);
	}
	
	

	@Override
	public List<AccountRelation> getAccountRelationByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<AccountRelation>) this.selectList(this.statement+"getAccountRelationByList", params);
	}

	@Override
	public void insertAccountRelation(AccountRelation relation) {
		// TODO Auto-generated method stub
		this.insert(this.statement+"insertAccountRelation", relation);
	}

}
