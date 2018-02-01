package com.graby.store.service.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CompanyProfitsDao;
import com.graby.store.dao.mybatis.ReportDao;
import com.graby.store.entity.ShipOrder;

/**
 * 统计报表
 * */
@Component
@Transactional
public class ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	/**
	 *条件查询统计销售出库数
	 *@param userId
	 *@param startDate
	 *@param endDate
	 *@return
	 * */
	public List<Map<String, Object>> sumUserSellouts(long userId, String startDate, String endDate) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate",startDate);
		p.put("endDate", endDate);
		return reportDao.sumUserSellouts(p);
	}
	

	/**
	 * 查询用户发货单
	 * @param parameters
	 * @return
	 */
	public List<ShipOrder> findOrderSellout(Map<String, Object> parameters) {
		return reportDao.findOrderSellout(parameters);
	}
	
	/**
	 * 查询用户发货单记录数
	 * @param parameters
	 * @return int
	 */
	public long findOrderSelloutCount(Map<String, Object> parameters) {
		return reportDao.findOrderSelloutCount(parameters);
	}
	
	/**
	 * 发货统计记录数
	 * @param parameters
	 * @return int
	 */
	public List<Map<String, Object>> shipCount(Map<String, Object> parameters) {
		return reportDao.shipCount(parameters);
	}

	
	public List<Map<String, Object>> orderCount(Map<String, Object> params) {
		return reportDao.orderCount(params);
	}

	/**
	 * 订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	public Long findShipOrderNumByParams(Map<String, Object> params){
		return reportDao.findShipOrderNumByParams(params);
	}
	
	/**
	 * 出库订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	public Long findCheckOutNumByParams(Map<String, Object> params){
		return reportDao.findCheckOutNumByParams(params);
	}


	/**
	 * 未出库或出库失败订单（马）
	 * @param map
	 * @return list
	 * */
	public List<Map<String, Object>> findUnfinishOrder(Map<String, Object> params) {
		return reportDao.findUnfinishOrder(params);
	}

}
