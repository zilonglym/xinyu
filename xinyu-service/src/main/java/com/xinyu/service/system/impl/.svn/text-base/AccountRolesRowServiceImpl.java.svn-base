package com.xinyu.service.system.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import  org.springframework.beans.factory.annotation.Autowired;
import  com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.AccountRolesRowService;
import com.xinyu.dao.base.AccountMenuDao;
import com.xinyu.dao.base.AccountRolesDao;
import com.xinyu.dao.base.AccountRolesRowDao;
import com.xinyu.model.system.AccountMenu;
import com.xinyu.model.system.AccountRoles;
import com.xinyu.model.system.AccountRolesRow;

/**
 * 角色菜单权限
 * */
@Service("accountRolesRowServiceImpl")
public class AccountRolesRowServiceImpl extends BaseServiceImpl implements AccountRolesRowService {
	
	public static final Logger logger = Logger.getLogger(AccountRolesRowServiceImpl.class);
	
	@Autowired
	private AccountRolesRowDao accountRolesRowDao;
	
	@Autowired
	private AccountMenuDao accountMenuDao;
	
	@Autowired
	private AccountRolesDao accountRolesDao;
	
	/**
	 * 新建权限
	 * @param AccountRolesRow
	 * */
	@Override
	public void saveAccountRolesRow(AccountRolesRow row){
		this.accountRolesRowDao.insertAccountRolesRow(row);
	}
	
	/**
	 * 修改权限
	 * @param AccountRolesRow
	 * */
	@Override
	public void updateAccountRolesRow(AccountRolesRow row){
		this.accountRolesRowDao.updateAccountRolesRow(row);
	}
	
	/**
	 * 根据Id查权限
	 * @param id
	 * @return AccountRolesRow
	 * */
	@Override
	public AccountRolesRow getAccountRolesRowById(String id){
		return this.accountRolesRowDao.getAccountRolesRowById(id);
	}
	
	/**
	 * 条件查权限
	 * @param map
	 * @return list
	 * */
	@Override
	public List<AccountRolesRow> getAccountRolesRowByList(Map<String, Object> params){
		return this.accountRolesRowDao.getAccountRolesRowByList(params);
	}

	/**
	 * 数据封装
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<AccountRolesRow> rows) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(AccountRolesRow row : rows){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", row.getId());
			AccountRoles roles = this.accountRolesDao.getAccountRolesById(row.getRoles().getId());
			map.put("roles", roles.getName());
			map.put("remark", row.getRemark());
			AccountMenu menu = this.accountMenuDao.getAccountMenuById(row.getMenu().getId());
			map.put("model", menu.getTitle());
			results.add(map);
		}
		return results;
	}

	/**
	 * 删除已存在的关联,再重新建
	 * @param ary
	 * @param roles
	 * @param remark
	 * 
	 * */
	@Override
	public void updateRows(String[] ary, AccountRoles roles,String remark) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roles", roles.getId());
		List<AccountRolesRow> rows = this.accountRolesRowDao.getAccountRolesRowByList(params);
		for(AccountRolesRow row:rows){
			this.accountRolesRowDao.deleteAccountRolesRowById(row.getId());
		}
		this.insertRows(ary, roles, remark);
	}

	/**
	 * 新建关联关系
	 * @param ary
	 * @param roles
	 * @param remark
	 * */
	@Override
	public void insertRows(String[] ary, AccountRoles roles,String remark) {
		for(int i=0;i<ary.length;i++){
			AccountMenu menu = this.accountMenuDao.getAccountMenuById(ary[i]);
			AccountRolesRow row = new AccountRolesRow();
			row.generateId();
			row.setRoles(roles);
			row.setMenu(menu);
			row.setRemark(remark);
//			row.setCu("");
			this.accountRolesRowDao.insertAccountRolesRow(row);
		}
	}
}
