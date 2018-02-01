package com.xinyu.check.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.check.dao.SystemItemDao;
import com.xinyu.check.dao.base.BaseDaoImpl;
import com.xinyu.check.model.SystemItem;

@Repository("systemItemDaoImpl")
public class SystemItemDaoImpl extends BaseDaoImpl implements SystemItemDao{

	private final String statement = "com.xinyu.check.dao.SystemItemDao.";
	
	/**
	 * 根据ID查找系统参数
	 * @param id
	 * @return SystemItem
	 * */
	@Override
	public SystemItem findSystemItemById(String id) {
		return (SystemItem) this.selectOne(this.statement+"findSystemItemById", id);
	}

	/**
	 * 条件查询系统参数
	 * @param params
	 * @return list
	 * */
	@Override
	public List<SystemItem> findSystemItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<SystemItem>) this.selectList("com.xinyu.check.dao.SystemItemDao.findSystemItemByList", params);
	}

	/**
	 * 新建系统参数
	 * @param systemItem
	 * */
	@Override
	public void insertSystemItem(SystemItem systemItem) {
		this.insert(this.statement+"insertSystemItem", systemItem);
	}

	/**
	 * 修改系统参数
	 * @param systemItem
	 * */
	@Override
	public void updateSysItem(SystemItem systemItem) {
		try {
			this.update(this.statement+"updateSysItem", systemItem);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 根据ID删除系统参数
	 * @param id
	 * */
	@Override
	public void deleteById(String id) {
		this.delete(this.statement+"deleteSystemItemById", id);
	}

}
