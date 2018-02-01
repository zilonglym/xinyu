package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountRelation;

/**
 * 账号信息Dao接口
 * */
public interface AccountDao extends BaseDao{

	/**
	 * 分页查询账号信息
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<Account> findAccountsPage(Map<String, Object> params, int page, int rows);

	/**
	 * 新建账号
	 * @param account
	 * 
	 * */
	void insertAccout(Account account);

	/**
	 * 根据ID查账号信息
	 * @param id
	 * @return account
	 * */
	Account findAcountById(String id);

	/**
	 * 查询账号信息
	 * @param params
	 * @return list
	 * */
	List<Account> findAccountsByList(Map<String, Object> params);

	int getTotal(Map<String, Object> params);

	void updateAccount(Account account);
	

}
