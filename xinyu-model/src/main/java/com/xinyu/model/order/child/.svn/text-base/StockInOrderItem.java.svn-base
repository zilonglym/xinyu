package com.xinyu.model.order.child;

import java.util.List;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;
import com.xinyu.model.order.StockInOrderConfirm;

/**
 * @author shark_cj
 * @since   2017-05-03
 * 订单入库商品信息
 */
public class StockInOrderItem extends  Entity{
	
	private StockInOrderConfirm  stockInOrderConfirm;
	
	/**
	 *  商品字段
	 */
	private Item  item;
	
	/**
	 * TRUE   入库单明细ID
	 * orderLineNo
	 */
	private String  orderItemId;
	
	/**
	 * FALSE   sku重量
	 * 单位克,采购入库单、普通入库单必传
	 * 传毛重  去掉小数位取整	
	 * 新增
	 */
	private long  weight;
	
	/**
	 * false   体积
	 * 单位立方厘米 
	 *  采购入库单、普通入库单必传
	 *  向上取整	
	 *  新增
	 */
	private int  volume;
	
	/**
	 * false   长 单位 mm
	 * 购入库单、普通入库单必传	
	 * 新增
	 */
	private int length;
	
	/**
	 * false  宽 单位 mm
	 * 采购入库单、普通入库单必传	
	 * 新增
	 */
	private int width;

	/**
	 * FALSE  高 单位 mm
	 * 采购入库单、普通入库单必传
	 */
	private int  height;
	
	/**
	 * false    items
	 * batchs
	 */
	private  List<StockInItemInventory> items;

	public StockInOrderConfirm getStockInOrderConfirm() {
		return stockInOrderConfirm;
	}

	public void setStockInOrderConfirm(StockInOrderConfirm stockInOrderConfirm) {
		this.stockInOrderConfirm = stockInOrderConfirm;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<StockInItemInventory> getItems() {
		return items;
	}

	public void setItems(List<StockInItemInventory> items) {
		this.items = items;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
