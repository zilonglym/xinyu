package com.xinyu.controller.system;

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
import com.xinyu.model.base.User;
import com.xinyu.service.system.UserService;

/**
 * 商家用户操作类
 * */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	/**
	 * 已对接仓库商家列表
	 * @param HttpServletRequest
	 * @param ModelMap
	 * @return 
	 * */
	@RequestMapping(value="userList")
	public String UserList(HttpServletRequest request,ModelMap model){
		return "admin/user/userList";
	}
	
	/**
	 * 商家列表数据填充
	 * @param page
	 * @param rows
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Map<String, Object> UserListData(HttpServletRequest request,@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int rows){
		
		if (rows==10) {
			rows = 100;
		}
		
		Map<String, Object> params =  new HashMap<String, Object>();
		String searchText = request.getParameter("searchText");
		params.put("searchText", searchText);
		List<User> users = this.userService.getUserByPage(params,page,rows);
		int total = this.userService.getTotal(params);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", total);
		resultMap.put("page", page);
		resultMap.put("rows", this.userService.buildListData(users));
		
		return resultMap;
	}
	
	/**
	 * 新建商家用户信息
	 * @param HttpServletRequest
	 * @return string
	 * */
	@RequestMapping(value = "f7Add")
	public String AddUser(HttpServletRequest request){
		return "admin/user/userAdd";
	}
	
	/**
	 * 修改商家用户信息
	 * @param HttpServletRequest
	 * @return string
	 * */
	@RequestMapping(value = "f7Edit")
	public String EditUser(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		User user  = this.userService.getUserById(id);
		model.put("user", user);
		return "admin/user/userEdit";
	}
	
	/**
	 * 提交商家用户信息
	 * @param HttpServletRequest
	 * @return map
	 * */
	@RequestMapping(value="saveUser")
	@ResponseBody
	public Map<String, Object> saveUser(HttpServletRequest request) throws JSONException{
		
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params=new HashMap<String, Object>();
		
		String id = object.getString("id");
		String serviceCode = object.getString("serviceCode");
		String subscriberName = object.getString("subscriberName");
		String subscriberNick = object.getString("subscriberNick");
		String subscriberMobile = object.getString("subscriberMobile");
		String subscriberPhone = object.getString("subscriberPhone");
		String subscriberContactEmail = object.getString("subscriberContactEmail");
		String subscriberAddress = object.getString("subscriberAddress");
		String content = object.getString("content");
		String remark = object.getString("remark");
		String ownerCode = object.getString("ownerCode");
		
		if (id.isEmpty()) {
			params.put("subscriberName", subscriberName);
			params.put("subscriberNick", subscriberNick);
			List<User> users = this.userService.getUserBySearch(params);
			
			/**
			 * 判断商家用户名和昵称是否出现相同的
			 * 相同的话新建失败
			 * 不相同的话新建成功
			 * */
			if (users.size()>0) {
				resultMap.put("ret", "repeat");
			}else{
				
				User user =  new User();
				
				user.generateId();
				
				user.setServiceCode(serviceCode);
				
				user.setSubscriberName(subscriberName);
				
				user.setSubscriberNick(subscriberNick);
				
				user.setSubscriberMobile(subscriberMobile);
				
				user.setSubscriberPhone(subscriberPhone);
				
				user.setSubscriberContactEmail(subscriberContactEmail);
				
				user.setSubscriberAddress(subscriberAddress);
				
				user.setContent(content);
				
				user.setRemark(remark);
				
				user.setOwnerCode(ownerCode);
				
				this.userService.saveUser(user);
				
				resultMap.put("ret", "success");
			}
		}else {
			
			User user  = this.userService.getUserById(id);
			
			user.setServiceCode(serviceCode);
			
			user.setSubscriberName(subscriberName);
			
			user.setSubscriberNick(subscriberNick);
			
			user.setSubscriberMobile(subscriberMobile);
			
			user.setSubscriberPhone(subscriberPhone);
			
			user.setSubscriberContactEmail(subscriberContactEmail);
			
			user.setSubscriberAddress(subscriberAddress);
			
			user.setContent(content);
			
			user.setRemark(remark);
			
			user.setOwnerCode(ownerCode);
			
			this.userService.updateUser(user);
			
			resultMap.put("ret", "success");
		}
		return resultMap;
	}

}
