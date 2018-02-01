package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ItemGroup;
import com.graby.store.entity.ItemGroupDetail;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderGroup;
import com.graby.store.service.item.ItemGroupService;

/**
 * 组合商品远程impl接口
 * */
@RemotingService(serviceInterface = ItemGroupRemote.class, serviceUrl = "/itemGroup.call")
public class ItemGroupRemoteImpl implements ItemGroupRemote{

	@Autowired
	private ItemGroupService itemGroupService;
	
	/**
	 * 分页查询组合商品信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<ItemGroup> getItemGroupListByParams(Map<String, Object> params, int page, int rows) {
		return this.itemGroupService.getItemGroupListByParams(params, page, rows);
	}

	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	@Override
	public int getToTal(Map<String, Object> params) {
		return this.itemGroupService.getToTal(params);
	}

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	@Override
	public ItemGroup getItemGroupById(String id) {
		return this.itemGroupService.getItemGroupById(id);
	}

	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	@Override
	public List<ItemGroupDetail> getDetailsByList(Map<String, Object> params) {
		return this.itemGroupService.getDetailsByList(params);
	}

	/**
	 * 持久化组合商品
	 * @param map
	 * @return map
	 * */
	@Override
	public Map<String, Object> saveItemGroup(Map<String, Object> params) {
		return this.itemGroupService.saveItemGroup(params);
	}

	/**
	 * 更新组合商品是否启用
	 * @param map
	 * */
	@Override
	public void updateState(Map<String, Object> params) {
		this.itemGroupService.updateState(params);
	}

	@Override
	public List<ItemGroup> getGroupByShip(ShipOrder order) {
		// TODO Auto-generated method stub
		return this.itemGroupService.getGroupByShip(order);
	}

	@Override
	public void insertShipOrderGroupList(List<ShipOrderGroup> orderList) {
		// TODO Auto-generated method stub
		itemGroupService.insertShipOrderGroupList(orderList);
	}
}
