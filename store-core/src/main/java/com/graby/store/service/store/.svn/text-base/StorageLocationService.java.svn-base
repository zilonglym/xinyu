package com.graby.store.service.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.StorageLocationDao;
import com.graby.store.entity.StorageLocation;

@Component
public class StorageLocationService {

	@Autowired
	private StorageLocationDao storageLocationDao;
	
	/**
	 * 创建
	 * @param StorageLocation
	 * @return map
	 * */
	public Map<String, Object> saveStorageLocation(StorageLocation storageLocation) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			this.storageLocationDao.saveStorageLocation(storageLocation);
			retMap.put("code", "200");
			retMap.put("msg", "操作成功！！");
		}catch(Exception e){
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
			e.printStackTrace();
		}	
		return retMap;
	}
	
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @param rows
	 * @return map
	 * */
	public List<Map<String, Object>> findeStorageLocationByList(Map<String, Object> params, int page, int rows) {
		
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start", start);
		params.put("offset",offset);
		List<StorageLocation> locations = this.storageLocationDao.findeStorageLocationByList(params);
		List<Map<String, Object>> resultList = this.buildListData(locations);
		return resultList;
	}

	/**
	 * 封装数据
	 * @param list
	 * @return list
	 * */
	private List<Map<String, Object>> buildListData(List<StorageLocation> locations) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(StorageLocation location:locations){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", String.valueOf(location.getId()));
			map.put("HJ", location.getHJ());
			map.put("WZ", location.getWZ());
			map.put("BW", location.getBW());
			map.put("CS", location.getCS());
			map.put("name", location.getName()!=null?location.getName():"");
			map.put("shop", location.getShop()!=null?location.getShop():"");
			map.put("type", location.getType()!=null?location.getType():"");
			map.put("num", String.valueOf(location.getNum()));
			Date lastUpdate = location.getLastUpdate();
			map.put("lastDate", lastUpdate!=null?sf.format(lastUpdate):"");
			list.add(map);
		}
		return list;
	}

	public int getTotal(Map<String, Object> params) {
		return this.storageLocationDao.getTotal(params);
	}

	/**
	 * 更新
	 * @param id
	 * @return map
	 * */
	public Map<String, Object> updateStorageLocationById(Long id) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			this.storageLocationDao.updateStorageLocationById(id);
			retMap.put("code", "200");
			retMap.put("msg", "操作成功！！");
		}catch(Exception e){
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
			e.printStackTrace();
		}	
		return retMap;	
	}

	/**
	 * 删除
	 * @param id
	 * @return map
	 * */
	public Map<String, Object> delete(Long id) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			this.storageLocationDao.delete(id);
			retMap.put("code", "200");
			retMap.put("msg", "操作成功！！");
		}catch(Exception e){
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
			e.printStackTrace();
		}	
		return retMap;		
	}
	
}
