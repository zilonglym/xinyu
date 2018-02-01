package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.MonthProfits;
import com.graby.store.service.finance.MonthProfitsService;

/**
 * 公司月利润统计明细
 * */
@RemotingService(serviceInterface = MonthProfitsRemote.class, serviceUrl = "/month.call")
public class MonthProfitsRemoteImpl implements MonthProfitsRemote{
	
	@Autowired
	private MonthProfitsService monthService;
	
	/**
	 * 条件查询公司月利润明细（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<MonthProfits> findMonthProfits(Map<String, Object> params,int page,int rows) {
		return this.monthService.findMonthProfits(params,page,rows);
	}
	
	/**
	 * 总记录条数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.monthService.getTotalResult(params);
	}
	
	/**
	 * 更新记录
	 * @param MonthProfits
	 * */
	@Override
	public void update(MonthProfits monthProfits) {
		this.monthService.update(monthProfits);
	}
	
	/**
	 * 新增记录
	 * @param MonthProfits
	 * */
	@Override
	public void save(MonthProfits monthProfits) {
		this.monthService.save(monthProfits);
	}
	
	/**
	 * 条件查询公司月利润明细（单个）
	 * @param params
	 * @return MonthProfits
	 * */
	@Override
	public MonthProfits findMonthProfitsByParams(Map<String, Object> params) {
		return this.monthService.findMonthProfitsByParams(params);
	}
	
	/**
	 * 条件查询公司月利润明细（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<MonthProfits> getMonthProfits(Map<String, Object> params) {
		return this.monthService.getMonthProfits(params);
	}

}
