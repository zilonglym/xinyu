
package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.entity.UserMenu;
import com.graby.store.entity.UserRolesRow;

/**
 * 验证服务
 * serviceUrl = "/auth.call"
 */
public interface AuthRemote {

	/**
	 * 查询用户
	 * @param username 用户名
	 * @return 用户
	 */
	public User findUserByUsername(String username);
	
	/**
	 * 取系统参数
	 * @param params
	 * @return
	 */
	public List<SystemItem> getSystemItemList(Map<String,Object> params);
	
	public List<UserMenu> getUserMenuByUser(String userId);
	
	public List<UserRolesRow> getUserRowByUser(String userId);
	
}