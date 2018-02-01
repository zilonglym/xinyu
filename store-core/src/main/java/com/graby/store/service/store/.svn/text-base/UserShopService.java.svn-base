package com.graby.store.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.UserShopDao;
import com.graby.store.entity.store.UserShop;

@Component
@Transactional
public class UserShopService {
	
	@Autowired
	private UserShopDao shopDao;

	public List<UserShop> getUserShopListByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.shopDao.getUserShopListByUserId(userId);
	}

	public void save(UserShop shop) {
		if(shop==null){
			return ;
		}
		if(shop.getId()!=null && shop.getId()!=0){
			//更新信息
			this.shopDao.update(shop);
		}else{
			//新增信息
			this.shopDao.insert(shop);
		}
	}

	public void delete(UserShop shop) {
		// TODO Auto-generated method stub
		this.shopDao.delete(shop);
	}

}
