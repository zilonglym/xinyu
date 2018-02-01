package com.graby.store.service.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.SettlementDao;
import com.graby.store.entity.Settlement;

/**
 * 公司结算
 * */
@Component
@Transactional
public class SettlementService {

	@Autowired
	private SettlementDao settlementDao;
	
	/**
	 * 条件查询（单个）
	 * @param params
	 * @return
	 * */
	public Settlement findSettlemnetByParam(Map<String, Object> params) {
		return this.settlementDao.findSettlemnetByParam(params);
	}
	
	/**
	 * 分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 * */
	public List<Settlement> findSettlemnets(Map<String, Object> params, int page, int rows) {
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start",start);
		params.put("offset",offset);
		List<Settlement> settlements=this.settlementDao.findSettlemnets(params);
		return settlements;
	}
	
	/**
	 * 总记录数
	 * @param params
	 * @return int
	 * */
	public int getTotalResult(Map<String, Object> params) {
		return this.settlementDao.getTotalResult(params);
	}
	

	/**
	 * 更新
	 * @param Settlement
	 * */
	public void update(Settlement settlement) {
		this.settlementDao.update(settlement);
	}
	
	/**
	 * 新增
	 * @param Settlement
	 * */
	public void save(Settlement settlement) {
		this.settlementDao.save(settlement);
	}
	
	/**
	 * 条件查询（多个）
	 * @param params
	 * @return
	 * */
	public List<Settlement> getSettlemnets(Map<String, Object> params) {
		return this.settlementDao.findSettlemnets(params);
	}
	
}
