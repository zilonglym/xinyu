package com.xinyu.model.trade;

import java.util.Date;

import com.xinyu.model.common.Entity;
import com.xinyu.model.system.Person;
import com.xinyu.model.system.enums.OrderStatusEnum;

/**
 * 订单发货实体，用于存放包裹信息
 * 
 * @author yangmin
 * 2017年11月15日
 *
 */
public class TmsOrder extends Entity {
	
	public final static String split="split";
	public final static String split_detail="split_detail";
	
	
	private ShipOrder order;
	/**
	 * 状态
	 */
	private OrderStatusEnum orderStatus;
	/**
	 * 快递公司编 码显示,比如YUNDA411353...
	 */
	private String displayCode;
	/**
	 * 快递公司编码
	 */
	private String code;
	/**
	 * 快递单号
	 */
	private String orderCode;
	
	private String packageCode;
	/**
	 * 单据状态
	 */
	private String status;
	
	private double packageLength;
	private double packageWidth;
	private double packageVolume;
	private double packageWeight;
	private double packageHeight;	
	private Date createDate;
	
	private String templateURL;
	private String routeCode;
	private String sortationName;
	private String consolidationName;
	private String consolidationCode;
	private String batchCode;
	private String orderFlag;
	private String items;
	/**
	 * 地址状态，是否包含乡镇村组
	 */
	private String addressStatus;
	/**
	 * 冗余字段，收件人省份，便于查询
	 */
	private String receiverProvince;
	
	
	public ShipOrder getOrder() {
		return order;
	}
	public void setOrder(ShipOrder order) {
		this.order = order;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
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
	public double getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(double packageWeight) {
		this.packageWeight = packageWeight;
	}
	public double getPackageHeight() {
		return packageHeight;
	}
	public void setPackageHeight(double packageHeight) {
		this.packageHeight = packageHeight;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTemplateURL() {
		return templateURL;
	}
	public void setTemplateURL(String templateURL) {
		this.templateURL = templateURL;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getSortationName() {
		return sortationName;
	}
	public void setSortationName(String sortationName) {
		this.sortationName = sortationName;
	}
	public String getConsolidationName() {
		return consolidationName;
	}
	public void setConsolidationName(String consolidationName) {
		this.consolidationName = consolidationName;
	}
	public String getConsolidationCode() {
		return consolidationCode;
	}
	public void setConsolidationCode(String consolidationCode) {
		this.consolidationCode = consolidationCode;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDisplayCode() {
		return displayCode;
	}
	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}
	public String getAddressStatus() {
		return addressStatus;
	}
	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}
	public String getReceiverProvince() {
		return receiverProvince;
	}
	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}
}
