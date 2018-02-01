package com.xinyu.service.system;
import java.util.List;
import java.util.Map;
import com.xinyu.service.common.BaseService;
import com.xinyu.model.system.AccountMenu;

/**
 * 系统菜单
 * */
public interface AccountMenuService extends BaseService {
	
	/**
	 * 新建菜单
	 * @param AccountMenu
	 * */
	public void saveAccountMenu(AccountMenu info);
	 
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
	
	/**
	 * 根据用户角色查询菜单信息
	 * @param params
	 * @return
	 */
	public List<AccountMenu> getAccountMenuByAccount(Map<String, Object> params);

	/**
	 * 菜单信息封装
	 * @param map
	 * @return list
	 * */
	public List<Map<String, Object>> buildListData(List<AccountMenu> menus);

	
	public List<Map<String, Object>> buildRowsMenusListData(String rolesId);
}
