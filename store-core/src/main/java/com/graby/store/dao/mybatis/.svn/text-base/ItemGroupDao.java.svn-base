package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ItemGroup;
import com.graby.store.entity.ItemGroupDetail;

/**
 * 组合商品Dao接口
 * */
@MyBatisRepository
public interface ItemGroupDao {

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	ItemGroup getItemGroupById(String id);

	/**
	 * 根据根据组合商品id删除明细
	 * @param itemGroupId
	 * */
	void deleteDetails(String itemGroupId);

	/**
	 * 插入一条组合商品
	 * @param itemGroup
	 * */
	void insertItemGroup(ItemGroup itemGroup);

	/**
	 * 更新组合商品
	 * @param itemGroup
	 * */
	void updateItemGroup(ItemGroup itemGroup);

	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	int getToTal(Map<String, Object> params);

	/**
	 * 更新组合商品是否启用
	 * @param map
	 * */
	void updateState(Map<String, Object> params);

	/**
	 * 参数查询组合商品
	 * @param map
	 * @return list
	 * */
	List<ItemGroup> getItemGroupListByParams(Map<String, Object> params);
	
	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	List<ItemGroupDetail> getDetailsByList(Map<String, Object> params);

	/**
	 * 插入组合商品明细
	 * @param itemGroupDetail
	 * */
	void insertItemGroupDetail(ItemGroupDetail itemGroupDetail);


	/**
	 * 插根据商品名称查询组合商品
	 * @param name
	 * @return ItemGroup
	 * */
	ItemGroup getItemGroupByName(String name);

	List<ItemGroup> getItemGroupByOrder(Map<String, Object> params);

}
