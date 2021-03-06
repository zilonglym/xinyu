package com.graby.store.service.qm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.base.StoreConstants;
import com.graby.store.dao.jpa.EntryOrderDetailJpaDao;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemInventory;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrder.EntryOrderStatus;
import com.graby.store.entity.ShipOrder.SendOrderStatus;
import com.graby.store.entity.ShipOrderCancel;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderReturn;
import com.graby.store.entity.StoreProcess;
import com.graby.store.entity.StoreProcessItem;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.entity.enums.qm.XmlEnum;
import com.graby.store.remote.ShipOrderReturnRemote;
import com.graby.store.service.base.CentroService;
import com.graby.store.service.base.SystemOperatorRecordService;
import com.graby.store.service.base.UserService;
import com.graby.store.service.inventory.Accounts;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.inventory.QmInventoryService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.trade.TradeUtil;
import com.graby.store.service.util.IRedisProxy;
import com.graby.store.service.util.ObjectTranscoder;
import com.graby.store.service.wms.ProcessService;
import com.graby.store.service.wms.ShipOrderCancelService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.util.qm.XmlUtil;
import com.schooner.MemCached.ObjectTransCoder;
import com.taobao.api.ApiException;


/**
 * 奇门接口具体事务处理类
 *  入库，出库，发货等单据的创建、
 *  QmConfirmService   确认接口
 *  QmSyncQueryService 初始化
 * @author 杨敏
 *
 */
@Component
public class QmSyncService{


	public static final Logger logger = Logger.getLogger(QmSyncService.class);
	
	/**
	 * 不能用奇门的itemId取商品的商家ownerCode
	 */
	private String exinclude_itemId="zc16473350918";
	
	private String include_itemId="zc16473350920";
	
	@Autowired
	private CentroService centroService;
	@Autowired
	private UserService userService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private EntryOrderDetailJpaDao entryOrderDetailJpaDao;
	@Autowired
	private ShipOrderCancelService cancelService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private SystemOperatorRecordService operatorService;
	@Autowired
	private ShipOrderReturnRemote shipOrderReturnRemote;
	@Autowired
	private QmInventoryService qmInventoryService;
	@Autowired
	private IRedisProxy redisProxy;
	/**
	 * 商品商步
	 */
	@Transactional
	public String itemSync(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SystemOperatorRecord record=new SystemOperatorRecord();
		try {
			Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
			Map<String, Object> xmlMap = map;
			
			String actionType = (String) xmlMap.get("actionType");
			String msg = "";
			if (actionType != null && actionType.equals("add")) {
				msg = this.updateItem(xmlMap,record);
			} else if (actionType != null && actionType.equals("update")) {
				msg = this.updateItem(xmlMap,record);
			} else {
				msg = "参数actionType异常";
				System.out.println(msg+"*******************");
			}
			resultMap.put("flag", "success");
			resultMap.put("message", "接口调用成功!");
			resultMap.put("itemId", msg);
			resultMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			resultMap.put("flag", "failure");
			resultMap.put("message", e.getMessage());
			resultMap.put("itemId", "");
		}
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		logger.info(resultXml);
		record.setOperatorModel(OperatorModel.QM_ITEM);
		record.setDescription("商品同步:"+xmlStr);
		record.setTime(new Date());
		this.operatorService.insert(record);
		return resultXml;
	}
	
	
	
	
	/**
	 * 商品批量商步
	 */
	@Transactional
	public String itemBatchSync(String xmlStr) throws Exception {
		return null;
	}

	/**
	 * 组合商品
	 */
	public String combineitem(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Item item = new Item();
		String code = (String) map.get("itemCode");
		item.setCode(code);
		item.setTitle("组合商品");
		item.setShortName("");
		item.setBarCode("");
		item.setPackageMaterial("");
		item.setType("");
		// 保存新商品，减去原商品库存数量
		this.itemService.saveItem(item);
		Item nItem = null;
		Map<String, Object> items = (Map<String, Object>) map.get("items");
		List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			// 迭代减少相应的库存
			Map<String, Object> obj = itemList.get(0);
			String itemCode = (String) obj.get("itemCode");
			int quantity = (Integer) obj.get("quantity");
			//nItem = this.itemService.getItemByCode(itemCode);
			// ItemInventory tory=this.inventoryService.in
			// 找到商品的库存信息进行修改，并写入相应的流水信息

		}
		ItemInventory inventory = new ItemInventory();
		inventory.setItem(item);
		inventory.setNum(1);
		// inventory.setCentro();
		// this.inventoryService.
		return null;
	}

	/**
	 * 入库单创建
	 */

	public String entryorder(String xmlStr) throws Exception {
		ShipOrder order = new ShipOrder();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);

			// 1.保存入库单据信息

			order.setType(ShipOrder.TYPE_ENTRY);
			buildShipOrder(order, map);
			order.setStatus(EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);// 入库单默认为等待商家发
			// 持久化相关信息
			this.shipOrderService.saveEntryOrder(order);
			for (int i = 0; order.getDetails() != null && i < order.getDetails().size(); i++) {
				this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
			resultMap.put("message", e.getMessage());
			resultMap.put("entryOrderId", "");
		}

		// entryOrderId
		resultMap.put("flag", "success");
		resultMap.put("message", "接口调用成功!");
		resultMap.put("entryOrderId", order.getId());
		resultMap.put("code", "200");
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		System.err.println(resultXml);
		return resultXml;
	}

	/**
	 * 退货入库单创建
	 */

	public String returnorder(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ShipOrder order = new ShipOrder();
		try {
			order.setType(ShipOrder.TYPE_ENTRY_RETURN);
			buildShipOrderReturn(order, map);
			// 持久化相关信息
			this.shipOrderService.saveEntryReturnOrder(order);
			for (int i = 0; order.getDetails() != null && i < order.getDetails().size(); i++) {
				this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
			}
			resultMap.put("flag", "success");
			resultMap.put("message", "退货入库创建接口调用成功!");
			resultMap.put("returnOrderId", order.getId());
			resultMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
			resultMap.put("message", e.getMessage());
			resultMap.put("itemId", "");
		}
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		System.err.println(resultXml);
		return resultXml;
	}

	/**
	 * 出库单创建
	 */
	public String stockout(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ShipOrder order = new ShipOrder();
		try {
			order.setType(ShipOrder.TYPE_SEND);
			order.setStatus(SendOrderStatus.WAIT_EXPRESS_RECEIVED);
			buildStockoutShipOrder(order, map);
			// 持久化相关信息
			this.shipOrderService.saveEntryOrder(order);
			for (int i = 0; order.getDetails() != null && i < order.getDetails().size(); i++) {
				this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
			}
		//	User user=this.userService.getUser(order.getCreateUser().getId());
			Centro centro=this.centroService.findCentro(order.getCentroId());
			String deliveryOrderId="zhongcang";
			if(centro!=null){
				deliveryOrderId=centro.getCode();
			}
			resultMap.put("flag", "success");
			resultMap.put("message", "出库单创建接口调用成功!");
			resultMap.put("deliveryOrderId", deliveryOrderId);
			resultMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
			resultMap.put("message", e.getMessage());
			resultMap.put("itemId", "");
		}
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		System.err.println(resultXml);
		return resultXml;
	}

	/**
	 * 发货单创建
	 */
	 
	public synchronized String deliveryorder(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ShipOrder order = new ShipOrder();
		try {
			order.setType(ShipOrder.TYPE_DELIVER);
			order.setStatus(EntryOrderStatus.ENTRY_WAIT_SELLER_SEND);// 发货单的初始状态
			order.setSource(Trade.SourceFlag.SourceFlag_QM);//打个标记，说明此单据是从奇门同步过来
			buildDeliveryOrderShipOrder(order, map);
			/**
			 * 判断当前单据的发货单号在系统中是否存在，如果存在则单据不能再次保存
			 */
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("orderNo", order.getOrderno());
			params.put("userId", order.getCreateUser().getId());
			List<ShipOrder> orderList=this.shipOrderService.findeSendOrderByList(params, 1, 10);
			if(orderList!=null && orderList.size()>0){
				resultMap.put("flag", "failure");
				resultMap.put("message", "此发货单在系统中已存在!");
				resultMap.put("deliveryOrderId", order.getId());
				resultMap.put("code", "200");
			}else {
				Map<String,Object> retMap=this.isHaveInventory(order);
				String ret=(String) retMap.get("ret");
				if(ret!=null && ret.equals("0")){
					resultMap.put("flag", "failure");
					resultMap.put("message", "库存问题！"+retMap.get("msg"));
				}else{
					/**
					 * 加入时间戳，做为单据对其它系统平台的唯一关键字
					 */
					order.setOtherOrderNo(UUID.randomUUID().toString());
					// 持久化相关信息
					this.shipOrderService.saveEntryOrder(order);
					for (int i = 0; order.getDetails() != null && i < order.getDetails().size(); i++) {
						ShipOrderDetail detail=order.getDetails().get(i);
						this.entryOrderDetailJpaDao.save(detail);
						/**
						 * 商品的已使用库存增加相应的数量
						 */
						logger.debug("qm item useNum:"+detail.getItem().getId()+"|"+detail.getNum());
						this.inventoryService.updateUserNum(1l, detail.getItem().getId(), Accounts.CODE_SALEABLE, detail.getNum());
					}
					
					
					TradeUtil  util  = new TradeUtil();
					Trade adapter = util.adapter(order);
					
					tradeService.createTrade(adapter, order.getId());
					//order.setTradeId(adapter.getId());
					//更新
					
					resultMap.put("flag", "success");
					resultMap.put("message", "发货单创建接口调用成功!");
					resultMap.put("deliveryOrderId", order.getId());
					resultMap.put("code", "200");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
			resultMap.put("message", "系统异常!"+e.getMessage()+"|"+e.getLocalizedMessage());
			resultMap.put("itemId", "");
		}
		
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
//		logger.info("同步订单:"+resultXml);
		/**
		 * 写入操作日志
		 */
		SystemOperatorRecord record=new SystemOperatorRecord();
		record.setOperatorModel(OperatorModel.QM_TRADE);
		record.setTime(new Date());
		record.setDescription(xmlStr);
		record.setUser(order.getCreateUser()!=null?order.getCreateUser().getId().intValue():0);
		this.operatorService.insert(record);
		return resultXml;
	}

	/**
	 * 判断当前订单的库存，是不是能允许出库单的同步
	 * @param shipOrder
	 * @return
	 */
	private Map<String,Object> isHaveInventory(ShipOrder shipOrder){
		List<ShipOrderDetail> detailList=shipOrder.getDetails();
		Map<String,Object> resultMap=new HashMap<String, Object>();
		String ret="1";
		int userNum=0;
		for(int i=0;detailList!=null && i<detailList.size();i++){
			ShipOrderDetail detail=detailList.get(i);
			Item item=detail.getItem();
			ItemInventory inventory=this.inventoryService.getItemInventory(1L, item.getId(), Accounts.CODE_SALEABLE.toString());
			if(inventory==null ){
				userNum=0+Long.valueOf(detail.getNum()).intValue();
			}else{
				userNum=inventory.getUseNum()<0?0:inventory.getUseNum()+Long.valueOf(detail.getNum()).intValue();
			}
			if(inventory==null  || inventory.getNum()<0 || inventory.getNum()<userNum){
				ret="0";
				System.err.println("isHaveInventory:"+item.getId());
				Item itemObj=this.itemService.getItem(item.getId());
				System.err.println("isHaveInventory"+itemObj);
				resultMap.put("msg", itemObj==null?item.getId():itemObj.getTitle()+"数量不足！");
				logger.info("itemId"+item.getId()+"useNum:"+userNum+"inventory.num"+inventory.getNum()+"detailNum:"+detail.getNum()+itemObj.getTitle()+"数量不足！");
				break;
			}
		}
		resultMap.put("ret", ret);
		return resultMap;
	}
	
	/**
	 * 发货单查询
	 */

	public String deliveryQuery(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		String orderCode = (String) map.get("orderCode");
		String orderId = (String) map.get("orderId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("orderId", orderId);
		params.put("type", ShipOrder.TYPE_DELIVER);
		// 查询发货单
		List<ShipOrder> list = this.shipOrderService.queryShipOrderCodeIdAndType(params);
		ShipOrder shipOrder = null;
		if (list != null && list.size() > 0) {
			shipOrder = list.get(0);
		}
		// 构建返回列表
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> order = new HashMap<String, Object>();
		order.put("deliveryOrderCode", shipOrder.getPreDeliveryOrderCode());
		order.put("deliveryOrderId", shipOrder.getPreDeliveryOrderId());
		order.put("orderType", shipOrder.getOrderType());
		order.put("operateTime", shipOrder.getOperateTime());
		Map<String, Object> invoices = new HashMap<String, Object>();
		order.put("invoices", invoices);
		List<Map<String, Object>> invoice = new ArrayList<Map<String, Object>>();
		Map<String, Object> invoiceItem = new HashMap<String, Object>();
		invoice.add(invoiceItem);
		invoices.put("invoice", invoice);
		invoiceItem.put("header", shipOrder.getInvoiceHeader());
		invoiceItem.put("amount", shipOrder.getInvoiceAmount());
		invoiceItem.put("content", shipOrder.getInvoiceContent());
		resultMap.put("deliveryOrder", order);
		
		
		Map<String, Object> packages = new HashMap<String, Object>();
		resultMap.put("packages", packages);
		List<Map<String, Object>> packList = new ArrayList<Map<String, Object>>();
		packages.put("package", packList);
		Map<String, Object> packItem = new HashMap<String, Object>();
		packList.add(packItem);
		packItem.put("logisticsCode", shipOrder.getLogisticsCode());
		packItem.put("logisticsName", shipOrder.getLogisticsName());
		packItem.put("expressCode", shipOrder.getExpressCode());
		Map<String, Object> items = new HashMap<String, Object>();
		packages.put("items", items);
		List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		items.put("item", itemList);
		
		
		
		params.clear();
		params.put("shipOrderId", orderId);
		List<ShipOrderDetail> detailList = this.shipOrderService.shipOrderDetailbyList(params);
		for (int i = 0; detailList != null && i < detailList.size(); i++) {
			ShipOrderDetail detail = detailList.get(i);
			Item it = this.itemService.getItem(detail.getItem().getId());
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("itemCode", it.getCode());
			itemMap.put("quantity", detail.getNum());
			itemList.add(itemMap);
		}
		String result = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		return result;
	}

	/**
	 * 订单流水查询接口
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */

	public String orderprocessQuery(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String orderCode = (String) map.get("orderCode");
		String orderId = (String) map.get("orderId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("orderId", orderId);
		// 单据
		List<ShipOrder> list = this.shipOrderService.queryShipOrderCodeIdAndType(params);
		if (list != null || list.size() == 0) {
			return "没有找到此单据!";
		}
		ShipOrder order = list.get(0);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> orderProcess = new HashMap<String, Object>();
		resultMap.put("orderProcess", orderProcess);
		orderProcess.put("orderCode", orderCode);
		orderProcess.put("orderId", orderId);
		orderProcess.put("orderType", order.getOrderType());
		Map<String, Object> processes = new HashMap<String, Object>();
		orderProcess.put("processes", processes);
		List<Map<String, Object>> process = new ArrayList<Map<String, Object>>();
		processes.put("processes", processes);
		Map<String, Object> item = new HashMap<String, Object>();
		process.add(item);
		item.put("processStatus", ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);// 暂时这个状态不做处理
		item.put("operateTime", sdf.format(order.getOperateTime()));
		item.put("remark", order.getRemark());
		String result = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		return result;
	}

	/**
	 * 单据取消接口
	 */

	public String orderCancel(String xmlStr) throws Exception {
		logger.debug("单据取消接口调用:"+xmlStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		String warehouseCode = (String) map.get("warehouseCode");
		String ownerCode = (String) map.get("ownerCode");
		String orderCode = (String) map.get("orderCode");
		String orderId = (String) map.get("orderId");
		String orderType = (String) map.get("orderType");// 单据类型，见枚举OrderTypeEnums
		String cancelReason = (String) map.get("cancelReason");// 取消原因
		// 先保存一个单据取消信息
		ShipOrder order=null;
		int flag=1;
		User user=getUserByOwnerCode(ownerCode);
		logger.debug("取商家信息:"+ownerCode+"|user:"+user!=null?user.getId():"000");
		// 处理相应单据，进行逻辑删除
		// 这里只处理入库单，发货单，退货单，出库单，仓内加工单五种
		// 直接根据订单号查询数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("userId", user.getId());
		List<ShipOrder> list = this.shipOrderService.queryShipOrderCodeIdAndType(params);
		logger.debug("取订单信息:"+params+"|list:"+list.size());
		String message="";
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (list != null && list.size() > 0) {
			logger.debug("取到订单信息,先保存退货记录并判断单据是否有运单号！");
			order = list.get(0);
			
			if(order!=null && order.getStatus().equals(EntryOrderStatus.WMS_FINISH)){
				flag=0;
				message="此单据已经确认发货，不允许取消。请联系仓库!";
				resultMap.put("flag", "fail");
				resultMap.put("message", message);
				resultMap.put("code", "500");
			}else{
				logger.debug("单据没有运单信息，修改单据状态为取消并保存取消记录!");
				if (order!=null && order.getCancelStatus() == 0) {
					logger.debug("单据状态为未取消,修改状态为取消！cancelStatus=1");
					params.clear();
					params.put("delStatus", 1);
					params.put("id", order.getId());
					this.shipOrderService.cancelShipOrder(params);
					logger.debug("单据状态修改成功！cancelStatus=1");
					// 这里应该对单据的前置单据的cancelStatus进行处理，修改为能取消
					//删除相关的单 据信息
					resultMap.put("flag", "success");
					message="单据取消接口调用成功!";
					resultMap.put("message", message);
					resultMap.put("code", "200");
				} else if(order!=null && order.getCancelStatus()>0) {
					logger.debug("单据状态不对,说明此单据已被取消过不能再重新取消!");
					flag=0;
					message="此单据在仓库的状态异常，请联系仓库处理!";
					resultMap.put("flag", "success");
					resultMap.put("message", message);
					resultMap.put("code", "200");
				}else{
					logger.debug("其它情况else。没有做更新状态之类的处理!");
					resultMap.put("flag", "success");
					message="单据取消接口调用成功!";
					resultMap.put("message", message);
					resultMap.put("code", "200");
				}
				if(order!=null && StringUtils.isNotEmpty(order.getExpressOrderno())){
					/**
					 * 订单已打，但还没有发货
					 */
					Map<String,Object> memcachedMap=new HashMap<String, Object>();
					List<ShipOrderDetail> detailList=this.shipOrderService.getShipOrderDetailByOrderId(order.getId());
					for(int i=0;i<detailList.size();i++){
						ShipOrderDetail detail=detailList.get(i);
						Item item=this.itemService.getItem(detail.getItem().getId());
						memcachedMap.put(item.getBarCode(), detail.getNum());
					}
					this.redisProxy.set((StoreConstants.memcached_return+order.getExpressOrderno()).getBytes(), ObjectTranscoder.serialize(memcachedMap));
					
				}
				logger.debug("其它情况else。没有做更新状态之类的处理!");
				resultMap.put("flag", "success");
				message="单据取消接口调用成功!";
				resultMap.put("message", message);
				resultMap.put("code", "200");
			}
		} else {
			logger.debug("没有找到订单,这里让取消接口返回正常成功数据!");
			resultMap.put("flag", "success");
			message="单据取消接口调用成功!";
			resultMap.put("message", message);
			resultMap.put("code", "200");
		}
		
		if(flag!=0){
			logger.debug("单据取消!保存单据取消与退货信息！");
			saveShipOrderReturn(orderId, order);
			ShipOrderCancel cancel = new ShipOrderCancel();
			cancel.setOrdercode(orderCode);
			cancel.setUserid(order!=null?order.getCreateUser().getId().toString():"0");
			cancel.setOrderid(orderId);
			cancel.setOrdertype(orderType);
			String reason="";
			if(order!=null){
				//reason=reason+order.getReceiverName()+"|"+order.getReceiverMobile();
				reason=xmlStr.substring(0,100);
			}else{
				reason=xmlStr.substring(0,100);
			}
			
			logger.debug("单据取消，原因!"+reason+message);
			cancel.setCancelreason(reason+message);
			cancel.setCreatetime(sdf.format(new Date()));
			this.cancelService.save(cancel);
			logger.debug("单据取消信息保存成功!"+resultMap);
		}
		logger.debug("单据取消resultmap!"+resultMap);
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		logger.debug("单据取消结果!"+resultXml);
		return resultXml;
	}



	/**
	 * 保存退货信息
	 * @param orderId
	 * @param order
	 */
	private void saveShipOrderReturn(String orderId, ShipOrder order) {
		if(order==null){
			return;
		}
		ShipOrderReturn returnOrder=new ShipOrderReturn();
		returnOrder.setCreateDate(new Date());
		returnOrder.setOrderId(orderId);
		returnOrder.setUserId(String.valueOf(order.getCreateUser().getId()));
		returnOrder.setExpressOrderno(order.getExpressOrderno());
		returnOrder.setMessage("奇门调用单据取消");
		returnOrder.setRemark(order.getReceiverName()+"|"+order.getReceiverMobile());
		this.shipOrderReturnRemote.save(returnOrder);
	}

	/**
	 * 商品库存查询
	 */

	public String inventoryQuery(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Map<String, Object> criterMap = (Map<String, Object>) map.get("criteriaList");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list.add((Map<String, Object>)criterMap.get("criteria"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; list != null && i < list.size(); i++) {
			Map<String, Object> obj = list.get(i);
			String warehouseCode = (String) obj.get("warehouseCode");
			String itemCode = (String) obj.get("itemCode");
			String itemId = (String) obj.get("itemId");
			String inventoryType = (String) obj.get("inventoryType");
			Map<String, Object> item = new HashMap<String, Object>();
			ItemInventory inventory=this.inventoryService.getItemInventory(1L, Long.valueOf(itemId), Accounts.CODE_SALEABLE);
			item.put("warehouseCode", warehouseCode);
			item.put("itemCode", itemCode);
			item.put("itemId", itemId);
			item.put("inventoryType", inventoryType);
			item.put("quantity", inventory.getNum());
			item.put("lockQuantity", inventory.getUseNum());
			mapList.add(item);
		}
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("item", mapList);
		resultMap.put("items", itemMap);
		String result = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		System.err.println(result);
		return result;
	}

	/**
	 * 仓内加工单
	 */

	public String storeprocessCreate(String xmlStr) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		StoreProcess process = new StoreProcess();
		process.setCode((String) map.get("processOrderCode"));
		process.setType((String) map.get("orderType"));
		process.setCreatetime((String) map.get("orderCreateTime"));
		process.setPlantime((String) map.get("planTime"));
		process.setServicetype((String) map.get("serviceType"));
		System.err.println(map.get("serviceType"));
		String planQty = (String) map.get("planQty");
		process.setPlanqty(Integer.valueOf(planQty));
		process.setRemark((String) map.get("remark"));
		this.processService.saveProcess(process);
		List<StoreProcessItem> itemList = new ArrayList<StoreProcessItem>();
		Map<String, Object> obj = (Map<String, Object>) map.get("materialitems");
		List<Map<String, Object>> list = (List<Map<String, Object>>) obj.get("item");
		for (int i = 0; list != null && i < list.size(); i++) {
			Map<String, Object> itemMap = list.get(i);
			StoreProcessItem item = new StoreProcessItem();
			item.setItemcode((String) itemMap.get("itemCode"));
			item.setItemid((String) itemMap.get("itemId"));
			item.setInventorytype((String) itemMap.get("inventoryType"));
			String quantity = (String) itemMap.get("quantity");
			item.setQuantity(Integer.valueOf(quantity));
			String productDate = (String) itemMap.get("productDate");
			if (productDate != null && productDate.length() > 0) {
				item.setProductdate(sdf.parse(productDate));
			}
			String expireDate = (String) itemMap.get("expireDate");
			if (expireDate != null && expireDate.length() > 0) {
				item.setExpiredate(sdf.parse(expireDate));
			}
			item.setProducecode((String) itemMap.get("produceCode"));
			item.setRemark((String) itemMap.get("remark"));
			item.setType("Mateiali");
			item.setParentid(process.getId());
			itemList.add(item);
		}
		Map<String, Object> product = (Map<String, Object>) map.get("productitems");
		List<Map<String, Object>> procuctList = (List<Map<String, Object>>) product.get("item");
		for (int i = 0; list != null && i < list.size(); i++) {
			Map<String, Object> itemMap = list.get(i);
			StoreProcessItem item = new StoreProcessItem();
			item.setItemcode((String) itemMap.get("itemCode"));
			item.setItemid((String) itemMap.get("itemId"));
			item.setInventorytype((String) itemMap.get("inventoryType"));
			String quantity = (String) itemMap.get("quantity");
			item.setQuantity(Integer.valueOf(quantity));
			String productDate = (String) itemMap.get("productDate");
			if (productDate != null && productDate.length() > 0) {
				item.setProductdate(sdf.parse(productDate));
			}
			String expireDate = (String) itemMap.get("expireDate");
			if (expireDate != null && expireDate.length() > 0) {
				item.setExpiredate(sdf.parse(expireDate));
			}
			item.setProducecode((String) itemMap.get("produceCode"));
			item.setRemark((String) itemMap.get("remark"));
			item.setType("Product");
			item.setParentid(process.getId());
			itemList.add(item);
		}
		this.processService.batchSaveProcessItem(itemList);
		return null;
	}

	/**
	 * 新增商品
	 * 
	 * @param xmlStr
	 * @return
	 * @throws ParseException
	 */
	private String addItem(Map<String, Object> xmlMap) throws ParseException {
		Map<String, Object> itemMap = (Map<String, Object>) xmlMap.get("item");
		Item item = new Item();
		buildItem(item, itemMap);
		String ownerCode = (String) xmlMap.get("ownerCode");
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userService.getUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId(1L);
		}
		item.setUserid(user.getId());
		this.itemService.saveItem(item);
		System.out.println("qmSyncService.itemSync:item.id="+item.getId());
		return ""+item.getId();
	}

	/**
	 * 修改商品
	 * 
	 * @param xmlStr
	 * @return
	 * @throws ParseException
	 */
	private String updateItem(Map<String, Object> xmlMap,SystemOperatorRecord record) throws ParseException {
		logger.info("QM update Item ...");
		Map<String, Object> itemMap = (Map<String, Object>) xmlMap.get("item");
		String itemCode = (String) itemMap.get("itemCode");
		String itemId = (String) itemMap.get("itemId");
		String ownerCode = (String) xmlMap.get("ownerCode");
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userService.getUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId(1L);
		}
		// 根据itemId找到系统中的item
		Item item = null;
		if(StringUtils.isNotBlank(itemId) && StringUtils.isNumeric(itemId)){
			item=this.itemService.getItem(Long.valueOf(itemId));
		}
		if(item==null){
			item =this.itemService.getItemByCode(itemCode,String.valueOf(user.getId()));
		}
		int k=0;
		if(item==null){
			item=new Item();
			item.setUserid(user.getId());
			k=1;
			//如果商品是新增的，则从商家取重量数据
			item.setWeight(itemMap.get("grossWeight")!=null?Double.valueOf((String)itemMap.get("grossWeight")):0f);
		}
		this.buildItem(item, itemMap);
		// 因为这里只做系统的商品同步，因此是否需要存储仓库信息这里暂时不做处理
		logger.info("QM save Item ." +item);
		this.itemService.saveItem(item);
		/**
		 * 新增商品时，库存初始化为0
		 */
		if(k==1){
			this.qmInventoryService.addInventory(1L, item.getUserid(), item.getId(), 0L, Accounts.CODE_SALEABLE, "奇门同步初始化库存", "");
		}
		record.setUser(user!=null?user.getId().intValue():0);
		return ""+item.getId();
	}

	/**
	 * 构建 Item对象
	 * 
	 * @param itemMap
	 * @return
	 * @throws ParseException
	 */
	private void buildItem(Item item, Map<String, Object> itemMap) throws ParseException {
		logger.info("QM build Item ...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String itemCode = (String) itemMap.get("itemCode");
		item.setCode(itemCode);
		item.setItemId((String) itemMap.get("itemId"));
		item.setColor((String) itemMap.get("ownerCode"));
		item.setShortName((String) itemMap.get("shortName"));
		if(item==null || item.getId()==null ||  item.getId()==0){
			item.setBarCode((String) itemMap.get("barCode"));
			item.setTitle((String) itemMap.get("itemName"));
		}
		
		item.setStockUnit((String) itemMap.get("stockUnit"));
		item.setColor((String) itemMap.get("color"));
		if(item.getUserid()!=13){
			item.setTitle((String) itemMap.get("itemName"));
			item.setSku((String) itemMap.get("skuProperty"));
		}
		item.setCategoryId((String) itemMap.get("categoryId"));
		item.setCategoryName((String) itemMap.get("categoryName"));
		item.setSafetyStock((String) itemMap.get("safetyStock"));
		item.setItemType((String) itemMap.get("itemType"));
		item.setBrandCode((String) itemMap.get("brandCode"));
		item.setBrandName((String) itemMap.get("brandName"));
		item.setDescription((String) itemMap.get("remark"));
		
		String updateTime = (String) itemMap.get("updateTime");
		if (updateTime != null) {
			item.setUpdateTime(sdf.parse(updateTime));
		}
		item.setPackageMaterial((String) itemMap.get("packageMaterial"));
		item.setType((String) itemMap.get("itemType"));
		String createTime = (String) itemMap.get("createTime");
		if (createTime != null) {
			try {
				item.setCreateTime(sdf.parse(createTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			;
		}
		item.setRemark((String) itemMap.get("remark"));
	}

	/**
	 * 构建入库单及相关信息
	 * 
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	private String buildShipOrder(ShipOrder order, Map<String, Object> map) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> entity = (Map<String, Object>) map.get("entryOrder");
		order.setOrderno((String) entity.get("entryOrderCode"));// 入库单号

		String warehouseCode = (String) entity.get("warehouseCode");// 仓库信息
		Centro centro = this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		String orderCreateTime = (String) entity.get("orderCreateTime");
		if (orderCreateTime != null && orderCreateTime != null && orderCreateTime.length() > 0) {
			order.setCreateDate(sdf.parse(orderCreateTime));
		}
		order.setOrderType((String) entity.get("orderType"));
		String logisticsCode = (String) entity.get("logisticsCode");
		if (logisticsCode != null && !logisticsCode.isEmpty()) {
			order.setLogisticsCode(logisticsCode);
		}
		String logisticsName = (String) entity.get("logisticsName");
		if (logisticsName != null && !logisticsName.isEmpty()) {
			order.setLogisticsName(logisticsName);
		}
		String expressCode = (String) entity.get("expressCode");
		if (expressCode != null && !expressCode.isEmpty()) {
			order.setExpressCode(expressCode);
		}
		String operatorCode = (String) entity.get("operatorCode");
		if (operatorCode != null && !operatorCode.isEmpty()) {
			order.setOperatorCode(operatorCode);
		}
		String operatorName = (String) entity.get("operatorName");
		if (operatorName != null && !operatorName.isEmpty()) {
			order.setOperatorName(operatorName);
		}
		String operateTime = (String) entity.get("operateTime");
		if (operateTime != null && !operateTime.isEmpty()) {
			order.setOperateTime(operateTime);
		}
		
		
		// 发件人信息
		Map<String, Object> info = (Map<String, Object>) entity.get("senderInfo");
		String company = (String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		// 收货人信息
		Map<String, Object> receive = (Map<String, Object>) entity.get("receiverInfo");
		order.setReceiveCopmany((String) receive.get("company"));
		order.setReceiverName((String) receive.get("name"));
		order.setReceiverZip((String) receive.get("zipCode"));
		order.setReceiverMobile((String) receive.get("mobile"));
		order.setReceiverState((String) receive.get("province"));
		order.setReceiverCity((String) receive.get("city"));
		order.setReceiverDistrict((String) receive.get("area"));
		order.setReceiverAddress((String) receive.get("detailAddress"));
		// 入库详情处理
		Map<String, Object> lineMap = (Map<String, Object>) map.get("orderLines");
		Object obj = lineMap.get("orderLine");
		List<Map<String, Object>> itemList = null;
		if (obj instanceof java.util.HashMap) {
			itemList = new ArrayList<Map<String, Object>>();
			Map<String, Object> objMap = (Map<String, Object>) obj;
			itemList.add(objMap);
		} else {
			itemList = (List<Map<String, Object>>) obj;
		}
		
		String ownerCode = (String) entity.get("ownerCode");
		// User user=this.userService.getUser(Long.valueOf(ownerCode));
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userService.getUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId(1L);
		}
		order.setCreateUser(user);
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			Map<String, Object> item = itemList.get(i);
			ShipOrderDetail detail = new ShipOrderDetail();
			detail.setOrderLineNo(String.valueOf(i + 1));
			detail.setOwnerCode((String) item.get("ownerCode"));
			String itemCode = (String) item.get("itemCode");
			Item itemObj = this.itemService.getItemByCode(itemCode,String.valueOf(user.getId()));
			detail.setItem(itemObj);
			String planQty = (String) item.get("planQty");
			detail.setNum(Long.valueOf(planQty));
			detail.setSkuPropertiesName((String) item.get("skuProperty"));
			detail.setInventoryType((String) item.get("inventoryType"));
			detail.setOrder(order);
			order.getDetails().add(detail);
		}

		return null;

	}

	/**
	 * 退货入库单创建
	 * 
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	private String buildShipOrderReturn(ShipOrder order, Map<String, Object> map) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> entity = (Map<String, Object>) map.get("returnOrder");
		order.setOrderno((String) entity.get("returnOrderCode"));// 入库单号

		String warehouseCode = (String) entity.get("warehouseCode");// 仓库信息
		Centro centro = this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		String orderFlag = (String) entity.get("orderFlag");
		order.setOrderFlag(orderFlag);
		order.setOrderType((String) entity.get("orderType"));
		order.setPreDeliveryOrderCode((String) entity.get("preDeliveryOrderCode"));
		order.setPreDeliveryOrderId((String) entity.get("preDeliveryOrderId"));
		order.setStatus(EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);//
		String logisticsCode = (String) entity.get("logisticsCode");
		if (!logisticsCode.isEmpty()) {
			order.setLogisticsCode(logisticsCode);
		}
		String logisticsName = (String) entity.get("logisticsName");
		if (!logisticsName.isEmpty()) {
			order.setLogisticsName(logisticsName);
		}
		String expressCode = (String) entity.get("expressCode");
		if (!expressCode.isEmpty()) {
			order.setExpressCode(expressCode);
		}
		
		
		order.setReturnReason((String) entity.get("returnReason"));
		order.setBuyerNick((String) entity.get("buyerNick"));
		order.setRemark((String) entity.get("remark"));
		// 发件人信息
		Map<String, Object> info = (Map<String, Object>) entity.get("senderInfo");
		String company = (String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		String city = (String) info.get("city");
		order.setSenderCity(city);
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		//
		
		Object obj = map.get("orderLines");
		List<Map<String, Object>> itemList = null;
		if (obj instanceof java.util.HashMap) {
			itemList = new ArrayList<Map<String, Object>>();
			Map<String, Object> objMap = (Map<String, Object>) obj;
			Object ot=objMap.get("orderLine");
			if(ot instanceof java.util.ArrayList){
				itemList = (List<Map<String, Object>>) ot;
			}else{
				itemList.add((Map<String, Object>) objMap.get("orderLine"));
			}
		} else if (obj instanceof java.util.ArrayList) {
			itemList = (List<Map<String, Object>>) obj;
		}
		///
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			Map<String, Object> item = itemList.get(i);
			ShipOrderDetail detail = new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setTradeOrderNo((String) item.get("sourceOrderCode"));
			detail.setSubTradeOrderNo((String) item.get("subSourceOrderCode"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			
			String itemCode = (String) item.get("itemCode");
			String itemId=(String) item.get("itemId");
			String ownerCode = detail.getOwnerCode();
			User user = this.userService.getUserByOwnerCode(ownerCode);
			if(user==null){
				user=new User();
				user.setId(1L);
			}
			Item itemObj = null;
			if(StringUtils.isNotBlank(itemId) && StringUtils.isNumeric(itemId)){
				itemObj=this.itemService.getItem(Long.valueOf(itemId));
			}
			if(itemObj==null) {
				itemObj= this.itemService.getItemByCode(itemCode,String.valueOf(user.getId()));
			}
			detail.setItem(itemObj);
			int planQty = Integer.valueOf(""+item.get("planQty"));
			detail.setNum(Long.valueOf(planQty));
			detail.setSkuPropertiesName((String) item.get("skuProperty"));
			detail.setInventoryType((String) item.get("inventoryType"));
			detail.setOrder(order);
			order.setCreateUser(user);
			order.getDetails().add(detail);
		}
		
		return null;
	}

	/**
	 * 出库单信息构建
	 * 
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	private String buildStockoutShipOrder(ShipOrder order, Map<String, Object> maps) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = (Map<String, Object>) maps.get("deliveryOrder");
		order.setOrderno((String) map.get("deliveryOrderCode"));// 出库单号

		String warehouseCode = (String) map.get("warehouseCode");// 仓库信息
		Centro centro = this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		order.setOrderType((String) map.get("orderType"));
		String createTime = (String) map.get("createTime");
		order.setCreateDate(sdf.parse(createTime));
		String scheduleDate = (String) map.get("scheduleDate");// 要求出库时间
		order.setScheduleDate(scheduleDate);
		
		String ownerCode = (String) map.get("ownerCode");
		// User user=this.userService.getUser(Long.valueOf(ownerCode));
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userService.getUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId(1L);
		}

		order.setRemark((String) map.get("remark"));
		String logisticsName = (String) map.get("logisticsName");
		if (!logisticsName.isEmpty()) {
			order.setLogisticsName(logisticsName);
		}
		String transportMode = (String) map.get("transportMode");
		order.setTransportMode(transportMode);
		// 提货人信息
		Map<String, Object> pickerMap = (Map<String, Object>) map.get("pickerInfo");
		order.setPickerCompany((String) pickerMap.get("company"));
		order.setPickerName((String) pickerMap.get("name"));
		order.setPickerTel((String) pickerMap.get("tel"));
		order.setPickerMobile((String) pickerMap.get("mobile"));
		order.setPickerId((String) pickerMap.get("id"));
		order.setPickerCarNo((String) pickerMap.get("carNo"));
		// 发件人信息
		Map<String, Object> info = (Map<String, Object>) map.get("senderInfo");
		String company = (String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		// 收件人信息
		Map<String, Object> receive = (Map<String, Object>) map.get("receiverInfo");
		order.setReceiveCopmany((String) receive.get("company"));
		order.setReceiverName((String) receive.get("name"));
		order.setReceiverZip((String) receive.get("zipCode"));
		order.setReceiverMobile((String) receive.get("mobile"));
		order.setReceiverState((String) receive.get("province"));
		order.setReceiverCity((String) receive.get("city"));
		order.setReceiverDistrict((String) receive.get("area"));
		order.setReceiverAddress((String) receive.get("detailAddress"));
		// 入库详情处理
		Object obj = map.get("orderLines");
		List<Map<String, Object>> itemList = null;
		if (obj instanceof java.util.HashMap) {
			itemList = new ArrayList<Map<String, Object>>();
			Map<String, Object> objMap = (Map<String, Object>) obj;
			itemList.add((Map<String, Object>) objMap.get("orderLine"));
		} else {
			itemList = (List<Map<String, Object>>) obj;
		}
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			Map<String, Object> item = itemList.get(i);
			ShipOrderDetail detail = new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			String itemCode = (String) item.get("itemCode");
			Item itemObj = this.itemService.getItemByCode(itemCode,String.valueOf(user.getId()));
			detail.setItem(itemObj);
			int planQty = (Integer) item.get("planQty");
			detail.setNum(Long.valueOf(planQty));
			detail.setInventoryType((String) item.get("inventoryType"));
			detail.setOrder(order);
			order.getDetails().add(detail);
		}

		return null;
	}

	/**
	 * 发货单构建
	 * 
	 * @param order
	 * @param maps
	 * @return
	 * @throws ParseException
	 * @throws ApiException 
	 */
	private String buildDeliveryOrderShipOrder(ShipOrder order, Map<String, Object> maps) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = (Map<String, Object>) maps.get("deliveryOrder");
		order.setOrderno((String) map.get("deliveryOrderCode"));// 出库单号
		order.setPreDeliveryOrderId((String) map.get("preDeliveryOrderId"));
		order.setPreDeliveryOrderCode((String) map.get("preDeliveryOrderCode"));
		String sourcePlatformCode=(String) map.get("sourcePlatformCode");
		order.setSourcePlatformCode(sourcePlatformCode);
		String warehouseCode = (String) map.get("warehouseCode");// 仓库信息
		Centro centro = this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		order.setOrderType((String) map.get("orderType"));
		order.setOrderFlag((String) map.get("orderFlag"));
		order.setPlaceOrderTime((String) map.get("placeOrderTime"));
		order.setPayTime((String) map.get("payTime"));
		order.setPayNo((String) map.get("payTime"));
		String createTime = (String) map.get("createTime");
		order.setCreateDate(sdf.parse(createTime));
		order.setOperateTime((String) map.get("operateTime"));
		order.setShopNick((String) map.get("shopNick"));
		order.setSellerNick((String) map.get("sellerNick"));
		order.setBuyerNick((String) map.get("buyerNick"));
		order.setLogisticsCode((String) map.get("logisticsCode"));
		order.setLogisticsName((String) map.get("logisticsName"));
		order.setLogisticsAreaCode((String) map.get("logisticsAreaCode"));
		order.setRemark((String) map.get("remark"));
		order.setUrgency((String) map.get("isUrgency"));
		order.setInvoiceFlag((String) map.get("invoiceFlag"));
		String shopNick=order.getShopNick();
		
		String ownerCode = (String) map.get("ownerCode");
		// User user=this.userService.getUser(Long.valueOf(ownerCode));
		
		// 发票信息处理
		
		List<Map<String,Object>> invoiceList=null;
		Object invObj = maps.get("invoices");
		if (invObj instanceof java.util.HashMap) {
			invoiceList = new ArrayList<Map<String, Object>>();
			Map<String, Object> objMap = (Map<String, Object>) invObj;
			Object ot=objMap.get("orderLine");
			if(ot instanceof java.util.ArrayList){
				invoiceList = (List<Map<String, Object>>) ot;
			}else{
				invoiceList.add((Map<String, Object>) objMap.get("orderLine"));
			}
		} else {
			invoiceList = (List<Map<String, Object>>) invObj;
		}
		// 这里只处理一张发票信息
		if (invoiceList != null && invoiceList.size() > 0) {
			Map<String, Object> obj = invoiceList.get(0);
			order.setInvoiceType((String) obj.get("type"));
			order.setInvoiceHeader((String) obj.get("header"));
			order.setInvoiceAmount((String) obj.get("amount"));
			order.setInvoiceContent((String) obj.get("content"));
		}
		order.setInsuranceFlag((String) map.get("insuranceFlag"));
		Map<String, Object> insurance = (Map<String, Object>) map.get("insurance");
		if (insurance != null) {
			order.setInsuranceAmount((String) insurance.get("amount"));
			order.setInsuranceType((String) insurance.get("type"));
		}
		order.setBuyerMessage((String) map.get("buyerMessage"));
		order.setSellerMessage((String) map.get("sellerMessage"));

		// 发件人信息
	/*	Map<String, Object> info = (Map<String, Object>) map.get("senderInfo");
		String company = (String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		*/
		// 收件人信息
		Map<String, Object> receive = (Map<String, Object>) map.get("receiverInfo");
		order.setReceiveCopmany((String) receive.get("company"));
		order.setReceiverName((String) receive.get("name"));
		order.setReceiverZip((String) receive.get("zipCode"));
		order.setReceiverMobile((String) receive.get("mobile"));
		order.setReceiverPhone((String) receive.get("tel"));//*****************
		order.setReceiverState((String) receive.get("province"));
		order.setReceiverCity((String) receive.get("city"));
		order.setReceiverDistrict((String) receive.get("area"));
		order.setReceiverAddress((String) receive.get("detailAddress"));
		User user = null;
		// 发货详情处理 orderLine
		Object obj = maps.get("orderLines");
		List<Map<String, Object>> itemList = null;
		if (obj instanceof java.util.HashMap) {
			itemList = new ArrayList<Map<String, Object>>();
			Map<String, Object> objMap = (Map<String, Object>) obj;
			Object ot=objMap.get("orderLine");
			if(ot instanceof java.util.ArrayList){
				itemList = (List<Map<String, Object>>) ot;
			}else{
				itemList.add((Map<String, Object>) objMap.get("orderLine"));
			}
		} else if (obj instanceof java.util.ArrayList) {
			itemList = (List<Map<String, Object>>) obj;
		}
		double weight=0f;
		int num=0;
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			Map<String, Object> item = itemList.get(i);
			ShipOrderDetail detail = new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setTradeOrderNo((String) item.get("sourceOrderCode"));
			detail.setSubTradeOrderNo((String) item.get("subSourceOrderCode"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			if(ownerCode==null){
				ownerCode=detail.getOwnerCode();
				user = this.userService.getUserByOwnerCode(ownerCode);
				if(user==null){
					user=new User();
					user.setId(1L);
				}
			}else{
				user = this.userService.getUserByOwnerCode(ownerCode);
			}
			detail.setActualPrice((String) item.get("actualPrice"));
			String itemCode = (String) item.get("itemCode");
			String itemId=(String) item.get("itemId");
			Item itemObj = null;
			logger.info("buildDeliveryOrderShipOrder:"+itemCode+"|"+user.getId()+"|"+itemObj+"|itemCode:"+itemCode+"|"+itemId);
			//电子琴的用ID来查询
			if(StringUtils.isNotEmpty(itemId) && StringUtils.isNumeric(itemId)){
				itemObj=this.itemService.getItem(Long.valueOf(itemId));
			}
			if(itemObj==null){
				itemObj=this.itemService.getItemByCode(itemCode,String.valueOf(user.getId()));
			}
			detail.setItem(itemObj);
			detail.setOrder(order);
			String planQty = (String) item.get("planQty");
			long qty=Long.valueOf(planQty);
			detail.setNum(qty);
			num+=detail.getNum();
			detail.setInventoryType((String) item.get("inventoryType"));
//			weight+=itemObj.getPackageWeight()==0?0:itemObj.getPackageWeight()*qty;
			weight+=itemObj.getWeight()==null?0:itemObj.getPackageWeight()*qty;
			order.getDetails().add(detail);
		}
		if(user==null){
			System.out.println("ownerCode:"+ownerCode);
			user = this.userService.getUserByOwnerCode(ownerCode);
			System.out.println("user is null;");
			user=new User();
			user.setId(1L);
		}
		/**
		 * 这边是特殊重单商家重量计算位置
		 */
		if(user.getId()==13){
			buildOrderWeight(order,num);
		}else{
			order.setTotalWeight(weight);
		}
		
		//User user=this.userService.findUserByShopName(shopNick);
		order.setCreateUser(user);
		
		return null;
	}

	
	/**
	 * 根据ownerCode取商家
	 * @param ownerCode
	 * @return
	 */
	private User getUserByOwnerCode(String ownerCode){
		User user=null;
		if(ownerCode!=null){
			 user = this.userService.getUserByOwnerCode(ownerCode);
		}else{
			user=new User();
			user.setId(1L);
		}
		return user;
	}
	/**
	 * 订单的重量重新构建
	 * @param order
	 */
	private void buildOrderWeight(ShipOrder order,int num){
		if(order==null){
			return;
		}
		double weight=0;
		if(num<=4){
			weight=0.4;
		}else if(num<=8){
			weight=0.8;
		}else{
			weight=1.2;
		}
		order.setTotalWeight(weight);
	}
	/**
	 * 追加返回头部信息
	 * 
	 * @param resultMap
	 */
	private void addHeadMapInfo(Map<String, Object> resultMap, String flag) {
		resultMap.put("flag", flag);
		resultMap.put("code", "");
		resultMap.put("message", flag.equals("SUCCESS") ? "success" : "");
	}
	
	public void createTradeByShipOrder(Long shipOrderId){
		
		ShipOrder order=this.shipOrderService.getShipOrder(shipOrderId);
		TradeUtil  util  = new TradeUtil();
		Trade adapter;
		try {
			adapter = util.adapter(order);
			tradeService.createTrade(adapter, order.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		
		
	}
	
	public  boolean isLong(String value) {
		  try {
			  Long.parseLong(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }
}
