package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.service.common.BaseService;

/**
 * 账号信息业务接口
 * */
public interface AccountService extends BaseService{

	/**
	 * 分页查询账号信息
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<Account> findAccountsPage(Map<String, Object> params, int page, int rows);

	/**
	 * 符合条件的账号数量
	 * @param params
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);

	/**
	 * 新建账号
	 * @param account
	 * 
	 * */
	void insertAccout(Account account);
	
	void insertAccountRelation(AccountRelation relation);

	/**
	 * 根据ID查账号信息
	 * @param id
	 * @return account
	 * */
	Account findAcountById(String id);

	/**
	 * 更新账号
	 * @param account
	 * */
	void updateAccount(Account account);
	
	List<AccountRelation> getAccountRelationByList(Map<String,Object> params);

	/**
	 * 数据封装
	 * @param accounts
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<Account> accounts);

	/**
	 * 查询账号信息
	 * @param params
	 * @return list
	 * */
	List<Account> findAccountsByList(Map<String, Object> params);

}
