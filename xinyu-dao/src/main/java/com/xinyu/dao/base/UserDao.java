package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.User;

/**
 * 商家用户DAO接口
 * */
public interface UserDao extends BaseDao {
	/**
	 * 条件批量查询用户信息
	 * @param map
	 * @return list
	 * */
	List<User> getUserBySearch(Map<String,Object> params);
	/**
	 * 新建用户
	 * @param User
	 * */
	void saveUser(User user);
	/**
	 * 更新用户
	 * @param User
	 * */
	void updateUser(User user);
	/**
	 * 根据ID查询用户信息
	 * @param id
	 * @return user
	 * */
	User getUserById(String id);
	/**
	 * 条件批量查询用户信息(分页)
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<User> getUserByPage(Map<String, Object> params, int page, int rows);
	
	/**
	 * 根据ownerCode查user
	 * @param ownerCode
	 * @return user
	 * */
	User findUserByOwnerCode(String ownerCode);
	
	int getTotal(Map<String, Object> params);
	
	Map<String, Object> getStoreUserById(String userId);
	
}
