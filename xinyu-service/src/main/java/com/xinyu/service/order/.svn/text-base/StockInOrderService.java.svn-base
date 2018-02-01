package com.xinyu.service.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.service.common.BaseService;

/**
 * 入库单Service接口
 * */
public interface StockInOrderService   extends BaseService {
	
	void  insertStockInOrder(StockInOrder stockInOrder);

	List<StockInOrder> findStockInOrderByList(Map<String, Object> params);
	/**
	 * 分页查询入库单信息
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	List<StockInOrder> findStockInOrderByPage(Map<String, Object> params, int page, int rows);

	/**
	 * 入库单计数
	 * @param params
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);

	/**
	 * 重组入库单列表数据
	 * @param list
	 * @return list
	 * */
	List<Map<String, Object>> buildListData(List<StockInOrder> stockInOrders);

	/**
	 * 根据ID查入库单
	 * @param id
	 * @return StockInOrder
	 * */
	StockInOrder findStockInOrderById(String id);

	
	void updateStockInOrder(StockInOrder stockInOrder);

	Map<String, Object> orderCancel(String orderCode, String userId, String type);

	StockInOrder findStockInOrderByParams(Map<String, Object> params);

}
