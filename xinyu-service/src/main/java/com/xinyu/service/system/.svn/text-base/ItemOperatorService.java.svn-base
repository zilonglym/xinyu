package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.ItemOperator;
import com.xinyu.service.common.BaseService;

/**
 * 商品操作日志
 * */
public interface ItemOperatorService extends BaseService{
	/**
	 * 生成日志
	 * @param ItemOperator
	 * */
	public void insertItemOperator(ItemOperator info);
	 
	/**
	 * 更新日志
	 * @param ItemOperator
	 * */
	public void updateItemOperator(ItemOperator info);
	
	/**
	 * 根据ID查日志
	 * @param id
	 * @return ItemOperator
	 * */
	public ItemOperator getItemOperatorById(String id);
	
	/**
	 * 条件查日志
	 * @param map
	 * @return list
	 * */
	public List<ItemOperator> getItemOperatorByList(Map<String, Object> params);
	
	/**
	 * 分页查日志
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<ItemOperator> getItemOperatorByPage(Map<String, Object> params,int page,int rows);
	
	/**
	 * 数据封装
	 * @param operators
	 * @return list
	 * */
	public List<Map<String, Object>> buildListData(List<ItemOperator> operators);

	
	/**
	 * 统计计数
	 * @param map
	 * @return int
	 * */
	public int getTotal(Map<String, Object> params);
}
