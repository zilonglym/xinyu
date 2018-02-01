package com.xinyu.model.order.child;

import java.util.Date;
import java.util.Map;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.order.StockInOrder;

/**
 * @author shark_cj
 * @since 2017-04-27
 * 入库单商品信息
 */
public class WmsStockInOrderItem  extends Entity{
	
	
	/**
	 *  父类ID
	 */
	private StockInOrder   stockInOrder;
	
	/**
	 * TRUE 入库单明细ID
	 * orderLineNo
	 */
	private  String  orderItemId;
	
	/**
	 *  FALSE  平台交易编码
	 *  只在销退订单时使用，
	 *  原销售订单的主交易单号	sourceOrderCode
	 */
	private String orderSourceCode;
	
	
	/**
	 * FALSE  平台子交易编码
	 * 在销退订单时使用
	 * 销售订单的子交易单号	subSourceOrderCode
	 */
	private String subSourceCode;
	
	/**
	 * TRUE  货主ID
	 * ownerCode
	 */
	private String  ownerUserId;
	
	/**
	 * TRUE  商品ID
	 * itemId
	 */
	private Item  item;
	
	/**
	 * FALSE   商品名称
	 * itemName
	 */
	private String  itemName;
	
	/**
	 * FALSE    商家对商品的编码
	 * itemCode
	 */
	private String  itemCode;
	
	/**
	 * FALSE  条形码，多条码请用”;”分隔
	 * 仓库入库扫码使用	新增字段，之前如果没有可以忽略
	 */
	private String  barCode;
	
	/**
	 * 注意：采购入库单下发的库存类型是301	需适配菜鸟库存类型
	 */
	private  InventoryTypeEnum inventoryType;
	
	/**
	 * TRUE 商品数量
	 * planQty
	 */
	private   int  itemQuantity;
	
	/**
	 * 中仓自动自定义字段
	 * 正品数量
	 */
	private int itemQuantityNormal;
	
	/**
	 * 中仓自动自定义字段
	 * 残次品数量
	 */
	private int itemQuantityDefective;
	
	/**
	 * TRUE  	商品版本
	 * 如果版本和WMS系统中的版本不一致，
	 * 需要CP使用商品抓取接口，
	 * 将最新的商品信息抓取下来	
	 * 新增字段，之前如果没有可以忽略
	 */
	private  Integer  itemVersion;
	
	/**
	 *  FALSE  批次号
	 *  batchCode
	 */
	private  String   batchCode;
	
	/**
	 * FALSE  到效日期
	 * 保质期商品信息，
	 * 如果商品启用了保质期管理，
	 * 需要仓库按指定保质期生产
	 * expireDate
	 */
	private Date   dueDate;
	
	/**
	 *  FALSE   生产日期
	 *  保质期商品信息，
	 *  如果商品启用了保质期管理，
	 *  需要仓库按指定保质期生产
	 *  productDate
	 */
	private  Date  produceDate;
	
	/**
	 * FALSE  生产编码
	 * 同一商品可能因商家不同有不同编码
	 * produceCode
	 */
	private  String  produceCode;
	
	/**
	 * FALSE  采购单号
	 * purchaseOrderCode
	 */
	private  String   purchaseOrderCode;
	
	/**
	 *  FALSE  税率
	 *  单位%，保留2位小数	
	 *  新增字段，之前如果没有可以忽略
	 */
	private Double   taxRate;
	
	/**
	 * FALSE   采购价
	 * purchasePrice  单位变成分
	 */
	private   Long  purchasePrice;
	
	/**
	 * FALSE  零售价
	 * retailPrice单位变成分
	 */
	private    Long  itemPrice;
	
	/**
	 *  FALSE
	 *  批次备注
	 *  新增字段，之前如果没有可以忽略
	 */
	private String   batchRemark;
	
	/**
	 * FALSE   拓展属性数据
	 * 详见 
	 * 订单明细下发扩展字段
	 * 新增字段，之前如果没有可以忽略
	 */
	private Map<String,String>  extendFields;
	
	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderSourceCode() {
		return orderSourceCode;
	}

	public void setOrderSourceCode(String orderSourceCode) {
		this.orderSourceCode = orderSourceCode;
	}

	public String getSubSourceCode() {
		return subSourceCode;
	}

	public void setSubSourceCode(String subSourceCode) {
		this.subSourceCode = subSourceCode;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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

	public Integer getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Integer itemVersion) {
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

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Long getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Long purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public StockInOrder getStockInOrder() {
		return stockInOrder;
	}

	public void setStockInOrder(StockInOrder stockInOrder) {
		this.stockInOrder = stockInOrder;
	}

	public int getItemQuantityNormal() {
		return itemQuantityNormal;
	}

	public void setItemQuantityNormal(int itemQuantityNormal) {
		this.itemQuantityNormal = itemQuantityNormal;
	}

	public int getItemQuantityDefective() {
		return itemQuantityDefective;
	}

	public void setItemQuantityDefective(int itemQuantityDefective) {
		this.itemQuantityDefective = itemQuantityDefective;
	}
	
	
}
