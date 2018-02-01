package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.User;

/**
 * 商家用户DAO实现接口
 * */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	/**
	 * 条件批量查询用户信息
	 * @param map
	 * @return list
	 * */
	@Override
	public List<User> getUserBySearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<User>) super.selectList("com.xinyu.dao.base.UserDao.getUserBySearch", params);
	}
	/**
	 * 新建用户
	 * @param User
	 * */
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		super.insert("com.xinyu.dao.base.UserDao.insertUser", user);
	}
	/**
	 * 更新用户
	 * @param User
	 * */
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		super.update("com.xinyu.dao.base.UserDao.updateUser", user);
	}
	/**
	 * 根据ID查询用户信息
	 * @param id
	 * @return user
	 * */
	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return (User) super.selectOne("com.xinyu.dao.base.UserDao.getUserById", id);
	}
	/**
	 * 条件批量查询用户信息(分页)
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<User> getUserByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return super.selectList("com.xinyu.dao.base.UserDao.getUserBySearch", params, rows, page);
	}
	
	/**
	 * 根据ownerCode查user
	 * @param ownerCode
	 * @return user
	 * */
	@Override
	public User findUserByOwnerCode(String ownerCode) {
		return (User) super.selectOne("com.xinyu.dao.base.UserDao.findUserByOwnerCode", ownerCode);
	}
	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne("com.xinyu.dao.base.UserDao.getTotal", params);
	}
	@Override
	public Map<String, Object> getStoreUserById(String userId) {
		return (Map<String, Object>) super.selectOne("com.xinyu.dao.base.UserDao.getStoreUserById", userId);
	}
	

}
