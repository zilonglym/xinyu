package com.xinyu.service.system;

import java.util.List;
import java.util.Map;

import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ItemGroupDetail;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.service.common.BaseService;

/**
 * 组合商品业务接口
 * */
public interface ItemGroupService extends BaseService{

	/**
	 * 分页查询组合商品信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<ItemGroup> getItemGroupListByParams(Map<String, Object> params, int page, int rows);

	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	int getToTal(Map<String, Object> params);

	/**
	 * 封装组合商品
	 * @param itemGroups
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<ItemGroup> itemGroups);

	/**
	 * 持久化组合商品
	 * @param map
	 * @return map
	 * */
	Map<String, Object> saveItemGroup(Map<String, Object> params);

	/**
	 * 更新组合商品是否启用
	 * @param map
	 * */
	void updateState(Map<String, Object> params);

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	ItemGroup getItemGroupById(String id);

	/**
	 * 封装组合商品明细
	 * @param details
	 * @return list
	 * */
	List<Map<String, Object>> buildDetaisListData(List<ItemGroupDetail> details);

	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	List<ItemGroupDetail> getDetailsByList(Map<String, Object> params);
	
	/**
	 * 根据订单查组合商品
	 * @param shipOrder
	 * @return list
	 * */
	List<ItemGroup> getGroupByShip(ShipOrder  order);

}
