package com.graby.store.entity.local;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sc_local_operator")
public class LocalOperateRecord implements Serializable{

	private static final long serialVersionUID = 2034918137018850059L;
	
	private int id;
	
	//单据编号
	private String batchCode;
	
	//货位编号
	private String fastCode;
	
	//操作人ID
	private String operateId;
	
	/**
	 * 操作类型
	 * DOWN:下架
	 * UP:上架
	 * CREATE:创建
	 * PRINT:打印
	 */
	private String model;
	
	//操作信息
	private String description;
	
	//操作时间
	private Date createDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getFastCode() {
		return fastCode;
	}

	public void setFastCode(String fastCode) {
		this.fastCode = fastCode;
	}

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
