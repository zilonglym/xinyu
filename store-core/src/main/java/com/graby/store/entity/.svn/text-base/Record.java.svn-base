package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * excel表格文件记录
 */
@Entity
@Table(name = "sc_record")
public class Record implements Serializable{
	
	private static final long serialVersionUID = 7855809701869500049L;
	//id
	private Long id;
	//创建时间
	private Date createTime;
	//商家名
	private String userName;
	//物流公司
	private String expressCompany;
	//物流单号
	private String expressOrderNo;
	//省份
	private String state;
	//目的地
	private String center;
	//扫描重量
	private Double weight;
	//运费
	private Double price;
	//成本
	private Double cost;
	//导入时间
	private Date importTime;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getExpressOrderNo() {
		return expressOrderNo;
	}

	public void setExpressOrderNo(String expressOrderNo) {
		this.expressOrderNo = expressOrderNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
}
