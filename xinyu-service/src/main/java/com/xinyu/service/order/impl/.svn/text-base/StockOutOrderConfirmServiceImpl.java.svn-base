package com.xinyu.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.order.StockOutOrderConfirmDao;
import com.xinyu.dao.order.StockOutOrderDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.PackageInfo;
import com.xinyu.model.base.PackageItem;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockInOrderConfirm;
import com.xinyu.model.order.StockOutOrder;
import com.xinyu.model.order.StockOutOrderConfirm;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;
import com.xinyu.model.order.child.StockOutItemInventory;
import com.xinyu.model.order.child.StockOutOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.StockOutOrderConfirmService;

@Service("stockOutOrderConfirmServiceImpl")
public class StockOutOrderConfirmServiceImpl extends BaseServiceImpl implements StockOutOrderConfirmService{

	@Autowired
	private StockOutOrderConfirmDao confirmDao;
	
	@Autowired
	private StockOutOrderDao stockOutOrderDao;
	
	@Autowired
	private ItemDao itemDao;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<Map<String, Object>> getConfirmListDataByOrderId(String orderId) {
		StockOutOrder stockOutOrder = this.stockOutOrderDao.getStockOutOrderById(orderId);
		String orderCode = stockOutOrder.getOrderCode();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		List<StockOutOrderConfirm> confirms = this.confirmDao.getStockOutOrderConfirmList(params);
		
		List<Map<String, Object>>   dataList  =  new ArrayList<Map<String,Object>>();
		buildConfirmData(confirms, dataList, stockOutOrder);
		
		return dataList;
	}

	private void buildConfirmData(List<StockOutOrderConfirm> confirms, List<Map<String, Object>> dataList, StockOutOrder stockOutOrder) {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("stockOutOrderID", stockOutOrder.getId());
			List<StockOutOrderItem> orderItems = this.confirmDao.getStockOutOrderItem(params);
			
			for(StockOutOrderItem  orderItem  : orderItems){
				Map<String, Object> retMap  = new HashMap<String, Object>();   //前台显示
//				retMap.put("date", formatter.format(confirm.getOrderConfirmTime()));
//				retMap.put("bizCode", confirm.getOutBizCode());
//				retMap.put("type",confirm.getConfirmType());
				String id = orderItem.getItem().getId();
				Item item = itemDao.getItem(id);
				retMap.put("itemName", item.getName());
			
				params.clear();
				params.put("stockOutOrderItemId", orderItem.getId());
				List<StockOutItemInventory> items = this.confirmDao.getStockOutItemInventory(params);
				
				for(StockOutItemInventory  inventoryItem : items){
					InventoryTypeEnum inventoryType = inventoryItem.getInventoryType();
					if("1".equals(inventoryType.getKey())){   //正品销售数量  
						retMap.put("num", inventoryItem.getQuantity());  
					}else if("101".equals(inventoryType.getKey())){//残次品数量
						retMap.put("num1", inventoryItem.getQuantity());  
					}
				}
				dataList.add(retMap);
			}
	}
	
}
