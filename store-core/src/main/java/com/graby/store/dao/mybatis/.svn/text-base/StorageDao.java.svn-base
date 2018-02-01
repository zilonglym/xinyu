package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;

@MyBatisRepository
public interface StorageDao {

	public void save(Storage storage);
	
	public void saveItems(StorageItem item);
	
	public Storage getStorageById(Long id);
	
	public List<Storage> getStorages(Map<String,Object> params);
	
	public Long getStoragesCount(Map<String,Object> params);
	/**
	 * 单据明细id查询
	 * */
	public List<StorageItem> storageDetailbyId(Map<String, Object> params);
	
	public List<StorageItem> getStorageItemList(Map<String,Object> params);
	
}
