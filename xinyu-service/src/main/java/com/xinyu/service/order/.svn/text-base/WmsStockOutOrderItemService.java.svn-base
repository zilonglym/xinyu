package com.xinyu.service.order;

import java.util.List;
import java.util.Map;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.service.common.BaseService;

/**
 * 出库单明细
 * */
public interface WmsStockOutOrderItemService extends BaseService{

	/**
	 * 出库单明细重组数据
	 * @param orderItems
	 * @return list
	 * */
	List<Map<String, Object>> buildDetailListData(List<WmsStockOutOrderItem> orderItems);

	List<WmsStockOutOrderItem> findWmsStockOutOrderItemsByList(Map<String, Object> params);

}
