package com.xinyu.service.order;

import java.util.List;
import java.util.Map;

import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.service.common.BaseService;

/**
 * 入库单明细
 * */
public interface WmsStockInOrderItemService extends BaseService{

	/**
	 * 入库单明细重组
	 * @param orderItems
	 * @return list
	 * */
	List<Map<String, Object>> buildDetailListData(List<WmsStockInOrderItem> orderItems);

}
