package com.graby.store.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.graby.store.entity.enums.StorageStatusEnums;

/**
 * 出入库单  ,单独拿开
 * @author lenovo
 *
 */
@Entity
@Table(name = "sc_storage")
public class Storage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6502434344437652329L;
	
	private Long id;
	
	private String orderNo; //出入库编号
	
	private StorageStatusEnums status;//出入库状态 现阶段只用ENTRY_FINISH 一个状态
	
	private String description;//描述
	
	private User user;//出入库操作人员
	
	private Person operator;//出入库操作人员
	
	private  Date operateTime;//出入库时间
	
	private Centro centro;//出入库仓库
	
	private CentroItem centroItem;
	
	
	/**
	 * 冗余字段
	 */
	public List<StorageItem> items;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public StorageStatusEnums getStatus() {
		return status;
	}

	public void setStatus(StorageStatusEnums status) {
		this.status = status;
	}

	
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}
	
	@Transient
	public List<StorageItem> getItems() {
		return items;
	}

	public void setItems(List<StorageItem> items) {
		this.items = items;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CentroItem getCentroItem() {
		return centroItem;
	}

	public void setCentroItem(CentroItem centroItem) {
		this.centroItem = centroItem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Person getOperator() {
		return operator;
	}

	public void setOperator(Person operator) {
		this.operator = operator;
	}

}
