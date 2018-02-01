package com.xinyu.dao.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockOutOrder;

public interface StockOutOrderDao {
	
	int isExitsByMap(Map<String,Object> params);
	
	void insertStockOutOrder(StockOutOrder stockOutOrder);

	List<StockOutOrder> findStockOutOrderByList(Map<String, Object> params);

	List<StockOutOrder> findStockOutOrderByPage(Map<String, Object> params, int page, int rows);

	StockOutOrder getStockOutOrderById(String id);

	void updateStockOutOrder(StockOutOrder stockOutOrder);

	int getTotal(Map<String, Object> params);

	StockOutOrder findStockOutOrderByParam(Map<String, Object> params);

}
