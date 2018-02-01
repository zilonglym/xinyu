package com.xinyu.dao.order.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.StockOutOrderDao;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.model.order.enums.OutOrderStateEnum;


/**
 * @author shark_cj
 * @since  2017-04-30
 */
@Repository("stockOutOrderDaoImpl")
public class StockOutOrderDaoImpl  extends BaseDaoImpl implements StockOutOrderDao {
	
	private final String statement = "com.xinyu.dao.order.StockOutOrderDao.";
	
	private final String statementDetail =  "com.xinyu.dao.order.child.WmsStockOutOrderItemDao.";

	@Override
	public void insertStockOutOrder(StockOutOrder stockOutOrder) {
		stockOutOrder.setState(OutOrderStateEnum.getOutOrderStateEnum("SAVE"));
		super.insert(this.statement+"insertStockOutOrder", stockOutOrder);
		List<WmsStockOutOrderItem> orderItemList = stockOutOrder.getOrderItemList();
		for(WmsStockOutOrderItem orderItem :orderItemList){
			super.insert(statementDetail+"insertWmsStockOutOrderItem", orderItem);
		}
	}

	@Override
	public List<StockOutOrder> findStockOutOrderByList(Map<String, Object> params) {
		return (List<StockOutOrder>) super.selectList(statement+"findStockOutOrderByList", params);
	}

	@Override
	public List<StockOutOrder> findStockOutOrderByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return super.selectList(statement+"findStockOutOrderByList", params, rows, page);
	}

	@Override
	public StockOutOrder getStockOutOrderById(String id) {
		StockOutOrder  stockOutOrder = (StockOutOrder) super.selectOne(statement+"getStockOutOrderById", id);
		Map<String,Object> sqlParams =   new HashMap<String, Object>(); 
		sqlParams.put("stockOutOrderId", id);
		List<WmsStockOutOrderItem> list  =	(List<WmsStockOutOrderItem>) super.selectList(statementDetail+"findWmsStockOutOrderItemsByList", sqlParams);
		stockOutOrder.setOrderItemList(list);	
		return stockOutOrder;
	}

	@Override
	public void updateStockOutOrder(StockOutOrder stockOutOrder) {
		super.update(statement+"updateStockOutOrder", stockOutOrder);
	}

	@Override
	public int isExitsByMap(Map<String, Object> params) {
		return (Integer) super.selectOne(statement+"isExitsByMap", params);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(statement+"getTotal", params);
	}

	@Override
	public StockOutOrder findStockOutOrderByParam(Map<String, Object> params) {
		return (StockOutOrder) super.selectOne(statement+"findStockOutOrderByParam", params);
	}
}
