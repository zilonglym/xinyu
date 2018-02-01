package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物流利润
 * */
@Entity
@Table(name = "sc_express_profits")
public class CompanyProfits implements Serializable{
	
	private static final long serialVersionUID = 7855809701869500049L;	
	//id
	private Long id;
	//时间
	private Date date;
	//快递费用（应收）
	private Double expressIncome;
	//面单费用（应收）
	private Double planeIncome;
	//总计（应收）
	private Double totalIncome;
	//快递费用（应付）
	private Double expressExpend;
	//发件费
	private Double sendFee;
	//包仓费
	private Double warehouseFee;
	//面单费用（应付）
	private Double planeExpend;	
	//总成本
	private Double totalCost;
	//总利润
	private Double totalProfits;
	//单量
	private Double num;	
	//商家名
	private String userName;
	//物流公司
	private String expressCompany;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getExpressIncome() {
		return expressIncome;
	}
	public void setExpressIncome(Double expressIncome) {
		this.expressIncome = expressIncome;
	}
	public Double getPlaneIncome() {
		return planeIncome;
	}
	public void setPlaneIncome(Double planeIncome) {
		this.planeIncome = planeIncome;
	}
	public Double getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public Double getExpressExpend() {
		return expressExpend;
	}
	public void setExpressExpend(Double expressExpend) {
		this.expressExpend = expressExpend;
	}
	public Double getSendFee() {
		return sendFee;
	}
	public void setSendFee(Double sendFee) {
		this.sendFee = sendFee;
	}
	public Double getWarehouseFee() {
		return warehouseFee;
	}
	public void setWarehouseFee(Double warehouseFee) {
		this.warehouseFee = warehouseFee;
	}
	public Double getPlaneExpend() {
		return planeExpend;
	}
	public void setPlaneExpend(Double planeExpend) {
		this.planeExpend = planeExpend;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public Double getTotalProfits() {
		return totalProfits;
	}
	public void setTotalProfits(Double totalProfits) {
		this.totalProfits = totalProfits;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
}
