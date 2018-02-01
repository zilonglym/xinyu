package com.xinyu.dao.base;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ItemGroupDetail;

/**
 * 组合商品Dao接口
 * */
public interface ItemGroupDao extends BaseDao{

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
	 * 持久化组合商品
	 * @param map
	 * @return map
	 * */
	void insertItemGroup(ItemGroup itemGroup);

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	ItemGroup getItemGroupById(String id);

	/**
	 * 更新组合商品
	 * @param itemGroup
	 * */
	void updateItemGroup(ItemGroup itemGroup);

	/**
	 * 更新组合商品启用状态
	 * @param map
	 * */
	void updateState(Map<String, Object> params);

	/**
	 * 根据组合商品ID删除明细
	 * @param itemGroupId
	 * */
	void deleteDetails(String itemGroupId);

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
	List<ItemGroup> getItemGroupByOrder(Map<String, Object> params);

}
