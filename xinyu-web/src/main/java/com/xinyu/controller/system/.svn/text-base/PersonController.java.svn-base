package com.xinyu.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xinyu.common.BaseController;
import com.xinyu.model.system.Person;
import com.xinyu.service.system.PersonService;

/**
 * 员工信息操作控制类
 * */
@Controller
@RequestMapping(value="person")
public class PersonController extends BaseController{
	
	@Autowired
	private PersonService personService;

	/**
	 * 员工列表
	 * @param HttpServletRequest
	 * @param ModelMap
	 * @return
	 * */
	@RequestMapping(value="/personList")
	public String PersonList(HttpServletRequest request,ModelMap model){
		return "admin/person/personList";
	}
	
	/**
	 * 员工列表数据填充(分页)
	 * @param HttpServletRequest
	 * @param page
	 * @param rows
	 * @return
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		
		if (rows==10) {
			rows=100;
		}
		
		Map<String,Object> params=new HashMap<String,Object>();
		String name=request.getParameter("name");
		params.put("name",name);
		
		List<Person> persons  =  this.personService.findPersonByPage(params, page,rows);
		
		int total = this.personService.findPersonByList(params).size();	
		
		List<Map<String,Object>> resultList =new ArrayList<Map<String,Object>>();
		buildPersonList(persons, resultList);
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("rows",resultList);
		result.put("page",page);
		result.put("total",total);
		
		return result;
	}
	
	/**
	 * 构建person列表
	 * @param persons
	 * @param resultList
	 */
	private void buildPersonList(List<Person> personList,List<Map<String,Object>> resultList){
		for(int i=0;personList!=null && i<personList.size();i++){
			Person person=personList.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("idCard", person.getIdCard());
			map.put("name", person.getName());
			map.put("age", person.getAge());
			map.put("phone", person.getPhone());
			map.put("roles", person.getRoles());
			map.put("inputDate", person.getInputDate());
			map.put("id", person.getId());
			map.put("operate", person.getId());
			map.put("ck", person.getId());
			resultList.add(map);		
		}
	}
	
	/**
	 * 添加员工信息表格
	 * @return
	 * */
	@RequestMapping(value="f7Add")
	public String personAdd(ModelMap model){
		return "admin/person/personAdd";
	}
	
	/**
	 * 提交保存员工信息
	 * @param HttpServletRequest
	 * @return
	 * @exception JSONException
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String,Object> personSave(HttpServletRequest request) throws JSONException{
		
		String json = request.getParameter("json");
		JSONObject object = new JSONObject(json);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		
// 		System.err.println("object:"+object);
		String age=object.getString("age");	
		String name=object.getString("name");
		String sex=object.getString("sex");
		String phone=object.getString("phone");
//		System.err.println("name:"+name);
		String id=object.getString("id");
		String idCard=object.getString("idCard");	
		
		params.put("idCard",idCard);
		List<Person> persons=this.personService.findPersonByList(params);
		
		if (persons.size()!=0&&id.isEmpty()) {
			resultMap.put("card","false");
		}else if(persons.size()==0&&id.isEmpty()){
			
				Person person=new Person();
				
				person.generateId();
				
				person.setAge(age);
				
				person.setName(name);
				
				person.setIdCard(idCard);
				
				person.setSex(sex);
				
				person.setPhone(phone);
				
				person.setRoles("admin");
				
				person.setInputDate(new Date());
				
				this.personService.insertPerson(person);
				
				resultMap.put("ret","insert");
		}else{
				params.clear();
				
				Person person=this.personService.findPersonById(id);
				
				person.setId(id);
				
				person.setAge(age);
				
				person.setName(name);
				
				person.setIdCard(idCard);
				
				person.setSex(sex);
				
				person.setPhone(phone);
				
				this.personService.updatePerson(person);
				
				resultMap.put("ret","update");
		}
		return resultMap;
	}
	
	/**
	 * 编辑修改员工信息
	 * @param HttpServletRequest
	 * @param ModelMap
	 * @return
	 * */
	@RequestMapping(value="f7Edit")
	public String personEdit(HttpServletRequest request,ModelMap model){
		
		String id=request.getParameter("id");
		Person person=this.personService.findPersonById(id);
//		System.err.println("person:"+person);
		model.put("person",person);
		
		return "admin/person/personEdit";
	}
	
	/**
	 * 删除员工信息
	 * @param HttpServletRequest
	 * @return 
	 * */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> personDelete(HttpServletRequest request){
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		String id=request.getParameter("id");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		Person person=this.personService.findPersonById(id);
		this.personService.deletePerson(person);
		
		List<Person> persons=this.personService.findPersonByList(params);
//		System.err.println("size:"+persons.size());
		if (persons.size()!=0) {
			result.put("ret","false");
		}else {
			result.put("ret","success");
		}
// 		System.err.println("result:"+result);
		return result;
	}

}
