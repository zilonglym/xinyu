package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.entity.UserMenu;
import com.graby.store.entity.UserRolesRow;
import com.graby.store.service.base.AuthService;
import com.graby.store.service.base.UserMenuRolesService;
import com.graby.store.service.wms.SystemItemService;

@RemotingService(serviceInterface = AuthRemote.class, serviceUrl = "/auth.call")
public class AuthRemoteImpl implements AuthRemote {

	@Autowired
	private AuthService authService;
	@Autowired
	private SystemItemService systemItemService;
	@Autowired
	private UserMenuRolesService rolesService;
	
	public User findUserByUsername(String username) {
		return authService.findUserByUsername(username);
	}

	@Override
	public List<SystemItem> getSystemItemList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String type=(String) params.get("type");
		return this.systemItemService.findSystemItemByType(type);
	}

	@Override
	public List<UserMenu> getUserMenuByUser(String userId) {
		// TODO Auto-generated method stub
		return rolesService.findMenuByUser(userId);
	}

	@Override
	public List<UserRolesRow> getUserRowByUser(String userId) {
		return this.rolesService.findRowsByUser(userId);
	}

}