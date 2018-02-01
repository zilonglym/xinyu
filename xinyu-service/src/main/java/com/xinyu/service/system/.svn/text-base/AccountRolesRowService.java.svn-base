package com.xinyu.service.system;
import java.util.List;
import java.util.Map;
import com.xinyu.service.common.BaseService;
import com.xinyu.model.system.AccountRoles;
import com.xinyu.model.system.AccountRolesRow;

/**
 * 角色菜单权限
 * */
public interface AccountRolesRowService extends BaseService {

	/**
	 * 新建权限
	 * @param AccountRolesRow
	 * */
	public void saveAccountRolesRow(AccountRolesRow info);
	
	/**
	 * 修改权限
	 * @param AccountRolesRow
	 * */
	public void updateAccountRolesRow(AccountRolesRow info);
	 
	/**
	 * 根据Id查权限
	 * @param id
	 * @return AccountRolesRow
	 * */
	public AccountRolesRow getAccountRolesRowById(String id);
	 
	/**
	 * 条件查权限
	 * @param map
	 * @return list
	 * */
	public List<AccountRolesRow> getAccountRolesRowByList(Map<String, Object> params);

	/**
	 * 数据封装
	 * @param list
	 * @return list
	 * */
	public List<Map<String, Object>> buildListData(List<AccountRolesRow> rows);

	/**
	 * 根据角色Id删除关联
	 * @param ary
	 * @param roles
	 * @param remark
	 * */
	public void updateRows(String[] ary, AccountRoles roles, String remark);

	/**
	 * 根据角色Id新建关联
	 * @param ary
	 * @param roles
	 * @param remark
	 * */
	public void insertRows(String[] ary, AccountRoles roles, String remark);
}
