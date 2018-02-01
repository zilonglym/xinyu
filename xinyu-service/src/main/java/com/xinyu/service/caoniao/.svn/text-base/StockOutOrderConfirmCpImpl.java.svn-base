package com.xinyu.service.caoniao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.WmsStockOutOrderConfirmCallbackRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_STOCK_OUT_ORDER_CONFIRM.WmsStockOutOrderConfirmResponse;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.order.StockOutOrderConfirmDao;
import com.xinyu.dao.order.StockOutOrderDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.PackageInfo;
import com.xinyu.model.base.PackageItem;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.StockOutOrderConfirm;
import com.xinyu.model.order.child.StockOutCheckItem;
import com.xinyu.model.order.child.StockOutItemInventory;
import com.xinyu.model.order.child.StockOutOrderItem;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.model.order.enums.OutOrderStateEnum;
import com.xinyu.service.common.Constants;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.util.CaiNiaoPacClient;


/**
 * @author shark_cj
 * 出库确认
 */
@Component
public class StockOutOrderConfirmCpImpl {

	
	@Autowired
	private  StockOutOrderDao  stockOUtOrderDao;
	
	@Autowired
	private  StockOutOrderConfirmDao  stockOutOrderConfirmDao;
	
	@Autowired
	private  InventoryService  inventoryService;
	
	public static final Logger logger = Logger.getLogger(StockOutOrderConfirmCpImpl.class);
	
	/**
	 * 出库单确认接口
	 * */
	public Map<String, Object> OutOrderConfirm(StockOutOrderConfirm stockOutOrderConfirm,StockOutOrder stockOutOrder) {
		logger.debug("出库单确认开始！");
		WmsStockOutOrderConfirmCallbackRequest  request  = new WmsStockOutOrderConfirmCallbackRequest();
		Map<String,Object>  retMap  = new HashMap<String, Object>();
		try {
			String status = stockOutOrder.getState().getKey();
			
			SendSysParams params=new SendSysParams();
			params.setFromCode(Constants.cainiao_fromCode);
//			buildStockInOrder(stockInOrder,request);
			//菜鸟接口拼接上传
			List<Map<String,Object>> parmasList = new ArrayList<Map<String,Object>>();
			build(stockOutOrderConfirm,request,parmasList);
			WmsStockOutOrderConfirmResponse response = CaiNiaoPacClient.getClient().send(request, params);
			if(response.isSuccess()){
				retMap.put("code", "200");
				retMap.put("msg", "菜鸟上传成功");
				//保存出库确认单
				stockOutOrderConfirmDao.insertStockOutOrderConfirm(stockOutOrderConfirm);
				if(stockOutOrderConfirm.getConfirmType()==1){
					stockOutOrder.setState(OutOrderStateEnum.getOutOrderStateEnum("WMS_CONFIRMING"));   //部分上传
				}else if(stockOutOrderConfirm.getConfirmType()==0){
					stockOutOrder.setState(OutOrderStateEnum.getOutOrderStateEnum("WMS_CONFIRM_FINISH"));  //全部上传
				}
				/**
				 * modify 2017-08-18
				 * @author fufangjue
				 * 出库单据状态已完成或者已取消，再次确认不再进行库存相关操作
				 * */
				if ("WMS_CONFIRM_FINISH".equals(status)||"CANCEL".equals(status)) {
					logger.debug(stockOutOrder.getOrderCode()+",该出库单已确认出库，不再重复操作！");
				}else {
					logger.debug(stockOutOrder.getOrderCode()+",该出库单开始进入库存更新操作！");
					inventoryService.addNumBatch(parmasList);
					stockOUtOrderDao.updateStockOutOrder(stockOutOrder);
					logger.debug(stockOutOrder.getOrderCode()+",该出库单库存更新操作结束！");
				}
				
				logger.debug("status:"+stockOutOrder.getState());
			}else{
				retMap.put("code", "500");
				retMap.put("msg", "菜鸟错误编码:"+response.getErrorCode()+",错误信息:"+response.getErrorMsg());
			}
			return retMap;
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
			e.printStackTrace();
			return retMap;
		}
	}


	/**
	 * 封装stockOutOrderConfirm信息
	 * */
	@Transactional
	private void build(StockOutOrderConfirm stockOutOrderConfirm, WmsStockOutOrderConfirmCallbackRequest request, List<Map<String, Object>> parmasList) 
			throws Exception{
		logger.debug("出库单封装信息开始！");

//		orderItems	List<StockInOrderItem>	　	FALSE	　	出库单商品信息列表	orderLines
		List<StockOutOrderItem> orderItemsTemp = stockOutOrderConfirm.getOrderItems();
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem> orderItems =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem>();	
		this.createStockOutOrderItem(orderItemsTemp,orderItems,parmasList,stockOutOrderConfirm);
		request.setOrderItems(orderItems);

//		orderCode	string	64	TRUE	LBX12345	仓储中心出库订单编码	新增，菜鸟订单号		
		request.setOrderCode(stockOutOrderConfirm.getOrderCode());

//		orderType	int	11	TRUE	302	单据类型： 302调拨出库单 501销退出库单 601采购出库单 904普通出库单306 B2B出库单604	B2B干线退货出库
//		704	库存状态调整出库	新增，对应菜鸟下发的orderType
		request.setOrderType(Integer.valueOf(""+stockOutOrderConfirm.getOrderType().getKey()));
		
//		outBizCode	string	64	TRUE	***	外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样	outBizCode
		request.setOutBizCode(stockOutOrderConfirm.getOutBizCode());

//		confirmType	int	11	TRUE	0	支持出库单多次确认(如：每上架一次就立刻回传) 0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0	confirmType
//		同一出库单;如果先收到0;后又收到1，不允许修改收货的数量)
		request.setConfirmType(stockOutOrderConfirm.getConfirmType());
		
//		orderConfirmTime	date	　	TRUE	　	仓库出库单完成时间	operateTime
		request.setOrderConfirmTime(stockOutOrderConfirm.getOrderConfirmTime());

//		timeZone	string	　16	FALSE	　	时区	新增
		request.setTimeZone(stockOutOrderConfirm.getTimeZone());
		
//		tmsServiceCode	string	64	FALSE	　	配送编码	logisticsCode
//		tmsServiceName	string	64	FALSE	　	配送公司名称	logisticsName
//		tmsOrderCode	string	64	FALSE	　	运单号，指定运单号发货业务	expressCode
				
//		returnReason	string	64	FALSE	***	退货原因：销退场景下，如可能请提供退货的原因，多个退货原因用；号分开	returnReason		
		
//		checkItems	List<StockInCheckItem>	　	FALSE	　	出库单商品校验信息列表	新增，分批的最后一批时必传，此节点是所有回传报文货品数量汇总。
		List<StockOutCheckItem> checkItemsTemp  =  stockOutOrderConfirm.getCheckItems();
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem>  checkItems = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem>();
		this.createStockOutCheckItem(checkItemsTemp,checkItems);
		request.setCheckItems(checkItems);

// 		packageInfos List<PackageInfo> FALSE 出库商品包裹信息
		List<PackageInfo> packageInfosTemp =  stockOutOrderConfirm.getPackageInfos();
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo> packageInfos = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo>();
		this.createPackageInfo(packageInfosTemp,packageInfos);
		request.setPackageInfos(packageInfos);
	}

	/**
	 * 封装PackageInfo
	 * */
	private void createPackageInfo(List<PackageInfo> packageInfosTemp,
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo> packageInfos) {
		
		for(PackageInfo packageInfoTemp:packageInfosTemp){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo packageInfo = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo();
			packageInfo.setTmsCode(packageInfoTemp.getTmsCode());
			packageInfo.setTmsOrderCode(packageInfoTemp.getTmsOrderCode());
			packageInfo.setTmsServiceName(packageInfoTemp.getTmsServiceName());
			//根据时间生成packageCode
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
			String packageCode = "PADX"+sf.format(new Date()).substring(2, 14);
			packageInfo.setPackageCode(packageCode);
			packageInfo.setPackageWeight(packageInfoTemp.getPackageWeight());
			packageInfo.setPackageLength(packageInfoTemp.getPackageLength());
			packageInfo.setPackageWidth(packageInfoTemp.getPackageWidth());
			packageInfo.setPackageHeight(packageInfoTemp.getPackageHeight());
			packageInfo.setPackageVolumn(Long.parseLong(packageInfoTemp.getPackageVolumn()));
			
			List<PackageItem> packageItemsTemp = packageInfoTemp.getPackageItemItems();
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageItem> packageItems = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageItem>();
			for(PackageItem packageItemTemp:packageItemsTemp){
				com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageItem packageItem = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageItem();
				packageItem.setItemId(packageItemTemp.getItem().getItemId());
				packageItem.setItemCode(packageItemTemp.getItemCode());
				packageItem.setItemQuantity(packageItemTemp.getItemQuantity());
				packageItems.add(packageItem);				
			}
			packageInfo.setPackageItemItems(packageItems);
			packageInfos.add(packageInfo);
		}
	}

	/**
	 * 封装StockOutCheckItem
	 * */
	private void createStockOutCheckItem(List<StockOutCheckItem> checkItemsTemp,
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem> checkItems) {	
		for(StockOutCheckItem checkItemTemp : checkItemsTemp ){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem  checkItem  =new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem();
			checkItem.setOrderItemId(checkItemTemp.getOrderItemId());
			checkItem.setQuantity(checkItemTemp.getQuantity());
			checkItems.add(checkItem);
		}	
	}

	/**
	 * 封装StockOutOrderItem
	 * */
	private void createStockOutOrderItem(List<StockOutOrderItem> orderItemsTemp,
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem> orderItems,
			List<Map<String, Object>> parmasList, StockOutOrderConfirm stockOutOrderConfirm) {
		
		for(StockOutOrderItem orderItemTemp : orderItemsTemp){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem  orderItem = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem();
			orderItem.setOrderItemId(orderItemTemp.getOrderItemId());
			
			List<StockOutItemInventory> itemsTemp = orderItemTemp.getItems();
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory>  items =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory>();
			
			String itemId = orderItemTemp.getItem().getId();
			for(StockOutItemInventory itemTemp : itemsTemp){
				com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory item  =new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory();
				item.setInventoryType(Integer.valueOf(itemTemp.getInventoryType().getKey()));
				item.setQuantity(itemTemp.getQuantity());
				item.setBatchCode(itemTemp.getBatchCode());
//				item.setProduceCode();
				item.setSnCode(itemTemp.getSnCode());
//				item.setcod
				//根据时间生成packageCode
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
				String packageCode = "PADX"+sf.format(new Date()).substring(2, 14);
				item.setPackageCode(packageCode);
				items.add(item);
				
				Map<String,Object>  sqlParmas = new HashMap<String, Object>();
				//库存记录表需要
				sqlParmas.put("id", stockOutOrderConfirm.getId());
				sqlParmas.put("inventoryOrderType", ""+stockOutOrderConfirm.getOrderType().getKey());
				//扣除库存用
				sqlParmas.put("itemId", itemId);
				sqlParmas.put("inventoryType", itemTemp.getInventoryType());
				Long num = 0L - Long.valueOf(itemTemp.getQuantity());
				sqlParmas.put("num", num);
				parmasList.add(sqlParmas);

			}
			orderItem.setItems(items);
			orderItems.add(orderItem);
		}
	}
	
//	public Map<String,Object> stockInOrderConfirm(String id){
//		WmsStockOutOrderConfirmCallbackRequest request =  new WmsStockOutOrderConfirmCallbackRequest();
//		
//		StockOutOrder stockOutOrder = stockOUtOrderDao.getStockOutOrderById(id);
//		try {
//			SendSysParams params=new SendSysParams();
//			params.setFromCode(Constants.cainiao_fromCode);
//			buildStockOutOrder(stockOutOrder,request);
//			CaiNiaoPacClient.getClient().send(request, params);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@Transactional
//	private Map<String,Object>  buildStockOutOrder(StockOutOrder stockOutOrder,WmsStockOutOrderConfirmCallbackRequest request)
//			throws Exception{
//		
//		request.setOrderCode(stockOutOrder.getOrderCode());
//		
//		request.setOrderType(Integer.valueOf(stockOutOrder.getOrderType().getKey()));
//		
//		request.setOutBizCode(stockOutOrder.getErpOrderCode());
//		
//		request.setConfirmType(1);
//		
//		request.setOrderConfirmTime(new Date());
//		
//		request.setTimeZone(stockOutOrder.getTimeZone());
//		
//		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem> checkItems = 
//				new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem>();
//		
//		List<WmsStockOutOrderItem> orderItemList = stockOutOrder.getOrderItemList();
//		 
//		for(WmsStockOutOrderItem   orderItem : orderItemList ){
//			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem checkItem  = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem();
//			checkItem.setQuantity(Long.valueOf(""+orderItem.getItemQuantity()));
//			checkItem.setOrderItemId(orderItem.getId());
//			checkItems.add(checkItem);
//		}
//		request.setCheckItems(checkItems);
//		
//		/**
//		 * 目前设置为空  ， 根据业务需求添加
//		 */
//		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo> packageInfos = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo>();
//		com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo  info = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo();
//		packageInfos.add(info);
//		request.setPackageInfos(packageInfos);
//		
//		
//		/**
//		 * 实际需要检测商品
//		 */
//		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem> orderItems  =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem>();
//		
//		List<WmsStockOutOrderItem> orderItemsTemp = stockOutOrder.getOrderItemList();
//		
//		for(WmsStockOutOrderItem orderItemTemp  :orderItemsTemp  ){
//			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem
//			      orderItem  =  new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem();
//			orderItem.setOrderItemId(orderItemTemp.getOrderItemId());
//			
//			
//			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory> 
//			items = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory>();
//			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory
//			item  =new  com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory();
//			item.setInventoryType(Integer.valueOf(orderItemTemp.getInventoryType().getKey()));
//			item.setQuantity(orderItemTemp.getItemQuantity());
//			item.setBatchCode(orderItemTemp.getBatchCode());
//			item.setSnCode("");
//			//根据时间生成packageCode
//			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
//			String packageCode = "PADX"+sf.format(new Date()).substring(2, 14);
//			item.setPackageCode(packageCode);
//			items.add(item);
//			orderItem.setItems(items);
//			orderItems.add(orderItem);
//		}
//		request.setOrderItems(orderItems);
//		
//		return null;
//	}
	
	
	
//	private  void  execute(StockOutOrderConfirm stockOutOrderConfirm){
//		
//		WmsStockOutOrderConfirmCallbackRequest request =  new WmsStockOutOrderConfirmCallbackRequest();
//		
//		request.setOrderCode(stockOutOrderConfirm.getOrderCode());
//		
//		request.setOrderType(Integer.valueOf(stockOutOrderConfirm.getOrderType().getKey()));
//		
//		request.setOutBizCode(stockOutOrderConfirm.getOutBizCode());
//		
//		request.setConfirmType(stockOutOrderConfirm.getConfirmType());
//		
//		request.setOrderConfirmTime(stockOutOrderConfirm.getOrderConfirmTime());
//		
//		request.setTimeZone(stockOutOrderConfirm.getTimeZone());
//		
//		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem> checkItems = 
//				new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem>();
//		
//		List<StockOutCheckItem> checkItemsTemp = stockOutOrderConfirm.getCheckItems();
//		
//		for(StockOutCheckItem  checkItemTemp : checkItemsTemp){
//			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem checkItem  = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutCheckItem();
//			checkItem.setOrderItemId(checkItemTemp.getOrderItemId());
//			checkItem.setQuantity(checkItemTemp.getQuantity());
//			checkItems.add(checkItem);
//		}
//		
//		request.setCheckItems(checkItems);
//		
//		/**
//		 * 目前设置为空  ， 根据业务需求添加
//		 */
//		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo> packageInfos = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo>();
//		com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo  info = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.PackageInfo();
//		packageInfos.add(info);
//		request.setPackageInfos(packageInfos);
//		
//		
//		/**
//		 * 实际需要检测商品
//		 */
//		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem> orderItems  =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem>();
//		
//		List<StockOutOrderItem> orderItemsTemp = stockOutOrderConfirm.getOrderItems();
//		
//		for(StockOutOrderItem orderItemTemp  :orderItemsTemp  ){
//			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem
//			      orderItem  =  new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutOrderItem();
//			orderItem.setOrderItemId(orderItemTemp.getOrderItemId());
//			List<StockOutItemInventory> itemsTemp = orderItemTemp.getItems();
//			
//			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory> 
//				items = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory>();
//			for(StockOutItemInventory itemTemp :  itemsTemp){
//				com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory
//				    item  =new  com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_OUT_ORDER_CONFIRM.StockOutItemInventory();
//				item.setInventoryType(Integer.valueOf(itemTemp.getInventoryType().getKey()));
//				item.setQuantity(itemTemp.getQuantity());
//				item.setBatchCode(itemTemp.getBatchCode());
//				item.setSnCode(itemTemp.getSnCode());
//				item.setPackageCode(itemTemp.getPackageCode());
//				items.add(item);
//			}
//			orderItem.setItems(items);
//			orderItems.add(orderItem);
//		}
//		request.setOrderItems(orderItems);
//	}

}
