package com.xinyu.model.order.child;

import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
/**
 * @author shark_cj
 * <pre>
 *    装箱明细信息
 * </pre>
 */
public class WmsStockInCaseItem  extends Entity{

	
	private WmsStockInCaseInfo wmsStockInCaseInfo;
	
	/**
	 *  TRUE  商品ID
	 */
	private String  itemId;
	
	/**
	 * FALSE 商品名称
	 */
	private String  itemName;
	
	/**
	 *  FALSE  商家对商品的编码
	 */
	private String  itemCode;

	/**
	 * false  条形码
	 * 多条码请用”;”分隔；仓库入库扫码使用
	 */
	private String  barCode;

	/**
	 * TRUE  库存类型
	 * 注意：采购入库单下发的库存类型是301
	 */
	private InventoryTypeEnum  inventoryType;
	
	/**
	 *  TRUE    商品数量
	 */
	private int  itemQuantity;
	
	/**
	 * TRUE 商品版本
	 */
	private  long  itemVersion;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	public long getItemVersion() {
		return itemVersion;
	}
	public void setItemVersion(long itemVersion) {
		this.itemVersion = itemVersion;
	}
	public WmsStockInCaseInfo getWmsStockInCaseInfo() {
		return wmsStockInCaseInfo;
	}
	public void setWmsStockInCaseInfo(WmsStockInCaseInfo wmsStockInCaseInfo) {
		this.wmsStockInCaseInfo = wmsStockInCaseInfo;
	}
}
