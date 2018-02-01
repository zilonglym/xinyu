package com.xinyu.service.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.system.Account;
import com.xinyu.model.util.MyException;
import com.xinyu.service.common.BaseService;
/**
 * 出库单Service接口
 * */
public interface StockOutOrderService extends BaseService{

	/**
	 * 出库单计数
	 * @param params
	 * @return int
	* */
	int getTotal(Map<String, Object> params);

	/**
	 * 分页查询出库单数据
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<StockOutOrder> findStockOutOrderByPage(Map<String, Object> params, int page, int rows);
	
	/**
	 * 出库单数据查询
	 * @param params
	 * @return list
	 * */
	List<StockOutOrder> findStockOutOrderByList(Map<String, Object> params);

	/**
	 * 重组出库单数据
	 * @param list
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<StockOutOrder> stockOutOrders);

	/**
	 * 根据Id查询出库单
	 * @param id
	 * @return StockOutOrder
	 * */
	StockOutOrder findStockOutOrderById(String id);

	/**
	 * 出库单取消
	 * @param orderCode
	 * @param userId
	 * @param orderType
	 * @return map
	 * @throws MyException 
	 * */
	Map<String, Object> orderCancel(String orderCode, String userId, String orderType) throws MyException;

	/**
	 * 提交出库单信息，创建confirm相关信息
	 * @param map
	 * @return map
	 * */
	Map<String, Object> submitStockOutOrder(Map<String, Object> map);

	
	StockOutOrder findStockOutOrderByParam(Map<String, Object> params);


}
