package com.xinyu.service.trade.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.dao.trade.ShipOrderOperatorDao;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.system.Account;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ShipOrderOperatorService;


@Repository("shipOrderOperatorServiceImpl")
public class ShipOrderOperatorServiceImpl extends BaseServiceImpl implements ShipOrderOperatorService {
	
	@Autowired
	private ShipOrderOperatorDao operatorDao;

	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private ShipOrderDao shipOrderDao;
	
	@Autowired
	private ReceiverInfoDao receiverInfoDao;
	
	public void saveShipOrderOperator(ShipOrderOperator info) {
		this.operatorDao.saveShipOrderOperator(info);
	}

	public void updateShipOrderOperator(ShipOrderOperator info) {
		this.operatorDao.updateShipOrderOperator(info);
	}

	public ShipOrderOperator getShipOrderOperatorById(String id) {
		return this.operatorDao.getShipOrderOperatorById(id);
	}

	public List<ShipOrderOperator> getShipOrderOperatorByList(Map<String, Object> params) {
		return this.operatorDao.getShipOrderOperatorByList(params);
	}

	 /**
	  * 分页查询订单操作日志
	  * @param params
	  * @param page
	  * @param rows
	  * @return list
	  * */
	@Override
	public List<ShipOrderOperator> getShipOrderOperatorByPage(Map<String, Object> params, int page, int rows) {
		
		int pageNum = (page-1)*rows;
		int pageSize = rows;
		params.put("pageSize", pageSize);
		params.put("paegNum", pageNum);
		
		return this.operatorDao.getShipOrderOperatorByList(params);
	}

	/**
	  * 订单操作日志统计
	  * @param params
	  * @return int
	  * */
	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getShipOrderOperatorByList(params).size();
	}

	/**
	  * 数据封装
	  * @param list
	  * @return list
	  * */
	@Override
	public List<Map<String, Object>> buildListData(List<ShipOrderOperator> operators) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(ShipOrderOperator operator:operators){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", operator.getId());
			Account account = operator.getAccount();			
			if (account!=null) {
				account = this.accountDao.findAcountById(operator.getAccount().getId());
				map.put("account", (account!=null?account.getShortName():"cainiao"));
			}else {
				map.put("account", "cainiao");
			}
			map.put("operatorDate", sf.format(operator.getOperatorDate()));
			
			if (operator.getOperatorModel()!=null) {
				map.put("model", operator.getOperatorModel().getDescription());
			}
			
			map.put("oldValue", operator.getOldValue());
			map.put("newValue", operator.getNewValue());
			map.put("description", operator.getDescription());	
			results.add(map);
		}
		return results;
	}
}
