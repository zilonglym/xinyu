package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.ItemGroup;
import com.graby.store.entity.ItemGroupDetail;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderGroup;

/**
 * 组合商品远程接口
 * */
public interface ItemGroupRemote {

	/**
	 * 分页查询组合商品信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<ItemGroup> getItemGroupListByParams(Map<String, Object> params, int page, int rows);

	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	public int getToTal(Map<String, Object> params);

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	public ItemGroup getItemGroupById(String id);

	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	public List<ItemGroupDetail> getDetailsByList(Map<String, Object> params);

	/**
	 * 持久化组合商品
	 * @param map
	 * @return map
	 * */
	public Map<String, Object> saveItemGroup(Map<String, Object> params);

	/**
	 * 更新组合商品是否启用
	 * @param map
	 * */
	public void updateState(Map<String, Object> params);

	/**
	 * 判断是否有套餐
	 * @param order
	 * @return
	 */
	public List<ItemGroup> getGroupByShip(ShipOrder  order);

	public void insertShipOrderGroupList(List<ShipOrderGroup> orderList);
}
