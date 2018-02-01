package com.xinyu.model.base;

import java.util.Date;

import com.xinyu.model.common.Entity;
import com.xinyu.model.trade.ShipOrder;

/**
 * 订单配送要求信息
 * 
 * @author yangmin 2017年4月25日
 *
 */
public class DeliverRequirements extends Entity {
	/**
	 * 投递时延要求( 1工作日 2 节假日 101当日达 102次晨达 103次日达 111 活动标 104 预约达 10101当日下午达
	 * 10102当日夜间达 10301次日上午达 )
	 */
	private Integer scheduleType;
	/**
	 * 送达日期（格式为 YYYYMMDD) scheduleDay
	 */
	private Date scheduleDay;
	/**
	 * 送达开始时间（格式为 hh:mm:ss） scheduleStartTime
	 */
	private Date scheduleStart;
	/**
	 * 送达结束时间（格式为 hh:mm:ss） scheduleEndTime
	 */
	private Date scheduleEnd;

	private ShipOrder order;

	private java.lang.String deliveryType;

	private java.lang.String scheduleArriveTime;

	public Integer getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(Integer scheduleType) {
		this.scheduleType = scheduleType;
	}

	public Date getScheduleDay() {
		return scheduleDay;
	}

	public void setScheduleDay(Date scheduleDay) {
		this.scheduleDay = scheduleDay;
	}

	public Date getScheduleStart() {
		return scheduleStart;
	}

	public void setScheduleStart(Date scheduleStart) {
		this.scheduleStart = scheduleStart;
	}

	public Date getScheduleEnd() {
		return scheduleEnd;
	}

	public void setScheduleEnd(Date scheduleEnd) {
		this.scheduleEnd = scheduleEnd;
	}

	public ShipOrder getOrder() {
		return order;
	}

	public void setOrder(ShipOrder order) {
		this.order = order;
	}
	
	

	public java.lang.String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(java.lang.String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public java.lang.String getScheduleArriveTime() {
		return scheduleArriveTime;
	}

	public void setScheduleArriveTime(java.lang.String scheduleArriveTime) {
		this.scheduleArriveTime = scheduleArriveTime;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
