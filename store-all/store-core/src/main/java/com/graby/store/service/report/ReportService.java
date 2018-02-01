package com.graby.store.service.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ReportDao;
import com.graby.store.entity.ShipOrder;


@Component
@Transactional(readOnly = true)
public class ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
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
	
	public long findOrderSelloutCount(Map<String, Object> parameters) {
		return reportDao.findOrderSelloutCount(parameters);
	}
	
	public List<Map<String, Object>> shipCount(Map<String, Object> parameters) {
		return reportDao.shipCount(parameters);
	}
	
	
}
