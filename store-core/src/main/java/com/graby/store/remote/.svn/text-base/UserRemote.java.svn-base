package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.AuditRules;
import com.graby.store.entity.User;
import com.graby.store.entity.UserRolesRow;

public interface UserRemote {
	
	public List<User> findAll(String userId);
	
	void saveUser(User user);
	
	void updateUserCentro(User user);
	
	User  getUser(Long userId);
	
	void saveUserRolesRow(UserRolesRow userRolesRow);
	
	List<AuditRules>  findAuditRulesList(Map<String,Object> param);
	
	public List<User> findUsers();
}
