package com.xinyu.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.UserDao;
import com.xinyu.model.base.User;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.UserService;

/**
 * 商家用户服务实现
 * */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl implements UserService{

	public static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;

	/**
	 * 条件批量查询用户信息
	 * @param map
	 * @return list
	 * */
	@Override
	public List<User> getUserBySearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.userDao.getUserBySearch(params);
	}
	/**
	 * 新建用户
	 * @param User
	 * */
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		this.userDao.saveUser(user);
	}
	/**
	 * 更新用户
	 * @param User
	 * */
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		this.userDao.updateUser(user);
	}
	/**
	 * 根据ID查询用户信息
	 * @param id
	 * @return user
	 * */
	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return this.userDao.getUserById(id);
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
		return this.userDao.getUserByPage(params,page,rows);
	}
	/**
	 * 符合条件的用户数量
	 * @param map
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		return this.userDao.getTotal(params);
	}
	
	/**
	 * 根据第三方货主ID查询User
	 * @param ownerCode
	 * @return
	 * */
	@Override
	public User getUserByOwnerCode(String ownerCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ownerCode", ownerCode);
		return this.userDao.getUserBySearch(params).get(0);
	}
	
	/**
	 * 数据封装
	 * @param users
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<User> users) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(User user:users){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			map.put("tbUserId", user.getTbUserId());
			map.put("subscriberName", user.getSubscriberName());
			map.put("subscriberNick", user.getSubscriberNick());
			map.put("subscriberMobile", user.getSubscriberMobile());
			map.put("subscriberPhone", user.getSubscriberPhone());
			map.put("subscriberContactEmail", user.getSubscriberContactEmail());
			map.put("subscriberAddress", user.getSubscriberAddress());
			map.put("ownerCode", user.getOwnerCode());
			map.put("content", user.getContent());
			map.put("remark", user.getRemark());
			resultList.add(map);
		}
		return resultList;
	}
	@Override
	public Map<String, Object> getStoreUserById(String userId) {
		return this.userDao.getStoreUserById(userId);
	}
	
}
