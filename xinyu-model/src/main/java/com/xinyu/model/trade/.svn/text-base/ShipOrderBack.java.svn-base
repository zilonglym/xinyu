package com.xinyu.model.trade;

import java.util.Date;

import com.xinyu.model.common.Entity;
import com.xinyu.model.system.Account;

/**
 * 仓库退回货物登记
 * @author yangmin
 * 2017年12月28日
 *
 *  如果在仓库能找到相应的寄出订单，则直接关联订单，并从订单中找出相应的商家与快递单号等信息写入进来。
 *  如果不能找到，就直接填写相应的字段，orderId则为空。
 *  原因字段可以做一个选择框下面加一个输入框，选择框里面放一些常用的原因，比如发货错之类的。选中后直接把内容写到下面的输入框
 */
public class ShipOrderBack extends Entity{

	/**
	 * 相应的订单
	 */
	private String orderId;
	
	/**
	 * 寄出快递单号
	 */
	private String tmsOrderCode;
	/**
	 * 退回快递单号
	 */
	private String backOrderCode;
	/**
	 * 退回原因
	 */
	private String description;
	/**
	 * 所属商家
	 */
	private String userId;
	/**
	 * 登记日期
	 */
	private Date createDate;
	/**
	 * 创建人员
	 */
	private Account createBy;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTmsOrderCode() {
		return tmsOrderCode;
	}
	public void setTmsOrderCode(String tmsOrderCode) {
		this.tmsOrderCode = tmsOrderCode;
	}
	public String getBackOrderCode() {
		return backOrderCode;
	}
	public void setBackOrderCode(String backOrderCode) {
		this.backOrderCode = backOrderCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Account getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Account createBy) {
		this.createBy = createBy;
	}
	
}
