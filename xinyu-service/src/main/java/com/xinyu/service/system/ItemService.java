package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.Item;
import com.xinyu.service.common.BaseService;

/**
 * 商品处理业务接口
 * @author lenovo
 *
 */
public interface ItemService extends BaseService {
	
	/**
	 * 根据ID查询Item信息
	 * @param id
	 * @return Item
	 * */
	public Item getItem(String id);

	/**
	 * 新增Item信息
	 * @param item
	 * */
	public void saveItem(Item item);
	
	/**
	 * 批量新增Item信息
	 * @param list
	 * */
	public void saveItemList(List<Item> itemList);
	
	/**
	 * 更新商品信息
	 * @param item
	 * */
	public void updateItem(Item item);
	
	public void updateItemStatus(Map<String,Object> params);
	/**
	 * 条件查询商品信息
	 * @param map
	 * @return list
	 * */
	public List<Item> getItemByList(Map<String,Object> params);
	
	/**
	 * 分页查询商品信息
	 * @param page
	 * @param rows
	 * @param params
	 * @return list
	 * */
	public List<Item> getItemsByPage(int page, int rows, Map<String, Object> params);

	/**
	 * 符合条件的商品数量
	 * @param params
	 * @return int
	 * */
	public int getTotal(Map<String, Object> params);

	/**
	 * 商品自动生成条码
	 * @param item
	 * @return map
	 * */
	public Map<String, Object> generateBarCode(Item item);

	/**
	 * 商品信息重组
	 * @param items
	 * @return list
	 * */
	public List<Map<String, Object>> buildItemList(List<Item> items);

	
	/**
	 * 商品入库处理
	 * @param itemId
	 * @param itemCount
	 * @return map
	 * */
	public Map<String, Object> submitItemCount(String itemId, String itemCount);

	/**
	 * 更新商品类型
	 * */
	public void updateItemType(Map<String, Object> params);

	
	public Map<String, Object> findStoreItemById(String id);

	public List<Map<String, Object>> getStoreItemsByPage(int page, int rows, Map<String, Object> params);

	public int getStoreTotal(Map<String, Object> params);


}
