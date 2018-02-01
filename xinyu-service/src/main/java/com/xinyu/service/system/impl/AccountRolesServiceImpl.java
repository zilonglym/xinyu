package com.xinyu.service.system.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import  org.springframework.beans.factory.annotation.Autowired;
import  com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.AccountRolesService;
import com.xinyu.dao.base.AccountRolesDao;
import com.xinyu.model.system.AccountRoles;

@Service("accountRolesServiceImpl")
public class AccountRolesServiceImpl extends BaseServiceImpl implements AccountRolesService {
	
	public static final Logger logger = Logger.getLogger(AccountRolesServiceImpl.class);
	
	@Autowired
	private AccountRolesDao accountRolesDao;
	
	@Override
	public void saveAccountRoles(AccountRoles roles){
		this.accountRolesDao.insertAccountRoles(roles);
	}
	
	@Override
	public void updateAccountRoles(AccountRoles roles){
		this.accountRolesDao.updateAccountRoles(roles);
	}
	
	@Override
	public AccountRoles getAccountRolesById(String id){
		return this.accountRolesDao.getAccountRolesById(id);
	}
	
	@Override
	public List<AccountRoles> getAccountRolesByList(Map<String, Object> params){
		return this.accountRolesDao.getAccountRolesByList(params);
	}

	@Override
	public List<Map<String, Object>> buildListData(List<AccountRoles> roles) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(AccountRoles role:roles){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", role.getId());
			map.put("name", role.getName());
			map.put("remark", role.getRemark());
			results.add(map);
		}
		return results;
	}

	
}
