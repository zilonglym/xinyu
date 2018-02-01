package com.xinyu.dao.order.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.StockInOrderDao;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;

/**
 * @author shark_cj
 * @since  2017-04-30
 */
@Repository("stockInOrderDaoImpl")
public class StockInOrderDaoImpl   extends BaseDaoImpl  implements StockInOrderDao {

	private final String statement = "com.xinyu.dao.order.StockInOrderDao.";
	
	private final String statementDetail =  "com.xinyu.dao.order.WmsStockInOrderItemDao.";
	
	
	@Override
	public int isExistByMap(Map<String,Object> params){
		return (Integer)super.selectOne(this.statement+"isExistByMap", params);
	}
	
	
	@Override
	public void insertStockInOrder(StockInOrder stockInOrder) {
		
		//设置新单据初始状态
		stockInOrder.setState(InOrderStateEnum.getInOrderStateEnum("SAVE"));
		
		super.insert(this.statement+"insertStockInOrder", stockInOrder);
		List<WmsStockInOrderItem> orderItemList = stockInOrder.getOrderItemList();
		for(WmsStockInOrderItem orderItem :orderItemList){
			super.insert(statementDetail+"insertWmsStockInOrderItem", orderItem);
		}
	}

	@Override
	public List<StockInOrder> findStockInOrderByList(Map<String, Object> params) {
		return (List<StockInOrder>) super.selectList(statement+"findStockInOrderByList", params);
	}

	@Override
	public StockInOrder getStockInOrderById(String id) {
		StockInOrder stockInOrder  = (StockInOrder) super.selectOne(this.statement+"getStockInOrderById", id);
		
		Map<String,Object>  sqlparam =  new HashMap<String, Object>();
		sqlparam.put("stockInOrderId", id);
		List<WmsStockInOrderItem> items = (List<WmsStockInOrderItem>) this.selectList(statementDetail+"getWmsStockInOrderItemByList",sqlparam);
		stockInOrder.setOrderItemList(items);
		return stockInOrder;
	}

	@Override
	public void updateStockInOrder(StockInOrder stockInOrder) {
		super.update(statement+"updateStockInOrder", stockInOrder);
	}


	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) super.selectOne(statement+"getTotal", params);
	}


	@Override
	public StockInOrder findStockInOrderByParam(Map<String, Object> params) {
		return (StockInOrder) super.selectOne(statement+"findStockInOrderByParam", params);
	}
}
