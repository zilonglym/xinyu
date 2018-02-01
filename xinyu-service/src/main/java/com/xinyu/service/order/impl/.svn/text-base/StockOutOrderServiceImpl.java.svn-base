package com.xinyu.service.order.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.SystemItemDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.dao.order.StockOutOrderConfirmDao;
import com.xinyu.dao.order.StockOutOrderDao;
import com.xinyu.dao.order.child.WmsStockOutOrderItemDao;
import com.xinyu.dao.trade.ReceiverInfoDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.PackageInfo;
import com.xinyu.model.base.PackageItem;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.StockOutOrderConfirm;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockOutCheckItem;
import com.xinyu.model.order.child.StockOutItemInventory;
import com.xinyu.model.order.child.StockOutOrderItem;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.model.order.enums.OrderTypeEnum;
import com.xinyu.model.order.enums.OutOrderStateEnum;
import com.xinyu.model.order.enums.OutOrderTypeEnum;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.util.MyException;
import com.xinyu.service.caoniao.StockOutOrderConfirmCpImpl;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.StockOutOrderService;

/**
 * 出库单业务处理
 * */
@Service("stockOutOrderServiceImpl")
public class StockOutOrderServiceImpl extends BaseServiceImpl implements StockOutOrderService{

	public static final Logger logger = Logger.getLogger(StockOutOrderServiceImpl.class); 
	
	@Autowired
	private StockOutOrderDao stockOutOrderDao;
	
	@Autowired
	private WmsStockOutOrderItemDao orderItemDao;
	
	@Autowired
	private StockOutOrderConfirmDao confirmDao;
	
	@Autowired
	private ReceiverInfoDao receiverInfoDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SystemItemDao sysDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private StockOutOrderConfirmCpImpl outOrderConfirmCpImpl;
	
	@Autowired
	private StockOrderOperatorDao operatorDao;
	
	/**
	 * 出库单计数
	 * @param params
	 * @return int
	 * */
	
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.stockOutOrderDao.getTotal(params);
	}

	/**
	 * 分页条件查询出库单
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	
	public List<StockOutOrder> findStockOutOrderByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.stockOutOrderDao.findStockOutOrderByPage(params,page,rows);
	}

	/**
	 * 条件查询出库单
	 * @param params
	 * @return list
	 * */
	
	public List<StockOutOrder> findStockOutOrderByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.stockOutOrderDao.findStockOutOrderByList(params);
	}

	/**
	 * 出库单数据重组
	 * @param stockOutOrders
	 * @return list
	 * */
	
	public List<Map<String, Object>> buildListData(List<StockOutOrder> stockOutOrders) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(StockOutOrder stockOutOrder:stockOutOrders){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", stockOutOrder.getId());
			User user = this.userDao.getUserById(stockOutOrder.getUser().getId());
			map.put("userName", user.getSubscriberName());
			map.put("orderCode", (stockOutOrder.getOrderCode()!=null?stockOutOrder.getOrderCode():stockOutOrder.getErpOrderCode()));
 			map.put("status",stockOutOrder.getState().getKey());
			for(OutOrderTypeEnum type:OutOrderTypeEnum.values()){
				if (stockOutOrder.getOrderType().getKey().equals(type.getKey())) {
					map.put("orderType", type.getDescription());
				}
			}
			//modify shark_cj  start 2017- 06  27
			//暂时添加， 正式ERP 调试发现此字段 有可能为空
			if(stockOutOrder.getSendTime()!=null){
				map.put("sendTime",sf.format(stockOutOrder.getSendTime()));
			}else{
				map.put("sendTime","");
			}
			//modify end;
			map.put("orderFlag", (stockOutOrder.getOrderFlag()==null?"":stockOutOrder.getOrderFlag()));
			map.put("orderCreateTime",sf.format(stockOutOrder.getOrderCreateTime()));
			map.put("tmsServiceCode", (stockOutOrder.getTmsServiceCode()==null?"":stockOutOrder.getTmsServiceCode()));
			map.put("pickCompany", (stockOutOrder.getPickCompany()==null?"":stockOutOrder.getPickCompany()));
			map.put("mode", (stockOutOrder.getTransportMode()==null?"":stockOutOrder.getTransportMode()));
			map.put("pickName", (stockOutOrder.getPickName()==null?"":stockOutOrder.getPickName()));
			map.put("pickTel", (stockOutOrder.getPickTel()==null?"":stockOutOrder.getPickTel()));
			map.put("pickCall", (stockOutOrder.getPickCall()==null?"":stockOutOrder.getPickCall()));
			map.put("pickId", (stockOutOrder.getPickId()==null?"":stockOutOrder.getPickId()));
			map.put("remark", (stockOutOrder.getRemark()==null?"":stockOutOrder.getRemark()));
			ReceiverInfo receiverInfo = this.receiverInfoDao.getReceiverInfoById(stockOutOrder.getReceiverInfo().getId());
			System.err.println(stockOutOrder.getReceiverInfo().getId());
			map.put("receiverName", receiverInfo.getReceiverName());
			map.put("receiverMobile", receiverInfo.getReceiverMobile());
			map.put("receiverAddress", receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity()+receiverInfo.getReceiverArea()+receiverInfo.getReceiveTown()+receiverInfo.getReceiverAddress());
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * 根据Id查出库单
	 * @param id
	 * @return StockOutOrder
	 * */
	
	public StockOutOrder findStockOutOrderById(String id) {
		return this.stockOutOrderDao.getStockOutOrderById(id);
	}

	/**
	 * 出库单取消
	 * @param orderCode
	 * @param userId
	 * @param orderType
	 * @param storeCode
	 * @throws MyException 
	 * */
	
	public Map<String, Object> orderCancel(String orderCode, String userId, String orderType) throws MyException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("userId", userId);
		params.put("orderType", OrderTypeEnum.getOrderTypeEnum(orderType));
		logger.debug("params:"+params);
//		System.err.println(params);
//		List<StockOutOrder> stockOutOrders = this.stockOutOrderDao.findStockOutOrderByList(params);
		StockOutOrder stockOutOrder = this.stockOutOrderDao.findStockOutOrderByParam(params);
//		System.err.println(stockOutOrders.size());
		if (stockOutOrder!=null) {
			//修改出库单状态（未确认的出库单，只需修改出库单状态）
			if(stockOutOrder.getState().equals(OutOrderStateEnum.CANCEL)){	
				//已取消的出库单不能重复取消
				retMap.put("msg", "该出库单已取消，不能重复取消");
				throw new MyException(orderCode+"该出库单已取消，不能重复取消");
				
			}else if(stockOutOrder.getState().equals(OutOrderStateEnum.WMS_CONFIRM_FINISH)){
				//已确认完成的订单不能取消
				retMap.put("msg", "该出库单已完成，不能取消");
				throw new MyException(orderCode+"该出库单已完成，不能取消");
				
			}else{
				//未取消的出库单，对库存进行修改
				stockOutOrder.setState(OutOrderStateEnum.CANCEL);
				this.stockOutOrderDao.updateStockOutOrder(stockOutOrder);
				retMap.put("msg", "该出库单取消成功!");
			}
		}
		logger.debug("msg:"+retMap);
		return retMap;
	}

	/**
	 * 提交出库单到菜鸟
	 * @param map
	 * @param data
	 * @return map
	 * */
	
	public Map<String, Object> submitStockOutOrder(Map<String, Object> map) {
		
		logger.debug("出库单确认Service！");
		
		Object orderId  = map.get("orderId"); //入库单 ID
		Object type  = map.get("type");  //提交类型,部分提交和   一次性提交
		Object company = map.get("company");//出库物流公司
		Object remark = map.get("remark");//备注
		
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) map.get("dataList");
		
//		Account account = (Account) map.get("account");
		Account account = SessionUser.get();
		
		StockOutOrder stockOutOrder = this.findStockOutOrderById(""+orderId);
		stockOutOrder.setTmsServiceCode(""+company);
		
		stockOutOrder.setRemark(""+remark);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("value", ""+company);
		List<SystemItem> systemItems = this.sysDao.findSystemItemByList(params);
		stockOutOrder.setTmsServiceName(systemItems.get(0).getDescription());
		
		//出库确认单据
		StockOutOrderConfirm  confirm  = this.createStockOutOrderConfirm(stockOutOrder,type);
		
		//确认单据明细
		List<StockOutOrderItem> orderItems = this.createStockOutOrderItems(stockOutOrder,dataList);	
		confirm.setOrderItems(orderItems);
		
		//出库单商品校验信息列表
		List<StockOutCheckItem> checkList = this.createStockOutCheckItems(confirm,dataList,stockOutOrder);
		confirm.setCheckItems(checkList);
		
		//包裹列表	
		List<PackageInfo> packageInfoList = this.createPackageInfos(map,confirm,dataList); 
		confirm.setPackageInfos(packageInfoList);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> confirmMap  = outOrderConfirmCpImpl.OutOrderConfirm(confirm,stockOutOrder);
		retMap.put("msg", confirmMap.get("msg"));
		this.createStockOrderOperator(stockOutOrder,account,""+confirmMap.get("msg"));
//		if (stockOutOrder.getState().equals(OutOrderStateEnum.getOutOrderStateEnum("WMS_CONFIRM_FINISH"))||stockOutOrder.getState().equals(OutOrderStateEnum.getOutOrderStateEnum("CANCEL"))) {
//			retMap.put("msg", "该出库单已操作完成或已取消！");
//			this.createStockOrderOperator(stockOutOrder,account,""+retMap.get("msg"));		
//		}else{
//			Map<String, Object> confirmMap  = outOrderConfirmCpImpl.OutOrderConfirm(confirm,stockOutOrder);
//			retMap.put("msg", confirmMap.get("msg"));
//			this.createStockOrderOperator(stockOutOrder,account,""+confirmMap.get("msg"));
//			
//		}
		return retMap;
	}

	/**
	 * 生成单据操作日志
	 * @param stockOutOrder
	 * @param account
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 * */
	private void createStockOrderOperator(StockOutOrder stockOutOrder,Account account,String msg) {
		
		logger.debug("出库操作日志记录！");
		
		StockOrderOperator stockOrderOperator = new StockOrderOperator();
		
		stockOrderOperator.generateId();
		
//		stockOrderOperator.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
		
		stockOrderOperator.setAccount(account);
		
		stockOrderOperator.setOperateDate(new Date());
		
		stockOrderOperator.setOperateType(StockOperateTypeEnum.CONFIRM);
		
		stockOrderOperator.setOrderId(stockOutOrder.getId());
		
		stockOrderOperator.setNewValue(OutOrderStateEnum.WMS_CONFIRM_FINISH.getKey());
		
		stockOrderOperator.setOldValue(stockOutOrder.getState().getKey());	
		
		stockOrderOperator.setOrderType(stockOutOrder.getOrderType().getDescription());	
		
		String str = "单据确认：" + stockOutOrder.getOrderCode() + "|" + msg;
		stockOrderOperator.setDescription(str);
 		
		this.operatorDao.insertStockOrderOperator(stockOrderOperator);
	}

	/**
	 * 根据出库单生成出库确认单
	 * @param StockOutOrder
	 * @param type(确认类型)
	 * @return StockOutOrderConfirm
	 * */
	private StockOutOrderConfirm createStockOutOrderConfirm(StockOutOrder stockOutOrder, Object type) {
		
		logger.debug("出库确认单StockOutOrderConfirm！");
		
		StockOutOrderConfirm confirm = new StockOutOrderConfirm();
		
		confirm.generateId();
		
		confirm.setOrderCode(stockOutOrder.getOrderCode());

		confirm.setOrderType(stockOutOrder.getOrderType());
		
		if (stockOutOrder.getState().equals(OutOrderStateEnum.WMS_CONFIRM_FINISH)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderCode", stockOutOrder.getOrderCode());
			params.put("confirmType", 0);
			List<StockOutOrderConfirm> confirms = this.confirmDao.getStockOutOrderConfirmList(params);
			confirm.setOutBizCode(confirms.get(0).getOutBizCode());
		}else{
			confirm.setOutBizCode(""+new Date().getTime());
		}
		
		confirm.setOrderConfirmTime(new Date());
		
		confirm.setTimeZone(stockOutOrder.getTimeZone());
		
		confirm.setConfirmType(Integer.valueOf(""+type));
		
		return confirm;
	}
	
	/**
	 * 根据出库确认单生成校验单
	 * @param StockOutOrderConfirm
	 * @param data
	 * @param stockOutOrder 
	 * @return list
	 * */
	private List<StockOutCheckItem> createStockOutCheckItems(StockOutOrderConfirm confirm, List<Map<String,Object>> data, StockOutOrder stockOutOrder) {
		
		logger.debug("出库StockOutCheckItem！");
		
		List<StockOutCheckItem> checkItems = new ArrayList<StockOutCheckItem>();
		
		System.err.println("data:"+data+" size:"+data.size());
		
		for(Map<String, Object> object : data){
			
			String  num1 = ""+object.get("num1");  // 正品数量
			String  num2 = ""+object.get("num2");  //残次品数量
			String  num3 = ""+object.get("num3");  //机损数量
			String  num4 = ""+object.get("num4");  //箱损数量
			
			if(("".equals(num1) ||  "null".equals(num1)) && ("".equals(num2) ||  "null".equals(num2)) && ("".equals(num3) ||  "null".equals(num3)) && ("".equals(num4) ||  "null".equals(num4))){
				continue;
			}
			
			Long  numCount  = 0L;
			//正品录入数量
			if(!("0".equals(num1) ||"".equals(num1) || "null".equals(num1))){		
				//添加check数量
				numCount  =  numCount + Integer.valueOf(num1);			
			}
			//残品录入数量
			if(!("0".equals(num2) ||"".equals(num2) || "null".equals(num2))){		
				//添加check数量
				numCount  =  numCount + Integer.valueOf(num2);			
			}
			//机损录入数量
			if(!("0".equals(num3) ||"".equals(num3) || "null".equals(num3))){		
				//添加check数量
				numCount  =  numCount + Integer.valueOf(num3);			
			}
			//箱损录入数量
			if(!("0".equals(num4) ||"".equals(num4) || "null".equals(num4))){		
				//添加check数量
				numCount  =  numCount + Integer.valueOf(num4);			
			}
			
			String  orderItemId = ""+object.get("orderItemId");
			
			WmsStockOutOrderItem orderItem = this.orderItemDao.getWmsStockOutOrderItemById(orderItemId);
			
			StockOutCheckItem checkItem = new StockOutCheckItem();
			
			checkItem.generateId();
			
			checkItem.setStockOutOrderConfirm(confirm);
			
			checkItem.setOrderItemId(orderItem.getOrderItemId());
			/**
			 * 根据ConfirmType来确认ChekItem的数量
			 * 0：最终确认，单据所有ChekItem的数量之和（包含当次操作数量）
			 * 1：多次确认，单据当次确认数量
			 * */
			logger.debug("进入确认类型判断流程确认数量！");
			if (confirm.getConfirmType()==1) {
				logger.debug("多次确认！");
				checkItem.setQuantity(numCount);
			}else{
				logger.debug("最终确认！");
				Map<String,Object>  params =  new HashMap<String, Object>();
				params.put("orderItemId", orderItemId);
				 int checkItemSum = this.confirmDao.getCheckItemNum(params);
				checkItem.setQuantity(checkItemSum+numCount);
				logger.debug("最终确认数量:"+checkItemSum+numCount);
			}
			logger.debug("checkItem数量确认完毕！");
			checkItems.add(checkItem);
		}
		return checkItems;
	}
	

	/**
	 * 生成包裹信息
	 * @param map
	 * @param StockOutOrderConfirm
	 * @param data
	 * @return list
	 * */
	private List<PackageInfo> createPackageInfos(Map<String, Object> map, StockOutOrderConfirm confirm,List<Map<String, Object>> data) {
		
		logger.debug("PackageInfo！");
		
		Object company = map.get("company");//出库物流公司
		Object orderNo = map.get("orderNo");//出库物流单号
		Object packageLength = map.get("packageLength");//包裹长度
		Object packageWidth = map.get("packageWidth");//包裹宽度
		Object packageHeight = map.get("packageHeight");//包裹高度
		
		List<PackageInfo> packageInfoList = new ArrayList<PackageInfo>(); 
		
		PackageInfo packageInfo = new PackageInfo();
		
		packageInfo.generateId();
		
		packageInfo.setTmsCode(""+company);
		
		int weight = this.getTotalWeight(data);
		packageInfo.setPackageWeight(weight);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("value", ""+company);
		List<SystemItem> systemItems = this.sysDao.findSystemItemByList(params);
		packageInfo.setTmsServiceName(systemItems.get(0).getDescription());
		
		packageInfo.setTmsOrderCode(""+orderNo);
		//根据时间生成packageCode
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
		String packageCode = "PADX"+sf.format(new Date()).substring(2, 14);
		packageInfo.setPackageCode(packageCode);
		
		int height = 0;
		int length = 0;
		int width = 0;
		int volumn = 0;
		
		//包裹高
		if (packageHeight!=null&&packageHeight!="") {
			height = Integer.parseInt(String.valueOf(packageHeight));
			packageInfo.setPackageHeight(height);
		}
		
		//包裹长
		if (packageLength!=null&&packageLength!="") {
			length = Integer.parseInt(String.valueOf(packageLength));
			packageInfo.setPackageLength(length);
		}
		
		//包裹宽
		if (packageWidth!=null&&packageWidth!="") {
			width = Integer.parseInt(String.valueOf(packageWidth));
			packageInfo.setPackageWidth(width);
		}
		
		//包裹体积
		volumn = (length*width*height)/1000;
		packageInfo.setPackageVolumn(String.valueOf(volumn));
		
		packageInfo.setStockOutOrderConfirm(confirm);
		
		List<PackageItem> packageItems = this.createPackageItems(packageInfo,data);
		
		packageInfo.setPackageItemItems(packageItems);
		
		packageInfoList.add(packageInfo);
		
		return packageInfoList;
	}

	
	/**
	 * 生成包裹明细
	 * @param packageInfo
	 * @param data
	 * @return list
	 * */
	private List<PackageItem> createPackageItems(PackageInfo packageInfo, List<Map<String, Object>> data) {
		
		logger.debug("PackageItem！");
		
		List<PackageItem> packageItems = new ArrayList<PackageItem>();
		
		for(Map<String, Object> object : data){
			
			int totalNum = this.getTotalNum(object);
			
			PackageItem packageItem = new PackageItem();
			
			packageItem.generateId();
			
			packageItem.setPackageItem(packageInfo);
			
			String itemId = ""+object.get("itemId");
			Item item = this.itemDao.getItem(itemId);
			packageItem.setItem(item);
			
			packageItem.setItemCode(item.getItemCode());
			
			packageItem.setItemQuantity(totalNum);
			
			packageItems.add(packageItem);
		}
		return packageItems;
	}

	/**
	 * 生成确认单明细
	 * @param StockOutOrder
	 * @param data
	 * @return list
	 * */
	private List<StockOutOrderItem> createStockOutOrderItems(StockOutOrder stockOutOrder, List<Map<String, Object>> data) {
		
		logger.debug("StockOutOrderItem");
		
		List<StockOutOrderItem>  orderList  = new ArrayList<StockOutOrderItem>();
	
		for(Map<String, Object> object : data){

			String  num1 = ""+object.get("num1");  // 正品数量
			String  num2 = ""+object.get("num2");  //残次品数量
			String  num3 = ""+object.get("num3");  //机损数量
			String  num4 = ""+object.get("num4");  //箱损数量
			
			System.err.println("num1:"+num1);
			
			if(("".equals(num1) ||  "null".equals(num1)) && ("".equals(num2) ||  "null".equals(num2)) && ("".equals(num3) ||  "null".equals(num3)) && ("".equals(num4) ||  "null".equals(num4))){
				continue;
			}
			
			StockOutOrderItem itemOrderItem  = new StockOutOrderItem();
			
			itemOrderItem.generateId();
			
			itemOrderItem.setStockOutOrder(stockOutOrder);
			
			String orderItemId = ""+object.get("orderItemId");
			WmsStockOutOrderItem orderItem = this.orderItemDao.getWmsStockOutOrderItemById(orderItemId);
			itemOrderItem.setOrderItemId(orderItem.getOrderItemId());
			
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			itemOrderItem.setItem(item);
			
			List<StockOutItemInventory>  inventoryList = new ArrayList<StockOutItemInventory>();
			
			//正品
			if(!("0".equals(num1) ||"".equals(num1) || "null".equals(num1))){			
				StockOutItemInventory itemInventory = this.createStockOutItemInventory(itemOrderItem,num1,InventoryTypeEnum.getInventoryType("1"));					
				inventoryList.add(itemInventory);
			}
				
			//残次品
			if(!("0".equals(num2) ||"".equals(num2) ||"null".equals(num2))) {		
				StockOutItemInventory itemInventory = this.createStockOutItemInventory(itemOrderItem,num2,InventoryTypeEnum.getInventoryType("101"));		
				inventoryList.add(itemInventory);
			}
			
			//机损
			if(!("0".equals(num3) ||"".equals(num3) || "null".equals(num3))){			
				StockOutItemInventory itemInventory = this.createStockOutItemInventory(itemOrderItem,num3,InventoryTypeEnum.getInventoryType("102"));				
				inventoryList.add(itemInventory);
			}
			
			//箱损
			if(!("0".equals(num4) ||"".equals(num4) || "null".equals(num4))){				
				StockOutItemInventory itemInventory = this.createStockOutItemInventory(itemOrderItem,num4,InventoryTypeEnum.getInventoryType("103"));			
				inventoryList.add(itemInventory);
			}
			
			itemOrderItem.setItems(inventoryList);
			
			orderList.add(itemOrderItem);
		
		}
		
		return orderList;
	}

	/**
	 * 创建校验信息
	 * @param StockOutOrderItem
	 * @param num1
	 * @param inventoryType
	 * @return StockOutItemInventory
	 * */
	private StockOutItemInventory createStockOutItemInventory(StockOutOrderItem itemOrderItem, String num1,
			InventoryTypeEnum inventoryType) {
		
		logger.debug("出库校验！");
		
		int totalNum = 0;
		
		StockOutItemInventory itemInventory = new StockOutItemInventory();
		
		itemInventory.generateId();
		
		itemInventory.setStockOutOrderItem(itemOrderItem);
		
		itemInventory.setInventoryType(inventoryType);
		
		itemInventory.setQuantity(Integer.valueOf(num1));	
		
		totalNum = totalNum+Integer.parseInt(num1);
		
		return itemInventory;
	}

	/**
	 * 计算订单重量
	 * @param data
	 * @return int
	 * */
	private int getTotalWeight(List<Map<String, Object>> data){
		
		logger.debug("重量校验！");
		
		int totalWeight = 0;
		int totalNum = 0;
		for(Map<String, Object> object : data){
			String  num1 = ""+object.get("num1");  // 正品数量
			String  num2 = ""+object.get("num2");  //残次品数量
			String  num3 = ""+object.get("num3");  //机损数量
			String  num4 = ""+object.get("num4");  //箱损数量
			if(!("".equals(num1) || "null".equals(num1))){
				totalNum = totalNum+Integer.parseInt(num1);
			}
			if(!("".equals(num2) || "null".equals(num2))){
				totalNum = totalNum+Integer.parseInt(num2);
			}
			if(!("".equals(num3) || "null".equals(num3))){
				totalNum = totalNum+Integer.parseInt(num3);
			}
			if(!("".equals(num4) || "null".equals(num4))){
				totalNum = totalNum+Integer.parseInt(num4);
			}
			String itemId = ""+object.get("itemId");
			Item item = this.itemDao.getItem(itemId);
			String grossWeight = String.valueOf((item.getWmsGrossWeight()==null?item.getGrossWeight():item.getWmsGrossWeight()));
			int weight = Integer.parseInt(grossWeight);
			totalWeight = totalWeight+weight*totalNum;
		}
		return totalWeight;
	}

	/**
	 * 计算包裹商品总数
	 * @param map
	 * @return int
	 * */
	private int getTotalNum(Map<String, Object> object){
		
		logger.debug("数量校验！");
		
		int totalNum = 0;
	
		String  num1 = ""+object.get("num1");  // 正品数量
		String  num2 = ""+object.get("num2");  //残次品数量
		String  num3 = ""+object.get("num3");  //机损数量
		String  num4 = ""+object.get("num4");  //箱损数量
		
		if(!("0".equals(num1) ||"".equals(num1) || "null".equals(num1))){
			totalNum = totalNum+Integer.parseInt(num1);
		}
		
		if(!("0".equals(num2) ||"".equals(num2) || "null".equals(num2))){
			totalNum = totalNum+Integer.parseInt(num2);
		}
		
		if(!("0".equals(num3) ||"".equals(num3) || "null".equals(num3))){
			totalNum = totalNum+Integer.parseInt(num3);
		}
		
		if(!("0".equals(num4) ||"".equals(num4) || "null".equals(num4))){
			totalNum = totalNum+Integer.parseInt(num4);
		}
	
		return totalNum;	
	}

	
	public StockOutOrder findStockOutOrderByParam(Map<String, Object> params) {
		return this.stockOutOrderDao.findStockOutOrderByParam(params);
	}
	
}
