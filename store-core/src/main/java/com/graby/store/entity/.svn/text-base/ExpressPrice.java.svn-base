package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 快递价格计算规则
 */
@Entity
@Table(name = "sc_express_price")
public class ExpressPrice implements Serializable{
	
	private static final long serialVersionUID = 7855809701869500049L;
	
	private Long id;//id:id
	
	private String area;//区域:area
	
	private Long userId;//用户id:user_id
	
	private String code;//快递编码:code
	
	private String name;//快递名:name
	
	private String userName;//店铺名:user_name
	
	private Double firstPrice;//首重价格:first_price
	
	private Double initialPrice;//续重价格:initial_price
	
	private Double otherPrice;//其他价格:other_price
	
	private Double deliveryPrice;//派送价格:delivery_price
	
	private Double firstCost;//首重:first_cost
	
	private Double initialCost;//续重:initial_cost
	
	private Double otherCost;//仓储费用:other_cost
	
	private Double deliveryCost;//打包费用:delivery_cost
	
	public Double getFirstCost() {
		return firstCost;
	}

	public void setFirstCost(Double firstCost) {
		this.firstCost = firstCost;
	}

	public Double getInitialCost() {
		return initialCost;
	}

	public void setInitialCost(Double initialCost) {
		this.initialCost = initialCost;
	}

	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public Double getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Double firstPrice) {
		this.firstPrice = firstPrice;
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

	public Double getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	
}
