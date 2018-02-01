package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.StorageLocation;
import com.graby.store.service.store.StorageLocationService;

@RemotingService(serviceInterface = StorageLocationRemote.class, serviceUrl = "/location.call")
public class StorageLocationRemoteImpl implements StorageLocationRemote{
	
	@Autowired
	private StorageLocationService storageLocationService;

	@Override
	public Map<String, Object>  updateStorageLocationById(Long id) {
		return this.storageLocationService.updateStorageLocationById(id);
	}

	@Override
	public Map<String, Object>  saveStorageLocation(StorageLocation storageLocation) {
		return this.storageLocationService.saveStorageLocation(storageLocation);
	}

	@Override
	public List<Map<String, Object>> findeStorageLocationByList(Map<String, Object> params, int page, int rows) {
		return this.storageLocationService.findeStorageLocationByList(params, page, rows);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return this.storageLocationService.getTotal(params);
	}

	@Override
	public Map<String, Object>  delete(Long id) {
		return this.storageLocationService.delete(id);
	}
	
	
}
