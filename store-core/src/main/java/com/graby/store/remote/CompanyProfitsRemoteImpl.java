package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CompanyProfits;
import com.graby.store.service.finance.CompanyProfitsService;

/**
 * 商家月利润明细
 * */
@RemotingService(serviceInterface = CompanyProfitsRemote.class, serviceUrl = "/companyProfits.call")
public class CompanyProfitsRemoteImpl implements CompanyProfitsRemote{
	
	@Autowired
	private CompanyProfitsService profitsSevice;
	
	/**
	 * 商家月利润明细条件查询（单个）
	 * @param params
	 * @return CompanyProfits
	 * */
	@Override
	public CompanyProfits findCompanyProfitsByParam(Map<String, Object> params) {
		return this.profitsSevice.findCompanyProfitsByParam(params);
	}
	
	/**
	 * 商家月利润明细条件查询（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<CompanyProfits> findCompanyProfits(Map<String, Object> params, int page, int rows) {
		return this.profitsSevice.findCompanyProfits(params,page,rows);
	}
	
	/**
	 * 商家月利润明细总记录条数
	 * @param params
	 * @return int 
	 * */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return this.profitsSevice.getTotalResult(params);
	}
	
	/**
	 * 更新记录
	 * @param CompanyProfits
	 * */
	@Override
	public void update(CompanyProfits companyProfits) {
		this.profitsSevice.update(companyProfits);
	}
	
	/**
	 * 新增记录
	 * @param CompanyProfits
	 * */
	@Override
	public void save(CompanyProfits companyProfits) {
		this.profitsSevice.save(companyProfits);
	}
	
	/**
	 * 商家月利润明细条件查询（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<CompanyProfits> findCompanyProfitsList(Map<String, Object> params) {
		return this.profitsSevice.findCompanyProfitsList(params);
	}

}
