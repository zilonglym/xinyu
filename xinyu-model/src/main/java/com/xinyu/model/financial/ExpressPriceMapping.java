package com.xinyu.model.financial;

import com.xinyu.model.common.Entity;

/**
 * 订单匹配的费用
 * @author Administrator
 *
 */
public class ExpressPriceMapping extends Entity{
	
	private String orderId;//对应订单ID
	
	private Double firstWeight;//首重(总)
	
	private Double firstPrice;//首重费用(总)
	
	private Double initialWeight;//续重(总)
	
	private Double initialPrice;//续重费用(总)
	
	private Double otherPrice;//其他费用(总)
	
	private Double totalPrice;//运费(总)

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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
