package com.xinyu.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.base.AccountRelationDao;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.AccountService;

/**
 * 账号信息业务处理
 * */
@Service("accountServiceImpl")
public class AccountServiceImpl extends BaseServiceImpl implements AccountService{

	public static final Logger logger = Logger.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountRelationDao relationDao;
	
	/**
	 * 分页查询账号信息
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Account> findAccountsPage(Map<String, Object> params, int page, int rows) {
		return this.accountDao.findAccountsPage(params,page,rows);
	}

	/**
	 * 符合条件的账号数量
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		return this.accountDao.getTotal(params);
	}

	/**
	 * 新建账号
	 * @param account
	 * 
	 * */
	@Override
	public void insertAccout(Account account) {
		this.accountDao.insertAccout(account);
	}

	/**
	 * 根据ID查账号信息
	 * @param id
	 * @return account
	 * */
	@Override
	public Account findAcountById(String id) {
		return this.accountDao.findAcountById(id);
	}

	/**
	 * 更新账号
	 * @param account
	 * */
	@Override
	public void updateAccount(Account account) {
		this.accountDao.updateAccount(account);
	}

	@Override
	public List<AccountRelation> getAccountRelationByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.relationDao.getAccountRelationByList(params);
	}

	/**
	 * 数据封装
	 * @param accounts
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<Account> accounts) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(Account account:accounts){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", account.getId());
			map.put("userName", account.getUserName());
			map.put("password", account.getPassword());
			map.put("shortName", account.getShortName());
			map.put("mobile", account.getMobile());
			map.put("phone", account.getPhone());
			map.put("email", account.getEmail());
			resultList.add(map);
		}
		return resultList;
	}

	@Override
	public void insertAccountRelation(AccountRelation relation) {
		// TODO Auto-generated method stub
		this.relationDao.insertAccountRelation(relation);
	}

	/**
	 * 查询账号信息
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Account> findAccountsByList(Map<String, Object> params) {
		return this.accountDao.findAccountsByList(params);
	}

}
