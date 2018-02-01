package com.xinyu.check.model;

import java.io.Serializable;

public class ShipOrderDetail implements Serializable,Cloneable{

	private static final long serialVersionUID = -7428439750730206579L;

	private Long id;

	private ShipOrder order;

	private Item item;

	private long num;
	
	private int actualnum;//实际数量
	
	private String skuPropertiesName;
	
	//接口加入新字段
	private String orderLineNo;//单据行号
	
	private String ownerCode;//货主编号
	
	private String inventoryType;//库存类型 库存类型，string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损，默认为ZP
	
	private String tradeOrderNo;//交易平台订单号
	private String subTradeOrderNo;//交易平台子订单号
	
	private String actualPrice;//实际成交价
	
	public Long getId() {
		return id;
	}
	
	public ShipOrder getOrder() {
		return order;
	}

	public Item getItem() {
		return item;
	}

	public String getItemTitle() {
		return item.getTitle();
	}
	
	public String getItemCode() {
		return item.getCode();
	}
	
	public long getNum() {
		return num;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public void setOrder(ShipOrder entryOrder) {
		this.order = entryOrder;
	}

	public String getSkuPropertiesName() {
		if (skuPropertiesName == null) return "";
		return skuPropertiesName;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}

	public String getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getSubTradeOrderNo() {
		return subTradeOrderNo;
	}

	public void setSubTradeOrderNo(String subTradeOrderNo) {
		this.subTradeOrderNo = subTradeOrderNo;
	}

	public String getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}

	public int getActualnum() {
		return actualnum;
	}

	public void setActualnum(int actualnum) {
		this.actualnum = actualnum;
	}
	

	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    } 
	
}
