package com.xinyu.model.trade;

import java.util.Date;

import com.xinyu.model.common.Entity;

/**
 * 发货拦截
 * 
 * @author yangmin 2017年5月11日
 *
 */
public class ShipOrderStop extends Entity {

	private Date createTime;

	/**
	 * 单据编号
	 */
	private String orderId;

	/**
	 * 拦截原因
	 */
	private String description;
	/**
	 * 拦截商家
	 */
	private String userId;
	/**
	 * 快递单号
	 */
	private String expressOrderno;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getExpressOrderno() {
		return expressOrderno;
	}

	public void setExpressOrderno(String expressOrderno) {
		this.expressOrderno = expressOrderno;
	}

}
