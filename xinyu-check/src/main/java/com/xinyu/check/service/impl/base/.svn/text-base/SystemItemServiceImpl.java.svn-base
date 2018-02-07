package com.xinyu.check.service.impl.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.check.dao.SystemItemDao;
import com.xinyu.check.model.SystemItem;
import com.xinyu.check.service.SystemItemService;

@Service("systemItemServiceImpl")
public class SystemItemServiceImpl extends BaseServiceImpl implements SystemItemService{

	@Autowired
	private SystemItemDao systemItemDao;
	
	/**
	 * 根据ID查找系统参数
	 * @param id
	 * @return SystemItem
	 * */
	@Override
	public SystemItem findSystemItemById(String id) {
		return this.systemItemDao.findSystemItemById(id);
	}

	/**
	 * 条件查询系统参数
	 * @param params
	 * @return list
	 * */
	@Override
	public List<SystemItem> findSystemItemByList(Map<String, Object> params) {
		return this.systemItemDao.findSystemItemByList(params);
	}

	/**
	 * 数据封装
	 * @param systemItems
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<SystemItem> systemItems) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(SystemItem systemItem:systemItems){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", systemItem.getId());
			map.put("description", systemItem.getDescription());
			map.put("type", systemItem.getType());
			map.put("value", systemItem.getValue());
			resultList.add(map);
		}
		return resultList;
	}

}
