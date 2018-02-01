package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.system.AccountRelation;

/**
 * 账号人员关联
 * */
public interface AccountRelationDao extends BaseDao{

	/**
	 * 根据人员查关联账号
	 * @param id
	 * @return AccountRelation
	 * */
	AccountRelation findAccountRlationByPersonId(String id);

	AccountRelation getAccountRelationById(String id);

	List<AccountRelation> getAccountRelationByList(Map<String, Object> params);
	void insertAccountRelation(AccountRelation relation) ;
}
