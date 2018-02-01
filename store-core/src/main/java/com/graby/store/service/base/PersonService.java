package com.graby.store.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.PersonDao;
import com.graby.store.entity.Person;

/**
 * 员工信息
 * */
@Component
public class PersonService {
	
	@Autowired
	private PersonDao personDao;
	/**
	 * 条件搜索（多个）
	 * @param params
	 * @return
	 * */
	public List<Person> searchPerson(Map<String, Object> params) {
		return personDao.searchPerson(params);
	}
	
	/**
	 * 所有员工信息
	 * @param params
	 * @return
	 * */
	public List<Person> findPersons() {
		return personDao.findPersons();
	}
	/**
	 * id查询员工信息
	 * @param id
	 * @return
	 * */
	public Person findPersonById(Long id) {
		return personDao.findPersonById(id);
	}
	
	/**
	 * 更新
	 * @param Person
	 * */
	public void updatePerson(Person person) {
		personDao.updatePerson(person);
	}
	
	/**
	 * 新增
	 * @param Person
	 * */
	public void savePerson(Person person) {
		personDao.savePerson(person);
	}
	
	/**
	 * 条件查询（多个）
	 * @param params
	 * @return
	 * */
	public Person findPersonByQ(Map<String, Object> params) {
		return personDao.findPersonByQ(params);
	}
	
	/**
	 * 分页查询（多个）
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<Person> getPersons(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start", start);
		params.put("offset", offset);
		List<Person> persons=this.personDao.getPersons(params);
		return persons;
	}
	
	/**
	 * 删除
	 * @param Person
	 * */
	public void delete(Person person) {
		this.personDao.delete(person);
	}
	
}
