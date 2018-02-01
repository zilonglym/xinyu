package com.graby.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.drools.core.util.StringUtils;
import org.hibernate.annotations.Index;


/**
 * 订单批次
 * @author huabiao.mahb
 */
@Entity
@Table(name = "sc_trade_batch")
public class TradeBatch  implements Serializable{
	
	private static final long serialVersionUID = -2639867449275956584L;
	
	private Long id;//ID 
	
	private Long userId;//商家ID
	
	private String userName;//商家名称
	
	private Long  centroId;//仓库编码
	
	private String centroName;//仓库名称
	
	private String expressCompany;//快递公司编 号
	
	private Long itemId;//批次商品ID
	
	
	private String itemName;//批次商品名称
	
	private Date date;//批次日期
	
	private String no;//批次号
	
	private int quantity;//批次总数量
	
	private int num;// 批次已打数量
	
	private TradeBatchStatusEnum status;//状态 
	
	
	public enum TradeBatchStatusEnum{
		PRINTING,//进行中
		COMPLETE;//完成
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}


	public String getExpressCompany() {
		return expressCompany;
	}


	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Long getCentroId() {
		return centroId;
	}


	public void setCentroId(Long centroId) {
		this.centroId = centroId;
	}


	public String getCentroName() {
		return centroName;
	}


	public void setCentroName(String centroName) {
		this.centroName = centroName;
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public TradeBatchStatusEnum getStatus() {
		return status;
	}


	public void setStatus(TradeBatchStatusEnum status) {
		this.status = status;
	}

}


