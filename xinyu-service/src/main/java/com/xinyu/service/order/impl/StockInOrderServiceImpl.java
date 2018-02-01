package com.xinyu.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.order.StockInOrderDao;
import com.xinyu.model.base.User;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.InOrderTypeEnum;
import com.xinyu.model.order.enums.OrderFlagEnum;
import com.xinyu.model.order.enums.OrderSourceEnum;
import com.xinyu.model.order.enums.OrderTypeEnum;
import com.xinyu.model.order.enums.OutOrderStateEnum;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.StockInOrderService;

/**
 * 入库单业务处理
 * */
@Service("stockInOrderServiceImpl")
public class StockInOrderServiceImpl extends BaseServiceImpl implements StockInOrderService {

	@Autowired
	private StockInOrderDao  stockInOrderDao;
	
	@Autowired
	private UserDao  userDao;

	@Override
	public void insertStockInOrder(StockInOrder stockInOrder) {
		stockInOrderDao.insertStockInOrder(stockInOrder);
	}

	@Override
	public List<StockInOrder> findStockInOrderByList(Map<String, Object> params) {
		return stockInOrderDao.findStockInOrderByList(params);
	}

	/**
	 * 分页查询入库单信息
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<StockInOrder> findStockInOrderByPage(Map<String, Object> params, int page, int rows) {
		int pageNum = (page - 1) * rows;
		int pageSize = rows;
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		return this.stockInOrderDao.findStockInOrderByList(params);
	}

	/**
	 * 入库单计数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.stockInOrderDao.getTotal(params);
	}


	/**
	 * 重组入库单列表数据
	 * @param list
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<StockInOrder> stockInOrders) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(StockInOrder stockInOrder:stockInOrders){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", stockInOrder.getId());
			map.put("source", stockInOrder.getOrderType().getKey());
			User user = this.userDao.getUserById(stockInOrder.getUser().getId());
			map.put("userName", user.getSubscriberName());
			map.put("erpOrderCode", (stockInOrder.getErpOrderCode()==null?"":stockInOrder.getErpOrderCode()));
			map.put("orderCode", stockInOrder.getOrderCode());
			map.put("status", stockInOrder.getState().getKey());
			for(InOrderTypeEnum orderType:InOrderTypeEnum.values()){
				if (stockInOrder.getOrderType().getKey().equals(orderType.getKey())) {
					map.put("orderType", orderType.getDescription());
				}
			}
			map.put("expectStartTime",(stockInOrder.getExpectStartTime()==null?"":stockInOrder.getExpectStartTime()));
			map.put("expectEndTime",(stockInOrder.getExpectEndTime()==null?"":stockInOrder.getExpectEndTime()));
			for(OrderFlagEnum flag:OrderFlagEnum.values()){
				if (stockInOrder.getOrderFlag().getKey().equals(flag.getKey())) {
					map.put("orderFlag", flag.getDescription());
				}
			}
			for(OrderSourceEnum source:OrderSourceEnum.values()){
				if (stockInOrder.getOrderSource().getKey().equals(source.getKey())) {
					map.put("orderSource", source.getDescription());
				}
			}
			map.put("orderCreateTime", sf.format(stockInOrder.getOrderCreateTime()));
			map.put("returnReason", (stockInOrder.getReturnReason()==null?"":stockInOrder.getReturnReason()));		
			map.put("tmsServiceCode", (stockInOrder.getTmsServiceCode()==null?"":stockInOrder.getTmsServiceCode()));
			map.put("tmsOrderCode", (stockInOrder.getTmsOrderCode()==null?"":stockInOrder.getTmsOrderCode()));
			map.put("buyerNick", (stockInOrder.getBuyerNick()==null?"":stockInOrder.getBuyerNick()));
			map.put("remark", (stockInOrder.getRemark()==null?"":stockInOrder.getRemark()));
			resultList.add(map);
		}
		return resultList;
	}


	/**
	 * 根据Id查入库单
	 * @param id
	 * @return StockInOrder
	 * */
	@Override
	public StockInOrder findStockInOrderById(String id) {
		return this.stockInOrderDao.getStockInOrderById(id);
	}

	/**
	 * 更新入库单状态
	 * */
	@Override
	public void updateStockInOrder(StockInOrder stockInOrder) {
		this.stockInOrderDao.updateStockInOrder(stockInOrder);
	}

	@Override
	public Map<String, Object> orderCancel(String orderCode, String userId, String type) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("userId", userId);
		params.put("orderType", OrderTypeEnum.getOrderTypeEnum(type));
//		logger.debug("params:"+params);
//		System.err.println(params);
//		List<StockInOrder> stockInOrders = this.stockInOrderDao.findStockInOrderByList(params);
		StockInOrder stockInOrder = this.stockInOrderDao.findStockInOrderByParam(params);
//		System.err.println(stockOutOrders.size());
		if (stockInOrder!=null) {
			stockInOrder.setState(InOrderStateEnum.CANCEL);
			if(stockInOrder.getState().equals("CANCEL")){
				//已取消的入库单不能重复取消
				retMap.put("msg", "该入库单已取消，不能重复取消");
			}else{
				//未取消的入库单，对库存进行修改
				this.stockInOrderDao.updateStockInOrder(stockInOrder);
				retMap.put("msg", "该入库单取消成功!");
			}
		}
//		logger.debug("msg:"+retMap);
		return retMap;
	}

	@Override
	public StockInOrder findStockInOrderByParams(Map<String, Object> params) {
		return this.stockInOrderDao.findStockInOrderByParam(params);
	}
}
