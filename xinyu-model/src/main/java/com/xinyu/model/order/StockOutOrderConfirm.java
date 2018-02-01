package com.xinyu.model.order;

import java.util.Date;
import java.util.List;

import com.xinyu.model.base.PackageInfo;
import com.xinyu.model.common.Entity;
import com.xinyu.model.order.child.StockOutCheckItem;
import com.xinyu.model.order.child.StockOutOrderItem;
import com.xinyu.model.order.enums.OutOrderTypeEnum;

/**
 * @author shark_cj
 * @since  2017-05-03
 * 出库确认
 */
public class StockOutOrderConfirm  extends  Entity{
	
	/**
	 * TRUE     仓储中心出库订单编码
	 */
	private String  orderCode;
	
	/**
	 *  TRUE    orderType
	 *  需适配菜鸟单据类型
	 */
	private OutOrderTypeEnum  orderType;
	
	/**
	 * TRUE    外部业务编码
	 * 物流宝用来去重，
	 * 需要每次确认都不一样，
	 * 同一次确认重试需要一样	
	 * outBizCode
	 */
	private String outBizCode;
	
	/**
	 *  true   支持出库单多次确认
	 *  0 最后一次确认或是一次性确认； 
	 *  1 多次确认；
	 *  默认值为 0 例如输入2认为是0	
	 *  confirmType
	 */
	private int  confirmType;
	
	/**
	 * TRUE    仓库出库单完成时间
	 * orderConfirmTime
	 */
	private Date orderConfirmTime;
	
	/**
	 * false    时区
	 */
	private String timeZone;
	
	/**
	 * false  出库单商品信息列表
	 * orderLines
	 */
	private  List<StockOutOrderItem>   orderItems;
	
	/**
	 * FALSE  包裹列表	
	 * packages
	 */
	private   List<PackageInfo>	 packageInfos;
	/**
	 * false   出库单商品校验信息列表
	 */
	private List<StockOutCheckItem>  checkItems;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public OutOrderTypeEnum getOrderType() {
		return orderType;
	}
	public void setOrderType(OutOrderTypeEnum orderType) {
		this.orderType = orderType;
	}
	public String getOutBizCode() {
		return outBizCode;
	}
	public void setOutBizCode(String outBizCode) {
		this.outBizCode = outBizCode;
	}
	public int getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(int confirmType) {
		this.confirmType = confirmType;
	}
	public Date getOrderConfirmTime() {
		return orderConfirmTime;
	}
	public void setOrderConfirmTime(Date orderConfirmTime) {
		this.orderConfirmTime = orderConfirmTime;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	public List<PackageInfo> getPackageInfos() {
		return packageInfos;
	}
	public void setPackageInfos(List<PackageInfo> packageInfos) {
		this.packageInfos = packageInfos;
	}
	public List<StockOutCheckItem> getCheckItems() {
		return checkItems;
	}
	public void setCheckItems(List<StockOutCheckItem> checkItems) {
		this.checkItems = checkItems;
	}
	public List<StockOutOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<StockOutOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
