package com.xinyu.model.order.child;

import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;


/**
 * @author shark_cj
 * @since  2017-05-03
 * 商品库存
 */
public class StockOutItemInventory  extends  Entity {
	
	private StockOutOrderItem  stockOutOrderItem;

	/**
	 * true   库存类型
	 * 1 可销售库存(正品) 101 残次 102 机损 103 箱损201 冻结库存
	 */
	private InventoryTypeEnum  inventoryType;
	
	/**
	 * TRUE    数量
	 */
	private int  quantity;
	
	/**
	 * FALSE    批次号
	 */
	private String batchCode;
	
	/**
	 * FALSE   sn编码
	 */
	private String  snCode;
	
	/**
	 * FALSE  运单号
	 * 需要回传sn时，如果有运单号时必传，其他不涉及sn的业务忽略
	 */
	private String  tmsOrderCode;
	
	/**
	 * FALSE   包裹号
	 * 需要回传sn时，
	 * 如果有包裹号时必传，
	 * 其他不涉及sn的业务忽略
	 */
	private String packageCode;

	public StockOutOrderItem getStockOutOrderItem() {
		return stockOutOrderItem;
	}

	public void setStockOutOrderItem(StockOutOrderItem stockOutOrderItem) {
		this.stockOutOrderItem = stockOutOrderItem;
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

	public String getTmsOrderCode() {
		return tmsOrderCode;
	}

	public void setTmsOrderCode(String tmsOrderCode) {
		this.tmsOrderCode = tmsOrderCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	
	
	
}
