package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.local.LocalOperateRecord;

@MyBatisRepository
public interface LocalOperateRecordDao {

	public List<LocalOperateRecord> findLocalOperateRecordList(Map<String, Object> params);
	
	public void save(LocalOperateRecord localOperateRecord);
	
	public int getTotal(Map<String, Object> params);
	
}
