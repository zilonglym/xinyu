package com.xinyu.model.order.child;

import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;

/**
 * @author shark_cj
 * @since  2017-05-03
 * 入库单实际入库数量  和库存类型
 */
public class StockInItemInventory  extends Entity {
	
	private StockInOrderItem   stockInOrderItem;
	
	/**
	 * true   库存类型
	 * 入库单确认不能回传 301 在途库存	inventoryType
	 */
	private InventoryTypeEnum    inventoryType;

	/**
	 * true   数量
	 * actualQty 
	 * 必传
	 */
	private int  quantity;
	
	/**
	 * false  批次号
	 * batchCode
	 */
	private String batchCode;
	
	/**
	 * false  sn编码
	 * 新增
	 */
	private String snCode;
	
	
	private  String produceCode;
	
	/**
	 * false    二维码
	 * qrCode
	 */
	private String qrCode;

	public StockInOrderItem getStockInOrderItem() {
		return stockInOrderItem;
	}

	public void setStockInOrderItem(StockInOrderItem stockInOrderItem) {
		this.stockInOrderItem = stockInOrderItem;
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

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getProduceCode() {
		return produceCode;
	}

	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}
}
