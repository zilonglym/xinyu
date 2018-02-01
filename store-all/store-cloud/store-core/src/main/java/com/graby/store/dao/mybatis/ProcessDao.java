package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;
import com.graby.store.entity.Process;
import com.graby.store.base.MyBatisRepository;

@MyBatisRepository
public interface ProcessDao {
	
	void insert(Process process);
	
	List<Process> selectByExample(Map<String,Object> params);
	
	Process selectById(int id);

}
