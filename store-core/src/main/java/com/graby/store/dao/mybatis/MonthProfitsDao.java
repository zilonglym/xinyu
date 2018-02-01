package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.MonthProfits;
/**
 * 公司月利润明细
 * */
@MyBatisRepository
public interface MonthProfitsDao {
	
	/**
	 * 条件查询公司月利润明细（多个）
	 * @param params
	 * @return list
	 * */
	public List<MonthProfits> findMonthProfits(Map<String,Object> params);
	
	/**
	 * 总记录条数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String,Object> params);
	
	/**
	 * 更新记录
	 * @param MonthProfits
	 * */
	public void update(MonthProfits monthProfits);
	
	/**
	 * 新增记录
	 * @param MonthProfits
	 * */
	public void save(MonthProfits monthProfits);
	
	/**
	 * 条件查询公司月利润明细（单个）
	 * @param params
	 * @return MonthProfits
	 * */
	public MonthProfits findMonthProfitsByParams(Map<String, Object> params);
}
