package com.xinyu.check.dao;

import java.util.List;
import java.util.Map;

import com.xinyu.check.dao.base.BaseDao;
import com.xinyu.check.model.Item;

/**
 * 商品Dao接口
 * */
public interface ItemDao extends BaseDao {
	
	/**
	 * 新增Item信息
	 * @param item
	 * */
	public void saveItem(Item item);

	/**
	 * 根据ID查询Item信息
	 * @param id
	 * @return Item
	 * */
	public Item getItem(String id);

	/**
	 * 更新商品信息
	 * @param item
	 * */
	public void updateItem(Item item);

	/**
	 * 条件查询商品信息
	 * @param map
	 * @return list
	 * */
	public List<Item> getItemByList(Map<String, Object> params);
	
	
	/**
	 * 分页条件查询商品信息
	 * @param page
	 * @param rows
	 * @param params
	 * @return list
	 * */
	public List<Item> getItemsByPage(int page, int rows, Map<String, Object> params);

	public void updateItemStatus(Map<String, Object> params);

}
