package com.xinyu.model.trade;

import java.util.Date;

import com.xinyu.model.common.Entity;

/**
 * 拆单打包发货
 * @author yangmin
 * 2017年9月23日
 *
 */
public class ShipOrderTmsOrder extends Entity{

	private ShipOrder shipOrder;
	
	private double packageLength;
	
	private double packageWidth;
	
	private double packageVolume;
	
	private double packageHeight;
	
	private double packageWeight;
	
	private double packageCode;
	
	private String tmsCode;
	
	private String tmsOrderCode;
	
	private Date createDate;
	
	private String otherOrderNo;

	public ShipOrder getShipOrder() {
		return shipOrder;
	}

	public void setShipOrder(ShipOrder shipOrder) {
		this.shipOrder = shipOrder;
	}

	public double getPackageLength() {
		return packageLength;
	}

	public void setPackageLength(double packageLength) {
		this.packageLength = packageLength;
	}

	public double getPackageWidth() {
		return packageWidth;
	}

	public void setPackageWidth(double packageWidth) {
		this.packageWidth = packageWidth;
	}

	public double getPackageVolume() {
		return packageVolume;
	}

	public void setPackageVolume(double packageVolume) {
		this.packageVolume = packageVolume;
	}

	public double getPackageHeight() {
		return packageHeight;
	}

	public void setPackageHeight(double packageHeight) {
		this.packageHeight = packageHeight;
	}

	public double getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(double packageWeight) {
		this.packageWeight = packageWeight;
	}

	public double getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(double packageCode) {
		this.packageCode = packageCode;
	}

	public String getTmsCode() {
		return tmsCode;
	}

	public void setTmsCode(String tmsCode) {
		this.tmsCode = tmsCode;
	}

	public String getTmsOrderCode() {
		return tmsOrderCode;
	}

	public void setTmsOrderCode(String tmsOrderCode) {
		this.tmsOrderCode = tmsOrderCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOtherOrderNo() {
		return otherOrderNo;
	}

	public void setOtherOrderNo(String otherOrderNo) {
		this.otherOrderNo = otherOrderNo;
	}
	
	
}
