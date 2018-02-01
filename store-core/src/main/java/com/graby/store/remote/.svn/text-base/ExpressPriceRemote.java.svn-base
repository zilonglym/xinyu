package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.apache.shiro.web.util.SavedRequest;

import com.graby.store.entity.CheckOut;
import com.graby.store.entity.ExpressPrice;
import com.graby.store.entity.ExpressPriceMaping;
import com.graby.store.entity.ShipOrder;

/**
 * 快递价格远程服务接口
 */
public interface ExpressPriceRemote {
	
	/**
	 * 通过param查询
	 * @param params
	 * @return ExpressPrice
	 * */
	public ExpressPrice findExpressPriceByParam(Map<String, Object> params);
	
	/**
	 * 添加
	 * @param expressPrice
	 * */
	public void save(ExpressPrice expressPrice);
	
	/**
	 * 更新
	 * @param expressPrice
	 * */
	public void update(ExpressPrice expressPrice);
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<ExpressPrice> findExpressPrices(Map<String, Object> params, int page, int rows);
	
	/**
	 * 根据param统计总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params);
	
	/**
	 * checkout出库记录运费计算
	 * @param checkOut
	 * @return price
	 * */
	public Map<String,Object> PriceCount(ShipOrder shipOrder);
	
	/**
	 * 根据param查找ExpressPriceMaping
	 * @param params
	 * @return ExpressPriceMaping
	 * */
	public ExpressPriceMaping findPriceByParams(Map<String, Object> params);

	/**
	 * 生成ExpressPriceMaping记录
	 * @param ExpressPriceMaping
	 * */
	public void insert(ExpressPriceMaping priceMaping);
	
	/**
	 * 更新已有ExpressPriceMaping记录
	 * @param ExpressPriceMaping
	 * */
	public void updatePrice(ExpressPriceMaping priceMaping);
	
	
}
