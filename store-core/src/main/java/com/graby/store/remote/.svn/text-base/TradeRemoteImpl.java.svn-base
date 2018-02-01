package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;
import com.graby.store.service.inventory.AccountTemplate;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.inventory.QmInventoryService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ShipOrderService;

@RemotingService(serviceInterface = TradeRemote.class, serviceUrl = "/trade.call")
public class TradeRemoteImpl implements TradeRemote {

	@Autowired
	private TradeService tradeService;
	
	@Override
	public  Map<String,Object> reSetAudit(Long  tradeId,Long centroId,Long  userId,Long operator){
		return tradeService.reSetAudit(tradeId, centroId, userId,operator);
	}
	
	@Override
	public void updateTradeStatus(Long tradeId, String status) {
		tradeService.updateTradeStatus(tradeId, status);
	}
	
	@Override
	public List<Trade> findTradeListByLgAging(String[] ids){
		return tradeService.findTradeListByLgAging(ids);
	}
	
	@Override
	public Long getRelatedTradeId(Long tid) {
		return tradeService.getRelatedTradeId(tid);
	}

	@Override
	public TradeMapping getRelatedMapping(Long tid) {
		return tradeService.getRelatedMapping(tid);
	}

	@Override
	public void createTrade(Trade trade) {
		tradeService.createTrade(trade, null);
	}

	@Override
	public ShipOrder createSendShipOrderByTradeId(Long tradeId) {
		return tradeService.createSendShipOrderByTradeId(tradeId);
	}

	@Override
	public void createAllSendShipOrder(Long centroId) {
		tradeService.createAllSendShipOrder(centroId);
	}

	@Override
	public List<Trade> findWaitAuditTrades() {
		return tradeService.findWaitAuditTrades();
	}

	@Override
	public List<Trade> findWaitAuditTradesBy(Map<String, Object> params) {
		return tradeService.findWaitAuditTradesBy(params);
	}

	@Override
	public Page<Trade> findUserTrades(Long userId, String status, long pageNo, long pageSize) {
		return tradeService.findUserTrades(userId, status, pageNo, pageSize);
	}

	@Override
	public Trade getTrade(Long id) {
		return tradeService.getTrade(id);
	}

	@Override
	public Page<Trade> findUnfinishedTrades(int pageNo, int pageSize,Map<String,Object> params) {
		return tradeService.findUnfinishedTrades(pageNo, pageSize,params);
	}

	@Override
	public void deleteTrade(Long tradeId,int userId) {
		tradeService.deleteTrade(tradeId,userId);
	}
	
	@Override
	public void invalidTrade(Long tradeId,Long userId) {
		// TODO Auto-generated method stub
		this.tradeService.invalidTrade(tradeId,userId);
	}
	@Override
	public void reset(Long tradeId) {
		tradeService.reset(tradeId);
	}

	@Override
	public List<Map<String, String>> findWaitAuditCitys(Map<String,Object> params) {
		return tradeService.findWaitAuditCitys(params);
	}

	@Override
	public void splitTrade(Long tradeId, Long orderId) {
		tradeService.splitTrade(tradeId, orderId);
	}

	@Override
	public void mergeTrade(Long[] tradeIds) {
		tradeService.mergeTrade(tradeIds);
	}

	@Override
	public List<Trade> findSplitedTrades(Map<String,Object> params) {
		return tradeService.findSplitedTrades(params);
	}


	@Override
	public List<TradeOrder> fetchTradeOrders(Long id) {
		// TODO Auto-generated method stub
		return tradeService.fetchTradeOrders(id);
	}


	@Override
	public void updateTradeWeight(Long tradeId, Double weight) {
		tradeService.updateTradeWeight(tradeId, weight);
	}


	@Override
	public void updateTrade(Map<String, Object> params) {
		tradeService.updateTrade(params);
	}


	@Override
	public List<Trade> findTradesBy(Map<String, Object> params, int page, int rows) {
		return this.tradeService.findTradesBy(params,page,rows);
	}


	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.tradeService.getTotalResult(params);
	}

	@Override
	public int findSplitedTradesCount(Map<String, Object> params) {
		return this.tradeService.findSplitedTradesCount(params);
	}


	@Override
	public List<Trade> findSplitedTradesByPage(Map<String, Object> params, int page, int rows) {
		return this.tradeService.findSplitedTradesByPage(params,page,rows);
	}


	@Override
	public List<Trade> findWaitAuditTradesByPages(Map<String, Object> params, int page, int rows) {
		return this.tradeService.findWaitAuditTradesByPages(params,page,rows);
	}

	@Override
	public String getItemsByTrade(int tradeId){
		return this.tradeService.getItemsByTrade(tradeId);
	}

	/**
	 * 获取未审核订单数量按商家分类统计
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> getWaitAuditTradesTotal() {
		return this.tradeService.getWaitAuditTradesTotal();
	}

	@Override
	public void initSystemItem() {
		// TODO Auto-generated method stub
		this.tradeService.initAuditMap();
	}

	@Override
	public List<Trade> findWaitAuditTradesByLgAging(Map<String, Object> params) {
		return this.tradeService.findWaitAuditTradesByLgAging(params);
	}
	
	
}
