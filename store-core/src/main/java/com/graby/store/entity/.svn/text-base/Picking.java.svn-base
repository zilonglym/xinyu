package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分拣单
 * @author lenovo
 *
 */
@Entity
@Table(name = "sc_centro_picking")
public class Picking implements Serializable{
	
	private static final long serialVersionUID = 7828317075483976345L;
	
	private int id;
	
	private String number;//单据编号
	
	private String status;//状态，是否确认 normal/confirm
	
	private String description;//描述
	
	private Date createDate;//创建时间
	
	private Person createBy;//创建人
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public Person getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Person createBy) {
		this.createBy = createBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
