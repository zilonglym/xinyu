package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.system.Person;

/**
 * 员工信息
 * */
public interface PersonDao extends BaseDao{
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	List<Person> findPersonByList(Map<String,Object> params);
	
	/**
	 * 条件搜索员工（分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<Person> findPersonByPage(Map<String,Object> params,int page,int rows);
	
	/**
	 * id搜索员工（单个）
	 * @param id
	 * @return Person
	 * */
	Person findPersonById(String id);
	
	/**
	 * 更新员工信息
	 * @param Person
	 * */
	void updatePerson(Person person);
	
	/**
	 * 新增员工
	 * @param Person
	 * */
	void insertPerson(Person person) ;
	
	/**
	 * 删除员工（单个）
	 * @param Person
	 * */
	void deletePerson(Person person);

	Person findStroePersonById(String id);
}
