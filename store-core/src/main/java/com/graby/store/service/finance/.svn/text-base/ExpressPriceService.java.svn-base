package com.graby.store.service.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mvel2.util.ThisLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ExpressPriceDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.entity.CheckOut;
import com.graby.store.entity.ExpressPrice;
import com.graby.store.entity.ExpressPriceMaping;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;

/**
 * 快递价格操作服务
 * */
@Component
@Transactional
public class ExpressPriceService {
	
	@Autowired
	private  ExpressPriceDao expressPriceDao;
	
	@Autowired
	private ShipOrderDao orderDao;
	
	/**
	 * 通过param查询ExpressPrice
	 * @param params
	 * @return ExpressPrice
	 * */
	public ExpressPrice findExpressPriceByParam(Map<String, Object> params) {
		return this.expressPriceDao.findExpressPriceByParam(params);
	}
	
	/**
	 * 新增
	 * @param expressPrice
	 * */
	public void save(ExpressPrice expressPrice){
		this.expressPriceDao.save(expressPrice);;
	}
	
	/**
	 * 修改
	 * @param expressPrice
	 * */
	public void update(ExpressPrice expressPrice){
		this.expressPriceDao.update(expressPrice);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return expressPrices
	 * */
	public List<ExpressPrice> findExpressPrices(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start", start);
		params.put("offset",offset);
		List<ExpressPrice> expressPrices=this.expressPriceDao.findExpressPrices(params);
		System.err.println("size:"+expressPrices.size());
		return expressPrices;
	} 
	
	/**
	 * 统计符合条件的总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params) {
		return this.expressPriceDao.getTotalResult(params);
	}
	
	/**
	 * checkout出库记录运费计算
	 * @param shipOrder
	 * @return map
	 * */
	public Map<String,Object> PriceCount(ShipOrder shipOrder){
 		ExpressPriceMaping expressPriceMaping=new ExpressPriceMaping();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
		Double price=0.0;
		resultMap.put("orderId",shipOrder.getId());
		expressPriceMaping.setOrderId(shipOrder.getId());
		params.put("userId",shipOrder.getCreateUser().getId());
		params.put("code",shipOrder.getExpressCode());
		params.put("area",shipOrder.getReceiverState());
		ExpressPrice expressPrice=this.expressPriceDao.findExpressPriceByParam(params);
		if (expressPrice!=null) {
			Double firstPrice=expressPrice.getFirstPrice();//首重费用
			Double initalPrice=expressPrice.getInitialPrice();//续重费用
			Double deliveryPrice=expressPrice.getDeliveryPrice();//派送费用
			Double otherPrice=expressPrice.getOtherPrice();//其他费用
			Double firstCost=expressPrice.getFirstCost();//首重:first_cost		
			Double initialCost=expressPrice.getInitialCost();//续重:initial_cost
			Double otherCost=expressPrice.getOtherCost();//仓储费用:other_cost
			Double deliveryCost=expressPrice.getDeliveryCost();//打包费用:delivery_cost
			Double totalWeight=shipOrder.getTotalWeight();
			if (totalWeight==null) {
				totalWeight=0.0;
			}
			Double total=0.0;
			if (totalWeight>firstCost) {			
				Double b=totalWeight-firstCost;
				Double a=b%initialCost;
				resultMap.put("firstWeight", firstCost);//首重
 				expressPriceMaping.setFirstWeight(firstCost);
				if (a==0.0) {
					total=b/initialCost;
				}else {
					total=(b/initialCost)+1;
				}
				resultMap.put("initialWeight", total);//续重
 				expressPriceMaping.setInitialWeight(total);
			}else {
				expressPriceMaping.setFirstWeight(firstCost);
				expressPriceMaping.setInitialWeight(0d);
			}
			resultMap.put("firstPrice", firstPrice);//首重费用
 			expressPriceMaping.setFirstPrice(firstPrice);
			resultMap.put("initalPrice", initalPrice);//续重费用
 			expressPriceMaping.setInitialPrice(initalPrice);
			Double other=otherPrice+deliveryPrice+otherCost+deliveryCost;
			resultMap.put("otherPrice",other);//其他费用
 			expressPriceMaping.setOtherPrice(other);
			price=total*initalPrice+firstPrice+otherPrice+deliveryPrice+otherCost+deliveryCost;	
			resultMap.put("totalPrice", price);//订单运费
 			expressPriceMaping.setTotalPrice(price);
 			expressPriceMaping.setOrderId(shipOrder.getId());
		}else {
			resultMap.put("initialWeight","0");
			resultMap.put("firstWeight","0");
			resultMap.put("firstPrice", "0");//首重费用
			resultMap.put("initalPrice", "0");//续重费用
			resultMap.put("otherPrice", "0");//其他费用
			resultMap.put("totalPrice", "0");
			expressPriceMaping.setFirstPrice(0d);
			expressPriceMaping.setFirstWeight(0d);
			expressPriceMaping.setInitialPrice(0d);
			expressPriceMaping.setInitialWeight(0d);
			expressPriceMaping.setOtherPrice(0d);
			expressPriceMaping.setTotalPrice(0d);	
		}
		params.clear();
		params.put("orderId",shipOrder.getId());
		ExpressPriceMaping priceMaping=this.expressPriceDao.findPriceByParams(params);
		if (priceMaping!=null) {
			priceMaping.setId(priceMaping.getId());
			priceMaping.setOrderId(shipOrder.getId());
			priceMaping.setFirstPrice(expressPriceMaping.getFirstPrice());
			priceMaping.setFirstWeight(expressPriceMaping.getFirstWeight());
			priceMaping.setInitialPrice(expressPriceMaping.getInitialPrice());
			priceMaping.setInitialWeight(expressPriceMaping.getInitialWeight());
			priceMaping.setOtherPrice(expressPriceMaping.getOtherPrice());
			priceMaping.setTotalPrice(expressPriceMaping.getTotalPrice());
			this.expressPriceDao.updatePrice(priceMaping);	
		}else {
			this.expressPriceDao.insert(expressPriceMaping);
		}
		return resultMap;
	}

 	public ExpressPriceMaping findPriceByParams(Map<String, Object> params) {
 		return this.expressPriceDao.findPriceByParams(params);
 	}

	public void save(ExpressPriceMaping priceMaping) {
		this.expressPriceDao.insert(priceMaping);
	}

	public void updatePrice(ExpressPriceMaping priceMaping) {
		this.expressPriceDao.updatePrice(priceMaping);
	}
}
