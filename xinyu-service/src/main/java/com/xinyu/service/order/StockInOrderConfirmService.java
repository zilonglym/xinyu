package com.xinyu.service.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockInOrderConfirm;


public interface StockInOrderConfirmService{

	
	Map<String, Object> save(StockInOrderConfirm confirm);
	
	/**
	 * 获得入库下所有 确认信息
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getConfirmListDataByOrderId(String  id);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	int getCheckItemSum(Map<String,Object> params);
	

	List<StockInOrderConfirm> getStockInOrderConfirm(Map<String, Object> params);
	
}