package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.system.Notice;
import com.xinyu.model.system.SystemItem;
import com.xinyu.service.common.BaseService;

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
	 * 新建系统参数业务处理
	 * @param object
	 * */
	public void  insertSystemItem(SystemItem systemItem);

	/**
	 * 修改系统参数
	 * @param systemItem
	 * */
	public void updateSystemItem(SystemItem systemItem);

	/**
	 * 根据ID删除系统参数
	 * */
	public void deleteById(String id);

	/**
	 * 数据封装
	 * @param systemItems
	 * @return list
	 * */
	public List<Map<String, Object>> buildListData(List<SystemItem> systemItems);


	/**
	 * 添加公告信息
	 * @param notice
	 * */
	public void insertNotice(Notice notice);


	/**
	 * 更新公告信息
	 * @param notice
	 * */
	public void updateNotice(Notice notice);


	/**
	 * 查询公告信息
	 * @param id
	 * @return Notice
	 * */
	public Notice findNoticeById(String id);
}
