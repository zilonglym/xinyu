package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountRolesRowDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.AccountRolesRow;

/**
 * 角色菜单权限
 * */
@Repository("accountRolesRowDaoImpl")
public class AccountRolesRowDaoImpl extends BaseDaoImpl implements AccountRolesRowDao{

	private final String statement = "com.xinyu.dao.base.AccountRolesRowDao.";
	
	/**
	 * 新建权限
	 * @param AccountRolesRow
	 * */
	@Override
	public void insertAccountRolesRow(AccountRolesRow accountRolesRow) {
		super.insert(this.statement+"insertAccountRolesRow", accountRolesRow);
	}

	/**
	 * 修改权限
	 * @param AccountRolesRow
	 * */
	@Override
	public void updateAccountRolesRow(AccountRolesRow accountRolesRow) {
		super.update(this.statement+"updateAccountRolesRow", accountRolesRow);
	}

	/**
	 * 根据Id查权限
	 * @param id
	 * @return AccountRolesRow
	 * */
	@Override
	public AccountRolesRow getAccountRolesRowById(String id) {
		return (AccountRolesRow) super.selectOne(this.statement+"getAccountRolesRowById", id);
	}

	/**
	 * 条件查权限
	 * @param map
	 * @return list
	 * */
	@Override
	public List<AccountRolesRow> getAccountRolesRowByList(Map<String, Object> params) {
		return (List<AccountRolesRow>) super.selectList(this.statement+"getAccountRolesRowByList", params);
	}

	/**
	 * 删除权限
	 * @param id
	 * */
	@Override
	public void deleteAccountRolesRowById(String id) {
		super.delete(this.statement+"deleteAccountRolesRowById", id);
	}

}
