package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountMenuDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.AccountMenu;

/**
 * 系统菜单
 * */
@Repository("accountMenuDaoImpl")
public class AccountMenuDaoImpl extends BaseDaoImpl implements AccountMenuDao{

	private final String statemnet = "com.xinyu.dao.base.AccountMenuDao.";
	

	/**
	 * 新建菜单
	 * @param AccountMenu
	 * */
	@Override
	public void insertAccountMenu(AccountMenu accountMenu) {
		super.insert(this.statemnet+"insertAccountMenu", accountMenu);
	}

	/**
	 * 修改菜单
	 *  @param AccountMenu
	 * */
	@Override
	public void updateAccountMenu(AccountMenu accountMenu) {
		super.insert(this.statemnet+"updateAccountMenu", accountMenu);
	}

	/**
	 * 根据ID查菜单信息
	 * @param id
	 * @return AccountMenu
	 * */
	@Override
	public AccountMenu getAccountMenuById(String id) {
		return (AccountMenu) super.selectOne(this.statemnet+"getAccountMenuById", id);
	}

	/**
	 * 条件查菜单信息
	 * @param map
	 * @return list
	 * */
	@Override
	public List<AccountMenu> getAccountMenuByList(Map<String, Object> params) {
		return (List<AccountMenu>) super.selectList(this.statemnet+"getAccountMenuByList", params);
	}

	
	@Override
	public List<AccountMenu> getAccountMenuByRoles(String roleId) {
		// TODO Auto-generated method stub
		return (List<AccountMenu>) this.selectList(statemnet+"getAccountMenuByRoles", roleId);
	}

}
