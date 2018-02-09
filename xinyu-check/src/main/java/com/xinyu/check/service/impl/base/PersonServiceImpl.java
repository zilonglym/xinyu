package com.xinyu.check.service.impl.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.check.dao.PersonDao;
import com.xinyu.check.model.Person;
import com.xinyu.check.service.PersonService;

/**
 * 员工信息
 * */
@Service("personServiceImpl")
public class PersonServiceImpl extends BaseServiceImpl implements PersonService{

	public static final Logger logger = Logger.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private PersonDao perosnDao;
	
	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Person> findPersonByList(Map<String, Object> params) {
		return this.perosnDao.findPersonByList(params);
	}

	
	/**
	 * id搜索员工（单个）
	 * @param id
	 * @return Person
	 * */
	@Override
	public Person findPersonById(String id) {
		return this.perosnDao.findPersonById(id);
	}

	/**
	 * 更新员工信息
	 * @param Person
	 * */
	@Override
	public void updatePerson(Person person) {
		this.perosnDao.updatePerson(person);
	}


	/**
	 * 条件搜索员工（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Person> findPersonByPage(Map<String, Object> params,int page,int rows) {
		return this.perosnDao.findPersonByPage(params,page,rows);
	}

	/**
	 * 删除员工（单个）
	 * @param Person
	 * */
	@Override
	public void deletePerson(Person person) {
		this.perosnDao.deletePerson(person);
	}


	/**
	 * 新增员工处理
	 * @param JSONObject
	 * @param map
	 * */
	@Override
	public void insertPerson(Person person) {
		this.perosnDao.insertPerson(person);;
	}


	/**
	 * 员工数据重组
	 * @param persons
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<Person> persons) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(Person person:persons){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", person.getId());
			map.put("idCard", person.getIdCard());
			map.put("name", person.getName());
			map.put("phone", person.getPhone());
			map.put("roles", person.getRoles());
			map.put("inputDate", sf.format(person.getInputDate()));
			resultList.add(map);
		}
		return resultList;
	}

}
