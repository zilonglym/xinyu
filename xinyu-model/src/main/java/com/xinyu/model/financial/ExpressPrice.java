package com.xinyu.model.financial;

import com.xinyu.model.common.Entity;

/**
 * 快递费用设置
 * @author Administrator
 *
 */
public class ExpressPrice extends Entity{
	
	private String area;//区域:area
	
	private String userId;//用户id:user_id
	
	private String code;//快递编码:code
	
	private String name;//快递名:name
	
	private Double firstPrice;//首重价格:first_price
	
	private Double initialPrice;//续重价格:initial_price
	
	private Double otherPrice;//其他价格:other_price
	
	private Double deliveryPrice;//派送价格:delivery_price
	
	private Double firstCost;//首重:first_cost
	
	private Double initialCost;//续重:initial_cost
	
	private Double otherCost;//仓储费用:other_cost
	
	private Double deliveryCost;//打包费用:delivery_cost

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	
}
