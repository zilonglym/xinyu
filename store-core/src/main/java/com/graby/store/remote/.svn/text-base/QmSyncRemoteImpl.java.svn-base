package com.graby.store.remote;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.service.qm.QmConfirmService;
import com.graby.store.service.qm.QmSyncQueryService;
import com.graby.store.service.qm.QmSyncService;



@RemotingService(serviceInterface = QmSyncRemote.class, serviceUrl = "/qmsync.call")
public class QmSyncRemoteImpl implements QmSyncRemote{
	@Autowired
	public QmSyncService qmSyncService;
	@Autowired
	public QmConfirmService qmConfirmService;
	@Autowired
	public QmSyncQueryService qmSyncQueryService;
	@Override
	public String itemSync(String xmlStr) throws Exception {
		
		return qmSyncService.itemSync(xmlStr);
	}

	@Override
	public String itemBatchSync(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		return this.qmSyncService.itemBatchSync(xmlStr);
	}
	@Override
	public String combineitem(String xmlStr) throws Exception {
		
		return qmSyncService.combineitem(xmlStr);
	}

	@Override
	public String entryorder(String xmlStr) throws Exception {
		
		return qmSyncService.entryorder(xmlStr);
	}

	@Override
	public String returnorder(String xmlStr) throws Exception {
		
		return qmSyncService.returnorder(xmlStr);
	}

	@Override
	public String stockout(String xmlStr) throws Exception {
	
		return qmSyncService.stockout(xmlStr);
	}

	@Override
	public String deliveryorder(String xmlStr) throws Exception {
		
		return qmSyncService.deliveryorder(xmlStr);
	}

	@Override
	public String deliveryQuery(String xmlStr) throws Exception {
		
		return qmSyncService.deliveryQuery(xmlStr);
	}

	@Override
	public String orderprocessQuery(String xmlStr) throws Exception {
		
		return qmSyncService.orderprocessQuery(xmlStr);
	}

	@Override
	public String orderCancel(String xmlStr) throws Exception {
		
		return qmSyncService.orderCancel(xmlStr);
	}

	@Override
	public String inventoryQuery(String xmlStr) throws Exception {
		
		return qmSyncService.inventoryQuery(xmlStr);
	}

	@Override
	public String storeprocessCreate(String xmlStr) throws Exception {
		
		return qmSyncService.storeprocessCreate(xmlStr);
	}
	
	@Override
	public String submitQm(String xmlStr, String method,String customerId) {
		return qmSyncQueryService.submitQm(xmlStr, method,customerId);
	}

	@Override
	public String itemlackReport(String jsonStr) {		
		return qmSyncQueryService.itemlackReport(jsonStr);
	}
	@Override
	public String entryOrderConfirm(Map<String, Object> map) throws Exception {
		return qmConfirmService.entryOrderConfirm(map);
	}

	@Override
	public String stockoutConfirm(Map<String, Object> map) throws Exception {
		return qmConfirmService.stockoutConfirm(map);
	}

	@Override
	public String returnorderConfirm(Map<String, Object> map) throws Exception {
		return qmConfirmService.returnorderConfirm(map);
	}

	@Override
	public void createTradeByShipOrder(Long shipOrderId) {
		// TODO Auto-generated method stub
		 this.qmSyncService.createTradeByShipOrder(shipOrderId);
	}

	
}
