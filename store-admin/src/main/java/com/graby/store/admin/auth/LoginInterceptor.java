package com.graby.store.admin.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.graby.store.admin.auth.ShiroDbRealm.ShiroUser;
import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Person;
import com.graby.store.entity.UserMenu;
import com.graby.store.entity.UserRolesRow;
import com.graby.store.remote.AuthRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.PersonRemote;

public class LoginInterceptor extends HandlerInterceptorAdapter   {
	@Autowired
	private PersonRemote personRemote;
	@Autowired
	private AuthRemote authRemote;
	@Autowired
	private CentroRemote centroRemote;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		ShiroUser obj=(ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Person person=(Person) SecurityUtils.getSubject().getSession().getAttribute("admin_user_key");
		if(person==null && obj!=null){
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("userName", String.valueOf(obj));
			 person=this.personRemote.searchPerson(params).get(0);
			if(person.getCentro()!=null){
				Centro centro=this.centroRemote.getCentroById(person.getCentro().getId().intValue());
				person.setCentro(centro);
			}
			SecurityUtils.getSubject().getSession().setAttribute(BaseResource.ADMIN_USER_KEY, person);
			
			
		}
		if(person!=null){
			List<UserMenu> list=this.authRemote.getUserMenuByUser(String.valueOf(person.getId()));
			List<UserRolesRow> rows=this.authRemote.getUserRowByUser(String.valueOf(person.getId()));
			if(rows!=null && rows.size()>0){
				request.getSession().setAttribute("rows", rows);
			}
			request.getSession().setAttribute("menuList", list);
		}
		return super.preHandle(request, response, handler);
	}

}
