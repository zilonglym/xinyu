package com.xinyu.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.StockOrderOperatorService;

@Service("stockOrderOperatorServiceImpl")
public class StockOrderOperatorServiceImpl extends BaseServiceImpl implements StockOrderOperatorService{

	@Autowired
	private StockOrderOperatorDao operatorDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public List<StockOrderOperator> findStockOrderOperatorByList(Map<String, Object> params) {
		return this.operatorDao.findStockOrderOperatorByList(params);
	}

	@Override
	public void insertStockOrderOperator(StockOrderOperator stockOrderOperator) {
		this.operatorDao.insertStockOrderOperator(stockOrderOperator);
		
	}

	@Override
	public List<StockOrderOperator> getStockOrderOperatorsByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.operatorDao.getStockOrderOperatorsByPage(params,page,rows);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.operatorDao.getTotal(params);
	}

	/**
	 * 数据封装
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<StockOrderOperator> stockOrderOperators) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(StockOrderOperator operator:stockOrderOperators){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("operatorDate", sf.format(operator.getOperateDate()));
			Account account = this.accountDao.findAcountById(operator.getAccount().getId());
			if (account!=null) {
				map.put("account", (account!=null?account.getShortName():"cainiao"));
			}else {
				map.put("account", "cainiao");
			}
			map.put("remark", operator.getOrderType());
			map.put("model", operator.getOperateType().getDescription());
			map.put("oldValue", operator.getOldValue());
			map.put("newValue", operator.getNewValue());
			map.put("description", operator.getDescription());
			resultList.add(map);
		}
		return resultList;
	}

}
