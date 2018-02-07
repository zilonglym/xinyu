package com.xinyu.check.dao;

import java.util.List;
import java.util.Map;

import com.xinyu.check.dao.base.BaseDao;
import com.xinyu.check.model.SystemItem;

public interface SystemItemDao extends BaseDao{
	
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
	 * 新建系统参数
	 * @param systemItem
	 * */
	public void insertSystemItem(SystemItem systemItem);

	/**
	 * 修改系统参数
	 * @param systemItem
	 * */
	public void updateSysItem(SystemItem systemItem);

	/**
	 * 根据ID删除系统参数
	 * @param id
	 * */
	public void deleteById(String id);
}
