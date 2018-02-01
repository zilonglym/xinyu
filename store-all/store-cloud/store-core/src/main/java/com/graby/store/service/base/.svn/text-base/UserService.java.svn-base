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
	public List<User> findAll() {
		return userDao.findAll();
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
	
}
