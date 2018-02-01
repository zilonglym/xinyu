package com.xinyu.model.trade;

import java.util.Date;

import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.system.enums.OrderStatusEnum;

/**
 * 订单统计对象
 * @author yangmin
 * 2017年11月29日
 *
 */
public class ShipOrderReport extends Entity {

	private ShipOrder order;
	
	/**
	 * 快递公司
	 */
	private String expressCompany;
	/**
	 * 快递单号
	 */
	private String expressOrderNo;
	/**
	 * 商家
	 */
	private User user;
	/**
	 * 订单创建时间
	 */
	private Date orderCreateTime;
	/**
	 * 订单确认发货时间
	 */
	private Date confirmDate;
	/**
	 * 制单时间(单位秒)
	 */
	private int printTime;
	
	/**
	 * 订单确认发货时间
	 */
	private Date checkOrderTime;
	
	/**
	 * 操作部操作时间
	 */
	private int operatorTime;
	/**
	 * 单据状态
	 */
	private OrderStatusEnum status;
	
	/**
	 * 物流信息
	 */
	private String logistInfo;
	
	private Date createTime;
	

	public ShipOrder getOrder() {
		return order;
	}

	public void setOrder(ShipOrder order) {
		this.order = order;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressOrderNo() {
		return expressOrderNo;
	}

	public void setExpressOrderNo(String expressOrderNo) {
		this.expressOrderNo = expressOrderNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public int getPrintTime() {
		return printTime;
	}

	public void setPrintTime(int printTime) {
		this.printTime = printTime;
	}

	public Date getCheckOrderTime() {
		return checkOrderTime;
	}

	public void setCheckOrderTime(Date checkOrderTime) {
		this.checkOrderTime = checkOrderTime;
	}

	public int getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(int operatorTime) {
		this.operatorTime = operatorTime;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public String getLogistInfo() {
		return logistInfo;
	}

	public void setLogistInfo(String logistInfo) {
		this.logistInfo = logistInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
