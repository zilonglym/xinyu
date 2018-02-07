package com.xinyu.check.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单退货处理
 * @author lenovo
 */
public class ShipOrderReturn implements Serializable{

	private static final long serialVersionUID = -6806332084455412910L;

	private int id;
	
	private String orderId;  //订单ID
	
	private String userId;//商家
	
	private String expressOrderno; //快递单号
	
	private Date createDate; //创建日期
	
	private String remark;//
	
	private String message;//

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
