package com.xinyu.service.qm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.taobao.api.ApiException;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.ItemOperatorDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryDao;
import com.xinyu.dao.order.StockInOrderDao;
import com.xinyu.dao.trade.InvoiceInfoDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.dao.trade.ShipOrderOperatorDao;
import com.xinyu.dao.trade.ShipOrderStopDao;
import com.xinyu.dao.trade.WmsConsignOrderItemDao;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.ItemTypeEnum;
import com.xinyu.model.base.enums.XmlEnum;
import com.xinyu.model.inventory.Inventory;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.InOrderTypeEnum;
import com.xinyu.model.order.enums.OrderFlagEnum;
import com.xinyu.model.qm.ReturnorderConfirmEntry.senderInfo;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.SystemSourceEnums;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.util.XmlUtil;


/**
 * 奇门接口具体事务处理类
 *  入库，出库，发货等单据的创建、
 *  QmConfirmDao   确认接口
 *  QmSyncQueryDao 初始化
 * @author 杨敏
 *
 */
@Component
public class QmSyncService {


	public static final Logger logger = Logger.getLogger(QmSyncService.class);
	
	/**
	 * 不能用奇门的itemId取商品的商家ownerCode
	 */
	private String exinclude_itemId="zc16473350918";
	
	private String include_itemId="zc16473350920";

	@Autowired
	private UserDao userDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ItemOperatorDao itemOperatorDao;
	@Autowired
	private ShipOrderDao shipOrderDao;
	@Autowired
	private InvoiceInfoDao invoiceInfoDao;
	@Autowired
	private WmsConsignOrderItemDao orderItemDao;
	@Autowired
	private ShipOrderOperatorDao shipOrderOperatorDao;
	@Autowired
	private ShipOrderStopDao orderStopDao;
	@Autowired
	private StockInOrderDao stockInOrderDao;
	@Autowired
	private InventoryDao inventoryDao;
	/**
	 * 商品商步
	 */
	@Transactional
	public String itemSync(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
 		ItemOperator record = new ItemOperator();
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
	 * 新增商品
	 * 
	 * @param xmlStr
	 * @return
	 * @throws ParseException
	 */
	private String addItem(Map<String, Object> xmlMap) throws ParseException {
		Map<String, Object> itemMap = (Map<String, Object>) xmlMap.get("item");
		Item item = new Item();
		item.generateId();
		buildItem(item, itemMap);
		String ownerCode = (String) xmlMap.get("ownerCode");
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userDao.findUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId("1");
		}
		item.setUser(user);
		this.itemDao.saveItem(item);
		this.insertItemOperator(item);
		
		System.out.println("qmSyncDao.itemSync:item.id="+item.getId());
		return ""+item.getId();
	}

	/**
	 * 生成商品操作日志
	 * */
	private void insertItemOperator(Item item) {
		ItemOperator itemOperator = new ItemOperator();
		itemOperator.generateId();
		Account account = new Account();
		account.setId("qm");
		itemOperator.setAccount(account);
		itemOperator.setCu("");
		itemOperator.setNewValue(item.getId());
		itemOperator.setOldValue(item.getId());
		itemOperator.setDescription("奇门新建商品资料！");
		itemOperator.setItem(item);
		itemOperator.setOperatorDate(new Date());
		itemOperator.setOperatorModel(OperatorModel.ITEM_CREATE);
		this.itemOperatorDao.insertItemOperator(itemOperator);
	}




	/**
	 * 修改商品
	 * 
	 * @param xmlStr
	 * @return
	 * @throws ParseException
	 */
	private String updateItem(Map<String, Object> xmlMap,ItemOperator record) throws ParseException {
		logger.info("QM update Item ...");
		Map<String, Object> itemMap = (Map<String, Object>) xmlMap.get("item");
		String itemCode = (String) itemMap.get("itemCode");
		String itemId = (String) itemMap.get("itemId");
		String ownerCode = (String) xmlMap.get("ownerCode");
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userDao.findUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId("1");
		}
		// 根据itemId找到系统中的item
		Item item = null;
		if(StringUtils.isNotBlank(itemId) && StringUtils.isNumeric(itemId)){
			item=this.itemDao.getItem(itemId);
		}
		if(item==null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemCode", itemCode);
			params.put("userId", user.getId());
			item =this.itemDao.getItemByList(params).get(0);
		}
		int k=0;
		if(item==null){
			item=new Item();
			item.generateId();
			item.setUser(user);
			k=1;
			//如果商品是新增的，则从商家取重量数据
			item.setWmsGrossWeight(itemMap.get("grossWeight")!=null?Long.valueOf((String)itemMap.get("grossWeight")):0L);
		}
		this.buildItem(item, itemMap);
		// 因为这里只做系统的商品同步，因此是否需要存储仓库信息这里暂时不做处理
		logger.info("QM save Item ." +item);
		this.itemDao.saveItem(item);
		/**
		 * 新增商品时，库存初始化为0
		 */
//		if(k==1){
//			this.qmInventoryDao.addInventory(1L, item.getUserid(), item.getId(), 0L, Accounts.CODE_SALEABLE, "奇门同步初始化库存", "");
//		}
//		record.setAccount();
		this.insertItemOperator(item);
		
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
		item.setItemCode(itemCode);
		item.setItemId((String) itemMap.get("itemId"));
		item.setColor((String) itemMap.get("ownerCode"));
//		item.setShortName((String) itemMap.get("shortName"));
		if(item==null || item.getId()==null ||  item.getId()=="0"){
			item.setBarCode((String) itemMap.get("barCode"));
			item.setName((String) itemMap.get("itemName"));
		}
		item.setSystemSource(SystemSourceEnums.QM);
		item.setSpecification((String) itemMap.get("skuProperty"));
		item.setUnit((String) itemMap.get("stockUnit"));
		item.setColor((String) itemMap.get("color"));
		item.setName((String) itemMap.get("itemName"));
		item.setCategory((String) itemMap.get("categoryId"));
		item.setCategoryName((String) itemMap.get("categoryName"));
//		item.setSafetyStock((String) itemMap.get("safetyStock"));
		item.setType(ItemTypeEnum.QMZC);
		item.setBrand((String) itemMap.get("brandCode"));
		item.setBrandName((String) itemMap.get("brandName"));
//		item.setDescription((String) itemMap.get("remark"));
		
//		String updateTime = (String) itemMap.get("updateTime");
//		if (updateTime != null) {
//			item.setUpdateTime(sdf.parse(updateTime));
//		}
 		item.setMaterialType((String) itemMap.get("packageMaterial"));
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
//		item.setRemark((String) itemMap.get("remark"));
	}
	
	/**
	 * 组合商品
	 */
	public String combineitem(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Item item = new Item();
		item.generateId();
		String code = (String) map.get("itemCode");
		item.setItemCode(code);
		item.setName("组合商品");
//		item.setShortName("");
 		item.setBarCode("");
 		item.setMaterialType("");
 		item.setType(ItemTypeEnum.QMZC);
		// 保存新商品，减去原商品库存数量
		this.itemDao.saveItem(item);
		Item nItem = null;
		Map<String, Object> items = (Map<String, Object>) map.get("items");
		List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			// 迭代减少相应的库存
			Map<String, Object> obj = itemList.get(0);
			String itemCode = (String) obj.get("itemCode");
			int quantity = (Integer) obj.get("quantity");
			//nItem = this.itemDao.getItemByCode(itemCode);
			// ItemInventory tory=this.inventoryDao.in
			// 找到商品的库存信息进行修改，并写入相应的流水信息

		}
//		ItemInventory inventory = new ItemInventory();
//		inventory.setItem(item);
//		inventory.setNum(1);
		// inventory.setCentro();
		// this.inventoryDao.
		return null;
	}

	/**
	 * 入库单创建
	 */

	public String entryorder(String xmlStr) throws Exception {
		StockInOrder order = new StockInOrder();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
			// 1.保存入库单据信息
//			buildShipOrder(order, map);
			buildStockInOrder(order, map);
			// 持久化相关信息
			this.stockInOrderDao.insertStockInOrder(order);
// 			for (int i = 0; order.getOrderItemList() != null && i < order.getOrderItemList().size(); i++) {
//				this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
// 			}
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
		StockInOrder order = new StockInOrder();
		try {
			order.setOrderType(InOrderTypeEnum.QMRETURN);
			buildShipOrderReturn(order, map);
			order.setState(InOrderStateEnum.SAVE);
			// 持久化相关信息
			this.stockInOrderDao.insertStockInOrder(order);
//			for (int i = 0; order.getOrderItemList() != null && i < order.getOrderItemList().size(); i++) {
//				this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
//			}
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
	 * 发货单创建
	 */
	 
	public synchronized String deliveryorder(String xmlStr) throws Exception {
		Map<String, Object> map = XmlUtil.Dom2Map(xmlStr);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ShipOrder order = new ShipOrder();
		try {
 			order.setOrderType(201);
			order.setStatus(OrderStatusEnum.WMS_ACCEPT);// 发货单的初始状态
			order.setSystemSource(SystemSourceEnums.QM);//打个标记，说明此单据是从奇门同步过来
			buildDeliveryOrderShipOrder(order, map);
			/**
			 * 判断当前单据的发货单号在系统中是否存在，如果存在则单据不能再次保存
			 */
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("erpOrderCode", order.getErpOrderCode());
			params.put("userId", order.getUser().getId());
			List<ShipOrder> orderList=this.shipOrderDao.getShipOrderByList(params);
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
 					order.setExtendFields(String.valueOf(new Date().getTime()));
					// 持久化相关信息
					this.shipOrderDao.insertShipOrder(order);
					//更新库存信息
					for(WmsConsignOrderItem orderItem:order.getOrderItemList()){
						Map<String, Object> itemParams = new HashMap<String, Object>();
						itemParams.put("itemId", orderItem.getItem().getId());
						Inventory inventory = this.inventoryDao.findInventoryByList(itemParams).get(0);
						long userNum = inventory.getNum()-orderItem.getItemQuantity();
						inventory.setNum(userNum);
						this.inventoryDao.updateInventory(inventory);
					}
//					for (int i = 0; order.getDetails() != null && i < order.getDetails().size(); i++) {
//						ShipOrderDetail detail=order.getDetails().get(i);
//						this.entryOrderDetailJpaDao.save(detail);
//						/**
//						 * 商品的已使用库存增加相应的数量
//						 */
//						logger.debug("qm item useNum:"+detail.getItem().getId()+"|"+detail.getNum());
//						this.inventoryDao.updateUserNum(1l, detail.getItem().getId(), Accounts.CODE_SALEABLE, detail.getNum());
//					}
//					
					
//					TradeUtil  util  = new TradeUtil();
//					Trade adapter = util.adapter(order);
//					
//					tradeDao.createTrade(adapter, order.getId());
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
		ShipOrderOperator record=new ShipOrderOperator();
		record.generateId();
		Account account = new Account();
		account.setId("qm");
		record.setAccount(account);
		record.setCu("");
		record.setShipOrder(order);
		record.setOldValue(order.getId());
		record.setNewValue(order.getId());
		record.setOperatorModel(OperatorModel.ITEM_CREATE);
		record.setOperatorDate(new Date());
		record.setDescription("奇门订单同步创建！");
		this.shipOrderOperatorDao.saveShipOrderOperator(record);
		return resultXml;
	}

	/**
	 * 判断当前订单的库存，是不是能允许出库单的同步
	 * @param shipOrder
	 * @return map
	 */
	private Map<String,Object> isHaveInventory(ShipOrder shipOrder){
		List<WmsConsignOrderItem> orderItems = shipOrder.getOrderItemList();
 		Map<String,Object> resultMap = new HashMap<String, Object>();
 		for(int i = 0;orderItems!=null && i<orderItems.size();i++){
 			WmsConsignOrderItem orderItem = orderItems.get(i);
 			Item item = orderItem.getItem();
 			Item itemObj = this.itemDao.getItem(item.getId());
 			Map<String, Object> params = new HashMap<String, Object>();
 			params.put("itemId", item.getId());
 			Inventory inventory = this.inventoryDao.findInventoryByList(params).get(0);
 			long userNum = inventory.getNum()-orderItem.getItemQuantity();
 			if (userNum<0) {
 				resultMap.put("msg", itemObj==null?item.getId():itemObj.getName()+"数量不足！");
 				logger.info("itemId"+item.getId()+"useNum:"+userNum+"inventory.num"+inventory.getNum()+"detailNum:"+orderItem.getItemQuantity()+itemObj.getName()+"数量不足！");
 				resultMap.put("ret", "0");
 				break;
			}else{
				resultMap.put("ret", "1");
			}
//			ItemInventory inventory=this.inventoryDao.getItemInventory(1L, item.getId(), Accounts.CODE_SALEABLE.toString());
//			if(inventory==null ){
//				userNum=0+Long.valueOf(detail.getNum()).intValue();
//			}else{
//				userNum=inventory.getUseNum()<0?0:inventory.getUseNum()+Long.valueOf(detail.getNum()).intValue();
//			}
//			if(inventory==null  || inventory.getNum()<0 || inventory.getNum()<userNum){
//				ret="0";
//				System.err.println("isHaveInventory:"+item.getId());
//				Item itemObj=this.itemDao.getItem(item.getId());
//				System.err.println("isHaveInventory"+itemObj);
			
//			}
		}
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
		params.put("id", orderId);
		// 查询发货单
		List<ShipOrder> list = this.shipOrderDao.getShipOrderByList(params);
		ShipOrder shipOrder = null;
		if (list != null && list.size() > 0) {
			shipOrder = list.get(0);
		}
		// 构建返回列表
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> order = new HashMap<String, Object>();
		order.put("deliveryOrderCode", shipOrder.getErpOrderCode());
		order.put("deliveryOrderId", shipOrder.getPrevErpOrderCode());
		order.put("orderType", shipOrder.getOrderType());
		order.put("operateTime", shipOrder.getOrderExaminationTime());
		
		//发票字段
		Map<String, Object> invoices = new HashMap<String, Object>();
		List<Map<String, Object>> invoice = new ArrayList<Map<String, Object>>();
 		Map<String, Object> invoiceItem = new HashMap<String, Object>();
 		for(InvoiceInfo invoiceInfo:shipOrder.getInvoiceInfoList()){
 			InvoiceInfo info = this.invoiceInfoDao.getInvoiceInfoById(invoiceInfo.getId());
 			invoiceItem.put("header", info.getBillTitle());
 	 		invoiceItem.put("amount", info.getBillAccount());
 	 		invoiceItem.put("content", info.getBillContent());
 		}
 		invoice.add(invoiceItem);
 		invoices.put("invoice", invoice);
		order.put("invoices", invoices);
 		
		resultMap.put("deliveryOrder", order);
		
		Map<String, Object> packages = new HashMap<String, Object>();
		resultMap.put("packages", packages);
		List<Map<String, Object>> packList = new ArrayList<Map<String, Object>>();
		packages.put("package", packList);
		Map<String, Object> packItem = new HashMap<String, Object>();
		packList.add(packItem);
		packItem.put("logisticsCode", shipOrder.getTmsServiceCode());
		packItem.put("logisticsName", shipOrder.getTmsServiceName());
		packItem.put("expressCode", shipOrder.getTmsOrderCode());
		Map<String, Object> items = new HashMap<String, Object>();
		packages.put("items", items);
		List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		items.put("item", itemList);
			
		params.clear();
		params.put("orderId", orderId);
		List<WmsConsignOrderItem> detailList = this.orderItemDao.getWmsConsignOrderItemByList(params);
		for (int i = 0; detailList != null && i < detailList.size(); i++) {
			WmsConsignOrderItem detail = detailList.get(i);
			Item it = this.itemDao.getItem(detail.getItem().getId());
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("itemCode", it.getItemCode());
			itemMap.put("quantity", detail.getItemQuantity());
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
		List<ShipOrder> list = this.shipOrderDao.getShipOrderByList(params);
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
 		// 暂时这个状态不做处理
// 		item.put("processStatus", ShipOrder.EntryOrderStatus.ENTRY_WAIT_STORAGE_RECEIVED);
 		item.put("operateTime", sdf.format(order.getOrderExaminationTime()));
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
		User user = this.userDao.findUserByOwnerCode(ownerCode);
		logger.debug("取商家信息:"+ownerCode+"|user:"+user!=null?user.getId():"000");
		// 处理相应单据，进行逻辑删除
		// 这里只处理入库单，发货单，退货单，出库单，仓内加工单五种
		// 直接根据订单号查询数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("userId", user.getId());
		List<ShipOrder> list = this.shipOrderDao.getShipOrderByList(params);
		logger.debug("取订单信息:"+params+"|list:"+list.size());
		String message="";
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (list != null && list.size() > 0) {
			logger.debug("取到订单信息,先保存退货记录并判断单据是否有运单号！");
			order = list.get(0);			
			if(order.getTmsOrderCode()!=null && order.getTmsOrderCode().length()>0){
				flag=2;
				message="此单据已经有运单号，请联系仓库说明情况!";
				resultMap.put("flag", "success");
				resultMap.put("message", message);
				resultMap.put("code", "500");
			}else{
				logger.debug("单据没有运单信息，修改单据状态为取消并保存取消记录!");
				if (order!=null && order.getStatus().getKey()!="WMS_CANCEL") {
					String oldValue = order.getStatus().getKey();
					logger.debug("单据状态为未取消,修改状态为取消！cancelStatus=1");
					params.clear();
					order.setStatus(OrderStatusEnum.WMS_CANCEL);
					this.shipOrderDao.updateShipOrder(order);
					//生成退货单据记录
					ShipOrderStop orderStop = new ShipOrderStop();
					orderStop.generateId();
					orderStop.setCreateTime(new Date());
					orderStop.setCu("");
					orderStop.setDescription("奇门订单取消！");
					orderStop.setExpressOrderno(order.getTmsOrderCode());
					orderStop.setOrderId(order.getId());
					orderStop.setUserId(order.getUser().getId());
					this.orderStopDao.saveShipOrderStop(orderStop);
					//写入日志
					ShipOrderOperator orderOperator = new ShipOrderOperator();
					orderOperator.generateId();
					orderOperator.setCu("");
					orderOperator.setOldValue(oldValue);
					orderOperator.setNewValue(OrderStatusEnum.WMS_CANCEL.getKey());
					orderOperator.setDescription("奇门订单取消！");
					orderOperator.setOperatorDate(new Date());
					orderOperator.setOperatorModel(OperatorModel.TRADE_INVALID);
					orderOperator.setShipOrder(order);
					Account account = new Account();
					account.setId("qm");
					orderOperator.setAccount(account);
					
					logger.debug("单据状态修改成功！cancelStatus=1");
					// 这里应该对单据的前置单据的cancelStatus进行处理，修改为能取消
					//删除相关的单 据信息
					resultMap.put("flag", "success");
					message="单据取消接口调用成功!";
					resultMap.put("message", message);
					resultMap.put("code", "200");
				} else if(order!=null && order.getStatus().getKey()=="WMS_CANCEL") {
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
		
//		if(flag!=0){
//			logger.debug("单据取消!保存单据取消与退货信息！");
//			saveShipOrderReturn(orderId, order);
//			ShipOrder cancel = new ShipOrder();
//			cancel.setOrderCode(orderCode);
//			cancel.setUser(user);
//			cancel.setOrderid(orderId);
//			cancel.setOrdertype(orderType);
//			String reason="";
//			if(order!=null){
//				//reason=reason+order.getReceiverName()+"|"+order.getReceiverMobile();
//				reason=xmlStr.substring(0,100);
//			}else{
//				reason=xmlStr.substring(0,100);
//			}
//			
//			logger.debug("单据取消，原因!"+reason+message);
//			cancel.setCancelreason(reason+message);
//			cancel.setCreatetime(sdf.format(new Date()));
//			this.cancelDao.save(cancel);
//			logger.debug("单据取消信息保存成功!"+resultMap);
//		}
		logger.debug("单据取消resultmap!"+resultMap);
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		logger.debug("单据取消结果!"+resultXml);
		return resultXml;
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
//			ItemInventory inventory=this.inventoryDao.getItemInventory(1L, Long.valueOf(itemId), Accounts.CODE_SALEABLE);
			item.put("warehouseCode", warehouseCode);
			item.put("itemCode", itemCode);
			item.put("itemId", itemId);
			item.put("inventoryType", inventoryType);
//			item.put("quantity", inventory.getNum());
//			item.put("lockQuantity", inventory.getUseNum());
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
	 * 构建入库单及相关信息
	 * 
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	private String buildStockInOrder(StockInOrder order, Map<String, Object> map) throws ParseException {
		order.generateId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> entity = (Map<String, Object>) map.get("entryOrder");
		order.setErpOrderCode((String) entity.get("entryOrderCode"));// 入库单号

		// 仓库信息
// 		Centro centro = this.centroDao.findCentroByCode(warehouseCode);
// 		order.setCentroId(centro.getId());
		String warehouseCode = (String) entity.get("warehouseCode");
		if (warehouseCode!=null) {
			order.setStoreCode(warehouseCode);
		}
		String orderCreateTime = (String) entity.get("orderCreateTime");
		if (orderCreateTime != null && orderCreateTime != null && orderCreateTime.length() > 0) {
			order.setOrderCreateTime(sdf.parse(orderCreateTime));
		}
		order.setOrderType(InOrderTypeEnum.getOrderTypeEnum("1001"));
		String logisticsCode = (String) entity.get("logisticsCode");
		if (logisticsCode != null && !logisticsCode.isEmpty()) {
			order.setTmsServiceCode(logisticsCode);
		}
		String logisticsName = (String) entity.get("logisticsName");
		if (logisticsName != null && !logisticsName.isEmpty()) {
			order.setTmsServiceName(logisticsName);
		}
		String expressCode = (String) entity.get("expressCode");
		if (expressCode != null && !expressCode.isEmpty()) {
			order.setTmsOrderCode(expressCode);
		}
//		String operatorCode = (String) entity.get("operatorCode");
//		if (operatorCode != null && !operatorCode.isEmpty()) {
//			order.setOperatorCode(operatorCode);
//		}
//		String operatorName = (String) entity.get("operatorName");
//		if (operatorName != null && !operatorName.isEmpty()) {
//			order.setOperatorName(operatorName);
//		}
		String operateTime = (String) entity.get("operateTime");
		if (operateTime != null && !operateTime.isEmpty()) {
			order.setOrderCreateTime(sdf.parse(operateTime));
		}
		order.setState(InOrderStateEnum.SAVE);	
		// 发件人信息
		SenderInfo senderInfo = new SenderInfo();
		Map<String, Object> info = (Map<String, Object>) entity.get("senderInfo");
//		String company = (String) info.get("company");
		senderInfo.generateId();
		senderInfo.setSenderName((String) info.get("name"));
		senderInfo.setSenderZipCode((String) info.get("zipCode"));
		senderInfo.setSenderMobile((String) info.get("mobile"));
		senderInfo.setSenderProvince((String) info.get("province"));
		senderInfo.setSenderCity((String) info.get("city"));
		senderInfo.setSenderArea((String) info.get("area"));
		senderInfo.setSenderTown((String) info.get("town"));
		senderInfo.setSenderAddress((String) info.get("detailAddress"));
		order.setSenderInfo(senderInfo);
		// 收货人信息
//		Map<String, Object> receive = (Map<String, Object>) entity.get("receiverInfo");
//		order.setReceiveCopmany((String) receive.get("company"));
//		order.setReceiverName((String) receive.get("name"));
//		order.setReceiverZip((String) receive.get("zipCode"));
//		order.setReceiverMobile((String) receive.get("mobile"));
//		order.setReceiverState((String) receive.get("province"));
//		order.setReceiverCity((String) receive.get("city"));
//		order.setReceiverDistrict((String) receive.get("area"));
//		order.setReceiverAddress((String) receive.get("detailAddress"));
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
		order.setOwnerUserName(ownerCode);
		// User user=this.userDao.getUser(Long.valueOf(ownerCode));
		System.err.println("ownerCode:"+ownerCode);
		User user = this.userDao.findUserByOwnerCode(ownerCode);
		if(user==null){
			System.err.println("user is null;");
			user=new User();
			user.setId("1");
		}
		order.setUser(user);
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			Map<String, Object> item = itemList.get(i);
			WmsStockInOrderItem detail = new WmsStockInOrderItem();
			detail.generateId();
			detail.setOrderItemId(String.valueOf(i + 1));
			detail.setOwnerUserId((String)item.get("ownerCode"));
			String itemCode = (String) item.get("itemCode");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemCode", itemCode);
			params.put("userId", user.getId());
			Item itemObj = this.itemDao.getItemByList(params).get(0);
			detail.setItem(itemObj);
			detail.setItemCode(itemObj.getItemCode());
			detail.setItemName(itemObj.getName());
			String itemVersion = String.valueOf(itemObj.getItemVersion());
			detail.setItemVersion(Integer.parseInt(itemVersion));
			String price = String.valueOf(itemObj.getCostPrice());
			detail.setItemPrice(Long.parseLong(price));
			String planQty = (String) item.get("planQty");
			detail.setItemQuantity(Integer.valueOf(planQty));
// 			detail.setSkuPropertiesName((String) item.get("skuProperty"));
 			detail.setInventoryType(InventoryTypeEnum.getInventoryType("1"));
			detail.setStockInOrder(order);
			order.getOrderItemList().add(detail);
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
	private String buildShipOrderReturn(StockInOrder order, Map<String, Object> map) throws ParseException {
		order.generateId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> entity = (Map<String, Object>) map.get("returnOrder");
		order.setErpOrderCode((String) entity.get("returnOrderCode"));// 入库单号

		String warehouseCode = (String) entity.get("warehouseCode");// 仓库信息
//		Centro centro = this.centroDao.findCentroByCode(warehouseCode);
//		order.setCentroId(centro.getId());
 		String orderFlag = (String) entity.get("orderFlag");
 		order.setOrderFlag(OrderFlagEnum.getOrderFlagEnum("31"));
		order.setOrderType(InOrderTypeEnum.getOrderTypeEnum("1002"));
		order.setPrevEprOrderCode((String) entity.get("preDeliveryOrderCode"));
		order.setPrevOrderCode((String) entity.get("preDeliveryOrderId"));
//		order.setStatus();
		String logisticsCode = (String) entity.get("logisticsCode");
		if (!logisticsCode.isEmpty()) {
			order.setTmsServiceCode(logisticsCode);
		}
		String logisticsName = (String) entity.get("logisticsName");
		if (!logisticsName.isEmpty()) {
			order.setTmsServiceName(logisticsName);
		}
		String expressCode = (String) entity.get("expressCode");
		if (!expressCode.isEmpty()) {
			order.setTmsOrderCode(expressCode);
		}
		
		order.setState(InOrderStateEnum.SAVE);
		order.setReturnReason((String) entity.get("returnReason"));
		order.setBuyerNick((String) entity.get("buyerNick"));
		order.setRemark((String) entity.get("remark"));
		// 发件人信息
		SenderInfo senderInfo = new SenderInfo();
		senderInfo.generateId();
		Map<String, Object> info = (Map<String, Object>) entity.get("senderInfo");
//		String company = (String) info.get("company");
		senderInfo.setSenderName((String) info.get("name"));
		senderInfo.setSenderZipCode((String) info.get("zipCode"));
		senderInfo.setSenderMobile((String) info.get("mobile"));
		senderInfo.setSenderProvince((String) info.get("province"));
		senderInfo.setSenderCity((String) info.get("city"));
		senderInfo.setSenderArea((String) info.get("area"));
		senderInfo.setSenderTown((String) info.get("town"));
		senderInfo.setSenderAddress((String) info.get("detailAddress"));
		order.setSenderInfo(senderInfo);
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
			WmsStockInOrderItem detail = new WmsStockInOrderItem();
			detail.generateId();
			detail.setOrderItemId((String) item.get("orderLineNo"));
			detail.setOrderSourceCode((String) item.get("sourceOrderCode"));
			detail.setSubSourceCode((String) item.get("subSourceOrderCode"));
			detail.setOwnerUserId((String) item.get("ownerCode"));
			
			String itemCode = (String) item.get("itemCode");
			String itemId=(String) item.get("itemId");
			String ownerCode = detail.getOwnerUserId();
			User user = this.userDao.findUserByOwnerCode(ownerCode);
			if(user==null){
				user=new User();
				user.setId("1");
			}
			Item itemObj = null;
			if(StringUtils.isNotBlank(itemId) && StringUtils.isNumeric(itemId)){
				itemObj=this.itemDao.getItem(itemId);
			}
			if(itemObj==null) {
				Map<String, Object> params =  new HashMap<String, Object>();
				params.put("itemCode", itemCode);
				params.put("userId", user.getId());
				itemObj= this.itemDao.getItemByList(params).get(0);
			}
			detail.setItem(itemObj);
			int planQty = Integer.valueOf(""+item.get("planQty"));
			detail.setItemQuantity(planQty);
			detail.setInventoryType(InventoryTypeEnum.getInventoryType("1"));
			detail.setBarCode(itemObj.getBarCode());
			detail.setItemCode(itemObj.getItemCode());
			detail.setItemName(itemObj.getName());
			String price = String.valueOf(itemObj.getCostPrice());
			detail.setItemPrice(Long.parseLong(price));
			String itemVersion = String.valueOf(itemObj.getItemVersion());
			detail.setItemVersion(Integer.parseInt(itemVersion));
			detail.setStockInOrder(order);
			order.setUser(user);
			order.getOrderItemList().add(detail);
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
		order.generateId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = (Map<String, Object>) maps.get("deliveryOrder");
		order.setErpOrderCode((String) map.get("deliveryOrderCode"));// 出库单号
		order.setPrevOrderCode((String) map.get("preDeliveryOrderId"));
		order.setPrevErpOrderCode((String) map.get("preDeliveryOrderCode"));
		String sourcePlatformCode=(String) map.get("sourcePlatformCode");
		order.setOrderSource(Integer.parseInt(sourcePlatformCode));
		String warehouseCode = (String) map.get("warehouseCode");// 仓库信息
//		Centro centro = this.centroDao.findCentroByCode(warehouseCode);
//		order.setCentroId(centro.getId());
		order.setOrderType(201);
		order.setOrderFlag((String) map.get("orderFlag"));
		order.setOrderShopCreateTime(sdf.parse((String) map.get("placeOrderTime")));
		order.setOrderPayTime(sdf.parse((String) map.get("payTime")));
		order.setPayNo((String) map.get("payTime"));
		String createTime = (String) map.get("createTime");
		order.setCreateTime(sdf.parse(createTime));
		order.setOrderExaminationTime(sdf.parse((String) map.get("operateTime")));
		order.setShopName((String) map.get("shopNick"));
		order.setTmsServiceCode((String) map.get("logisticsCode"));
		order.setTmsServiceName((String) map.get("logisticsName"));
//		order.setLogisticsAreaCode((String) map.get("logisticsAreaCode"));
		order.setRemark((String) map.get("remark"));
//		order.setUrgency((String) map.get("isUrgency"));
//		order.setInvoiceFlag((String) map.get("invoiceFlag"));
//		String shopNick=order.getShopName();
		
 		String ownerCode = (String) map.get("ownerCode");
// 		User ownerUser=this.userDao.findUserByOwnerCode(ownerCode);
//		order.setUser(ownerUser);
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
		List<InvoiceInfo> list = new ArrayList<InvoiceInfo>();
		if (invoiceList != null && invoiceList.size() > 0) {
			Map<String, Object> obj = invoiceList.get(0);
			InvoiceInfo invoiceInfo = new InvoiceInfo();
			invoiceInfo.generateId();
			invoiceInfo.setBillTitle((String) obj.get("header"));
			invoiceInfo.setBillAccount((String) obj.get("amount"));
			invoiceInfo.setBillContent((String) obj.get("content"));
			invoiceInfo.setBillType((String) obj.get("type"));
			list.add(invoiceInfo);
		}
		order.setInvoiceInfoList(list);
//		order.setInsuranceFlag((String) map.get("insuranceFlag"));
//		Map<String, Object> insurance = (Map<String, Object>) map.get("insurance");
//		if (insurance != null) {
//			order.setInsuranceAmount((String) insurance.get("amount"));
//			order.setInsuranceType((String) insurance.get("type"));
//		}
		order.setBuyerMessage((String) map.get("buyerMessage"));
		order.setSellerMessage((String) map.get("sellerMessage"));

		// 发件人信息
	 	Map<String, Object> info = (Map<String, Object>) map.get("senderInfo");
	 	SenderInfo senderInfo = new SenderInfo();
	 	senderInfo.generateId();
//		String company = (String) info.get("company");
		senderInfo.setCu("");
		senderInfo.setSenderName((String) info.get("name"));
		senderInfo.setSenderZipCode((String) info.get("zipCode"));
		senderInfo.setSenderPhone((String) info.get("mobile"));
		senderInfo.setSenderMobile((String) info.get("mobile"));
		senderInfo.setSenderProvince((String) info.get("province"));
		senderInfo.setSenderCity((String) info.get("city"));
		senderInfo.setSenderArea((String) info.get("area"));
		senderInfo.setSenderTown((String) info.get("town"));
		senderInfo.setSenderAddress((String) info.get("detailAddress"));
		 order.setSenderInfo(senderInfo);
		// 收件人信息
		Map<String, Object> receive = (Map<String, Object>) map.get("receiverInfo");
		ReceiverInfo receiverInfo = new ReceiverInfo();
		receiverInfo.generateId();
//		receiverInfo.setReceiveCopmany((String) receive.get("company"));
		receiverInfo.setReceiverName((String) receive.get("name"));
		receiverInfo.setReceiverZipCode((String) receive.get("zipCode"));
		receiverInfo.setReceiverMobile((String) receive.get("mobile"));
		receiverInfo.setReceiverPhone((String) receive.get("tel"));
		receiverInfo.setReceiverProvince((String) receive.get("province"));
		receiverInfo.setReceiverCity((String) receive.get("city"));
		receiverInfo.setReceiverArea((String) receive.get("area"));
		receiverInfo.setReceiverAddress((String) receive.get("detailAddress"));
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
			WmsConsignOrderItem detail = new WmsConsignOrderItem();
			detail.generateId();
			detail.setOrderItemId((String) item.get("orderLineNo"));
			detail.setOrderSourceCode((String) item.get("sourceOrderCode"));
			detail.setSubSourceCode((String) item.get("subSourceOrderCode"));
			detail.setOwnerUserId((String) item.get("ownerCode"));
			if(ownerCode==null){
				ownerCode=detail.getOwnerUserId();
				user = this.userDao.findUserByOwnerCode(ownerCode);
				if(user==null){
					user=new User();
					user.setId("1");
				}
			}else{
				user = this.userDao.findUserByOwnerCode(ownerCode);
			}
			detail.setActualPrice(Long.valueOf((String)item.get("actualPrice")));
			String itemCode = (String) item.get("itemCode");
			String itemId=(String) item.get("itemId");
			Item itemObj = null;
			logger.info("buildDeliveryOrderShipOrder:"+itemCode+"|"+user.getId()+"|"+itemObj+"|itemCode:"+itemCode+"|"+itemId);
			//电子琴的用ID来查询
			if(StringUtils.isNotEmpty(itemId) && StringUtils.isNumeric(itemId)){
				itemObj=this.itemDao.getItem(itemId);
			}
			if(itemObj==null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("itemCode", itemCode);
				params.put("userId", user.getId());
				itemObj=this.itemDao.getItemByList(params).get(0);
			}
			detail.setItem(itemObj);
			detail.setOrder(order);
			String planQty = (String) item.get("planQty");
			long qty=Long.valueOf(planQty);
			detail.setItemQuantity(qty);
			num+=detail.getItemQuantity();
//			detail.setInventoryType((String) item.get("inventoryType"));
//			weight+=itemObj.getPackageWeight()==0?0:itemObj.getPackageWeight()*qty;
			weight+=itemObj.getWmsGrossWeight()*qty;
			order.getOrderItemList().add(detail);
		}
		if(user==null){
			System.out.println("ownerCode:"+ownerCode);
			user = this.userDao.findUserByOwnerCode(ownerCode);
			System.out.println("user is null;");
			user=new User();
			user.setId("1");
		}
		/**
		 * 这边是特殊重单商家重量计算位置
		 */
//		if(user.getId()=="13"){
//			buildOrderWeight(order,num);
//		}else{
//			order.setTotalWeight(weight);
//		}
		
		//User user=this.userDao.findUserByShopName(shopNick);
		order.setUser(user);
		
		return null;
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
	
	
	public  boolean isLong(String value) {
		  try {
			  Long.parseLong(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }


	public String stockout(String xmlStr) {
		// TODO Auto-generated method stub
		return null;
	}

	public String storeprocessCreate(String xmlStr) {
		// TODO Auto-generated method stub
		return null;
	}
}
