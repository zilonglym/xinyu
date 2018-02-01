package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.Item;

/**
 * 商品信息Dao接口的实现
 */
@Repository("itemDaoImpl")
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

	private final String statement = "com.xinyu.dao.base.ItemDao.";

	/**
	 * 新增Item信息
	 * 
	 * @param item
	 */
	@Override
	public void saveItem(Item item) {
		// TODO Auto-generated method stub
		this.insert(this.statement + "insertItem", item);
	}

	/**
	 * 根据ID查询Item信息
	 * 
	 * @param id
	 * @return Item
	 */
	@Override
	public Item getItem(String id) {
		// TODO Auto-generated method stub
		return (Item) this.selectOne(this.statement + "getItemById", id);
	}

	/**
	 * 更新商品信息
	 * 
	 * @param item
	 */
	@Override
	public void updateItem(Item item) {
		// TODO Auto-generated method stub
		this.update(this.statement + "updateItem", item);
	}

	/**
	 * 条件查询商品信息
	 * 
	 * @param map
	 * @return list
	 */
	@Override
	public List<Item> getItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<Item>) this.selectList(this.statement + "getItemByList", params);
	}
	@Override
	public void updateItemStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.update(this.statement + "updateItemStatus", params);
	}

	@Override
	public void updateItemType(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.update(this.statement+ "updateItemType", params);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) this.selectOne(this.statement+"getTotal", params);
	}
	@Override
	public Map<String, Object> findStoreItemById(String id) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) this.selectOne(this.statement+"findStoreItemById", id);
	}
	@Override
	public List<Map<String, Object>> getStoreItemList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) this.selectList(this.statement+"getStoreItemList", params);
	}
	@Override
	public int getStoreTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) this.selectOne(this.statement+"getStoreTotal", params);
	}

}
