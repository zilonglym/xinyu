package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ExpressPrice;
import com.graby.store.entity.ExpressPriceMaping;

/**
 * 快递费用服务Dao接口
 * */
@MyBatisRepository
public interface ExpressPriceDao {
	
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
	 * @return list
	 * */
	public List<ExpressPrice> findExpressPrices(Map<String, Object> params);

	/**
	 * 根据param统计总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params);
	
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
