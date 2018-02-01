package com.xinyu.service.order;

import java.util.List;
import java.util.Map;
import com.xinyu.service.common.BaseService;

public interface StockOutOrderConfirmService extends BaseService{

	List<Map<String, Object>> getConfirmListDataByOrderId(String orderId);

}
