package com.xinyu.check.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.check.dao.PersonDao;
import com.xinyu.check.dao.base.BaseDaoImpl;
import com.xinyu.check.model.Person;

/**
 * 员工信息
 * */
@Repository("personDaoImpl")
public class PersonDaoImpl extends BaseDaoImpl implements PersonDao{
	
	private final String statement="com.xinyu.dao.base.PersonDao.";
	
	public List<Person> findPersonByList(Map<String, Object> params) {
		return (List<Person>) this.selectList(this.statement+"findPersonByList", params);
	}

	public Person findPersonById(String id) {
		return (Person) this.selectOne(this.statement+"findPersonById", id);
	}

	/**
	 * 更新员工信息
	 * @param person
	 * */
	@Override
	public void updatePerson(Person person) {
		this.update(this.statement+"updatePerson",person);
	}

	/**
	 * 保存员工信息
	 * @param person
	 * */
	@Override
	public void insertPerson(Person person) {
		this.insert(this.statement+"insertPerson", person);
	}


	/**
	 * 条件查询员工信息（分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Person> findPersonByPage(Map<String, Object> params,int page,int rows) {
		return this.selectList(this.statement+"findPersonByList", params, rows, page);
	}

	/**
	 * 删除员工
	 * @param person
	 * */
	@Override
	public void deletePerson(Person person) {
		this.delete(this.statement+"deletePerson", person);
	}

}
