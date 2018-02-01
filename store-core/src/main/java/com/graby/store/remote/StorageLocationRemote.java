package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.StorageLocation;

/**
 * 库位操作远程操作接口
 * 
 * */
public interface StorageLocationRemote {
	
	/**
	 * 更新库位信息
	 * @param long
	 * */
	public Map<String, Object> updateStorageLocationById(Long id);
	
	/**
	 * 新建库位信息
	 * @param storageLocation
	 * */
	public Map<String, Object>  saveStorageLocation(StorageLocation storageLocation);
	
	/**
	 * 分页查询信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<Map<String, Object>> findeStorageLocationByList(Map<String, Object> params,int page,int rows);
	
	/**
	 * 统计总数
	 * @param map
	 * @return int
	 * */
	public int getTotal(Map<String, Object> params);

	
	/**
	 * 删除
	 * @param long
	 * */
	public Map<String, Object>  delete(Long id);

	
}
