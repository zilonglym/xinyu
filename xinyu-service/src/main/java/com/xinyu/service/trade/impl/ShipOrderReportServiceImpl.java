package com.xinyu.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xinyu.dao.trade.ShipOrderReportDao;
import com.xinyu.model.trade.ShipOrderReport;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.trade.ShipOrderReportService;


@Repository("shipOrderReportServiceImpl")
public class ShipOrderReportServiceImpl extends BaseServiceImpl implements ShipOrderReportService {

	@Autowired
	private ShipOrderReportDao shipOrderReportDao;
	
	@Override
	public void insertShipOrderReport(ShipOrderReport report) {
		// TODO Auto-generated method stub
		this.shipOrderReportDao.insertShipOrderReport(report);
	}

	@Override
	public List<ShipOrderReport> getShipOrderReportByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderReportDao.getShipOrderReportByList(params);
	}

	@Override
	public void updateShipOrderReport(ShipOrderReport report) {
		// TODO Auto-generated method stub
		this.shipOrderReportDao.updateShipOrderReport(report);
	}

}
