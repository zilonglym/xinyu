package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;

@MyBatisRepository
public interface ShipOrderDao {
	
	
	void updateShipOrder(Map<String, Object> params);
	
	List<ShipOrderDetail> getShipOrderDetailList(Map<String,Object> params);
	ShipOrderDetail getShipOrderDetail(Map<String,Object> params);
	ShipOrder getSendShipOrderByTradeId(Long tid);
	ShipOrder getShipOrder(Long id);
	Long getSendOrderIdByTradeId(Long tradeId);
	
	void setSumEntryOrderDetail(Long detailId,long num,long actualnum);
	
	void deleteOrder(Long orderId);
	void deleteDetailByOrderId(Long orderId);
	void deleteDetail(Long detailId);
	void sendEntryOrder(Long orderId);
	void setOrderStatus(Long orderId, String status);
	
	void setTradeStatus(Long orderId, String status);
	void setSendOrderExpress(Map<String,String> orders);
	
	void setSendOrderExpressById(Map<String,String> orders);
	
	void chooseExpress(Long orderId, String expressCompany);
	void increaseEntryOrderDetail(Long detailId, long num);
	List<ShipOrderDetail> shipOrderDetailbyList(Map<String,Object> params);
	void cancelShipOrder(Map<String,Object> params);
	Long getEntryOrderDetail(Long orderId, Long itemId);
	
	
	List<ShipOrder> findEntryOrderOnWay(Map<String,Object> params);
	List<ShipOrder> findReturnOrderOnWay();
	List<ShipOrder> findSendOrderWaits(Map<String,Object> params);
	
	List<ShipOrder> findTradeBatchSendOrderWaits(Map<String,Object> params);
	
	List<ShipOrder> findSendOrderWaitsOk(Map<String,Object> params);
	
	List<ShipOrder> findSendOrderByStatus(Long centroId, String status, int rownum);
	List<ShipOrder> findSendOrderSignWaits(Map<String,Object> params);
	List<ShipOrder> findSendOrders(Long[] orderIds);
	List<Map<String,Object>> findSendOrdersGroup(Long[] orderIds);
	List<ShipOrder> findSendOrderByQ(String q);
	
	List<ShipOrder> selectShipOrderByIdCodeAndtype(Map<String,Object> params);
	
	List<ShipOrder> selectShipOrderByList(Map<String,Object> params);
	
	void updateDetailQuantity(Map<String,Object> params);
	
	void updateShipOrderCentro(Map<String,Object> params);
	
	void updateShipOrderitems(Map<String,Object> params);
	
	void updateShipOrderStatus(Map<String,Object> params);
	
	void updateShipOrderTradeId(Map<String,Object> params);
	List<ShipOrderDetail> getShipOrderDetailByOrderId(Long orderId);
	List<ShipOrder> findSendOrderByStatus(Long centroId, String status, Long userId);
	List<ShipOrder> findSendOrderByStatusAndUserId(Long centroId, String status, Long userId);
	/**
	 * 查询所有的订单数据
	 * @param params
	 * @return
	 */
	List<ShipOrder> findSendOrderList(Map<String,Object> params);
	/**
	 * 统计分页总记录
	 * @param params
	 * @return
	 */
	long findTradeBatchSendOrderWaitsCount(Map<String,Object> params);
	/**
	 * 查询记录总数
	 * @param params
	 * @return
	 */
	long findSendOrderListCount(Map<String,Object> params);
	
	void updateShipOrderWeight(Long orderId,Double weight);
	long getTotalResults(Map<String, Object> params);
	void updateShipOrderTradeBatchId(Map<String,Object> params);
	void updateShipOrderByTradeId(Map<String, Object> params);
	List<ShipOrder> findShipOrdersByParams(Map<String, Object> params);
	
	/**
	 * 更新运费
	 * @param id
	 * @param totalPrice
	 * */
	void updateShipOrderPrice(Long id, Double totalPrice);
	
	List<ShipOrder> findeSendOrderByList(Map<String, Object> params);
	
	int getSendOrderCount(Map<String, Object> params);
	
	long findSendOrderImportCount();
	
	List<ShipOrder> findSendOrderImport();
	
	void updateImportShipOrderSub();
	
	List<ShipOrder> findSendOrderWaitsByPages(Map<String, Object> params);
	
	void updateShipOrderSourcePlatformCodeById (Map<String, Object> params);
	
	void updateShipOrderDetailNum (Map<String, Object> params);
	
	void deleteShipOrderDetailNum (Map<String, Object> params);

	void updateShipOrderStatusSetOrdernoIsNull(Map<String,Object> params);
	
	ShipOrder findShipOrderByExpressOrderno(String  expressOrderno);
	
	/**
	 * 更新remark
	 * @param orderId
	 * @param remark
	 * */
	void updateRemark(Map<String,Object> params);
	
	Long getCountByDate(Map<String,Object> params);
	
	Long getSuccessCountByDate(Map<String,Object> params);
	
	Long getCheckSuccessCountByDate(Map<String,Object> params);
	
	Long getCheckFailCountByDate(Map<String,Object> params);
	
	List<ShipOrder> selectShipOrderNotEXISTS(Map<String, Object> params);

	void updateShipOrderById(ShipOrder order);
	/**
	 * 工作组取订单商品信息
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> getShipOrderItemByWorkGroup(Map<String,Object> params);
	
	/**
	 * 查询旧表数据(其它地方不要调用) BEGIN
	 */
	
	long findSendOrderOldListCount(Map<String,Object> params);
	List<ShipOrder> findSendOrderOldList(Map<String,Object> params);
	
	/**
	 * 查询符合条件的订单数量
	 * */
	int getShipOrderCount(Map<String, Object> params);
}
