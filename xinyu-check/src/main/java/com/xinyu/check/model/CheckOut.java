package com.xinyu.check.model;

import java.io.Serializable;
import java.util.Date;


public class CheckOut implements Serializable{

	private static final long serialVersionUID = 3535102389229723879L;

	private Long id;
	
	private Date createDate;
	
	private String  orderCode;
	
	private Long orderId;//快递条码
	
	private String cpCompany;//快递公司 
	
	private Long centroId;//仓库编码
	
	private String barCode;//商品编码
	
	private Long itemId;
	
	private String itemName;//商品名称
	
	private Long userId; //商家ID
	
	private Long personId; //操作人员ID
	
	private Long num; //数量
	
	private String msg;
	
	private  String status;
	
	private  String  state;//省份
	
	private Double weight; //包裹 重量
	
	public String getCpCompany() {
		return cpCompany;
	}

	public void setCpCompany(String cpCompany) {
		this.cpCompany = cpCompany;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public interface CheckOutStatus {
		/** 成功。*/
		public static final String SUCCESS_GOODS = "SUCCESS_GOODS";
		
		/**订单验证成功*/
		public static final String SUCCESS_TRADE = "SUCCESS_TRADE";
		
		/**补录验证成功*/
		public static final String SUCCESS_ADD = "SUCCESS_ADD";
		/**补录验证失败*/
		public static final String FAIL_ADD = "FAIL_ADD";
		
		/** 失败。*/
		public static final String FAIL_TRADE = "FAIL_TRADE";
		
		/** 失败。*/
		public static final String FAIL_GOODS = "FAIL_GOODS";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCentroId() {
		return centroId;
	}

	public void setCentroId(Long centroId) {
		this.centroId = centroId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}
