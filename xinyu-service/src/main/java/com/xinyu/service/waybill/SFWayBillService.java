package com.xinyu.service.waybill;

import java.util.Map;

import com.xinyu.model.trade.ShipOrder;
import com.xinyu.service.common.BaseService;

public interface SFWayBillService extends BaseService{
	/**
	  * 批量的取订单号
	  * @param ids
	  * @return
	  * @throws Exception
	  */
	Map<String, Object> getSFBillNo(String[] ids, String batchCode) throws Exception;
	
}
