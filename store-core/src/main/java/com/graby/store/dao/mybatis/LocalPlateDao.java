package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.local.LocalPlate;


@MyBatisRepository
public interface LocalPlateDao {
	
	void save(LocalPlate  localPlate);
	
	List<LocalPlate> findLocalPlateByBoxOutId(int boxOutId);
	
	LocalPlate findLocalPlate(int id);
	
	int downLocalPlate(int  id);
	
	int updateLocalPlate(Map<String,Object> params);
	
	List<LocalPlate> findLocalPlateByItemId(int itemId);
	

	List<Map<String, Object>> findLocalPlateList(Map<String, Object> params);

	int getTotal(Map<String, Object> params);

	List<Map<String, Object>> findLocalReport(Map<String, Object> params);

	LocalPlate findLocalPlateByFastCode(String fastCode);
	
	LocalPlate findLocalPlateByBatchId(String batchId);

	List<Map<String, Object>> findItemReport(Map<String, Object> p);

	List<Map<String, Object>> findPCLocalPlate(Map<String, Object> params);
}
