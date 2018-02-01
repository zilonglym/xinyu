package com.xinyu.model.order.child;

import java.util.List;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;
import com.xinyu.model.order.StockOutOrder;

/**
 * @author shark_cj
 * @since   2017-05-03
 * 出库单商品信息
 */
public class StockOutOrderItem  extends  Entity{

	private StockOutOrder  stockOutOrder;
	
	/**
	 * 商品
	 * */
	private Item item;
	
	/**
	 * true  出库单明细ID
	 * orderLineNo
	 */
	private String orderItemId;
	
	/**
	 * false     商品属性列表
	 * orderLine
	 */
	private  List<StockOutItemInventory>    items;

	public StockOutOrder getStockOutOrder() {
		return stockOutOrder;
	}

	public void setStockOutOrder(StockOutOrder stockOutOrder) {
		this.stockOutOrder = stockOutOrder;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public List<StockOutItemInventory> getItems() {
		return items;
	}

	public void setItems(List<StockOutItemInventory> items) {
		this.items = items;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
