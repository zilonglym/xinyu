package com.xinyu.service.trade;
import java.util.List;
import java.util.Map;
import com.xinyu.service.common.BaseService;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
/**
 * 订单操作日志
 * */
public interface ShipOrderOperatorService extends BaseService {
	 public void saveShipOrderOperator(ShipOrderOperator info);
	 public void updateShipOrderOperator(ShipOrderOperator info);
	 public ShipOrderOperator getShipOrderOperatorById(String id);
	 public List<ShipOrderOperator> getShipOrderOperatorByList(Map<String, Object> params);
	
	 /**
	  * 分页查询订单操作日志
	  * @param params
	  * @param page
	  * @param rows
	  * @return list
	  * */
	 public List<ShipOrderOperator> getShipOrderOperatorByPage(Map<String, Object> params, int page, int rows);
	
	 /**
	  * 订单操作日志统计
	  * @param params
	  * @return int
	  * */
	 public int getTotal(Map<String, Object> params);
	
	 /**
	  * 数据封装
	  * @param list
	  * @return list
	  * */
	 public List<Map<String, Object>> buildListData(List<ShipOrderOperator> operators);
	 
}
