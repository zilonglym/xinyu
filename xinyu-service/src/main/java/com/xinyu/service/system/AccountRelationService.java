package com.xinyu.service.system;

import com.xinyu.model.system.AccountRelation;

/**
 * 账号人员关联
 * */
public interface AccountRelationService {

	/**
	 * 根据人员查关联账号
	 * @param id
	 * @return AccountRelation
	 * */
	AccountRelation findAccountRlationByPersonId(String id);

	/**
	 * 根据ID查关联
	 * @param id
	 * @return AccountRelation
	 * */
	AccountRelation getAccountRelationById(String id);

}
