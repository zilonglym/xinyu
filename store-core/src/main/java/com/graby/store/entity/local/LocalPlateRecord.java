package com.graby.store.entity.local;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.graby.store.entity.Item;


@Entity
@Table(name = "sc_local_plate_record")
public class LocalPlateRecord implements Serializable{
	
	private static final long serialVersionUID = 8304842498610380820L;

	private int  id;
	
 	private LocalPlate localPlate;
 	
 	private String state;
 	
 	private String item;
	
	private Long  num;
	
	private Date  createDate; 
	
	private String   opertionId;
	
	private String   batchId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="local_plate_id")
	public LocalPlate getLocalPlate() {
		return localPlate;
	}

	public void setLocalPlate(LocalPlate localPlate) {
		this.localPlate = localPlate;
	}

	
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOpertionId() {
		return opertionId;
	}

	public void setOpertionId(String opertionId) {
		this.opertionId = opertionId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
}
