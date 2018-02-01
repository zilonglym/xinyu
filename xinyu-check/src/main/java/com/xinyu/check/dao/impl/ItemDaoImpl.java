package com.xinyu.check.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.check.dao.ItemDao;
import com.xinyu.check.dao.base.BaseDaoImpl;
import com.xinyu.check.model.Item;

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
	public List<Item> getItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<Item>) this.selectList(this.statement + "getItemByList", params);
	}

	/**
	 * 分页条件查询商品信息
	 * 
	 * @param page
	 * @param rows
	 * @param params
	 * @return list
	 */
	@Override
	public List<Item> getItemsByPage(int page, int rows, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.selectList(this.statement + "getItemByList", params, rows, page);
	}

	@Override
	public void updateItemStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.update(this.statement + "updateItemStatus", params);
	}

}
