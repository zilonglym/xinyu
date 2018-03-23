package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.CompanyProfits;
import com.graby.store.entity.MonthProfits;
import com.graby.store.entity.Settlement;
import com.graby.store.entity.ShipOrder;

/**
 * 报表统计
 * */
public interface ReportRemote {
	
	/**
	 * 出入库明细统计
	 * @param map
	 * @return list
	 * */
	List<Map<String, Object>> getInventoryRecordReport(Map<String, Object> map);
	
	/**
	 * 库存明细统计
	 * @param map
	 * @return list
	 * */
	List<Map<String, Object>> getInventoryReport(Map<String, Object> map);
	/**
	 * 统计发货单
	 * @param parameters
	 * @return
	 */
	public List<Map<String, Object>> shipCount(Map<String, Object> parameters);
	
	/**
	 * 卖出商品统计
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> sumUserSellouts(long userId, String startDate, String endDate);
	
	/**
	 * 查询用户发货单
	 * @param parameters
	 * @return
	 */
	public List<ShipOrder> findOrderSellout(Map<String, Object> parameters);
	/**
	 * 分页查询库存明细
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 */
	List<Map<String, Object>> getInventoryReportDetails(Map<String, Object> params, int page, int rows);
	/**
	 * 查询库存明细总记录数
	 * @param params
	 * @return int
	 */
	int getTotalResult(Map<String, Object> params);
	/**
	 * 查询库存总记录数
	 * @param params
	 * @return int
	 */
	int getInventoryRecordTotal(Map<String, Object> params);
	/**
	 * 分页查询库存出入库明细
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 */
	List<Map<String, Object>> getInventoryRecordReportByPages(Map<String, Object> params, int page, int rows);

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
	public int getCompanyProfitsTotalResult(Map<String, Object> params);
	
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
	
	/**
	 * 条件查询公司月利润明细（多个分页）
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<MonthProfits> findMonthProfits(Map<String,Object> params,int page,int rows);

	/**
	 * 总记录条数
	 * @param params
	 * @return int
	 * */
	public int getMonthProfitsTotalResult(Map<String,Object> params);
	
	/**
	 * 更新记录
	 * @param MonthProfits
	 * */
	public void update(MonthProfits monthProfits);
	
	/**
	 * 新增记录
	 * @param MonthProfits
	 * */
	public void save(MonthProfits monthProfits);
	
	/**
	 * 条件查询公司月利润明细（单个）
	 * @param params
	 * @return MonthProfits
	 * */
	public MonthProfits findMonthProfitsByParams(Map<String, Object> params);
	

	/**
	 * 条件查询公司月利润明细（多个）
	 * @param params
	 * @return list
	 * */
	public List<MonthProfits> getMonthProfits(Map<String, Object> params);
	
	/**
	 * 结算记录条件查询（单个）
	 * @param params
	 * @return Settlement
	 * */
	public Settlement findSettlemnetByParam(Map<String,Object> params);
	/**
	 * 结算记录条件查询（多个分页）
	 * @param params
	 * @param rows
	 * @param page
	 * @return list
	 * */
	public List<Settlement> findSettlemnets(Map<String,Object> params,int page,int rows);
	/**
	 * 结算记录总数
	 * @param params
	 * @return int
	 * */
	public int getSettlementTotalResult(Map<String,Object> params);
	/**
	 * 更新结算记录
	 * @param Settlement
	 * */
	public void update(Settlement settlement);
	/**
	 * 新增结算记录
	 * @param Settlement
	 * */
	public void save(Settlement settlement);
	/**
	 * 结算记录条件查询（多个）
	 * @param params
	 * @return list
	 * */
	public List<Settlement> getSettlemnets(Map<String, Object> params);

	public List<Map<String, Object>> orderCount(Map<String, Object> params);


	/**
	 * 订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	Long findShipOrderNumByParams(Map<String, Object> params);
	
	/**
	 * 出库订单总数查询(马)
	 * @param map
	 * @return long
	 * */
	Long findCheckOutNumByParams(Map<String, Object> params);
	
	
	/**
	 * 未出库或出库失败订单(马)
	 * @param map
	 * @return list
	 * */
	public List<Map<String, Object>> findUnfinishOrder(Map<String, Object> params);

	/**
	 * 未出库统计
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findNotExist(Map<String, Object> params);

	
}
