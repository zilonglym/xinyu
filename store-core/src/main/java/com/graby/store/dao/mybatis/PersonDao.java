package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Person;

/**
 * 员工信息
 * */
@MyBatisRepository
public interface PersonDao {
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	List<Person> searchPerson(Map<String,Object> params);
	
	/**
	 * 搜索所有员工（多个）
	 * @return list
	 * */
	List<Person> findPersons();
	
	/**
	 * id搜索员工（单个）
	 * @param id
	 * @return Person
	 * */
	Person findPersonById(Long id);
	
	/**
	 * 更新员工信息
	 * @param Person
	 * */
	void updatePerson(Person person);
	
	/**
	 * 新增员工
	 * @param Person
	 * */
	void savePerson(Person person) ;
	
	/**
	 * 条件搜索员工（单个）
	 * @param params
	 * @return Person
	 * */
	Person findPersonByQ(Map<String,Object> params);
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	List<Person> getPersons(Map<String, Object> params);
	
	/**
	 * 删除员工（单个）
	 * @param Person
	 * */
	void delete(Person person);
}
