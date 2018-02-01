package com.graby.store.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.User;

@Component
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 返回所有用户
	 * @return
	 */
	public List<User> findUsers(){
		return userDao.findUsers();
	}
	public List<User> findAll() {
		return userDao.findAll();
	}
	/**
	 * 根据店铺名查找用户
	 * @param shopName
	 * @return
	 */
	public User findUserByShopName(String shopName){
		return this.userDao.findUserByShopName(shopName);
	}
	
	public User getUser(long userid) {
		return userDao.get(userid);
	}
	
	public void updateDesc(long id, String desc) {
		userDao.updateDesc(id, desc);
	}
	
	public void save(User user) {
		userDao.save(user);
	}
	
	public User getUserByCode(String code){
		return this.userDao.getUserByCode(code);
	}
	
	public User getUserByOwnerCode(String ownercode){
		return this.userDao.getUserByOwnerCode(ownercode);
	}
	
	public void updateUserCentro(User user){
		this.userDao.updateUserCentro(user);
	}
	
	public  void updateUserTbUserId(User user){
		this.userDao.updateUserTbUserId(user);
	}
}
