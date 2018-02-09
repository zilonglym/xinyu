package com.xinyu.check.service;

import java.util.List;
import java.util.Map;

import com.xinyu.check.model.SystemItem;

public interface SystemItemService extends BaseService{

	/**
	 * 根据ID查找系统参数
	 * @param id
	 * @return SystemItem
	 * */
	public SystemItem findSystemItemById(String id);
	
	
	/**
	 * 条件查询系统参数
	 * @param params
	 * @return list
	 * */
	public List<SystemItem> findSystemItemByList(Map<String,Object> params);

	

	/**
	 * 数据封装
	 * @param systemItems
	 * @return list
	 * */
	public List<Map<String, Object>> buildListData(List<SystemItem> systemItems);
}
