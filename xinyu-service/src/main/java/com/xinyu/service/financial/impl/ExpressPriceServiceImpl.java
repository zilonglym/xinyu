package com.xinyu.service.financial.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.financial.ExpressPriceDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.financial.ExpressPrice;
import com.xinyu.model.financial.ExpressPriceMapping;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.financial.ExpressPriceService;

@Service("expressPriceServiceImpl")
public class ExpressPriceServiceImpl extends BaseServiceImpl implements ExpressPriceService{

	@Autowired
	private ExpressPriceDao priceDao;
	
	@Autowired
	private ShipOrderDao orderDao;
	
	@Autowired
	private ReceiverInfoDao receiverInfoDao;
	
	@Override
	public List<ExpressPrice> findExpressPriceList(Map<String, Object> params) {
		return this.priceDao.findExpressPriceList(params);
	}

	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.priceDao.getTotalResult(params);
	}

	@Override
	public ExpressPrice findExpressPriceByParam(Map<String, Object> params) {
		return this.priceDao.findExpressPriceByParam(params);
	}

	@Override
	public void save(ExpressPrice expressPrice) {
		this.priceDao.save(expressPrice);
	}

	@Override
	public void update(ExpressPrice expressPrice) {
		this.priceDao.update(expressPrice);
	}

	/**
	 * 根据TmsOrder计算运费：
	 * 没有匹配到对应的运费设置，返回404；
	 * 匹配的对应的运费设置，返回200;
	 * 异常，返回500;
	 */
	@Override
	public Map<String, Object> priceCalculate(TmsOrder order) {
		
		ShipOrder shipOrder = this.orderDao.getShipOrderById(order.getOrder().getId());
		
		ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
		
		/**
		 * 根据收件省，商家，快递编码匹配设置
		 */
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("area", receiverInfo.getReceiverProvince());
		params.put("userId", shipOrder.getUser().getId());
		params.put("code", order.getCode());
		ExpressPrice price = this.priceDao.findExpressPriceByParam(params);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		/**
		 * 没有匹配到，直接返回404；
		 * 匹配到执行新增保存操作，返回200.
		 */
		if (price!=null) {
			try {
				ExpressPriceMapping priceMapping = new ExpressPriceMapping();
				priceMapping.generateId();
				priceMapping.setOrderId(order.getId());	
				//首重重量
				priceMapping.setFirstWeight(price.getFirstCost());		
				//首重费用
				priceMapping.setFirstPrice(price.getFirstPrice());			
				//续重重量:订单重量-首重重量
				Double initialWeight = (order.getPackageWeight() - (price.getFirstCost()*1000))/1000;
				if (initialWeight>0) {
					priceMapping.setInitialWeight(initialWeight);
				}else {
					priceMapping.setInitialWeight(0d);
				}
								
				//续重重量对续重设置计算重量分别取整，取余操作
				Double a = initialWeight/price.getInitialCost();
				Double b = initialWeight%price.getInitialCost();
				//续重总重量初始化
				Double initialPrice = 0d;
				/**
				 * 取整大于0，取余大于0，取整结果加1乘以续重设置费用，得出续重总费用；
				 * 取整大于0，取余不大于0，取整结果乘以续重设置费用，得出续重总费用；
				 * 取整不大于0，续重总费用为0。
				 */
				if (a>0) {
					if (b>0) {
						initialPrice = (Math.floor(a)+1)*price.getInitialPrice();
						priceMapping.setInitialPrice(initialPrice);
					}else {
						initialPrice = Math.floor(a)*price.getInitialPrice();
						priceMapping.setInitialPrice(initialPrice);
					}
				}else {
					priceMapping.setInitialPrice(0d);
				}
				//其他单件包裹额外收取总费用
				Double otherPrice = price.getOtherPrice() + price.getDeliveryPrice() + price.getOtherCost() + price.getDeliveryCost();
				priceMapping.setOtherPrice(otherPrice);
				//订单收取总费用
				Double totalPrice = price.getFirstPrice() + initialPrice + otherPrice;
				priceMapping.setTotalPrice(totalPrice);
				
				this.priceDao.insert(priceMapping);
				
				retMap.put("code", "200");
				
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("code", "500");
				retMap.put("msg", e.getMessage());
			}
			
		}else {
			retMap.put("code", "404");
			retMap.put("msg", order.getId()+"没有匹配到对应运费设置！");
		}
			
		return retMap;
	}

}
