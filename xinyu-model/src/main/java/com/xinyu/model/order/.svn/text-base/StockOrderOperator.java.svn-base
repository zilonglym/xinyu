package com.xinyu.model.order;

import java.util.Date;

import com.xinyu.model.common.Entity;
import com.xinyu.model.order.enums.StockOperateTypeEnum;
import com.xinyu.model.system.Account;


/**
 * 库存单据操作日志
 * */
public class StockOrderOperator extends Entity{
	
	//操作账号
	private Account account;
	
	//操作单据ID
	private String orderId;
	
	//操作单据类型
	private String orderType;
	
	//操作类型
	private StockOperateTypeEnum operateType;
	
	//操作时间
	private Date operateDate;

	//修改之前的值
	private String oldValue;
	
	//修改之后的值
	private String newValue;
	
	//其它描述
	private String description;
	
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public StockOperateTypeEnum getOperateType() {
		return operateType;
	}

	public void setOperateType(StockOperateTypeEnum operateType) {
		this.operateType = operateType;
	}

	public String getOrderId() {
		return orderId;
	}	

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
}
