package com.graby.store.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.store.UserShop;

@MyBatisRepository
public interface UserShopDao {
	
	public List<UserShop> getUserShopListByUserId(String userId) ;
	
	public void delete(UserShop shop);
	
	public void insert(UserShop shop);
	
	public void update(UserShop shop);
	
	/*{
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		
	//	return (List<UserShop>) this.selectList(this.statement+"getUserShopList", params);
		return null;
	}

	public void insert(UserShop shop) {
		// TODO Auto-generated method stub
		//this.insert(this.statement+"insertUserShop", shop);
	}

	public void update(UserShop shop) {
		// TODO Auto-generated method stub
		//super.update(this.statement+"updateUserShop", shop);
	}

	public void delete(UserShop shop) {
		// TODO Auto-generated method stub
	//	super.delete(this.statement+"deleteUserShop", shop);
	}*/

}
