package com.xinyu.check.service;

import java.util.List;
import java.util.Map;

import com.xinyu.check.model.Person;

/**
 * 员工信息
 * */

public interface PersonService extends BaseService {
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	List<Person> findPersonByList(Map<String,Object> params);
	
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
	 * 新增员工处理
	 * @param perosn
	 * */
	void insertPerson(Person person) ;
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	List<Person> findPersonByPage(Map<String, Object> params,int page,int rows);
	
	/**
	 * 删除员工（单个）
	 * @param Person
	 * */
	void deletePerson(Person person);
	
	/**
	 * 员工数据重组
	 * @param persons
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<Person> persons);

}
