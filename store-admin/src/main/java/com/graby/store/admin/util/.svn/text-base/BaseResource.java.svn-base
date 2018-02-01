package com.graby.store.admin.util;

import org.apache.shiro.SecurityUtils;

import com.graby.store.entity.Person;
import com.graby.store.entity.User;
/**
 * 资源调用公共类
 * @author yangmin
 *
 */
public class BaseResource {
	
	public static final String ADMIN_USER_KEY="admin_user_key";
	
	public static final String ADMIN_CENTRO_KEY="admin_centro_key";
	
	/**
	 * 取当前的仓库
	 * @return
	 */
	public static int getCurrentCentroId(){
		String centro=(String)SecurityUtils.getSubject().getSession().getAttribute(ADMIN_CENTRO_KEY);
		if(centro==null || centro.trim().length()==0){
			User user=getCurrentUser();
			if(user!=null && user.getCentro()!=null){
				return user.getCentro().getId().intValue();
			}else{
				return 1;
			}
		}else{
			return Integer.valueOf(centro);
		}
	}
	

	/**
	 * 取当前登陆用户
	 * @return
	 */
	public static User getCurrentUser(){
		Person person=(Person) SecurityUtils.getSubject().getSession().getAttribute(ADMIN_USER_KEY);
		User user=new User();
		user.setCentro(person.getCentro());
		user.setId(person.getId());
		user.setUsername(person.getUserName());
		return user;
	}
}
