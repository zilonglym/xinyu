
package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.service.inventory.AccountEntryArray;
import com.taobao.api.ApiException;

/**
 * 货单服务
 * serviceUrl = "/ship.call"
 */
public interface ShipOrderRemote {
	 Map<String,Object>  copyShipOrder(Long  shipOrderId);
	
	/**
	 * shipOrder表的更新操作，以后所有的shipOrder表的更新操作请都在这个SQL里面加判断条件，
	 * 有特殊情况请先找我
	 * @param params
	 */
	void updateShipOrder(Map<String,Object> params);
	
	List<ShipOrder> selectShipOrderNotEXISTS(Map<String,Object> params);
	
	List<ShipOrder> findShipOrdersByParams(Map<String,Object> params);
	
	void updateShipOrderWeight(Long orderId,Double weight);
	
	/**
	 * 订单分单
	 */
	Map<String,Object> splitShipOrder(Map<String,Object> param);
	/**
	 * 快捷返单
	 * @param type
	 * @param id
	 * @param num
	 * @return
	 */
	Map<String,Object> splitShipOrderOperation(String type,Long tradeId,Long num);
	/**
	 * 查询所有在途入库单
	 */
	public List<ShipOrder> findEntryOrderOnWay(Map<String,Object> params);

	/**
	 * 保存入库单
	 * 
	 * @param order
	 */
	public void saveEntryOrder(ShipOrder order);

	/**
	 * 保存发货单明细
	 * 
	 * @param orderId
	 *            入库单号
	 * @param itemId
	 *            商品ID
	 * @param num
	 *            商品数量
	 */
	public void saveShipOrderDetail(Long orderId, Long itemId, long num);

	/**
	 * 删除发货单
	 * 
	 * @param orderId
	 */
	public void deleteShipOrder(Long orderId);

	/**
	 * 删除发货单明细
	 * 
	 * @param id
	 */
	public void deleteShipOrderDetail(Long id);

	/**
	 * 返回用户的入库单
	 * 
	 * @param userid
	 * @param status
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<ShipOrder> findEntrys(Long userid, String status, int page, int pageSize);

	/**
	 * 获取发货单
	 * 
	 * @param id
	 * @return
	 */
	public ShipOrder getShipOrder(Long id);

	/**
	 * 确认发送入库单
	 * 
	 * @param id
	 */
	public boolean sendEntryOrder(Long id);

	/**
	 * 接收发送入库单
	 * 
	 * @param id
	 * @param entrys
	 */
	public void recivedEntryOrder(Long id, List<AccountEntryArray> entrys);

	/**
	 * 设置快递公司
	 * @param orderId
	 * @param expressCompany
	 */
	public void chooseExpress(Long orderId, String expressCompany);
	
	/**
	 * 查询所有出库单(未处理)
	 * 
	 * @return
	 */
	public List<ShipOrder> findSendOrderWaits(Map<String,Object> params);
	
	
	/**
	 * 批次查询所有出库单(未处理)
	 * 
	 * @return
	 */
	public List<ShipOrder> findTradeBatchSendOrderWaits(Map<String,Object> params);
	
	public Page<ShipOrder> findTradeBatchSendOrderWaits(int page, int i, Map<String, Object> params);
	
	/**
	 * 查询所有出库单(已获取单号)
	 * 
	 * @return
	 */
	 //public List<ShipOrder> findSendOrderWaitsOk(Map<String,Object> params);
	
	/**
	 * 分组查询所有未处理出库单
	 * @param centroId 仓库ID
	 * @return GroupMap 结构
	 *   key=快递公司编码
	 *   value=归类的出库单列表
	 */
	public List<ShipOrder> findGroupSendOrderWaits(Long centroId);
	

	
	/**
	 * 运单打印成功，更新出库单物流信息。
	 * @param orderMaps 已完成打印的出货单
	 * Map结构:
	 * id=出货单ID
	 * expressCompany=运输公司CODE
	 * expressOrderno=运单号
	 * 
	 */
	public void setSendOrderExpress(List<Map<String,String>> orderMaps);

	/**
	 * 根据状态查询出库单
	 * @return
	 */
	List<ShipOrder> findSendOrderByStatus(Long centroId, String status);
	
	
	public List<ShipOrder> findSendOrderByStatusAndUserId(Long centroId, String status,Long userId);
	
	/**
	 * 重置货单为运单未打印状态。
	 * @param orderids
	 */
	public void reExpressShipOrder(Long[] orderIds);
	
	/**
	 * 查询出库单
	 * @param orderIds
	 * @return
	 */
	List<ShipOrder> findSendOrders(Long[] orderIds);	
	
	/**
	 * 分组查询出库单
	 * @param orderIds
	 * @return
	 */
	List<Map<String,Object>> findSendOrdersGroup(Long[] orderIds);
	
	/**
	 * 根据运单号或昵称查询出库单
	 * @param q
	 * @return
	 */
	List<ShipOrder> findSendOrderByQ(String q);
	
	/**
	 * 批量提交出库单，等待用户签收.
	 * @param orderids
	 */
	void submits(Long[] orderIds);
	
	/**
	 * 查询所有出库单(等待用户签收)
	 * 
	 * @return
	 */
	List<ShipOrder> findSendOrderSignWaits(Map<String,Object> params);

	/**
	 * 根据淘宝交易号查询出货单
	 * 
	 * @param tid
	 * @return
	 */
	ShipOrder getShipOrderByTid(Long tid);


	/**
	 * 提交出货单，仓库发货. 记录商品库存到可销售到冻结 更新出货单交易订单状态到等待用户签收
	 * 
	 * @param order
	 * @return
	 * @throws ApiException
	 */
	ShipOrder submitSendOrder(ShipOrder order) throws ApiException;
	
	/**
	 * 出货单用户签收确认 库存记录商品冻结到已售出 更新订单状态
	 * 
	 * @param orderId
	 */
	ShipOrder signSendOrder(Long orderId);

	/**
	 *  强制更新快递和状态信息
	 * @param Map
	 *            printedOrders 已完成打印的出货单 Map结构: id=出货单ID
	 *            expressCompany=运输公司CODE expressOrderno=运单号
	 *            status  =  状态值
	 *            只要ID信息  无前置状态
	 * 
	 */
	void setSendOrderExpressAndStatusById(List<Map<String, String>> orderMaps);
	void updateShipOrder(ShipOrder order);
	List<ShipOrder> findReturnOrderOnWay() ;
	ShipOrder getSendShipOrderByTradeId(Long tradeId);
	void updateDetailQuantity(Map<String,Object> params);
	ShipOrder getShipOrderInfo(Long id);
	ShipOrderDetail getShipOrderDetail(Map<String,Object> params);
	
	void updateShipOrderCentro(int centroId,int shipOrderId);
	
	List<ShipOrder> selectShipOrderByList(Map<String,Object> params);
	
	List<ShipOrderDetail> shipOrderDetailbyList(Map<String,Object> params);
	/**
	 * 更新发货单状态
	 * @param params
	 */
	void updateShipOrderStatus(Map<String,Object> params);
	
	/**
	 * 获取发货单明细
	 */
	List<ShipOrderDetail> getShipOrderDetailByOrderId(Long orderId);
	/**
	 * 分页查询已打印的订单
	 */
	Page<ShipOrder> findSendOrderWaitsOk(int page, int i, Map<String, Object> params);

	//List<ShipOrder> findSendOrderByStatus(Long centroId, String status, Long userId);
	 

	/**
	 * 更新批次信息
	 */
	void updateCompleteTradeBatch();
	/**
	 * 查询所有的订单 数据
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page<ShipOrder> findSendOrderList(int pageNo, int pageSize, Map<String, Object> params);

	/**
	 * 更新发货单收件人信息
	 */
	void updateShipOrderByTradeId(Map<String, Object> params);
	
	/**
	 * 更新发货单的运费信息
	 * */
	void updateShipOrderPrice(Long id, Double totalPrice);

	long getTotalResults(Map<String, Object> params);

	List<ShipOrder> findShipOrderWaitsOk(int page, int rows, Map<String, Object> params);


	long findTradeBatchSendOrderWaitsCount(Map<String, Object> params);

	int getSendOrderCount(Map<String, Object> params);

	List<ShipOrder> findeSendOrderByList(Map<String, Object> params, int page, int rows);
	
	List<Map<String, Object>> importOrders(List<Map<String, Object>> params);

	long findSendOrderImportCount();

	List<ShipOrder> findSendOrderImport();

	void updateImportShipOrderSub();

	List<ShipOrder> findSendOrderWaitsByPages(int page, int rows, Map<String, Object> params);
 
	
	ShipOrder findShipOrderByExpressOrderno(String  expressOrderno);
 
	void updateRemark(Map<String,Object> params);
	
	/**
	 * ERP 导入 订单
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> importERPOrders(Map<String,Object> params);

	ShipOrder getShipOrderById(Long id);

	Long getCountByDate(Map<String, Object> params);

	Long getSuccessCountByDate(Map<String, Object> params);

	Long getCheckSuccessCountByDate(Map<String, Object> params);

	Long getCheckFailCountByDate(Map<String, Object> params);

	void updateShipOrderById(ShipOrder order);
	
	List<Map<String,Object>> getShipOrderItemByWorkGroup(Map<String,Object> params);

	/**
	 * 订单导入打印
	 * */
	List<Map<String, Object>> importOrdersNew(Map<String, Object> params);
}