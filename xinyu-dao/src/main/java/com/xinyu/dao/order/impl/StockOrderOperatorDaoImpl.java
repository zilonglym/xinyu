package com.xinyu.dao.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.order.StockOrderOperator;

@Repository("stockOrderOperatorDaoImpl")
public class StockOrderOperatorDaoImpl extends BaseDaoImpl implements StockOrderOperatorDao{
	
	@Override
	public List<StockOrderOperator> findStockOrderOperatorByList(Map<String, Object> params) {
		return (List<StockOrderOperator>) super.selectList("com.xinyu.dao.order.StockOrderOperatorDao.findStockOrderOperatorByList",params);
	}

	@Override
	public void insertStockOrderOperator(StockOrderOperator stockOrderOperator) {
		super.insert("com.xinyu.dao.order.StockOrderOperatorDao.insertStockOrderOperator", stockOrderOperator);
	}

	@Override
	public List<StockOrderOperator> getStockOrderOperatorsByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return super.selectList("com.xinyu.dao.order.StockOrderOperatorDao.findStockOrderOperatorByList", params, rows, page);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) super.selectOne("com.xinyu.dao.order.StockOrderOperatorDao.getTotal", params);
	}

}
