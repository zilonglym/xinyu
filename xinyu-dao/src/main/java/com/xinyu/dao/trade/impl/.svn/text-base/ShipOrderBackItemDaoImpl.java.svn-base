package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderBackItemDao;
import com.xinyu.model.trade.ShipOrderBackItem;

@Repository("shipOrderBackItemDaoImpl")
public class ShipOrderBackItemDaoImpl extends BaseDaoImpl implements ShipOrderBackItemDao{

	private final String statement = "com.xinyu.dao.trade.ShipOrderBackItemDao.";
	
	public List<ShipOrderBackItem> getOrderBackItemList(Map<String, Object> params) {
		return (List<ShipOrderBackItem>) super.selectList(this.statement+"getOrderBackItemList", params);
	}

	public void insertOrderBackItem(ShipOrderBackItem orderBackItem) {
		super.insert(this.statement+"insertOrderBackItem", orderBackItem);
	}

	public void updateOrderBackItem(ShipOrderBackItem orderBackItem) {
		super.update(this.statement+"updateOrderBackItem", orderBackItem);
	}

	public void deleteOrderBackItemById(String id) {
		super.delete(this.statement+"deleteOrderBackItemById", id);
	}

	public void deleteOrderBackItemByBackId(String orderBackId) {
		super.delete(this.statement+"deleteOrderBackItemByBackId", orderBackId);
	}

}
