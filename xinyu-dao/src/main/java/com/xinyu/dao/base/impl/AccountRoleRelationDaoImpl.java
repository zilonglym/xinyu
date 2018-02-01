package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountRoleRelationDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.AccountRoleRelation;

/**
 * 账号角色关联
 * */
@Repository("accountRoleRelationDaoImpl")
public class AccountRoleRelationDaoImpl extends BaseDaoImpl implements AccountRoleRelationDao{

	private final String statement = "com.xinyu.dao.base.AccountRoleRelationDao.";
	
	/**
	 * 新建关联
	 * @param AccountRoleRelation
	 * */
	@Override
	public void insertAccountRoleRelation(AccountRoleRelation info) {
		super.insert(this.statement+"insertAccountRoleRelation", info);
	}

	/**
	 * 修改关联
	 * @param AccountRoleRelation
	 * */
	@Override
	public void updateAccountRoleRelation(AccountRoleRelation info) {
		super.update(this.statement+"updateAccountRoleRelation", info);
	}

	/**
	 * 根据ID查关联
	 * @param id
	 * @return AccountRoleRelation
	 * */
	@Override
	public AccountRoleRelation getAccountRoleRelationById(String id) {
		return (AccountRoleRelation) super.selectOne(this.statement+"getAccountRoleRelationById", id);
	}

	/**
	 * 条件查关联
	 * @param map
	 * @return list
	 * */
	@Override
	public List<AccountRoleRelation> getAccountRoleRelationByList(Map<String, Object> params) {
		return (List<AccountRoleRelation>) super.selectList(this.statement+"getAccountRoleRelationByList", params);
	}

	/**
	 * 账号查关联角色
	 * @param accountId
	 * @return AccountRoleRelation
	 * */
	@Override
	public AccountRoleRelation findAccountRoleRelationByAccountId(String accountId) {
		return (AccountRoleRelation) super.selectOne(this.statement+"findAccountRoleRelationByAccountId", accountId);
	}
	
}
