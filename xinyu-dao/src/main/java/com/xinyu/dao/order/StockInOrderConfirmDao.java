package com.xinyu.dao.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockInOrderConfirm;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;

public interface StockInOrderConfirmDao {
	
	 void insertStockInOrderConfirm(StockInOrderConfirm confirm);
	 
	 List<StockInOrderConfirm>  getStockInOrderConfirm(Map<String,Object> params);
	 
	 List<StockInOrderItem>  getStockInOrderItem(Map<String,Object> params);
	 
	 List<StockInItemInventory>  getStockInItemInventory(Map<String,Object> params);
	 
	 int getCheckItemSum(Map<String,Object> params);
}