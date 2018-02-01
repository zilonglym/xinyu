package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.local.LocalPlateRecord;


@MyBatisRepository
public interface LocalPlateRecordDao {
	
	void save(LocalPlateRecord  localPlateRecord);

	List<Map<String, Object>> findLocalPlateRecord(Map<String, Object> params);
	
	List<Map<String, Object>> findTop10LocalPlateWorkByUserId(Map<String, Object> params);
	
	
}
