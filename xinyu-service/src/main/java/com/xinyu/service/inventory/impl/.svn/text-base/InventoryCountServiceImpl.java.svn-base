package com.xinyu.service.inventory.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.pac.sdk.cp.dataobject.request.DMS_CUSTOM_SORTING_CONFIRM.order_point;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.SystemItemDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryCountDao;
import com.xinyu.dao.inventory.child.InventoryCountReturnOrderItemDao;
import com.xinyu.dao.order.StockOrderOperatorDao;
import com.xinyu.model.base.Centro;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.inventory.enums.InventoryCountStateEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.SystemItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.common.Constants;
import com.xinyu.service.inventory.InventoryCountService;

/**
 * 库存盘点单业务处理接口
 * */
@Service("inventoryCountServiceImpl")
public class InventoryCountServiceImpl extends BaseServiceImpl implements InventoryCountService{

	@Autowired
	private InventoryCountDao inventoryCountDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SystemItemDao sysDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private StockOrderOperatorDao  operatorDao;
	
	@Autowired
	private  InventoryCountReturnOrderItemDao  inventoryCountReturnOrderItemDao;
	
	/**
	 * 保存库存盘点单
	 * @param inventoryCount
	 */
	@Override
	@Transactional
	public void   insertInventoryCount(InventoryCount inventoryCount){
		
		List<InventoryCountReturnOrderItem> itemList = inventoryCount.getItemList();
		inventoryCountDao.insertInventoryCount(inventoryCount);
		for(InventoryCountReturnOrderItem item: itemList ){
			
			inventoryCountReturnOrderItemDao.saveInventoryCountReturnOrderItem(item);
		}
	}
	
	
	/**
	 * 分页查询盘点单
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<InventoryCount> findInventoryCountsByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return this.inventoryCountDao.findInventoryCountsByPage(params,page,rows);
	}

	/**
	 * 查询库存盘点单
	 * @param params
	 * @return list
	 * */
	@Override
	public List<InventoryCount> findInventoryCountsByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.inventoryCountDao.findInventoryCountsByList(params);
	}

	/**
	 * 计数库存盘点单
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.inventoryCountDao.getTotal(params);
	}

	/**
	 * 库存盘点单数据重组
	 * @param inventoryCounts
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<InventoryCount> inventoryCounts) {
		// TODO Auto-generated method stub
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(InventoryCount inventoryCount:inventoryCounts){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", inventoryCount.getId());
			map.put("orderType", inventoryCount.getOrderType().getDescription());
			User user = this.userDao.getUserById(inventoryCount.getUser().getId());
			map.put("user", user.getSubscriberName());
			map.put("checkOrderCode", inventoryCount.getCheckOrderCode());
			map.put("adjustReasonType", inventoryCount.getAdjustReasonType().getDescription());
			map.put("operateTime", sf.format(inventoryCount.getOperateTime()));
			map.put("remark", inventoryCount.getRemark());
			map.put("status", inventoryCount.getState().getKey());
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * 根据Id查盘点单
	 * @param id
	 * @return InventoryCount
	 * */
	@Override
	public InventoryCount getInventoryCountById(String id) {
		InventoryCount inventoryCount = this.inventoryCountDao.getInventoryCountById(id);
		List<InventoryCountReturnOrderItem> itemList = inventoryCountReturnOrderItemDao.getInventoryCountReturnOrderItemByInventoryCountId(id);
		inventoryCount.setItemList(itemList);
		return inventoryCount;
	}

	/**
	 * 盘点单商品明细数据
	 * @param orderItems
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildDetailListData(List<InventoryCountReturnOrderItem> orderItems) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(InventoryCountReturnOrderItem orderItem:orderItems){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", orderItem.getId());
			map.put("itemName", orderItem.getItem().getName());
			map.put("itemCode", orderItem.getItemCode());
			map.put("itemSku", orderItem.getItem().getColor());
			map.put("barCode", orderItem.getItem().getBarCode());
			map.put("type", orderItem.getInventoryType().getDescription());
			map.put("num", orderItem.getQuantity());
			resultList.add(map);
		}
		return resultList;
	}


	@Override
	public void updateInventoryCount(InventoryCount inventoryCount) {
		List<InventoryCountReturnOrderItem> itemList = inventoryCount.getItemList();
		inventoryCountDao.updateInventoryCount(inventoryCount);
		for(InventoryCountReturnOrderItem item: itemList ){
			inventoryCountReturnOrderItemDao.updateInventoryCountReturnOrderItem(item);
		}
	}


	/**
	 * 创建盘点单
	 * @param data
	 * @param rows
	 * @return map
	 * */
	@Override
	public Map<String, Object> saveInventoryCount(Map<String, Object> map, String type) {
		
		Map<String, Object> data = (Map<String, Object>) map.get("data");
		
		List<Map<String, Object>> rows = (List<Map<String, Object>>) map.get("dataList");
		
		Account account = (Account) map.get("account");
		
		InventoryCount inventoryCount = new InventoryCount();
		
		if (type.equals("insert")) {
			inventoryCount.generateId();
		}else if(type.equals("update")){
			inventoryCount = this.inventoryCountDao.getInventoryCountById(""+data.get("id"));
		}
		
		Object userId = data.get("userId");
		User user = this.userDao.getUserById(String.valueOf(userId));
		inventoryCount.setUser(user);
		
		inventoryCount.setStoreCode(Constants.cainiao_fromCode);		
		Object checkOrderCode = data.get("checkOrderCode");
		
		inventoryCount.setCheckOrderCode(String.valueOf(checkOrderCode));
		Object outBizCode = data.get("outBizCode");
		
 		inventoryCount.setOutBizCode(String.valueOf(outBizCode));
//		Object imbalanceOrderCode = data.get("imbalanceOrderCode");
		
 		inventoryCount.setImbalanceOrderCode("");
		Object orderType = data.get("orderType");
		InventoryOrderTypeEnum[] types = InventoryOrderTypeEnum.values();
		for(int i=0;i<types.length;i++){
			if (types[i].getKey().equals(orderType)) {
				inventoryCount.setOrderType(types[i]);
			}
		}
		
		Object reason = data.get("reason");
		AdjustReasonTypeEnum[] reasons = AdjustReasonTypeEnum.values();
		for(int i=0;i<reasons.length;i++){
			if (reasons[i].getKey().equals(reason)) {
				inventoryCount.setAdjustReasonType(reasons[i]);
			}
		}
		
		Object responsibilityCode = data.get("responsibilityCode");
		inventoryCount.setResponsibilityCode(String.valueOf(responsibilityCode));
		
		Object adjustBizKey = data.get("adjustBizKey");
		inventoryCount.setAdjustBizKey(String.valueOf(adjustBizKey));
		
		Object remark = data.get("remark");
		inventoryCount.setRemark(String.valueOf(remark));
		
		inventoryCount.setOperateTime(new Date());
		
// 		inventoryCount.setCu("");
		
		inventoryCount.setState(InventoryCountStateEnum.SAVE);
		
		List<InventoryCountReturnOrderItem> orderItems = this.createOrderItems(data,rows,inventoryCount);	
		inventoryCount.setItemList(orderItems);	
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (type.equals("insert")) {
			System.err.println("-----------");
			this.inventoryCountDao.insertInventoryCount(inventoryCount);
			retMap.put("ret", "success");
			this.createStockOperator(inventoryCount,account,StockOperateTypeEnum.CREATE,""+retMap.get("ret"));
		}else if(type.equals("update")){
			this.inventoryCountDao.updateInventoryCount(inventoryCount);
			retMap.put("ret", "success");
			this.createStockOperator(inventoryCount,account,StockOperateTypeEnum.EDIT,""+retMap.get("ret"));
		}
		return retMap;
		
	}


	/**
	 * 盘点单创建修改日志
	 * @param inventoryCount
	 * @param account
	 * @param type
	 * @param msg
	 * */
	private void createStockOperator(InventoryCount inventoryCount, Account account, StockOperateTypeEnum type, String msg) {
			
		StockOrderOperator operator = new StockOrderOperator();
			
		operator.generateId();
			
//		operator.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
			
		operator.setAccount(account);
		
		operator.setOperateDate(new Date());
		
		operator.setOperateType(type);
		
		operator.setNewValue(inventoryCount.getState().getKey());
		
		operator.setOldValue(inventoryCount.getState().getKey());
		
		operator.setOrderId(inventoryCount.getId());
		
		operator.setOrderType(inventoryCount.getOrderType().getDescription());
		
		if (type.equals(StockOperateTypeEnum.EDIT)) {		
			operator.setDescription("单据修改："+inventoryCount.getCheckOrderCode()+"|"+msg);	
		}else if(type.equals(StockOperateTypeEnum.CREATE)){
			operator.setDescription("单据新建："+inventoryCount.getCheckOrderCode()+"|"+msg);			
		}
		
		this.operatorDao.insertStockOrderOperator(operator);
	}


	/**
	 * 创建明细单据list
	 * @param data
	 * @param rows
	 * @param InventoryCount
	 * @return list
	 * */
	private List<InventoryCountReturnOrderItem> createOrderItems(Map<String, Object> data, List<Map<String, Object>> rows,InventoryCount inventoryCount) {
		System.err.println("rows:"+rows);
		List<InventoryCountReturnOrderItem> orderItems = new ArrayList<InventoryCountReturnOrderItem>();
		for(Map<String, Object> jsonObj : rows){
			String itemId = ""+jsonObj.get("itemId");
			String num1 = ""+jsonObj.get("num1");//库存可销售商品数
			String num2 = ""+jsonObj.get("num2");//残品
			String num3 = ""+jsonObj.get("num3");//机损
			String num4 = ""+jsonObj.get("num4");//箱损
			Item item = this.itemDao.getItem(itemId);
			
			//库存可销售商品数
			if (num1!=null&&num1.equals("null")==false&&num1.equals("")==false){
				InventoryCountReturnOrderItem orderItem = this.createOrderItem(data,num1,inventoryCount,item,InventoryTypeEnum.NORMAL);
 				orderItems.add(orderItem);
			}
			
			//库存残次
			if (num2!=null&&num2.equals("null")==false&&num2.equals("")==false) {
				InventoryCountReturnOrderItem orderItem = this.createOrderItem(data,num2,inventoryCount,item,InventoryTypeEnum.DEFECTIVE);
 				orderItems.add(orderItem);
			}
			
			//库存机损
			if (num3!=null&&num3.equals("null")==false&&num3.equals("")==false) {
				InventoryCountReturnOrderItem orderItem = this.createOrderItem(data,num3,inventoryCount,item,InventoryTypeEnum.MECHANICAL);
 				orderItems.add(orderItem);
			}
			
			//库存机损
			if (num4!=null&&num4.equals("null")==false&&num4.equals("")==false) {
				InventoryCountReturnOrderItem orderItem = this.createOrderItem(data,num4,inventoryCount,item,InventoryTypeEnum.CASES);
 				orderItems.add(orderItem);
			}		
		}
		System.err.println("size:"+orderItems.size());
		return orderItems;
	}


	/**
	 * 创建明细单据
	 * @param data
	 * @param num1
	 * @param InventoryCount
	 * @param item
	 * @param InventoryTypeEnum
	 * @return InventoryCountReturnOrderItem
	 * */
	private InventoryCountReturnOrderItem createOrderItem(Map<String, Object> data, String num1, InventoryCount inventoryCount,
		Item item, InventoryTypeEnum type) {
		
		InventoryCountReturnOrderItem orderItem = new InventoryCountReturnOrderItem();
		
		orderItem.generateId();
		
		orderItem.setItem(item);
		
		orderItem.setInventoryCount(inventoryCount);
		
		orderItem.setItemCode(item.getItemCode());
		
		String outBizCode = ""+data.get("outBizCode");
		orderItem.setDetailOutBizCode(outBizCode);
		
		orderItem.setQuantity(Integer.parseInt(num1));
		
		orderItem.setBatchCode("");
		
		orderItem.setSnCode("");
		
		String remark = ""+data.get("remark");
		orderItem.setRemark(remark);
		
		orderItem.setDueDate(new Date());
		
		orderItem.setProduceCode("");
		
//		orderItem.setCu("");
		
		orderItem.setInventoryType(type);
		
		return orderItem;
	}

}
