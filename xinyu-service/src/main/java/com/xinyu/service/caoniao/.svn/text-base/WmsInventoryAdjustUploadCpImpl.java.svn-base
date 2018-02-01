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
import com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_ADJUST_UPLOAD.InventoryDetail;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_ADJUST_UPLOAD.OrderItem;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_INVENTORY_ADJUST_UPLOAD.WmsInventoryAdjustUploadRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_INVENTORY_ADJUST_UPLOAD.WmsInventoryAdjustUploadResponse;
import com.xinyu.dao.base.CentroDao;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryAdjustDao;
import com.xinyu.dao.inventory.child.InventoryOrderItemDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.inventory.InventoryAdjustUpload;
import com.xinyu.model.inventory.child.InventoryOrderItem;
import com.xinyu.model.inventory.enums.InventoryAdjustStateEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.Constants;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.util.CaiNiaoPacClient;


/**
 * 库存调整接口
 * WMS_INVENTORY_LACK_UPLOAD
 * */
@Component
public class WmsInventoryAdjustUploadCpImpl{
	
	public static final Logger logger = Logger.getLogger(WmsInventoryAdjustUploadCpImpl.class);
	
	@Autowired
	private InventoryAdjustDao adjustDao;
	
	@Autowired
	private CentroDao centroDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private StockOrderOperatorDao operatorDao;
	
	@Autowired
	private InventoryOrderItemDao orderItemDao;
	
	@Autowired
	private  InventoryService  inventoryService;
	
	public  Map<String,Object>  execute(String adjustId, Account account){
		WmsInventoryAdjustUploadRequest request  =new WmsInventoryAdjustUploadRequest();
		InventoryAdjustUpload inventoryAdjust = adjustDao.getInventoryAdjustUploadById(adjustId);
		Map<String,Object>  retMap  = new HashMap<String, Object>();
		try {
			SendSysParams params=new SendSysParams();
			params.setFromCode(Constants.cainiao_fromCode);
			List<Map<String,Object>> sourceList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> targetList = new ArrayList<Map<String,Object>>();
			buildInventoryAdjustUpload(inventoryAdjust, request, sourceList, targetList);
			WmsInventoryAdjustUploadResponse responnse = CaiNiaoPacClient.getClient().send(request, params);
			if(responnse.isSuccess()){
				retMap.put("msg", "上传菜鸟成功");
				retMap.put("code", "200");
				//商品来源仓库减库存
				inventoryService.addNumBatch(sourceList);
				//商品目的仓库加库存
				inventoryService.addNumBatch(targetList);
				
				inventoryAdjust.setState(InventoryAdjustStateEnum.WMS_UP_FINISH);
			}else{
				retMap.put("msg", responnse.getErrorMsg());
				retMap.put("code", "500");
				inventoryAdjust.setState(InventoryAdjustStateEnum.WMS_UP_FAIL);
			}
			this.adjustDao.updateInventoryAdjustUpload(inventoryAdjust);
			
			this.createInventoryAdjustUploadOperator(inventoryAdjust,"库存调整单："+retMap.get("msg"),account);
			
			return  retMap;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
		}
		return retMap;
	}

	/**
	 * 写操作日志
	 * */
	private void createInventoryAdjustUploadOperator(InventoryAdjustUpload inventoryAdjust, String msg, Account account) {
		StockOrderOperator operator = new StockOrderOperator();
		operator.generateId();
		operator.setAccount(account);
		operator.setOperateDate(new Date());
		operator.setOperateType(StockOperateTypeEnum.CONFIRM);
		operator.setOrderId(inventoryAdjust.getId());
		operator.setOrderType(InventoryOrderTypeEnum.INVENTORYADJUST.getDescription());
		operator.setOldValue(InventoryAdjustStateEnum.SAVE.getKey());
		operator.setNewValue(inventoryAdjust.getState().getKey());
		operator.setDescription(msg);
		this.operatorDao.insertStockOrderOperator(operator);
	}

	/**
	 * 封装InventoryAdjustUpload
	 * */
	@Transactional
	private void buildInventoryAdjustUpload(InventoryAdjustUpload inventoryAdjust,WmsInventoryAdjustUploadRequest request, 
			List<Map<String, Object>> sourceList, List<Map<String, Object>> targetList)throws Exception{
		
		Centro centro = this.centroDao.getCentroById(inventoryAdjust.getCentro().getId());
		request.setStoreCode(centro.getStoreCode());
		
		User user = this.userDao.getUserById(inventoryAdjust.getUser().getId());
		request.setOwnerUserId(user.getOwnerCode());
		
		request.setOutBizCode(inventoryAdjust.getOutBizCode());
		
//		request.setImbalanceOrderCode(inventoryAdjust.getImbalanceOrderCode());
//		
//		request.setAdjustReasonType(inventoryAdjust.getAdjustReasonType().getKey());
//		
//		request.setAdjustBizKey(inventoryAdjust.getAdjustBizKey());
//		
//		request.setResponsibilityCode(inventoryAdjust.getResponsibilityCode());
//		
 		request.setOperateTime(inventoryAdjust.getOperateTime());
			
//		request.setInitBatchOrderCode(inventoryAdjust.getInitBatchOrderCode());
//		
//		request.setConfirmType(inventoryAdjust.getConfirmType());
		
		List<OrderItem> itemsList = new ArrayList<OrderItem>();		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("adjustId", inventoryAdjust.getId());
		List<InventoryOrderItem> itemsListTemp = this.orderItemDao.getInventoryOrderItemByList(params);
		//创建InventoryOrderItem
		this.createInventoryOrderItem(itemsListTemp,itemsList,sourceList,targetList,inventoryAdjust);	
		request.setItemList(itemsList);
	
	}

	/**
	 * 封装InventoryOrderItem
	 * */
	private void createInventoryOrderItem(List<InventoryOrderItem> itemsListTemp, List<OrderItem> itemsList,
			List<Map<String, Object>> sourceList, List<Map<String, Object>> targetList, InventoryAdjustUpload inventoryAdjust) {
		
		for(InventoryOrderItem itemTemp:itemsListTemp){
			OrderItem item = new OrderItem();
			Item it = this.itemDao.getItem(itemTemp.getItem().getId()) ;
			item.setItemId(it.getItemId());
			item.setQuantity(itemTemp.getQuantity());
			
			InventoryDetail source = new InventoryDetail();
			source.setInventoryType(itemTemp.getSource());
			item.setSource(source);
			InventoryDetail target = new InventoryDetail();
			target.setInventoryType(itemTemp.getTarget());
			item.setTarget(target);
			itemsList.add(item);
			
			Map<String,Object>  sourceParmas = new HashMap<String, Object>();
			sourceParmas.put("id", inventoryAdjust.getId());
			//库存调整出库
			sourceParmas.put("inventoryOrderType", InventoryOrderTypeEnum.ADJUSTSO.getKey());
			sourceParmas.put("itemId", it.getId());
			sourceParmas.put("inventoryType", InventoryTypeEnum.getInventoryType(""+itemTemp.getSource()));
			Long num1 = 0L;
			num1 = num1 - itemTemp.getQuantity();
			sourceParmas.put("num",num1);
			sourceList.add(sourceParmas);
			
			Map<String,Object>  targetParmas = new HashMap<String, Object>();
			targetParmas.put("id", inventoryAdjust.getId());
			//库存调整入库
			targetParmas.put("inventoryOrderType",InventoryOrderTypeEnum.INVENTORYGRN.getKey());
			targetParmas.put("itemId", it.getId());
			targetParmas.put("inventoryType", InventoryTypeEnum.getInventoryType(""+itemTemp.getTarget()));	
			targetParmas.put("num",(long)itemTemp.getQuantity());
			targetList.add(targetParmas);
			
//			System.err.println("sourceParmas:"+sourceParmas);
//			System.err.println("targetParmas:"+targetParmas);
		}
	}
}
