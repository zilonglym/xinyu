package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.store.UserShop;
import com.graby.store.service.store.UserShopService;


@RemotingService(serviceInterface = UserShopRemote.class, serviceUrl = "/userShop.call")
public class UserShopRemoteImpl implements UserShopRemote {
	
	@Autowired
	private   UserShopService  userShopService;
	
	
	
	public List<UserShop> getUserShopListByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.userShopService.getUserShopListByUserId(userId);
	}

	public void save(UserShop shop) {
		this.userShopService.save(shop);
	}

	public void delete(UserShop shop) {
		// TODO Auto-generated method stub
		this.userShopService.delete(shop);
	}
	
}
