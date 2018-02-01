package com.xinyu.model.trade;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;

/**
 * 订单包裹明细
 * @author yangmin
 * 2017年11月15日
 *
 */
public class TmsOrderItem extends Entity{
	/**
	 * 订单，这里主要是做冗余
	 */
	private ShipOrder order;
	
	private Item item;
	/**
	 * 包裹 
	 */
	private TmsOrder tmsOrder;
	
	private String batchCode;
		
	private String itemId;
	
	private String itemCode;
	
	private Long itemQuantity;
	
	private String orderItemId;

	public ShipOrder getOrder() {
		return order;
	}

	public void setOrder(ShipOrder order) {
		this.order = order;
	}

	public TmsOrder getTmsOrder() {
		return tmsOrder;
	}

	public void setTmsOrder(TmsOrder tmsOrder) {
		this.tmsOrder = tmsOrder;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
