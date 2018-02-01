package com.xinyu.service.trade;

import java.util.List;
import java.util.Map;

import com.xinyu.model.trade.ShipOrderReport;
import com.xinyu.service.common.BaseService;

public interface ShipOrderReportService extends BaseService {

	public void insertShipOrderReport(ShipOrderReport report);

	public List<ShipOrderReport> getShipOrderReportByList(Map<String, Object> params);

	public void updateShipOrderReport(ShipOrderReport report);
}
