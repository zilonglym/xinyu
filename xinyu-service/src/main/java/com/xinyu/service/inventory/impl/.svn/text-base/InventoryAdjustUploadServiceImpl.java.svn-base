package com.xinyu.service.inventory.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinyu.dao.base.CentroDao;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryAdjustDao;
import com.xinyu.dao.inventory.child.InventoryOrderItemDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.InventoryAdjustUpload;
import com.xinyu.model.inventory.child.InventoryOrderItem;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.inventory.enums.InventoryAdjustStateEnum;
import com.xinyu.model.inventory.enums.InventoryAdjustTypeEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.inventory.InventoryAdjustUploadService;

/**
 * 库存调整业务接口
 * */
@Service("inventoryAdjustUploadServiceImpl")
public class InventoryAdjustUploadServiceImpl extends BaseServiceImpl implements InventoryAdjustUploadService{

	@Autowired
	private InventoryAdjustDao adjustDao;
	
	@Autowired
	private InventoryOrderItemDao orderItemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CentroDao centroDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private StockOrderOperatorDao operatorDao;
	
	/**
	 * 保存库存调整单
	 * @param map
	 * @return map
	 * */
	@Transactional
	@Override
	public Map<String, Object> saveInventoryAdjust(Map<String, Object> params) {
		
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
		InventoryAdjustUpload adjustUpload = new InventoryAdjustUpload();
		adjustUpload.generateId();
		
		//单据状态
		adjustUpload.setState(InventoryAdjustStateEnum.SAVE);
		
		//单据类型
		String orderType = "" + data.get("orderType");
		adjustUpload.setOrderType(InventoryAdjustTypeEnum.getInventoryAdjustTypeEnum(orderType));
		
		//单据对应的商家
		String userId = "" + data.get("userId");
		User user = this.userDao.getUserById(userId);
		adjustUpload.setUser(user);
		
		//单据操作的仓库
		String centroId = "" + data.get("centroId");
		Centro centro = this.centroDao.getCentroById(centroId);
		adjustUpload.setCentro(centro);
		
		Date date = new Date();
		String str = "LBX" + String.valueOf(date.getTime());
		
		adjustUpload.setAdjustBizKey(str);
		
		adjustUpload.setOutBizCode(String.valueOf(date.getTime()));
		
		String reasonType = ""+data.get("reasonType");
		adjustUpload.setAdjustReasonType(AdjustReasonTypeEnum.getAdjustReasonTypeEnum(reasonType));
		
		adjustUpload.setResponsibilityCode(str);
		
		adjustUpload.setImbalanceOrderCode(str);
		
		adjustUpload.setOperateTime(date);
		
		String InitBatchOrderCode = "LBZ" + String.valueOf(date.getTime());
		adjustUpload.setInitBatchOrderCode(InitBatchOrderCode);
		
//		String confirmType = "" + data.get("confirmType");
//		adjustUpload.setConfirmType(Integer.parseInt(confirmType));
		
		String remark = "" + data.get("remark");
		adjustUpload.setRemark(remark);
		
		List<InventoryOrderItem> orderItems = this.createInventoryOrderItems(adjustUpload,dataList);
		
		adjustUpload.setItems(orderItems);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			this.insertInventoryAdjustUpload(adjustUpload);
			retMap.put("msg", "success");			
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", e.getMessage());
		}
		
		this.createInventoryAdjustOperator(adjustUpload,"库存调整单："+retMap.get("msg"));
		
		return retMap;
	}
	
	/**
	 * 保存单据及明细
	 * @param inventoryAdjust
	 * */
	private void insertInventoryAdjustUpload(InventoryAdjustUpload inventoryAdjust) {
		
		//如果未设置单据状态,默认状态 为 ：本地保存
		if(inventoryAdjust.getState()==null){
			inventoryAdjust.setState(InventoryAdjustStateEnum.getInventoryAdjustStateEnum("SAVE"));
		}
		this.adjustDao.insertInventoryAdjustUpload(inventoryAdjust);
		
		//单据明细持久化
		List<InventoryOrderItem> items = inventoryAdjust.getItems();
		for(InventoryOrderItem  item :  items){
			this.orderItemDao.insertInventoryOrderItem(item);
		}	
	}

	/**
	 * 写操作日志
	 * @param InventoryAdjustUpload
	 * @param msg
	 * */
	private void createInventoryAdjustOperator(InventoryAdjustUpload adjustUpload, String msg) {
		StockOrderOperator operator = new StockOrderOperator();
		operator.generateId();
		Account account = SessionUser.get();
		operator.setAccount(account);
		operator.setOperateDate(new Date());
		operator.setOperateType(StockOperateTypeEnum.CREATE);
		operator.setOrderId(adjustUpload.getId());
		operator.setOrderType(InventoryOrderTypeEnum.INVENTORYADJUST.getDescription());
		operator.setOldValue("");
		operator.setNewValue(adjustUpload.getState().getKey());
		operator.setDescription(msg);
		this.operatorDao.insertStockOrderOperator(operator);
	}

	/**
	 * 创建库存调整单据明细
	 * @param InventoryAdjustUpload
	 * @param dataList
	 * @return list
	 * */
	private List<InventoryOrderItem> createInventoryOrderItems(InventoryAdjustUpload adjustUpload,
			List<Map<String, Object>> dataList) {
		List<InventoryOrderItem> orderItems = new ArrayList<InventoryOrderItem>();
		for(Map<String, Object> map:dataList){
			InventoryOrderItem orderItem = new InventoryOrderItem();
			orderItem.generateId();
			orderItem.setInventoryAdjustUpload(adjustUpload);
			
			String itemId = "" + map.get("itemId");
			Item item = this.itemDao.getItem(itemId);
			orderItem.setItem(item);
			
			orderItem.setItemCode(item.getItemCode());
			
			String num = "" + map.get("num");
			orderItem.setQuantity(Integer.parseInt(num));
			
			if (adjustUpload.getOrderType().equals(InventoryAdjustTypeEnum.FORWORDDEFECTIVE)) {
				//正转残
				orderItem.setSource(1);
				orderItem.setTarget(101);
			}else if(adjustUpload.getOrderType().equals(InventoryAdjustTypeEnum.FORWORDNORMAL)){
				//残转正
				orderItem.setSource(101);
				orderItem.setTarget(1);
			}
			orderItems.add(orderItem);
		}
		return orderItems;
	}

	@Override
	public List<InventoryAdjustUpload> getInventoryAdjustUploadListByParams(Map<String, Object> params) {
		return this.adjustDao.getInventoryAdjustUploadListByParams(params);
	}

	@Override
	public int getToTal(Map<String, Object> params) {
		return this.adjustDao.getToTal(params);
	}

	@Override
	public List<Map<String, Object>> buildListData(List<InventoryAdjustUpload> adjustUploads) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(InventoryAdjustUpload adjustUpload:adjustUploads){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", adjustUpload.getId());
			map.put("outBizCode", adjustUpload.getOutBizCode());
			map.put("adjustReasonType", adjustUpload.getAdjustReasonType().getDescription());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("operateTime", sf.format(adjustUpload.getOperateTime()));
			map.put("confirmType", adjustUpload.getConfirmType());
			map.put("orderType", adjustUpload.getOrderType().getDescription());
			map.put("remark", adjustUpload.getRemark());
			resultList.add(map);
		}
		return resultList;
	}

	@Override
	public InventoryAdjustUpload getInventoryAdjustUploadListById(String id) {
		return adjustDao.getInventoryAdjustUploadById(id);
	}

}
