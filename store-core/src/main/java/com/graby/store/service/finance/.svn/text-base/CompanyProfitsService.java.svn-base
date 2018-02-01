package com.graby.store.service.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CompanyProfitsDao;
import com.graby.store.entity.CompanyProfits;

/**
 * 各商家利润
 * */
@Component
@Transactional
public class CompanyProfitsService {
	
	@Autowired
	private CompanyProfitsDao profitsDao;
	
	/**
	 * 条件查询（单个）
	 * @param params
	 * @return
	 * */
	public CompanyProfits findCompanyProfitsByParam(Map<String, Object> params) {
		return this.profitsDao.findCompanyProfitsByParam(params);
	}
	
	/**
	 * 条件查询（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<CompanyProfits> findCompanyProfits(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start", start);
		params.put("offset",offset);
		List<CompanyProfits> profits=this.profitsDao.findCompanyProfits(params);
		return profits;
	}
	
	/**
	 * 总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params) {
		return this.profitsDao.getTotalResult(params);
	}
	
	/**
	 * 更新
	 * @param CompanyProfits
	 * */
	public void update(CompanyProfits companyProfits) {
		this.profitsDao.update(companyProfits);
	}

	/**
	 * 新增
	 * @param CompanyProfits
	 * */
	public void save(CompanyProfits companyProfits) {
		this.profitsDao.save(companyProfits);
	}
	
	/**
	 * 条件查询（多个个）
	 * @param params
	 * @return
	 * */
	public List<CompanyProfits> findCompanyProfitsList(Map<String, Object> params) {
		return this.profitsDao.findCompanyProfits(params);
	}

}
