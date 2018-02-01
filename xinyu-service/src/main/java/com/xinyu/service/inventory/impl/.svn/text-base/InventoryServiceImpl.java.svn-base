package com.xinyu.service.inventory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryDao;
import com.xinyu.dao.inventory.InventoryRecordDao;
import com.xinyu.dao.trade.WmsConsignOrderItemDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.inventory.Inventory;
import com.xinyu.model.inventory.InventoryRecord;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.enums.OrderTypeEnum;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.trade.impl.ShipOrderServiceImpl;


/**
 * @author shark_cj
 * @since  2017-05-02
 */
@Service("inventoryServiceImpl")
public class InventoryServiceImpl  extends BaseServiceImpl implements InventoryService {
	
	public static final Logger logger = Logger.getLogger(InventoryServiceImpl.class);
	
	@Autowired
	private  InventoryDao  inventoryDao;
	
	@Autowired
	private  ItemDao  itemDao;
	
	@Autowired
	private  WmsConsignOrderItemDao  orderItemDao;
	
	
	/**
	 * 批量库存操作
	 * @param params
	 * @return
	 */
	@Transactional
	public Map<String,Object> addNumBatch(List<Map<String,Object>> parmasList){
		
		if(parmasList!=null &&  parmasList.size()>0){
			for(Map<String,Object> sqlParam : parmasList){
				this.addNum(sqlParam);
			}
		}
		return null;
	}
	
	@Autowired
	private  UserDao  userDao;
	
	
	@Autowired
	private  InventoryRecordDao  inventoryRecordDao;
	
	public void  insertInventory(Inventory   inventory){
		inventoryDao.insertInventory(inventory);
	}
	
	/**
	 * 库存操作
	 * @param params
	 * @return
	 */
	public Map<String,Object>  addNum(Map<String,Object>  params){
		
		//库存记录表
		InventoryRecord record   = new InventoryRecord();
		record.generateId();
		//库存单据类型
		String  type  = ""+params.get("inventoryOrderType");
		InventoryOrderTypeEnum   inventoryOrderType  = InventoryOrderTypeEnum.getInventoryOrderTypeEnum(type);
		record.setOrderType(inventoryOrderType);
		
		Item  item  = itemDao.getItem(""+params.get("itemId"));
		record.setItem(item);
		record.setUser(item.getUser());
		record.setCreateDate(new Date());
		
		//设置库存类型
		InventoryTypeEnum  inventoryType  = (InventoryTypeEnum)params.get("inventoryType");
		record.setInventoryType(inventoryType);
		
		Long  num  =  (Long)params.get("num");
		record.setNum(num);
		
		String  id  =""+params.get("id");
		record.setOrderNo(id);
		
		if(inventoryDao.isExist(params)>0){ //入库已有库存记录, 直接修改, 添加库存
			inventoryDao.addNumByItemId(params);
			
		}else{ //没有库存记录商品,新增库存记录
			Inventory  inventory = new Inventory();
			inventory.generateId();
			inventory.setInventoryType(inventoryType);
			inventory.setNum(num);
			inventory.setItem(item);
			inventory.setUser(item.getUser()); 
			inventoryDao.insertInventory(inventory);
		}
		
		inventoryRecordDao.insertInventoryRecord(record);
		return null;
	}

	/**
	 * 商品库存分页查询
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
//	@Override
//	public List<Inventory> findInventoryByPage(Map<String, Object> params, int page, int rows) {
//		// TODO Auto-generated method stub
//		return inventoryDao.findInventoryByPage(params,page,rows);
//	}
	
	@Override
	public List<Map<String, Object>> findInventoryByPage(Map<String, Object> params, int page, int rows) {
		// TODO Auto-generated method stub
		return inventoryDao.findInventoryByPage(params,page,rows);
	}

	/**
	 * 计数商品库存
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return inventoryDao.getTotal(params);
	}
	
	@Override
	public Inventory findInventoryByParam(Map<String, Object> params) {
		return this.inventoryDao.findInventoryByParam(params);
	}

	/**
	 * 数据重组
	 * @param list
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<Map<String, Object>> inventories) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> inventory:inventories){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", inventory.get("id"));
			map.put("cu", inventory.get("cu"));
			map.put("num1", inventory.get("c1"));
			map.put("num2", inventory.get("c2"));
			map.put("num3", inventory.get("c3"));
			map.put("num4", inventory.get("c4"));
			map.put("num5", inventory.get("c5"));
			map.put("num6", inventory.get("c6"));
			map.put("num7", inventory.get("c7"));
			User user = this.userDao.getUserById(String.valueOf(inventory.get("userId")));
			map.put("userName", user.getSubscriberName());
			map.put("itemName", inventory.get("itemName"));
			map.put("itemCode", inventory.get("itemCode"));
			map.put("sku", inventory.get("color"));
			resultList.add(map);
		}
		return resultList;
	}

	
	/**
	 * 商品库存校验
	 * @param Item
	 * @param map
	 * @return map
	 * */
	@Override
	public Map<String, Object> isHaveInventory(Map<String, Object> params) {
		Item item = (Item) params.get("item");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("itemId", item.getId());
		retMap.put("inventoryType", InventoryTypeEnum.getInventoryType(""+params.get("inventoryType")));
		Inventory inventory = this.inventoryDao.findInventoryByParam(retMap);
		if (inventory!=null) {
			long num = inventory.getNum() - Long.parseLong(""+params.get("quantity"));
			if (num<0) {
				retMap.put("msg", item.getName()+":"+item.getColor()+"库存不足！");
			}
		}else {
			retMap.put("msg", item.getName()+":"+item.getColor()+"库存不足！");
		}
		return retMap;
	}
	
	
	/**
	 * 
	 * @param map
	 * @author shark_cj
	 * 为菜鸟压力测试 优化单独调用
	 * 此方法  针对 菜鸟订单创建  可以出库存逻辑
	 * 2017- 09 -  26
	 */
	@Override
	public void updateInventoryByCreate(Map<String,Object>  orderMap){
		logger.debug("订单创建更新库存开始！");
		//订单创建：减单据对应库存类型，加冻结库存
		List<Map<String, Object>> paramsList   = (List<Map<String, Object>>) orderMap.get("paramsList");
		Map<String, Object> params = new HashMap<String, Object>();
	
		
		for(Map<String, Object> map:paramsList){
			Date date  = null;
			Long quantity  =  Long.parseLong(""+map.get("quantity")); //商品数量
			String  itemId  = ""+map.get("itemId"); //商品ID
			User user =  (User)map.get("user");
			String  orderCode = "" +map.get("orderCode");
			
			params.clear();
			params.put("itemId", itemId);		
			params.put("inventoryType",  ""+map.get("inventoryType"));
			long num = 0L - quantity;
			params.put("num", num);	
			date=new Date();
			this.inventoryDao.addNumByItemId(params);
			logger.error("添加正式库存："+(new Date().getTime()-date.getTime()));
			
			//订单明细上商品加库存（待发货商品库存）
			
			params.put("inventoryType", InventoryTypeEnum.BLOCKING);
			date=new Date();
			int exist = this.inventoryDao.isExist(params);
			logger.error("待发货商品库存exist:"+(new Date().getTime()-date.getTime()));
			date=new Date();
			if (exist > 0) {
				//商品冻结库存有记录，直接修改
				params.put("num", quantity);	
				this.inventoryDao.addNumByItemId(params);
			}else {
				//商品冻结库存无记录，新建
				Inventory bNewInventory = new Inventory();
				bNewInventory.generateId();
				bNewInventory.setInventoryType(InventoryTypeEnum.BLOCKING);
				
				Item item = new Item();
				item.setId(itemId);
				bNewInventory.setItem(item);
				bNewInventory.setNum(quantity);
//				User user = this.userDao.findUserByOwnerCode(""+map.get("ownerUserId"));
				bNewInventory.setUser(user);
				this.inventoryDao.insertInventory(bNewInventory);
			}
			logger.error("添加冻结库存："+(new Date().getTime()-date.getTime()));
			
			date=new Date();
			InventoryRecord inventoryRecord = new InventoryRecord();
			inventoryRecord.setDescription(orderCode);
			inventoryRecord.setCreateDate(new Date());
			
			Item item = new Item();
			item.setId(itemId);
			inventoryRecord.setItem(item);
			inventoryRecord.setOrderNo(""+map.get("orderId"));
			inventoryRecord.setOrderType(InventoryOrderTypeEnum.TRADESO);
//			User user = this.userDao.findUserByOwnerCode(""+map.get("ownerUserId"));
			inventoryRecord.setUser(user);
				
			inventoryRecord.generateId();
			inventoryRecord.setInventoryType(InventoryTypeEnum.NORMAL);
			inventoryRecord.setNum(num);
			this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);

			inventoryRecord.generateId();
			inventoryRecord.setInventoryType(InventoryTypeEnum.BLOCKING);
			inventoryRecord.setNum(quantity);
			this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);
			logger.error("添加库存流水："+(new Date().getTime()-date.getTime()));
		}
		
		
	
		logger.debug("订单创建更新库存结束！");
		
	}
	
	
	/**
	 * 交易订单更新库存
	 * @param paramsList
	 * @param type
	 * */
	@Override
	public  void updateInventoryByOrder(List<Map<String, Object>> paramsList,String type) {
		logger.debug("订单库存更新操作开始！");
		Map<String, Object> params = new HashMap<String, Object>();
		if (type.equals("create")) {
			logger.debug("订单创建更新库存开始！");
			//订单创建：减单据对应库存类型，加冻结库存
			for(Map<String, Object> map:paramsList){
				
				params.clear();
				params.put("itemId", ""+map.get("itemId"));		
				params.put("inventoryType",  ""+map.get("inventoryType"));
				long num = 0L - Long.parseLong(""+map.get("quantity"));
				params.put("num", num);
				this.inventoryDao.addNumByItemId(params);
				
				//订单明细上商品加库存（待发货商品库存）
				params.put("inventoryType", InventoryTypeEnum.BLOCKING);
				Inventory bInventory = this.inventoryDao.findInventoryByParam(params);
				if (bInventory!=null) {
					//商品冻结库存有记录，直接修改
					params.put("num", Long.parseLong(""+map.get("quantity")));	
					this.inventoryDao.addNumByItemId(params);
				}else {
					//商品冻结库存无记录，新建
					Inventory bNewInventory = new Inventory();
					bNewInventory.generateId();
					bNewInventory.setInventoryType(InventoryTypeEnum.BLOCKING);
					Item item = this.itemDao.getItem(""+map.get("itemId"));
					bNewInventory.setItem(item);
					bNewInventory.setNum(Long.parseLong(""+map.get("quantity")));
					User user = this.userDao.findUserByOwnerCode(""+map.get("ownerUserId"));
					bNewInventory.setUser(user);
					saveInventory(bNewInventory);
				}			
			}
			
			//生成库存记录，单据创建
			this.insertOrderInventoryRecord(paramsList,"create");
		
			logger.debug("订单创建更新库存结束！");
			
		}else if(type.equals("confirm")){
			logger.debug("订单确认更新库存开始！");
			//订单确认减冻结库存
			for(Map<String, Object> map:paramsList){
				params.clear();
				params.put("itemId", ""+map.get("itemId"));
				params.put("inventoryType", InventoryTypeEnum.BLOCKING);
				long num = 0L - Long.parseLong(""+map.get("quantity"));
				params.put("num", num);
				this.inventoryDao.addNumByItemId(params);
			}
			
			//生成库存记录，单据确认
			this.insertOrderInventoryRecord(paramsList,"confirm");
		
			logger.debug("订单确认更新库存结束！");
			
		}else if(type.equals("cancel")){
			logger.debug("订单取消更新库存开始！");
			//订单取消：减冻结库存，加单据对应类型库存
			for(Map<String, Object> map:paramsList){
				
				params.clear();
				params.put("itemId", ""+map.get("itemId"));
				params.put("inventoryType", InventoryTypeEnum.BLOCKING);
				long num = 0L - Long.parseLong(""+map.get("quantity"));
				params.put("num", num);
				this.inventoryDao.addNumByItemId(params);
				
				params.clear();
				params.put("itemId", ""+map.get("itemId"));
				params.put("inventoryType", ""+map.get("inventoryType"));
				params.put("num", Long.parseLong(""+map.get("quantity")));
				this.inventoryDao.addNumByItemId(params);
				
			}
			
//			生成库存记录，单据取消
			this.insertOrderInventoryRecord(paramsList,"cancel");
			logger.debug("订单取消更新库存结束！");
			
		}	
		logger.debug("订单库存更新操作结束！");
	}

	private  void saveInventory(Inventory bNewInventory) {
		this.inventoryDao.insertInventory(bNewInventory);
	}


	/**
	 * 创建交易订单库存记录
	 * @param paramsList
	 * @param type
	 * */
	private void insertOrderInventoryRecord(List<Map<String, Object>> paramsList,String type) {
		for(Map<String, Object> map:paramsList){
			InventoryRecord inventoryRecord = new InventoryRecord();
			inventoryRecord.setCreateDate(new Date());
			Item item = this.itemDao.getItem(""+map.get("itemId"));
			inventoryRecord.setItem(item);
			inventoryRecord.setOrderNo(""+map.get("orderId"));
			inventoryRecord.setOrderType(InventoryOrderTypeEnum.TRADESO);
			User user = this.userDao.findUserByOwnerCode(""+map.get("ownerUserId"));
			inventoryRecord.setUser(user);
			if (type.equals("create")) {
				
				inventoryRecord.generateId();
				inventoryRecord.setDescription(""+map.get("orderCode")+":交易订单创建！"+item.getName()+"可销售库存减"+map.get("quantity"));
				inventoryRecord.setInventoryType(InventoryTypeEnum.NORMAL);
				long num = 0L-Long.parseLong(""+map.get("quantity"));
				inventoryRecord.setNum(num);	
				this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);
				
				inventoryRecord.generateId();
				inventoryRecord.setDescription(""+map.get("orderCode")+":交易订单创建！"+item.getName()+"冻结库存加"+map.get("quantity"));
				inventoryRecord.setInventoryType(InventoryTypeEnum.BLOCKING);
				inventoryRecord.setNum(Long.parseLong(""+map.get("quantity")));	
				this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);
				
			}else if(type.equals("confirm")){
				
				inventoryRecord.generateId();
				inventoryRecord.setDescription(""+map.get("orderCode")+":交易订单发货确认！"+item.getName()+"冻结库存减"+map.get("quantity"));
				inventoryRecord.setInventoryType(InventoryTypeEnum.BLOCKING);
				long num = 0L-Long.parseLong(""+map.get("quantity"));
				inventoryRecord.setNum(num);	
				this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);
				
			}else if(type.equals("cancel")){
				
				inventoryRecord.generateId();
				inventoryRecord.setDescription(""+map.get("orderCode")+":交易订单取消！"+item.getName()+"冻结库存减"+map.get("quantity"));
				inventoryRecord.setInventoryType(InventoryTypeEnum.BLOCKING);
				long num = 0L-Long.parseLong(""+map.get("quantity"));
				inventoryRecord.setNum(num);	
				this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);
				
				inventoryRecord.generateId();
				inventoryRecord.setDescription(""+map.get("orderCode")+":交易订单取消！"+item.getName()+"可销售库存加"+map.get("quantity"));
				inventoryRecord.setInventoryType(InventoryTypeEnum.NORMAL);
				inventoryRecord.setNum(Long.parseLong(""+map.get("quantity")));	
				this.inventoryRecordDao.insertInventoryRecord(inventoryRecord);
				
			}
					
		}
	}

	@Override
	public List<Map<String,Object>> inItInventory(List<Map<String, Object>> params) {
	
		logger.debug("库存初始化开始！");
		
 		if(params!=null   &&  params.size()>0){
 			
 			for(Map<String,Object> map  :  params){	
// 				System.err.println("map:"+map);
 				Item item =  null;
 				List<Item> items =  this.itemDao.getItemByList(map);
 				
 				if (items.size()>0) {
 					item = items.get(0);
				}
 				
 				if(item!=null){
 					System.err.println("item:"+item.getId()+":"+item.getItemCode());
 					String userId = ""+ map.get("userId");
 					User user = this.userDao.getUserById(userId);
 					
 					Long num =  Long.valueOf(""+ map.get("num"));
 					
 					Map<String, Object> pMap = new HashMap<String, Object>();
 					pMap.put("itemId", item.getId());
 					pMap.put("inventoryType", InventoryTypeEnum.NORMAL);
 					int i = this.inventoryDao.isExist(pMap);
 					
 					if(i==0){
 						Inventory inventory = new Inventory();
 						inventory.generateId();
 						inventory.setInventoryType(InventoryTypeEnum.NORMAL);
 						inventory.setItem(item);
 						inventory.setNum(num);	
 						inventory.setUser(user);
 						saveInventory(inventory);
 					}else{
 						Inventory inventory = this.inventoryDao.findInventoryByParam(pMap);
 						inventory.setNum(num);
 						this.inventoryDao.updateInventory(inventory);
 					}
 				}else {
 					System.err.println("item:"+map.get("code")+" 无法匹配！");
 				}
 			}
 		}
 		logger.debug("库存初始化结束！");
		return null;
	
	}
	
}
