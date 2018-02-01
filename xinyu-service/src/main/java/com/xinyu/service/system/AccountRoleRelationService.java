package com.xinyu.service.system;
import java.util.List;
import java.util.Map;
import com.xinyu.service.common.BaseService;
import com.xinyu.model.system.AccountRoleRelation;

/**
 * 账号角色关联
 * */
public interface AccountRoleRelationService extends BaseService {
	
	/**
	 * 新建关联
	 * @param AccountRoleRelation
	 * */
	public void saveAccountRoleRelation(AccountRoleRelation info);
	
	/**
	 * 修改关联
	 * @param AccountRoleRelation
	 * */
	public void updateAccountRoleRelation(AccountRoleRelation info);
	
	/**
	 * 根据ID查关联
	 * @param id
	 * @return AccountRoleRelation
	 * */
	public AccountRoleRelation getAccountRoleRelationById(String id);

	/**
	 * 条件查关联
	 * @param map
	 * @return list
	 * */
	public List<AccountRoleRelation> getAccountRoleRelationByList(Map<String, Object> params);

	/**
	 * 根据账号查关联角色
	 * @param accountId
	 * @return AccountRoleRelation
	 * */
	public AccountRoleRelation findAccountRoleRelationByAccountId(String accountId);
}
