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
import com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInOrderNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInOrderNotifyResponse;
import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.base.BatchSendCtrlParamDao;
import com.xinyu.dao.base.DriverInfoDao;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.SenderInfoDao;
import com.xinyu.dao.order.StockInOrderDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.dao.order.child.WmsStockInCaseInfoDao;
import com.xinyu.model.base.BatchSendCtrlParam;
import com.xinyu.model.base.DriverInfo;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.VehicleTypeEnum;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.child.WmsStockInCaseInfo;
import com.xinyu.model.order.child.WmsStockInCaseItem;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderTypeEnum;
import com.xinyu.model.order.enums.OrderFlagEnum;
import com.xinyu.model.order.enums.OrderSourceEnum;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.Constants;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.UserService;

/**
 * 入库通知接口，具体的实现类，
 * @author shark_cj
 * @since 2017-04-27
 */
@Component
public class WmsStockInOrderNotifyCpImpl implements ReceiveListener<WmsStockInOrderNotifyRequest, WmsStockInOrderNotifyResponse> {

	public static final Logger logger = Logger.getLogger(WmsStockInOrderNotifyCpImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StockInOrderDao stockInOrderDao;
	
	@Autowired
	private StockOrderOperatorDao operatorDao;
	
	@Autowired
	private  ItemDao   itemDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private  SenderInfoDao   senderInfoDao;
	
	@Autowired
	private  DriverInfoDao driverInfoDao;
	
	@Autowired
	private  BatchSendCtrlParamDao  batchSendCtrlParamDao;
	
	@Autowired
	private WmsStockInCaseInfoDao  wmsStockInCaseInfoDao;
	
	
	@Override
    public WmsStockInOrderNotifyResponse execute(ReceiveSysParams params,
            WmsStockInOrderNotifyRequest request) {
    	
    	StockInOrder stockInOrder  = new StockInOrder();
    	String content = params.getContent(); 
    	WmsStockInOrderNotifyResponse response = new WmsStockInOrderNotifyResponse();
    	System.out.println("content:"+content);
    	
    	Map<String, Object> sqlParams = new HashMap<String, Object>();
    	try {
    		String orderCode = request.getOrderCode();
    		sqlParams.put("orderCode", orderCode);
    		int existByCode = stockInOrderDao.isExistByMap(sqlParams);
    		
    		Integer orderType = request.getOrderType();
    		sqlParams.put("orderType", orderType);
    		
    		sqlParams.put("type", StockOperateTypeEnum.CREATE);
    		
    		if(existByCode>0){
    			System.err.println("入库订单菜鸟重复下发:"+orderCode);
    			response.setSuccess(true);
    			
    			String msg =  "出库单菜鸟重复下发:"+orderCode;
    			sqlParams.put("msg", msg);
    			
    			this.createStockOrderOperator(sqlParams);
    			
    			return response;
    		}
    		
			Map<String, Object> retMap = buildOrderStockInOrder(stockInOrder, request);
			String code  = ""+retMap.get("code");
			if("200".equals(code)){
				response.setSuccess(true);//业务成功
				
				String msg = orderCode+"入库单创建成功！";
				sqlParams.put("msg", msg); 			
        		this.createStockOrderOperator(sqlParams);
        		
			}else{
				response.setSuccess(false);
				response.setErrorMsg(""+retMap.get("msg"));
				
				String msg = response.getErrorMsg();
				sqlParams.put("msg", msg); 			
    			this.createStockOrderOperator(sqlParams);
    			
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
			
			String msg = response.getErrorMsg();
			sqlParams.put("msg", msg); 			
			this.createStockOrderOperator(sqlParams);
		}
	 
        return response;
    } 
	
	/**
	 * 入库日志
	 * @param params
	 * */
    private void createStockOrderOperator(Map<String, Object> params) {
    	StockOrderOperator operator = new StockOrderOperator();
		operator.generateId();
		operator.setDescription(""+params.get("msg"));
		operator.setOperateDate(new Date());
		StockOperateTypeEnum type = (StockOperateTypeEnum) params.get("type");
		operator.setOperateType(type);
		String orderType = ""+params.get("orderType");
		operator.setOrderType(InOrderTypeEnum.getOrderTypeEnum(orderType).getDescription());
		operator.setOrderId(""+params.get("orderCode"));
		
		Account account = new Account();
		account.setId("cainiao");	
		operator.setAccount(account);
		
		operator.setNewValue(""+params.get("orderCode"));
		operator.setOldValue(""+params.get("orderCode"));
		
		this.operatorDao.insertStockOrderOperator(operator);
	}
    
	/**
     * @param stockInOrder
     * @param params
     * @throws Exception
     * 入库单通知单构建
     */
    @Transactional
    private  Map<String,Object> buildOrderStockInOrder(StockInOrder stockInOrder,WmsStockInOrderNotifyRequest request) throws Exception{
		
    	Map<String,Object>  retMap  = new HashMap<String,Object>();
    	
    	String userId = request.getOwnerUserId();
    	User user = userService.getUserByOwnerCode(userId);
    	if(user==null){
    		retMap.put("code", "404");
    		retMap.put("msg", "ownerUserId:【"+userId+"】无法匹配商家信息");
    		return   retMap;
    	}
    	stockInOrder.generateId();
    	stockInOrder.setUser(user);
    	
    	String ownerUserName = request.getOwnerUserName();
    	stockInOrder.setOwnerUserName(ownerUserName);
    	
    	String storeCode = request.getStoreCode();
    	stockInOrder.setStoreCode(storeCode);
    	
    	String orderCode = request.getOrderCode();
    	stockInOrder.setOrderCode(orderCode);
    	
    	String erpOrderCode = request.getErpOrderCode();
    	stockInOrder.setErpOrderCode(erpOrderCode);
    	
    	String orderType = ""+request.getOrderType();
    	InOrderTypeEnum  orderTypeEnum  = InOrderTypeEnum.getOrderTypeEnum(orderType);
    	stockInOrder.setOrderType(orderTypeEnum);
    	
    	String inboundTypeDesc = request.getInboundTypeDesc();
    	stockInOrder.setInboundTypeDesc(inboundTypeDesc);
    	
    	String orderFlag = request.getOrderFlag();
    	OrderFlagEnum  orderFlagEnum  = OrderFlagEnum.getOrderFlagEnum(orderFlag);
    	if(orderFlagEnum==null){
    		orderFlagEnum   =   OrderFlagEnum.getOrderFlagEnum("100");
    	}
    	stockInOrder.setOrderFlag(orderFlagEnum);
    	
    	String orderSource =  "" +  request.getOrderSource();
    	OrderSourceEnum orderSourceEnum  = OrderSourceEnum.getOrderSourceEnum(orderSource);
    	if(orderSourceEnum==null){
    		orderSourceEnum=  OrderSourceEnum.getOrderSourceEnum("301");
    	}
    	stockInOrder.setOrderSource(orderSourceEnum);
    	
    	Date orderCreateTime = request.getOrderCreateTime();
    	stockInOrder.setOrderCreateTime(orderCreateTime);
    	
    	String supplierCode = request.getSupplierCode();
    	stockInOrder.setSupplierCode(supplierCode);
    	
    	String supplierName = request.getSupplierName();
    	stockInOrder.setSupplierName(supplierName);
    	
    	String tmsServiceCode = request.getTmsServiceCode();
    	stockInOrder.setTmsServiceCode(tmsServiceCode);
    	
    	String tmsServiceName = request.getTmsServiceName();
    	stockInOrder.setTmsServiceName(tmsServiceName);
    	
    	String tmsOrderCode = request.getTmsOrderCode();
    	stockInOrder.setTmsOrderCode(tmsOrderCode);
    	
    	String prevOrderCode = request.getPrevOrderCode();
    	stockInOrder.setPrevOrderCode(prevOrderCode);
    	
    	String prevEprOrderCode = request.getErpOrderCode();
    	stockInOrder.setPrevEprOrderCode(prevEprOrderCode);
    	
    	String returnReason = request.getReturnReason();
    	stockInOrder.setReturnReason(returnReason);
    	
    	stockInOrder.setExpectStartTime(request.getExpectStartTime());
    	
    	stockInOrder.setExpectEndTime(request.getExpectEndTime());
    	
    	stockInOrder.setBuyerNick(request.getBuyerNick());
    	
    	/**
    	 * 分批下发控制信息
    	 */
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.BatchSendCtrlParam batchSendCtrlParam = request.getBatchSendCtrlParam();
    	if(batchSendCtrlParam!=null){
    		BatchSendCtrlParam   batchSendCtrlParamTemp  = new BatchSendCtrlParam();
    		batchSendCtrlParamTemp.generateId();
    		batchSendCtrlParamTemp.setDistributeType(batchSendCtrlParam.getDistributeType());
    		batchSendCtrlParamTemp.setTotalOrderItemCount(batchSendCtrlParam.getTotalOrderItemCount());
    		batchSendCtrlParamDao.saveBatchSendCtrlParam(batchSendCtrlParamTemp);
    		stockInOrder.setBatchSendCtrlParam(batchSendCtrlParamTemp);
    	}
    	
    	/**
    	 * 出库单明细
    	 */
    	List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInOrderItem> orderItemList = request.getOrderItemList();
    	List<WmsStockInOrderItem> orderItemListTemp = new ArrayList<WmsStockInOrderItem>();
    	Map<String,Object>  sqlParmas = new HashMap<String, Object>();
    	for(com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInOrderItem stockInOrderItem :orderItemList){
    		WmsStockInOrderItem stockInOrderItemTemp = new WmsStockInOrderItem();
    		stockInOrderItemTemp.generateId();
    		stockInOrderItemTemp.setOrderItemId(stockInOrderItem.getOrderItemId());
    		stockInOrderItemTemp.setOrderSourceCode(stockInOrderItem.getOrderSourceCode());
    		stockInOrderItemTemp.setSubSourceCode(stockInOrderItem.getSubSourceCode());
    		stockInOrderItemTemp.setOwnerUserId(stockInOrderItem.getOwnerUserId());
    		sqlParmas.clear();
    		sqlParmas.put("itemCode", stockInOrderItem.getItemCode());
    		sqlParmas.put("userId", user.getId());
    		System.err.println("itemCode:"+stockInOrderItem.getItemCode());
    		System.err.println("userId:"+request.getOwnerUserId());
    		List<Item> itemByList = itemDao.getItemByList(sqlParmas);
    		Item  item  =itemByList.get(0);
    		stockInOrderItemTemp.setItem(item);
    		stockInOrderItemTemp.setItemName(stockInOrderItem.getItemName());
    		stockInOrderItemTemp.setItemCode(stockInOrderItem.getItemCode());
    		stockInOrderItemTemp.setBarCode(stockInOrderItem.getBarCode());
    		stockInOrderItemTemp.setInventoryType(InventoryTypeEnum.getInventoryType(""+stockInOrderItem.getInventoryType()));
    		stockInOrderItemTemp.setItemQuantity(stockInOrderItem.getItemQuantity());
    		stockInOrderItemTemp.setPurchaseOrderCode(stockInOrderItem.getPurchaseOrderCode());
    		stockInOrderItemTemp.setPurchasePrice(stockInOrderItem.getPurchasePrice());
    		stockInOrderItemTemp.setTaxRate(stockInOrderItem.getTaxRate());
    		stockInOrderItemTemp.setItemVersion(stockInOrderItem.getItemVersion());
    		stockInOrderItemTemp.setBatchCode(stockInOrderItem.getBatchCode());
    		stockInOrderItemTemp.setDueDate(stockInOrderItem.getDueDate());
    		stockInOrderItemTemp.setProduceDate(stockInOrderItem.getProduceDate());
    		stockInOrderItemTemp.setProduceCode(stockInOrderItem.getProduceCode());
    		stockInOrderItemTemp.setBatchRemark(stockInOrderItem.getBatchRemark());
    		stockInOrderItemTemp.setExtendFields(stockInOrderItem.getExtendFields());
    		
    		stockInOrderItemTemp.setStockInOrder(stockInOrder);//对象关联起来
    		orderItemListTemp.add(stockInOrderItemTemp);
    	}
    	stockInOrder.setOrderItemList(orderItemListTemp);
    	
    	/**
    	 *  发件人信息  
    	 */
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.SenderInfo senderInfo = request.getSenderInfo();
    	SenderInfo senderInfoTemp  =  new SenderInfo();
    	senderInfoTemp.generateId();
    	senderInfoTemp.setSenderZipCode(senderInfo.getSenderZipCode());
    	senderInfoTemp.setSenderCountry(senderInfo.getSenderCountry());
    	senderInfoTemp.setSenderProvince(senderInfo.getSenderProvince());
    	senderInfoTemp.setSenderCity(senderInfo.getSenderCity());
    	senderInfoTemp.setSenderArea(senderInfo.getSenderArea());
    	senderInfoTemp.setSenderAddress(senderInfo.getSenderAddress());
    	senderInfoTemp.setSenderName(senderInfo.getSenderName());
    	senderInfoTemp.setSenderMobile(senderInfo.getSenderMobile());
    	senderInfoTemp.setSenderPhone(senderInfo.getSenderPhone());
    	senderInfoTemp.setSenderTown(senderInfo.getSenderTown());
    	senderInfoTemp.setSenderDivisionId(senderInfo.getSenderDivisionId());
    	senderInfoTemp.setSenderEmail(senderInfo.getSenderEmail());
    	
    	senderInfoDao.save(senderInfoTemp);
    	stockInOrder.setSenderInfo(senderInfoTemp);
    	
    	/**
    	 * 车装信息
    	 */
    	com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.DriverInfo driverInfo = request.getDriverInfo();
    	if(driverInfo!=null){
    		DriverInfo  driverInfoTemp  = new  DriverInfo();
    		driverInfoTemp.generateId();
    		driverInfoTemp.setDriverIdentityId(driverInfo.getDriverIdentityId());
    		driverInfoTemp.setDriverName(driverInfo.getDriverName());
    		driverInfoTemp.setDriverPhone(driverInfo.getDriverName());
    		driverInfoTemp.setVehicleLoad(driverInfo.getVehicleLoad());
    		VehicleTypeEnum  vehicleType    =  VehicleTypeEnum.getVehicleTypeEnum(""+driverInfo.getVehicleType());
    		driverInfoTemp.setVehicleType(vehicleType);
    		driverInfoTemp.setLicensePlate(driverInfo.getLicensePlate());
    		driverInfoDao.saveDriverInfo(driverInfoTemp);
    		stockInOrder.setDriverInfo(driverInfoTemp);
    	}
    	
    	/**
    	 * 装箱列表
    	 */
    	List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInCaseInfo> caseInfoList = request.getCaseInfoList();
    	if(caseInfoList!=null){
    		List<WmsStockInCaseInfo> caseInfoListTemp  = new ArrayList<WmsStockInCaseInfo>();
    		if(caseInfoList!=null  &&  caseInfoList.size()>0){
    			for(com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInCaseInfo caseInfo: caseInfoList){
    			WmsStockInCaseInfo caseInfoTemp = new WmsStockInCaseInfo();
    			caseInfoTemp.generateId();
    			caseInfoTemp.setCaseNumber(caseInfo.getCaseNumber());
    			caseInfoTemp.setCaseSequence(caseInfo.getCaseSequence());
    			caseInfoTemp.setWidth(caseInfo.getWidth());
    			caseInfoTemp.setPalletNumebr(caseInfo.getPalletNumebr());
    			caseInfoTemp.setWeight(caseInfo.getWeight());
    			caseInfoTemp.setHeight(caseInfo.getHeight());
    			caseInfoTemp.setContainerNumber(caseInfo.getContainerNumber());
    			
    			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInCaseItem> caseItemList = caseInfo.getCaseItemList();
    			List<WmsStockInCaseItem>  stockInCaseItemTemplist  = new ArrayList<WmsStockInCaseItem>();
    			for(com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_NOTIFY.WmsStockInCaseItem  stockInCaseItem :caseItemList){
    				WmsStockInCaseItem  stockInCaseItemTemp  = new WmsStockInCaseItem();
    				stockInCaseItemTemp.generateId();
    				stockInCaseItemTemp.setItemId(""+stockInCaseItem.getItemId());
    				stockInCaseItemTemp.setItemName(stockInCaseItem.getItemName());
    				stockInCaseItemTemp.setItemCode(stockInCaseItem.getItemCode());
    				stockInCaseItemTemp.setBarCode(stockInCaseItem.getBarCode());
    				stockInCaseItemTemp.setInventoryType(InventoryTypeEnum.getInventoryType(""+stockInCaseItem.getInventoryType()));
    				stockInCaseItemTemp.setItemQuantity(stockInCaseItem.getItemQuantity());
    				stockInCaseItemTemp.setItemVersion(stockInCaseItem.getItemVersion());
    				stockInCaseItemTemp.setWmsStockInCaseInfo(caseInfoTemp);
    				stockInCaseItemTemplist.add(stockInCaseItemTemp);
    			}
    			caseInfoTemp.setCaseItemList(stockInCaseItemTemplist);
    			caseInfoTemp.setStockInOrder(stockInOrder);
    			wmsStockInCaseInfoDao.saveWmsStockInCaseInfo(caseInfoTemp);
    			caseInfoListTemp.add(caseInfoTemp);
    			}
    		} 
    		stockInOrder.setCaseInfoList(caseInfoListTemp);
    	}
    	stockInOrder.setExtendFields(request.getExtendFields());
    	stockInOrder.setTimeZone(request.getTimeZone());
    	stockInOrder.setCurrency(request.getCurrency());
    	stockInOrder.setRemark(request.getRemark());
    	
    	retMap.put("code", "200");
    	retMap.put("msg", "成功");
    	
    	//仓库参数
    	this.buildCuByStoreCode(stockInOrder.getUser());
    	
    	stockInOrderDao.insertStockInOrder(stockInOrder);
    	return retMap;
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