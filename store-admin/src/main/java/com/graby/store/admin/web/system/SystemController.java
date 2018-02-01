package com.graby.store.admin.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.entity.AuditRules;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Person;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.entity.UserRolesRow;
import com.graby.store.entity.enums.StoreModelEnums;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.remote.AuditRulesRemote;
import com.graby.store.remote.AuthRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.util.Digests;
import com.graby.store.util.Encodes;

/**
 * 系统设置处理控制类
 * @author yangmin
 *
 */
@Controller
@RequestMapping(value="/")
public class SystemController {

	@Autowired
	private UserRemote userRemote;
	@Autowired
	private AuthRemote authRemote;
	@Autowired
	private AuditRulesRemote auditRemote;
	@Autowired
	private CentroRemote centroRemote;
	@Autowired
	private PersonRemote personRemote;
	
	/**
	 * 跳转用户权限设置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/sysUserRolesRow")
	public String sysUserRolesRow(HttpServletRequest request,ModelMap model){
		List<User> userList = userRemote.findAll(null);
		List<Person> personList=this.personRemote.findPersons();
		model.put("userList", userList);
		model.put("storeModeList", StoreModelEnums.values());
		model.put("personList", personList);
		return "system/userRolesRowEdit";
	}
	
	/**
	 * 跳转用户权限保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/sysUserRolesRowSave")
	public String sysUserRolesRowSave(HttpServletRequest request,ModelMap model){
		
		String person=request.getParameter("person");
		String user=request.getParameter("user");
		String storeMode=request.getParameter("storeMode");
		String remark=request.getParameter("remark");
		
		UserRolesRow  userRolesRow   = new UserRolesRow();
		Person  personObject  =new Person();
		personObject.setId(new Long(person));
		userRolesRow.setPerson(personObject);
		//userRolesRow.setStore(userRemote.getUser(Long.valueOf(user)));
		userRolesRow.setModelEnums(StoreModelEnums.valueOf(storeMode));
		userRolesRow.setRemark(remark);
		List<User> userList = userRemote.findAll(null);
		model.put("userList", userList);
		
		model.put("storeModeList", StoreModelEnums.values());
		return "system/userRolesRowEdit";
	}
	
	
	/**
	 * 用户列表设置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/centroList")
	public String centroList(HttpServletRequest request,ModelMap model){
		List<Centro> centroList=this.centroRemote.findCentros();
		model.put("centroList", centroList);
		return "system/centroList";
	}
	/**
	 * 转向至保存修改用户列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/toEditCenter")
	public String toAddUser(HttpServletRequest request,ModelMap model){
		String id=request.getParameter("id");
		if(id!=null && id.length()>0){
			Centro centro=this.centroRemote.getCentroById(Integer.valueOf(id));
			model.put("centro", centro);
		}
		return "system/centroEdit";
	}
	/**
	 * 提交用户修改与添加
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/submitCentro")
	public String submitUser(HttpServletRequest request,ModelMap model){
		String code=request.getParameter("code");
		String name=request.getParameter("name");
		String person=request.getParameter("person");
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		String id=request.getParameter("id");
		if(id!=null && id.length()>0){
			Centro centro=this.centroRemote.getCentroById(Integer.valueOf(id));
			centro.setName(name);
			centro.setCode(code);
			centro.setAddress(address);
			centro.setPerson(person);
			centro.setPhone(phone);
			this.centroRemote.updateCentro(centro);
		}else{
			Centro centro=new Centro();
			centro.setName(name);
			centro.setCode(code);
			centro.setAddress(address);
			centro.setPerson(person);
			centro.setPhone(phone);
			this.centroRemote.saveCentro(centro);
		}
		return "redirect:centroList";
	}
	@RequestMapping(value="sys/toCreateUser/{id}")
	public String toCreateUserPage(@PathVariable int id,HttpServletRequest request,ModelMap model){
		Centro centro=this.centroRemote.getCentroById(id);
		model.put("centro", centro);
		return "system/createUserByCentro";
	}
	/**
	 * 根据仓库生成一个管理帐号
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sys/createAccount/{id}")
	public String createManagerAccount(@PathVariable int id,HttpServletRequest request,ModelMap model){
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		Centro centro=this.centroRemote.getCentroById(id);
		User user=new User();
		user.setUsername(userName);
		user.setDescription("超级管理员");
		user.setRoles("admin");
		user.setShopName(centro.getName());
		user.setSalt(password);
		user.setCentroId(centro.getId().intValue());
		user.setPlainPassword(password);
		entryptPassword(user);
		this.userRemote.saveUser(user);
		centro.setAccount(userName);
		this.centroRemote.updateCentro(centro);
		return "redirect:centroList";
	}
	/**
	 * 转向至设置发货地页面
	 * @return
	 */
	@RequestMapping(value="sys/toCity/{id}")
	public String toSetCentroCityPage(@PathVariable int id,ModelMap model){
		Centro centro=this.centroRemote.getCentroById(id);
		model.put("centro", centro);
		return "system/centroCity";
	}
	
	@RequestMapping(value="sys/submitCentroCity/{id}")
	public String submitCentroCity(ModelMap model,HttpServletRequest request,@PathVariable int id){
		String cityStr=request.getParameter("cityStr");
		Centro centro=this.centroRemote.getCentroById(id);
		centro.setCityStr(cityStr);
		this.centroRemote.updateCentro(centro);
		return "redirect:../centroList";
	}
	/**
	 * 审单规则设置列表
	 * @return
	 */
	@RequestMapping(value="sys/auditRules/list")
	public String toAuditRulesList(ModelMap model){
		List<AuditRules> list=this.userRemote.findAuditRulesList(null);
		for(int i=0;list!=null && i<list.size();i++){
			AuditRules audit=list.get(i);
			User user=this.userRemote.getUser(audit.getUser().getId());
			audit.setUser(user);
		}
		model.put("list", list);
		
		return "system/auditRulesList";
	}
	/**
	 * 新增修改寓意规则列表
	 * @return
	 */
	@RequestMapping(value="sys/auditRules/edit")
	public String toAuditRuesEdit(HttpServletRequest request,ModelMap model){
		String id=request.getParameter("id");
		List<User> userList=this.userRemote.findAll(null);
		model.put("userList", userList);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.WAYBILL.getKey());
		List<SystemItem> list=this.authRemote.getSystemItemList(params);
		model.put("itemList", list);
		if(id!=null && id.trim().length()>0){
			AuditRules audit=this.auditRemote.findAuditRulesbyId(id);
			model.put("audit", audit);
		}
		return "system/auditRulesAdd";
	}
	@RequestMapping(value="sys/auditRules/isHave")
	@ResponseBody
	public Map<String,Object> isHaveAuditRules(HttpServletRequest request){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String id=request.getParameter("id");
		String compOrder=request.getParameter("comp");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("expressCompany", compOrder);
		params.put("userId", id);
		List<AuditRules> list=this.auditRemote.getAuditRulesList(params);
		if(list!=null && list.size()>0){
			resultMap.put("ret", 1);
		}else{
			resultMap.put("ret", 0);
		}
		return resultMap;
	}
	/**
	 * 提交保存审单规则
	 * @return
	 */
	@RequestMapping(value="sys/auditRules/submit")
	public String submitAuditRules(HttpServletRequest request,ModelMap model){
		String userId=request.getParameter("userId");
		String waybill=request.getParameter("waybill");
		String includes=request.getParameter("includes");
		String exincludes=request.getParameter("exincludes");
		String startWegiht=request.getParameter("startWegiht");
		String endWegiht=request.getParameter("endWegiht");
		String id=request.getParameter("id");
		AuditRules  audit=null;
		if(id==null || id.trim().length()==0){
			audit=new AuditRules();
			audit.setEndWegiht(Double.valueOf(endWegiht));
			audit.setStartWegiht(Double.valueOf(startWegiht));
			audit.setExincludes(exincludes);
			audit.setExpressCompany(waybill);
			audit.setIncludes(includes);
			User user=this.userRemote.getUser(Long.valueOf(userId));
			audit.setUser(user);
			this.auditRemote.save(audit);
		}else{
			audit=this.auditRemote.findAuditRulesbyId(id);
			audit.setEndWegiht(Double.valueOf(endWegiht));
			audit.setStartWegiht(Double.valueOf(startWegiht));
			audit.setExincludes(exincludes);
			audit.setExpressCompany(waybill);
			audit.setIncludes(includes);
			User user=this.userRemote.getUser(Long.valueOf(userId));
			audit.setUser(user);
			this.auditRemote.updateAuditRules(audit);
		}
		return toAuditRulesList(model);
	}
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(8);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, 1024);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
}
