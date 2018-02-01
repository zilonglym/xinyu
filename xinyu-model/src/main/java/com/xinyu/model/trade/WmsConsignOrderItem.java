package com.xinyu.model.trade;

import java.util.Date;
import java.util.Map;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;

/**
 * 订单商品信息
 * 
 * @author yangmin 2017年4月25日
 *
 */
public class WmsConsignOrderItem extends Entity {

	/**
	 * 订单主类
	 */
	private ShipOrder order;
	/**
	 * TRUE 订单商品ID orderLineNo 菜鸟此字段必填，回传菜鸟时已此字段回传菜鸟
	 * 
	 */
	private String orderItemId;

	/**
	 * 订单商品
	 */
	private Item item;

	/**
	 * FALSE 交易编码 sourceOrderCode
	 */
	private String orderSourceCode;
	/**
	 * FALSE 子交易编码 subSourceOrderCode
	 */
	private String subSourceCode;
	/**
	 * 卖家ID
	 */
	private String userId;
	/**
	 * 卖家名称
	 */
	private String username;
	/**
	 * TRUE 货主ID ownerCode
	 */
	private String ownerUserId;
	
	private String ownerUserName;
	/**
	 * FALSE 商品名称 itemName
	 */
	private String itemName;
	/**
	 * FALSE 商家对商品的编码 itemCode
	 */
	private String itemCode;
	/**
	 * FALSE 批次号 指定批次发货，目前是俪人购，喵生鲜业务，一般不会指定 batchCode
	 * 
	 */
	private String batchCode;
	/**
	 * FALSE 生产日期,保质期商品信息，如果商品启用了保质期管理，需要仓库按指定保质期生产 指定批次发货，目前是俪人购，喵生鲜业务，一般不会指定
	 * productDate
	 * 
	 */
	private Date produceDate;
	/**
	 * FALSE 到效日期,保质期商品信息，如果商品启用了保质期管理，需要仓库按指定保质期生产 指定批次发货，目前是俪人购，喵生鲜业务，一般不会指定
	 * expireDate
	 * 
	 */
	private Date dueDate;
	/**
	 * FALSE 采购单号，是批次属性，指定PO发货
	 */
	private String purchaseOrderCode;
	/**
	 * TRUE 库存类型(1 正品 ) inventoryType 需适配菜鸟规范
	 * 
	 */
	private int inventoryType;
	/**
	 * TRUE 商品数量 planQty
	 */
	private Long itemQuantity;
	/**
	 * 已确认的出库数量
	 */
	private Long itemOutQuantity;
	
	/**
	 * 批次确认本次的数量
	 */
	private Long itemBatchQuantity;
	/**
	 * FALSE 商品实际价格 actualPrice 需按照单位匹配，菜鸟单位分；奇门单位元
	 * 
	 */
	private Long actualPrice;
	/**
	 * FALSE 商品销售价格 retailPrice 需按照单位匹配，菜鸟单位分；奇门单位元
	 * 
	 */
	private Long itemPrice;
	/**
	 * FALSE 税率，单位%，保留2位小数
	 */
	private double taxRate;
	/**
	 * FALSE 商品优惠金额 discountAmount 需按照单位匹配，菜鸟单位分；奇门单位元
	 * 
	 */
	private Long discountAmount;
	/**
	 * TRUE 商品版本
	 */
	private int itemVersion;
	/**
	 * FALSE 批次备注
	 */
	private String batchRemark;
	
	private String remark;
	
	
	/**
	 * FALSE 拓展属性数据 详见 订单明细下发扩展字段 extendProps 数据结构与奇门不一致
	 * 
	 */
	private Map<String, Object> extendFields;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public Date getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}
	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}
	public int getInventoryType() {
		return inventoryType;
	}
	public void setInventoryType(int inventoryType) {
		this.inventoryType = inventoryType;
	}
	public Long getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public Long getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(Long actualPrice) {
		this.actualPrice = actualPrice;
	}
	public Long getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public Long getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
	public int getItemVersion() {
		return itemVersion;
	}
	public void setItemVersion(int itemVersion) {
		this.itemVersion = itemVersion;
	}
	public String getBatchRemark() {
		return batchRemark;
	}
	public void setBatchRemark(String batchRemark) {
		this.batchRemark = batchRemark;
	}
	public Map<String, Object> getExtendFields() {
		return extendFields;
	}
	public void setExtendFields(Map<String, Object> extendFields) {
		this.extendFields = extendFields;
	}
	public String getOwnerUserName() {
		return ownerUserName;
	}
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ShipOrder getOrder() {
		return order;
	}
	public void setOrder(ShipOrder order) {
		this.order = order;
	}
	
	public Long getItemOutQuantity() {
		return itemOutQuantity;
	}
	public void setItemOutQuantity(Long itemOutQuantity) {
		this.itemOutQuantity = itemOutQuantity;
	}
	
	
	
	
	public Long getItemBatchQuantity() {
		return itemBatchQuantity;
	}
	public void setItemBatchQuantity(Long itemBatchQuantity) {
		this.itemBatchQuantity = itemBatchQuantity;
	}
	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    } 


}
