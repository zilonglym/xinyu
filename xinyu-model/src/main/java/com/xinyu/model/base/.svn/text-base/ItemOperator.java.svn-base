package com.xinyu.model.base;

import java.util.Date;

import com.xinyu.model.common.Entity;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;

/**
 * 商品操作日志
 * */
public class ItemOperator extends Entity{
	
	/**
	 * 操作商品
	 */
	private Item item;
	/**
	 * 操作日期
	 */
	private Date operatorDate;
	/**
	 * 操作帐号
	 */
	private Account account;
	/**
	 * 操作方式（商品同步，商品创建，商品更新）
	 */
	private OperatorModel operatorModel;
	/**
	 * 修改之前的值
	 */
	private String oldValue;
	/**
	 * 修改之后的值
	 */
	private String newValue;
	/**
	 * 其它描述
	 */
	private String description;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Date getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public OperatorModel getOperatorModel() {
		return operatorModel;
	}
	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
