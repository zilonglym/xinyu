package com.xinyu.service.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.Item;
import com.xinyu.model.inventory.Inventory;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.WmsConsignOrderItem;

public interface InventoryService {

	/**
	 * 批量库存处理
	 * @param parmasList
	 * @return
	 */
	Map<String,Object> addNumBatch(List<Map<String,Object>> parmasList);
	
	/**
	 * 商品库存分页查询 
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<Map<String, Object>> findInventoryByPage(Map<String, Object> params, int page, int rows);

	/**
	 * 计数商品库存
	 * @param params
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);

	/**
	 * 数据重组
	 * @param list
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<Map<String, Object>> inventories);


	/**
	 * 更新库存：待发货直接减库存
	 * @param type 
	 * @param paramsList 
	 * */
	void updateInventoryByOrder(List<Map<String, Object>> paramsList,String type);
	
	/**
	 * 查询商品库存
	 * @param params
	 * @return Inventory
	 * */
	Inventory findInventoryByParam(Map<String, Object> params);

	/**
	 * 库存校验
	 * @param Item
	 * @param map
	 * @return map
	 * */
	Map<String, Object> isHaveInventory(Map<String, Object> params);

	
	/**
	 * 库存初始化
	 * @param list
	 * */
	List<Map<String,Object>> inItInventory(List<Map<String, Object>> list);
	
	
	
	public void updateInventoryByCreate(Map<String,Object>  orderMap);
}
