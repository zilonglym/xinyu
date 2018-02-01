package com.xinyu.service.order.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.order.child.WmsStockOutOrderItemDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.WmsStockOutOrderItemService;

@Service
public class WmsStockOutOrderItemServiceImpl extends BaseServiceImpl implements WmsStockOutOrderItemService{

	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private WmsStockOutOrderItemDao orderItemDao;
	
	@Override
	public List<Map<String, Object>> buildDetailListData(List<WmsStockOutOrderItem> orderItems) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(WmsStockOutOrderItem orderItem:orderItems){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderItemId", orderItem.getId());
			map.put("itemCode", orderItem.getItemCode());
			map.put("itemName", orderItem.getItemName());
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			map.put("barCode", item.getBarCode());
			map.put("itemId", item.getId());
			map.put("itemSku", item.getSpecification());
			if (orderItem.getInventoryType().getKey().equals("1")) {
				map.put("num1", orderItem.getItemQuantity());
			}else if(orderItem.getInventoryType().getKey().equals("101")){
				map.put("num2", orderItem.getItemQuantity());
			}else if(orderItem.getInventoryType().getKey().equals("102")){
				map.put("num3", orderItem.getItemQuantity());
			}else if(orderItem.getInventoryType().getKey().equals("103")){
				map.put("num4", orderItem.getItemQuantity());
			}
			resultList.add(map);
		}
		return resultList;
	}

	@Override
	public List<WmsStockOutOrderItem> findWmsStockOutOrderItemsByList(Map<String, Object> params) {
		System.err.println("size:"+this.orderItemDao.findWmsStockOutOrderItemsByList(params).size());
		return this.orderItemDao.findWmsStockOutOrderItemsByList(params);
	}

}
