package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.service.inventory.AccountEntryArray;
import com.graby.store.service.waybill.TradeBatchService;
import com.graby.store.service.wms.ShipOrderService;
import com.taobao.api.ApiException;

@RemotingService(serviceInterface = ShipOrderRemote.class, serviceUrl = "/ship.call")
public class ShipOrderRemoteImpl implements ShipOrderRemote {
	
	public static final Logger logger = Logger.getLogger(ShipOrderRemoteImpl.class);
	@Autowired
	private	ShipOrderService shipOrderService;
	
	@Autowired
	private	TradeBatchService tradeBatchService;
	
	@Autowired
	private	ItemDao itemDao;
	
	
	@Override
	public void updateShipOrder(Map<String, Object> params) {
		// TODO Auto-generated method stub
		 shipOrderService.updateShipOrder(params);
	}
	
	@Override
	public Map<String,Object> splitShipOrderOperation(String type,Long tradeId,Long num){
		return shipOrderService.splitShipOrderOperation(type, tradeId, num);
				
	}
	
	@Override
	public Map<String,Object> splitShipOrder(Map<String,Object> param){
		return shipOrderService.splitShipOrder( param);
	}
	
	@Override
	public List<Map<String,Object>> importERPOrders(Map<String,Object> params){
		return shipOrderService.importERPOrders(params);
	}
	
	
	@Override
	public void updateCompleteTradeBatch(){
		tradeBatchService.updateComplete();
	}
	
	@Override
	public List<ShipOrder> findEntryOrderOnWay(Map<String,Object> params) {
		return shipOrderService.findEntryOrderOnWay(params);
	}
	
	@Override
	public void setSendOrderExpressAndStatusById(List<Map<String, String>> orderMaps) {
		 shipOrderService.setSendOrderExpressAndStatusById(orderMaps);
	}
	
	

	@Override
	public void saveEntryOrder(ShipOrder order) {
		shipOrderService.saveEntryOrder(order);
	}

	@Override
	public void saveShipOrderDetail(Long orderId, Long itemId, long num) {
		shipOrderService.saveShipOrderDetail(orderId, itemId, num, null);
	}

	@Override
	public void deleteShipOrder(Long orderId) {
		shipOrderService.deleteShipOrder(orderId);
	}

	@Override
	public void deleteShipOrderDetail(Long id) {
		shipOrderService.deleteShipOrderDetail(id);
	}

	@Override
	public Page<ShipOrder> findEntrys(Long userid, String status, int page, int pageSize) {
		return shipOrderService.findEntrys(userid, status, page, pageSize);
	}

	@Override
	public ShipOrder getShipOrder(Long id) {
		ShipOrder shipOrder=this.shipOrderService.getShipOrder(id);
		for(int i=0;shipOrder!=null && shipOrder.getDetails()!=null && i<shipOrder.getDetails().size();i++){
			ShipOrderDetail detail=shipOrder.getDetails().get(i);
			Item item=this.itemDao.get(detail.getItem().getId());
			detail.setItem(item);
		}
		return shipOrder;
	}

	@Override
	public boolean sendEntryOrder(Long id) {
		return shipOrderService.sendEntryOrder(id);
	}

	@Override
	public void recivedEntryOrder(Long id, List<AccountEntryArray> entrys) {
		shipOrderService.recivedEntryOrder(id, entrys);
	}

	@Override
	public List<ShipOrder> findSendOrderWaits(Map<String,Object> params) {
		return shipOrderService.findSendOrderWaits(params);
	}
	
	@Override
	public List<ShipOrder> findTradeBatchSendOrderWaits(Map<String,Object> params) {
		return shipOrderService.findTradeBatchSendOrderWaits(params);
	}

	@Override
	public List<ShipOrder> findSendOrderSignWaits(Map<String,Object> params) {
		return shipOrderService.findSendOrderSignWaits(params);
	}

	@Override
	public ShipOrder getShipOrderByTid(Long tid) {
		return shipOrderService.getSendShipOrderByTradeId(tid);
	}

	@Override
	public ShipOrder submitSendOrder(ShipOrder order) throws ApiException {
		return shipOrderService.submitSendOrder(order);
	}

	@Override
	public ShipOrder signSendOrder(Long orderId) {
		return shipOrderService.closeOrder(orderId);
	}


	@Override
	public List<ShipOrder> findGroupSendOrderWaits(Long centroId) {
		return shipOrderService.findGroupSendOrderWaits(centroId);
	}
	
	@Override
	public void setSendOrderExpress(List<Map<String,String>> orderMaps) {
		shipOrderService.setSendOrderExpress(orderMaps);
	}

	@Override
	public List<ShipOrder> findSendOrderByStatus(Long centroId, String status) {
		return shipOrderService.findSendOrderByStatus(centroId, status);
	}

	@Override
	public void reExpressShipOrder(Long[] orderids) {
		shipOrderService.reExpressShipOrder(orderids);
	}	
	
	@Override
	public List<ShipOrder> findSendOrderByQ(String q) {
		return shipOrderService.findSendOrderByQ(q);
	}	

	@Override
	public List<ShipOrder> findSendOrders(Long[] orderIds) {
		return shipOrderService.findSendOrders(orderIds);
	}
	
	
	@Override
	public void submits(Long[] orderids) {
		shipOrderService.submits(orderids);
	}

	@Override
	public void chooseExpress(Long orderId, String expressCompany) {
		shipOrderService.chooseExpress(orderId, expressCompany);
	}

	@Override
	public List<Map<String, Object>> findSendOrdersGroup(Long[] orderIds) {
		return shipOrderService.findSendOrdersGroup(orderIds);
	}
	
	@Override
	public ShipOrder getSendShipOrderByTradeId(Long tradeId) {
		return shipOrderService.getSendShipOrderByTradeId(tradeId);
	}

	@Override
	public void updateShipOrder(ShipOrder order) {
		shipOrderService.updateShipOrder(order);
		
	}

	@Override
	public List<ShipOrder> findReturnOrderOnWay() {
		
		return shipOrderService.findReturnOrderOnWay();
	}

	@Override
	public void updateDetailQuantity(Map<String, Object> params) {
	shipOrderService.updateDetailQuantity(params);
	}

	@Override
	public ShipOrder getShipOrderInfo(Long id) {
		
		return shipOrderService.getShipOrderInfo(id);
	}

	@Override
	public ShipOrderDetail getShipOrderDetail(Map<String, Object> params) {
		
		return shipOrderService.getShipOrderDetail(params);
	}

//  	@Override
//  	public List<ShipOrder> findSendOrderWaitsOk(Map<String, Object> params) {
//  		// TODO Auto-generated method stub
//  		return this.shipOrderService.findSendOrderWaitsOk(params);
//  	}

	@Override
	public void updateShipOrderCentro(int centroId,int shipOrderId) {
		// TODO Auto-generated method stub
		this.shipOrderService.updateShipOrderCentro(centroId,shipOrderId);
	}

	@Override
	public List<ShipOrder> selectShipOrderByList(Map<String, Object> params) {
		return this.shipOrderService.selectShipOrderByList(params);
	}

	@Override
	public List<ShipOrderDetail> shipOrderDetailbyList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderService.shipOrderDetailbyList(params);
	}

	@Override
	public void updateShipOrderStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.shipOrderService.updateShipOrderStatus(params);
	}

	@Override
	public List<ShipOrderDetail> getShipOrderDetailByOrderId(Long orderId) {
		return this.shipOrderService.getShipOrderDetailByOrderId(orderId);
	}

	@Override
	public List<ShipOrder> findSendOrderByStatusAndUserId(Long centroId, String status, Long userId) {
		return this.shipOrderService.findSendOrderByStatusAndUserId(centroId, status, userId);
	}

	@Override
	public void updateShipOrderWeight(Long orderId, Double weight) {
	shipOrderService.updateShipOrderWeight(orderId, weight);
	}

  	@Override
  	public Page<ShipOrder> findSendOrderWaitsOk(int page, int i, Map<String, Object> params) {
   		// TODO Auto-generated method stub
  		 return this.shipOrderService.findSendOrderWaitsOk(page,i,params);
  	}

	@Override
	public Page<ShipOrder> findTradeBatchSendOrderWaits(int page, int i, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderService.findTradeBatchSendOrderWaits(page, i, params);
	}

	@Override
	public Page<ShipOrder> findSendOrderList(int pageNo, int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderService.findSendOrderList(pageNo, pageSize, params);
	}

	@Override
	public void updateShipOrderByTradeId(Map<String, Object> params) {
		shipOrderService.updateShipOrderByTradeId(params);
	}

	@Override
	public List<ShipOrder> findShipOrdersByParams(Map<String, Object> params) {
		return shipOrderService.findShipOrdersByParams(params);
	}

	@Override
	public void updateShipOrderPrice(Long id, Double totalPrice) {
		shipOrderService.updateShipOrderPrice(id,totalPrice);
	}
	@Override
	public List<Map<String, Object>> importOrders(List<Map<String, Object>> params) {
		return shipOrderService.importOrders(params);
	}
	
	@Override
	public long findSendOrderImportCount(){
		return shipOrderService.findSendOrderImportCount();
	}
	
	@Override
	public List<ShipOrder> findSendOrderImport(){
		return shipOrderService.findSendOrderImport();
	}
	
	@Override
	public void updateImportShipOrderSub(){
		shipOrderService.updateImportShipOrderSub();
	}
	
	@Override
	public long getTotalResults(Map<String, Object> params) {
		return this.shipOrderService.getTotalResults(params);
	}

	@Override
	public List<ShipOrder> findShipOrderWaitsOk(int page, int rows, Map<String, Object> params) {
		return this.shipOrderService.findShipOrderWaitsOk(page,rows,params);
	}

	@Override
	public long findTradeBatchSendOrderWaitsCount(Map<String, Object> params) {
		return this.shipOrderService.findTradeBatchSendOrderWaitsCount(params);
	}

	@Override
	public int getSendOrderCount(Map<String, Object> params) {
		return this.shipOrderService.getSendOrderCount(params);
	}

	@Override
	public List<ShipOrder> findeSendOrderByList(Map<String, Object> params, int page, int rows) {
		return this.shipOrderService.findeSendOrderByList(params,page,rows);
	}

	@Override
	public List<ShipOrder> findSendOrderWaitsByPages(int page, int rows, Map<String, Object> params) {
		return this.shipOrderService.findSendOrderWaitsByPages(page,rows,params);
	}

	@Override
	public ShipOrder findShipOrderByExpressOrderno(String expressOrderno) {
		return this.shipOrderService.findShipOrderByExpressOrderno(expressOrderno);
	}

	@Override
	public void updateRemark(Map<String, Object> params) {
		this.shipOrderService.updateRemark(params);
	}

	@Override
	public ShipOrder getShipOrderById(Long id) {
		// TODO Auto-generated method stub
		return this.shipOrderService.getShipOrder(id);
	}
	
	@Override
	public Long getCountByDate(Map<String,Object> params){
		return shipOrderService.getCountByDate(params);
	}
	@Override
	public Long getSuccessCountByDate(Map<String,Object> params){
		return shipOrderService.getSuccessCountByDate(params);
	}
	@Override
	public Long getCheckSuccessCountByDate(Map<String,Object> params){
		return shipOrderService.getCheckSuccessCountByDate(params);
	}
	@Override
	public Long getCheckFailCountByDate(Map<String,Object> params){
		return shipOrderService.getCheckFailCountByDate(params);
	}
	@Override
	public List<ShipOrder> selectShipOrderNotEXISTS(Map<String,Object> params){
		return shipOrderService.selectShipOrderNotEXISTS(params);
	}

	@Override
	public void updateShipOrderById(ShipOrder order) {
		this.shipOrderService.updateShipOrderById(order);
	}

	@Override
	public Map<String, Object> copyShipOrder(Long shipOrderId) {
		// TODO Auto-generated method stub
		return shipOrderService.copyShipOrder(shipOrderId);
	}

	@Override
	public List<Map<String, Object>> getShipOrderItemByWorkGroup(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderService.getShipOrderItemByWorkGroup(params);
	}

	@Override
	public List<Map<String, Object>> importOrdersNew(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderService.importOrdersNew(params);
	}
}
