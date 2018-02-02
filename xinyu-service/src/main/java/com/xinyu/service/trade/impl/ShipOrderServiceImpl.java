package com.xinyu.service.trade.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.SenderInfoDao;
import com.xinyu.dao.base.SystemItemDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.trade.DeliverRequirementsDao;
import com.xinyu.dao.trade.InvoiceInfoDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.dao.trade.ShipOrderDao;
import com.xinyu.dao.trade.ShipOrderGroupDao;
import com.xinyu.dao.trade.ShipOrderOperatorDao;
import com.xinyu.dao.trade.ShipOrderStopDao;
import com.xinyu.dao.trade.TmsOrderDao;
import com.xinyu.dao.trade.WmsConsignOrderItemDao;
import com.xinyu.dao.trade.WmsConsignOrderPackageRequirementDao;
import com.xinyu.model.base.DeliverRequirements;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ItemGroupDetail;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.qm.InventoryReportEntry.item;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.system.enums.SystemSourceEnums;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderGroup;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.model.trade.ShipOrderStop;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.trade.TmsOrderItem;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.model.util.MyException;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.system.ItemGroupService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.service.util.IRedisProxy;

/**
 * shipOrderService业务处理实现
 * */
@Service("shipOrderServiceImpl")
public class ShipOrderServiceImpl extends BaseServiceImpl implements ShipOrderService{

	public static final Logger logger = Logger.getLogger(ShipOrderServiceImpl.class);
	
	@Autowired
	private ShipOrderDao shipOrderDao;

	@Autowired
	private ShipOrderStopDao shipOrderStopDao;

	@Autowired
	private ReceiverInfoDao receiverInfoDao;
	
	@Autowired
	private DeliverRequirementsDao deliverDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private SenderInfoDao senderInfoDao;
	
	@Autowired
	private InvoiceInfoDao invoiceInfoDao;
	
	@Autowired
	private WmsConsignOrderPackageRequirementDao packageDao;

	
	@Autowired
	private SystemItemDao systemItemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private WmsConsignOrderItemDao orderItemDao;
	
	@Autowired
	private ShipOrderOperatorDao operatorDao;
	
	@Autowired
	private ShipOrderStopDao stopDao;
	
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ItemGroupService itemGroupService;
	@Autowired
	private ShipOrderGroupDao groupDao;
	@Autowired
	private TmsOrderDao tmsOrderDao;
	@Autowired
	private IRedisProxy redisProxy;
	/**
	 * 条件查询shipOrder数据（非分页）
	 * @param params
	 * @return list
	 * */
	
	public void insertShipOrder(ShipOrder shipOrder) {
		// TODO Auto-generated method stub
		if(shipOrder.getReceiverInfo()!=null){
			this.receiverInfoDao.save(shipOrder.getReceiverInfo());
		}
		if(shipOrder.getDeliverRequirements()!=null){
			this.deliverDao.saveDeliverRequirements(shipOrder.getDeliverRequirements());
		}
		if(shipOrder.getOrderItemList()!=null){
			for(int i=0;i<shipOrder.getOrderItemList().size();i++){
				this.orderItemDao.insertWmsConsignOrderItem(shipOrder.getOrderItemList().get(i));
			}
		}
		if(shipOrder.getSenderInfo()!=null){
			this.senderInfoDao.save(shipOrder.getSenderInfo());
		}
		if(shipOrder.getInvoiceInfoList()!=null){
			for(int i=0;i<shipOrder.getInvoiceInfoList().size();i++){
				InvoiceInfo info=shipOrder.getInvoiceInfoList().get(i);
				for(int j=0;info.getDetailList()!=null && j<info.getDetailList().size();j++){
					this.invoiceInfoDao.insertInvoiceDetail(info.getDetailList().get(j));
				}
				this.invoiceInfoDao.insertInvoiceInfo(info);
			}
		}
		if(shipOrder.getPackageRequirements()!=null){
			for(int i=0;i<shipOrder.getPackageRequirements().size();i++){
				this.packageDao.insertWmsConsignOrderPackageRequirement(shipOrder.getPackageRequirements().get(i));
			}
		}
		this.shipOrderDao.insertShipOrder(shipOrder);
	}
	
	public List<ShipOrder> findShipOrderByList(Map<String, Object> params) {
		return this.shipOrderDao.getShipOrderByList(params);
	}


	/**
	 * 根据Id查询订单
	 * @param id
	 * @return shipOrder
	 * */
	
	public ShipOrder findShipOrderById(String id) {
		return this.shipOrderDao.getShipOrderById(id);
	}

	/**
	 * 条件查询shipOrder数据（分页）
	 * @param params
	 * @param pageNump
	 * @param pageSize
	 * @return list
	 * */
	
	public List<ShipOrder> findShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize) {
		return this.shipOrderDao.findShipOrderListByPage(params,pageNum,pageSize);
	}

	/**
	 * 条件查询shipOrder数据数量
	 * @param params
	 * @return int
	 * */
	
	public int getTotal(Map<String, Object> params) {
		return this.shipOrderDao.getShipOrderTotal(params);
	}

	
	public List<ShipOrder> getShipOrderListByAll(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderDao.getShipOrderListByALl(params);
	}
	
	public int getShipOrderListByAllCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderDao.getShipOrderListByAllCount(params);
//		return 999;
	}
	/**
	 * 订单数据重构
	 * @param list
	 * @return list
	 * */
	
	public List<Map<String, Object>> bulidListData(List<ShipOrder> shipOrders) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat df = new DecimalFormat("######0.00");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (ShipOrder order:shipOrders) {
			ReceiverInfo receiverInfo =order.getReceiverInfo();
			TmsOrder tmsOrder=order.getTmsOrder();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tmsId", tmsOrder!=null?tmsOrder.getId():"");
			map.put("id", tmsOrder.getOrder().getId());
			map.put("val", order.getId());
			map.put("operator", order.getId());
			map.put("createDate", sf.format(order.getCreateTime()));
			if (order.getLastUpdateTime()!=null) {
				map.put("printDate", sf.format(order.getLastUpdateTime()));//打印时间
			}
			map.put("batchNumber",(tmsOrder.getBatchCode()==null?"":tmsOrder.getBatchCode()));//打印批次号
			User user = this.userDao.getUserById(order.getUser().getId());
			if (user!=null) {
				map.put("userName", user.getSubscriberName());
			}
			String items=tmsOrder!=null?tmsOrder.getItems():order.getItems();
			if (StringUtils.isEmpty(items)) {
				map.put("items",this.bulidItemsData(order));
			}else {
				map.put("items",items);
			}
//			ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
			
			if (receiverInfo!=null) {
				map.put("nick",receiverInfo.getReceiverNick());
				map.put("name", receiverInfo.getReceiverName());
				map.put("phone", (receiverInfo.getReceiverMobile()!=null?receiverInfo.getReceiverMobile():"")+","+(receiverInfo.getReceiverPhone()!=null?receiverInfo.getReceiverPhone():""));
				String address = receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity()+receiverInfo.getReceiverArea()+receiverInfo.getReceiverAddress();
				map.put("address", address.replace(" ", ""));
			}
			map.put("expressCode", order.getTmsServiceCode());
			/**
			 * OrderPayTime为空，取OrderShopCreateTime
			 */
			if (order.getOrderPayTime()!=null) {
				map.put("payTime", sf.format(order.getOrderPayTime()));
			}else {
				map.put("payTime", sf.format(order.getOrderShopCreateTime()));
			}
			map.put("orderNo", tmsOrder.getOrderCode());
			map.put("company", tmsOrder.getCode());
			map.put("buyerMemo", order.getBuyerMessage());
			map.put("sellerMemo", order.getSellerMessage());
			map.put("remark", order.getRemark());
			map.put("orderCode", order.getOrderCode());
			map.put("erpCode", order.getErpOrderCode());
			if (StringUtils.isNotBlank(order.getShopName())) {
				map.put("shopName", order.getShopName());
			}
			map.put("status", tmsOrder.getOrderStatus().getKey());	
			double weight = (order.getTotalWeight()==null?0d:order.getTotalWeight())*0.001;
			map.put("weight", df.format(weight));
			
			//tmsOrder的重量
			double tmsWeight = (tmsOrder.getPackageWeight())*0.001;
			map.put("tmsWeight", df.format(tmsWeight));
			
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * 计算订单重量
	 * @param shipOrder
	 * @return weight
	 * */
	private Long buildShipOrderWeight(ShipOrder shipOrder) {
		List<WmsConsignOrderItem> orderItems = shipOrder.getOrderItemList();
		Long weight = 0L;
		if(orderItems==null){
			return weight;
		}
		for(WmsConsignOrderItem orderItem:orderItems){
			Item item = orderItem.getItem();
			weight=weight+item.getWmsGrossWeight();
		}
		return weight;
	}


	/**
	 * 2017-09-14
	 * 拼商品明细
	 * @param shipOrder
	 * @return items
	 * */
	
	public String bulidItemsData(ShipOrder shipOrder) {
		String items = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", shipOrder.getId());
		List<WmsConsignOrderItem> orderItems = this.orderItemDao.getWmsConsignOrderItemByList(params);
		for(WmsConsignOrderItem orderItem:orderItems){
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			/**
			 * 如果订单商品是天际的，订单明细用Specification字段拼
			 * */
			String sku = (item.getSpecification()==null?"":(item.getSpecification()+";"));
			if (("idzc16473350928").equals(item.getUser().getId())) {
				items = items + item.getSpecification() + "("+orderItem.getItemQuantity()+"件);";
			}else if(("idzc16473350940").equals(item.getUser().getId())){
				items = items + item.getCategoryName()+item.getSpecification() + "("+orderItem.getItemQuantity()+"件);";
			}else if(("idzc83817342").equals(item.getUser().getId())){
				items = items + item.getItemCode()+":"+item.getSpecification()+"("+orderItem.getItemQuantity()+"件);";
			}else if(("idzc2902840865").equals(item.getUser().getId())){
				items = items + item.getName() + "/" + item.getItemCode() + "/" + item.getSpecification()+"("+orderItem.getItemQuantity()+"件);";
			}else{
				items = items + item.getName() +":"+sku+ "("+orderItem.getItemQuantity()+"件);";
			}
		}
		return items;
	}
	
	
	
	public String bulidItemsDataByTms(TmsOrder tmsOrder) {
		String items = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmsOrderId", tmsOrder.getId());
		List<TmsOrderItem> orderItems =this.tmsOrderDao.getTmsOrderItemByList(params);
		for(TmsOrderItem orderItem:orderItems){
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			/**
			 * 如果订单商品是天际的，订单明细用Specification字段拼
			 * */
			String sku = (item.getSpecification()==null?"":item.getSpecification());
			if (("idzc16473350928").equals(item.getUser().getId())) {
				items = items + item.getSpecification() + "("+orderItem.getItemQuantity()+"件);|";
			}else if(("idzc16473350940").equals(item.getUser().getId())){
				items = items + item.getCategoryName()+item.getSpecification() + "("+orderItem.getItemQuantity()+"件);|";
			}else if(("idzc83817342").equals(item.getUser().getId())||("idzc16473350931").equals(item.getUser().getId())){
				items = items + item.getItemCode()+":"+item.getSpecification()+"("+orderItem.getItemQuantity()+"件);|";
			}else if (("idzc2902840865").equals(item.getUser().getId())) {
				items = items + item.getName() + "/" + item.getItemCode() + "/" + sku+ "("+orderItem.getItemQuantity()+"件);|";
			}else if (("idzc660824712").equals(item.getUser().getId())) {
				items = items + item.getName() + "/" + (item.getGoodsNo()!=null?(item.getGoodsNo()+ "/"):"")  + sku+ "("+orderItem.getItemQuantity()+"件);|";
			}else{
				items = items + item.getName() +":"+sku+ "("+orderItem.getItemQuantity()+"件);|";
			}
		}
		return items;
	}

	
	private String bulidItemsDataByTms(List<TmsOrderItem> orderItems) {
		String items = "";
		for(TmsOrderItem orderItem:orderItems){
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			/**
			 * 如果订单商品是天际的，订单明细用Specification字段拼
			 * */
			String sku = (item.getSpecification()==null?"":(item.getSpecification()+";"));
			if (("idzc16473350928").equals(item.getUser().getId())) {
				items = items + item.getSpecification() + "("+orderItem.getItemQuantity()+"件);";
			}else if(("idzc16473350940").equals(item.getUser().getId())){
				items = items + item.getCategoryName()+item.getSpecification() + "("+orderItem.getItemQuantity()+"件);";
			}else if(("idzc83817342").equals(item.getUser().getId())||("idzc16473350931").equals(item.getUser().getId())){
				items = items + item.getItemCode()+":"+item.getSpecification()+"("+orderItem.getItemQuantity()+"件);";
			}else if (("idzc2902840865").equals(item.getUser().getId())) {
				items = items + item.getName() + "/" + item.getItemCode() + "/" + sku+ "("+orderItem.getItemQuantity()+"件);";
			}else{
				items = items + item.getName() +":"+sku+ "("+orderItem.getItemQuantity()+"件);";
			}
		}
		return items;
	}

	/**
	 * 根据ID修改订单快递信息
	 * @param id
	 * @param sysId
	 * */
	
	public void chooseExpress(String id, String sysId, Account account) {
		ShipOrder shipOrder = this.findShipOrderById(id);
		String oldValue = shipOrder.getTmsServiceCode();
		SystemItem systemItem = this.systemItemDao.findSystemItemById(sysId);
		shipOrder.setTmsServiceCode(systemItem.getValue());
		shipOrder.setTmsServiceName(systemItem.getDescription());
		shipOrder.setStatus(OrderStatusEnum.WMS_AUDIT);
		this.shipOrderDao.updateShipOrder(shipOrder);		
		String newValue = systemItem.getValue();
		String description = "订单审核！";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shipOrder", shipOrder);
		params.put("description", description);
		params.put("newValue", newValue);
		params.put("oldValue", oldValue);
		params.put("model", OperatorModel.TRADE_AUDIT);
		params.put("account", account);
		this.insertShipOrderOperator(params);
	}

	/**
	 * 订单删除
	 * @param id
	 * @param account
	 * */
	
	public void deleteShipOrder(String id,Account account) {
		ShipOrder shipOrder = this.shipOrderDao.getShipOrderById(id);
		if (shipOrder!=null) {
			
			/**
			 * 订单删除，更新TmsOrder
			 */
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("orderId", shipOrder.getId());
			List<TmsOrder> tmsOrders = this.tmsOrderDao.getTmsOrderByList(p);
			for(TmsOrder tmsOrder:tmsOrders){
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_DELETED);
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
			}
			
			
			String oldValue = shipOrder.getStatus().getKey();
			shipOrder.setStatus(OrderStatusEnum.WMS_DELETED);
			this.shipOrderDao.updateShipOrder(shipOrder);
			
			String description = shipOrder.getOrderCode()+"订单删除！"+shipOrder.getTmsOrderCode();
			this.insertShipOrderStop(shipOrder, description);
			
			String newValue = OrderStatusEnum.WMS_DELETED.getKey();	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shipOrder", shipOrder);
			params.put("description", description);
			params.put("newValue", newValue);
			params.put("oldValue", oldValue);
			params.put("model", OperatorModel.TRADE_DEL);
			params.put("account", account);
			this.insertShipOrderOperator(params);
		}
		
//		List<WmsConsignOrderItem> orderItems = shipOrder.getOrderItemList();
//		for(WmsConsignOrderItem orderItem:orderItems){
//			this.orderItemDao.deleteWmsConsignOrderItem(orderItem.getId());
//		}
//		
//		List<WmsConsignOrderPackageRequirement> packageRequirements = shipOrder.getPackageRequirements();
//		for(WmsConsignOrderPackageRequirement orderPackageRequirement:packageRequirements){
//			this.orderPackageRequirementDao.deleteWmsConsignOrderItemPackageRequirement(orderPackageRequirement.getId());
//		}
//		
//		
//		DeliverRequirements deliverRequirements = shipOrder.getDeliverRequirements();
//		this.deliverRequirementsDao.deleteDeliverRequirements(deliverRequirements.getId());
//		
//		List<InvoiceInfo> invoiceInfoList = shipOrder.getInvoiceInfoList();
//		for(InvoiceInfo invoiceInfo:invoiceInfoList){
//			invoiceInfo.getDetailList();
//			this.invoiceInfoDao.deleteInvoiceInfo(invoiceInfo.getId());
//		}
//		
//		ReceiverInfo receiverInfo = shipOrder.getReceiverInfo();
//		ReceiverInfo uniReceiverInfo = shipOrder.getUniReceiverInfo();
//		this.receiverInfoDao.deleteReceiverInfo(receiverInfo.getId());
//		this.receiverInfoDao.deleteReceiverInfo(uniReceiverInfo.getId());
//		
//		SenderInfo senderInfo = shipOrder.getSenderInfo();
//		this.senderInfoDao.deleteSenderInfo(senderInfo.getId());
//		
//		this.shipOrderDao.deleteShipOrder(shipOrder.getId());
	}

	/**
	 * 作废订单 
	 * @param id
	 * @param account
	 */
	
	public void invalidShipOrder(String id,Account account) {
		/**
		 * 1.修改订单删除功能为UPDATE
		 * 2.判断当前单据是不是奇门单据，如果是则判断单据状态。如果是未出库单据，则更新占用库存。
		 */
		System.err.println("orderId:"+id);
		ShipOrder shipOrder=this.shipOrderDao.getShipOrderById(id);
		if (shipOrder!=null) {
			
			/**
			 * 订单作废，更新TmsOrder
			 */
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("orderId", shipOrder.getId());
			List<TmsOrder> tmsOrders = this.tmsOrderDao.getTmsOrderByList(p);
			for(TmsOrder tmsOrder:tmsOrders){
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_CANCEL);
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
			}
			
			String oldValue = shipOrder.getStatus().getKey();
			String newValue = OrderStatusEnum.WMS_CANCEL.getKey();
			shipOrder.setStatus(OrderStatusEnum.WMS_CANCEL);
			this.shipOrderDao.updateShipOrder(shipOrder);
			String description = shipOrder.getOrderCode()+"订单作废！"+shipOrder.getTmsOrderCode();
			this.insertShipOrderStop(shipOrder, description);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shipOrder", shipOrder);
			params.put("description", description);
			params.put("newValue", newValue);
			params.put("oldValue", oldValue);
			params.put("model", OperatorModel.TRADE_INVALID);
			params.put("account", account);
			this.insertShipOrderOperator(params);
			if(StringUtils.isNotEmpty(shipOrder.getTmsOrderCode())){
				this.redisProxy.set("Return"+shipOrder.getTmsOrderCode(), shipOrder.getTmsOrderCode());
			}
		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("orderId", id);
//		List<WmsConsignOrderItem> detailList=this.orderItemDao.getWmsConsignOrderItemByList(params);
//		order.setStatus(ShipOrderStatusEnum.WMS_DELETED);
		
	}


	/**
	 * 订单退货
	 * @param shipOrder
	 * @param account
	 * @return map
	 * */
	
	@Transactional
	public Map<String, Object> addReturn(ShipOrder shipOrder,Account account) {
		String oldValue = shipOrder.getStatus().getKey();
		String newValue = OrderStatusEnum.WMS_RETURN.getKey();
 		shipOrder.setStatus(OrderStatusEnum.WMS_RETURN);
		Map<String, Object> resultMap = new HashMap<String,Object>();
		String msg = "";
		if (shipOrder!=null) {
			
			/**
			 * 订单退货，更新tmsOrder
			 */
			Map<String,Object> p=new HashMap<String, Object>();
			p.put("orderId", shipOrder.getId());
			List<TmsOrder> tmsOrders = this.tmsOrderDao.getTmsOrderByList(p);		
			for(TmsOrder tmsOrder:tmsOrders){
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_RETURN);
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
			}
			
			this.shipOrderDao.updateShipOrder(shipOrder);
			if (shipOrder.getTmsOrderCode()!=null) {
				//判断是否有过退货，如果有过则不能再退
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("orderId", shipOrder.getId());
				List<ShipOrderStop> shipOrderStops=this.shipOrderStopDao.getShipOrderStopByList(params);
				if(shipOrderStops==null || shipOrderStops.size()==0){
					String description=shipOrder.getOrderCode()+"订单退货！"+shipOrder.getTmsOrderCode();
					this.insertShipOrderStop(shipOrder, description);
					Map<String, Object> map = new HashMap<String, Object>();
					params.put("shipOrder", shipOrder);
					params.put("description", description);
					params.put("newValue", newValue);
					params.put("oldValue", oldValue);
					params.put("model", OperatorModel.TRADE_RETURN);
					params.put("account", account);
					this.insertShipOrderOperator(map);
					this.redisProxy.set("Return"+shipOrder.getTmsOrderCode(), shipOrder.getTmsOrderCode());
					logger.error("订单退货缓存:"+shipOrder.getTmsOrderCode());
					resultMap.put("ret", 1);
					resultMap.put("msg", "订单退货成功!");
				}else{
					resultMap.put("ret", 0);
					resultMap.put("msg", "订单不能重复退货!");
				}		
			}else {
				resultMap.put("ret",0);
				resultMap.put("msg", "此订单没有快递,不能退货!");
			}
		}else {
			resultMap.put("ret", 0);
			resultMap.put("msg", "此订单不存在!");
		}
		return resultMap;
	}

	/**
	 * 修改shipOrder信息
	 * @param shipOrder
	 * */
	
	public void updateShipOrder(ShipOrder shipOrder) {
		this.shipOrderDao.updateShipOrder(shipOrder);
	}

	/**
	 * 计算订单重量
	 * @param shipOrder
	 * @return long(克)
	 * */
	
	public Long buildWeight(ShipOrder shipOrder) {
		System.err.println("shipOrder:"+shipOrder.getId());
		Long weight = 0L;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", shipOrder.getId());
		List<WmsConsignOrderItem> orderItems = this.orderItemDao.getWmsConsignOrderItemByList(params);
		System.err.println("orderItems:"+orderItems);
		for(WmsConsignOrderItem orderItem:orderItems){
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			if (item.getWmsGrossWeight()!=null) {
				weight=weight+(item.getWmsGrossWeight())*orderItem.getItemQuantity();
			}
		}
		return weight;
	}


	/**
	 * 拆单操作
	 * */
	
	public Map<String, Object> splitShipOrderOperation(String type, String orderId, Long num) {
		ShipOrder shipOrder = this.shipOrderDao.getShipOrderById(orderId);
		DeliverRequirements requirements = this.deliverDao.getDeliverRequirementsById(shipOrder.getDeliverRequirements().getId());
		ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(shipOrder.getReceiverInfo().getId());
		SenderInfo senderInfo = this.senderInfoDao.getSenderInfoById(shipOrder.getSenderInfo().getId());
		logger.debug("splitShipOrderOperation"+orderId+"userId:"+shipOrder.getUser().getId());
		Map<String,Object> retMap  = new HashMap<String,Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		List<WmsConsignOrderItem> detailList = this.orderItemDao.getWmsConsignOrderItemByList(params);
		int size =  detailList.size();		
		boolean  isSplit   = false;
		//快速拆单 按照行   拆
		if ("line".equals(type) && size > 1) {  
			isSplit =  true;
			//复制原始单据单头信息
			ShipOrder order  =  null;
			ReceiverInfo receiver = null;
			SenderInfo sender = null;
			DeliverRequirements deliverRequirements = null;
			Date now = new Date();
			try {
				order = (ShipOrder) shipOrder.clone();
				receiver = (ReceiverInfo) receiverInfo.clone();
				sender = (SenderInfo) senderInfo.clone();	
				deliverRequirements = (DeliverRequirements) requirements.clone();
				order.generateId();
				//寄件人
				sender.generateId();
				order.setSenderInfo(sender);
				//收件人
				receiver.generateId();
				order.setReceiverInfo(receiver);
				order.setUniReceiverInfo(receiver);
				//包装信息
				deliverRequirements.generateId();
				deliverRequirements.setOrder(order);
				order.setDeliverRequirements(deliverRequirements);
				
				order.setCreateTime(now);
				order.setOrderExaminationTime(now);
				order.setSplitStatus("split_"+order.getOrderCode());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				logger.error(e);
				retMap.put("code", "500");
				retMap.put("msg", "原始单据抬头异常");
				e.printStackTrace();
			}
					
			for (int i = 1; i < size; i++) {
				//辅助单据明细
				WmsConsignOrderItem orderItem = detailList.get(i);  
				WmsConsignOrderItem detail = null;
				try {
					detail = (WmsConsignOrderItem) orderItem.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					logger.error(e);
					retMap.put("code", "500");
					retMap.put("msg", "出库单明细复制异常");
					e.printStackTrace();
				}
				detail.generateId();
				detail.setOrder(order);				
				detailList.add(detail);
								
				//删除订单明细数据
				this.orderItemDao.deleteWmsConsignOrderItem(orderItem.getId());
				
				//查单明细信息
				List<WmsConsignOrderItem> detailListTemp  = new ArrayList<WmsConsignOrderItem>();
				detailListTemp.add(detail);
				order.setOrderItemList(detailListTemp);
				this.shipOrderDao.insertShipOrder(order);
//				order.setOtherOrderNo(new Date().getTime());
//				this.saveEntryOrder(order);
 				for (int j = 0; order.getOrderItemList() != null && j < order.getOrderItemList().size(); j++) {
//					this.entryOrderDetailJpaDao.save(order.getOrderItemList().get(j));
 					this.orderItemDao.insertWmsConsignOrderItem(order.getOrderItemList().get(j));
 				}
			}
		}else  if("count".equals(type)){
			for (int i = 0; i < size; i++) {
				WmsConsignOrderItem shipOrderDetail = detailList.get(i);
				Long  numTemp = shipOrderDetail.getItemQuantity();
				if(numTemp<=num){  //不满足拆单最小数量
					continue;
				}
				isSplit =  true;
				long index  = numTemp/num;  //除数
				long indexTemp = numTemp%num;//余数
				index  =  indexTemp>0  ? index+1:index; //余数不为0  时
				
				for(int k  = 0; k<index ; k++){
					if(k!=0){ //非第一条数据
						ShipOrder order  =  null;
						ReceiverInfo receiver = null;
						SenderInfo sender = null;
						DeliverRequirements deliverRequirements = null;
						Date now = new Date();
						try {
							order = (ShipOrder) shipOrder.clone();
							receiver = (ReceiverInfo) receiverInfo.clone();
							sender = (SenderInfo) senderInfo.clone();	
							deliverRequirements = (DeliverRequirements) requirements.clone();
							order.generateId();
							//寄件人
							sender.generateId();
							order.setSenderInfo(sender);
							//收件人
							receiver.generateId();
							order.setReceiverInfo(receiver);
							order.setUniReceiverInfo(receiver);
							//包装信息
							deliverRequirements.generateId();
							deliverRequirements.setOrder(order);
							order.setDeliverRequirements(deliverRequirements);
							
							order.setCreateTime(now);
							order.setOrderExaminationTime(now);
							order.setSplitStatus("split_"+order.getOrderCode());
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							logger.error(e);
							e.printStackTrace();
							retMap.put("code", "500");
							retMap.put("msg", "原始单据抬头异常");
						}
						WmsConsignOrderItem detail = null;
						try {
							detail = (WmsConsignOrderItem) shipOrderDetail.clone();
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							logger.error(e);
							e.printStackTrace();
							retMap.put("code", "500");
							retMap.put("msg", "出库单明细复制异常");
						}
						detail.generateId();
						detail.setItemQuantity(num); // 商品数量
//						detail.setActualnum(num.intValue()); // 商品实际数量
						detail.setOrder(order);
						//查单明细信息
						List<WmsConsignOrderItem> detailListTemp  = new ArrayList<WmsConsignOrderItem>();
						detailListTemp.add(detail);
						order.setOrderItemList(detailListTemp);
						this.shipOrderDao.insertShipOrder(order);
//						order.setOtherOrderNo(new Date().getTime());
//						this.saveEntryOrder(order);
						for (int j = 0; order.getOrderItemList() != null && j < order.getOrderItemList().size(); j++) {
//							this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
							this.orderItemDao.insertWmsConsignOrderItem(order.getOrderItemList().get(j));
						}
						
					}else{
						Map<String, Object> detailParams = new HashMap<String, Object>();
						// 修改订单明细数据
						shipOrderDetail.setItemQuantity((indexTemp > 0 ? indexTemp : num));
						this.orderItemDao.updateWmsConsignOrderItem(shipOrderDetail);
					}
				}
			}
		}
		//标记主表是否拆单
		if(isSplit){
			Map<String, Object> orderParams = new HashMap<String, Object>();
			shipOrder.setSplitStatus("split");
			this.shipOrderDao.updateShipOrder(shipOrder);
		}
		retMap.put("code", "200");
		retMap.put("msg", "成功");
		return retMap;
	}

	@Transactional
	public Map<String, Object> orderCancel(String orderCode, String userId, String orderType) throws MyException {
		logger.debug("ERP订单取消开始:"+userId+"|"+orderCode);
		/**
		 * 单据取消业务逻辑
		 * 1.修改单据状态
		 * 3.修改库存相关
		 * 4.拦截出库处理
		 */
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("userId", userId);
		params.put("orderType", orderType);
		ShipOrder order = this.shipOrderDao.getShipOrderByParams(params);
			
		if(order!=null){
			
			/**
			 * 订单取消，更新TmsOrder单据状态
			 */
			params.clear();
			params.put("orderId", order.getId());
			List<TmsOrder> tmsOrders = this.tmsOrderDao.getTmsOrderByList(params);	
			for(TmsOrder tmsOrder:tmsOrders){
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_CANCEL);
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
			}
			
			logger.debug("订单已在系统创建！");
			if(order.getStatus().equals(OrderStatusEnum.WMS_FINASH)){
				logger.error("订单取消失败！此订单已发货!");
				throw new MyException("订单已发货");
			}
			//2.修改单据状态
//			order.setStatus(OrderStatusEnum.WMS_CANCEL);
//			this.shipOrderDao.updateShipOrder(order);
			//3.拦截出库
			insertShipOrderStop(order, "发货单取消拦截出库!");
			//4.修改库存相关,有可以会要，暂时未定
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("order", order);
			//修改库存
			this.updateInventoryNum(param);
			
		}else{
			logger.debug("订单不存在！");
			resultMap.put("ret", 0);
			resultMap.put("msg", "发货单不存在!");
		}
		logger.debug("ERP订单取消结束:"+userId+"|"+orderCode);
		return resultMap;
	}


	/**
	 * 订单取消成功，修改库存
	 * @param map
	 * */
	private void updateInventoryNum(Map<String, Object> param) {
		
		logger.debug("订单修改库存开始！"+param);
		
		String type = "cancel"; 
		List<Map<String, Object>> paramsList = new ArrayList<Map<String,Object>>();
		
		ShipOrder order = (ShipOrder) param.get("order");
		
		/**
		 * modify 2017-08-31
		 * @author fufangjue
		 * order.getOrderItems()报空指针
		 * */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getId());	
		List<WmsConsignOrderItem> orderItems = this.orderItemDao.getWmsConsignOrderItemByList(params);
		logger.debug("orderItemsSize:"+order.getId()+"|"+orderItems.size());
		for(WmsConsignOrderItem orderItem:orderItems){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", orderItem.getItem().getId());
			map.put("quantity", orderItem.getItemQuantity());
			map.put("inventoryType", orderItem.getInventoryType());
			map.put("ownerUserId", orderItem.getOwnerUserId());
			map.put("orderCode", order.getOrderCode());
			map.put("orderId", order.getId());
			paramsList.add(map);
		}
		
		this.inventoryService.updateInventoryByOrder(paramsList, type);
		
		logger.debug("订单修改库存结束！"+param);
	}

	/**
	 * 按数据进行自定义拆单
	 * 1.首先算出来此单据可以拆成多少个单据，最后剩下的单据数量
	 * 2.拆单保存数据，余数的那个单据就为原主单据
	 * @param param
	 * @throws Exception 
	 */
	
	public Map<String, Object> splitShipOrder(HashMap<String, Object> param) {
		
		String type =  ""+param.get("type");
		if("line".equals(type)){
			return splitShipOrderLine(param);
		}
		String tmsOrderId =  (String) param.get("tmsOrderId");
		TmsOrder tmsOrder=this.tmsOrderDao.getTmsOrderById(tmsOrderId);
		ShipOrder shipOrder = this.shipOrderDao.getShipOrderById(tmsOrder.getOrder().getId());
		logger.error("自定义拆单按数量拆:"+tmsOrderId+"userId:"+shipOrder.getUser().getId());		
		Map<String,Object> retMap  = new HashMap<String,Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmsOrderId", tmsOrderId);
		List<TmsOrderItem> detailList=this.tmsOrderDao.getTmsOrderItemByList(params);
		
		int size =  detailList.size();
		boolean isSplit =  false;
		long index  = 0;  //除数
		long indexTemp = 0;//余数
	
		Map<String,Object>  quantityMap =  new HashMap<String,Object>();
		
		for(int i = 0 ;i<size ; i++){
			TmsOrderItem shipOrderDetail = detailList.get(i);
			Object  mapTemp = param.get(""+shipOrderDetail.getId());
			System.out.println("shipOrderDetail:"+shipOrderDetail.getId());
			if(mapTemp!=null){
				long  numTemp  = shipOrderDetail.getItemQuantity();
				Long quantity  = Long.valueOf(""+mapTemp);
				if(quantity>=numTemp){
					retMap.put("code", "500");
					retMap.put("msg", "数量["+quantity+"]大于或者等于订单数量");
					return retMap;
				}else{
					 if(isSplit){  //计算组合拆单   是否是可以拆分成同样数量的单据
						long index1  = numTemp/quantity;  //除数
						long  indexTemp1 = numTemp%quantity;//余数
						index1  =  indexTemp1>0  ? index1+1:index1; //余数不为0  时
						if(index1!=index){
							retMap.put("code", "500");
							retMap.put("msg", "组合拆单数量异常,不能组合成有效拆单数量");
							return retMap;
						}
					 }
					 index  = numTemp/quantity;  //除数
					 indexTemp = numTemp%quantity;//余数
					 quantityMap.put(""+shipOrderDetail.getId(), indexTemp);
					 index  =  indexTemp>0  ? index+1:index; //余数不为0  时
					 isSplit=  true;
				}
			}
		}
		/**
		 * 拆单保存数据
		 */
		for(int k  = 0; k<index ; k++){
			
			Double weight = 0d;
			
			if(k!=0){ //非第一条数据
				Date now = new Date();
				TmsOrder tempOrder=new TmsOrder();
				tempOrder.generateId();
				tempOrder.setOrder(shipOrder);
				tempOrder.setCreateDate(new Date());
				tempOrder.setStatus(TmsOrder.split_detail);
				List<TmsOrderItem> detailListTemp  = new ArrayList<TmsOrderItem>();
				for(int i = 0 ;i<size ; i++){
					TmsOrderItem objItem=detailList.get(i);
					Object  mapTemp = param.get(""+objItem.getId());
					TmsOrderItem orderItem=new TmsOrderItem();
					orderItem.generateId();
					orderItem.setOrder(shipOrder);
					orderItem.setTmsOrder(tempOrder);

					Long quantity = 0L;
					if (mapTemp!=null) {
						quantity  = Long.valueOf(""+mapTemp);
					}
					
					Item item =  itemDao.getItem(objItem.getItem().getId());
					
					//重量计算
					weight = weight + item.getGrossWeight()*quantity;
					
					orderItem.setItem(item);
					orderItem.setItemQuantity(quantity);
					orderItem.setItemCode(objItem.getItemCode());
					orderItem.setItemId(objItem.getItemId());
					orderItem.setOrderItemId(objItem.getOrderItemId());
					
					/**
					 * mapTemp为空，不添加到明细里面
					 */
					if (mapTemp!=null) {
						detailListTemp.add(orderItem);
					}
					
				}
				/**
				 * 保存信息
				 */
				String items=this.bulidItemsDataByTms(detailListTemp);
				tempOrder.setItems(items);
				tempOrder.setOrderStatus(OrderStatusEnum.WMS_ACCEPT);
				
				/**
				 * 重量更新
				 */
				tempOrder.setPackageWeight(weight);
				
				this.tmsOrderDao.insertTmsOrder(tempOrder);
				this.tmsOrderDao.insertTmsOrderItem(detailListTemp);
				
				//操作日志
				Map<String, Object> pMap = new HashMap<String, Object>();
				Account account = (Account) param.get("account");
				pMap.put("account", account);
				pMap.put("shipOrder", shipOrder);
				pMap.put("model", OperatorModel.TRADE_SPLIT);
				pMap.put("oldValue", shipOrder.getId());
				pMap.put("newValue", tempOrder.getId());
				pMap.put("description", shipOrder.getOrderCode()+"拆单："+tempOrder.getId());
				this.insertShipOrderOperator(pMap);
				
			}else{
				/**
				 * 余数处理
				 */
				Map<String, Object> detailParams = new HashMap<String, Object>();
				for(int i = 0 ;i<size ; i++){
					TmsOrderItem shipOrderDetail = detailList.get(i);
					Object  mapTemp = param.get(""+shipOrderDetail.getId());
					if(mapTemp!=null){
						Long quantity  = Long.valueOf(""+mapTemp);
						indexTemp  =Long.valueOf(""+quantityMap.get(""+shipOrderDetail.getId()));
						// 修改订单明细数据
						shipOrderDetail.setItemQuantity((indexTemp > 0 ? indexTemp : quantity));
						
						//重量计算
						Item item = this.itemDao.getItem(shipOrderDetail.getItem().getId());
						weight = weight + item.getGrossWeight()*shipOrderDetail.getItemQuantity();
						
						this.tmsOrderDao.updateTmsOrderItem(shipOrderDetail);
					}
				}
				/**
				 * 修改主单的明细
				 */
				String items=this.bulidItemsDataByTms(tmsOrder);
				
				/**
				 * 重量更新
				 */
				tmsOrder.setPackageWeight(weight);
				
				tmsOrder.setItems(items);
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
				
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
				
				//操作日志
				Map<String, Object> pMap = new HashMap<String, Object>();
				Account account = (Account) param.get("account");
				pMap.put("account", account);
				pMap.put("shipOrder", shipOrder);
				pMap.put("model", OperatorModel.TRADE_SPLIT);
				pMap.put("oldValue", shipOrder.getId());
				pMap.put("newValue", tmsOrder.getId());
				pMap.put("description", shipOrder.getOrderCode()+"拆单："+tmsOrder.getId()+":"+tmsOrder.getItems());
				this.insertShipOrderOperator(pMap);
			}
		}
		
		retMap.put("code", "200");
		retMap.put("msg", "拆单成功");
		return retMap;
	}

	

	/**
	 * 按行来进行自定义拆单
	 * 拆一行，删除掉一行。然后保存一个新的TmsOrder数据
	 * 
	 * @param param
	 * @throws Exception 
	 */
	public Map<String, Object> splitShipOrderLine(HashMap<String, Object> param) {
		logger.error("自定义拆单按行拆:"+param);
		String tmsOrderId =  (String) param.get("tmsOrderId");
		TmsOrder tmsOrder=this.tmsOrderDao.getTmsOrderById(tmsOrderId);		
		ShipOrder shipOrder = this.shipOrderDao.getShipOrderById(tmsOrder.getOrder().getId());			
		Map<String,Object> retMap  = new HashMap<String,Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmsOrderId", tmsOrderId);
		List<TmsOrderItem> detailList = this.tmsOrderDao.getTmsOrderItemByList(params);
		int size =  detailList.size();
		int k  = 0;
		for (int i = 0; i < size; i++) {
			TmsOrderItem tmsOrderItem  = detailList.get(i);
			Object  mapTemp = param.get(tmsOrderItem.getId());
			if(mapTemp!=null){
				k++;
			}
		}
		if(size==k){
			retMap.put("code", "200");
			retMap.put("msg", "最少需要留一行数据");
			return  retMap;
		}else if(k==0){
			retMap.put("code", "200");
			retMap.put("msg", "请选择拆单行记录");
			return  retMap;
		}
		/**
		 * 拆单数据保存
		 */
		for (int i = 0; i < size; i++) {
			TmsOrderItem objItem = detailList.get(i);  
			Object  mapTemp = param.get(""+objItem.getId());
			if(mapTemp==null){
				continue;
			}
			List<TmsOrderItem> detailListTemp  = new ArrayList<TmsOrderItem>();
			TmsOrder tempOrder=new TmsOrder();
			tempOrder.generateId();
			tempOrder.setOrder(shipOrder);
			tempOrder.setCreateDate(new Date());
			tempOrder.setStatus(TmsOrder.split_detail);
			//辅助单据明细
			TmsOrderItem orderItem=new TmsOrderItem();
			orderItem.generateId();
			orderItem.setOrder(shipOrder);
			orderItem.setTmsOrder(tempOrder);
//			Long quantity  = Long.valueOf(""+mapTemp);
			Item item =  itemDao.getItem(objItem.getItem().getId());
			orderItem.setItem(item);
			orderItem.setItemQuantity(objItem.getItemQuantity());
			orderItem.setItemCode(objItem.getItemCode());
			orderItem.setItemId(objItem.getItemId());
			orderItem.setOrderItemId(objItem.getOrderItemId());
			detailListTemp.add(orderItem);			
			//删除订单明细数据
			tempOrder.setOrderStatus(OrderStatusEnum.WMS_ACCEPT);
			this.tmsOrderDao.deleteTmsOrderItembyId(objItem.getId());
			String items=this.bulidItemsDataByTms(detailListTemp);
			
			/**
			 * 重量更新
			 */
			tempOrder.setPackageWeight(item.getGrossWeight()*orderItem.getItemQuantity());
			tempOrder.setItems(items);
			
			this.tmsOrderDao.insertTmsOrder(tempOrder);
			this.tmsOrderDao.insertTmsOrderItem(detailListTemp);
			
			//操作日志
			Map<String, Object> pMap = new HashMap<String, Object>();
			Account account = (Account) param.get("account");
			pMap.put("account", account);
			pMap.put("shipOrder", shipOrder);
			pMap.put("model", OperatorModel.TRADE_SPLIT);
			pMap.put("oldValue", shipOrder.getId());
			pMap.put("newValue", tempOrder.getId());
			pMap.put("description", shipOrder.getOrderCode()+"拆单："+tempOrder.getId()+":"+tempOrder.getItems());
			this.insertShipOrderOperator(pMap);
			
		}		
		String items=this.bulidItemsDataByTms(tmsOrder);
		tmsOrder.setItems(items);
		
		//重量计算
		tmsOrder.setPackageWeight(this.buildTmsWeight(tmsOrder));
		
		this.tmsOrderDao.updateTmsOrder(tmsOrder);
		
		//操作日志
		Map<String, Object> pMap = new HashMap<String, Object>();
		Account account = (Account) param.get("account");
		pMap.put("account", account);
		pMap.put("shipOrder", shipOrder);
		pMap.put("model", OperatorModel.TRADE_SPLIT);
		pMap.put("oldValue", shipOrder.getId());
		pMap.put("newValue", tmsOrder.getId());
		pMap.put("description", shipOrder.getOrderCode()+"拆单："+tmsOrder.getId()+":"+tmsOrder.getItems());
		this.insertShipOrderOperator(pMap);
		
		retMap.put("code", "200");
		retMap.put("msg", "行拆单成功");
		return retMap;
	}
	
	/**
	 * TmsOrder重量计算
	 * @param tmsOrder
	 * @return
	 */
	private double buildTmsWeight(TmsOrder tmsOrder) {
		
		Double weight = 0d;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmsOrderId",tmsOrder.getId());
		List<TmsOrderItem> orderItems = this.tmsOrderDao.getTmsOrderItemByList(params);	
		
		for(TmsOrderItem orderItem:orderItems){
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			weight = weight + item.getGrossWeight()*orderItem.getItemQuantity();
		}
		
		return weight;
	}

	/**
	 * 写操作日志
	 */
	private void insertShipOrderOperator(Map<String, Object> map){
		
		ShipOrderOperator obj=new ShipOrderOperator();		
		obj.generateId();		
		Account account = (Account) map.get("account");
		obj.setAccount(account);		
		obj.setDescription(""+map.get("description"));		
		obj.setNewValue(""+map.get("newValue"));		
		obj.setOldValue(""+map.get("oldValue"));		
		obj.setOperatorDate(new Date());		
		OperatorModel model = (OperatorModel) map.get("model");
		obj.setOperatorModel(model);		
		ShipOrder shipOrder = (ShipOrder) map.get("shipOrder");
		obj.setShipOrder(shipOrder);		
		logger.debug("operator:"+obj.getId());		
		this.operatorDao.saveShipOrderOperator(obj);
	}

	private void insertShipOrderStop(ShipOrder order,String description){
		ShipOrderStop stop=new ShipOrderStop();
		stop.generateId();
		stop.setCreateTime(new Date());
		stop.setOrderId(order.getId());
		stop.setDescription(description);
		stop.setExpressOrderno(order.getTmsOrderCode());
		stop.setUserId(order.getUser().getId());
		this.stopDao.saveShipOrderStop(stop);
	}

	/**
	 * ERP单据导入
	 * @param params
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	
	public synchronized List<Map<String, Object>> importERPOrders(Map<String, Object> params) {
		//返回信息  集合
		List<Map<String,Object>> retMapList= null;
		
		if(params!=null){
			System.err.println("ERP导入开始！！！！！");
			System.out.println("ERP导入！！！！");
			
			retMapList   =  new  ArrayList<Map<String,Object>>();
			
			// 用户信息
			String userId = String.valueOf(""+params.get("userId"));
			User   user  = this.userDao.getUserById(userId);
			//仓库信息
//			Centro centro  = new Centro(); 
//			Long  centroId =  Long.valueOf(""+params.get("centroId")); 
//			centro.setId(centroId);
			
			//erp 信息
			String  erp =  ""+ params.get("erp"); 
			//获得快系统快递 信息
			Map<String, Object> sysParam = new HashMap<String, Object>();
			sysParam.put("type", StoreSystemItemEnums.LOGISTICS.getKey());
			List<SystemItem> sysItemList = systemItemDao.findSystemItemByList(params);
			
			//单条 数据信息操作
			List<Map<String,Object>> orderListMap  = (List<Map<String,Object>>) params.get("orderList");
			int size =  orderListMap.size();
			Date now  = new  Date();  //时间初始化
			for(int i= 0; i<size;i++){
				Map<String,Object> map  = orderListMap.get(i);
				Map<String,Object> retMap = new HashMap<String,Object>();
			   
			    String[] itemCode =(String[])map.get("itemCode");//商品code
			    String[] itemCount =(String[])map.get("itemCount");//商品数量
			    String tbNumber =""+  map.get("tbNumber");//淘宝订单号
			    String expressOrderno =(""+  map.get("expressOrderno")).trim();//菜鸟面单号
			    String state  =""+  map.get("state");//省份
			    String city  =""+  map.get("city");//市区
			    String district  =""+  map.get("district");//区域
			    String address  =""+  map.get("address");//详细地址 
			    String  userName = ""+map.get("userName");  //收货人名
			    String  userPhone =  ""+ map.get("userPhone"); //收货人手机号码
			    String  express =  ""+ map.get("express"); //快递公司名称
			    String  buyerName =  ""+ map.get("buyerName"); //买家昵称
			    Object object = map.get("barCode");
			    String[]  barCode  =null;
			    if(object!=null){ //商品条码
			    	barCode=  (String[]) map.get("barCode");
			    }
			    String  items =  ""+ map.get("items"); //本地商品 基本信息
			    
			    //导入记录初始化
//				ImportRecord importRecord  = new ImportRecord();
//				importRecord.setCentroId(centroId);
//				importRecord.setUserId(userId);
//				importRecord.setErp(erp);
//				importRecord.setCreateDate(now);
//				importRecord.setExpressOrderno(expressOrderno);
//				importRecord.setCompany(express);
//				importRecord.setStatus(ImportRecord.ImportRecordStatus.FAIL);
			   //检测电子面单是否存在
			    Map<String,Object> itemParams = new HashMap<String,Object>();
				itemParams.put("tmsOrderCode", expressOrderno);
				int orderCount = shipOrderDao.getShipOrderByList(itemParams).size();
				String msg  =  "";
				if(orderCount>0){   //电子面单存在不 保存记录
					msg  = (i+1)+"行 电子面单号："+expressOrderno+"已存在";
//					importRecord.setMsg(msg);
//					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				
			    //匹配商品信息    code=#{code} and userid=#{userId} 
			    itemParams.clear();
			    List<Item> itemList =  new ArrayList<Item>();
			    List<String> itemListCount =  new ArrayList<String>();
//			    Item item = null;
			    String itemCodeStr   =  "";
			    String barCodeStr   =  "";
			    if(userId.equals("30")){  //德尔玛 特殊处理  
			    	
			    	for(int j  = 0  ; j<barCode.length;j++){
			    		String barCodeStrTemp  =  barCode[j];
			    		barCodeStr =  barCodeStr+barCodeStrTemp+";";
			    		System.out.println("导入德尔玛商品条码："+barCodeStrTemp);
			    		itemParams.clear();
			    		itemParams.put("userId", userId);
			    		itemParams.put("barCode", barCodeStrTemp.trim());
			    		Item item  = itemDao.getItemByList(itemParams).get(0);
			    		if(item!=null){
			    			itemList.add(item);
			    			itemListCount.add(itemCount[j]);
			    		}
			    	}
			    }else{
			    	for(int j  = 0  ; j<itemCode.length;j++){
			    		String  itemCodeStrTemp  = itemCode[j];
			    		System.out.println("导入商品名称："+itemCodeStrTemp);
			    		itemParams.clear();
			    		itemCodeStr =  itemCodeStr+itemCodeStrTemp+";";
			    		itemParams.put("userId", userId);
			    		itemParams.put("name", itemCodeStrTemp.trim());
			    		Item item  =  itemDao.getItemByList(itemParams).get(0);
			    		if(item!=null){
			    			itemList.add(item);
			    			itemListCount.add(itemCount[j]);
			    		}
			    	}
				}
				if (userId!="30") {  //添加提示语言
					msg = (i + 1) + "行;" + itemCodeStr;
//					importRecord.setItemCode(itemCodeStr);
				} else {
					msg = (i + 1) + "行 商品条码：" + barCodeStr;
//					importRecord.setItemCode(barCodeStr);
				}

				if (itemList.size() == 0) { // 商品信息无法匹配
					 if(userId!="30"){
						 msg =  (i+1)+"行 商品名称："+itemCodeStr+"无法匹配";
//						 importRecord.setItemCode(itemCodeStr);
					 }else{
						 msg =  (i+1)+"行 商品条码："+barCodeStr+"无法匹配";
//						 importRecord.setItemCode(barCodeStr);
					 }
//					importRecord.setMsg(msg);
//					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				//匹配快递信息
				SystemItem systemItem =  null;
				for(int j  = 0,jsize = sysItemList.size();j<jsize;j++){
					SystemItem sysItem = sysItemList.get(j);
					if(express.indexOf(sysItem.getDescription())>-1){
						systemItem  =  sysItem;
						break;
					}
				}
				if(systemItem==null){ //快递信息 无法匹配
					msg =(i+1)+"行 订单快递信息："+express+"无法匹配" ;
//					importRecord.setMsg(msg);
//					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				
				ShipOrder  order1 = shipOrderDao.getShipOrderById("2df79b82-b468-4c03-bcfe-b1ec364b8129");// 正式环境
				Map<String, Object> mapParams = new HashMap<String, Object>();
				mapParams.put("orderId", order1.getId());
				List<WmsConsignOrderItem> orderItems = this.orderItemDao.getWmsConsignOrderItemByList(mapParams);
				order1.setOrderItemList(orderItems);
				System.err.println(order1.getId());
//				ShipOrder  order1 = shipOrderDao.getShipOrder(135L);  //测试
				System.out.println("order1:"+order1.getOrderItemList().size());
				ShipOrder order = null;
				ReceiverInfo receiver = null;
				SenderInfo sender = null;
				try {
					order = (ShipOrder) order1.clone();
					ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(order.getReceiverInfo().getId());
					receiver = (ReceiverInfo) receiverInfo.clone();
					SenderInfo senderInfo = this.senderInfoDao.getSenderInfoById(order.getSenderInfo().getId());
					sender = (SenderInfo) senderInfo.clone();
					List<WmsConsignOrderItem> list  = new ArrayList<WmsConsignOrderItem>();
					for (int j = 0, jsize = itemList.size(); j < jsize; j++) {
						WmsConsignOrderItem detail = (WmsConsignOrderItem) order1.getOrderItemList().get(0).clone();
						Item item = itemList.get(j);
						System.out.println("item:"+item.getId());
						String itemCountStr = itemListCount.get(j);
						detail.setId(null);
						detail.generateId();
						detail.setItem(item); // 商品信息
						detail.setItemCode(item.getItemCode());
						detail.setItemName(item.getName());
						detail.setItemVersion(Integer.valueOf(String.valueOf(item.getItemVersion())));
						detail.setOrderItemId(item.getItemId());
						if (itemCount != null) {
							detail.setItemQuantity(Long.valueOf(itemCountStr)); // 商品数量
 							detail.setItemOutQuantity(Long.valueOf(itemCountStr)); // 商品实际数量
						}
						detail.setOrderSourceCode(tbNumber);
						list.add(detail);
						detail.setOrder(order);
					}	System.out.println("list:" + list.size());
					order.setOrderItemList(list);
				} catch (CloneNotSupportedException e) {
					msg = (i+1)+"行 商品"+itemCodeStr+"数据出现系统异常,请联系管理员..";
//					importRecord.setMsg(msg);
//					importRecordDao.save(importRecord);
					retMap.put("msg", msg);
					retMapList.add(retMap);
					continue;
				}
				order.setId(null);
				order.generateId();
//				order.setTradeId(null);
//				order.setTradeBatchId(null);
				order.setUser(user);
				order.setShopName(user.getSubscriberName());
				order.setTmsServiceName(systemItem.getDescription());//快递信息
				order.setTmsServiceCode(systemItem.getValue());//快递信息
				order.setTmsServiceName(systemItem.getValue());//快递信息
				order.setTmsOrderCode(expressOrderno);  // 快递面单号
				order.setErpOrderCode(tbNumber);  //淘宝订单号
				order.setSystemSource(SystemSourceEnums.IMPORT);  //设置商品来源
				order.setOrderType(201);
				order.setStatus(OrderStatusEnum.WMS_FINASH);
				order.setCreateTime(now);
				order.setOrderExaminationTime(now);
				order.setOrderPayTime(now);
				
				receiver.setId(null);
				receiver.generateId();
				receiver.setReceiverName(userName);
				receiver.setReceiverMobile(userPhone);
				receiver.setReceiverPhone(userPhone);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
				receiver.setReceiverProvince(state);
				receiver.setReceiverCity(city);
				receiver.setReceiverArea(district);
				receiver.setReceiverAddress(address);
				receiver.setReceiverNick(buyerName);
				order.setReceiverInfo(receiver);
				
				sender.setId(null);
				sender.generateId();
				order.setSenderInfo(sender);
				
		        order.setExtendFields(""+map.get("items"));  
		        this.insertShipOrder(order);
//		        for (int j = 0; order.getOrderItemList() != null && j < order.getOrderItemList().size(); j++) {
//					this.entryOrderDetailJpaDao.save(order.getDetails().get(j));
//				}
//				TradeUtil  util  = new TradeUtil();
//				Trade adapter  = null;
				try {
//					adapter = util.adapter(order);
				} catch (Exception e) {
					logger.info(e.getMessage());
					retMap.put("msg", (i+1)+"行 ApiException 单据转换失败");
					retMapList.add(retMap);
				}
				//update sc_ship_order set trade_id=#{trade_id} where id=#{id}
//				Map<String,Object> updaeParams   = new HashMap<String, Object>();
//				Trade createTrade = tradeService.createTradeERP(adapter, order.getId(),tbNumber);
//				updaeParams.put("trade_id", createTrade.getId());
//				updaeParams.put("id",  order.getId());
//				shipOrderDao.updateShipOrderTradeId(updaeParams);
				retMap.put("msg", (i+1)+"行 成功");
				retMapList.add(retMap);
			}
		}
		return retMapList;
	}

	/**
	 * 指定单据导入
	 * @param params
	 * @return
	 */
	
	public List<Map<String,Object>> importOrders(List<Map<String, Object>> params) {
//		type='deliver' and a.status='ENTRY_WAIT_STORAGE_RECEIVED'
		if(params!=null   &&  params.size()>0){
			System.out.println("导入");	
			for(Map<String,Object> map  :  params){
				ShipOrder  order1 = shipOrderDao.getShipOrderById("6c73d94e-1d20-44b8-98a6-19ea73bb83b1");
				System.out.println("order1:"+order1.getOrderItemList().size());
				ShipOrder order = null;
				try {
					order = (ShipOrder) order1.clone();
					WmsConsignOrderItem detail = (WmsConsignOrderItem)order1.getOrderItemList().get(0).clone();
					List<WmsConsignOrderItem> list  = new ArrayList<WmsConsignOrderItem>();
					detail.setId(null);
					list.add(detail);
					detail.setOrder(order);
					order.setOrderItemList(list);
					
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				User  user = new User();
				user.setId(String.valueOf(""+map.get("userId")) );		
				order.generateId();
				order.setOrderFlag(null);
				order.setTmsServiceName(""+map.get("expressCompany"));				
				String orderNo  = ""+map.get("code");//去掉 淘宝订单号第一个引号
				order.setErpOrderCode(orderNo);
				order.setUser(user) ; //设置 商家
				order.setTmsOrderCode(null);
				order.setSystemSource(SystemSourceEnums.IMPORT);
				order.setStatus(OrderStatusEnum.WMS_ACCEPT);
				order.setCreateTime(new Date());
				order.setOrderExaminationTime(new Date());
				order.setOrderPayTime(new  Date());
				
				ReceiverInfo receiverInfo1 = this.receiverInfoDao.getReceiverInfoById("");
				ReceiverInfo receiverInfo = null;
				try {
					receiverInfo = (ReceiverInfo) receiverInfo1.clone();
					order.setReceiverInfo(receiverInfo);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				receiverInfo.generateId();
				receiverInfo.setReceiverName((String) map.get("userName"));
				receiverInfo.setReceiverMobile((String) map.get("userPhone"));
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String address = "" + map.get("address");
				System.out.println("orderNo:"+orderNo+";address:"+address);		
				receiverInfo.setReceiverProvince("" + map.get("state"));
				receiverInfo.setReceiverCity("" + map.get("city"));
				receiverInfo.setReceiverArea("" + map.get("district"));
				receiverInfo.setReceiverAddress("" + map.get("address"));
				
				SenderInfo senderInfo1 = this.senderInfoDao.getSenderInfoById("");
				SenderInfo senderInfo = null;
				try {
					senderInfo = (SenderInfo) senderInfo1.clone();
					senderInfo.generateId();
					order.setSenderInfo(senderInfo);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				WmsConsignOrderItem orderItem1 = this.orderItemDao.getWmsConsignOrderItemById("");
				WmsConsignOrderItem orderItem = null;
				try {
					orderItem = (WmsConsignOrderItem) orderItem1.clone();
					orderItem.generateId();
					orderItem.setOrder(order);
					List<WmsConsignOrderItem> orderItems = new ArrayList<WmsConsignOrderItem>();
					orderItems.add(orderItem);
					order.setOrderItemList(orderItems);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}		
							
		        order.setExtendFields(""+map.get("items"));
		        this.insertShipOrder(order);
			}
		}
		return null;
	}

	/**
	 * 审核订单，更新订单状态和物流公司
	 * @param shipOrder
	 * @param systemItem
	 * @param account
	 * */
	
	@Transactional
	public void auditOneTrade(String id, String expressCompany, Account account) {
		logger.debug("account:"+account.getUserName());
		TmsOrder tmsOrder=this.tmsOrderDao.getTmsOrderById(id);
		ShipOrder shipOrder = this.shipOrderDao.getShipOrderById(tmsOrder.getOrder().getId());
		String oldValue = shipOrder.getTmsServiceCode();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("value", expressCompany);
		SystemItem systemItem = systemItemDao.findSystemItemByList(params).get(0);
		shipOrder.setTmsServiceCode(systemItem.getValue());
		shipOrder.setTmsServiceName(systemItem.getDescription());
 		shipOrder.setStatus(OrderStatusEnum.WMS_AUDIT);
 		/**
 		 * 判断当前单据是否有拆单。
 		 */
 		Map<String,Object> param=new HashMap<String, Object>();
 		param.put("orderId", tmsOrder.getOrder().getId());
// 		param.put("orderStatus", OrderStatusEnum.WMS_ACCEPT);
 		List<TmsOrder> orderList=this.tmsOrderDao.getTmsOrderByList(param);
 		if(orderList!=null && orderList.size()==1){
 			/**
 			 * 如果没有拆单，则进行组合套餐判断，并直接更新审单状态
 			 */
	 		this.buildShipOrderItemGroup(shipOrder);
	 		tmsOrder.setItems(shipOrder.getItems());
	 		if(expressCompany!=null && expressCompany.equals("YUNDAX")){
				shipOrder.setTmsDisplayName("411353"); 			
			}else if(expressCompany!=null && expressCompany.equals("YUNDA")){
				shipOrder.setTmsDisplayName("411106");
			}else if(expressCompany!=null && expressCompany.equals("HTKY11")){
				shipOrder.setTmsDisplayName("HTKY11");
			}else if(expressCompany!=null && expressCompany.equals("HTKY5")){
				shipOrder.setTmsDisplayName("HTKY5");
			} 		
	 		this.updateShipOrder(shipOrder);	
//	 		tmsOrder.setOrderStatus(OrderStatusEnum.WMS_AUDIT);
	 		tmsOrder.setDisplayCode(shipOrder.getTmsDisplayName());	 		
 		}else{
 			/**
 			 * 单据如果有进行拆单，则判断所拆分的单据是不是全审完毕。
 			 */
 			param.put("orderStatus", OrderStatusEnum.WMS_ACCEPT.getKey());
 			orderList=this.tmsOrderDao.getTmsOrderByList(param);
 			if(orderList!=null && orderList.size()==1){
 				/**
 				 * 表明是审这个单据的最后一个子单。
 				 */
 				if(expressCompany!=null && expressCompany.equals("YUNDAX")){
 					shipOrder.setTmsDisplayName("411353"); 			
 				}else if(expressCompany!=null && expressCompany.equals("YUNDA")){
 					shipOrder.setTmsDisplayName("411106");
 				}else if(expressCompany!=null && expressCompany.equals("HTKY11")){
 					shipOrder.setTmsDisplayName("HTKY11");
 				}else if(expressCompany!=null && expressCompany.equals("HTKY5")){
 					shipOrder.setTmsDisplayName("HTKY5");
 				} 		
 		 		this.updateShipOrder(shipOrder);	
 		 		
 			}
 			tmsOrder.setDisplayCode(shipOrder.getTmsDisplayName());			
 		}
 		
 		/**
 		 * tmsOrder没有赋值code
 		 */
 		tmsOrder.setCode(shipOrder.getTmsServiceCode());
 		
 		tmsOrder.setOrderStatus(OrderStatusEnum.WMS_AUDIT);
 		this.tmsOrderDao.updateTmsOrder(tmsOrder);
 		String newValue = systemItem.getValue();
 		String description = shipOrder.getOrderCode()+"订单批量审核！";
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("shipOrder", shipOrder);
 		map.put("description", description);
 		map.put("newValue", newValue);
 		map.put("oldValue", oldValue);
 		map.put("model", OperatorModel.TRADE_AUDIT);
 		map.put("account", account);
		this.insertShipOrderOperator(map);
	}

	/**
	 * 判断是否有组合商品，如果有则重构
	 * 
	 * @param order
	 */
	private void buildShipOrderItemGroup(ShipOrder order) {
		StringBuffer buffer = new StringBuffer();
		Date date=new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getId());
		List<WmsConsignOrderItem> itemList = this.orderItemDao.getWmsConsignOrderItemByList(params);
		order.setOrderItemList(itemList);
		Map<String, Object> itemMap = new HashMap<String, Object>();
		buildItemListToMap(itemMap, itemList);
		Map<String, Object> objMap=null;

		List<ShipOrderGroup> orderList=new ArrayList<ShipOrderGroup>();
		List<ItemGroup> groupList = this.itemGroupService.getGroupByShip(order);
		if (groupList != null && groupList.size() > 0) {
			for (int i = 0; groupList != null && i < groupList.size(); i++) {
				objMap = new HashMap<String, Object>();
				try {
					BeanUtils.copyProperties(objMap, itemMap);
					objMap.putAll(itemMap);
				} catch (Exception e) {
					e.printStackTrace();
				}

				ItemGroup group = groupList.get(i);
				List<ItemGroupDetail> detailList = group.getDetai();
				int index = 0;
				for (int j = 0; detailList != null && j < detailList.size(); j++) {
					ItemGroupDetail detail = detailList.get(j);
					/**
					 * 是否包含商品
					 */
					int include=0;
					if (objMap.get(detail.getItem().getId()) != null) {
						include=1;
						// 匹配数量
						Long quantity = (Long) objMap.get(detail.getItem().getId());
						if (detail.getNum() <= quantity) {
							Long num = (Long) objMap.get(detail.getItem().getId());
							num = num - detail.getNum();
							/**
							 * 如果此商品与套餐计算之后为0，则删除掉此商品
							 */
							if (num == 0) {
								objMap.remove(detail.getItem().getId());
							}else{
								objMap.put(detail.getItem().getId(), num);
							}
							index++;
						}else{
							include=0;
						}
						
					}

					/**
					 * 判断是否列表的最后一个商品，如果是最后一个商品，则计算是否能进行再一次比对，如果可以则计时器还原重新开始，
					 * 如果不行则退出。 这里有一种情况可能会做多余的计算: 经过判断后套餐外的商品比套餐商品数量要多的话则会再计算机一次
					 */
					if (j == detailList.size()-1 ) {
						int k = objMap.values().size();
						if (k >= detailList.size() && include==1) {
							j = -1;
							include=0;
							continue;
						}
					}
				}

				/**
				 * 一个套餐明细匹配完成，判断是否完成匹配. 如果相等则表明这轮套餐每个商品都匹配上，计算剩余商品的文本
				 */
				if (index>0 && index % detailList.size() == 0) {
					
					buffer.append(group.getName())
						  .append(index/detailList.size())
						  .append("[套] ");
					/**
					 * 修改订单的套餐标记
					 */
					order.setItemGroupStatus(ShipOrder.item_Group_Status);
					
					ShipOrderGroup orderGroup=new ShipOrderGroup();
					orderGroup.generateId();
					orderGroup.setCreateDate(new Date());
					orderGroup.setItemGroup(group);
					orderGroup.setOrder(order);
					orderGroup.setQuantity(index/detailList.size());
					orderList.add(orderGroup);
				/*	int k = objMap.values().size();
					if (k >= detailList.size()) {
						continue;
					}*/
					
					/**
					 * 剩余商品匹配
					 */
					for (Map.Entry<String, Object> entry : objMap.entrySet()) { 
						String itemId=entry.getKey();
						Long num=(Long) entry.getValue();
						Item item=this.itemDao.getItem(itemId);
						buffer.append(item.getName())
							  .append("[")
							  .append(num).append("件] ");
					}
					order.setItems(buffer.toString());
					logger.debug("套餐匹配时间长为:"+(new Date().getTime()-date.getTime()));
					break;
				}
				
				/**
				 * 退出匹配,
				 * 暂时如果匹配到了一个套餐后就不再对后续其它返回的套餐进行匹配
				 */
				
			}
		}
	
		if(StringUtils.isNotEmpty(order.getItemGroupStatus()) && order.getItemGroupStatus().equals(ShipOrder.item_Group_Status)){
//			this.shipOrderDao.updateShipOrder(order);
			this.groupDao.insertShipOrderGroupList(orderList);
		}
		
	}

	private void buildItemListToMap(Map<String, Object> itemMap, List<WmsConsignOrderItem> itemList) {
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			WmsConsignOrderItem item = itemList.get(i);
			itemMap.put(item.getItem().getId(), item.getItemQuantity());
		}
	}

	/**
	 * 订单反审
	 * @param request
	 * @param account
	 * @return map
	 * */
	
	public void resetShipOrder(ShipOrder shipOrder,Account account) {
		
		/**
		 * 订单反审，更新TmsOrder
		 */
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("orderId", shipOrder.getId());
		List<TmsOrder> tmsOrders = this.tmsOrderDao.getTmsOrderByList(p);
		if (tmsOrders.isEmpty()) {
			/**
			 * tmsOrder不存在,不作处理
			 */
		}else {
			for(TmsOrder tmsOrder:tmsOrders){
				tmsOrder.setCode(null);;
				tmsOrder.setOrderCode(null);
				tmsOrder.setOrderStatus(OrderStatusEnum.WMS_ACCEPT);
				this.tmsOrderDao.updateTmsOrder(tmsOrder);
			}
		}
		String oldValue = shipOrder.getStatus().getKey();
		shipOrder.setStatus(OrderStatusEnum.WMS_ACCEPT);
		shipOrder.setTmsServiceCode(null);
		shipOrder.setTmsServiceName(null);
		shipOrder.setTmsOrderCode(null);
		String otherOrderNo = shipOrder.getOtherOrderNo()+"-1";
		shipOrder.setOtherOrderNo(otherOrderNo);
		this.shipOrderDao.updateShipOrder(shipOrder);
		String newValue = OrderStatusEnum.WMS_ACCEPT.getKey();
		String description = shipOrder.getOrderCode()+"订单反审！"+shipOrder.getTmsOrderCode();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shipOrder", shipOrder);
		map.put("description", description);
		map.put("newValue", newValue);
		map.put("oldValue", oldValue);
		map.put("model", OperatorModel.TRADE_CANCEL);
		map.put("account", account);
		this.insertShipOrderOperator(map);
	}

	/**
	 * 创建TmsOrder对象
	 * @param shipOrder
	 */
	private void createTmsOrder(ShipOrder shipOrder) {
		TmsOrder tmsOrder=new TmsOrder();
		tmsOrder.generateId();
		List<TmsOrderItem> tmsOrderItem=new ArrayList<TmsOrderItem>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", shipOrder.getId());
		List<WmsConsignOrderItem> orderItems = this.orderItemDao.getWmsConsignOrderItemByList(params);
		for(WmsConsignOrderItem orderItem:orderItems){
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			TmsOrderItem tmsItem=new TmsOrderItem();
			tmsItem.generateId();
			tmsItem.setItemCode(item.getItemCode());
			tmsItem.setItemId(item.getItemId());
			tmsItem.setItemQuantity(orderItem.getItemQuantity());
			tmsItem.setOrderItemId(orderItem.getOrderItemId());
			tmsItem.setTmsOrder(tmsOrder);
			tmsItem.setItem(item);
			tmsItem.setOrder(shipOrder);
			tmsOrderItem.add(tmsItem);
		}
		tmsOrder.setOrder(shipOrder);
		tmsOrder.setCreateDate(new Date());
		tmsOrder.setStatus(TmsOrder.split);
		tmsOrder.setItems(shipOrder.getItems());
		tmsOrder.setOrderStatus(OrderStatusEnum.WMS_ACCEPT);
		this.tmsOrderDao.insertTmsOrder(tmsOrder);
		this.tmsOrderDao.insertTmsOrderItem(tmsOrderItem);
	}

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
	
	public void setSendOrderExpressAndStatusById(List<Map<String, String>> orderMaps) {
		if (CollectionUtils.isNotEmpty(orderMaps)) {
			for (Map<String, String> map : orderMaps) {
				Assert.notNull(map.get("expressCompany"), "物流公司不能为空");
				Assert.notNull(map.get("expressOrderno"), "运单号不能为空");
				Assert.notNull(map.get("status"), "状态不能为空");
			//	Assert.notNull(map.get("orderFlag"), "大头标记不能为空");
				
			//	Assert.notNull(map.get("sellerMobile"), "收货网点名称");
			//	Assert.notNull(map.get("sellerPhone"),  "收货网点CODE");
				String id = map.get("id");
				ShipOrder shipOrder = this.shipOrderDao.getShipOrderById(id);
				shipOrder.setTmsServiceCode(map.get("expressCompany"));
				shipOrder.setTmsOrderCode(map.get("expressOrderno"));
				shipOrder.setOrderFlag(map.get("orderFlag"));
				shipOrder.setTemplateURL(map.get("templateURL"));
				shipOrder.setSortationName(map.get("sortationName"));
				shipOrder.setTmsRouteCode(map.get("routeCode"));
				shipOrder.setConsolidationName(map.get("consolidationName"));
				shipOrder.setConsolidationCode(map.get("consolidationCode"));
				shipOrder.setBatchCode(map.get("batchCode"));
				shipOrder.setLastUpdateTime(new Date());
				for(OrderStatusEnum status:OrderStatusEnum.values()){
					if (map.get("status").equals(status.getKey())) {
						shipOrder.setStatus(status);
					}
				}
				/**
				 * 菜鸟订单package处理
				 */
				String tmsOrderId=map.get("tmsId");
				Map<String,Object> p=new HashMap<String, Object>();
				p.put("orderId", id);
				p.put("orderStatus", OrderStatusEnum.WMS_ACCEPT);
				List<TmsOrder> tmsList=this.tmsOrderDao.getTmsOrderByList(p);
				
				if(StringUtils.isNotEmpty(tmsOrderId)){
					TmsOrder tmsOrder=this.tmsOrderDao.getTmsOrderById(tmsOrderId);
					tmsOrder.setOrderCode(shipOrder.getTmsOrderCode());
					tmsOrder.setCode(shipOrder.getTmsServiceCode());
					tmsOrder.setOrderFlag(shipOrder.getOrderFlag());
					tmsOrder.setTemplateURL(shipOrder.getTemplateURL());
					tmsOrder.setSortationName(shipOrder.getSortationName());
					tmsOrder.setRouteCode(shipOrder.getTmsRouteCode());
					tmsOrder.setConsolidationCode(shipOrder.getConsolidationCode());
					tmsOrder.setConsolidationName(shipOrder.getConsolidationName());
					tmsOrder.setBatchCode(shipOrder.getBatchCode());
					String weight=map.get("weight");
					if(StringUtils.isNotBlank(weight)){
						tmsOrder.setPackageWeight(Double.valueOf(weight));
					}
					tmsOrder.setOrderStatus(OrderStatusEnum.WMS_GETNO);
					this.tmsOrderDao.updateTmsOrder(tmsOrder);
				}
				/**
				 * 如果未取快递单号的包裹大于1，则暂时不修改主单据的状态.
				 * 也就是说最后一个包裹的时候才修改主单据状态
				 */
				if(tmsList!=null && tmsList.size()>1){
					shipOrder.setStatus(OrderStatusEnum.WMS_AUDIT);
				}
			  this.shipOrderDao.updateShipOrder(shipOrder);
			}
		}
	}

	
	public void updateShipOrderStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.shipOrderDao.updateShipOrderStatus(params);
	}

	
	public int isHaveOrder(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderDao.isHaveOrder(params);
	}

	
	public void updateExpressOrderNo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.shipOrderDao.updateExpressOrderNo(params);
	}

	
	public List<Map<String,Object>> getShipOrderItemByWorkGroup(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.shipOrderDao.getShipOrderItemByWorkGroup(params);
	}
	
	
	public ShipOrder getShipOrderByParams(Map<String, Object> params) {
		return this.shipOrderDao.getShipOrderByParams(params);
	}

	
	public int getOldTotal(Map<String, Object> params) {
		return this.shipOrderDao.getOldShipOrderTotal(params);
	}

	
	public List<ShipOrder> findOldShipOrderListByPage(Map<String, Object> params, int pageNum, int pageSize) {
		return this.shipOrderDao.findOLdShipOrderListByPage(params, pageNum, pageSize);
	}

	
	public List<ShipOrder> findTmsNotExsist(Map<String, Object> params) {
		return this.shipOrderDao.findTmsNotExsist(params);
	}

	public List<Map<String, Object>> bulidOldListData(List<ShipOrder> shipOrders) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(ShipOrder shipOrder:shipOrders){
			Map<String, Object> map = new HashMap<String, Object>();
			ShipOrder order = shipOrder.getOrder();
			map.put("createDate", sf.format(order.getCreateTime()));
			User user = this.userDao.getUserById(order.getUser().getId());
			map.put("userName", user.getSubscriberName());
			map.put("items", order.getItems());
			ReceiverInfo receiverInfo = shipOrder.getReceiverInfo();
			map.put("nick", receiverInfo.getReceiverNick());
			map.put("name", receiverInfo.getReceiverName());
			map.put("phone", receiverInfo.getReceiverMobile()+","+receiverInfo.getReceiverPhone());
			map.put("address", receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity()+receiverInfo.getReceiverArea()+receiverInfo.getReceiverAddress());
			map.put("expressCode", order.getTmsServiceCode());
			map.put("orderNo", order.getTmsOrderCode());
			map.put("buyerMemo", order.getBuyerMessage()); 
			map.put("sellerMemo", order.getSellerMessage()); 
			map.put("remark", order.getRemark()); 
			map.put("orderCode", order.getErpOrderCode()); 
			results.add(map);
		}
		return results;
	}
	
	public List<Map<String, Object>> findStoreOrderList(Map<String, Object> params) {
		return this.shipOrderDao.findStoreOrderList(params);
	}

	public List<Map<String, Object>> getShopNameByUserId(String userId) {
		// TODO Auto-generated method stub
//		return this.shipOrderDao.getShopNameByUserId(userId);
		return null;
	}

}
