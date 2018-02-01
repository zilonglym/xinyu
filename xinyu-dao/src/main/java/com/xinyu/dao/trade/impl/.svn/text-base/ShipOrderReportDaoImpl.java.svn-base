package com.xinyu.dao.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.trade.ShipOrderReportDao;
import com.xinyu.model.trade.ShipOrderReport;


@Repository("shipOrderReportDaoImpl")
public class ShipOrderReportDaoImpl extends BaseDaoImpl implements ShipOrderReportDao {

	
	private String statement="com.xinyu.dao.shipOrderReportDao.";
	
	@Override
	public void insertShipOrderReport(ShipOrderReport report) {
		// TODO Auto-generated method stub
		this.insert(statement+"insertShipOrderReport", report);
	}

	@Override
	public List<ShipOrderReport> getShipOrderReportByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<ShipOrderReport>) this.selectList(statement+"getShipOrderReportByList", params);
	}

	@Override
	public void updateShipOrderReport(ShipOrderReport report) {
		// TODO Auto-generated method stub
		this.update(statement+"updateShipOrderReport", report);
	}

}
