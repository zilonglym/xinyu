package com.xinyu.model.inventory.child;

import com.xinyu.model.base.Item;
import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.InventoryAdjustUpload;

public class InventoryOrderItem extends  Entity{
	
	private InventoryAdjustUpload inventoryAdjustUpload;
	
//	itemId	string	　20	FALSE	1345	商品ID	itemId
	private Item  item;
	
//	itemCode	string	64	TRUE	137903	商家对商品的编码	itemCode
	private String ItemCode ;
	
//	quantity	int	16	TRUE	12	商品数量	quantity
	private Integer  quantity;
	
//	source	InventoryDetail		TRUE		调整前内容	新增字段
	private  Integer  source;
	
//	target	InventoryDetail		TRUE		调整后内容	新增字段
	private Integer target;

	
	public InventoryAdjustUpload getInventoryAdjustUpload() {
		return inventoryAdjustUpload;
	}

	public void setInventoryAdjustUpload(InventoryAdjustUpload inventoryAdjustUpload) {
		this.inventoryAdjustUpload = inventoryAdjustUpload;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getItemCode() {
		return ItemCode;
	}

	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}
	
	

	
}
