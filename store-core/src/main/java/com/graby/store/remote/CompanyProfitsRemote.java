package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CompanyProfits;

/**
 * 商家月利润明细
 * */
public interface CompanyProfitsRemote {
	
	/**
	 * 商家月利润明细条件查询（单个）
	 * @param params
	 * @return CompanyProfits
	 * */
	public CompanyProfits findCompanyProfitsByParam(Map<String, Object> params);
	
	/**
	 * 商家月利润明细条件查询（多个分页）
	 * @param params
	 * @param rows
	 * @param page
	 * @return list
	 * */
	public List<CompanyProfits> findCompanyProfits(Map<String, Object> params,int page,int rows);
	
	/**
	 * 商家月利润明细总记录条数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params);
	
	/**
	 * 更新记录
	 * @param CompanyProfits
	 * */
	public void update(CompanyProfits companyProfits);
	
	/**
	 * 新增记录
	 * @param CompanyProfits
	 * */
	public void save(CompanyProfits companyProfits);
	
	/**
	 * 商家月利润明细条件查询（多个）
	 * @param params
	 * @return list
	 * */
	public List<CompanyProfits> findCompanyProfitsList(Map<String, Object> params);
}
