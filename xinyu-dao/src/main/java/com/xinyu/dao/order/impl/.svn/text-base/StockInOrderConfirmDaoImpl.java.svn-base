package com.xinyu.dao.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.order.StockInOrderConfirmDao;
import com.xinyu.model.order.StockInOrder;
import com.xinyu.model.order.StockInOrderConfirm;
import com.xinyu.model.order.child.StockInCheckItem;
import com.xinyu.model.order.child.StockInItemInventory;
import com.xinyu.model.order.child.StockInOrderItem;


/**
 * @author shark_cj
 * @since  2017-05-25
 */
@Repository("stockInOrderConfirmDaoImpl")
public class  StockInOrderConfirmDaoImpl extends BaseDaoImpl implements   StockInOrderConfirmDao{
	
	private final String statement = "com.xinyu.dao.order.StockInOrderConfirmDao.";
	
	private final String statementInOrderItems =  "com.xinyu.dao.order.StockInOrderItemDao.";
	
	private final String statementInventoryItems =  "com.xinyu.dao.order.StockInItemInventoryDao.";
	
	private final String statementCheckItems =  "com.xinyu.dao.order.StockInCheckItemDao.";
	
	@Override
	public void insertStockInOrderConfirm(StockInOrderConfirm confirm){
		super.insert(this.statement+"insertStockInOrderConfirm", confirm);
		List<StockInCheckItem> checkItems = confirm.getCheckItems();
		for(StockInCheckItem checkItem: checkItems){
			super.insert(this.statementCheckItems+"insertStockInCheckItem", checkItem);
		}
		
		List<StockInOrderItem> orderItems = confirm.getOrderItems();
		for(StockInOrderItem  orderItem : orderItems){
			super.insert(this.statementInOrderItems+"insertStockInOrderItem", orderItem);
			List<StockInItemInventory> items = orderItem.getItems();
			for(StockInItemInventory inventoryItem: items ){
				super.insert(this.statementInventoryItems+"insertStockInItemInventory", inventoryItem);
			}
		}
	}
	
	@Override
	public List<StockInOrderConfirm>  getStockInOrderConfirm(Map<String,Object> params){
		return (List<StockInOrderConfirm>) super.selectList(statement+"getStockInOrderConfirm", params);
	}
	
	@Override
	public List<StockInOrderItem>  getStockInOrderItem(Map<String,Object> params){
		
		List<StockInOrderItem>   list  =(List<StockInOrderItem>) super.selectList(statementInOrderItems+"getStockInOrderItem", params);
		
		if(list!=null   &&  list.size()>0){
			for(StockInOrderItem  item   :  list ){
				params.clear();
				params.put("stockInOrderItemId",item.getId() );
				List<StockInItemInventory>  itemList  =(List<StockInItemInventory>) super.selectList(statementInventoryItems+"getStockInItemInventory", params);
				item.setItems(itemList);
			}
		}
		return list;
	}
	
	@Override
	public List<StockInItemInventory>  getStockInItemInventory(Map<String,Object> params){
		return (List<StockInItemInventory>) super.selectList(statementInventoryItems+"getStockInItemInventory", params);
	}
	
	@Override
	public int getCheckItemSum(Map<String,Object> params){
		return (Integer)super.selectOne(this.statementCheckItems+"getCheckItemSum", params);
	}
	
	
	
}