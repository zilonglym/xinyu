package com.xinyu.service.waybill;

import java.util.Map;

import com.xinyu.model.system.Account;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.service.common.BaseService;

public interface WayBillService extends BaseService{

	String getCainiaoTemplates() throws Exception;

	 /**
	  * 取新的菜鸟单号
	  * @param trade
	  * @param cpCode
	  * @param url
	  * @param batchCode
	  * @return
	  */
	Map<String, Object> getCainiaoBill(String ids, String cpCode, String url, String batchCode,Account account)  throws Exception;


	Map<String, String> billGet(ShipOrder shipOrder, String cp_code, String valueOf);

	
	Map<String, String> billCancel(ShipOrder shipOrder);

	/**
	  * 查看快递单号状态
	  * @param code
	  * @return
	  */
	 Map<String,Object> queryDetail(String code);

}
