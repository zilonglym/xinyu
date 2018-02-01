package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountRelation;

/**
 *账号信息Dao实现
 * */
@Repository("accountDaoImpl")
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao{

	private static final Logger logger = Logger.getLogger(AccountDaoImpl.class);
	
	private final String statement = "com.xinyu.dao.base.AccountDao.";
	
	/**
	 * 分页查询账号信息
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Account> findAccountsPage(Map<String, Object> params, int page, int rows) {
		logger.error("account dao impl logger!");
		return super.selectList(this.statement+"findAccountsByList", params, rows, page);
	}

	/**
	 * 新建账号
	 * @param account
	 * 
	 * */
	@Override
	public void insertAccout(Account account) {
		super.insert(this.statement+"insertAccout", account);
	}

	/**
	 * 根据ID查账号信息
	 * @param id
	 * @return account
	 * */
	@Override
	public Account findAcountById(String id) {
		return (Account) super.selectOne(this.statement+"findAcountById", id);
	}

	/**
	 * 查询账号信息
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Account> findAccountsByList(Map<String, Object> params) {
		return (List<Account>) super.selectList(this.statement+"findAccountsByList", params);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(this.statement+"getTotal", params);
	}

	@Override
	public void updateAccount(Account account) {
		super.update(this.statement+"updateAccount", account);
	}


}
