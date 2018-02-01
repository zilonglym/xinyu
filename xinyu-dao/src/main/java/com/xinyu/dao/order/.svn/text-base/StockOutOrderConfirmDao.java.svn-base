package com.xinyu.dao.order;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.StockOutOrderConfirm;
import com.xinyu.model.order.child.StockOutItemInventory;
import com.xinyu.model.order.child.StockOutOrderItem;

public interface StockOutOrderConfirmDao extends BaseDao{

	void insertStockOutOrderConfirm(StockOutOrderConfirm stockOutOrderConfirm);

	List<StockOutOrderConfirm> getStockOutOrderConfirmList(Map<String, Object> params);

	int getCheckItemNum(Map<String, Object> params);

	List<StockOutOrderItem> getStockOutOrderItem(Map<String, Object> params);

	List<StockOutItemInventory> getStockOutItemInventory(Map<String, Object> params);

}
