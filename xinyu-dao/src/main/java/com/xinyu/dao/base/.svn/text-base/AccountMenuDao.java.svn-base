package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.system.AccountMenu;

/**
 * 系统菜单
 * */
public interface AccountMenuDao extends BaseDao{

	/**
	 * 新建菜单
	 * @param AccountMenu
	 * */
	public void insertAccountMenu(AccountMenu info);
	
	/**
	 * 修改菜单
	 *  @param AccountMenu
	 * */
	public void updateAccountMenu(AccountMenu info);
	
	/**
	 * 根据ID查菜单信息
	 * @param id
	 * @return AccountMenu
	 * */
	public AccountMenu getAccountMenuById(String id);
	
	/**
	 * 条件查菜单信息
	 * @param map
	 * @return list
	 * */
	public List<AccountMenu> getAccountMenuByList(Map<String, Object> params);
	
	public List<AccountMenu> getAccountMenuByRoles(String roleId);
}
