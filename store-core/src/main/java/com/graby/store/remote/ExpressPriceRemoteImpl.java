package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CheckOut;
import com.graby.store.entity.ExpressPrice;
import com.graby.store.entity.ExpressPriceMaping;
import com.graby.store.entity.ShipOrder;
import com.graby.store.service.finance.ExpressPriceService;

/**
 * 快递价格远程服务接口实现
 */
@RemotingService(serviceInterface = ExpressPriceRemote.class, serviceUrl = "/expressPrice.call")
public class ExpressPriceRemoteImpl implements ExpressPriceRemote{

	@Autowired
	private ExpressPriceService expressPriceService;

	/**
	 * 通过param查询
	 * @param params
	 * @return ExpressPrice
	 * */
	@Override
	public ExpressPrice findExpressPriceByParam(Map<String, Object> params) {
		return this.expressPriceService.findExpressPriceByParam(params);
	}

	/**
	 * 添加
	 * @param expressPrice
	 * */
	@Override
	public void save(ExpressPrice expressPrice) {
		this.expressPriceService.save(expressPrice);
	}

	/**
	 * 更新
	 * @param expressPrice
	 * */
	@Override
	public void update(ExpressPrice expressPrice) {
		this.expressPriceService.update(expressPrice);
	}

	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<ExpressPrice> findExpressPrices(Map<String, Object> params, int page, int rows) {
		return this.expressPriceService.findExpressPrices(params, page, rows);
	}

	/**
	 * 根据param统计总记录数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.expressPriceService.getTotalResult(params);
	}

	/**
	 * checkout出库记录运费计算
	 * @param checkOut
	 * @return price
	 * */
	@Override
	public Map<String, Object> PriceCount(ShipOrder shipOrder) {
		return this.expressPriceService.PriceCount(shipOrder);
	}

	@Override
	public void insert(ExpressPriceMaping priceMaping) {
		this.expressPriceService.save(priceMaping);
	}

	@Override
	public ExpressPriceMaping findPriceByParams(Map<String, Object> params) {
		return this.expressPriceService.findPriceByParams(params);
	}

	@Override
	public void updatePrice(ExpressPriceMaping priceMaping) {
		this.expressPriceService.updatePrice(priceMaping);
	}
	
}
