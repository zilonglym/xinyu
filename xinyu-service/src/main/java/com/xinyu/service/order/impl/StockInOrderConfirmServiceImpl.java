package com.xinyu.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.order.StockInOrderConfirmDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockInOrderConfirm;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.order.StockInOrderConfirmService;

/**
 * @author shark_cj
 * @since 2017-05-25
 */

@Service("stockInOrderConfirmServiceImpl")
public class StockInOrderConfirmServiceImpl  extends BaseServiceImpl implements StockInOrderConfirmService{
	
	
	@Autowired
	private StockInOrderConfirmDao stockInOrderConfirmDao;
	
	
	@Autowired
	private ItemDao itemDao;

	@Override
	@Transactional
	public Map<String, Object> save(StockInOrderConfirm confirm) {
		stockInOrderConfirmDao.insertStockInOrderConfirm(confirm);
		return null;
	}
	
	
	public  int getCheckItemSum(Map<String,Object> params){
		
		return stockInOrderConfirmDao.getCheckItemSum(params);
	}
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 查询批次记录
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getConfirmListDataByOrderId(String  id) {
		
		Map<String,Object>  params = new HashMap<String, Object>();
		
		params.put("stockInOrderId", id);
		
		//获得所有入库单下所有入库确认信息
		List<StockInOrderConfirm> confirms = stockInOrderConfirmDao.getStockInOrderConfirm(params);
		
		if(confirms!=null &&  confirms.size()>0){
			for(StockInOrderConfirm confirm  :  confirms){
				this.getConfirmDetail(confirm);
			}
		}
		List<Map<String, Object>>   dataList  =  new ArrayList<Map<String,Object>>();
		buildConfirmData(confirms, dataList);
		
		return dataList;
	}
	
	
	
	
	
	/**
	 * 获得入库确认明细信息
	 * @param confirm
	 */
	public void getConfirmDetail(StockInOrderConfirm  confirm){
		Map<String,Object>  params = new HashMap<String, Object>();
		params.put("stockInOrderConfirmId", confirm.getId());
		List<StockInOrderItem> stockInOrderItem = stockInOrderConfirmDao.getStockInOrderItem(params);
		confirm.setOrderItems(stockInOrderItem);
	}
	
	
	/**
	 * 组装前台展示界面
	 * @param confirms
	 * @param dataList
	 */
	private void   buildConfirmData(List<StockInOrderConfirm> confirms ,List<Map<String, Object>> dataList){
		
		
		for(StockInOrderConfirm confirm  :  confirms){  //多次提交时使用
			
			List<StockInOrderItem> orderItems = confirm.getOrderItems();
			for(StockInOrderItem  orderItem  : orderItems){
				Map<String, Object> retMap  = new HashMap<String, Object>();   //前台显示
				retMap.put("date", formatter.format(confirm.getOrderConfirmTime()));
				retMap.put("bizCode", confirm.getOutBizCode());
				retMap.put("type",confirm.getConfirmType());
				String id = orderItem.getItem().getId();
				Item item = itemDao.getItem(id);
				retMap.put("itemName", item.getName());
				
				List<StockInItemInventory> items = orderItem.getItems();
				
				for(StockInItemInventory  inventoryItem : items){
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


	@Override
	public List<StockInOrderConfirm> getStockInOrderConfirm(Map<String, Object> params) {
		return this.stockInOrderConfirmDao.getStockInOrderConfirm(params);
	}
}