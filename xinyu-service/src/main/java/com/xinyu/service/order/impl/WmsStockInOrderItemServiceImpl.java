package com.xinyu.service.order.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.WmsStockInOrderItemService;

@Service("wmsStockInOrderItemServiceImpl")
public class WmsStockInOrderItemServiceImpl extends BaseServiceImpl implements WmsStockInOrderItemService{

	@Autowired
	private ItemDao itemDao;
		
	@Override
	public List<Map<String, Object>> buildDetailListData(List<WmsStockInOrderItem> orderItems) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(WmsStockInOrderItem stockInOrderItem:orderItems){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", stockInOrderItem.getId());
			map.put("orderItemId", stockInOrderItem.getOrderItemId());
			map.put("itemName", stockInOrderItem.getItemName());
			map.put("itemCode", stockInOrderItem.getItemCode());
			Item item = this.itemDao.getItem(stockInOrderItem.getItem().getId());
			map.put("itemId", item.getId());
			map.put("itemSku", (item.getSpecification()==null?"":item.getSpecification()));
			map.put("barCode", stockInOrderItem.getBarCode());
			for(InventoryTypeEnum type:InventoryTypeEnum.values()){
				if (type.getKey().equals(stockInOrderItem.getInventoryType().getKey())) {
					map.put("type", type.getDescription());
				}
			}
			map.put("num", stockInOrderItem.getItemQuantity());
			map.put("num1", stockInOrderItem.getItemQuantityNormal());
			map.put("num12", stockInOrderItem.getItemQuantityDefective());
			resultList.add(map);
		}
		return resultList;
	}
}
