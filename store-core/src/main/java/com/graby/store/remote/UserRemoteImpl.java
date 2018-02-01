package com.graby.store.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.AuditRules;
import com.graby.store.entity.User;
import com.graby.store.entity.UserRolesRow;
import com.graby.store.service.base.AuditRulesService;
import com.graby.store.service.base.UserMenuRolesService;
import com.graby.store.service.base.UserService;

@RemotingService(serviceInterface = UserRemote.class, serviceUrl = "/user.call")
public class UserRemoteImpl implements UserRemote {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserMenuRolesService userRolesService;
	
	@Autowired
	private UserMenuRolesService userMenuRolesService;
	
	@Autowired
	private AuditRulesService auditRulesService;
	
	@Override
	public void saveUserRolesRow(UserRolesRow userRolesRow) {
		 userMenuRolesService.save(userRolesRow);
	}
	

	@Override
	public List<User> findAll(String userId) {
		//查询权限内的商家数据
		//User user=(User) SecurityUtils.getSubject().getSession().getAttribute("admin_user_key");
		//System.err.println("UserRemoteImpl:"+user);
		if(userId==null || userId.length()==0){
			return userService.findAll();
		}
		List<UserRolesRow> rows=this.userRolesService.findRowsByUser(userId);
		if(rows==null || rows.size()==0){
			return userService.findAll();
		}else{
//			List<User> userList=new ArrayList<User>();
//			for(int i=0;i<rows.size();i++){
//				UserRolesRow row=rows.get(i);
//				User user=this.userService.getUser(row.getPerson().getId());
//				userList.add(user);
//			}
//			return userList;
			return userService.findAll();
		}
	}
	
	@Override
	public User  getUser(Long userId) {
		return userService.getUser(userId);
	}
	
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		this.userService.save(user);
	}

	@Override
	public void updateUserCentro(User user) {
		// TODO Auto-generated method stub
		this.userService.updateUserCentro(user);
	}


	@Override
	public List<AuditRules> findAuditRulesList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.auditRulesService.findAuditRulesList(param);
	}

	@Override
	public List<User> findUsers() {
		
		return userService.findUsers();
	}

}
