package com.xinyu.service.caoniao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.pac.sdk.cp.ReceiveListener;
import com.taobao.pac.sdk.cp.ReceiveSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.WmsStockOutOrderNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_STOCK_OUT_ORDER_NOTIFY.WmsStockOutOrderNotifyResponse;
import com.xinyu.dao.base.BatchSendCtrlParamDao;
import com.xinyu.dao.base.DriverInfoDao;
import com.xinyu.dao.base.SenderInfoDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.dao.order.StockOutOrderDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.model.base.BatchSendCtrlParam;
import com.xinyu.model.base.DriverInfo;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.VehicleTypeEnum;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.model.order.enums.OutOrderTypeEnum;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.util.MyException;
import com.xinyu.service.common.Constants;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;


/**
 * 出库通知接口，具体的实现类，
 * @author shark_cj
 * @since 2017-04-27
 */
@Component
public class WmsStockOutOrderNotifyCpImpl  implements ReceiveListener<WmsStockOutOrderNotifyRequest, WmsStockOutOrderNotifyResponse>{

	public static final Logger logger = Logger.getLogger(WmsStockOutOrderNotifyCpImpl.class);
	
	@Autowired
	private  UserService userService;
	
	@Autowired
	private ItemService  itemService;
	
	@Autowired  
	private  BatchSendCtrlParamDao  batchSendCtrlParamDao;
	
	@Autowired
	private  DriverInfoDao  driverInfoDao;
	
	@Autowired
	private  ReceiverInfoDao  receiverInfoDao;
	
	@Autowired
	private  SenderInfoDao senderInfoDao;
	
	@Autowired
	private StockOutOrderDao   stockOutOrderDao;
	
	@Autowired
	private StockOrderOperatorDao   operatorDao;
	
	@Autowired
	private AccountService accountService;
	
	
	public  WmsStockOutOrderNotifyResponse execute(ReceiveSysParams params, WmsStockOutOrderNotifyRequest request) {
		logger.debug("StockOutOrderNotify:Begin");
		StockOutOrder stockOutOrder  = new StockOutOrder();
        // 业务处理逻辑
    	WmsStockOutOrderNotifyResponse response = new WmsStockOutOrderNotifyResponse();
    	
    	String orderCode = request.getOrderCode();
    	Integer orderType = request.getOrderType();
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("orderCode", orderCode);	
    	int existByCode = this.stockOutOrderDao.isExitsByMap(map);
    	
    	map.put("orderType", orderType);
    	map.put("type", StockOperateTypeEnum.CREATE);
    	
    	try {
    		/**
    		 * 根据existByCode值判定是否重复推送
    		 * existByCode大于0，重复推送不作处理，返回true
    		 * */
    		logger.error("出库单是否已存在existByCode："+existByCode);
    		if(existByCode>0){
    			logger.error("出库单菜鸟重复下发:"+orderCode);
    			String msg =  "出库单菜鸟重复下发:"+orderCode;
    			map.put("msg", msg);
    			response.setSuccess(true);
    			this.createStockOrderOperator(map);
    		}else {
    			Map<String, Object> retMap = buildOrderStockOutOrder(stockOutOrder, request);
            	String code  = ""+retMap.get("code");
            	if ("200".equals(code)) {
            		logger.debug("true:200");
            		response.setSuccess(true);//业务成功  
            		String msg = orderCode+"出库单创建成功！";
            		map.put("msg", msg); 			
            		this.createStockOrderOperator(map);
        		}else{
        			response.setSuccess(false);
        			logger.debug("false:"+retMap.get("msg"));
        			response.setErrorMsg(""+retMap.get("msg")); 
        			String msg = response.getErrorMsg();
            		map.put("msg", msg); 			
        			this.createStockOrderOperator(map);
        		}		
			}	
		} catch (Exception e) {
			response.setSuccess(false);//业务成功    
			response.setErrorMsg("e:"+e.getMessage());
			logger.debug(e.getMessage());
			e.printStackTrace();
		} 	
        return response;
	}
	
	/**
	 * 创建出库单日志
	 * @param map
	 * */
	private void createStockOrderOperator(Map<String, Object> params) {
		StockOrderOperator operator = new StockOrderOperator();
		operator.generateId();
		operator.setDescription(""+params.get("msg"));
		operator.setOperateDate(new Date());
		StockOperateTypeEnum type = (StockOperateTypeEnum) params.get("type");
		operator.setOperateType(type);
		String orderType = ""+params.get("orderType");
		operator.setOrderType(OutOrderTypeEnum.getOrderTypeEnum(orderType).getDescription());
		operator.setOrderId(""+params.get("orderCode"));
		
		Account account = new Account();
		account.setId("cainiao");	
		operator.setAccount(account);
		
		operator.setNewValue(""+params.get("orderCode"));
		operator.setOldValue(""+params.get("orderCode"));
		
		this.operatorDao.insertStockOrderOperator(operator);
	}

	/**
	 * 封装出库单信息
	 * */
	@Transactional
	private   Map<String,Object>  buildOrderStockOutOrder(StockOutOrder stockOutOrder,WmsStockOutOrderNotifyRequest request) 
			throws Exception{
		Map<String,Object>  retMap  = new HashMap<String,Object>();
    	String userId = request.getOwnerUserId();
    	User user = userService.getUserByOwnerCode(userId);
    	System.err.println("OwnerUserId:"+userId);
    	if(user==null){
    		retMap.put("code", "404");
    		retMap.put("msg", "ownerUserId:【"+userId+"】无法匹配商家信息");
    		return   retMap;
    	}
    	stockOutOrder.generateId();
    	stockOutOrder.setUser(user);
    	stockOutOrder.setOwnerUserName(user.getSubscriberName());	
    	stockOutOrder.setPickCompany(request.getPickCompany());
    	stockOutOrder.setPickName(request.getPickName());	
    	stockOutOrder.setPickCall(request.getPickCall());
    	stockOutOrder.setPickTel(request.getPickTel());
    	stockOutOrder.setCarriersName(request.getCarriersName());
    	stockOutOrder.setPickId(request.getPickId());
    	stockOutOrder.setStoreCode(request.getStoreCode());
    	stockOutOrder.setOrderCode(request.getOrderCode());
    	stockOutOrder.setErpOrderCode(request.getErpOrderCode());
    	
    	OutOrderTypeEnum orderTypeEnum = OutOrderTypeEnum.getOrderTypeEnum(""+request.getOrderType());
    	stockOutOrder.setOrderType(orderTypeEnum);
    	stockOutOrder.setOutboundTypeDesc(request.getOutboundTypeDesc());
    	stockOutOrder.setOrderFlag(request.getOrderFlag());
    	stockOutOrder.setOrderCreateTime(request.getOrderCreateTime());
    	stockOutOrder.setSendTime(request.getSendTime());
    	
    	stockOutOrder.setTmsServiceCode(request.getTmsServiceCode());
    	stockOutOrder.setTmsServiceName(request.getTmsServiceName());
    	stockOutOrder.setSupplierCode(request.getSupplierName());
    	stockOutOrder.setSupplierName(request.getSupplierName());  	
    	
    	stockOutOrder.setTransportMode(request.getTransportMode());
    	stockOutOrder.setCarNo(request.getCarNo());
    	
    	stockOutOrder.setTimeZone(request.getTimeZone()==null?"":request.getTimeZone());
    	stockOutOrder.setCurrency(request.getCurrency()==null?"":request.getCurrency());
    	stockOutOrder.setRemark(request.getRemark());
    	stockOutOrder.setExtendFields(request.getExtendFields());
    	
    	//批次下发控制信息
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.BatchSendCtrlParam batchParam = request.getBatchSendCtrlParam();
    	if (batchParam!=null) {
    		BatchSendCtrlParam batchParamTemp =  new BatchSendCtrlParam();
    		this.createBatchSendCtrlParam(batchParam,batchParamTemp);
    		stockOutOrder.setBatchSendCtrlParam(batchParamTemp);
		}
    	
    	//收件人信息
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.ReceiverInfo receiverInfo = request.getReceiverInfo();
    	ReceiverInfo  receiverInfoTemp = new ReceiverInfo();
    	this.createReceiverInfo(receiverInfo,receiverInfoTemp);
    	stockOutOrder.setReceiverInfo(receiverInfoTemp);	
    		
    	//寄件人信息
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.SenderInfo senderInfo = request.getSenderInfo();
    	SenderInfo  senderInfoTemp = new   SenderInfo();
    	this.createSenderInfo(senderInfo,senderInfoTemp);
    	stockOutOrder.setSenderInfo(senderInfoTemp);	
		  	  
    	//出库单明细
    	List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.WmsStockOutOrderItem> orderItemList = request.getOrderItemList();
    	List<WmsStockOutOrderItem>  orderItemListTemp  = new ArrayList<WmsStockOutOrderItem>();
    	this.createWmsStockOutOrderItem(orderItemList,orderItemListTemp,request,stockOutOrder);  	
    	stockOutOrder.setOrderItemList(orderItemListTemp);
    	
    	//装车信息
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.DriverInfo driverInfo = request.getDriverInfo();
		if (driverInfo != null) {
			DriverInfo driverInfoTemp = new DriverInfo();
			this.createDriverInfo(driverInfo,driverInfoTemp);			
			stockOutOrder.setDriverInfo(driverInfoTemp);
		}
		
		//仓库参数
		this.buildCuByStoreCode(stockOutOrder.getUser());
    	  	
    	stockOutOrderDao.insertStockOutOrder(stockOutOrder);
    	
    	retMap.put("code", "200");
    	retMap.put("msg", "成功");
    	
    	return retMap;
	}

	/**
	 * 创建装车信息
	 * */
	private void createDriverInfo(
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.DriverInfo driverInfo,
			DriverInfo driverInfoTemp) {
		driverInfoTemp.generateId();
		driverInfoTemp.setDriverName(driverInfo.getDriverName());
		driverInfoTemp.setDriverIdentityId(driverInfo.getDriverIdentityId());
		driverInfoTemp.setDriverPhone(driverInfo.getDriverPhone());
		VehicleTypeEnum vehicleType = VehicleTypeEnum.getVehicleTypeEnum("" + driverInfo.getVehicleType());
		driverInfoTemp.setVehicleType(vehicleType);
		driverInfoTemp.setVehicleLoad(driverInfo.getVehicleLoad());
		driverInfoTemp.setLicensePlate(driverInfo.getLicensePlate());
		driverInfoDao.saveDriverInfo(driverInfoTemp);
	}

	/**
	 * 创建出库明细信息
	 * msg：匹配不到商品信息商品编码拼接字符串
	 * 当前明细全部遍历完成后抛出匹配失败信息
	 * @exception MyException
	 * */
	private void createWmsStockOutOrderItem(
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.WmsStockOutOrderItem> orderItemList,
			List<WmsStockOutOrderItem> orderItemListTemp, WmsStockOutOrderNotifyRequest request, StockOutOrder stockOutOrder) 
					throws MyException {
		Map<String,Object> sqlParmas  = new HashMap<String, Object>();
		String msg = null;
		for(com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.WmsStockOutOrderItem orderItem: orderItemList) {
    		WmsStockOutOrderItem orderItemTemp  = new WmsStockOutOrderItem();
    		orderItemTemp.generateId();
    		orderItemTemp.setOrderItemId(orderItem.getOrderItemId());
    		orderItemTemp.setUser(userService.getUserByOwnerCode(orderItem.getOwnerUserId()));
    		orderItemTemp.setItemName(orderItem.getItemName());
    		orderItemTemp.setItemCode(orderItem.getItemCode());
    		InventoryTypeEnum inventoryType  =  InventoryTypeEnum.getInventoryType(""+orderItem.getInventoryType());
    		orderItemTemp.setInventoryType(inventoryType);
    		orderItemTemp.setItemQuantity(orderItem.getItemQuantity());
    		orderItemTemp.setItemPrice(orderItem.getItemPrice());
    		orderItemTemp.setItemVersion(orderItem.getItemVersion());
    		orderItemTemp.setBatchCode(orderItem.getBatchCode());
    		orderItemTemp.setPurchaseOrderCode(orderItem.getPurchaseOrderCode());
    		logger.debug("DueDate:Begin");
    		logger.debug("DueDate:"+orderItem.getDueDate());
    		logger.debug("DueDate:End");
    		orderItemTemp.setDueDate(orderItem.getDueDate()!=null?new Date(orderItem.getDueDate().getTime()):null);
    		orderItemTemp.setProduceDate(orderItem.getProduceDate()!=null?new Date(orderItem.getProduceDate().getTime()):null);
    		orderItemTemp.setProduceCode(orderItem.getProduceCode());
    		orderItemTemp.setBatchRemark(orderItem.getBatchRemark()==null?"":orderItem.getBatchRemark());
    		orderItemTemp.setExtendFields(orderItem.getExtendFields());
    		
    		sqlParmas.clear();
    		sqlParmas.put("itemCode", orderItem.getItemCode());
    		User user = this.userService.getUserByOwnerCode(request.getOwnerUserId());
    		sqlParmas.put("userId", user.getId());
    		List<Item> itemByList = itemService.getItemByList(sqlParmas);
    		if(itemByList!=null  && itemByList.size()>0){
    			Item  item  =itemByList.get(0);
    			orderItemTemp.setItem(item);
        		orderItemTemp.setStockOutOrder(stockOutOrder);
        		orderItemListTemp.add(orderItemTemp);
    		}else{
    			//拼接商品编码
    			msg = msg + orderItem.getItemCode()+";";
    		}
    	}
		if (msg!=null&&msg!="") {
			//抛出匹配不到商品信息
			throw new MyException(msg+"无法匹配到商品信息!");
		}
	}

	/**
	 * 创建寄件人信息
	 * */
	private void createSenderInfo(
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.SenderInfo senderInfo,
			SenderInfo senderInfoTemp) {
		senderInfoTemp.generateId();
    	senderInfoTemp.setSenderZipCode(senderInfo.getSenderZipCode());
    	senderInfoTemp.setSenderCountry(senderInfo.getSenderCountry()==null?"中国":senderInfo.getSenderCountry());
    	senderInfoTemp.setSenderProvince(senderInfo.getSenderProvince());
    	senderInfoTemp.setSenderCity(senderInfo.getSenderCity());
    	senderInfoTemp.setSenderArea(senderInfo.getSenderArea());
    	senderInfoTemp.setSenderTown(senderInfo.getSenderTown()==null?"":senderInfo.getSenderTown());
    	senderInfoTemp.setSenderAddress(senderInfo.getSenderAddress());
    	senderInfoTemp.setSenderDivisionId(senderInfo.getSenderDivisionId());
    	senderInfoTemp.setSenderName(senderInfo.getSenderName());
    	senderInfoTemp.setSenderMobile(senderInfo.getSenderMobile());
    	senderInfoTemp.setSenderPhone(senderInfo.getSenderPhone());
    	senderInfoTemp.setSenderEmail(senderInfo.getSenderEmail()==null?"":senderInfo.getSenderEmail());   	
    	senderInfoDao.save(senderInfoTemp);
	}

	/**
	 * 创建收件人信息
	 * */
	private void createReceiverInfo(
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.ReceiverInfo receiverInfo,
			ReceiverInfo receiverInfoTemp) {
		receiverInfoTemp.generateId();
    	receiverInfoTemp.setReceiverZipCode(receiverInfo.getReceiverZipCode());
    	receiverInfoTemp.setReceiverCountry(receiverInfo.getReceiverCountry()==null?"中国":receiverInfo.getReceiverCountry());
    	receiverInfoTemp.setReceiverProvince(receiverInfo.getReceiverProvince());
    	receiverInfoTemp.setReceiverCity(receiverInfo.getReceiverCity());
    	receiverInfoTemp.setReceiverArea(receiverInfo.getReceiverArea());
    	receiverInfoTemp.setReceiveTown(receiverInfo.getReceiverTown()==null?"":receiverInfo.getReceiverTown());
    	receiverInfoTemp.setReceiverCode(receiverInfo.getReceiverCode());
    	receiverInfoTemp.setReceiverAddress(receiverInfo.getReceiverAddress());
    	receiverInfoTemp.setReceiverDivisionId(receiverInfo.getReceiverDivisionId());
    	receiverInfoTemp.setReceiverName(receiverInfo.getReceiverName());
    	receiverInfoTemp.setReceiverMobile(receiverInfo.getReceiverMobile());
    	receiverInfoTemp.setReceiverPhone(receiverInfo.getReceiverPhone());
    	receiverInfoTemp.setReceiverEmail(receiverInfo.getReceiverEmail()==null?"":receiverInfo.getReceiverEmail());
    	receiverInfoDao.save(receiverInfoTemp);
	}

	/**
	 * 创建批次信息
	 * */
	private void createBatchSendCtrlParam(
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_NOTIFY.BatchSendCtrlParam batchParam, BatchSendCtrlParam batchParamTemp) {
    	batchParamTemp.generateId();
    	batchParamTemp.setDistributeType(batchParam.getDistributeType());
    	batchParamTemp.setTotalOrderItemCount(batchParam.getTotalOrderItemCount());
    	batchSendCtrlParamDao.saveBatchSendCtrlParam(batchParamTemp);
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
