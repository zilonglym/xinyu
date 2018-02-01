package com.xinyu.model.trade;

import java.util.Date;

import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.Person;

/**
 * 出库扫描记录
 * */
public class CheckOut extends Entity{
	
	private Item item;//商品
	
	private ShipOrder shipOrder;//订单
	
	private Date createDate;//出库时间
	
	private String  orderCode;//快递条码
	
	private String cpCompany;//快递公司 
	
	private String barCode;//商品编码
	
	private String itemName;//商品名称
	
	private User user; //商家
	
	private Account account; //操作人员
	
	private Long num; //数量
	
	private String msg;//出库信息
	
	private  String status;//状态
	
	private  String  state;//省份
	
	private Double weight; //包裹 重量
	
	private String orderDate;//订单时间
	
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCpCompany() {
		return cpCompany;
	}

	public void setCpCompany(String cpCompany) {
		this.cpCompany = cpCompany;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ShipOrder getShipOrder() {
		return shipOrder;
	}

	public void setShipOrder(ShipOrder shipOrder) {
		this.shipOrder = shipOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	
}
