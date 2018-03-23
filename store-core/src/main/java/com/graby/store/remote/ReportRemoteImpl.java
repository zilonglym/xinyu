package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.CompanyProfits;
import com.graby.store.entity.MonthProfits;
import com.graby.store.entity.Settlement;
import com.graby.store.entity.ShipOrder;
import com.graby.store.service.finance.CompanyProfitsService;
import com.graby.store.service.finance.MonthProfitsService;
import com.graby.store.service.finance.SettlementService;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.report.ReportService;

@RemotingService(serviceInterface = ReportRemote.class, serviceUrl = "/report.call")
public class ReportRemoteImpl implements ReportRemote {
	
	@Autowired
	private ReportService reportService;//报表
	
	@Autowired
	private InventoryService inventoryService;//库存
	
	@Autowired
	private CompanyProfitsService profitsSevice;//物流利润明细
	
	@Autowired
	private MonthProfitsService monthService;//公司月利润明细
	
	@Autowired
	private SettlementService settlemenService;//公司结算明细
	
	/**
	 * 出入库明细统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> getInventoryRecordReport(Map<String, Object> map) {
		return inventoryService.getInventoryRecordReport(map);
	}
	
	/**
	 * 库存明细统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> getInventoryReport(Map<String, Object> map) {
		return inventoryService.getInventoryReport(map);
	}
	
	/**
	 * 统计发货单
	 * @param parameters
	 * @return
	 */
	@Override
	public List<Map<String, Object>> shipCount(Map<String, Object> parameters) {
		return reportService.shipCount(parameters);
	}

	/**
	 * 卖出商品统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<Map<String, Object>> sumUserSellouts(long userId, String startDate, String endDate) {
		return reportService.sumUserSellouts(userId, startDate, endDate);
	}
	
	/**
	 * 查询用户发货单
	 * @param parameters
	 * @return
	 */
	@Override
	public List<ShipOrder> findOrderSellout(Map<String, Object> parameters) {
		return reportService.findOrderSellout(parameters);
	}
	
	/**
	 * 分页查询库存明细
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 */
	@Override
	public List<Map<String, Object>> getInventoryReportDetails(Map<String, Object> params, int page, int rows) {
		return inventoryService.getInventoryReportDetails(params,page,rows);
	}
	
	/**
	 * 查询库存明细总记录数
	 * @param params
	 * @return int
	 */
	@Override
	public int getTotalResult(Map<String, Object> params) {
		return inventoryService.getTotalResult(params);
	}

	/**
	 * 查询库存总记录数
	 * @param params
	 * @return int
	 */
	@Override
	public int getInventoryRecordTotal(Map<String, Object> params) {
		return inventoryService.getInventoryRecordTotal(params);
	}

	/**
	 * 分页查询库存出入库明细
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 */
	@Override
	public List<Map<String, Object>> getInventoryRecordReportByPages(Map<String, Object> params, int page, int rows) {
		return inventoryService.getInventoryRecordReportByPages(params,page,rows);
	}
	
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
	public int getCompanyProfitsTotalResult(Map<String, Object> params) {
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
	
	/**
	 * 条件查询公司月利润明细（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<MonthProfits> findMonthProfits(Map<String, Object> params,int page,int rows) {
		return this.monthService.findMonthProfits(params,page,rows);
	}
	
	/**
	 * 总记录条数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getMonthProfitsTotalResult(Map<String, Object> params) {
		return this.monthService.getTotalResult(params);
	}
	
	/**
	 * 更新记录
	 * @param MonthProfits
	 * */
	@Override
	public void update(MonthProfits monthProfits) {
		this.monthService.update(monthProfits);
	}
	
	/**
	 * 新增记录
	 * @param MonthProfits
	 * */
	@Override
	public void save(MonthProfits monthProfits) {
		this.monthService.save(monthProfits);
	}
	
	/**
	 * 条件查询公司月利润明细（单个）
	 * @param params
	 * @return MonthProfits
	 * */
	@Override
	public MonthProfits findMonthProfitsByParams(Map<String, Object> params) {
		return this.monthService.findMonthProfitsByParams(params);
	}
	
	/**
	 * 条件查询公司月利润明细（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<MonthProfits> getMonthProfits(Map<String, Object> params) {
		return this.monthService.getMonthProfits(params);
	}
	
	/**
	 * 结算记录条件查询（单个）
	 * @param params
	 * @return Settlement
	 * */
	@Override
	public Settlement findSettlemnetByParam(Map<String, Object> params) {
		return this.settlemenService.findSettlemnetByParam(params);
	}
	
	/**
	 * 结算记录条件查询（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Settlement> findSettlemnets(Map<String, Object> params,int page,int rows) {
		return this.settlemenService.findSettlemnets(params,page,rows);
	}
	
	/**
	 * 结算记录总数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getSettlementTotalResult(Map<String, Object> params) {
		return this.settlemenService.getTotalResult(params);
	}

	/**
	 * 更新结算记录
	 * @param Settlement
	 * */
	@Override
	public void update(Settlement settlement) {
		this.settlemenService.update(settlement);
	}
	
	/**
	 * 新增结算记录
	 * @param Settlement
	 * */
	@Override
	public void save(Settlement settlement) {
		this.settlemenService.save(settlement);
	}
	
	/**
	 * 结算记录条件查询（多个）
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Settlement> getSettlemnets(Map<String, Object> params) {
		return this.settlemenService.getSettlemnets(params);
	}

	@Override
	public List<Map<String, Object>> orderCount(Map<String, Object> params) {
		return this.reportService.orderCount(params);
	}

	/**
	 * 订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	@Override
	public Long findShipOrderNumByParams(Map<String, Object> params){
		return reportService.findShipOrderNumByParams(params);
	}
	
	/**
	 * 出库订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	@Override
	public Long findCheckOutNumByParams(Map<String, Object> params){
		return reportService.findCheckOutNumByParams(params);
	}
	
	/**
	 * 未出库或出库失败订单(马)
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> findUnfinishOrder(Map<String, Object> params) {	
		return this.reportService.findUnfinishOrder(params);
	}
	
	/**
	 * 未出库统计
	 * @param map
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> findNotExist(Map<String, Object> params) {
		return this.reportService.findNotExist(params);
	}

}
