package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 运费映射
 * 一个订单对应一个运费
 */
@Entity
@Table(name = "sc_express_price_mapping")
public class ExpressPriceMaping implements Serializable{
	
	private static final long serialVersionUID = -5858869718493852851L;
	
	private Long id;//id
	
	private Long orderId;//对应订单ID
	
	private Double firstWeight;//首重
	
	private Double firstPrice;//首重费用
	
	private Double initialWeight;//续重
	
	private Double initialPrice;//续重费用
	
	private Double otherPrice;//其他费用
	
	private Double totalPrice;//运费
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(Double firstWeight) {
		this.firstWeight = firstWeight;
	}

	public Double getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Double firstPrice) {
		this.firstPrice = firstPrice;
	}

	public Double getInitialWeight() {
		return initialWeight;
	}

	public void setInitialWeight(Double initialWeight) {
		this.initialWeight = initialWeight;
	}

	public Double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Double getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(Double otherPrice) {
		this.otherPrice = otherPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
