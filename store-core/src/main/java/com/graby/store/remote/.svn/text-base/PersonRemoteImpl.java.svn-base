package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Person;
import com.graby.store.service.base.PersonService;

/**
 * 员工信息
 * */
@RemotingService(serviceInterface = PersonRemote.class, serviceUrl = "/person.call")
public class PersonRemoteImpl implements PersonRemote{
	
	@Autowired
	private PersonService personService;
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Person> searchPerson(Map<String, Object> params) {
		return personService.searchPerson(params);
	}
	/**
	 * id搜索员工（单个）
	 * @param id
	 * @return Person
	 * */
	@Override
	public Person findPersonById(Long id) {
		return personService.findPersonById(id);
	}
	/**
	 * 更新员工信息
	 * @param Person
	 * */
	@Override
	public void updatePerson(Person person) {
		personService.updatePerson(person);
	}
	
	/**
	 * 新增员工
	 * @param Person
	 * */
	@Override
	public void savePerson(Person person) {
		personService.savePerson(person);
	}
	
	/**
	 * 条件搜索员工（单个）
	 * @param params
	 * @return Person
	 * */
	@Override
	public Person findPersonByQ(Map<String, Object> params) {
		return personService.findPersonByQ(params);
	}
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Person> getPersons(Map<String, Object> params, int page, int rows) {
		return personService.getPersons(params,page,rows);
	}
	

	/**
	 * 搜索所有员工（多个）
	 * @return list
	 * */
	@Override
	public List<Person> findPersons() {
		// TODO Auto-generated method stub
		return personService.findPersons();
	}
	
	/**
	 * 删除员工（单个）
	 * @param Person
	 * */
	@Override
	public void delete(Person person) {
		this.personService.delete(person);
	}

}
