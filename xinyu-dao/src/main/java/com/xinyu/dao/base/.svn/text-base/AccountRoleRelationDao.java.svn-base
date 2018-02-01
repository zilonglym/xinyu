package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.system.AccountRoleRelation;

/**
 * 账号角色关联
 * */
public interface AccountRoleRelationDao extends BaseDao{

	/**
	 * 新建关联
	 * @param AccountRoleRelation
	 * */
	void insertAccountRoleRelation(AccountRoleRelation info);

	/**
	 * 修改关联
	 * @param AccountRoleRelation
	 * */
	void updateAccountRoleRelation(AccountRoleRelation info);

	/**
	 * 根据ID查关联
	 * @param id
	 * @return AccountRoleRelation
	 * */
	AccountRoleRelation getAccountRoleRelationById(String id);

	/**
	 * 条件查关联
	 * @param map
	 * @return list
	 * */
	List<AccountRoleRelation> getAccountRoleRelationByList(Map<String, Object> params);

	/**
	 * 账号查关联角色
	 * @param accountId
	 * @return AccountRoleRelation
	 * */
	AccountRoleRelation findAccountRoleRelationByAccountId(String accountId);

}
