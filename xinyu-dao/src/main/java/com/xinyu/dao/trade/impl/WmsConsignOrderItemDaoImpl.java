package com.xinyu.dao.trade.impl;

import java.util.List;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.WmsConsignOrderItemDao;
import com.xinyu.model.trade.WmsConsignOrderItem;

@Repository("wmsConsignOrderItemDao")
public class WmsConsignOrderItemDaoImpl extends BaseDaoImpl implements WmsConsignOrderItemDao {

	private final String statement="com.xinyu.dao.WmsConsignOrderItemDao.";
	
	@Override
	public void insertWmsConsignOrderItem(WmsConsignOrderItem item) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertWmsConsignOrderItem", item);
	}

	@Override
	public void updateWmsConsignOrderItem(WmsConsignOrderItem item) {
		// TODO Auto-generated method stub
		this.update(statement+"updateWmsConsignOrderItem", item);
	}

	@Override
	public WmsConsignOrderItem getWmsConsignOrderItemById(String id) {
		// TODO Auto-generated method stub
		return (WmsConsignOrderItem) this.selectOne(statement+"getWmsConsignOrderItemById", id);
	}

	@Override
	public List<WmsConsignOrderItem> getWmsConsignOrderItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<WmsConsignOrderItem>) this.selectList(statement+"getWmsConsignOrderItemByList", params);
	}

	@Override
	public void deleteWmsConsignOrderItem(String id) {
		// TODO Auto-generated method stub
		this.delete(this.statement+"deleteWmsConsignOrderItem", id);
	}

	@Override
	public List<Map<String, Object>> findStoreOrderItemList(Map<String, Object> params) {
		return (List<Map<String, Object>>) this.selectList(this.statement+"findStoreOrderItemList", params);
	}

}
