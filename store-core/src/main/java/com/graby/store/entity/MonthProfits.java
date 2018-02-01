package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公司月利润统计
 * */
@Entity
@Table(name = "sc_month_profits")
public class MonthProfits implements Serializable{
	
	private static final long serialVersionUID = 981755267881595757L;
	//id
	private Long id;
	//时间
	private Date dateTime;
	//主营业务收入
	private Double mainIncome;
	//主营业务成本
	private Double mainCost;
	// 发个人件及接货费
	private Double deliveryFee;
	// 废纸箱
	private Double basketFee;
	//本月打包费
	private Double packingFee;
	//物料,防水袋胶带
	private Double materielFee;
	//破损,漏发等
	private Double damagedFee;
	//财务费用
	private Double financeFee;
	//房租,物业费
	private Double propertyFee;
	//水电
	private Double waterFee;
	//其他办公费用
	private Double officeFee;
	// 伙食费
	private Double boardFee;
	//车辆费用
	private Double vehicleFee;
	//固定、无形资产待摊
	private Double assetsFee;
	//招待费
	private Double hospitalityFee;
	//应交税金
	private Double taxFee;
	//保险+工会经费
	private Double insuranceFee;
	//工资
	private Double wagesFee;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getMainIncome() {
		return mainIncome;
	}
	public void setMainIncome(Double mainIncome) {
		this.mainIncome = mainIncome;
	}
	public Double getMainCost() {
		return mainCost;
	}
	public void setMainCost(Double mainCost) {
		this.mainCost = mainCost;
	}
	public Double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Double getBasketFee() {
		return basketFee;
	}
	public void setBasketFee(Double basketFee) {
		this.basketFee = basketFee;
	}
	public Double getPackingFee() {
		return packingFee;
	}
	public void setPackingFee(Double packingFee) {
		this.packingFee = packingFee;
	}
	public Double getMaterielFee() {
		return materielFee;
	}
	public void setMaterielFee(Double materielFee) {
		this.materielFee = materielFee;
	}
	public Double getDamagedFee() {
		return damagedFee;
	}
	public void setDamagedFee(Double damagedFee) {
		this.damagedFee = damagedFee;
	}
	public Double getFinanceFee() {
		return financeFee;
	}
	public void setFinanceFee(Double financeFee) {
		this.financeFee = financeFee;
	}
	public Double getPropertyFee() {
		return propertyFee;
	}
	public void setPropertyFee(Double propertyFee) {
		this.propertyFee = propertyFee;
	}
	public Double getWaterFee() {
		return waterFee;
	}
	public void setWaterFee(Double waterFee) {
		this.waterFee = waterFee;
	}
	public Double getOfficeFee() {
		return officeFee;
	}
	public void setOfficeFee(Double officeFee) {
		this.officeFee = officeFee;
	}
	public Double getBoardFee() {
		return boardFee;
	}
	public void setBoardFee(Double boardFee) {
		this.boardFee = boardFee;
	}
	public Double getVehicleFee() {
		return vehicleFee;
	}
	public void setVehicleFee(Double vehicleFee) {
		this.vehicleFee = vehicleFee;
	}
	public Double getAssetsFee() {
		return assetsFee;
	}
	public void setAssetsFee(Double assetsFee) {
		this.assetsFee = assetsFee;
	}
	public Double getHospitalityFee() {
		return hospitalityFee;
	}
	public void setHospitalityFee(Double hospitalityFee) {
		this.hospitalityFee = hospitalityFee;
	}
	public Double getTaxFee() {
		return taxFee;
	}
	public void setTaxFee(Double taxFee) {
		this.taxFee = taxFee;
	}
	public Double getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(Double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public Double getWagesFee() {
		return wagesFee;
	}
	public void setWagesFee(Double wagesFee) {
		this.wagesFee = wagesFee;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}
