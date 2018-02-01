package com.xinyu.model.base;

import com.xinyu.model.common.Entity;
/**
 * 发票明细
 * @author yangmin
 * 2017年4月25日
 *
 */
public class InvoiceItemDetail extends Entity {

	/**
	 * 发票主体
	 */
	private InvoiceInfo invoice;
	
	private String taxCode;
	
	/**
	 * TRUE	　	商品名称	itemName
	 */
	private String itemName;
	/**
	 * FALSE		规格型号
	 */
	private String specificModel;
	/**
	 * TRUE	　	数量单位
	 */
	private String unit;
	/**
	 * FALSE	　	单价,单位分(增值税发票可以为空)
	 */
	private double price;
	/**
	 * TRUE	　	数量
	 */
	private long quantity;
	/**
	 * TRUE	　	发票明细总金额,单位分
	 */
	private double amount;
	/**
	 * FALSE		税率(增值发票必填)
	 */
	private double taxRate;
	/**
	 * FALSE		税额
	 */
	private double taxAmount;
	public InvoiceInfo getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceInfo invoice) {
		this.invoice = invoice;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSpecificModel() {
		return specificModel;
	}
	public void setSpecificModel(String specificModel) {
		this.specificModel = specificModel;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
}
