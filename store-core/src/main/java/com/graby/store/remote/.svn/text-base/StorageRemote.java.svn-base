package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;

/**
 * 入库服务
 * serviceUrl = "/storage.call"
 */
public interface StorageRemote {

	public void save(Storage storage);
	
	public void saveStorageAndAddInventory(Storage storage);
	
	public Storage getStorageById(Long id);
	
	public Long findStoragesCount(Map<String, Object> params);
	
	public List<Storage> getStorageList(Map<String,Object> params);
	
	public List<StorageItem> getStorageItemList(Map<String,Object> params);
	/**
	 * 单据明细id查询
	 * */
	public List<StorageItem> storageDetailbyId(Map<String, Object> params);
	
	public Page<Storage> findStoragesList(int pageNo, int pageSize, Map<String, Object> params);
	
}
