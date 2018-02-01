package com.graby.store.service.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.MonthProfitsDao;
import com.graby.store.entity.MonthProfits;

/**
 * 公司月利润
 * */
@Component
@Transactional
public class MonthProfitsService {
	
	@Autowired
	private MonthProfitsDao monthDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<MonthProfits> findMonthProfits(Map<String, Object> params,int page,int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start",start);
		params.put("offset",offset);
		List<MonthProfits> monthProfits=this.monthDao.findMonthProfits(params);
		return monthProfits;
	}
	
	/**
	 * 总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params) {
		return this.monthDao.getTotalResult(params);
	}
	
	/**
	 * 更新
	 * @param MonthProfits
	 * */
	public void update(MonthProfits monthProfits) {
		this.monthDao.update(monthProfits);
	}
	
	/**
	 * 新增
	 * @param MonthProfits
	 * */
	public void save(MonthProfits monthProfits) {
		this.monthDao.save(monthProfits);
	}
	
	/**
	 * 条件查询（单个）
	 * @param params
	 * @return
	 * */
	public MonthProfits findMonthProfitsByParams(Map<String, Object> params) {
		return this.monthDao.findMonthProfitsByParams(params);
	}
	
	/**
	 * 条件查询（多个）
	 * @param params
	 * @return
	 * */
	public List<MonthProfits> getMonthProfits(Map<String, Object> params) {
		return this.monthDao.findMonthProfits(params);
	}

}
