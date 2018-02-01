package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeMapping;
import com.graby.store.entity.TradeOrder;

@MyBatisRepository
public interface TradeDao {
	/**
	 * 修改交易收件人信息
	 * */
	void updateTrade(Map<String, Object> params);
	
	
	Trade findTradeByLgAging(String lgAging);
	
	
	List<Trade> findTradeListByLgAging(String[] ids);
	
	/**
	 * 修改订单明细数量信息 
	 * ${num}
	 * ${trade_id}
	 * ${item_id}
	 * @param params
	 */
	void updateTradeOrderNum(Map<String, Object> params);
	
	void  deleteTradeOrderNum (Map<String, Object> params);
	/**
	 * 获取交易
	 * @param id
	 * @return
	 */
	public Trade getTrade(Long id);
	
	/**
	 * 关联淘宝交易ID和系统交易ID
	 * @param tid
	 * @param tradeId
	 */
	public void createTradeMapping(TradeMapping tradeMapping);
	
	/**
	 * 更新订单映射表状态。
	 * @param tradeId 系统交易号
	 * @param status
	 */
	public void updateTradeMappingStatus(Long tradeId, String status);
	
	/**
	 * 查询淘宝交易ID是否已存在系统交易ID
	 * @param tid 淘宝交易ID
	 * @return 系统交易ID
	 */
	public Long getRelatedTradeId(Long tid);
	
	/**
	 * 查询淘宝交易ID关联的Mapping
	 * @param tid
	 * @return
	 */
	public TradeMapping getRelatedMapping(Long tid);
	
	/**
	 * 根据系统交易ID查询淘宝交易ID
	 * @param tradeId
	 * @return
	 */
	public List<Long> getRelatedTid(Long tradeId);
	
	/**
	 * 查询最近200条待处理订单(等待物流通审核)
	 * TODO 多仓库
	 * @return
	 */
	public List<Trade> findWaitAuditTrades();
	
	/**
	 * 查询最近200条待处理订单(等待物流通审核)
	 * TODO 多仓库
	 * @return
	 */
	public List<Trade> findWaitAuditTradesBy(Map<String, Object> map);
	
	/**
	 * 查询可合并的的待处理订单
	 * @param map
	 * @return
	 */
	public List<Trade> findSplitedTrades(Map<String,Object> params);
	
	/**
	 * 查询待处理订单城市列表
	 * @return
	 */
	public List<Map<String,String>> findWaitAuditCitys(Map<String,Object> params);
	
	/**
	 * 查询所有待审核交易订单
	 * @param centroId
	 * @return
	 */
	public List<Long> findWaitAuditTradeIds(Long centroId);
	
	/**
	 * 查询未完成交易订单
	 */
	public List<Trade> findUnfinishedTrades(Map<String,Object> params);
	public long countUnfinishedTrades();
	
	
	/**
	 * 设置订单状态
	 * @param tradeId
	 * @param status
	 */
	public void updateTradeStatus(Long tradeId, String status);
	
	
	/**
	 * 设置订单商品总重量
	 * */
	public void updateTradeWeight(Long tradeId, Double weight);
	
	/**
	 * 设置订单合并编码
	 * @param tradeId
	 * @param status
	 */
	public void updateTradeMergeHash(Long tradeId, String hash);
	
	/**
	 * 获取用户订单总数
	 * @param userId
	 * @param status
	 * @return
	 */
	public long getTotalResults(Long userId, String status);
	
	public long getTotalResultsTBnumber(Long userId, String tbnumber);
	
	
	
	/**
	 * 获取用户订单列表
	 * @param userId
	 * @param status
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Trade> getTrades(Long userId, String status, long start, long offset);
	
	public List<Trade> getTradesTBnumber(Long userId, String tbnumber, long start, long offset);
	
	
	// ** 删除交易信息：出货单明细、出货单、交易关联、交易订单 */
	public void deleteShipOrderDetail(Long tradeId);
	public void deleteShipOrder(Long tradeId);
	public void deleteTradeMapping(Long tradeId);
	public void deleteTradeOrder(Long tradeId);
	public void deleteTrade(Long tradeId);

	public List<TradeOrder> fetchTradeOrders(Long id);
	
	int getTotalResult(Map<String, Object> params);
	
	List<Trade> findTradesBy(Map<String, Object> params);

	int findSplitedTradesCount(Map<String, Object> params);
	
	List<Trade> findSplitedTradesByPage(Map<String, Object> params);
	
	/**
	 * 获取待处理订单列表
	 * @param params
	 * @return list
	 */
	List<Trade> findWaitAuditTradesByPages(Map<String, Object> params);
	
	String getItemsByTrade(int tradeId);
	
	/**
	 * 获取未审核订单数量按商家分类统计
	 * @return list
	 * */
	List<Map<String,Object>> getWaitAuditTradesTotal();


	List<Trade> findWaitAuditTradesByLgAging(Map<String, Object> params);


	void updateTradeStatusAndOtherNo(Long tradeId, String tradeWaitCentroAudit, long parseLong);


}
