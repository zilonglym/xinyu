package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.MonthProfits;

/**
 * 公司月利润统计明细
 * */
public interface MonthProfitsRemote {
	/**
	 * 条件查询公司月利润明细（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<MonthProfits> findMonthProfits(Map<String,Object> params,int page,int rows);

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
	

	/**
	 * 条件查询公司月利润明细（多个）
	 * @param params
	 * @return list
	 * */
	public List<MonthProfits> getMonthProfits(Map<String, Object> params);
}
