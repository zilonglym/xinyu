package com.xinyu.service.system.impl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import  org.springframework.beans.factory.annotation.Autowired;import  com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.AccountRoleRelationService;
import com.xinyu.dao.base.AccountRoleRelationDao;
import com.xinyu.model.system.AccountRoleRelation;

/**
 * 账号角色关联
 * */
@Repository("accountRoleRelationServiceImpl")
public class AccountRoleRelationServiceImpl extends BaseServiceImpl implements AccountRoleRelationService {

	@Autowired
	private AccountRoleRelationDao roleRelationDao;
	
	/**
	 * 新建关联
	 * @param AccountRoleRelation
	 * */
	@Override
	public void saveAccountRoleRelation(AccountRoleRelation info) {
		this.roleRelationDao.insertAccountRoleRelation(info);
	}

	/**
	 * 修改关联
	 * @param AccountRoleRelation
	 * */
	@Override
	public void updateAccountRoleRelation(AccountRoleRelation info) {
		this.roleRelationDao.updateAccountRoleRelation(info);
	}

	/**
	 * 根据ID查关联
	 * @param id
	 * @return AccountRoleRelation
	 * */
	@Override
	public AccountRoleRelation getAccountRoleRelationById(String id) {
		return this.roleRelationDao.getAccountRoleRelationById(id);
	}
	/**
	 * 条件查关联
	 * @param map
	 * @return list
	 * */
	@Override
	public List<AccountRoleRelation> getAccountRoleRelationByList(Map<String, Object> params) {
		return this.roleRelationDao.getAccountRoleRelationByList(params);
	}

	/**
	 * 根据账号查关联角色
	 * @param accountId
	 * @return AccountRoleRelation
	 * */
	@Override
	public AccountRoleRelation findAccountRoleRelationByAccountId(String accountId) {
		return this.roleRelationDao.findAccountRoleRelationByAccountId(accountId);
	}
	
}
