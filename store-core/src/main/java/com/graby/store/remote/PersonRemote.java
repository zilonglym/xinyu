package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.Person;

/**
 * 员工信息
 * */
public interface PersonRemote {
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	public List<Person> searchPerson(Map<String,Object> params);
	
	/**
	 * 条件搜索员工（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<Person> getPersons(Map<String, Object> params,int page,int rows);
	
	/**
	 * 搜索所有员工（多个）
	 * @return list
	 * */
	public List<Person> findPersons();
	
	/**
	 * id搜索员工（单个）
	 * @param id
	 * @return Person
	 * */
	public Person findPersonById(Long id);
	
	/**
	 * 更新员工信息
	 * @param Person
	 * */
	public void updatePerson(Person person);
	
	/**
	 * 新增员工
	 * @param Person
	 * */
	public void savePerson(Person person);
	
	/**
	 * 条件搜索员工（单个）
	 * @param params
	 * @return Person
	 * */
	public Person findPersonByQ(Map<String,Object> params);

	/**
	 * 删除员工（单个）
	 * @param Person
	 * */
	public void delete(Person person);
}
