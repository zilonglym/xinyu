package com.graby.store.service.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.base.GroupMap;
import com.graby.store.entity.Item;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.trade.sync.DateUtils;
import com.graby.store.util.EncryptUtil;
import com.graby.store.web.top.TopApi;
import com.graby.store.web.top.TopApi.TradeStatus;
import com.graby.store.web.top.TradeAdapter;
import com.taobao.api.ApiException;

@Component
public class SyncTopTradeServiceImpl  {

/*	
	
	// 是否合并淘宝订单
	private  boolean combine;
		
	@Autowired
	private TopApi topApi;
	@Autowired
	private ItemService itemService;
	@Autowired
	private TradeAdapter tradeAdapter;
	@Autowired
	private TradeInfoDao tradeDao;
	
	
	
	
	public void syncTrade(String sessionKey,int ...day) throws Exception{
		topApi.setSessionKey(sessionKey);
		//fetchTopTrades(TopApi.TradeStatus.TRADE_WAIT_SELLER_SEND_GOODS, day);
		GroupMap<String, Trade> group=fetchWaitSendTopTrades(day);
		createTradesFromTop(group);
	}
	
	/**
	 * 查询最近几天的交易待发货订单
	 * @param preDays
	 * @return
	 * @throws Exception 
	public GroupMap<String, Trade> fetchWaitSendTopTrades(int... preDays) throws Exception {
	//	List<com.taobao.api.domain.Trade> trades = fetchTopTrades(TopApi.TradeStatus.TRADE_WAIT_SELLER_SEND_GOODS, preDays);
		
		List<com.taobao.api.domain.Trade> trades = fetchTopTrades(null, preDays);
		return groupAdapter(trades);
	}
	/**
	 * 查询最近几天的交易待发货订单
	 * @param preDays
	 * @return
	 * @throws Exception 
	private List<com.taobao.api.domain.Trade> fetchTopTrades(String status, int... preDays) throws Exception {
		List<com.taobao.api.domain.Trade> trades = new ArrayList<com.taobao.api.domain.Trade>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (int preday : preDays) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -preday);
			Date day = cal.getTime();
			Date start =  DateUtils.getMoning(day);
			Date end = preday == 0 ? day : DateUtils.getMoning(day);
			System.err.println("start:"+sdf.format(start)+"|end:"+sdf.format(end));
			List<com.taobao.api.domain.Trade> result = topApi.getTrades(status, start, end);
			trades.addAll(result);
		}
		return trades;
	}
	
	
	/**
	 * 根据淘宝交易ID批量创建系统交易
	 * 合并收货方相同的订单。
	 * 1000条限制，后续待接口无限制放开。
	 * @param tids
	 * @throws NumberFormatException
	 * @throws ApiException
	public void createTradesFromTop(GroupMap<String, Trade> tradeGroup) throws NumberFormatException, ApiException {
		int success = 0;
		Set<String> keys = tradeGroup.getKeySet();
		for (String key : keys) {
			List<Trade> topTrades = tradeGroup.getList(key);
			// 是否合并订单
			if (topTrades.size()>1 && combine) {
				Long[] tidArray = new Long[topTrades.size()];
				tidArray[0] = topTrades.get(0).getTid();
				// 第一个订单
				Trade trade = topTrades.get(0);
				// 合并其他订单
				for (int i = 1; i < topTrades.size(); i++) {
					Trade anotherTrade = topTrades.get(i);
					// 合并订单明细
					trade.getOrders().addAll(anotherTrade.getOrders());
					// 合并备注及留言
					if (StringUtils.isNotBlank(anotherTrade.getSellerMemo())) {
						trade.setSellerMemo(trade.getSellerMemo() + "," + anotherTrade.getSellerMemo());
					}
					if (StringUtils.isNotBlank(anotherTrade.getBuyerMessage())) {
						trade.setBuyerMessage(trade.getBuyerMessage() + "," + anotherTrade.getBuyerMessage());
					}
					if (StringUtils.isNotBlank(anotherTrade.getBuyerMemo())) {
						trade.setBuyerMemo(trade.getBuyerMemo() + "," + anotherTrade.getBuyerMemo());
					}
					tidArray[i] = topTrades.get(i).getTid();
				}
				trade.setTradeFrom(StringUtils.join(tidArray, ","));
				createTrade(trade, null);
				for (Long tid : tidArray) {
					mappingTrade(tid, trade.getId());
					success++;
				}
			} else {
				for (Trade topTrade : topTrades) {
					Trade trade = topTrade;
					createTrade(trade, topTrade.getTid());
					success++;
				}
			}
		}
	//	MessageContextHelper.append("成功创建系统交易:" + success + "条");
	}
	
	// 合并规则:详细地址+买家昵称
	private String hashTrade(com.taobao.api.domain.Trade trade) {
		StringBuffer buf = new StringBuffer();
		buf.append(trade.getBuyerNick());
		buf.append(trade.getReceiverState()).append(trade.getReceiverCity()).append(trade.getReceiverDistrict());
		buf.append(trade.getReceiverAddress()).append(trade.getReceiverName()).append(trade.getReceiverMobile());
		return EncryptUtil.md5(buf.toString());
	}
	
	
	private void mappingTrade(Long tid, String tradeId) {
		if (tid != null) {
			TradeMapping mapping = new TradeMapping(tid, tradeId);
			tradeDao.createTradeMapping(mapping);
		}
	}
	
	/**
	 * 创建系统交易.
	 * TODO 都用mybatis
	 * @param trade
	 * @param tid TODO
	@Transactional(readOnly = false)
	public Trade createTrade(Trade trade, Long tid) {
			// 状态等待物流通审核
			trade.setStatus(TradeStatus.TRADE_WAIT_CENTRO_AUDIT);
			this.tradeDao.insert(trade);
			List<TradeOrder> orders = trade.getOrders();
			if (CollectionUtils.isNotEmpty(orders)) {
				for (TradeOrder tradeOrder : orders) {
					// 退款的不创建
					if (!tradeOrder.isHasRefund()) {
						// 更新商品SKU
						if(!trade.getTradeFrom().equals(TradeStatus.SourceFlag_QM)){
							itemService.updateSku(tradeOrder.getItem().getId(), tradeOrder.getSkuPropertiesName());
						}
						tradeOrder.setTrade(trade);
						tradeDao.saveTradeOrder(tradeOrder);
					}
				}
			}
			// 创建关联关系
			mappingTrade(tid, trade.getId());
//		}
		return trade;
	}
	
	/**
	 * 分组交易
	 * @param trades
	 * @return
	 * @throws ApiException
	private GroupMap<String, Trade> groupAdapter(List<com.taobao.api.domain.Trade> trades) throws ApiException {
		GroupMap<String, Trade> groupResults = new GroupMap<String, Trade>(); 
		for (com.taobao.api.domain.Trade topTrade : trades) {
			Trade trade = tradeAdapter.adapter(topTrade);
			// 是否已关联
			TradeMapping mapping = getRelatedMapping(topTrade.getTid());
			if (mapping != null) {
				trade.setTag(mapping.getStatus());
				groupResults.put("related", trade);
			} else {
				
				// 是否退款（子订单都需要退款）
				if (trade.isNeedRefund()) {
					groupResults.put("refund", trade);
					continue;
				}
				
				// 根据库存查看订单是否可发送
				boolean useable = true;
				for (TradeOrder order : trade.getOrders()) {
					Long numIid = order.getNumIid();
					Long skuId = order.getSkuId();
					// 是否已关联
					String itemId = itemService.getRelatedItemId(""+numIid ,skuId);
					if (itemId == null) {
						// 未关联
						order.setStockNum(-1);
						useable = false;
					} else {
						Item item = itemService.getItem(""+itemId);
						order.setItem(item);
//						
					}
				}
				groupResults.put(useable? "useable" : "failed", trade);
			}
		}
		return groupResults;
	}
	
	/**
	 * 根据淘宝交易ID获取系统Mapping
	 * @param tid
	 * @return
	public TradeMapping getRelatedMapping(Long tid) {
		return tradeDao.getRelatedMapping(tid);
	}
	*/
}
