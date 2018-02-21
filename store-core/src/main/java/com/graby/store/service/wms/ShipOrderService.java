package com.graby.store.service.wms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.graby.store.base.MessageContextHelper;
import com.graby.store.dao.jpa.EntryOrderDetailJpaDao;
import com.graby.store.dao.jpa.ShipOrderJpaDao;
import com.graby.store.dao.mybatis.ImportRecordDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.dao.mybatis.SystemItemDao;
import com.graby.store.dao.mybatis.SystemOperatorRecordDao;
import com.graby.store.dao.mybatis.TradeDao;
import com.graby.store.entity.Centro;
import com.graby.store.entity.ImportRecord;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrder.EntryOrderStatus;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.service.base.UserService;
import com.graby.store.service.inventory.AccountEntryArray;
import com.graby.store.service.inventory.AccountTemplate;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.inventory.QmInventoryService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.trade.TradeUtil;
import com.graby.store.service.util.IRedisProxy;
import com.graby.store.service.util.ObjectTranscoder;
import com.graby.store.util.ERPEnums;
import com.graby.store.util.Seqence;
import com.graby.store.web.auth.ShiroContextUtils;
import com.graby.store.web.top.TopApi;
import com.graby.store.web.top.TopWmsApi;
import com.taobao.api.ApiException;
import com.taobao.api.domain.PackageItem;
import com.taobao.api.domain.TradeOrderInfo;
import com.taobao.api.domain.WaybillAddress;
import com.taobao.api.internal.util.json.JSONReader;
import com.taobao.api.response.WlbWaybillIGetResponse;

@Component
@Transactional
public class ShipOrderService {

	public static final Logger logger = Logger.getLogger(ShipOrderService.class);
	// 默认查询待打单条数
	private static final int DEFAULT_FETCH_ROWS = 100;

	@Autowired
	private ShipOrderJpaDao orderJpaDao;

	@Autowired
	private ShipOrderDao shipOrderDao;

	@Autowired
	private EntryOrderDetailJpaDao entryOrderDetailJpaDao;

	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeDao tradeDao;
	@Autowired
	private ItemDao itemDao;

	@Autowired
	private QmInventoryService qmInventoryService;

	@Autowired
	private SystemItemDao systemItemDao;

	@Autowired
	private ImportRecordDao importRecordDao;
	@Autowired
	private IRedisProxy redisProxy;

	// @Autowired
	// private StatefulKnowledgeSession ksession;

	@Autowired
	private ExpressService expressService;
	@Autowired
	private SystemOperatorRecordDao recordDao;

	@Autowired
	private TopApi topApi;

	@Autowired
	private TopWmsApi topWmsApi;

	public void updateShipOrder(Map<String, Object> params) {
		this.shipOrderDao.updateShipOrder(params);
	}

	/**
	 * 修改打印批次
	 */
	public void updateShipOrderTradeBatchId(Map<String, Object> params) {
		shipOrderDao.updateShipOrderTradeBatchId(params);
	}

	private String formateDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	private String geneOrderno(String type) {
		StringBuffer number = new StringBuffer();
		number.append(type.equals(ShipOrder.TYPE_ENTRY) ? "E" : "S");
		Date today = new Date();
		number.append(formateDate(today, "yyyyMMdd"));
		String max = Seqence.getInstance().next();
		number.append(max);
		return number.toString();
	}

	/**
	 * 查询所有在途入库单
	 */
	public List<ShipOrder> findEntryOrderOnWay(Map<String, Object> params) {
		return shipOrderDao.findEntryOrderOnWay(params);
	}

	/**
	 * 查询所有待入库单退货
	 */
	public List<ShipOrder> findReturnOrderOnWay() {
		return shipOrderDao.findReturnOrderOnWay();
	}

	/**
	 * 保存入库单,shipOrder单据保存
	 * 
	 * @param order
	 */
	@Transactional(readOnly = false)
	public void saveEntryOrder(ShipOrder order) {
		Date now = new Date();
		try {
			System.err.println("BEGIN....");
			Long userid = ShiroContextUtils.getUserid();
			System.err.println("userid:" + userid);
			User user = new User();
			user.setId(userid);
			System.err.println("orderId:" + order.getId());
			if (order.getId() == null) {
				if (order.getOrderno() == null) {
					String orderno = geneOrderno(ShipOrder.TYPE_ENTRY);
					System.err.println("orderNo:" + orderno);
					order.setOrderno(orderno);
				}
				if (order.getType() == null) {
					order.setType(ShipOrder.TYPE_ENTRY);
				}

				order.setCreateDate(now);
				if (userid != null) {
					order.setCreateUser(user);
				}
			}
			// 等待商家发货

			if (order.getStatus() == null) {
				System.out.println("入库单状态设置order.getStatus()");
				order.setStatus(ShipOrder.EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);
			}

			if (order.getCentroId() == null) {
				System.out.println("入库单设置order.getCentroId()");
				order.setCentroId(1L);
			}
			order.setLastUpdateDate(now);
			if (userid != null) {
				order.setLastUpdateUser(user);
				order.setLastUpdateDate(now);
			}
			System.out.println("入库到保存start");
			orderJpaDao.save(order);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error:" + e.getMessage());
		}

		System.out.println("入库到保存end:" + order.getId());
	}

	/**
	 * 保存入库单,shipOrder单据保存
	 * 
	 * @param order
	 */
	@Transactional(readOnly = false)
	public void saveEntryReturnOrder(ShipOrder order) {
		Date now = new Date();
		try {
			Long userid = ShiroContextUtils.getUserid();
			User user = new User();
			user.setId(userid);
			if (order.getId() == null) {
				order.setCreateDate(now);
				if (userid != null) {
					order.setCreateUser(user);
				}
			}

			order.setLastUpdateDate(now);
			if (userid != null) {
				order.setLastUpdateUser(user);
				order.setLastUpdateDate(now);
			}
			logger.debug("退货入库到保存start");
			orderJpaDao.save(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("error:" + e.getMessage());
		}

		logger.debug("入库到保存end:" + order.getId());
	}

	/**
	 * 保存发货单明细
	 * 
	 * @param orderId
	 *            入库单号
	 * @param itemId
	 *            商品ID
	 * @param num
	 *            商品数量
	 * @param sku
	 *            TODO
	 */
	@Transactional(readOnly = false)
	public void saveShipOrderDetail(Long orderId, Long itemId, long num, String sku) {
		Long detailId = shipOrderDao.getEntryOrderDetail(orderId, itemId);
		if (detailId == null) {
			ShipOrderDetail detail = new ShipOrderDetail();
			ShipOrder order = new ShipOrder();
			order.setId(orderId);
			Item item = new Item();
			item.setId(itemId);
			detail.setOrder(order);
			detail.setItem(item);
			detail.setNum(num);
			if (StringUtils.isNotBlank(sku)) {
				detail.setSkuPropertiesName(sku);
			}
			entryOrderDetailJpaDao.save(detail);
		} else {
			shipOrderDao.increaseEntryOrderDetail(detailId, num);
		}

	}

	/**
	 * 删除发货单
	 * 
	 * @param orderId
	 */
	public void deleteShipOrder(Long orderId) {
		shipOrderDao.deleteDetailByOrderId(orderId);
		shipOrderDao.deleteOrder(orderId);
	}

	/**
	 * 删除发货单明细
	 * 
	 * @param id
	 */
	public void deleteShipOrderDetail(Long id) {
		shipOrderDao.deleteDetail(id);
	}

	/**
	 * 返回用户的入库单
	 * 
	 * @param userid
	 * @param status
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<ShipOrder> findEntrys(Long userid, String status, int page, int pageSize) {
		User user = new User();
		user.setId(userid);
		return orderJpaDao.findByCreateUserAndStatus(user, status, new PageRequest(page - 1, pageSize));
	}

	/**
	 * 获取发货单
	 * 
	 * @param id
	 * @return
	 */
	public ShipOrder getShipOrder(Long id) {
		return shipOrderDao.getShipOrder(id);
	}

	/**
	 * 获取订单信息，带详情
	 * 
	 * @param id
	 * @return
	 */
	public ShipOrder getShipOrderInfo(Long id) {
		ShipOrder shipOrder = this.getShipOrder(id);
		for (int i = 0; shipOrder.getDetails() != null && i < shipOrder.getDetails().size(); i++) {
			ShipOrderDetail detail = shipOrder.getDetails().get(i);
			Item item = this.itemDao.get(detail.getItem().getId());
			detail.setItem(item);
		}
		return shipOrder;
	}

	/**
	 * 确认发送入库单
	 * 
	 * @param id
	 */
	public boolean sendEntryOrder(Long id) {
		ShipOrder order = this.getShipOrder(id);
		List<ShipOrderDetail> details = order.getDetails();
		// 库存记账-商铺发送入库单
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(order.getCentroId(), order.getCreateUser().getId(), detail.getItem().getId(),
						detail.getNum(), AccountTemplate.SHIP_ENTRY_SEND);
			}
			shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取消发送入库单
	 * 
	 * @param id
	 */
	public boolean cancelEntryOrder(Long id) {
		ShipOrder order = this.getShipOrder(id);
		List<ShipOrderDetail> details = order.getDetails();
		// 库存记账-商铺发送入库单
		if (CollectionUtils.isNotEmpty(details)) {
			for (ShipOrderDetail detail : details) {
				inventoryService.input(order.getCentroId(), order.getCreateUser().getId(), detail.getItem().getId(),
						detail.getNum(), AccountTemplate.SHIP_ENTRY_CANCEL);
			}
			shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 接收发送入库单
	 * 
	 * @param id
	 * @param entrys
	 */
	public void recivedEntryOrder(Long id, List<AccountEntryArray> entrys) {
		// 库存记账
		if (CollectionUtils.isNotEmpty(entrys)) {
			for (AccountEntryArray accountEntrys : entrys) {
				inventoryService.inputs(accountEntrys.getCentroId(), accountEntrys.getUserId(),
						accountEntrys.getItemId(), accountEntrys.getEntrys(), "入库");

			}
		}
		shipOrderDao.setOrderStatus(id, ShipOrder.EntryOrderStatus.ENTRY_FINISH);
	}

	/* ------------ 出库单 ------------ */

	/**
	 * 查询所有出库单(待处理)
	 * 
	 * @return
	 */
	public List<ShipOrder> findSendOrderWaits(Map<String, Object> params) {
		long centroId = 1L;
		if (params != null && params.get("cuid") != null) {
			centroId = Long.valueOf((Integer) params.get("cuid"));
		}
		params.put("rownum", 500);

		params.put("centroId", centroId);
		return shipOrderDao.findSendOrderWaits(params);
	}

	/**
	 * 查询批次出库所有出库单(待处理)
	 * 
	 * @return
	 */
	public List<ShipOrder> findTradeBatchSendOrderWaits(Map<String, Object> params) {
		long centroId = 1L;
		if (params != null && params.get("cuid") != null) {
			centroId = Long.valueOf((Integer) params.get("cuid"));
		}
		int rownum = DEFAULT_FETCH_ROWS;
		if (params.get("rownum") != null) {
			rownum = (Integer) params.get("rownum");
		}
		params.put("rownum", rownum);

		params.put("centroId", centroId);
		return shipOrderDao.findTradeBatchSendOrderWaits(params);
	}

	/**
	 * 获取出库单 已取得运单号
	 * 
	 * @param params
	 * @return
	 */
	public List<ShipOrder> findSendOrderWaitsOk(Map<String, Object> params) {
		long centroId = 1L;
		if (params != null && params.get("cuid") != null) {
			centroId = Long.valueOf((Integer) params.get("cuid"));
		}
		params.put("rownum", DEFAULT_FETCH_ROWS);

		params.put("centroId", centroId);
		return shipOrderDao.findSendOrderWaitsOk(params);
	}

	/**
	 * 根据交易订单ID查询发货单
	 * 
	 * @param tradeId
	 * @return
	 */
	public Long getSendOrderIdByTradeId(Long tradeId) {
		return shipOrderDao.getSendOrderIdByTradeId(tradeId);
	}

	/**
	 * 运单打印调用接口， 按规则分类所有未处理出库单。
	 * 
	 * 先查询仓库的所有待处理出库单，按规则将运输公司和运输公司编码填充到出库单。
	 * 
	 * @param centroId
	 *            仓库ID
	 * @return 出库单列表
	 */
	public List<ShipOrder> findGroupSendOrderWaits(Long centroId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rownum", DEFAULT_FETCH_ROWS);
		params.put("centroId", centroId);
		List<ShipOrder> orders = shipOrderDao.findSendOrderWaits(params);
		// for (ShipOrder shipOrder : orders) {
		// ksession.insert(shipOrder);
		// }
		// ksession.fireAllRules();
		String companyCode;
		String companyName;
		List<ShipOrder> results = new ArrayList<ShipOrder>();
		for (ShipOrder shipOrder : orders) {
			// 商品的名称这里不用再去拼写计算，直接取items字段就可以。
			// shipOrder.setItems(buildItems(shipOrder));
			shipOrder.setRemark(buildRemark(shipOrder));
			shipOrder.setOriginPhone(getPhone(shipOrder.getTradeId()));
			companyCode = shipOrder.getExpressCompany();
			companyName = companyCode == null ? "未分类" : expressService.getExpressCompanyName(companyCode);
			shipOrder.setExpressCompanyName(companyName);
			results.add(shipOrder);
		}
		return results;
	}

	/**
	 * 提交给运单打印的商品明细字段
	 * 
	 * @param order
	 * @return
	 */
	private String buildItems(ShipOrder order) {
		StringBuffer buf = new StringBuffer();
		// 放商品明细
		int total = 0;
		for (Iterator<ShipOrderDetail> iterator = order.getDetails().iterator(); iterator.hasNext();) {
			ShipOrderDetail detail = iterator.next();
			total += detail.getNum();
			buf.append(detail.getItemTitle() + detail.getSkuPropertiesName()).append(":" + detail.getNum() + "件");
			if (iterator.hasNext()) {
				buf.append(",\n");
			}
		}
		if (buf.length() > 120) {
			buf = new StringBuffer("商品过多 请根据拣货单拣货,");
		}
		order.setTotalnum(total);
		return buf.toString();
	}

	// 备注
	private String buildRemark(ShipOrder order) {
		StringBuffer buf = new StringBuffer();
		// 卖家买家留言备注
		if (StringUtils.isNotBlank(order.getSellerMemo())) {
			buf.append("卖家:").append(order.getSellerMemo()).append(",");
		}
		if (StringUtils.isNotBlank(order.getBuyerMemo())) {
			buf.append("买家:").append(order.getBuyerMemo()).append(",");
		}
		if (StringUtils.isNotBlank(order.getBuyerMessage())) {
			buf.append("买家留言:" + order.getBuyerMessage());
		}
		return buf.toString();
	}

	/**
	 * 查询所有出库单(等待用户签收) TODO 多仓库
	 * 
	 * @return
	 */
	public List<ShipOrder> findSendOrderSignWaits(Map<String, Object> params) {
		return shipOrderDao.findSendOrderSignWaits(params);
	}

	/**
	 * 根据淘宝交易号查询出货单
	 * 
	 * @param tradeId
	 * @return
	 */
	public ShipOrder getSendShipOrderByTradeId(Long tradeId) {
		return shipOrderDao.getSendShipOrderByTradeId(tradeId);
	}

	/**
	 * 创建发货单
	 * 
	 * @param shipOrder
	 */
	public void createSendShipOrder(ShipOrder shipOrder) {
		Date now = new Date();
		if (shipOrder.getId() == null) {
			String orderno = geneOrderno(ShipOrder.TYPE_DELIVER);
			shipOrder.setType(ShipOrder.TYPE_DELIVER);
			shipOrder.setOrderno(orderno);
			shipOrder.setCreateDate(now);
		}
		// 等待物流接收
		shipOrder.setStatus(ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
		// shipOrder.setStatus(ShipOrder.SendOrderStatus.WAIT_EXPRESS_RECEIVED);
		shipOrder.setLastUpdateDate(now);
		orderJpaDao.save(shipOrder);
		// 这里items字段更新不进去，重新调用UPDATE语句来手动更新
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("items", shipOrder.getItems());
		params.put("id", shipOrder.getId());
		params.put("itemid", shipOrder.getItemsId());
		params.put("totalPrice", shipOrder.getInvoiceAmount());
		this.shipOrderDao.updateShipOrderitems(params);
		System.err.println("发货单保存OK,order_id=" + shipOrder.getId() + "ITEMS:" + shipOrder.getItems());
		List<ShipOrderDetail> items = shipOrder.getDetails();
		if (CollectionUtils.isNotEmpty(items)) {
			for (ShipOrderDetail detail : items) {
				saveShipOrderDetail(shipOrder.getId(), detail.getItem().getId(), detail.getNum(),
						detail.getSkuPropertiesName());
			}
		}
	}

	/**
	 * 选择快递公司
	 * 
	 * @param orderId
	 * @param expressCompany
	 */
	public void chooseExpress(Long orderId, String expressCompany) {
		shipOrderDao.chooseExpress(orderId, expressCompany);
	}

	/**
	 * 运单打印成功，注册运单信息。
	 * 
	 * @param Map
	 *            printedOrders 已完成打印的出货单 Map结构: id=出货单ID
	 *            expressCompany=运输公司CODE expressOrderno=运单号 前置状态
	 *            WAIT_EXPRESS_RECEIVED 修改为 WAIT_EXPRESS_PICKING
	 * 
	 * 
	 */
	public void setSendOrderExpress(List<Map<String, String>> orderMaps) {
		System.err.println("更新运单信息。。。。。。setSendOrderExpress" + orderMaps);
		if (CollectionUtils.isNotEmpty(orderMaps)) {
			for (Map<String, String> map : orderMaps) {
				Assert.notNull(map.get("expressCompany"), "物流公司不能为空");
				Assert.notNull(map.get("expressOrderno"), "运单号不能为空");
				shipOrderDao.setSendOrderExpress(map);
			}
		}
	}

	/**
	 * 强制更新快递和状态信息
	 * 
	 * @param Map
	 *            printedOrders 已完成打印的出货单 Map结构: id=出货单ID
	 *            expressCompany=运输公司CODE expressOrderno=运单号 status = 状态值
	 *            orderFlag =大头标记 sellerMobile = 收货网点名称 sellerPhone = 收货网点CODE
	 *            只要ID信息 无前置状态
	 * 
	 */
	public void setSendOrderExpressAndStatusById(List<Map<String, String>> orderMaps) {
		if (CollectionUtils.isNotEmpty(orderMaps)) {
			for (Map<String, String> map : orderMaps) {
				Assert.notNull(map.get("expressCompany"), "物流公司不能为空");
				Assert.notNull(map.get("expressOrderno"), "运单号不能为空");
				Assert.notNull(map.get("status"), "状态不能为空");
				// Assert.notNull(map.get("orderFlag"), "大头标记不能为空");

				// Assert.notNull(map.get("sellerMobile"), "收货网点名称");
				// Assert.notNull(map.get("sellerPhone"), "收货网点CODE");

				shipOrderDao.setSendOrderExpressById(map);
			}
		}
	}

	/**
	 * 查询所有未拣货出库单
	 * 
	 * @param centroId
	 * @param status
	 *            TODO
	 * @return
	 */
	// public List<ShipOrder> findSendOrderByStatus(Long centroId, String
	// status,Long userId) {
	// //return shipOrderDao.findSendOrderByStatus(centroId, status,userId,
	// DEFAULT_FETCH_ROWS);
	// return shipOrderDao.findSendOrderByStatus(centroId, status,userId);
	// }

	// public List<ShipOrder> findSendOrderByStatus(Long centroId, String
	// status) {
	// return shipOrderDao.findSendOrderByStatus(centroId, status,
	// DEFAULT_FETCH_ROWS);
	// }
	/**
	 * 重置货单为运单未打印状态。
	 * 
	 * @param orderIds
	 */
	public void reExpressShipOrder(Long[] orderIds) {
		if (orderIds == null || orderIds.length == 0) {
			return;
		}
		for (Long orderId : orderIds) {
			shipOrderDao.setOrderStatus(orderId, ShipOrder.SendOrderStatus.WAIT_EXPRESS_RECEIVED);
		}
	}

	/**
	 * 根据出库单ID查询
	 * 
	 * @param orderIds
	 * @return
	 */
	public List<ShipOrder> findSendOrders(Long[] orderIds) {
		if (orderIds == null || orderIds.length == 0) {
			return null;
		}
		List<ShipOrder> orders = shipOrderDao.findSendOrders(orderIds);
		for (ShipOrder shipOrder : orders) {
			int num = 0;
			shipOrder.setExpressCompanyName(expressService.getExpressCompanyName(shipOrder.getExpressCompany()));
			List<ShipOrderDetail> details = shipOrderDao.getShipOrderDetailByOrderId(shipOrder.getId());
			for (ShipOrderDetail detail : details) {
				num = num + (int) detail.getNum();
				Item item = itemDao.get(detail.getItem().getId());
				detail.setItem(item);
			}

			shipOrder.setTotalnum(num);
			shipOrder.setDetails(details);
		}
		return orders;
	}

	/**
	 * 根据ID查询出库单（按产品分组）。
	 * 
	 * @param orderIds
	 * @return
	 */
	public List<Map<String, Object>> findSendOrdersGroup(Long[] orderIds) {
		if (orderIds == null || orderIds.length == 0) {
			return null;
		}
		List<Map<String, Object>> orders = shipOrderDao.findSendOrdersGroup(orderIds);
		return orders;
	}

	/**
	 * 根据运单号或昵称查询出库单
	 * 
	 * @param q
	 * @return
	 */
	public List<ShipOrder> findSendOrderByQ(String q) {
		return shipOrderDao.findSendOrderByQ("%" + q + "%");
	}

	/**
	 * (仓库方)批量提交出库单，等待用户签收. 此时订单已具有快递公司及运单号信息 修改订单的状态为 TRADE_WAIT_EXPRESS_NOFITY
	 * 
	 * @param orderIds
	 */
	public void submits(Long[] orderIds) {
		if (orderIds == null || orderIds.length == 0) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (Long orderId : orderIds) {
			/**
			 * 批量拣货操作，奇门的单 据不做任何处理
			 */
//			ShipOrder order = this.shipOrderDao.getShipOrder(orderId);
			ShipOrder order=this.getShipOrderInfo(orderId);
			if (order.getSource() != null && order.getSource().equals(Trade.SourceFlag.SourceFlag_QM)) {
				continue;
			}
			Map<String,String> memcachedMap=new HashMap<String, String>();
			int zcIndex=0,zpIndex=0;
			for(int j=0;j<order.getDetails().size();j++){
				ShipOrderDetail detail=order.getDetails().get(j);
				Item item=detail.getItem();
				memcachedMap.put(item.getBarCode(), item.getId()+"||"+detail.getNum()+"||"+item.getItemType());		
				if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZC")){
					zcIndex+=detail.getNum();
				}
				if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZP")){
					zpIndex+=detail.getNum();
				}
			}
			SystemOperatorRecord record=new SystemOperatorRecord();
			record.setDescription("导入订单批量拣货处理缓存操作:"+order.getExpressOrderno()+"||"+memcachedMap);
			record.setIpaddr(order.getExpressOrderno());
			record.setOperatorModel(OperatorModel.CONFIRM);
			record.setStatus("");
			record.setTime(new Date());
			this.recordDao.insert(record);
			
			logger.error("导入订单批量拣货处理缓存操作:"+order.getExpressOrderno()+"||"+memcachedMap);
			memcachedMap.put("orderInfo", order.getId()+"||"+order.getTotalWeight()+"||"+order.getCreateUser().getId());
			memcachedMap.put("zcIndex", String.valueOf(zcIndex));
			memcachedMap.put("zpIndex", String.valueOf(zpIndex));			
			memcachedMap.put("orderDate", sdf.format(order.getLastUpdateDate()));
			memcachedMap.put("state", order.getReceiverState());
			memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
			memcachedMap.put("user", String.valueOf(order.getCreateUser().getId()));			
//			manager.add(order.getExpressOrderno(), memcachedMap);
			this.saveMemcachedInfo(order.getExpressOrderno(), memcachedMap);
			Trade trade = this.tradeDao.getTrade(order.getTradeId());
			shipOrderDao.setOrderStatus(orderId, ShipOrder.EntryOrderStatus.WMS_FINISH);
			shipOrderDao.setTradeStatus(orderId, Trade.Status.TRADE_WAIT_EXPRESS_NOFITY);
			tradeService.updateTradeStatus(trade.getId(), Trade.Status.TRADE_WAIT_EXPRESS_NOFITY);
		}
	}

	/**
	 * (商铺方)通知用户签收[多条]
	 * 
	 * @param tradeIds
	 * @return 错误的通知条数。
	 * @throws ApiException
	 */
	public void batchNotifyUserSign(Long[] tradeIds) throws ApiException {
		if (tradeIds == null || tradeIds.length == 0) {
			return;
		}
		for (Long tradeId : tradeIds) {
			notifyUserSign(tradeId);
		}
	}

	/**
	 * (商铺方)通知用户签收[单条] 库存记账: 冻结->可销售
	 * 
	 * @param tradeId
	 * @return
	 * @throws ApiException
	 */
	public void notifyUserSign(Long tradeId) throws ApiException {
		ShipOrder order = getSendShipOrderByTradeId(tradeId);
		List<Long> tids = tradeService.getRelatedTid(tradeId);
		for (Long tid : tids) {
			topApi.tradeOfflineShipping(tid, order.getExpressOrderno(), order.getExpressCompany());
			MessageContextHelper.append("已通知" + order.getBuyerNick() + "签收, 运单号:" + order.getExpressOrderno());
		}
		tradeService.updateTradeStatus(tradeId, Trade.Status.TRADE_WAIT_BUYER_RECEIVED);
		inventoryService.input(order, AccountTemplate.STORAGE_SHIPPING_CONFIRM);
	}

	/**
	 * (仓库方)提交出货单 - 单条老版
	 * 
	 * @param order
	 * @return
	 * @throws ApiException
	 */
	public ShipOrder submitSendOrder(ShipOrder order) throws ApiException {
		// 更新基本信息（运单号、运输公司）
		ShipOrder sendOrderEntity = getShipOrder(order.getId());
		sendOrderEntity.setExpressCompany(order.getExpressCompany());
		sendOrderEntity.setExpressOrderno(order.getExpressOrderno());
		sendOrderEntity.setLastUpdateDate(new Date());
		sendOrderEntity.setLastUpdateUser(order.getLastUpdateUser());
		// 更新出货单状态-仓库已打印
		sendOrderEntity.setStatus(OrderStatusEnums.WMS_PRINT.getKey());
		// sendOrderEntity.setStatus(ShipOrder.SendOrderStatus.WAIT_BUYER_RECEIVED);
		updateShipOrder(sendOrderEntity);
		tradeService.updateTradeStatus(sendOrderEntity.getTradeId(), Trade.Status.TRADE_WAIT_EXPRESS_NOFITY);
		return sendOrderEntity;
	}

	/**
	 * 关闭出货单(用户签收确认,后台关闭)
	 * 
	 * @param orderId
	 */
	public ShipOrder closeOrder(Long orderId) {
		ShipOrder order = getShipOrder(orderId);
		shipOrderDao.setOrderStatus(orderId, ShipOrder.SendOrderStatus.SEND_FINISHED);
		tradeService.updateTradeStatus(order.getTradeId(), Trade.Status.TRADE_FINISHED);
		return order;
	}

	/**
	 * 关闭出货单
	 */
	public void closeOrderByTradeId(Long tradeId) {
		tradeService.updateTradeStatus(tradeId, Trade.Status.TRADE_FINISHED);
		ShipOrder order = getSendShipOrderByTradeId(tradeId);
		shipOrderDao.setOrderStatus(order.getId(), ShipOrder.SendOrderStatus.SEND_FINISHED);
	}

	@Transactional(readOnly = false)
	public void updateShipOrder(ShipOrder order) {
		orderJpaDao.save(order);
	}

	/**
	 * 创建出库单（电子面单）
	 * 
	 * @param tradeId
	 * @param expressCompany
	 * @return
	 * @throws ApiException
	 */
	public void createWmsOrder(String cpCode, String[] tids) throws ApiException {
		for (String tid : tids) {
			// 创建出库单
			ShipOrder order = getSendShipOrderByTradeId(Long.valueOf(tid));
			if (order == null) {
				order = tradeService.createSendShipOrderByTradeId(Long.valueOf(tid));
			}
			// 设置电子面单号
			if (StringUtils.isEmpty(order.getExpressOrderno())) {
				WaybillAddress address = toWaybillAddress(order);
				TradeOrderInfo tradeOrderInfo = toTradeOrderInfo(order);
				WlbWaybillIGetResponse resp = topWmsApi.wayBillGet(cpCode, address, tradeOrderInfo);
				if (resp.isSuccess()) {
					// 设置出库单状态
					String billCode = resp.getWaybillApplyNewCols().get(0).getWaybillCode();
					order.setExpressCompany(cpCode);
					order.setExpressOrderno(billCode);
					order.setBuyerMemo("WAYBILL");
					updateShipOrder(order);
					MessageContextHelper.append("电子面单创建成功:" + billCode);
				} else {
					tradeService.updateTradeStatus(order.getTradeId(), Trade.Status.TRADE_WAIT_CENTRO_AUDIT);
					deleteShipOrder(order.getId());
					MessageContextHelper.append("电子面单创建失败:" + resp.getMsg());
				}
			}
		}
	}

	private WaybillAddress toWaybillAddress(ShipOrder shipOrder) {
		WaybillAddress address = new WaybillAddress();
		address.setProvince(shipOrder.getReceiverState()); // 省
		address.setCity(shipOrder.getReceiverCity()); // 市
		address.setArea(shipOrder.getReceiverDistrict()); // 区
		address.setTown(shipOrder.getReceiverDistrict()); // TODO 街道
		address.setAddressDetail(shipOrder.getReceiverAddress()); // 地址
		return address;
	}

	private TradeOrderInfo toTradeOrderInfo(ShipOrder shipOrder) {
		TradeOrderInfo tradeOrderInfo = new TradeOrderInfo();

		// 发货人
		tradeOrderInfo.setSendPhone(shipOrder.getSellerMobile() + " " + shipOrder.getSellerPhone());
		tradeOrderInfo.setSendName(shipOrder.getBuyerNick());

		// 收货人
		tradeOrderInfo.setConsigneeAddress(toWaybillAddress(shipOrder));
		tradeOrderInfo.setConsigneeName(shipOrder.getReceiverName()); // 收货人
		tradeOrderInfo.setConsigneePhone(shipOrder.getReceiverMobile() + " " + shipOrder.getReceiverPhone()); // 收货人电话

		// 订单来源
		String tids = tradeService.getTrade(shipOrder.getTradeId()).getTradeFrom();
		List<String> orderList = Arrays.asList(tids.split(","));
		tradeOrderInfo.setTradeOrderList(orderList);

		// 商品信息
		shipOrder.setItems(buildItems(shipOrder));
		tradeOrderInfo.setItemName(shipOrder.getItems());
		List<ShipOrderDetail> details = shipOrder.getDetails();
		List<PackageItem> items = new ArrayList<PackageItem>(details.size());
		for (ShipOrderDetail detail : details) {
			PackageItem packageItem = new PackageItem();
			packageItem.setCount(detail.getNum());
			packageItem.setItemName(detail.getItemTitle());
			items.add(packageItem);
		}
		tradeOrderInfo.setPackageItems(items);
		tradeOrderInfo.setOrderChannelsType("TB");
		tradeOrderInfo.setProductType("STANDARD_EXPRESS");
		tradeOrderInfo.setRealUserId(ShiroContextUtils.getUserid()); // 使用者ID

		return tradeOrderInfo;
	}

	@Autowired
	private UserService userService;

	private String getPhone(long tradeId) {
		String phone = "0731-52777568";
		User user = userService.getUser(tradeService.getTrade(tradeId).getUser().getId());
		Map<String, String> profile = (Map<String, String>) reader.read(user.getDescription());
		if (profile != null) {
			phone = profile.get("phone");
		}
		return phone;
	}

	private static JSONReader reader = new JSONReader() {
	};

	public List<ShipOrder> queryShipOrderCodeIdAndType(Map<String, Object> params) {
		return this.shipOrderDao.selectShipOrderByIdCodeAndtype(params);
	}

	public List<ShipOrderDetail> shipOrderDetailbyList(Map<String, Object> params) {
		return this.shipOrderDao.shipOrderDetailbyList(params);
	}

	public void cancelShipOrder(Map<String, Object> params) {
		this.shipOrderDao.cancelShipOrder(params);
	}

	/***
	 * 查询shipOrder 表数据
	 * 
	 * @param params
	 * @return
	 */
	public List<ShipOrder> selectShipOrderByList(Map<String, Object> params) {
		return this.shipOrderDao.selectShipOrderByList(params);
	}

	public void updateDetailQuantity(Map<String, Object> params) {
		this.shipOrderDao.updateDetailQuantity(params);
	}

	public ShipOrderDetail getShipOrderDetail(Map<String, Object> params) {
		return this.shipOrderDao.getShipOrderDetail(params);
	}

	/**
	 * 处理商品库存类型
	 * 
	 * @param inventoryType
	 * @return
	 */
	public String processinventoryType(String inventoryType) {
		if (inventoryType == null || inventoryType.length() == 0) {
			return "";
		}
		if (inventoryType.equals("ZP")) {
			return "正品";
		}
		if (inventoryType.equals("CC")) {
			return "残次";
		}
		if (inventoryType.equals("JS")) {
			return "机损";
		}
		if (inventoryType.equals("XS")) {
			return "箱损";
		}

		return "";

	}

	/**
	 * 修改单据仓库信息
	 * 
	 * @param centroId
	 */
	public void updateShipOrderCentro(int centroId, int shipOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cuid", centroId);
		params.put("id", shipOrderId);
		this.shipOrderDao.updateShipOrderCentro(params);
	}

	/**
	 * 修改仓库状态
	 * 
	 * @param params
	 */
	public void updateShipOrderStatus(Map<String, Object> params) {
		this.shipOrderDao.updateShipOrderStatus(params);
	}

	public void updateShipOrderTradeId(Map<String, Object> params) {
		this.shipOrderDao.updateShipOrderTradeId(params);
	}

	public List<ShipOrderDetail> getShipOrderDetailByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return this.shipOrderDao.getShipOrderDetailByOrderId(orderId);
	}

	public List<ShipOrder> findSendOrderByStatusAndUserId(Long centroId, String status, Long userId) {
		return this.shipOrderDao.findSendOrderByStatusAndUserId(centroId, status, userId);
	}

	public void updateShipOrderWeight(Long orderId, Double weight) {
		this.shipOrderDao.updateShipOrderWeight(orderId, weight);
	}

	public Page<ShipOrder> findSendOrderWaitsOk(int pageNo, int pageSize, Map<String, Object> params) {
		// pageNo--;
		int start = (pageNo - 1) * pageSize;
		int offset = pageSize;
		params.put("start", start);
		params.put("offset", offset);
		List<ShipOrder> orders = shipOrderDao.findSendOrderWaitsOk(params);
		long count = shipOrderDao.getTotalResults(params);
		PageRequest pageable = new PageRequest(pageNo - 1, offset);
		// PageRequest pageable = new PageRequest(start, offset);
		Page<ShipOrder> page = new PageImpl<ShipOrder>(orders, pageable, count);
		return page;
	}

	public Page<ShipOrder> findTradeBatchSendOrderWaits(int pageNo, int pageSize, Map<String, Object> params) {
		int start = (pageNo - 1) * pageSize;
		int offset = pageSize;
		params.put("start", start);
		params.put("offset", offset);
		params.remove("rownum");
		List<ShipOrder> orders = this.shipOrderDao.findTradeBatchSendOrderWaits(params);
		long count = shipOrderDao.findTradeBatchSendOrderWaitsCount(params);
		PageRequest pageable = new PageRequest(pageNo - 1, offset);
		Page<ShipOrder> page = new PageImpl<ShipOrder>(orders, pageable, count);
		return page;
	}

	/**
	 * 查询所有的订单 数据
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page<ShipOrder> findSendOrderList(int pageNo, int pageSize, Map<String, Object> params) {
		int start = (pageNo - 1) * pageSize;
		int offset = pageSize;
		params.put("start", start);
		params.put("offset", offset);
		params.remove("rownum");
		/**
		 * index 用来判断是否查询旧表历史数据，不调历史数据时不用理就好。
		 */
		String history = (String) params.get("history"); 
		if (StringUtils.isEmpty(history)) {
			List<ShipOrder> orders = shipOrderDao.findSendOrderList(params);
			long count = shipOrderDao.findSendOrderListCount(params);
			PageRequest pageable = new PageRequest(pageNo - 1, offset);
			Page<ShipOrder> page = new PageImpl<ShipOrder>(orders, pageable, count);
			return page;
		} else {
			List<ShipOrder> orders = shipOrderDao.findSendOrderOldList(params);
			long count = shipOrderDao.findSendOrderOldListCount(params);
			PageRequest pageable = new PageRequest(pageNo - 1, offset);
			Page<ShipOrder> page = new PageImpl<ShipOrder>(orders, pageable, count);
			return page;
		}
	}

	public void updateShipOrderByTradeId(Map<String, Object> params) {
		shipOrderDao.updateShipOrderByTradeId(params);
	}

	public List<ShipOrder> findSendOrderByStatus(Long centroId, String status) {
		return shipOrderDao.findSendOrderByStatus(centroId, status, DEFAULT_FETCH_ROWS);
	}

	public List<ShipOrder> findShipOrdersByParams(Map<String, Object> params) {
		return shipOrderDao.findShipOrdersByParams(params);
	}

	public void updateShipOrderPrice(Long id, Double totalPrice) {
		shipOrderDao.updateShipOrderPrice(id, totalPrice);
	}

	public long getTotalResults(Map<String, Object> params) {
		return this.shipOrderDao.getTotalResults(params);
	}

	public List<ShipOrder> findShipOrderWaitsOk(int page, int rows, Map<String, Object> params) {
		int start = (page - 1) * rows;
		int offset = rows;
		params.put("start", start);
		params.put("offset", offset);
		List<ShipOrder> orders = shipOrderDao.findSendOrderWaitsOk(params);
		return orders;
	}

	public long findTradeBatchSendOrderWaitsCount(Map<String, Object> params) {
		return this.shipOrderDao.findTradeBatchSendOrderWaitsCount(params);
	}

	public int getSendOrderCount(Map<String, Object> params) {
		return this.shipOrderDao.getSendOrderCount(params);
	}

	public List<ShipOrder> findeSendOrderByList(Map<String, Object> params, int page, int rows) {
		int start = (page - 1) * rows;
		int offset = rows;
		params.put("start", start);
		params.put("offset", offset);
		List<ShipOrder> orders = this.shipOrderDao.findeSendOrderByList(params);
		return orders;
	}

	/**
	 * 指定单据导入
	 * 
	 * @param params
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public List<Map<String, Object>> importOrders(List<Map<String, Object>> params) {
		// type='deliver' and a.status='ENTRY_WAIT_STORAGE_RECEIVED'
		if (params != null && params.size() > 0) {
			System.out.println("导入");

			Centro centro = new Centro();
			centro.setId(1L);
			for (int j = 0;j<params.size();j++) {
				Map<String, Object> map = params.get(j);
				ShipOrder order1 = shipOrderDao.getShipOrder(1L);
				System.out.println("order1:" + order1.getDetails().size());
				ShipOrder order = null;
				try {
					order = (ShipOrder) order1.clone();
					ShipOrderDetail detail = (ShipOrderDetail) order1.getDetails().get(0).clone();
					List<ShipOrderDetail> list = new ArrayList<ShipOrderDetail>();
					detail.setId(null);
					list.add(detail);
					detail.setOrder(order);
					order.setDetails(list);

				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				User user = new User();
				user.setId(Long.valueOf("" + map.get("userId")));

				order.setId(null);
				order.setTradeId(null);
				order.setSellerMobile(null);
				order.setOriginPersion(null);
				order.setOrderFlag(null);
				order.setSellerPhone(null);
				order.setExpressCompany("" + map.get("expressCompany"));

				String orderNo = "" + map.get("code");// 去掉 淘宝订单号第一个引号
				order.setOrderno(orderNo);
				order.setCreateUser(user); // 设置 商家
				order.setExpressOrderno(null);
				order.setType(ShipOrder.TYPE_DELIVER);
				order.setStatus(EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);
				order.setCreateDate(new Date());
				order.setLastUpdateDate(new Date());
				order.setReceiverName((String) map.get("userName"));
				order.setReceiverMobile((String) map.get("userPhone"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.setPayTime(sdf.format(new Date()));
				String address = "" + map.get("address");
				System.out.println("orderNo:" + orderNo + ";address:" + address);
				System.err.println("导入行:"+(j+1)+";address:"+address);
				order.setReceiverState("" + map.get("state"));
				order.setReceiverCity("" + map.get("city"));
				order.setReceiverDistrict("" + map.get("district"));
				order.setReceiverAddress(address);
				order.setSource(Trade.SourceFlag.SourceFlag_IMPORT);
				order.setItems("" + map.get("items"));
				
				order.setBuyerNick((String) map.get("userName"));
				order.setBuyerMemo("");
				order.setBuyerMessage("");
				order.setSellerMemo("");
				order.setSellerMessage("");
				
				this.saveEntryOrder(order);
				for (int i = 0; order.getDetails() != null && i < order.getDetails().size(); i++) {
					this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
				}
				TradeUtil util = new TradeUtil();
				Trade adapter = null;
				try {
					adapter = util.adapter(order);

				} catch (Exception e) {
					logger.info(e.getMessage());
				}
				// update sc_ship_order set trade_id=#{trade_id} where id=#{id}

				// Map<String,Object> updaeParams = new HashMap<String,
				// Object>();
				Trade createTrade = tradeService.createTrade(adapter, order.getId());
				// updaeParams.put("trade_id", createTrade.getId());
				// updaeParams.put("id", order.getId());
				// shipOrderDao.updateShipOrderTradeId(updaeParams);
			}
		}
		return null;
	}

	/**
	 * ERP单据导入
	 * 
	 * @param params
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public  List<Map<String, Object>> importERPOrders(Map<String, Object> params) {
		// 返回信息 集合
		SimpleDateFormat ssdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> retMapList = null;
		
		if (params != null) {
			System.err.println("ERP导入开始！！！！！");
			System.out.println("ERP导入！！！！");

			retMapList = new ArrayList<Map<String, Object>>();
			
			String accountId = "" + params.get("accountId");
		
			// 用户信息
			Long userId = Long.valueOf("" + params.get("userId"));
			User user = userService.getUser(userId);
			// 仓库信息
			Centro centro = new Centro();
			Long centroId = Long.valueOf("" + params.get("centroId"));
			centro.setId(centroId);

			// erp 信息
			String erp = "" + params.get("erp");
			// 获得快系统快递 信息
			List<SystemItem> sysItemList = systemItemDao.findSystemItemByType(StoreSystemItemEnums.LOGISTICS.getKey());

			// 单条 数据信息操作
			List<Map<String, Object>> orderListMap = (List<Map<String, Object>>) params.get("orderList");
			int size = orderListMap.size();
			Date now = new Date(); // 时间初始化
			for (int i = 0; i < size; i++) {
				
				Map<String, Object> map = orderListMap.get(i);
				Map<String, Object> retMap = new HashMap<String, Object>();

				String[] itemCode = (String[]) map.get("itemCode");// 商品code
				String[] itemCount = (String[]) map.get("itemCount");// 商品数量
				logger.debug("导入行:"+(i+1)+";itemCode:"+itemCode[0]);
				String tbNumber = "" + map.get("tbNumber");// 淘宝订单号
				String expressOrderno = ("" + map.get("expressOrderno")).trim();// 菜鸟面单号
				String state = "" + map.get("state");// 省份
				String city = "" + map.get("city");// 市区
				String district = "" + map.get("district");// 区域
				String address = "" + map.get("address");// 详细地址
				String userName = "" + map.get("userName"); // 收货人名
				String userPhone = "" + map.get("userPhone"); // 收货人手机号码
				String express = "" + map.get("express"); // 快递公司名称
				String buyerName = "" + map.get("buyerName"); // 买家昵称
				Object object = map.get("barCode");
			
				String[] barCode = null;
				if (object != null) { // 商品条码
					barCode = (String[]) map.get("barCode");
				}
				String items = "" + map.get("items"); // 本地商品 基本信息

				// 导入记录初始化
				ImportRecord importRecord = new ImportRecord();
				importRecord.setCentroId(centroId);
				importRecord.setUserId(userId);
				importRecord.setErp(erp);
				importRecord.setCreateDate(now);
				importRecord.setExpressOrderno(expressOrderno);
				importRecord.setCompany(express);
				importRecord.setStatus(ImportRecord.ImportRecordStatus.FAIL);
				// 检测电子面单是否存在
				Map<String, Object> itemParams = new HashMap<String, Object>();
				itemParams.put("expressOrderno", expressOrderno);
				int orderCount = shipOrderDao.getSendOrderCount(itemParams);
				String msg = "";
				if (orderCount > 0) { // 电子面单存在不 保存记录
					msg = (i + 2) + "行 电子面单号：" + expressOrderno + "已存在";
					// importRecord.setMsg(msg);
					// importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}

				// 匹配商品信息 code=#{code} and userid=#{userId}
				itemParams.clear();
				List<Item> itemList = new ArrayList<Item>();
				List<String> itemListCount = new ArrayList<String>();
				// Item item = null;
				String itemCodeStr = "";
				String barCodeStr = "";
				if (30L == userId) { // 德尔玛 特殊处理

					for (int j = 0; j < barCode.length; j++) {
						String barCodeStrTemp = barCode[j];
						barCodeStr = barCodeStr + barCodeStrTemp + ";";
						System.out.println("导入德尔玛商品条码：" + barCodeStrTemp);
						itemParams.clear();
						itemParams.put("userId", userId);
						itemParams.put("barCode", barCodeStrTemp.trim());
						Item item = itemDao.findItmeByBarCodeAndUserId(itemParams);
						if (item != null) {
							itemList.add(item);
							itemListCount.add(itemCount[j]);
						}
					}
				} else {
					for (int j = 0; j < itemCode.length; j++) {
						String itemCodeStrTemp = itemCode[j];
						System.out.println("导入商品名称：" + itemCodeStrTemp);
						itemParams.clear();
						itemCodeStr = itemCodeStr + itemCodeStrTemp + ";";
						itemParams.put("userId", userId);
						itemParams.put("name", itemCodeStrTemp.trim());
						Item item = itemDao.getItemByName(itemParams);
						if (item != null) {
							itemList.add(item);
							itemListCount.add(itemCount[j]);
						}
					}
				}
				if (30L != userId) { // 添加提示语言
					msg = (i + 2) + "行;" + itemCodeStr;
					importRecord.setItemCode(itemCodeStr);
				} else {
					msg = (i + 2) + "行 商品条码：" + barCodeStr;
					importRecord.setItemCode(barCodeStr);
				}

				if (itemList.size() == 0) { // 商品信息无法匹配
					if (30L != userId) {
						msg = (i + 2) + "行 商品名称：" + itemCodeStr + "无法匹配";
						importRecord.setItemCode(itemCodeStr);
					} else {
						msg = (i + 2) + "行 商品条码：" + barCodeStr + "无法匹配";
						importRecord.setItemCode(barCodeStr);
					}
					importRecord.setMsg(msg);
					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				// 匹配快递信息
				SystemItem systemItem = null;
				for (int j = 0, jsize = sysItemList.size(); j < jsize; j++) {
					SystemItem sysItem = sysItemList.get(j);
					if (express.indexOf(sysItem.getDescription()) > -1) {
						systemItem = sysItem;
						break;
					}
				}
				if (systemItem == null) { // 快递信息 无法匹配
					msg = (i + 2) + "行 订单快递信息：" + express + "无法匹配";
					importRecord.setMsg(msg);
					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}

				//ShipOrder order1 = shipOrderDao.getShipOrder(1L); // 正式环境
				 ShipOrder order1 = shipOrderDao.getShipOrder(1L); //测试
				System.out.println("order1:" + order1.getDetails().size());
				ShipOrder order = null;
				int zcIndex=0,zpIndex=0;
				Map<String,String> memcachedMap=new HashMap<String, String>();
				try {
					order = (ShipOrder) order1.clone();
					List<ShipOrderDetail> list = new ArrayList<ShipOrderDetail>();
					
					for (int j = 0, jsize = itemList.size(); j < jsize; j++) {
						ShipOrderDetail detail = (ShipOrderDetail) order1.getDetails().get(0).clone();
						Item item = itemList.get(j);
						
						System.out.println("item:" + item.getId());
						String itemCountStr = itemListCount.get(j);
						detail.setId(null);
						detail.setItem(item); // 商品信息
						if (itemCount != null) {
							detail.setNum(Long.valueOf(itemCountStr)); // 商品数量
							detail.setActualnum(Integer.valueOf(itemCountStr)); // 商品实际数量
						}
						
						memcachedMap.put(item.getBarCode(), item.getId()+"||"+detail.getNum()+"||"+item.getItemType());
						
						if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZC")){
							zcIndex+=detail.getNum();
						}
						if(item!=null && item.getItemType()!=null && item.getItemType().equals("ZP")){
							zpIndex+=detail.getNum();
						}
						detail.setTradeOrderNo(tbNumber);
						list.add(detail);
						detail.setOrder(order);
					}
					System.out.println("list:" + list.size());
					order.setDetails(list);

				} catch (CloneNotSupportedException e) {
					msg = (i + 2) + "行 商品" + itemCodeStr + "数据出现系统异常,请联系管理员..";
					importRecord.setMsg(msg);
					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				order.setId(null);
				order.setTradeId(null);
				order.setTradeBatchId(null);
				order.setCreateUser(user);
				order.setOriginPersion(user.getShopName());
				order.setExpressCompany(systemItem.getValue());// 快递信息
				order.setExpressOrderno(expressOrderno); // 快递面单号
				order.setOrderno(tbNumber); // 淘宝订单号
				order.setSource(ERPEnums.FURUN.getKey()); // 设置商品来源
				order.setType(ShipOrder.TYPE_DELIVER);
				order.setStatus(EntryOrderStatus.ENTRY_FINISH);
				order.setCreateDate(now);
				order.setLastUpdateDate(now);
				order.setReceiverName(userName);
				order.setReceiverMobile(userPhone);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.setPayTime(sdf.format(now));
				order.setReceiverState(state);
				order.setReceiverCity(city);
				order.setReceiverDistrict(district);
				order.setReceiverAddress(address);
				order.setItems("" + map.get("items"));
				order.setBuyerNick(buyerName);
				order.setBuyerMemo("");
				order.setBuyerMessage("");
				order.setSellerMemo("");
				order.setSellerMessage("");
				/**
				 * modify 2017-09-14 
				 * 操作人
				 * */	
				order.setOperatorName(accountId);
				this.saveEntryOrder(order);
				for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
					this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
				}
				Date date=new Date();
				memcachedMap.put("zcIndex", String.valueOf(zcIndex));
				memcachedMap.put("zpIndex", String.valueOf(zpIndex));
				memcachedMap.put("orderInfo", order.getId()+"||"+order.getTotalWeight()+"||"+order.getCreateUser().getId());
				memcachedMap.put("orderDate", ssdf.format(order.getLastUpdateDate()));
				memcachedMap.put("state", order.getReceiverState());
				memcachedMap.put("totalWeight", String.valueOf(order.getTotalWeight()));
				memcachedMap.put("user", String.valueOf(order.getCreateUser().getId()));
//				manager.add(order.getExpressOrderno(), memcachedMap);
				this.saveMemcachedInfo(order.getExpressOrderno(), memcachedMap);
				logger.error("订单 导入缓存时间"+(new Date().getTime()-date.getTime()));
				
				TradeUtil util = new TradeUtil();
				Trade adapter = null;
				try {
					adapter = util.adapter(order);
				} catch (Exception e) {
					logger.info(e.getMessage());
					retMap.put("msg", (i + 2) + "行 ApiException 单据转换失败");
					retMapList.add(retMap);
				}
				// update sc_ship_order set trade_id=#{trade_id} where id=#{id}
				Map<String, Object> updaeParams = new HashMap<String, Object>();
				Trade createTrade = tradeService.createTradeERP(adapter, order.getId(), tbNumber);
				updaeParams.put("trade_id", createTrade.getId());
				updaeParams.put("id", order.getId());
				shipOrderDao.updateShipOrderTradeId(updaeParams);
				retMap.put("msg", (i + 2) + "行 成功");
				retMapList.add(retMap);
				
				
				
				
			}
		}
		return retMapList;
	}

	public long findSendOrderImportCount() {
		return shipOrderDao.findSendOrderImportCount();
	}

	public List<ShipOrder> findSendOrderImport() {
		return shipOrderDao.findSendOrderImport();
	}

	public void updateImportShipOrderSub() {
		shipOrderDao.updateImportShipOrderSub();
	}

	public ShipOrder findShipOrderByExpressOrderno(String expressOrderno) {
		System.err.println(expressOrderno);
		return shipOrderDao.findShipOrderByExpressOrderno(expressOrderno);
	}

	public List<ShipOrder> findSendOrderWaitsByPages(int page, int rows, Map<String, Object> params) {
		int start = (page - 1) * rows;
		int offset = rows;
		params.put("start", start);
		params.put("offset", offset);
		List<ShipOrder> orders = this.shipOrderDao.findSendOrderWaitsByPages(params);
		return orders;
	}

	public void updateRemark(Map<String, Object> params) {
		this.shipOrderDao.updateRemark(params);
	}

	// public Map<String,Object> batchSplitShipOrder(String type,Long[]
	// tradeId,Long num) {
	// for(int i = 0, size =tradeId.length;i<size;i++){
	// splitShipOrderOperation(type, tradeId[i], num);
	// }
	// return null;
	// }

	public Map<String, Object> splitShipOrderOperation(String type, Long tradeId, Long num) {
		Trade trade = tradeDao.getTrade(tradeId);
		long id = 1L;
		logger.debug("splitShipOrderOperation" + tradeId + "userId:" + trade.getUser().getId());
		if (Trade.splitByTB.indexOf(String.valueOf(trade.getUser().getId())) >= 0) {
			ShipOrder tempOrder = this.shipOrderDao.getSendShipOrderByTradeId(trade.getId());
			id = tempOrder.getId();
		} else {
			id = Long.valueOf(trade.getLgAging());// 获得 出库单ID
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<ShipOrderDetail> detailList = shipOrderDao.getShipOrderDetailByOrderId(id);
		int size = detailList.size();

		boolean isSplit = false;
		// 快速拆单 按照行 拆
		if ("line".equals(type) && size > 1) {
			ShipOrder shipOrder = shipOrderDao.getShipOrder(id);
			isSplit = true;
			// 复制原始单据单头信息
			ShipOrder order = null;
			Date now = new Date();
			try {
				order = (ShipOrder) shipOrder.clone();
				order.setId(null);
				order.setTradeId(null);
				order.setCreateDate(now);
				order.setLastUpdateDate(now);

				order.setSourcePlatformCode(ShipOrder.SplitStatus.split_detail);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				logger.error(e);
				retMap.put("code", "500");
				retMap.put("msg", "原始单据抬头异常");
				e.printStackTrace();
			}

			for (int i = 1; i < size; i++) {
				// 辅助单据明细
				ShipOrderDetail shipOrderDetail = detailList.get(i);
				ShipOrderDetail detail = null;
				try {
					detail = (ShipOrderDetail) shipOrderDetail.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					logger.error(e);
					retMap.put("code", "500");
					retMap.put("msg", "出库单明细复制异常");
					e.printStackTrace();
				}
				detail.setId(null);
				detail.setOrder(order);

				detailList.add(detail);

				Map<String, Object> detailParams = new HashMap<String, Object>();
				// 删除订单明细数据
				detailParams.put("trade_id", trade.getId());
				detailParams.put("item_id", shipOrderDetail.getItem().getId());
				tradeDao.deleteTradeOrderNum(detailParams);
				// 删除出库单据明细数据
				detailParams.clear();
				detailParams.put("id", shipOrderDetail.getId());
				shipOrderDao.deleteShipOrderDetailNum(detailParams);

				// 查单明细信息
				List<ShipOrderDetail> detailListTemp = new ArrayList<ShipOrderDetail>();
				detailListTemp.add(detail);
				order.setDetails(detailListTemp);
				order.setOtherOrderNo(UUID.randomUUID().toString());
				this.saveEntryOrder(order);
				for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
					this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
				}

				TradeUtil util = new TradeUtil();
				Trade adapter = null;
				try {
					adapter = util.adapter(order);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(e.getMessage());
					retMap.put("code", "500");
					retMap.put("msg", "出库单生产异常");
					e.printStackTrace();
					return retMap;
				}
				Trade createTrade = tradeService.createTrade(adapter, order.getId());
			}
		} else if ("count".equals(type)) {
			ShipOrder shipOrder = shipOrderDao.getShipOrder(id);
			for (int i = 0; i < size; i++) {
				ShipOrderDetail shipOrderDetail = detailList.get(i);
				Long numTemp = shipOrderDetail.getNum();
				if (numTemp <= num) { // 不满足拆单最小数量
					continue;
				}
				isSplit = true;
				long index = numTemp / num; // 除数
				long indexTemp = numTemp % num;// 余数
				index = indexTemp > 0 ? index + 1 : index; // 余数不为0 时

				for (int k = 0; k < index; k++) {
					if (k != 0) { // 非第一条数据
						ShipOrder order = null;
						Date now = new Date();
						try {
							order = (ShipOrder) shipOrder.clone();
							order.setId(null);
							order.setTradeId(null);
							order.setCreateDate(now);
							order.setLastUpdateDate(now);
							order.setSourcePlatformCode(ShipOrder.SplitStatus.split_detail);
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							logger.error(e);
							e.printStackTrace();
							retMap.put("code", "500");
							retMap.put("msg", "原始单据抬头异常");
						}
						ShipOrderDetail detail = null;
						try {
							detail = (ShipOrderDetail) shipOrderDetail.clone();
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							logger.error(e);
							e.printStackTrace();
							retMap.put("code", "500");
							retMap.put("msg", "出库单明细复制异常");
						}
						detail.setId(null);
						detail.setNum(num); // 商品数量
						detail.setActualnum(num.intValue()); // 商品实际数量
						detail.setOrder(order);
						// 查单明细信息
						List<ShipOrderDetail> detailListTemp = new ArrayList<ShipOrderDetail>();
						detailListTemp.add(detail);
						order.setDetails(detailListTemp);
						order.setOtherOrderNo(UUID.randomUUID().toString());
						this.saveEntryOrder(order);
						for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
							this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
						}

						TradeUtil util = new TradeUtil();
						Trade adapter = null;
						try {
							adapter = util.adapter(order);
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(e.getMessage());
							retMap.put("code", "500");
							retMap.put("msg", "出库单生产异常");
							return retMap;
						}
						Trade createTrade = tradeService.createTrade(adapter, order.getId());
					} else {
						Map<String, Object> detailParams = new HashMap<String, Object>();
						// 修改订单明细数据
						detailParams.put("trade_id", trade.getId());
						detailParams.put("item_id", shipOrderDetail.getItem().getId());
						detailParams.put("num", indexTemp > 0 ? indexTemp : num);
						System.out.println("tradeParam:" + detailParams);
						tradeDao.updateTradeOrderNum(detailParams);

						// 修改出库单据明细数据
						detailParams.clear();
						detailParams.put("id", shipOrderDetail.getId());
						detailParams.put("num", indexTemp > 0 ? indexTemp : num);
						detailParams.put("actualnum", indexTemp > 0 ? indexTemp : num);
						System.out.println("shipParam:" + detailParams);
						shipOrderDao.updateShipOrderDetailNum(detailParams);
					}
				}
			}
		}
		// 标记主表是否拆单
		if (isSplit) {
			Map<String, Object> orderParams = new HashMap<String, Object>();
			orderParams.put("id", id);
			orderParams.put("sourcePlatformCode", ShipOrder.SplitStatus.split);
			shipOrderDao.updateShipOrderSourcePlatformCodeById(orderParams);
		}
		retMap.put("code", "200");
		retMap.put("msg", "成功");
		return retMap;
	}

	public static void main(String[] args) {
		System.out.println(2 / 1);
		System.out.println(2 % 1);
	}

	/**
	 * 新建自定义拆弹行
	 * 
	 * @param param
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> splitShipOrderLine(Map<String, Object> param) {
		Long tradeId = Long.valueOf("" + param.get("tradeId"));
		Trade trade = tradeDao.getTrade(tradeId);
		long id = 1L;
		logger.debug("splitShipOrderOperation:" + tradeId + "userId:" + trade.getUser().getId());
		if (Trade.splitByTB.indexOf(String.valueOf(trade.getUser().getId())) >= 0) {
			ShipOrder tempOrder = this.shipOrderDao.getSendShipOrderByTradeId(trade.getId());
			id = tempOrder.getId();
		} else {
			id = Long.valueOf(trade.getLgAging());// 获得 出库单ID
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<ShipOrderDetail> detailList = shipOrderDao.getShipOrderDetailByOrderId(id);
		int size = detailList.size();
		;
		ShipOrder shipOrder = shipOrderDao.getShipOrder(id);
		int k = 0;
		for (int i = 0; i < size; i++) {
			ShipOrderDetail shipOrderDetail = detailList.get(i);
			Object mapTemp = param.get("" + shipOrderDetail.getId());
			if (mapTemp != null) {
				k++;
			}
		}
		if (size == k) {
			retMap.put("code", "200");
			retMap.put("msg", "最少需要留一行数据");
			return retMap;
		} else if (k == 0) {
			retMap.put("code", "200");
			retMap.put("msg", "请选择拆单行记录");
			return retMap;
		}
		// 复制原始单据单头信息
		ShipOrder order = null;
		Date now = new Date();
		try {
			order = (ShipOrder) shipOrder.clone();
			order.setId(null);
			order.setTradeId(null);
			order.setCreateDate(now);
			order.setLastUpdateDate(now);
			order.setSourcePlatformCode(ShipOrder.SplitStatus.split_detail);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			logger.error(e);
			retMap.put("code", "500");
			retMap.put("msg", "原始单据抬头异常");
			e.printStackTrace();
		}
		List<ShipOrderDetail> detailListTemp = new ArrayList<ShipOrderDetail>();
		for (int i = 0; i < size; i++) {
			ShipOrderDetail shipOrderDetail = detailList.get(i);
			Object mapTemp = param.get("" + shipOrderDetail.getId());
			if (mapTemp == null) {
				continue;
			}
			// 辅助单据明细

			ShipOrderDetail detail = null;
			try {
				detail = (ShipOrderDetail) shipOrderDetail.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				logger.error(e);
				retMap.put("code", "500");
				retMap.put("msg", "出库单明细复制异常");
				e.printStackTrace();
			}
			detail.setId(null);
			detail.setOrder(order);
			Item item = itemDao.get(detail.getItem().getId());
			detail.setItem(item);

			Map<String, Object> detailParams = new HashMap<String, Object>();
			// 删除订单明细数据
			detailParams.put("trade_id", trade.getId());
			detailParams.put("item_id", shipOrderDetail.getItem().getId());
			tradeDao.deleteTradeOrderNum(detailParams);
			// 删除出库单据明细数据
			detailParams.clear();
			detailParams.put("id", shipOrderDetail.getId());
			shipOrderDao.deleteShipOrderDetailNum(detailParams);
			// 查单明细信息
			detailListTemp.add(detail);
		}
		order.setDetails(detailListTemp);
		order.setOtherOrderNo(UUID.randomUUID().toString());
		this.saveEntryOrder(order);
		for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
			this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
		}

		TradeUtil util = new TradeUtil();
		Trade adapter = null;
		try {
			adapter = util.adapter(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			retMap.put("code", "500");
			retMap.put("msg", "出库单生产异常");
			e.printStackTrace();
			return retMap;
		}
		Trade createTrade = tradeService.createTrade(adapter, order.getId());
		retMap.put("code", "200");
		retMap.put("msg", "行拆单成功");

		Map<String, Object> orderParams = new HashMap<String, Object>();
		orderParams.put("id", id);
		orderParams.put("sourcePlatformCode", ShipOrder.SplitStatus.split);
		shipOrderDao.updateShipOrderSourcePlatformCodeById(orderParams);
		return retMap;
	}

	/**
	 * 新建自定义拆弹 数量
	 * 
	 * @param param
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> splitShipOrder(Map<String, Object> param) {
		String type = "" + param.get("type");
		if ("line".equals(type)) {
			return splitShipOrderLine(param);
		}
		Long tradeId = Long.valueOf("" + param.get("tradeId"));
		Trade trade = tradeDao.getTrade(tradeId);
		long id = 1L;
		logger.debug("splitShipOrderOperation:" + tradeId + "userId:" + trade.getUser().getId());
		if (Trade.splitByTB.indexOf(String.valueOf(trade.getUser().getId())) >= 0) {
			ShipOrder tempOrder = this.shipOrderDao.getSendShipOrderByTradeId(trade.getId());
			id = tempOrder.getId();
		} else {
			id = Long.valueOf(trade.getLgAging());// 获得 出库单ID
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<ShipOrderDetail> detailList = shipOrderDao.getShipOrderDetailByOrderId(id);
		int size = detailList.size();
		;
		ShipOrder shipOrder = shipOrderDao.getShipOrder(id);

		boolean isSplit = false;
		long index = 0; // 除数
		long indexTemp = 0;// 余数

		Map<String, Object> quantityMap = new HashMap<String, Object>();

		for (int i = 0; i < size; i++) {
			ShipOrderDetail shipOrderDetail = detailList.get(i);
			Object mapTemp = param.get("" + shipOrderDetail.getId());
			System.out.println("shipOrderDetail:" + shipOrderDetail.getId());
			if (mapTemp != null) {
				long numTemp = shipOrderDetail.getNum();
				Long quantity = Long.valueOf("" + mapTemp);
				if (quantity >= numTemp) {
					retMap.put("code", "500");
					retMap.put("msg", "数量[" + quantity + "]大于或者等于订单数量");
					return retMap;
				} else {
					if (isSplit) { // 计算组合拆单 是否是可以拆分成同样数量的单据
						long index1 = numTemp / quantity; // 除数
						long indexTemp1 = numTemp % quantity;// 余数
						index1 = indexTemp1 > 0 ? index1 + 1 : index1; // 余数不为0
																		// 时
						if (index1 != index) {
							retMap.put("code", "500");
							retMap.put("msg", "组合拆单数量异常,不能组合成有效拆单数量");
							return retMap;
						}
					}
					index = numTemp / quantity; // 除数
					indexTemp = numTemp % quantity;// 余数
					quantityMap.put("" + shipOrderDetail.getId(), indexTemp);
					index = indexTemp > 0 ? index + 1 : index; // 余数不为0 时
					isSplit = true;
				}
			}
		}

		for (int k = 0; k < index; k++) {
			Double weight = 0d;
			if (k != 0) { // 非第一条数据
				ShipOrder order = null;
				Date now = new Date();
				try {
					order = (ShipOrder) shipOrder.clone();
					order.setId(null);
					order.setTradeId(null);
					order.setCreateDate(now);
					order.setLastUpdateDate(now);
					order.setSourcePlatformCode(ShipOrder.SplitStatus.split_detail);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					logger.error(e);
					e.printStackTrace();
					retMap.put("code", "500");
					retMap.put("msg", "原始单据抬头异常");
				}

				ShipOrderDetail detail = null;
				List<ShipOrderDetail> detailListTemp = new ArrayList<ShipOrderDetail>();
				for (int i = 0; i < size; i++) {
					ShipOrderDetail shipOrderDetail = detailList.get(i);
					Object mapTemp = param.get("" + shipOrderDetail.getId());
					if (mapTemp != null) {
						try {
							detail = (ShipOrderDetail) shipOrderDetail.clone();
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							logger.error(e);
							e.printStackTrace();
							retMap.put("code", "500");
							retMap.put("msg", "出库单明细复制异常");
						}
						Long quantity = Long.valueOf("" + mapTemp);
						detail.setId(null);
						Item item = itemDao.get(detail.getItem().getId());
						
						weight = weight + item.getWeight()*quantity;
						
						detail.setItem(item);
						detail.setNum(quantity); // 商品数量
						detail.setActualnum(quantity.intValue()); // 商品实际数量
						detail.setOrder(order);
						detailListTemp.add(detail);
					}
				}
				// 查单明细信息
				order.setDetails(detailListTemp);
				order.setOtherOrderNo(UUID.randomUUID().toString());
				
				order.setTotalWeight(weight);
				
				this.saveEntryOrder(order);
				for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
					this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
				}

				TradeUtil util = new TradeUtil();
				Trade adapter = null;
				try {
					adapter = util.adapter(order);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(e.getMessage());
					retMap.put("code", "500");
					retMap.put("msg", "出库单生产异常");
					return retMap;
				}

				Trade createTrade = tradeService.createTrade(adapter, order.getId());
			} else {
				Map<String, Object> detailParams = new HashMap<String, Object>();
				for (int i = 0; i < size; i++) {
					ShipOrderDetail shipOrderDetail = detailList.get(i);
					Object mapTemp = param.get("" + shipOrderDetail.getId());
					if (mapTemp != null) {
						Long quantity = Long.valueOf("" + mapTemp);
						indexTemp = Long.valueOf("" + quantityMap.get("" + shipOrderDetail.getId()));
						// 修改订单明细数据
						detailParams.put("trade_id", trade.getId());
						detailParams.put("item_id", shipOrderDetail.getItem().getId());
						detailParams.put("num", indexTemp > 0 ? indexTemp : quantity);
						System.out.println("tradeParam:" + detailParams);
						tradeDao.updateTradeOrderNum(detailParams);
						
						//重量
						Item item = this.itemDao.get(shipOrderDetail.getItem().getId());
						weight = weight + item.getWeight()*quantity;
						
						
						// 修改出库单据明细数据
						detailParams.clear();
						detailParams.put("id", shipOrderDetail.getId());
						detailParams.put("num", indexTemp > 0 ? indexTemp : quantity);
						detailParams.put("actualnum", indexTemp > 0 ? indexTemp : quantity);
						System.out.println("shipParam:" + detailParams);
						shipOrderDao.updateShipOrderDetailNum(detailParams);
					}
				}
			}		
		}
		Map<String, Object> orderParams = new HashMap<String, Object>();
		orderParams.put("id", id);
		orderParams.put("sourcePlatformCode", ShipOrder.SplitStatus.split);
		shipOrderDao.updateShipOrderSourcePlatformCodeById(orderParams);
		retMap.put("code", "200");
		retMap.put("msg", "拆单成功");
		return retMap;
	}

	/**
	 * 拆单逻辑
	 * 
	 * @param param
	 */
	@Transactional
	public Map<String, Object> splitShipOrder1(Map<String, Object> param) {

		Long id = (Long) param.get("id");
		List<Map<String, Long>> list = (List<Map<String, Long>>) param.get("detailList");
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 辅助原始单据单头信息
		ShipOrder shipOrder = shipOrderDao.getShipOrder(id);
		ShipOrder order = null;
		Date now = new Date();
		try {
			order = (ShipOrder) shipOrder.clone();
			order.setId(null);
			order.setTradeId(null);
			order.setCreateDate(now);
			order.setLastUpdateDate(now);
			order.setSourcePlatformCode(ShipOrder.SplitStatus.split_detail);
		} catch (CloneNotSupportedException e) {
			logger.error(e);
			retMap.put("code", "500");
			retMap.put("msg", "原始单据异常");
			return retMap;
		}

		// 设置已分单标记
		Map<String, Object> sourceParams = new HashMap<String, Object>();
		// sourcePlatformCode = #{sourcePlatformCode}
		// where id=#{id}
		sourceParams.put("id", id);
		sourceParams.put("sourcePlatformCode", ShipOrder.SplitStatus.split);
		shipOrderDao.updateShipOrderSourcePlatformCodeById(sourceParams);

		// 明细信息
		List<ShipOrderDetail> detailList = new ArrayList<ShipOrderDetail>();
		for (int i = 0, size = list.size(); i < size; i++) {
			Map<String, Long> map = list.get(i);
			Long detailId = map.get("detailId");
			Map<String, Object> shipDetailMap = new HashMap<String, Object>();

			shipDetailMap.put("id", detailId);
			ShipOrderDetail shipOrderDetail = shipOrderDao.getShipOrderDetail(shipDetailMap);
			// update sc_ship_order_detail set actualnum=#{actualnum}, num =
			// #{num}
			// where id=#{id}

			Map<String, Object> detailNumParams = new HashMap<String, Object>();
			detailNumParams.put("id", detailId);
			Long quantity = map.get("quantity");

			Long num = shipOrderDetail.getNum() - quantity;
			detailNumParams.put("num", num);
			detailNumParams.put("actualnum", num);
			if (num == 0) {
				shipOrderDao.deleteShipOrderDetailNum(detailNumParams);
			} else {
				shipOrderDao.updateShipOrderDetailNum(detailNumParams);
			}

			Trade trade = tradeDao.findTradeByLgAging("" + shipOrder.getId());

			detailNumParams.clear();
			detailNumParams.put("num", num);
			detailNumParams.put("trade_id", trade.getId());
			detailNumParams.put("item_id", shipOrderDetail.getItem().getId());
			if (num == 0) {
				tradeDao.deleteTradeOrderNum(detailNumParams);
			} else {
				tradeDao.updateTradeOrderNum(detailNumParams);
			}

			ShipOrderDetail detail = null;
			try {
				detail = (ShipOrderDetail) shipOrderDetail.clone();
			} catch (CloneNotSupportedException e) {
				logger.error(e);
				e.printStackTrace();
				retMap.put("code", "500");
				retMap.put("msg", "订单复制异常");
				return retMap;
			}
			detail.setId(null);
			detail.setNum(quantity == null ? 0 : quantity); // 商品数量
			detail.setActualnum(quantity == null ? 0 : quantity.intValue()); // 商品实际数量
			detail.setOrder(order);
			detailList.add(detail);
		}
		order.setDetails(detailList);

		this.saveEntryOrder(order);

		for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
			this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
		}

		TradeUtil util = new TradeUtil();
		Trade adapter = null;
		try {
			adapter = util.adapter(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			retMap.put("code", "500");
			retMap.put("msg", "出库单生产异常");
			return retMap;
		}
		// Map<String,Object> updaeParams = new HashMap<String, Object>();
		Trade createTrade = tradeService.createTrade(adapter, order.getId());
		// updaeParams.put("trade_id", createTrade.getId());
		// updaeParams.put("id", order.getId());
		// shipOrderDao.updateShipOrderTradeId(updaeParams);
		retMap.put("code", "200");
		retMap.put("msg", "订单拆单成功");
		return retMap;
	}

	public Long getCountByDate(Map<String, Object> params) {
		return shipOrderDao.getCountByDate(params);
	}

	public Long getSuccessCountByDate(Map<String, Object> params) {
		return shipOrderDao.getSuccessCountByDate(params);
	}

	public Long getCheckSuccessCountByDate(Map<String, Object> params) {
		return shipOrderDao.getCheckSuccessCountByDate(params);
	}

	public Long getCheckFailCountByDate(Map<String, Object> params) {
		return shipOrderDao.getCheckFailCountByDate(params);
	}

	public List<ShipOrder> selectShipOrderNotEXISTS(Map<String, Object> params) {
		return shipOrderDao.selectShipOrderNotEXISTS(params);
	}

	public void updateShipOrderById(ShipOrder order) {
		this.shipOrderDao.updateShipOrderById(order);
	}

	/**
	 * COPY
	 * 
	 * @param shipOrderId
	 * @return
	 */
	public Map<String, Object> copyShipOrder(Long shipOrderId) {

		ShipOrder shipOrder = shipOrderDao.getShipOrder(shipOrderId);
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (shipOrder == null) {
			retMap.put("code", "404");
			retMap.put("msg", "找不到指定订单号");
			return retMap;
		}
		List<ShipOrderDetail> detailList = shipOrderDao.getShipOrderDetailByOrderId(shipOrderId);
		int size = detailList.size();

		ShipOrder order = null;
		Date now = new Date();

		try {
			order = (ShipOrder) shipOrder.clone();
			order.setId(null);
			order.setTradeId(null);
			order.setCreateDate(now);
			order.setLastUpdateDate(now);
			order.setStatus(EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
			order.setExpressOrderno(null);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			logger.error(e);
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("msg", "原始单据抬头异常");
			return retMap;
		}

		ShipOrderDetail detail = null;

		List<ShipOrderDetail> detailListTemp = new ArrayList<ShipOrderDetail>();
		for (int i = 0; i < size; i++) {
			ShipOrderDetail shipOrderDetail = detailList.get(i);
			try {
				detail = (ShipOrderDetail) shipOrderDetail.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				logger.error(e);
				e.printStackTrace();
				retMap.put("code", "500");
				retMap.put("msg", "出库单明细复制异常");
			}
			detail.setId(null);
			Item item = itemDao.get(detail.getItem().getId());
			detail.setItem(item);
			detail.setOrder(order);
			detailListTemp.add(detail);
		}
		// 查单明细信息
		order.setDetails(detailListTemp);
		order.setOtherOrderNo(UUID.randomUUID().toString());
		this.saveEntryOrder(order);
		for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
			this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
		}

		TradeUtil util = new TradeUtil();
		Trade adapter = null;
		try {
			adapter = util.adapter(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			retMap.put("code", "500");
			retMap.put("msg", "出库单生产异常");
			return retMap;
		}

		Trade createTrade = tradeService.createTrade(adapter, order.getId());

		retMap.put("code", "200");
		retMap.put("msg", "生产成功");

		return retMap;
	}

	/**
	 * 工作组取订单商品数量信息
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getShipOrderItemByWorkGroup(Map<String, Object> params) {
		// Object obj=
		// System.err.println("getShipOrderItemByWorkGroup"+obj);
		return this.shipOrderDao.getShipOrderItemByWorkGroup(params);
	}

	/**
	 * 订单导入打印
	 * */
	public List<Map<String, Object>> importOrdersNew(Map<String, Object> params) {
		// 返回信息 集合
		List<Map<String, Object>> retMapList = null;

		if (params != null) {
			System.err.println("ERP导入开始！！！！！");
			System.out.println("ERP导入！！！！");

			retMapList = new ArrayList<Map<String, Object>>();
			
			// 用户信息
			Long userId = Long.valueOf("" + params.get("userId"));
			User user = userService.getUser(userId);
			// 仓库信息
			Centro centro = new Centro();
			Long centroId = Long.valueOf("" + params.get("centroId"));
			centro.setId(centroId);
			
			String accountId = "" + params.get("accountId");
			
			// 获得快系统快递 信息
			List<SystemItem> sysItemList = systemItemDao.findSystemItemByType(StoreSystemItemEnums.LOGISTICS.getKey());

			// 单条 数据信息操作
			List<Map<String, Object>> orderListMap = (List<Map<String, Object>>) params.get("orderList");
			int size = orderListMap.size();
			Date now = new Date(); // 时间初始化
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = orderListMap.get(i);
				Map<String, Object> retMap = new HashMap<String, Object>();

				String[] itemCode = (String[]) map.get("itemCode");// 商品code
				String[] itemCount = (String[]) map.get("itemCount");// 商品数量
				String tbNumber = "" + map.get("tbNumber");// 淘宝订单号
				
 				Map<String, Object> pMap = new HashMap<String, Object>();
 				pMap.put("orderno", tbNumber);
 				pMap.put("userId", userId);
 				System.err.println("pMap:"+pMap);
 				int count = this.shipOrderDao.getShipOrderCount(pMap);
 				System.err.println("size:"+count);
				
				// 菜鸟面单号
//				String expressOrderno = ("" + map.get("expressOrderno")).trim();
				String state = "" + map.get("state");// 省份
				String city = "" + map.get("city");// 市区
				String district = "" + map.get("district");// 区域
				String address = "" + map.get("address");// 详细地址
				System.out.println((i + 2) + "行"+" address:"+address);
				String userName = "" + map.get("userName"); // 收货人名
				System.out.println("userName:"+userName);
				String userPhone = "" + map.get("userPhone"); // 收货人手机号码
				String express = "" + map.get("express"); // 快递公司名称
				String buyerName = "" + map.get("buyerName"); // 买家昵称
				Object object = map.get("barCode");
			
				String[] barCode = null;
				if (object != null) { // 商品条码
					barCode = (String[]) map.get("barCode");
				}
				String items = "" + map.get("items"); // 本地商品 基本信息

				// 导入记录初始化
				ImportRecord importRecord = new ImportRecord();
				importRecord.setCentroId(centroId);
				importRecord.setUserId(userId);
				importRecord.setErp("PRINT");
				importRecord.setCreateDate(now);
				importRecord.setExpressOrderno(tbNumber);
				importRecord.setCompany(express);
				importRecord.setStatus(ImportRecord.ImportRecordStatus.FAIL);
				
				// 匹配商品信息 code=#{code} and userid=#{userId}
				Map<String, Object> itemParams = new HashMap<String, Object>();
				String msg = "";
				
 				if (count>0) {
 					msg = (i + 2) + "行 订单号：" + tbNumber + "已存在！";
 					importRecordDao.save(importRecord);
 					retMap.put("msg", msg);
 					retMapList.add(retMap);
 					continue;			
 				}
				
				List<Item> itemList = new ArrayList<Item>();
				List<String> itemListCount = new ArrayList<String>();
				// Item item = null;
				String itemCodeStr = "";
				String barCodeStr = "";
				if (30L == userId) { // 德尔玛 特殊处理

					for (int j = 0; j < barCode.length; j++) {
						String barCodeStrTemp = barCode[j];
						barCodeStr = barCodeStr + barCodeStrTemp + ";";
						System.out.println("导入德尔玛商品条码：" + barCodeStrTemp);
						itemParams.clear();
						itemParams.put("userId", userId);
						itemParams.put("barCode", barCodeStrTemp.trim());
						Item item = itemDao.findItmeByBarCodeAndUserId(itemParams);
						if (item != null) {
							itemList.add(item);
							itemListCount.add(itemCount[j]);
						}
					}
				} else {
					for (int j = 0; j < itemCode.length; j++) {
						String itemCodeStrTemp = itemCode[j];
						System.out.println("导入商品名称：" + itemCodeStrTemp);
						itemParams.clear();
						itemCodeStr = itemCodeStr + itemCodeStrTemp + ";";
						itemParams.put("userId", userId);
						itemParams.put("name", itemCodeStrTemp.trim());
						Item item = itemDao.getItemByName(itemParams);
						if (item != null) {
							itemList.add(item);
							itemListCount.add(itemCount[j]);
						}
					}
				}
				if (30L != userId) { // 添加提示语言
					msg = (i + 2) + "行;" + itemCodeStr;
					importRecord.setItemCode(itemCodeStr);
				} else {
					msg = (i + 2) + "行 商品条码：" + barCodeStr;
					importRecord.setItemCode(barCodeStr);
				}

				if (itemList.size() == 0) { // 商品信息无法匹配
					if (30L != userId) {
						msg = (i + 2) + "行 商品名称：" + itemCodeStr + "无法匹配";
						importRecord.setItemCode(itemCodeStr);
					} else {
						msg = (i + 2) + "行 商品条码：" + barCodeStr + "无法匹配";
						importRecord.setItemCode(barCodeStr);
					}
					importRecord.setMsg(msg);
					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				// 匹配快递信息
				SystemItem systemItem = null;
				for (int j = 0, jsize = sysItemList.size(); j < jsize; j++) {
					SystemItem sysItem = sysItemList.get(j);
					if (express.indexOf(sysItem.getDescription()) > -1) {
						systemItem = sysItem;
						break;
					}
				}
				if (systemItem == null) { // 快递信息 无法匹配
					msg = (i + 2) + "行 订单快递信息：" + express + "无法匹配";
					importRecord.setMsg(msg);
					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				
				ShipOrder order1 = shipOrderDao.getShipOrder(49488L); 
// 				ShipOrder order1 = shipOrderDao.getShipOrder(1L); 
				System.err.println("order1:" + order1.getDetails().size());
				ShipOrder order = null;
				
				Double weight = 0d;
				
				try {
					order = (ShipOrder) order1.clone();
					List<ShipOrderDetail> list = new ArrayList<ShipOrderDetail>();

					for (int j = 0, jsize = itemList.size(); j < jsize; j++) {
						ShipOrderDetail detail = (ShipOrderDetail) order1.getDetails().get(0).clone();
						Item item = itemList.get(j);
						System.err.println("item:" + item.getId());
						String itemCountStr = itemListCount.get(j);
						detail.setId(null);
						detail.setItem(item); // 商品信息
						if (itemCount != null) {
							detail.setNum(Long.valueOf(itemCountStr)); // 商品数量
							detail.setActualnum(Integer.valueOf(itemCountStr)); // 商品实际数量
						}
						//计算重量
						weight = weight + item.getWeight()*detail.getNum();
						
						detail.setTradeOrderNo(tbNumber);
						list.add(detail);
						detail.setOrder(order);
					}
					System.out.println("list:" + list.size());
					order.setDetails(list);

				} catch (CloneNotSupportedException e) {
					msg = (i + 2) + "行 商品" + itemCodeStr + "数据出现系统异常,请联系管理员..";
					importRecord.setMsg(msg);
					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				order.setId(null);
				order.setTradeId(null);
				order.setTradeBatchId(null);
				order.setCreateUser(user);
				order.setOriginPersion(user.getShopName());
				order.setShopNick(user.getShopName());
				order.setExpressCompany(systemItem.getValue());// 快递信息
				order.setExpressOrderno(null); // 快递面单号
				order.setOrderno(tbNumber); // 淘宝订单号
				order.setSource("import"); // 设置商品来源
				order.setType(ShipOrder.TYPE_DELIVER);
				order.setStatus(EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);
				order.setCreateDate(now);
				order.setLastUpdateDate(now);
				order.setReceiverName(userName);
				order.setReceiverMobile(userPhone);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.setPayTime(sdf.format(now));
				order.setReceiverState(state);
				order.setReceiverCity(city);
				order.setReceiverDistrict(district);
				order.setReceiverAddress(address);
				order.setItems(items);
				order.setBuyerNick(buyerName);
				order.setBuyerMemo("");
				order.setBuyerMessage("");
				order.setSellerMemo("");
				order.setSellerMessage("");
				order.setTradeBatch(tbNumber);
				order.setTotalWeight(weight);	
				order.setOperatorName(accountId);
				this.saveEntryOrder(order);
				for (int j = 0; order.getDetails() != null && j < order.getDetails().size(); j++) {
					this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
				}
				TradeUtil util = new TradeUtil();
				Trade adapter = null;
				try {
					adapter = util.adapter(order);
				} catch (Exception e) {
					logger.info(e.getMessage());
					retMap.put("msg", (i + 2) + "行 ApiException 单据转换失败");
					retMapList.add(retMap);
				}
				// update sc_ship_order set trade_id=#{trade_id} where id=#{id}
				Map<String, Object> updaeParams = new HashMap<String, Object>();
				Trade createTrade = tradeService.createTradePrint(adapter, order.getId(), tbNumber);
				updaeParams.put("trade_id", createTrade.getId());
				updaeParams.put("id", order.getId());
				shipOrderDao.updateShipOrderTradeId(updaeParams);
				retMap.put("msg", (i + 2) + "行 成功");
				retMapList.add(retMap);

//				Date date=new Date();
//				saveMemcachedInfo(order.getExpressOrderno(), memcachedMap, manager);
//				logger.error("订单 导入缓存时间"+(new Date().getTime()-date.getTime()));
				

			}
		}
		return retMapList;
	}
	 
	
	private void saveMemcachedInfo(String key,Map<String,String>  value){
		this.redisProxy.set(key.getBytes(), ObjectTranscoder.serialize(value));
	}
	
	
}
