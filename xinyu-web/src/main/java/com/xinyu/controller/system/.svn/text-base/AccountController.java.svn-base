package com.xinyu.controller.system;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountMenu;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.model.system.AccountRoleRelation;
import com.xinyu.model.system.AccountRoles;
import com.xinyu.model.system.AccountRolesRow;
import com.xinyu.model.system.Person;
import com.xinyu.model.system.enums.StoreMenuModelEnums;
import com.xinyu.model.system.enums.StoreModelEnums;
import com.xinyu.service.system.AccountMenuService;
import com.xinyu.service.system.AccountRelationService;
import com.xinyu.service.system.AccountRoleRelationService;
import com.xinyu.service.system.AccountRolesRowService;
import com.xinyu.service.system.AccountRolesService;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.PersonService;

import net.sf.json.JSONArray;

/**
 * 系统账号信息
 * */
@Controller
@RequestMapping(value = "account")
public class AccountController extends BaseController{
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private AccountRolesService accountRolesService;
	
	@Autowired
	private AccountMenuService accountMenuService;
	
	@Autowired
	private AccountRolesRowService accountRolesRowService;

	@Autowired
	private AccountRelationService accountRelationService;
	
	@Autowired
	private AccountRoleRelationService accountRoleRelationService;
	
	/**
	 * 系统账号信息列表页面
	 * @param HttpServletRequest
	 * @return String
	 * */
	@RequestMapping(value="acountList")
	public String AccountList(HttpServletRequest request){
		return "admin/account/accountList";
	}
	
	/**
	 *  系统账号信息列表数据填充
	 *  @param page
	 *  @param rows
	 *  @param request
	 *  @return
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> AccountListData(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int rows,HttpServletRequest request){
		
		if (rows==10) {
			rows=100;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		String searchText = request.getParameter("searchText");
		params.put("searchText", searchText);
		
		List<Account> accounts = this.accountService.findAccountsPage(params,page,rows);
		
		int total  = this.accountService.getTotal(params);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", this.accountService.buildListData(accounts));
		map.put("page", page);
		map.put("total", total);
		
		return map;
	}
	
	
	/**
	 * 修改账号信息（admin）页面
	 * @param request
	 * @return String
	 * */
	@RequestMapping(value="f7Edit")
	public String AccountEdit(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		Account account = this.accountService.findAcountById(id);
		model.put("account", account);
		return "admin/account/accountEdit";
	}
	
	/**
	 * 提交保存账号信息
	 * @param request
	 * @return map
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * */
	@RequestMapping(value="accountSave")
	@ResponseBody
	public Map<String, Object> AccountSave(HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);	
		
		String id = object.getString("id");
		String userName = object.getString("userName");
		String password = object.getString("password");
		String shortName = object.getString("shortName");
		String mobile = object.getString("mobile");
		String phone = object.getString("phone");
		String email = object.getString("email");
		
		params.clear();
		params.put("userName", userName);
		
		//查询相同userName的账号数量
		int total = this.accountService.getTotal(params);
		
		if (id.isEmpty()) {
			
			Account account = new Account();
			account.generateId();
			account.setUserName(userName);
			account.setPassword(super.EncoderByMd5(password));
			account.setShortName(shortName);
			account.setMobile(mobile);
			account.setPhone(phone);
			account.setEmail(email);
			account.setCreateTime(new Date());
			
			if (total>0) {
				
				resultMap.put("ret", "repeat");
				
			}else {
				
				this.accountService.insertAccout(account);
				resultMap.put("ret", "success");
				
				}
			}else {
				
				params.clear();
				params.put("id", id);
				
				Account account = this.accountService.findAcountById(id);
				account.setUserName(userName);
				account.setPassword(super.EncoderByMd5(password));
				account.setShortName(shortName);
				account.setMobile(mobile);
				account.setPhone(phone);
				account.setEmail(email);
				account.setCreateTime(new Date());
				this.accountService.updateAccount(account);
				
				resultMap.put("ret", "success");
			}
		System.err.println(resultMap);
		return resultMap;
	}
	
	/**
	 * 创建生成account帐号
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value="createAccount")
	@ResponseBody
	public Map<String,Object> createAccount() throws Exception{
		
		String personId=this.getString("id");
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		/**
		 * 判断当前人员有没有生成帐号
		 */
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("objectId", personId);
		List<AccountRelation> relationList=this.accountService.getAccountRelationByList(params);
		
		if(relationList!=null && relationList.size()>0){
//			
			AccountRelation relation=relationList.get(0);
			Account account=this.accountService.findAcountById(relation.getAccount().getId());
			resultMap.put("ret", 0);
			resultMap.put("msg", "此员工已有帐号，帐号名:"+account.getUserName());
			
		}else{
			/**
			 * 生成帐号
			 */
			int total=this.accountService.getTotal(null);
			Person person=this.personService.findPersonById(personId);
			
			Account account = new Account();
			account.generateId();
			account.setUserName("zc"+autoGenericCode(String.valueOf(total), 4));
			account.setPassword(super.EncoderByMd5(super.defaultPassword));
			account.setShortName(person.getName());
			account.setMobile(person.getPhone());
			account.setPhone(person.getPhone());
			account.setEmail("");
			account.setCreateTime(new Date());
			this.accountService.insertAccout(account);
			
			AccountRelation relation=new AccountRelation();
			relation.generateId();
			relation.setAccount(account);
			relation.setObject(person);
			relation.setType("person");
			this.accountService.insertAccountRelation(relation);
			
			resultMap.put("ret", 1);
			resultMap.put("msg", "帐号生成成功:帐号名:"+account.getUserName());
		}
		return resultMap;
	}
	
	/**
	 * 位数不足，前面补0
	 * @param code
	 * @param num
	 * @return
	 */
	 private String autoGenericCode(String code, int num) {
	        String result = "";
	        result = String.format("%0" + num + "d", Integer.parseInt(code) + 1);

	        return result;
	    }
	
		
	/**
	 * 账号角色类型列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="accountRolesList")
	public String AccountRolesList(ModelMap model){
		return "admin/account/accountRolesList";
	}
	
	/**
	 * 账号角色类型列表数据
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="accountRolesListData")
	@ResponseBody
	public Map<String, Object> AccountRolesListData(HttpServletRequest request){
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<AccountRoles> roles = this.accountRolesService.getAccountRolesByList(params);
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", this.accountRolesService.buildListData(roles));
		resultMap.put("total", roles.size());
		
		return resultMap;
	}
	
	/**
	 * 新建账号角色信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7AddRoles")
	public String AccountRolesAdd(ModelMap model){
		return "admin/account/rolesAdd";
	}
	
	/**
	 * 修改账号角色信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7EditRoles")
	public String AccountRolesEdit(ModelMap model,HttpServletRequest request){
		
		String id = request.getParameter("id");
		AccountRoles accountRoles = this.accountRolesService.getAccountRolesById(id);
		
		model.put("roles", accountRoles);
		
		model.put("menus", this.accountMenuService.buildRowsMenusListData(id));
		
		return "admin/account/rolesEdit";
	}
	
	/**
	 * 保存账号角色信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="saveRoles")
	@ResponseBody
	public Map<String, Object> AccountRolesSave(ModelMap model,HttpServletRequest request){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String json = request.getParameter("json");
		JSONObject object=new JSONObject(json);	
		String name = object.getString("name");
		String remark = object.getString("remark");
		String id = object.getString("id");
 		AccountRoles roles = this.accountRolesService.getAccountRolesById(id);
 		
 		/**
 		 * 判断name是否已存在
 		 * 修改信息size小于2修改
 		 * 新增信息size小于1新建
 		 * */
 		Map<String, Object> params = new HashMap<String, Object>();
 		params.put("name", name);
 		List<AccountRoles> rolesList = this.accountRolesService.getAccountRolesByList(params);
 		
 		if (roles!=null) {
 			
 			roles.setName(name);
  			roles.setRemark(remark);
  			
 			if (rolesList.size()>1) {
 				
 				resultMap.put("ret", "repeat");
 				
 			}else {
 				
 				this.accountRolesService.updateAccountRoles(roles);
 				
 				resultMap.put("ret", "update");
 				
 			}			
 		}else {
 			
 			AccountRoles accountRoles = new AccountRoles();
 			accountRoles.generateId();
 			accountRoles.setName(name);
  			accountRoles.setRemark(remark);
// 			accountRoles.setCu("");
 			
 			if (rolesList.size()>0) {
 				
 				resultMap.put("ret", "repeat");
 				
 			}else {
 				
 				this.accountRolesService.saveAccountRoles(accountRoles);
 				
 				resultMap.put("ret", "insert");
 			}	
 		}		
		return resultMap;
	}

	/**
	 * 设置角色权限
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7AddRolesMenu")
	public String AddRolesMenu(HttpServletRequest request,ModelMap model){
		
		String id = request.getParameter("id");
		AccountRoles roles = this.accountRolesService.getAccountRolesById(id);
		model.put("roles", roles);
		
		List<AccountMenu> menus = this.accountMenuService.getAccountMenuByList(null);
		model.put("menus", this.accountMenuService.buildListData(menus));
		
		return "admin/account/addRolesMenu";
	}

	/**
	 * 保存角色权限
	 * @param request
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="saveRolesMenu")
	@ResponseBody
	public Map<String, Object> saveRolesMenu(HttpServletRequest request,ModelMap model){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String ids = request.getParameter("ids");
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		String name = request.getParameter("name");
		String[] ary = ids.split(",");
		
		AccountRoles roles = this.accountRolesService.getAccountRolesById(id);
		roles.setName(name);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roles", id);
		List<AccountRolesRow> rows = this.accountRolesRowService.getAccountRolesRowByList(params);
		this.accountRolesService.updateAccountRoles(roles);
		
		if (rows.size()>0) {
			
			this.accountRolesRowService.updateRows(ary, roles,remark);
			
			retMap.put("ret", "success");
			
		}else {
			
			this.accountRolesRowService.insertRows(ary, roles,remark);
			
			retMap.put("ret", "success");
			
		}
		return retMap;
	}
	
	/**
	 * 设置账号角色关联信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7EditRlationRoles")
	public String RelationRolesAdd(ModelMap model,HttpServletRequest request){
		
		String objectId = request.getParameter("id");
		AccountRelation accountRelation = this.accountRelationService.findAccountRlationByPersonId(objectId);
		Account account = this.accountService.findAcountById(accountRelation.getAccount().getId());
		model.put("account", account);
		
		List<AccountRoles> roles= this.accountRolesService.getAccountRolesByList(null);
		model.put("roles", roles);
		
		return "admin/account/relationRolesAdd";
	}
	
	/**
	 * 保存账号角色关联信息
	 * @param model
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="saveRelationRoles")
	@ResponseBody
	public Map<String, Object> saveRelationRoles(ModelMap model,HttpServletRequest request){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String json = request.getParameter("json");
		JSONObject object = new JSONObject(json);
		String accountId = object.getString("accountId");
		String roleId = object.getString("roleId");
		AccountRoleRelation roleRlation = this.accountRoleRelationService.findAccountRoleRelationByAccountId(accountId);
		
		if (roleRlation!=null) {
			
			AccountRoles roles = this.accountRolesService.getAccountRolesById(roleId);	
			roleRlation.setRole(roles);
			
			this.accountRoleRelationService.updateAccountRoleRelation(roleRlation);
			
			retMap.put("ret", "update");
			
		}else {
			
			AccountRoleRelation relation = new AccountRoleRelation();
			
			relation.generateId();
			
//			relation.setCu("");
			
			AccountRoles roles = this.accountRolesService.getAccountRolesById(roleId);
			relation.setRole(roles);
			
			Account account = this.accountService.findAcountById(accountId);
			relation.setAccount(account);
			
			this.accountRoleRelationService.saveAccountRoleRelation(relation);
			
			retMap.put("ret", "insert");
		}
		return retMap;
	}
	
	/**
	 * 菜单列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="accountMenuList")
	public String AccountMenuList(ModelMap model){
		return "admin/account/accountMenuList";
	}
	
	/**
	 * 账号角色类型列表数据
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="accountMenuListData")
	@ResponseBody
	public Map<String, Object> AccountMenuListData(HttpServletRequest request){
		
		Map<String, Object> params = new HashMap<String, Object>();
		String title = request.getParameter("title");
		params.put("title", title);
		List<AccountMenu> menus = this.accountMenuService.getAccountMenuByList(params);
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", this.accountMenuService.buildListData(menus));
		resultMap.put("total", menus.size());
		
		return resultMap;
	}
	
	/**
	 * 新建菜单信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7AddMenu")
	public String AccountMenuAdd(ModelMap model){
		model.put("models", StoreMenuModelEnums.values());
		return "admin/account/menuAdd";
	}
	
	/**
	 * 修改菜单信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7EditMenu")
	public String AccountMenuEdit(ModelMap model,HttpServletRequest request){
		
		String id = request.getParameter("id");
		AccountMenu accountMenu = this.accountMenuService.getAccountMenuById(id);
		model.put("menu", accountMenu);
		
		model.put("models", StoreMenuModelEnums.values());
		
		return "admin/account/menuEdit";
	}
	
	/**
	 * 保存菜单信息
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="saveMenu")
	@ResponseBody
	public Map<String, Object> AccountMenuSave(ModelMap model,HttpServletRequest request){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String json = request.getParameter("json");
		JSONObject object=new JSONObject(json);	
		String id = object.getString("id");
		String title = object.getString("title");
		String link = object.getString("link");
		String menus = object.getString("menus");
		AccountMenu menu = this.accountMenuService.getAccountMenuById(id);
		
		/**
		 * 判断name是否已存在
		 * 修改信息size小于2修改
		 * 新增信息size小于1新建
		 * */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("link", link);
		params.put("menus", menus);
		List<AccountMenu> menuList = this.accountMenuService.getAccountMenuByList(params);
		
		//菜单不存在
		if (menu!=null) {
			if (menuList.size()>1) {
				resultMap.put("ret", "repeat");
			}else {
				
				menu.setTitle(title);
				
				menu.setLink(link);
				
				StoreMenuModelEnums[] list = StoreMenuModelEnums.values();
				for(int i=0;i<list.length;i++){
					if (list[i].getKey().equals(menus)) {
						menu.setMenus(list[i]);
					}
				}
				
				this.accountMenuService.updateAccountMenu(menu);
				
				resultMap.put("ret", "update");
			}			
		}else {
			//菜单存在
			if (menuList.size()>0) {
				resultMap.put("ret", "repeat");
			}else {
				
				AccountMenu accountMenu = new AccountMenu();
				
				accountMenu.generateId();
				
//				accountMenu.setCu("");
				
				accountMenu.setTitle(title);
				
				accountMenu.setLink(link);
				
				StoreMenuModelEnums[] list = StoreMenuModelEnums.values();
				for(int i=0;i<list.length;i++){
					if (list[i].getKey().equals(menus)) {
						accountMenu.setMenus(list[i]);
					}
				}
				
				this.accountMenuService.saveAccountMenu(accountMenu);
				
				resultMap.put("ret", "insert");
			}	
		}		
		return resultMap;
	}
	
	/**
	 * 账号权限信息列表
	 * @return string
	 * */
	@RequestMapping(value="accountRolesRowList")
	public String AccountRolesRowList(){
		return "admin/account/accountRolesRowList";
	}
	
	/**
	 * 账号权限信息列表数据
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="accountRolesRowListData")
	@ResponseBody
	public Map<String, Object> AccountRolesRowListData(HttpServletRequest request){
		
		String searchText = request.getParameter("searchText");
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("searchText", searchText);
		List<AccountRolesRow> rows = this.accountRolesRowService.getAccountRolesRowByList(params);
		
		Map<String, Object> retMap = new HashMap<String,Object>();
		retMap.put("rows", this.accountRolesRowService.buildListData(rows));
		retMap.put("total", rows.size());
		return retMap;
	}
	
	/**
	 * 修改账号权限信息
	 * @param request
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7RowEdit")
	public String AccountRolesRowEdit(HttpServletRequest request,ModelMap model){
		
		List<Account> accounts = this.accountService.findAccountsByList(null);
		model.put("accounts", accounts);
		
		String id = request.getParameter("id");
		AccountRolesRow row = this.accountRolesRowService.getAccountRolesRowById(id);
		AccountMenu menu = this.accountMenuService.getAccountMenuById(row.getMenu().getId());
		row.setMenu(menu);
		model.put("row", row);
		
		List<AccountRoles> roles = this.accountRolesService.getAccountRolesByList(null);
		model.put("roles", roles);
		
		model.put("models", StoreMenuModelEnums.values());
		
		return "admin/account/rowEdit";
	}
	
	
	/**
	 * 保存账号权限信息
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="saveRow")
	@ResponseBody
	public Map<String, Object> AccountRolesRowEditSave(HttpServletRequest request){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String json = request.getParameter("json");
		JSONObject object=new JSONObject(json);	
		String id = object.getString("id");
		String accountId = object.getString("account");
		String rolesId = object.getString("roles");
		String menuId = object.getString("menu");
		String remark = object.getString("remark");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", accountId);
		List<AccountRolesRow> rows = this.accountRolesRowService.getAccountRolesRowByList(params);	
		
		AccountRolesRow row = this.accountRolesRowService.getAccountRolesRowById(id);
		
		if (row!=null) {
			if (rows.size()>1) {
				resultMap.put("ret", "repeat");
			}else {
				
				row.setRemark(remark);
				
				AccountRoles roles = this.accountRolesService.getAccountRolesById(rolesId);
				row.setRoles(roles);
				
				AccountMenu menu = this.accountMenuService.getAccountMenuById(menuId);
				row.setMenu(menu);
				
				this.accountRolesRowService.updateAccountRolesRow(row);
				
				resultMap.put("ret", "update");
			}
		}else{
			if (rows.size()>1) {
				resultMap.put("ret", "repeat");
			}else {
				
				AccountRolesRow rolesRow = new AccountRolesRow();
				
				rolesRow.generateId();
				
				rolesRow.setRemark(remark);
				
				AccountRoles roles = this.accountRolesService.getAccountRolesById(rolesId);
				rolesRow.setRoles(roles);
				
				AccountMenu menu = this.accountMenuService.getAccountMenuById(menuId);
				row.setMenu(menu);
				
				this.accountRolesRowService.saveAccountRolesRow(rolesRow);
				
				resultMap.put("ret", "insert");
			}
		}
		return resultMap;
	}
	
	/**
	 * 当前登录用户修改信息页面
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="editAccount")
	public String AccountEdit(ModelMap model){
		return "admin/account/account";
	}
	
	/**
	 * 当前登录用户密码修改页面
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="editPassword")
	public String AccountPasswordEdit(ModelMap model){
		/**
		 * 获取当前账号信息
		 * */
		Account account = SessionUser.get();
		model.put("account", account);
		return "admin/account/editPassword";
	}
	
	/**
	 * 新密码更新保存
	 * @param request
	 * @return string
	 * */
	@RequestMapping(value="savePassword")
	public String AccountPasswordSave(HttpServletRequest request){
		
		String password = request.getParameter("password");
		
		String id = request.getParameter("id");
		Account account = this.accountService.findAcountById(id);
		
		try {
			
			account.setPassword(EncoderByMd5(password));
			
			this.accountService.updateAccount(account);	
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/account/account";
	}
}
