package com.xinyu.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.NoticeDao;
import com.xinyu.dao.base.SystemItemDao;
import com.xinyu.model.system.Notice;
import com.xinyu.model.system.SystemItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.SystemItemService;

@Service("systemItemServiceImpl")
public class SystemItemServiceImpl extends BaseServiceImpl implements SystemItemService{

	@Autowired
	private SystemItemDao systemItemDao;
	
	@Autowired
	private NoticeDao noticeDao;
	
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
	 * 新建系统参数业务处理
	 * @param object
	 * @return map
	 * */
	@Override
	public void insertSystemItem(SystemItem systemItem) {
		this.systemItemDao.insertSystemItem(systemItem);
	}

	/**
	 * 修改系统参数
	 * @param systemItem
	 * */
	@Override
	public void updateSystemItem(SystemItem systemItem) {
		this.systemItemDao.updateSysItem(systemItem);
	}


	/**
	 * 根据ID删除系统参数
	 * @param id
	 * */
	@Override
	public void deleteById(String id) {
		this.systemItemDao.deleteById(id);
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

	@Override
	public void insertNotice(Notice notice) {
		this.noticeDao.insert(notice);
	}

	@Override
	public void updateNotice(Notice notice) {
		this.noticeDao.update(notice);
	}

	@Override
	public Notice findNoticeById(String id) {
		return this.noticeDao.findNoticeById(id);
	}

}
