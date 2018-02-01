package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单退货处理
 * @author lenovo
 *
 */
@Entity
@Table(name = "sc_shiporder_return")
public class ShipOrderReturn implements Serializable{

	private static final long serialVersionUID = -6806332084455412910L;

	private int id;
	
	private String orderId;  //订单ID
	
	private String userId;//商家
	
	private String expressOrderno; //快递单号
	
	private Date createDate; //创建日期
	
	private String remark;//
	
	private String message;//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
