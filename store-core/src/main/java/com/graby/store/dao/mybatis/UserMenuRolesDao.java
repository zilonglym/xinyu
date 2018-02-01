package com.graby.store.dao.mybatis;

import java.util.List;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.User;
import com.graby.store.entity.UserMenu;
import com.graby.store.entity.UserRolesRow;

@MyBatisRepository
public interface UserMenuRolesDao {

	List<UserMenu> findMenuByUser(String userId);
	
	List<UserRolesRow> findRowsByUser(String userId);
	
	void save(UserRolesRow  userRolesRow);
	
}
