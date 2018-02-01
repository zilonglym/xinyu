package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.AuditRules;
import com.graby.store.entity.store.UserShop;

public interface UserShopRemote {
	
	
	public List<UserShop> getUserShopListByUserId(String userId);

	public void save(UserShop shop);

	public void delete(UserShop shop);

}
