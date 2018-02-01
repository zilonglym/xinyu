package com.xinyu.dao.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockInOrder;

public interface StockInOrderDao {
	
	int isExistByMap(Map<String,Object> params);
	
	void  insertStockInOrder(StockInOrder stockInOrder);

	List<StockInOrder> findStockInOrderByList(Map<String, Object> params);

	StockInOrder getStockInOrderById(String id);

	void updateStockInOrder(StockInOrder stockInOrder);

	int getTotal(Map<String, Object> params);

	StockInOrder findStockInOrderByParam(Map<String, Object> params);
}
