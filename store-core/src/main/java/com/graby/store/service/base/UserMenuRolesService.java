package com.graby.store.service.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.UserMenuRolesDao;
import com.graby.store.entity.UserMenu;
import com.graby.store.entity.UserRolesRow;
import com.graby.store.util.Digests;
import com.graby.store.util.Encodes;
import com.graby.store.util.EncryptUtil;
import com.graby.store.web.auth.ShiroDbRealm.ShiroUser;

@Component
@Transactional
public class UserMenuRolesService {
	
	private static Logger logger = LoggerFactory.getLogger(UserMenuRolesService.class);
	
	
	@Autowired
	private UserMenuRolesDao userMenuDao;
	
	/**
	 * 根据用户ID查询出他所拥有的权限
	 * @param userId
	 * @return
	 */
	public List<UserMenu> findMenuByUser(String userId){
		List<UserMenu> list=this.userMenuDao.findMenuByUser(userId);
		return buildList(list);
	}
	/**
	 * 查询用户的数据权限，最初是查询打单 员所操作的商家
	 * @param userId
	 * @return
	 */
	public List<UserRolesRow> findRowsByUser(String userId){
		return this.userMenuDao.findRowsByUser(userId);
	}
	
	
	
	public void save(UserRolesRow userRolesRow){
		userMenuDao.save(userRolesRow);
	}
	
	/**
	 * 去除重复菜单项
	 * @param list
	 * @return
	 */
	private List<UserMenu> buildList(List<UserMenu> list){
		String tempStr="";
		List<UserMenu> retList=new ArrayList<UserMenu>();
		for(int i=0,j=list.size();i<j;i++){
			UserMenu menu=list.get(i);
			if(tempStr.indexOf(menu.getTitle())==-1){
				retList.add(menu);
				tempStr+=menu.getTitle()+",";
			}
			
		}
		return retList;
	}
	
}
