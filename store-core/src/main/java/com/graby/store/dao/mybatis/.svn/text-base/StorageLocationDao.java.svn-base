package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.StorageLocation;

@MyBatisRepository
public interface StorageLocationDao {

	void saveStorageLocation(StorageLocation storageLocation);

	List<StorageLocation> findeStorageLocationByList(Map<String, Object> params);

	int getTotal(Map<String, Object> params);

	void updateStorageLocationById(Long id);

	void delete(Long id);


}
