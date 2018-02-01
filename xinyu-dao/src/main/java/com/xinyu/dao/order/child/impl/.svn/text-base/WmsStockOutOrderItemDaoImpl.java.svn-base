package com.xinyu.dao.order.child.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.child.WmsStockOutOrderItemDao;
import com.xinyu.model.order.child.WmsStockOutOrderItem;

@Repository("wmsStockOutOrderItemDaoImpl")
public class WmsStockOutOrderItemDaoImpl extends BaseDaoImpl implements WmsStockOutOrderItemDao{

	@Override
	public List<WmsStockOutOrderItem> findWmsStockOutOrderItemsByList(Map<String, Object> params) {
		return (List<WmsStockOutOrderItem>) super.selectList("com.xinyu.dao.order.child.WmsStockOutOrderItemDao.findWmsStockOutOrderItemsByList",params);
	}

	@Override
	public void updateWmsStockOutOrderItem(WmsStockOutOrderItem stockOutOrderItem) {
		super.update("com.xinyu.dao.order.child.WmsStockOutOrderItemDao.updateWmsStockOutOrderItem",stockOutOrderItem);
	}

	@Override
	public void insertWmsStockOutOrderItem(WmsStockOutOrderItem stockOutOrderItem) {
		super.insert("com.xinyu.dao.order.child.WmsStockOutOrderItemDao.insertWmsStockOutOrderItem",stockOutOrderItem);
	}

	@Override
	public WmsStockOutOrderItem getWmsStockOutOrderItemById(String id) {
		return (WmsStockOutOrderItem) super.selectOne("com.xinyu.dao.order.child.WmsStockOutOrderItemDao.getWmsStockOutOrderItemById", id);
	}

}
