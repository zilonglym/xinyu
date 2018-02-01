package com.graby.store.admin.web.system;

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

import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Person;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.util.Digests;
import com.graby.store.util.Encodes;

/**
 * 员工操作
 * */
@Controller
@RequestMapping(value="person")
public class PersonController extends BaseController{
	
	@Autowired
	private PersonRemote personRemote;
	@Autowired
	private CentroRemote centroRemote;
	
	/**
	 * 员工列表
	 * */
	@RequestMapping(value="/personList")
	public String PersonList(HttpServletRequest request,ModelMap model){
		return "system/personList";
	}
	
	/**
	 * 员工列表数据加载
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		Map<String,Object> params=new HashMap<String,Object>();
		String name=request.getParameter("name");
 		params.put("name",name);
		List<Person> personList=this.personRemote.searchPerson(params);
		List<Person> persons=this.personRemote.getPersons(params,page,rows);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("rows",persons);
		result.put("page",page);
		result.put("total",personList.size());
		return result;
	}
	
	/**
	 * 添加员工
	 * */
	@RequestMapping(value="f7Add")
	public String personAdd(ModelMap model){
		List<Centro> centroList=this.centroRemote.findCentros();
		model.put("centroList",centroList);
//		System.out.println(model);
		return "system/personAdd";
	}
	
	/**
	 * 提交保存员工信息
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String,Object> personSave(HttpServletRequest request) throws JSONException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
//		System.err.println("object:"+object);
		String age=object.getString("age");	
		String name=object.getString("name");
		String sex=object.getString("sex");
		String phone=object.getString("phone");
		String centroId=object.getString("centro");
//		System.err.println("name:"+name);
		String id=object.getString("id");
		String idCard=object.getString("idCard");
		
		Person person=new Person();
		params.put("idCard",idCard);
		List<Person> persons=this.personRemote.searchPerson(params);
		if (persons.size()!=0&&id.isEmpty()) {
			resultMap.put("card","false");
		}else if(persons.size()==0&&id.isEmpty()){
				person.setAge(age);
				person.setName(name);
				person.setIdCard(idCard);
				person.setSex(sex);
				person.setPhone(phone);
				Centro centro=this.centroRemote.findCentroById(centroId);
				person.setCentro(centro);
				person.setCentroId(centro.getId());
				person.setPassword("00000000");
				entryptPassword(person);
				person.setRoles("admin");
				person.setInputDate(new Date());
				person.setUserName(idCard);
				this.personRemote.savePerson(person);
				resultMap.put("ret","insert");
		}else{
				String password=object.getString("password");
				params.clear();
				params.put("id",id);
				person=this.personRemote.findPersonByQ(params);
				person.setPassword(password);
				entryptPassword(person);
				person.setAge(age);
				person.setName(name);
				person.setIdCard(idCard);
				person.setSex(sex);
				person.setPhone(phone);
				Centro centro=this.centroRemote.findCentroById(centroId);
				person.setCentro(centro);
				person.setCentroId(centro.getId());
				this.personRemote.updatePerson(person);
				resultMap.put("ret","update");
		}
		return resultMap;
	}
	
	/**
	 * 编辑修改员工信息
	 * */
	@RequestMapping(value="f7Edit")
	public String personEdit(HttpServletRequest request,ModelMap model){
		Map<String,Object> params=new HashMap<String,Object>();
		String id=request.getParameter("id");
//		System.err.println("id:"+id);
		params.put("id",id);
		Person person=this.personRemote.findPersonByQ(params);
		model.put("person",person);
		List<Centro> centroList=this.centroRemote.findCentros();
		model.put("centroList",centroList);
		return "system/personEdit";
	}
	
	/**
	 * 删除员工信息
	 * */
	@RequestMapping(value="delete")
	public Map<String, Object> personDelete(HttpServletRequest request){
		Map<String, Object> result=new HashMap<String, Object>();
		String id=request.getParameter("id");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		Person person=this.personRemote.findPersonByQ(params);
		this.personRemote.delete(person);
		List<Person> persons=this.personRemote.searchPerson(params);
//		System.err.println("size:"+persons.size());
		if (persons.size()!=0) {
			result.put("ret","false");
		}else {
			result.put("ret","success");
		}
//		System.err.println("result:"+result);
		return result;
	}
	

	
	
}
