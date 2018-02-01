package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountRolesDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.AccountRoles;

@Repository("accountRolesDaoImpl")
public class AccountRolesDaoImpl extends BaseDaoImpl implements AccountRolesDao{

	private final String statement = "com.xinyu.dao.base.AccountRolesDao.";
	
	@Override
	public void insertAccountRoles(AccountRoles roles) {
		super.insert(this.statement+("insertAccountRoles"), roles);
	}

	@Override
	public void updateAccountRoles(AccountRoles roles) {
		super.update(this.statement+"updateAccountRoles", roles);
	}

	@Override
	public AccountRoles getAccountRolesById(String id) {
		return (AccountRoles) super.selectOne(this.statement+"getAccountRolesById", id);
	}

	@Override
	public List<AccountRoles> getAccountRolesByList(Map<String, Object> params) {
		return (List<AccountRoles>) super.selectList(this.statement+"getAccountRolesByList", params);
	}

}
