package com.xinyu.service.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.service.common.BaseService;

public interface StockOrderOperatorService extends BaseService{
	
	public List<StockOrderOperator> findStockOrderOperatorByList(Map<String, Object> params);
	
	public void insertStockOrderOperator(StockOrderOperator stockOrderOperator);

	public List<StockOrderOperator> getStockOrderOperatorsByPage(Map<String, Object> params, int page, int rows);

	public int getTotal(Map<String, Object> params);

	public List<Map<String, Object>> buildListData(List<StockOrderOperator> stockOrderOperators);
}
