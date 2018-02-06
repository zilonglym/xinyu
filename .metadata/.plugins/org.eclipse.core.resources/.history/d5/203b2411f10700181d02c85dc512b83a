package com.xinyu.service.caoniao;

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

import com.taobao.api.request.WlbWmsConsignInventoryOccupancyRequest.Receiverinfo;
import com.taobao.api.response.WlbWmsConsignBillGetResponse.Tmsorder;
import com.taobao.pac.sdk.cp.ReceiveListener;
import com.taobao.pac.sdk.cp.ReceiveSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.WmsConsignOrderNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_CONSIGN_ORDER_NOTIFY.WmsConsignOrderNotifyResponse;
import com.xinyu.dao.trade.TmsOrderDao;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.DeliverRequirements;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.InvoiceItemDetail;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.RdsConstants;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.WmsConsignOrderPackageRequirement;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.SystemSourceEnums;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.model.util.MyException;
import com.xinyu.service.common.Constants;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.CentroService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.DeliverRequirementsService;
import com.xinyu.service.trade.InvoiceInfoService;
import com.xinyu.service.trade.SenderInfoService;
import com.xinyu.service.trade.ShipOrderOperatorService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.trade.TmsOrderService;
import com.xinyu.service.trade.WmsConsignOrderItemService;
import com.xinyu.service.trade.WmsConsignOrderPackageRequirementService;
import com.xinyu.service.util.IRedisProxy;
import com.xinyu.service.util.ObjectTranscoder;
import com.xinyu.service.util.XmlUtil;

import sun.nio.cs.ext.TIS_620;

/**
 * 销售订单发货通知接口（主动模式，WMS_CONSIGN_ORDER_NOTIFY，必选！）
 * 
 * @author yangmin 2017年4月26日
 *
 */
@Component
public class WmsConsignOrderNotifyCpImpl
		implements ReceiveListener<WmsConsignOrderNotifyRequest, WmsConsignOrderNotifyResponse> {

	public static final Logger logger = Logger.getLogger(WmsConsignOrderNotifyCpImpl.class);

	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private WmsConsignOrderItemService wmsConsignOrderItemService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CentroService centroService;
	@Autowired
	private DeliverRequirementsService deliverRequirementsService;
	@Autowired
	private ShipOrderOperatorService operatorService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private WmsOrderStatusUploadCpImpl orderStatusUploadService;
	@Autowired
	private IRedisProxy redisProxy;
	@Autowired
	private TmsOrderService tmsOrderService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private SimpleDateFormat sdfyy = new SimpleDateFormat("yyyy-MM-dd");

	public WmsConsignOrderNotifyResponse execute(ReceiveSysParams params, WmsConsignOrderNotifyRequest request) {
		// TODO Auto-generated method stub
		logger.debug("销售订单发货业务逻辑开始处理!");
		Date date=new Date();
		WmsConsignOrderNotifyResponse response = new WmsConsignOrderNotifyResponse();
		try {
			List<Map<String, Object>> paramsList = new ArrayList<Map<String,Object>>();
			String type = "create";
			ShipOrder order = new ShipOrder();
			order.generateId();
//			Map<String, Object> map = XmlUtil.Dom2Map(content);
			buildOrderInfo(order,  request, paramsList);
			response.setSuccess(true);// 业务成功
			//订单同步成功,修改库存
			logger.error("订单处理时间:"+(new Date().getTime()-date.getTime()));
		} catch (Exception e) {
			//com.xinyu.model.util.MyException: null
			Map<String,Object> rejectMap=new HashMap<String, Object>();
			rejectMap.put("orderType", ""+request.getOrderType());
			rejectMap.put("storeOrderCode", request.getStoreCode());
			rejectMap.put("status", OrderStatusEnum.WMS_REJECT);
			rejectMap.put("orderCode", request.getOrderCode());
			rejectMap.put("description", e.getMessage());
			String rejectKey=RdsConstants.ORDER_REJECT+request.getOrderCode();
			this.redisProxy.set(rejectKey.getBytes(), ObjectTranscoder.serialize(rejectMap));
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}		
		return response;
	}
	
	

	/**
	 * 构建订单发货数据
	 * 
	 * @param order
	 * @param params
	 * @param paramsList 
	 * @throws Exception
	 */
	
	@Transactional
	private  void buildOrderInfo(ShipOrder order, WmsConsignOrderNotifyRequest request, List<Map<String, Object>> paramsList)
			throws Exception {
		Date date=new Date();
		String type = "create";
		String ownerUserId = request.getOwnerUserId();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("ownerCode", ownerUserId);
		User user = this.userService.getUserBySearch(p).get(0);
		/**
		 * 判断当前的订单是否存在数据库
		 */
		String orderCode=request.getOrderCode();
		p.clear();
		p.put("orderCode", orderCode);
		p.put("userId", user.getId());
		int isHaveOrder=this.shipOrderService.isHaveOrder(p);
		if(isHaveOrder>0){
			ShipOrder shipOrder=new ShipOrder();
			order.setId(request.getOrderCode());
			this.saveShipOrderOperator(shipOrder, order.getOrderCode()+"菜鸟订单重复创建！");
			return ;
		}
		logger.error("判断是否重复"+(new Date().getTime()-date.getTime()));
		date=new Date();
		order.setCreateTime(new Date());
		order.setUser(user);
		order.setStoreCode(request.getStoreCode());
		order.setOrderCode(request.getOrderCode());
		order.setOrderType(request.getOrderType());
		order.setOrderSource(request.getOrderSource());
		order.setOrderCreateTime(request.getOrderCreateTime());
		order.setErpOrderCode(request.getErpOrderCode());
		order.setShopId(request.getUserId());
		/**
		 * modify 2017-11-02 
		 * fufangjue
		 */
		order.setShopName(request.getUserName());
		/**
		 * 单据到达仓库
		 */
		order.setStatus(OrderStatusEnum.WMS_ACCEPT);
		order.setSystemSource(SystemSourceEnums.CAINIAO);
		order.setOrderFlag(request.getOrderFlag());
		order.setOrderSource(request.getOrderSource());
		order.setOrderCreateTime(request.getOrderCreateTime());
		order.setOtherOrderNo(getOtherNo());
		if(request.getOrderPayTime()!=null){
			order.setOrderPayTime(request.getOrderPayTime());
		}
		if(request.getOrderExaminationTime()!=null){
			order.setOrderExaminationTime(request.getOrderExaminationTime());
		}
		if(request.getOrderShopCreateTime()!=null){
			order.setOrderShopCreateTime(request.getOrderShopCreateTime());
		}
		order.setOrderAmount(request.getOrderAmount());
		order.setDiscountAmount(request.getDiscountAmount());
		order.setArAmount(request.getArAmount());
		order.setGotAmount(request.getGotAmount());
		order.setPostfee(request.getPostfee());
		order.setTmsServiceCode(request.getTmsServiceCode());
		order.setTmsServiceName(request.getTmsServiceName());
		order.setTmsOrderCode(request.getTmsOrderCode());
		order.setPrevOrderCode(request.getPrevOrderCode());
		order.setPrevErpOrderCode(request.getPrevErpOrderCode());
		order.setTimeZone(request.getTimeZone());
		order.setCurrency(request.getCurrency());
		order.setRemark(request.getRemark());
		order.setBuyerMessage(request.getBuyerMessage());
		order.setSellerMessage(request.getSellerMessage());
		order.setServiceFee(request.getServiceFee());
		this.buildCuByStoreCode(order.getUser());
		this.buildDeliverRequirements(order, request);
		this.buildReceiverInfo(order, request);
		this.buildSenderInfo(order, request);
		this.buildInvoiceInfo(order, request);
		this.buildOrderItemInfo(order, request, paramsList);
		this.buildExtendFields(order, request);
		this.buildPackageRequirements(order, request);
		// 保存订单 发货信息
		this.shipOrderService.insertShipOrder(order);
		this.saveShipOrderOperator(order, order.getOrderCode()+"菜鸟订单创建！");
		this.redisProxy.set(RdsConstants.ORDER_ACCEPT+order.getId(), order.getId());
		/**
		 * 状态回传
		 */
//		this.orderStatusUploadService.updateOrderState(order, null, "201");
		
		
		Map<String,Object>  updateMap = new HashMap<String,Object>();
		updateMap.put("paramsList", paramsList);
		updateMap.put("user", order.getUser());
		//modify shark 2017 - 09 - 26
		date=new Date();	
		this.inventoryService.updateInventoryByCreate(updateMap);
		logger.error("更新库存"+(new Date().getTime()-date.getTime()));
//		this.inventoryService.updateInventoryByOrder(paramsList, type);
	}
	
	private  String getOtherNo(){
//		return new Date().getTime();
		return UUID.randomUUID().toString();
	}

	/**
	 * 订单商品明细处理
	 * 
	 * @param order
	 * @param paramsList 
	 * @param object
	 */
	private void buildOrderItemInfo(ShipOrder order, WmsConsignOrderNotifyRequest request, List<Map<String, Object>> paramsList) throws MyException{
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.WmsConsignOrderItem> objList = request
				.getOrderItemList();
		Double totalWeight=new Double(0);
		String errMsg = "";
		List<WmsConsignOrderItem> itemList = new ArrayList<WmsConsignOrderItem>();
		StringBuilder buffer=new StringBuilder();
		TmsOrder tmsOrder=new TmsOrder();
		tmsOrder.generateId();
		List<TmsOrderItem> tmsOrderItem=new ArrayList<TmsOrderItem>();
		for (int i = 0; objList != null && i < objList.size(); i++) {
			com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.WmsConsignOrderItem obj = objList.get(i);
			Map<String,Object> pMap=new HashMap<String, Object>();
			pMap.put("itemCode", obj.getItemCode());
			pMap.put("userId", order.getUser().getId());
			List<Item> itemMap=this.itemService.getItemByList(pMap);
			Item itemObj=itemMap.get(0);
			TmsOrderItem tmsItem=new TmsOrderItem();
			tmsItem.generateId();
			WmsConsignOrderItem item = new WmsConsignOrderItem();
			item.generateId();
			item.setOrderItemId(obj.getOrderItemId());
			item.setItem(itemMap!=null?itemMap.get(0):null);
			item.setOrderSourceCode(obj.getOrderSourceCode());
			item.setSubSourceCode(obj.getSubSourceCode());
			item.setUserId(obj.getUserId());
			item.setUsername(obj.getUserName());
			item.setOwnerUserId(obj.getOwnerUserId());
			item.setOwnerUserName(obj.getOwnerUserName());
			item.setItemName(obj.getItemName());
			item.setItemCode(obj.getItemCode());
			item.setInventoryType(obj.getInventoryType());
			item.setItemQuantity(obj.getItemQuantity());
			item.setActualPrice(obj.getActualPrice());
			item.setItemPrice(obj.getItemPrice());
			item.setItemVersion(obj.getItemVersion());
			String sku = (itemObj.getSpecification()==null?"":(itemObj.getSpecification()+";"));
			if (itemObj.getGrossWeight()!=null) {
				totalWeight+=itemObj.getGrossWeight()*item.getItemQuantity();
			}
			/**
			 * TMSOrderItem对象封装
			 */
			tmsItem.setItemCode(itemObj.getItemCode());
			tmsItem.setItemId(itemObj.getItemId());
			tmsItem.setItemQuantity(item.getItemQuantity());
			tmsItem.setOrderItemId(item.getOrderItemId());
			tmsItem.setTmsOrder(tmsOrder);
			tmsItem.setItem(itemMap!=null?itemMap.get(0):null);
			tmsItem.setOrder(order);
			/**
			 * END
			 */
			if(("idzc16473350928").equals(order.getUser().getId())||("idzc2697044122").equals(order.getUser().getId())){
				buffer.append(itemObj.getSpecification()).append("(").append(item.getItemQuantity()).append("件);|");
			}else if(("idzc16473350940").equals(order.getUser().getId())){
				buffer.append(itemObj.getCategoryName()).append(itemObj.getSpecification()).append("(").append(item.getItemQuantity()).append("件);|");
			}else if(("idzc2259039943").equals(order.getUser().getId())||("idzc83817342").equals(order.getUser().getId())||("idzc16473350931").equals(order.getUser().getId())){
				buffer.append(itemObj.getItemCode()).append(":").append(itemObj.getSpecification()).append("(").append(item.getItemQuantity()).append("件);|");
			}else if(("idzc2736520696").equals(order.getUser().getId())||("idzc3400408914").equals(order.getUser().getId())){
				buffer.append(itemObj.getName()).append("(").append(item.getItemQuantity()).append("件);|");
			}else if(("idzc16473350917").equals(order.getUser().getId())||("idzc3400408914").equals(order.getUser().getId())){
				//优比特明细打印需竖排打印，明细里面添加换行字符
				buffer.append(itemObj.getName()).append(":").append(sku).append("(").append(item.getItemQuantity()).append("件);|").append("\n");
			}else if(("idzc2902840865").equals(order.getUser().getId())){
				buffer.append(itemObj.getName()).append("/").append(itemObj.getItemCode()).append("/").append(itemObj.getSpecification()).append("(").append(item.getItemQuantity()).append("件);|");
			}else if(("idzc660824712").equals(order.getUser().getId())){
				String goodsNo = (itemObj.getGoodsNo()!=null?itemObj.getGoodsNo():"");
				buffer.append(itemObj.getName()).append("/").append(goodsNo).append("/").append(itemObj.getSpecification()).append("(").append(item.getItemQuantity()).append("件);|");
			}else {
				buffer.append(itemObj.getName()).append(":").append(sku).append("(").append(item.getItemQuantity()).append("件);|");
			}
			
			item.setOrder(order);
			itemList.add(item);
			
			//库存校验
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("item", itemObj);
			p.put("inventoryType", item.getInventoryType());
			p.put("quantity", item.getItemQuantity());
			Map<String, Object> map = this.inventoryService.isHaveInventory(p);
			errMsg = errMsg+(map.get("msg")==null?"":map.get("msg"));	
			
			//修改库存map拼接
			p.clear();
			p.put("itemId", itemObj.getId());
			p.put("orderId", order.getId());
			p.put("ownerUserId", obj.getOwnerUserId());
			p.put("inventoryType", InventoryTypeEnum.getInventoryType(""+item.getInventoryType()));
			p.put("quantity", item.getItemQuantity());
			p.put("orderCode", order.getOrderCode());
			paramsList.add(p);
			tmsOrderItem.add(tmsItem);
		}
		
		//tmsOrder的items赋值
		tmsOrder.setItems(buffer.toString());
		
		order.setItems(buffer.toString());
		/**
		 * 库存不足，异常抛出
		 */
		if (StringUtils.isNotEmpty(errMsg)) {
			this.saveShipOrderOperator(order, order.getOrderCode()+"库存不足,拒单！"+errMsg);
			throw new MyException(errMsg);
		}
		order.setTotalWeight(totalWeight);
		order.setOrderItemList(itemList);
		
		//tmsOrder重量
		tmsOrder.setPackageWeight(totalWeight);
		
		tmsOrder.setOrder(order);
		tmsOrder.setCreateDate(new Date());
		tmsOrder.setStatus(TmsOrder.split);
		tmsOrder.setItems(order.getItems());
		tmsOrder.setOrderStatus(OrderStatusEnum.WMS_ACCEPT);
		
		/**
		 * tmsOrder推送订单没有赋值code字段
		 */
		tmsOrder.setCode(order.getTmsServiceCode());
		
		/**
		 * 地址处理
		 */
		ReceiverInfo receiverInfo=order.getReceiverInfo();
		tmsOrder.setReceiverProvince(receiverInfo.getReceiverProvince());
		int index=0;
		if(StringUtils.isNotEmpty(receiverInfo.getReceiverAddress())){
			if(receiverInfo.getReceiverAddress().indexOf("乡")!=-1 || receiverInfo.getReceiverAddress().indexOf("镇")!=-1 
					||receiverInfo.getReceiverAddress().indexOf("村")!=-1 || receiverInfo.getReceiverAddress().indexOf("组")!=-1  ){
				tmsOrder.setAddressStatus("include");
				index=1;
			}
		}
		if(StringUtils.isNotEmpty(receiverInfo.getReceiveTown())){
			if(receiverInfo.getReceiveTown().indexOf("乡")!=-1 || receiverInfo.getReceiveTown().indexOf("镇")!=-1 
					||receiverInfo.getReceiveTown().indexOf("村")!=-1 || receiverInfo.getReceiveTown().indexOf("组")!=-1  ){
				tmsOrder.setAddressStatus("include");
				index=1;
			}
		}
		if(index==0){
			tmsOrder.setAddressStatus("noInclude");
		}
		
		this.tmsOrderService.insertTmsOrder(tmsOrder);
		this.tmsOrderService.insertTmsOrderItem(tmsOrderItem);
		// 持久化数据
//		this.wmsConsignOrderItemService.insertWmsConsignOrderItemList(itemList);
	}

	/**
	 * 配送要求处理
	 * 
	 * @param order
	 * @param obj
	 * @throws Exception
	 */
	private void buildDeliverRequirements(ShipOrder order,
			WmsConsignOrderNotifyRequest request) throws Exception {
		com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.DeliverRequirements deliver = request
				.getDeliverRequirements();
		if(deliver==null){
			return;
		}
		DeliverRequirements oo = new DeliverRequirements();
		oo.generateId();
		oo.setOrder(order);
		oo.setDeliveryType(deliver.getDeliveryType());
		oo.setScheduleArriveTime(deliver.getScheduleArriveTime());
		oo.setScheduleType(deliver.getScheduleType());
		if(deliver.getScheduleDay()!=null){
			oo.setScheduleDay(sdfyy.parse(deliver.getScheduleDay()));
			oo.setScheduleEnd(sdf.parse(deliver.getScheduleDay()+" "+deliver.getScheduleEnd()));
			oo.setScheduleStart(sdf.parse(deliver.getScheduleDay()+" "+deliver.getScheduleStart()));
		}
		// 持久化信息
		 this.deliverRequirementsService.saveDeliverRequirements(oo);
	}

	/**
	 * 收件人信息处理
	 * 
	 * @param order
	 * @param obj
	 */
	private void buildReceiverInfo(ShipOrder order,  WmsConsignOrderNotifyRequest request) {
		com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.ReceiverInfo obj = request.getReceiverInfo();
		ReceiverInfo info = new ReceiverInfo();
		order.setReceiverInfo(info);
		info.generateId();
		info.setReceiverZipCode(obj.getReceiverZipCode());
		info.setReceiverAddress(obj.getReceiverAddress());
		info.setReceiverArea(obj.getReceiverArea());
		info.setReceiverCity(obj.getReceiverCity());
		info.setReceiverCountry(obj.getReceiverCountry());
		info.setReceiverDivisionId(obj.getReceiverDivisionId());
		info.setReceiverEmail(obj.getReceiverEmail());
		info.setReceiverMobile(obj.getReceiverMobile());
		info.setReceiverName(obj.getReceiverName());
		info.setReceiverNick(obj.getReceiverNick());
		info.setReceiverPhone(obj.getReceiverPhone());
		info.setReceiverProvince(obj.getReceiverProvince());
		info.setReceiveTown(obj.getReceiveTown());
		// 持久化对象
		// this.receiverInfoService.saveReceiverInfo(info);
	}

	/**
	 * 发货方信息处理
	 * 
	 * @param order
	 * @param obj
	 */
	private void buildSenderInfo(ShipOrder order, WmsConsignOrderNotifyRequest request) {
		com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.SenderInfo obj = request.getSenderInfo();
		SenderInfo info = new SenderInfo();
		info.generateId();
		order.setSenderInfo(info);
		info.setSenderAddress(obj.getSenderAddress());
		info.setSenderArea(obj.getSenderArea());
		info.setSenderCity(obj.getSenderCity());
		info.setSenderCountry(obj.getSenderCountry());
		info.setSenderDivisionId(obj.getSenderDivisionId());
		info.setSenderEmail(obj.getSenderEmail());
		info.setSenderMobile(obj.getSenderMobile());
		info.setSenderName(obj.getSenderName());
		info.setSenderPhone(obj.getSenderPhone());
		info.setSenderProvince(obj.getSenderProvince());
		info.setSenderTown(obj.getSenderTown());
		info.setSenderZipCode(obj.getSenderZipCode());
		/**
		 * 持久化对象信息
		 */
		// this.senderInfoService.save(info);
	}

	/**
	 * 发票信息处理。
	 * 
	 * @param order
	 * @param obj
	 */
	private void buildInvoiceInfo(ShipOrder order,  WmsConsignOrderNotifyRequest request) {

		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.InvoinceItem> invoiceList = request
				.getInvoiceInfoList();
		List<InvoiceInfo> resultList = new ArrayList<InvoiceInfo>();
		for (int i = 0; invoiceList != null && i < invoiceList.size(); i++) {
			com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.InvoinceItem invoice = invoiceList.get(i);
			InvoiceInfo info = new InvoiceInfo();
			info.generateId();
			info.setBillType(invoice.getBillType());
			info.setBillId(invoice.getBillId());
			info.setBillTitle(invoice.getBillTitle());
			info.setBillAccount(invoice.getBillAccount());
			info.setBillContent(invoice.getBillContent());
			info.setBuyerNo(invoice.getBuyerNo());
			info.setBuyerAddrPhone(invoice.getBuyerAddrPhone());
			info.setBuyerBankAccount(invoice.getBuyerBankAccount());
			/**
			 * 处理发票商品明细
			 */
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<InvoiceItemDetail> detailList = new ArrayList<InvoiceItemDetail>();
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.ItemDetail> itemList = invoice
					.getDetailList();

			for (int j = 0; itemList != null && j < itemList.size(); j++) {
				com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.ItemDetail item = itemList.get(j);
				InvoiceItemDetail detail = new InvoiceItemDetail();
				detail.generateId();
				if (info!=null) {
					detail.setInvoice(info);
				}
				detail.setItemName(item.getItemName());
				if (item.getUnit()!=null) {
					detail.setUnit(item.getUnit());
				}
				detail.setPrice(item.getPrice());
				detail.setQuantity(item.getQuantity());
				detail.setAmount(item.getAmount());
				if (item.getSpecificModel()!=null) {
					detail.setSpecificModel(item.getSpecificModel());
				}		
				if (item.getTaxRate()!=null) {
					detail.setTaxRate(item.getTaxRate());
				}
				if (item.getTaxAmount()!=null) {
					detail.setTaxAmount(item.getTaxAmount());
				}
				if (item.getTaxCode()!=null) {
					detail.setTaxCode(item.getTaxCode());
				}	
				detailList.add(detail);
			}
			info.setDetailList(detailList);
			resultList.add(info);
		}
		order.setInvoiceInfoList(resultList);
	}

	/**
	 * 包装要求信息处理
	 * 
	 * @param order
	 * @param params
	 */
	private void buildPackageRequirements(ShipOrder order,
			WmsConsignOrderNotifyRequest request) {
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.WmsConsignOrderPackageRequirement> packageList = request
				.getPackageRequirements();

		List<WmsConsignOrderPackageRequirement> list = new ArrayList<WmsConsignOrderPackageRequirement>();
		for (int i = 0; packageList != null && i < packageList.size(); i++) {
			com.taobao.pac.sdk.cp.dataobject.request.WMS_CONSIGN_ORDER_NOTIFY.WmsConsignOrderPackageRequirement obj=packageList.get(i);
			WmsConsignOrderPackageRequirement pack = new WmsConsignOrderPackageRequirement();
			pack.generateId();
			pack.getMaterialGroup();
			pack.setOrder(order);
			pack.setMaterialTypes(obj.getMaterialTypes());
			pack.setMaterialClass(obj.getMaterialClass());
			pack.setMaterialGroup(obj.getMaterialGroup());
			pack.setPriority(String.valueOf(obj.getPriority()));
			list.add(pack);
		}
		order.setPackageRequirements(list);

	}

	/**
	 * 扩展信息处理
	 * 
	 * @param order
	 * @param obj
	 */
	private void buildExtendFields(ShipOrder order,  WmsConsignOrderNotifyRequest request) {
//		String extendFields = (String) params.get("extendFields");
//		request.getExtendFields();
//		order.setExtendFields(extendFields);
	}
	
	/**
	 * 保存操作记录
	 * @param order
	 * @param description
	 */
	private void saveShipOrderOperator(ShipOrder order,String description){
		ShipOrderOperator operator=new ShipOrderOperator();
		operator.generateId();
//		Account account =;
//		account.setId("cainiao");
		operator.setAccount(new Account("cainiao"));
		operator.setDescription(description);
		operator.setOperatorDate(new Date());
		operator.setShipOrder(order);
		operator.setOperatorModel(OperatorModel.TRADE_CREATE);
		this.operatorService.saveShipOrderOperator(operator);
	}
	
	
	/**
	 * 构建CU，并存放在当前的sessionUser中
	 * @param storeCode
	 */
	private void buildCuByStoreCode(User user){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userName", Constants.cainiao_account);
		List<Account> list=this.accountService.findAccountsByList(params);
		//1.查询出属于菜鸟的那个帐号
		if(list!=null && list.size()>0){
			Account account=list.get(0);
			//2.
			account.setCu(user.getCu());
			SessionUser.set(account);
		}
	}
}