package com.xinyu.model.base;

import java.util.List;

import com.xinyu.model.common.Entity;

/**
 * 发票信息
 * 
 * @author yangmin 2017年4月25日
 *
 */
public class InvoiceInfo extends Entity {

	/**
	 * TRUE 发票类型(1营业税发票（普通）3增值税发票) type 需WMS按照菜鸟规范，与奇门字段枚举值不一致
	 * 
	 */
	private String billType;

	/**
	 * TRUE	　	发票ID，发票去重使用，一个订单可以有多张发票，LBX+billId是唯一的
	 */
	private Long billId;
	/**
	 * TRUE	　	发票抬头
	 */
	private String billTitle;
	/**
	 * TRUE	　	发票金额，单位：分	amount
	 */
	private String billAccount;
	/**
	 * FALSE		购方纳税人识别号
	 */
	private String buyerNo;
	/**
	 * FALSE		购方地址、电话
	 */
	private String buyerAddrPhone;
	/**
	 * FALSE		购方开户行及帐号
	 */
	private String buyerBankAccount;
	/**
	 * FALSE	　	发票内容,发票内容和明细二选一
	 */
	private String billContent;
	/**
	 * List<ItemDetail>	　	FALSE	　	发票明细列表,发票内容和明细二选一
	 */
	private List<InvoiceItemDetail>	 detailList;
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public String getBillTitle() {
		return billTitle;
	}
	public void setBillTitle(String billTitle) {
		this.billTitle = billTitle;
	}
	public String getBillAccount() {
		return billAccount;
	}
	public void setBillAccount(String billAccount) {
		this.billAccount = billAccount;
	}
	public String getBuyerNo() {
		return buyerNo;
	}
	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}
	public String getBuyerAddrPhone() {
		return buyerAddrPhone;
	}
	public void setBuyerAddrPhone(String buyerAddrPhone) {
		this.buyerAddrPhone = buyerAddrPhone;
	}
	public String getBuyerBankAccount() {
		return buyerBankAccount;
	}
	public void setBuyerBankAccount(String buyerBankAccount) {
		this.buyerBankAccount = buyerBankAccount;
	}
	public String getBillContent() {
		return billContent;
	}
	public void setBillContent(String billContent) {
		this.billContent = billContent;
	}
	public List<InvoiceItemDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<InvoiceItemDetail> detailList) {
		this.detailList = detailList;
	}
	
}
