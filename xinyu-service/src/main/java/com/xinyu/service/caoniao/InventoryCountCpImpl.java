package com.xinyu.service.caoniao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.WmsInventoryCountCallBackRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_INVENTORY_COUNT.WmsInventoryCountCallBackResponnse;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryCountDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
import com.xinyu.model.inventory.enums.InventoryCountStateEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.Constants;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.util.CaiNiaoPacClient;


/**
 * @author shark_cj
 * @since  2017-04-30
 * 
 * 盘点单接口
 */
@Component
public class InventoryCountCpImpl  {

	public static final Logger logger = Logger.getLogger(InventoryCountCpImpl.class);
	
	@Autowired
	private  UserDao userDao;
	
	@Autowired
	private  ItemService itemService;
	
	@Autowired
	private StockOrderOperatorDao  operatorDao;
	
	@Autowired 
	private InventoryCountDao inventoryCountDao;
	
	@Autowired
	private  InventoryService  inventoryService;
	
	public  Map<String,Object>  execute(String inventoryCountId, Account account){
		WmsInventoryCountCallBackRequest request  =new WmsInventoryCountCallBackRequest();
		InventoryCount inventoryCount = inventoryCountDao.getInventoryCountById(inventoryCountId);
		Map<String,Object>  retMap  = new HashMap<String, Object>();
		try {
			SendSysParams params=new SendSysParams();
			params.setFromCode(Constants.cainiao_fromCode);
			List<Map<String,Object>> parmasList = new ArrayList<Map<String,Object>>();
			buildInventoryCount(inventoryCount, request, parmasList);
			WmsInventoryCountCallBackResponnse responnse = CaiNiaoPacClient.getClient().send(request, params);
			if(responnse.isSuccess()){
				retMap.put("msg", "上传菜鸟成功");
				retMap.put("code", "200");
				System.err.println(parmasList);
				inventoryService.addNumBatch(parmasList);
				inventoryCount.setState(InventoryCountStateEnum.WMS_UP_FINISH);
				this.inventoryCountDao.updateInventoryCount(inventoryCount);
				this.createStockOperator(inventoryCount,account,StockOperateTypeEnum.CONFIRM,""+retMap.get("msg"));
			}else{
				retMap.put("msg", responnse.getErrorMsg());
				retMap.put("code", "500");
				inventoryCount.setState(InventoryCountStateEnum.WMS_UP_FAIL);
				this.inventoryCountDao.updateInventoryCount(inventoryCount);
				this.createStockOperator(inventoryCount,account,StockOperateTypeEnum.CONFIRM,""+retMap.get("msg"));
			}
			return  retMap;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
			inventoryCount.setState(InventoryCountStateEnum.WMS_UP_FAIL);
			this.inventoryCountDao.updateInventoryCount(inventoryCount);
		}
		return retMap;
	}
	
	/**
	 * 盘点单日志
	 * @param inventoryCount
	 * @param account
	 * @param StockOperateTypeEnum
	 * @param msg
	 * */
	private void createStockOperator(InventoryCount inventoryCount, Account account, StockOperateTypeEnum type,
			String msg) {
		StockOrderOperator operator = new StockOrderOperator();
		
		operator.generateId();
			
//		operator.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
			
		operator.setAccount(account);
		
		operator.setOperateDate(new Date());
		
		operator.setOperateType(type);
		
		operator.setNewValue(inventoryCount.getState().getKey());
		
		operator.setOldValue(InventoryCountStateEnum.SAVE.getKey());
		
		operator.setOrderId(inventoryCount.getCheckOrderCode());
		
		operator.setOrderType(inventoryCount.getOrderType().getDescription());
			
		operator.setDescription("单据确认："+inventoryCount.getCheckOrderCode()+"|"+msg);	
		
		this.operatorDao.insertStockOrderOperator(operator);
	}


	private void  buildInventoryCount(InventoryCount inventoryCount ,WmsInventoryCountCallBackRequest request, List<Map<String, Object>> parmasList){
		
		InventoryOrderTypeEnum orderType = inventoryCount.getOrderType();
		
		User user = inventoryCount.getUser();
		
		user  =userDao.getUserById(user.getId());
		
		request.setOwnerUserId(user.getOwnerCode());
		request.setStoreCode(inventoryCount.getStoreCode());
		
		request.setOrderType(Integer.valueOf(inventoryCount.getOrderType().getKey()));
		request.setOutBizCode(inventoryCount.getOutBizCode());
		request.setImbalanceOrderCode(inventoryCount.getImbalanceOrderCode());
		request.setAdjustReasonType(inventoryCount.getAdjustReasonType().getKey());
		request.setAdjustBizKey(inventoryCount.getAdjustBizKey());
		request.setRemark(inventoryCount.getRemark());
		request.setResponsibilityCode(inventoryCount.getResponsibilityCode());
		request.setOperateTime(inventoryCount.getOperateTime());
		
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.InventoryCountReturnOrderItem> itemList  = new ArrayList<com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.InventoryCountReturnOrderItem>();
		List<InventoryCountReturnOrderItem> itemListTemp = inventoryCount.getItemList();
		
		for(InventoryCountReturnOrderItem itemTemp : itemListTemp){
			com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.InventoryCountReturnOrderItem item = new com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.InventoryCountReturnOrderItem();
			Item it = this.itemService.getItem(itemTemp.getItem().getId()); 
			item.setItemId(it.getItemId());
			item.setDetailOutBizCode(itemTemp.getDetailOutBizCode());
			item.setItemCode(itemTemp.getItemCode());
			item.setInventoryType(Integer.valueOf(itemTemp.getInventoryType().getKey()));
			item.setQuantity(itemTemp.getQuantity());
			item.setBatchCode(itemTemp.getBatchCode());
			item.setDueDate(itemTemp.getDueDate());
			item.setProduceCode(itemTemp.getProduceCode());
			item.setRemark(itemTemp.getRemark());
			itemList.add(item);
			
			Map<String,Object>  sqlParmas = new HashMap<String, Object>();
			//库存记录表需要
			sqlParmas.put("id", inventoryCount.getId());
			sqlParmas.put("inventoryOrderType", ""+inventoryCount.getOrderType().getKey());
			//扣除库存用
			sqlParmas.put("itemId", itemTemp.getItem().getId());
			sqlParmas.put("inventoryType", itemTemp.getInventoryType());
			
			/**
			 * 库存盘点单
			 * 盘盈INVENTORYSURPLUS：num为正数；
			 * 盘亏INVENTORYLOSSES：num为负数；
			 * */
			Long num = 0L;
			if (orderType.equals(InventoryOrderTypeEnum.INVENTORYLOSSES)) {
				num = num - Long.valueOf(itemTemp.getQuantity());
			}else if(orderType.equals(InventoryOrderTypeEnum.INVENTORYSURPLUS)){
				num = num + Long.valueOf(itemTemp.getQuantity());
			}
	
			sqlParmas.put("num", num);
			parmasList.add(sqlParmas);
		
		}
	
		request.setItemList(itemList);	
	}
	
//	@Override
//	public WmsInventoryCountCallBackResponnse execute(ReceiveSysParams params, WmsInventoryCountCallBackRequest request) {
//		InventoryCount inventoryCount  = new InventoryCount();
//    	try {
//    		buildInventoryCount(inventoryCount, request);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    	WmsInventoryCountCallBackResponnse response = new WmsInventoryCountCallBackResponnse();
//		response.setSuccess(true);// 业务成功
//		
//		
//		return response;
//	}
//	
//	private  Map<String,Object>  buildInventoryCount(InventoryCount inventoryCount,WmsInventoryCountCallBackRequest request){
//		Map<String,Object>  retMap  = new HashMap<String,Object>();
//		
//		String userId = request.getOwnerUserId();
//		User user = userService.getUserById(userId);
//    	if(user==null){
//    		retMap.put("code", "404");
//    		retMap.put("msg", "ownerUserId:【"+userId+"】无法匹配商家信息");
//    		return   retMap;
//    	}
//    	
//    	inventoryCount.setUser(user);
//    	inventoryCount.setStoreCode(request.getStoreCode());
//    	
//    	InventoryOrderTypeEnum inventoryOrderType = InventoryOrderTypeEnum.getInventoryOrderTypeEnum(""+request.getOrderType());
//    	inventoryCount.setOrderType(inventoryOrderType);
//    	
//    	inventoryCount.setOutBizCode(request.getOutBizCode());
//		inventoryCount.setImbalanceOrderCode(request.getImbalanceOrderCode());
////		inventoryCount.setOperateTime(request.getop);
//		
//		AdjustReasonTypeEnum adjustReasonType = AdjustReasonTypeEnum.getAdjustReasonTypeEnum(""+request.getAdjustReasonType());
//    	inventoryCount.setAdjustReasonType(adjustReasonType);
//    	inventoryCount.setAdjustBizKey(request.getAdjustBizKey());
//    	inventoryCount.setRemark(request.getRemark());
//    	inventoryCount.setResponsibilityCode(request.getResponsibilityCode());
//    	
//    	
//    	List<com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.InventoryCountReturnOrderItem> countItemList = request.getItemList();
//    	List<InventoryCountReturnOrderItem>  countItemListTemp = new  ArrayList<InventoryCountReturnOrderItem>();
//    	for(com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_COUNT.InventoryCountReturnOrderItem countItem:countItemList){
//    		InventoryCountReturnOrderItem  countItemTemp  = new InventoryCountReturnOrderItem();
//    		countItemTemp.generateId();
//    		Item item = itemService.getItem(countItem.getItemId());
//    		countItemTemp.setItem(item);
//    		
//    		countItemTemp.setDetailOutBizCode(countItem.getDetailOutBizCode());
//    		countItemTemp.setItemCode(countItem.getItemCode());
//    		
//    		InventoryTypeEnum inventoryType = InventoryTypeEnum.getInventoryType(""+countItem.getInventoryType());
//    		countItemTemp.setInventoryType(inventoryType);
//    		
//    		countItemTemp.setQuantity(countItem.getQuantity());
//    		countItemTemp.setBatchCode(countItem.getBatchCode());
////    		countItemTemp.setSnCode(countItem.getsn);
//    		countItemTemp.setDueDate(countItem.getDueDate());
//    		countItemTemp.setProduceCode(countItem.getProduceCode());
//    		countItemTemp.setRemark(countItem.getRemark());
//    		countItemTemp.setInventoryCount(inventoryCount);
//    		countItemListTemp.add(countItemTemp);
//    	}
//    	inventoryCount.setItemList(countItemListTemp);
//    	inventoryCount.generateId();
//    	retMap.put("code", "200");
//    	return retMap;
//	}

}
