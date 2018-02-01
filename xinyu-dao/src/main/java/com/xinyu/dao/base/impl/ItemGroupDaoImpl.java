package com.xinyu.dao.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.ItemGroupDao;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ItemGroupDetail;

/**
 * 组合商品DaoImpl
 * */
@Repository("itemGroupDaoImpl")
public class ItemGroupDaoImpl extends BaseDaoImpl implements ItemGroupDao{

	private final String statement = "com.xinyu.dao.base.ItemGroupDao.";
	
	private final String detailStatement = "com.xinyu.dao.base.ItemGroupDetailDao.";
	
	/**
	 * 分页查询组合商品信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<ItemGroup> getItemGroupListByParams(Map<String, Object> params, int page, int rows) {
		return this.selectList(this.statement+"getItemGroupListByParams", params, rows, page);
	}

	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	@Override
	public int getToTal(Map<String, Object> params) {
		return (Integer) this.selectOne(this.statement+"getToTal", params);
	}

	/**
	 * 持久化组合商品
	 * @param map
	 * @return map
	 * */
	@Override
	public void insertItemGroup(ItemGroup itemGroup) {
		List<ItemGroupDetail> details = itemGroup.getDetai();
		for(ItemGroupDetail detail:details){
			this.insert(this.detailStatement+"insertItemGroupDetail", detail);
		}
		this.insert(this.statement+"insertItemGroup", itemGroup);
	}

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	@Override
	public ItemGroup getItemGroupById(String id) {
		return (ItemGroup) this.selectOne(this.statement+"getItemGroupById", id);
	}

	/**
	 * 更新组合商品
	 * @param itemGroup
	 * */
	@Override
	public void updateItemGroup(ItemGroup itemGroup) {
		List<ItemGroupDetail> details = itemGroup.getDetai();
		for(ItemGroupDetail detail:details){
			this.insert(this.detailStatement+"insertItemGroupDetail", detail);
		}
		this.update(this.statement+"updateItemGroup",itemGroup);
	}

	/**
	 * 更新组合商品启用状态
	 * @param map
	 * */
	@Override
	public void updateState(Map<String, Object> params) {
		this.update(this.statement+"updateState",params);
	}

	/**
	 * 根据组合商品ID删除明细
	 * @param itemGroupId
	 * */
	@Override
	public void deleteDetails(String id) {
		this.delete(this.detailStatement+"deleteDetails", id);
	}

	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	@Override
	public List<ItemGroupDetail> getDetailsByList(Map<String, Object> params) {
		return (List<ItemGroupDetail>) this.selectList(this.detailStatement+"getDetailsByList", params);
	}

	/**
	 * 根据订单查组合商品
	 * @param shipOrder
	 * @return list
	 * */
	@Override
	public List<ItemGroup> getItemGroupByOrder(Map<String, Object> params) {
		return (List<ItemGroup>) this.selectList(this.statement+"getItemGroupByOrder", params);
	}
}
