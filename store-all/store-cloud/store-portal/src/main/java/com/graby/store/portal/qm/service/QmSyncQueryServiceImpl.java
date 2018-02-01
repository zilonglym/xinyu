package com.graby.store.portal.qm.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.graby.store.portal.qm.util.XmlUtil;

@Component
public class QmSyncQueryServiceImpl implements QmSyncQueryService {

	/**
	 * 发货单查询
	 */
	@Override
	public String deliveryorderQuery(String xmlStr) throws Exception {
		Map<String,Object> map= XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> params=(Map<String, Object>)map.get("request");
		String orderCode=(String) params.get("orderCode");
		String orderId=(String) params.get("orderId");
		
		return null;
	}
	
	/**
	 * 订单流水查询
	 */
	@Override
	public String orderprocessQuery(String xmlStr) throws Exception {
		Map<String,Object> map= XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> params=(Map<String, Object>)map.get("request");
		String orderCode=(String) params.get("orderCode");
		String orderId=(String) params.get("orderId");
		return null;
	}

}
