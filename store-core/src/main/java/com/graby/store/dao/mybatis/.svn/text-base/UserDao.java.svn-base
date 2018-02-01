package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.User;

@MyBatisRepository
public interface UserDao {

	User findUserByUsername(String username);
	User findUserByShopName(String shopName);
	User get(long id);
	User getUserByOwnerCode(String ownercode);
	void save(User user);
	void updateDesc(long id, String desc);
	void delete(Long id);
	List<User> findAll();
	 User getUserByCode(String code);
	 List<User> findUsers();
	 void updateUserCentro(User user);
	 
	 void updateUserTbUserId(User user);
	
}
