package com.xinyu.model.inventory.child;

import java.util.Date;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.InventoryCount;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;

/**
 * @author shark_cj
 * @since  2017-04-30
 * 盘点单商品信息
 */
public class InventoryCountReturnOrderItem   extends Entity{
	
	
	/**
	 * 父类
	 */
	private InventoryCount  inventoryCount;
	
	/**
	 * FALSE  商品信息
	 * 商品ID	itemId
	 */
	private  Item  item;
	
	/**
	 * true    商家对商品的编码
	 * itemCode
	 */
	private String  itemCode;
	
	/**
	 * false    明细外部业务编号
	 * 差异单的盘点需要填写差异单生成的明细外部业务编号
	 * 差异单：主要是为了先锁定库存
	 * 无差异单业务不填
	 */
	private String  detailOutBizCode;
	
	/**
	 * true   库存类型
	 * inventoryType  需适配菜鸟库存类型
	 */
	private InventoryTypeEnum  inventoryType;
	
	/**
	 * true      商品数量
	 */
	private int   quantity;
	
	/**
	 * false  批次号
	 * batchCode
	 */
	private String  batchCode;

	/**
	 * FALSE   sn编码
	 * snCode
	 */
	private String snCode;
	
	/**
	 * false   备注
	 * 新增字段，之前如果没有可以忽略
	 */
	private String remark;
	
	
	private  Date  dueDate;
	
	private String  produceCode;

	public InventoryCount getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(InventoryCount inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDetailOutBizCode() {
		return detailOutBizCode;
	}

	public void setDetailOutBizCode(String detailOutBizCode) {
		this.detailOutBizCode = detailOutBizCode;
	}

	public InventoryTypeEnum getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryTypeEnum inventoryType) {
		this.inventoryType = inventoryType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProduceCode() {
		return produceCode;
	}

	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}

}
