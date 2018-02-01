package com.xinyu.model.order.child;

import java.util.Date;
import java.util.Map;

import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockOutOrder;


/**
 * @author shark_cj
 * @since  2017-04-29
 */
public class WmsStockOutOrderItem  extends Entity {
	
	private StockOutOrder stockOutOrder;
	
	/**
	 * TRUE出库单明细ID
	 */
	private  String  orderItemId;
	
	/**
	 * TRUE    货主ID
	 */
	private  User   user;
	
	/**
	 * TRUE   商品ID
	 */
	private Item  item;
	
	/**
	 * FALSE  商品名称
	 */
	private  String   itemName;
	
	/**
	 * FALSE   商家对商品的编码
	 */
	private String  itemCode;
	
	/**
	 * 库存类型 
	 * 可销售库存 正品 
	 */
	private InventoryTypeEnum   inventoryType;
	
	/**
	 * TRUE     商品数量
	 */
	private  int   itemQuantity;
	
	/**
	 * true   钱钱..
	 */
	private Long   itemPrice;
	
	
	/**
	 *  TRUE  商品版本
	 */
	private  int  itemVersion;
	
	/**
	 * false 批次号
	 */
	private String  batchCode;
	
	/**
	 * false   到效日期
	 * 保质期商品信息，
	 * 如果商品启用了保质期管理，
	 * 需要仓库按指定保质期生产
	 */
	private  Date   dueDate;
	
	/**
	 * FALSE  生产日期
	 * 保质期商品信息，
	 * 如果商品启用了保质期管理，
	 * 需要仓库按指定保质期生产
	 */
	private  Date  produceDate;
	
	/**
	 * FALSE     生产编码
	 * 同一商品可能因商家不同有不同编码
	 */
	private  String  produceCode;
	
	/**
	 * false  采购单号
	 * 是批次属性，指定PO出库
	 */
	private String  purchaseOrderCode;
	
	/**
	 * FALSE   批次备注
	 */
	private String  batchRemark;
	
	
	/**
	 * FALSE   拓展属性数据
	 */
	private  Map<String,String>  extendFields;


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


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemCode() {
		return itemCode;
	}


	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public InventoryTypeEnum getInventoryType() {
		return inventoryType;
	}


	public void setInventoryType(InventoryTypeEnum inventoryType) {
		this.inventoryType = inventoryType;
	}


	public int getItemQuantity() {
		return itemQuantity;
	}


	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}


	public int getItemVersion() {
		return itemVersion;
	}


	public void setItemVersion(int itemVersion) {
		this.itemVersion = itemVersion;
	}


	public String getBatchCode() {
		return batchCode;
	}


	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Date getProduceDate() {
		return produceDate;
	}


	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}


	public String getProduceCode() {
		return produceCode;
	}


	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}


	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}


	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}


	public String getBatchRemark() {
		return batchRemark;
	}


	public void setBatchRemark(String batchRemark) {
		this.batchRemark = batchRemark;
	}


	public Map<String, String> getExtendFields() {
		return extendFields;
	}


	public void setExtendFields(Map<String, String> extendFields) {
		this.extendFields = extendFields;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Long getItemPrice() {
		return itemPrice;
	}


	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}


	public StockOutOrder getStockOutOrder() {
		return stockOutOrder;
	}


	public void setStockOutOrder(StockOutOrder stockOutOrder) {
		this.stockOutOrder = stockOutOrder;
	}
}
