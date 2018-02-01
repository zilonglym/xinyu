package com.xinyu.service.system;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.xinyu.model.base.User;
import com.xinyu.service.common.BaseService;

/**
 * 商家用户服务接口
 * */
public interface UserService extends BaseService{
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
	 * 符合条件的用户数量
	 * @param map
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);
	
	/**
	 * 奇门根据货主ID查询用户
	 * @param ownerCode
	 * @return user
	 * */
	User getUserByOwnerCode(String ownerCode);
	
	/**
	 * 数据封装
	 * @param users
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<User> users);
	
	Map<String, Object> getStoreUserById(String userId);
}
