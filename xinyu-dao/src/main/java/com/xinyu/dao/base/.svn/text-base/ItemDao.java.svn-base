package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.Item;

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

	public void updateItemStatus(Map<String, Object> params);

	public void updateItemType(Map<String, Object> params);

	public int getTotal(Map<String, Object> params);

	public Map<String, Object> findStoreItemById(String id);

	public List<Map<String, Object>> getStoreItemList(Map<String, Object> params);

	public int getStoreTotal(Map<String, Object> params);

}
