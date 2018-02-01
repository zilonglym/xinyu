package com.xinyu.service.inventory.child.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import  org.springframework.beans.factory.annotation.Autowired;import  com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.inventory.child.InventoryCountReturnOrderItemService;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.inventory.child.InventoryCountReturnOrderItemDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
@Repository("inventoryCountReturnOrderItemServiceImpl")
public class InventoryCountReturnOrderItemServiceImpl extends BaseServiceImpl implements InventoryCountReturnOrderItemService {
	@Autowired
	private InventoryCountReturnOrderItemDao dao;
	
	@Autowired
	private ItemDao itemDao;
	
	public void saveInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info){
		this.dao.saveInventoryCountReturnOrderItem(info);
	}
	public void updateInventoryCountReturnOrderItem(InventoryCountReturnOrderItem info){
		this.dao.updateInventoryCountReturnOrderItem(info);
	}
	public InventoryCountReturnOrderItem getInventoryCountReturnOrderItemById(String id){
		return this.dao.getInventoryCountReturnOrderItemById(id);
	}
	public List<InventoryCountReturnOrderItem> getInventoryCountReturnOrderItemByList(Map<String, Object> params){
		return this.dao.getInventoryCountReturnOrderItemByList(params);
	}
	
	@Override
	public List<Map<String, Object>> buildListData(List<InventoryCountReturnOrderItem> orderItems) {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(InventoryCountReturnOrderItem orderItem:orderItems){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", orderItem.getId());
			Item item = this.itemDao.getItem(orderItem.getItem().getId());
			map.put("itemName", item.getName());
			map.put("sku", item.getColor());
			String description = orderItem.getInventoryType().getDescription();
			map.put("itemType", description);	
			String key = orderItem.getInventoryType().getKey();
			map.put("itemKey", key);
			if (key.equals("1")) {
				map.put("num1", orderItem.getQuantity());
			}else if(key.equals("101")){
				map.put("num2", orderItem.getQuantity());
			}else if(key.equals("102")){
				map.put("num3", orderItem.getQuantity());
			}else if(key.equals("103")){
				map.put("num4", orderItem.getQuantity());
			}
			results.add(map);
		}
		return results;
	}
}
