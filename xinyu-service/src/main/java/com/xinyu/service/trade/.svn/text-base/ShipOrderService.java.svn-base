package com.xinyu.service.trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xinyu.model.system.Account;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.util.MyException;
import com.xinyu.service.common.BaseService;

/**
 * shipOrder业务处理接口
 * */
public interface ShipOrderService extends BaseService {
	

	/**
	 * 条件查询shipOrder数据（非分页）
	 * @param params
	 * @return list
	 * */
	List<ShipOrder> findShipOrderByList(Map<String, Object> params);

	/**
	 * 根据Id查询订单
	 * @param id
	 * @return shipOrder
	 * */
	ShipOrder findShipOrderById(String id);
	/**
	 * 条件查询shipOrder数据（分页）
	 * @param params
	 * @param pageNump
	 * @param pageSize
	 * @return list
	 * */
	List<ShipOrder> findShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize);
	/**
	 * 条件查询shipOrder数据数量
	 * @param params
	 * @return int
	 * */
	int getTotal(Map<String, Object> params);
	
	
	/**
	 * 条件查询shipOrder表所有数据
	 * @param params
	 * @return
	 */
	public List<ShipOrder> getShipOrderListByAll(Map<String,Object> params);

	public int getShipOrderListByAllCount(Map<String,Object> params);

	/**
	 * shipOrder数据重构
	 * @param list
	 * @return list
	 * */
	List<Map<String, Object>> bulidListData(List<ShipOrder> shipOrders);

	/**
	 * shipOrder数据Items拼接
	 * @param shipOrder
	 * @return items
	 * */
	String bulidItemsData(ShipOrder shipOrder);
	
	String bulidItemsDataByTms(TmsOrder tmsOrder);

	/**
	 * 单据取消操作
	 * @param orderCode
	 * @param userId
	 * @param orderType
	 * @return
	 */
	public Map<String,Object> orderCancel(String orderCode,String userId,String orderType) throws MyException;
	/**
	 * 根据ID修改订单快递信息
	 * @param id
	 * @param sysId
	 * @param account
	 * */
	void chooseExpress(String id, String sysId, Account account);

	/**
	 * 删除订单
	 * @param id
	 * @param account
	 * */
	void deleteShipOrder(String id,Account account);

	/**
	 * 作废订单
	 * @param id
	 * @param account
	 * */
	void invalidShipOrder(String id,Account account);

	/**
	 * 订单退货
	 * @param shipOrder
	 * @param account
	 * @return map
	 * */
	Map<String, Object> addReturn(ShipOrder shipOrder,Account account);

	/**
	 * 修改shipOrder信息
	 * @param shipOrder
	 * */
	void updateShipOrder(ShipOrder shipOrder);
	
	void updateShipOrderStatus(Map<String,Object> params);

	/**
	 * 计算订单重量
	 * @param shipOrder
	 * @return long
	 * */
	Long buildWeight(ShipOrder shipOrder);

	/**
	 * 快速拆单处理
	 * @param type
	 * @param id
	 * @param num
	 * @return map
	 * */
	Map<String,Object> splitShipOrderOperation(String type, String id, Long num);

	/**
	 * 拆单处理
	 * @param param
	 * @return map
	 * */
	Map<String, Object> splitShipOrder(HashMap<String, Object> param);
	
	/**
	 * 新建订单
	 * @param shipOrder
	 * */
	public void insertShipOrder(ShipOrder shipOrder);

	
	List<Map<String, Object>> importERPOrders(Map<String, Object> params);

	
	List<Map<String, Object>> importOrders(List<Map<String, Object>> list);

	
	/**
	 * 审核订单，更新订单状态和物流公司
	 * @param id
	 * @param expressCompany
	 * @param account
	 * */
	void auditOneTrade(String id, String expressCompany, Account account);

	
	/**
	 * 订单反审
	 * @param request
	 * @param account
	 * @return map
	 * */
	void resetShipOrder(ShipOrder shipOrder,Account account);

	/**
	 *  强制更新快递和状态信息
	 * @param Map
	 *            printedOrders 已完成打印的出货单 Map结构: id=出货单ID
	 *            expressCompany=运输公司CODE expressOrderno=运单号
	 *            status  =  状态值
	 *            orderFlag  =大头标记
	 *            sellerMobile  = 收货网点名称
	 *            sellerPhone  = 收货网点CODE
	 *            只要ID信息  无前置状态
	 * 
	 */
	void setSendOrderExpressAndStatusById(List<Map<String, String>> paramMapList);

	
	public int isHaveOrder(Map<String,Object> params);
	
	void updateExpressOrderNo(Map<String, Object> retMap);
	
	/**
	 * 查询工作组商品信息
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> getShipOrderItemByWorkGroup(Map<String,Object> params);

	ShipOrder getShipOrderByParams(Map<String, Object> params);

	int getOldTotal(Map<String, Object> params);

	List<ShipOrder> findOldShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize);

	List<ShipOrder> findTmsNotExsist(Map<String, Object> params);

	List<Map<String,Object>> bulidOldListData(List<ShipOrder> shipOrders);

	/**
	 * 查询数据库store的shipOrder表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findStoreOrderList(Map<String, Object> params);

	
}
