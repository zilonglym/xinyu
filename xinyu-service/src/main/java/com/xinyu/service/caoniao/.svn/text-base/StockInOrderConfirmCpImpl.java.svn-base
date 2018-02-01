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

import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.WmsStockInOrderConfirmCallbackRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_STOCK_IN_ORDER_CONFIRM.WmsStockInOrderConfirmResponse;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.order.StockInOrderConfirmDao;
import com.xinyu.dao.order.StockInOrderDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockInOrderConfirm;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.child.StockInCheckItem;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.Constants;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.util.CaiNiaoPacClient;


/**
 * @author shark_cj
 * @since  2017-05-03
 *
 * 被动结构请求
 */
@Component
public class StockInOrderConfirmCpImpl {

	public static final Logger logger = Logger.getLogger(StockInOrderConfirmCpImpl.class);
	
	@Autowired
	private StockInOrderDao  stockInOrderDao;
	
	@Autowired
	private ItemDao  itemDao;
	
	@Autowired
	private StockInOrderConfirmDao  stockInOrderConfirmDao;
	
	@Autowired
	private InventoryService  inventoryService;
	
	@Autowired
	private StockOrderOperatorDao  operatorDao;
	
	public Map<String,Object> stockInOrderConfirm(StockInOrderConfirm stockInOrderConfirm){
		
		WmsStockInOrderConfirmCallbackRequest  request  = new WmsStockInOrderConfirmCallbackRequest();
		Map<String,Object>  retMap  = new HashMap<String, Object>();
		
		Account account = SessionUser.get();
		
		try {
			SendSysParams params=new SendSysParams();
			params.setFromCode(Constants.cainiao_fromCode);
//			buildStockInOrder(stockInOrder,request);
			//扣库存所有Map
			List<Map<String,Object>> parmasList = new ArrayList<Map<String,Object>>();
			//菜鸟接口拼接上传
			build(stockInOrderConfirm,request,parmasList);
			WmsStockInOrderConfirmResponse response = CaiNiaoPacClient.getClient().send(request, params);
			if(response.isSuccess()){
				retMap.put("code", "200");
				retMap.put("msg", "菜鸟上传成功");
				//保存入库确认单
				stockInOrderConfirmDao.insertStockInOrderConfirm(stockInOrderConfirm);
				StockInOrder stockInOrder = stockInOrderConfirm.getStockInOrder();
				if(stockInOrderConfirm.getConfirmType()==1){
					stockInOrder.setState(InOrderStateEnum.getInOrderStateEnum("WMS_CONFIRMING"));    //部分上传
				}else if(stockInOrderConfirm.getConfirmType()==0){
					stockInOrder.setState(InOrderStateEnum.getInOrderStateEnum("WMS_CONFIRM_FINISH"));  //全部上传
				}
				inventoryService.addNumBatch(parmasList);
				stockInOrderDao.updateStockInOrder(stockInOrder);
				this.createStockOperator(stockInOrder,account,""+retMap.get("msg"),"success");
			}else{
				StockInOrder stockInOrder = stockInOrderConfirm.getStockInOrder();
				retMap.put("code", "500");
				retMap.put("msg", "菜鸟错误编码:"+response.getErrorCode()+",错误信息:"+response.getErrorMsg());
				this.createStockOperator(stockInOrder,account,""+retMap.get("msg"),"fail");
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
	 * 生成入库单操作日志
	 * @param stockInOrder
	 * @param account 
	 * @param msg
	 * @param isSuccess
	 * */
	private void createStockOperator(StockInOrder stockInOrder, Account account, String msg, String isSuccess) {
		
		StockOrderOperator orderOperator = new StockOrderOperator();
		
		orderOperator.generateId();
		
		orderOperator.setAccount(account);
		
		orderOperator.setOperateDate(new Date());
		
		orderOperator.setOperateType(StockOperateTypeEnum.CONFIRM);
		
		orderOperator.setOrderId(stockInOrder.getId());
		
		orderOperator.setOrderType(stockInOrder.getOrderType().getDescription());
		
		orderOperator.setDescription("单据确认："+stockInOrder.getOrderCode()+"|"+msg);
		
		orderOperator.setNewValue(stockInOrder.getState().getKey());
		
		if (isSuccess.equals("success")) {
			orderOperator.setOldValue(InOrderStateEnum.WMS_CONFIRMWAITING.getKey());
		}else{
			orderOperator.setOldValue(stockInOrder.getState().getKey());
		}
		
		this.operatorDao.insertStockOrderOperator(orderOperator);
	}

	private  void  build(StockInOrderConfirm stockInOrderConfirm,WmsStockInOrderConfirmCallbackRequest request,List<Map<String,Object>> parmaList){
		stockInOrderConfirm.getOrderType();
//		orderItems	List<StockInOrderItem>	　	FALSE	　	入库单商品信息列表	orderLines
		List<StockInOrderItem> orderItemsTemp = stockInOrderConfirm.getOrderItems();
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem> orderItems =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem>();
	
		//商品入库明细
		for(StockInOrderItem orderItemTemp : orderItemsTemp){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem  orderItem = new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem();
			
			orderItem.setOrderItemId(orderItemTemp.getOrderItemId());
			orderItem.setWeight(orderItemTemp.getWeight());
			orderItem.setVolume(orderItemTemp.getVolume());
			orderItem.setLength(orderItemTemp.getLength());
			orderItem.setHeight(orderItemTemp.getHeight());
			orderItem.setWidth(orderItemTemp.getWidth());
		
			List<StockInItemInventory> itemsTemp = orderItemTemp.getItems();//商品入库类型
			
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory>  items =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory>();
			
			String itemId = orderItemTemp.getItem().getId();//商品ID  
			for(StockInItemInventory itemTemp : itemsTemp){ //单一商品不同入库类型
				com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory item  =new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory();
				item.setInventoryType(Integer.valueOf(itemTemp.getInventoryType().getKey()));
				item.setQuantity(itemTemp.getQuantity());
				item.setBatchCode(itemTemp.getBatchCode());
//				item.setProduceCode();
				item.setSnCode(itemTemp.getSnCode());
//				item.setcod
				items.add(item);
				
				Map<String,Object>  sqlParmas = new HashMap<String, Object>();
				//库存记录表需要
				sqlParmas.put("id", stockInOrderConfirm.getId());
				sqlParmas.put("inventoryOrderType", ""+stockInOrderConfirm.getOrderType().getKey());
				//扣除库存用
				sqlParmas.put("itemId", itemId);
				sqlParmas.put("inventoryType", itemTemp.getInventoryType());
				sqlParmas.put("num", Long.valueOf(itemTemp.getQuantity()));
				parmaList.add(sqlParmas);
			}
			orderItem.setItems(items);
			orderItems.add(orderItem);
		}
		request.setOrderItems(orderItems);
		
		request.setOrderCode(stockInOrderConfirm.getOrderCode());
//		orderCode	string	64	TRUE	LBX12345	仓储中心入库订单编码	新增，菜鸟订单号
//		orderType	int	11	TRUE	302	单据类型： 302调拨入库单 501销退入库单 601采购入库单 904普通入库单306 B2B入库单604	B2B干线退货入库
//		704	库存状态调整入库	新增，对应菜鸟下发的orderType
		request.setOrderType(Integer.valueOf(""+stockInOrderConfirm.getOrderType().getKey()));
		
		//		outBizCode	string	64	TRUE	***	外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样	outBizCode
		
		request.setOutBizCode(stockInOrderConfirm.getOutBizCode());
		//		confirmType	int	11	TRUE	0	支持入库单多次确认(如：每上架一次就立刻回传) 0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0	confirmType
//		同一入库单;如果先收到0;后又收到1，不允许修改收货的数量)
		request.setConfirmType(stockInOrderConfirm.getConfirmType());
		
//		orderConfirmTime	date	　	TRUE	　	仓库入库单完成时间	operateTime
		request.setOrderConfirmTime(stockInOrderConfirm.getOrderConfirmTime());

		//		timeZone	string	　16	FALSE	　	时区	新增
		request.setTimeZone(stockInOrderConfirm.getTimeZone());
		
//		tmsServiceCode	string	64	FALSE	　	配送编码	logisticsCode
//		tmsServiceName	string	64	FALSE	　	配送公司名称	logisticsName
//		tmsOrderCode	string	64	FALSE	　	运单号，指定运单号发货业务	expressCode
		
		
//		returnReason	string	64	FALSE	***	退货原因：销退场景下，如可能请提供退货的原因，多个退货原因用；号分开	returnReason
		
		
//		checkItems	List<StockInCheckItem>	　	FALSE	　	入库单商品校验信息列表	新增，分批的最后一批时必传，此节点是所有回传报文货品数量汇总。
		List<StockInCheckItem> checkItemsTemp  =  stockInOrderConfirm.getCheckItems();
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem>  checkItems = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem>();
		
		for(StockInCheckItem checkItemTemp : checkItemsTemp ){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem  checkItem  =new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem();
			checkItem.setOrderItemId(checkItemTemp.getOrderItemId());
			checkItem.setQuantity(checkItemTemp.getQuantity());
			checkItems.add(checkItem);
		}
		request.setCheckItems(checkItems);
	}
	
	
	/**
	 * 接口调试用,
	 * 目前暂不使用
	 * @param 
	 * @param 
	 * @return
	 */
	private Map<String,Object>  buildStockInOrder(StockInOrder stockInorder, WmsStockInOrderConfirmCallbackRequest request){

		Map<String,Object>  sqlParam = new HashMap<String, Object>();
		sqlParam.put("stockInOrderId", stockInorder.getId());
		List<WmsStockInOrderItem> orderItemList = stockInorder.getOrderItemList();
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem> orderItems =  new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem>();
		for(WmsStockInOrderItem orderItem :  orderItemList){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem item  =new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInOrderItem();
			
			item.setOrderItemId(orderItem.getOrderItemId());
			System.out.println("orderItem.getItem().getId():"+orderItem.getItem().getId());
			Item itemTemp = itemDao.getItem(orderItem.getItem().getId());
			item.setItemId(itemTemp.getItemId());
			item.setHeight(itemTemp.getWmsHeight().intValue());
			item.setVolume(itemTemp.getVolume().intValue());
			item.setLength(itemTemp.getLength().intValue());
			item.setWeight(itemTemp.getWmsGrossWeight());
			item.setWidth(itemTemp.getWidth().intValue());
//			                 缺少字段 [orderItems[0].volume]
//					缺少字段 [orderItems[0].length]
//					缺少字段 [orderItems[0].width]
//					缺少字段 [orderItems[0].weight]
//					缺少字段 [orderItems[0].height]
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory> itemInventoryList = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory>();
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory itemInventory =  new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInItemInventory();
			itemInventory.setQuantity(orderItem.getItemQuantity());
			//入库确认后， 此商品为1
			itemInventory.setInventoryType(Integer.valueOf("1"));
			itemInventoryList.add(itemInventory);
			
			
			item.setItems(itemInventoryList);
			orderItems.add(item);
		}
		request.setOrderItems(orderItems);
		
		
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem>  checkItems = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem>();
		
		for(WmsStockInOrderItem checkItemTemp : orderItemList ){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem  checkItem  =new com.taobao.pac.sdk.cp.dataobject.request.WMS_STOCK_IN_ORDER_CONFIRM.StockInCheckItem();
			checkItem.setOrderItemId(checkItemTemp.getOrderItemId());
			checkItem.setQuantity(Long.valueOf(checkItemTemp.getItemQuantity()));
			checkItems.add(checkItem);
		}
		request.setCheckItems(checkItems);
		
		request.setOrderCode(stockInorder.getOrderCode());
//		orderCode	string	64	TRUE	LBX12345	仓储中心入库订单编码	新增，菜鸟订单号
//		orderType	int	11	TRUE	302	单据类型： 302调拨入库单 501销退入库单 601采购入库单 904普通入库单306 B2B入库单604	B2B干线退货入库
//		704	库存状态调整入库	新增，对应菜鸟下发的orderType
		request.setOrderType(Integer.valueOf(""+stockInorder.getOrderType().getKey()));
		
		//		outBizCode	string	64	TRUE	***	外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样	outBizCode
		request.setOutBizCode(stockInorder.getOrderCode());
		//		confirmType	int	11	TRUE	0	支持入库单多次确认(如：每上架一次就立刻回传) 0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0	confirmType
//		同一入库单;如果先收到0;后又收到1，不允许修改收货的数量)
		request.setConfirmType(0);
		
//		orderConfirmTime	date	　	TRUE	　	仓库入库单完成时间	operateTime
		request.setOrderConfirmTime(new Date());

		//		timeZone	string	　16	FALSE	　	时区	新增
		request.setTimeZone(stockInorder.getTimeZone());
		
		return null;
	}
}
