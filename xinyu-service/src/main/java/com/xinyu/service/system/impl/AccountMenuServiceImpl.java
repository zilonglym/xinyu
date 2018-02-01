package com.xinyu.service.system.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.AccountMenuDao;
import com.xinyu.dao.base.AccountRoleRelationDao;
import com.xinyu.dao.base.AccountRolesRowDao;
import com.xinyu.model.system.AccountMenu;
import com.xinyu.model.system.AccountRoleRelation;
import com.xinyu.model.system.AccountRolesRow;
import com.xinyu.model.system.enums.StoreMenuModelEnums;
import  com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.AccountMenuService;

/**
 * 系统菜单
 * */
@Service("accountMenuServiceImpl")
public class AccountMenuServiceImpl extends BaseServiceImpl implements AccountMenuService {
	
	public static final Logger logger = Logger.getLogger(AccountMenuServiceImpl.class);
	
	@Autowired
	private AccountMenuDao accountMenuDao;
	@Autowired
	private AccountRoleRelationDao relationDao;
	
	@Autowired
	private AccountRolesRowDao rowDao;
	
	/**
	 * 新建菜单
	 * @param AccountMenu
	 * */
	@Override
	public void saveAccountMenu(AccountMenu info){
		this.accountMenuDao.insertAccountMenu(info);
	}
	
	/**
	 * 修改菜单
	 *  @param AccountMenu
	 * */
	@Override
	public void updateAccountMenu(AccountMenu info){
		this.accountMenuDao.updateAccountMenu(info);
	}
	
	/**
	 * 根据ID查菜单信息
	 * @param id
	 * @return AccountMenu
	 * */
	@Override
	public AccountMenu getAccountMenuById(String id){
		return this.accountMenuDao.getAccountMenuById(id);
	}
	
	/**
	 * 条件查菜单信息
	 * @param map
	 * @return list
	 * */
	@Override
	public List<AccountMenu> getAccountMenuByList(Map<String, Object> params){
		return this.accountMenuDao.getAccountMenuByList(params);
	}

	/**
	 * 菜单信息封装
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<AccountMenu> menus) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(int i = 0;i<menus.size();i++){
			AccountMenu menu = menus.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", menu.getId());
			map.put("index", i);
			map.put("title", menu.getTitle());
			map.put("link", menu.getLink());
			StoreMenuModelEnums[] models = StoreMenuModelEnums.values();
			for(StoreMenuModelEnums model:models){
				if (model.equals(menu.getMenus())) {
					map.put("menu", model.getTitle());
				}
			}
			results.add(map);
		}
		return results;
	}

	@Override
	public List<AccountMenu> getAccountMenuByAccount(Map<String, Object> params) {
		List<AccountRoleRelation> relationList=this.relationDao.getAccountRoleRelationByList(params);
		if(relationList!=null && relationList.size()>0){
			AccountRoleRelation relation =relationList.get(0);
			List<AccountMenu> menuList=this.accountMenuDao.getAccountMenuByRoles(relation.getRole().getId());
			return menuList;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> buildRowsMenusListData(String rolesId) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		List<AccountMenu> menus = this.accountMenuDao.getAccountMenuByList(null);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roles", rolesId);
		List<AccountRolesRow> rows = this.rowDao.getAccountRolesRowByList(params);
		for(int i=0;i<menus.size();i++){
			AccountMenu menu = menus.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", menus.get(i).getId());
			map.put("index", i);
			map.put("title", menus.get(i).getTitle());
			for(int j=0;j<rows.size();j++){
				AccountRolesRow row = rows.get(j);
				if (menu.getId().equals(row.getMenu().getId())) {
					map.put("check", "true");
				}
			}
			results.add(map);
		}
		return results;
	}
}
