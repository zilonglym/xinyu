package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;
import com.graby.store.entity.StoreProcess;
import com.graby.store.base.MyBatisRepository;

@MyBatisRepository
public interface ProcessDao {
	
	void insert(StoreProcess process);
	
	List<StoreProcess> selectByExample(Map<String,Object> params);
	
	StoreProcess selectById(Integer id);
	
	List<StoreProcess> findProcessConfirm();
	

}
