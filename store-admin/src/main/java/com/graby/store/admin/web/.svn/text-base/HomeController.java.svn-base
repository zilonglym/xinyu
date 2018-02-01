package com.graby.store.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.admin.auth.ShiroDbRealm.ShiroUser;
import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Person;
import com.graby.store.entity.User;
import com.graby.store.entity.UserMenu;
import com.graby.store.entity.UserRolesRow;
import com.graby.store.remote.AuthRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.remote.UserRemote;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	@Autowired
	protected AuthRemote authRemote;
	@Autowired
	protected CentroRemote centroRemote;
	@Autowired
	protected PersonRemote personRemote;
	@Autowired
	protected TradeRemote tradeRemote;
	@Autowired
	protected UserRemote userRemote;
	
	/**
	 * 后台入口首页
	 * @param request
	 * @param response
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "")
	public String auth(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws ApiException {
		ShiroUser obj=(ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userName", String.valueOf(obj));
		Person person=this.personRemote.searchPerson(params).get(0);
		if(person.getCentro()!=null){
			Centro centro=this.centroRemote.getCentroById(person.getCentro().getId().intValue());
			person.setCentro(centro);
		}
		SecurityUtils.getSubject().getSession().setAttribute(BaseResource.ADMIN_USER_KEY, person);
		/**
		 * 查询菜单 权限，写入缓存
		 */
		List<UserMenu> list=this.authRemote.getUserMenuByUser(String.valueOf(person.getId()));
		List<UserRolesRow> rows=this.authRemote.getUserRowByUser(String.valueOf(person.getId()));
		if(rows!=null && rows.size()>0){
			request.getSession().setAttribute("rows", rows);
		}
		request.getSession().setAttribute("menuList", list);
		
		/**
		 * 提示未审核单据信息
		 * */
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> result=this.tradeRemote.getWaitAuditTradesTotal();
		for(Map<String,Object> objMap:result){
			Map<String,Object> map=new HashMap<String, Object>();
			Long id=(Long) objMap.get("userId");
			User user=this.userRemote.getUser(id);
			map.put("userName",user.getShopName());
			map.put("num",objMap.get("num"));
			map.put("userId", id);
			resultList.add(map);
		}
		model.addAttribute("resultList", resultList);
		return "welcome";
	}
	/**
	 * 初始化所有的系统变量
	 * @return
	 */
	@RequestMapping(value="init")
	public Map<String,Object> initSystemItem(){
		this.tradeRemote.initSystemItem();//初始化审单数据
		return null;
	}
}
