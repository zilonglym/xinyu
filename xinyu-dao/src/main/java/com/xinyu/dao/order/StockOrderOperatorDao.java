package com.xinyu.dao.order;

import java.util.List;
import java.util.Map;

import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.order.StockOrderOperator;

public interface StockOrderOperatorDao extends BaseDao{

	public List<StockOrderOperator> findStockOrderOperatorByList(Map<String, Object> params);
	
	public void insertStockOrderOperator(StockOrderOperator stockOrderOperator);

	public List<StockOrderOperator> getStockOrderOperatorsByPage(Map<String, Object> params, int page, int rows);

	public int getTotal(Map<String, Object> params);
	
}
