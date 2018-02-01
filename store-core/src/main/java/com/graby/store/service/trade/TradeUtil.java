package com.graby.store.service.trade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;

public class TradeUtil {
	public static final Logger logger = Logger.getLogger(TradeUtil.class);
	
	public  Trade adapter(ShipOrder torder) throws Exception {
		Trade trade = new Trade();
		trade.setUser(torder.getCreateUser());
		// 目前只支持单仓库
		Centro centro =new Centro();
		centro.setId(1L);
		trade.setCentro(centro);
		
		// -------- 主订单适配 -------- //
		
		// 订单基本信息
		trade.setTradeFrom(Trade.SourceFlag.SourceFlag_QM);   
		
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String payTime = torder.getPayTime();
			if(payTime==null || payTime.trim().length()==0){
				trade.setPayTime(new Date());
			}else{
				//trade.setPayTime(sdf.parse(payTime));
				trade.setPayTime(new Date());
			}
		trade.setShippingType("express"); //设置默认  为快递
		trade.setType(torder.getType());
		trade.setStatus(Trade.Status.TRADE_WAIT_EXPRESS_SHIP);
		trade.setLgAging(""+torder.getId());
		trade.setWeight(torder.getTotalWeight());
		
		trade.setOtherOrderNo(torder.getOtherOrderNo());
		
		// 收货方信息
		trade.setReceiverAddress(torder.getReceiverAddress());
		trade.setReceiverCity(torder.getReceiverCity());
		trade.setReceiverDistrict(torder.getReceiverDistrict());
		trade.setReceiverMobile(torder.getReceiverMobile());
		trade.setReceiverName(torder.getReceiverName());
		trade.setReceiverPhone(torder.getReceiverPhone());
		trade.setReceiverState(torder.getReceiverState());
		trade.setReceiverZip(torder.getReceiverZip());
		trade.setLgAgingType(torder.getOrderno());//  奇门发货单编码  deliveryOrderCode 
		
		// 卖家和买家相关信息
		trade.setBuyerArea(torder.getSenderArea());
		trade.setBuyerNick(torder.getBuyerNick());
		trade.setSellerPhone(torder.getSellerPhone());
		trade.setSellerMobile(torder.getSellerMobile());
		trade.setTid(torder.getId());
		trade.setSellerMemo(torder.getSellerMessage());
		
		trade.setBuyerMemo(torder.getBuyerMemo());
		trade.setBuyerMessage(torder.getBuyerMessage());
		
		// 子订单适配
		List<ShipOrderDetail> orders = torder.getDetails();
		for (ShipOrderDetail order : orders) {
			TradeOrder tradeOrder = new TradeOrder();
			logger.info("TradeUtil.item:"+order.getId()+"|"+order.getItem());
			tradeOrder.setNumIid(order.getItem().getId());
			tradeOrder.setTitle(order.getItemTitle());
			tradeOrder.setAdjustFee(order.getActualPrice());
			tradeOrder.setDiscountFee(order.getActualPrice());
			tradeOrder.setTotalFee(order.getActualPrice());
			tradeOrder.setNum(order.getNum());
			tradeOrder.setItem(order.getItem());
			tradeOrder.setSkuPropertiesName(order.getSkuPropertiesName());
			tradeOrder.setBuyerNick(trade.getBuyerNick());
			trade.addOrder(tradeOrder);
		}
		return trade;
	}
	
}
