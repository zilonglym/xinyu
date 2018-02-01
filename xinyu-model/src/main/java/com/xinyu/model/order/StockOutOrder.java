package com.xinyu.model.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xinyu.model.base.BatchSendCtrlParam;
import com.xinyu.model.base.DriverInfo;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.order.child.WmsStockOutOrderItem;
import com.xinyu.model.order.enums.OutOrderStateEnum;
import com.xinyu.model.order.enums.OutOrderTypeEnum;


/**
 * @author shark_cj
 * @since  2017-04-28
 *  出库单
 */
public class StockOutOrder  extends Entity{
	
	private  OutOrderStateEnum state;
	
//	ownerUserId	string	20	TRUE	14544	货主ID	货主
	private  User user;
	/**
	 * FALSE   货主名称
	 */
	private String  ownerUserName;

	/**
	 * TRUE  仓储编码
	 * warehouseCode
	 */
	private String storeCode ;
	
	/**
	 * TRUE   仓储中心出库订单编码
	 */
	private  String orderCode;
	
	/**
	 * 商家订单编码
	 * deliveryOrderCode
	 */
	private  String  erpOrderCode;
//	orderType	int	11	TRUE	301	操作类型：301 调拨出库单 901 普通出库单 (如货主拉走一部分货) 305 B2B出库单
//	703	库存状态调整出库
//	701	(大家电)盘点出库	orderType
//	需适配菜鸟单据类型。
//	奇门定义：
//	出库单类型(PTCK=普通出库单;DBCK=调拨出库;B2BCK=B2B出库;QTCK=其他出库;CGTH=采购退货出库单;XNCK=虚拟出库单)
	
	
	private  OutOrderTypeEnum  orderType;
	
	/**
	 * FALSE 加工领料
	 * 自定义文本： 加工领料 委外领料 借出领用 内部领用	新增
	 */
	private String  outboundTypeDesc;
	
	/**
	 * FALSE  仓库订单需要履行服务标识
	 */
	private  String  orderFlag;
	
	/**
	 * TRUE   订单创建时间(格式为 YYYY-MM-DD HH:mm:ss)
	 */
	private Date  orderCreateTime;
	
	/**
	 * FALSE  要求出库时间: 格式为YYYY-MM-DD HH:mm:ss
	 * scheduleDate，时间格式和奇门不同
	 */
	private Date sendTime ;
	
	/**
	 *  FALSE  物流公司编码
	 *  SF=顺丰、EMS=标准快递、
	 *  EYB=经济快件、ZJS=宅急送、
	 *  YTO=圆通  、ZTO=中通 (ZTO) 、
	 *  HTKY=百世汇通、UC=优速、
	 *  STO=申通、TTKDEX=天天快递  、
	 *  QFKD=全峰、FAST=快捷、POSTB=邮政小包  、
	 *  GTO=国通、YUNDA=韵达、JD=京东配送、DD=当当宅配、
	 *  AMAZON=亚马逊物流、OTHER=其他 ，
	 *  (只传英文编码)	logisticsCode
	 */
	private String  tmsServiceCode;
	
	/**
	 * FALSE  物流公司名称（包括干线物流公司等）
	 * logisticsName
	 */
	private String  tmsServiceName;
	
	/**
	 * FALSE  供应商编码
	 * supplierCode
	 */
	private String   supplierCode;
	
	/**
	 * FALSE  供应商名称
	 * supplierName
	 */
	private String  supplierName;
	
	/**
	 * FALSE  出库方式（自提，非自提）
	 * transportMode，菜鸟枚举值扩展透出
	 */
	private String transportMode;
	
	/**
	 * FALSE   公司名称	取件公司名称
	 * pickerInfo. company
	 */
	private  String  pickCompany;
	
	/** 
	 * FALSE   取件人姓名
	 * 	pickerInfo.name
	 */
	private  String  pickName ;
	
	/**
	 * FALSE   固定电话
	 * pickerInfo. tel
	 */
	private String  pickTel;
	
	/**
	 *  FALSE   取件人电话
	 * 	pickerInfo.mobile
	 */
	private String pickCall;
	
	/**
	 * FALSE    承运商名称
	 * pickerInfo.name
	 */
	private  String  carriersName;
	
	/**
	 *  FALSE   取件人身份证
	 *  pickerInfo. id
	 */
	private String pickId;
	
	/**
	 * FALSE   车牌号
	 * pickerInfo. carNo
	 */
	private String carNo;
	
	/**
	 * FALSE   分批下发控制参数
	 * totalOrderLines
	 */
	private  BatchSendCtrlParam  batchSendCtrlParam;
	
	/**
	 * FALSE    收货方信息
	 * receiverInfo
	 */
	private ReceiverInfo receiverInfo;
	
	/**
	 * FALSE   发件方信息
	 * senderInfo
	 */
	private SenderInfo senderInfo;
	
	/**
	 * TRUE   出库单商品信息列表
	 * orderLines
	 */
	private List<WmsStockOutOrderItem>  orderItemList;
	
	/**
	 * FALSE   时区 格式
	 * +0800	新增
	 */
	private String  timeZone;

	/**
	 * FALSE   币种
	 * 如CNY USD	新增
	 */
	private String  currency;
	
	/**
	 * FALSE   拓展属性数据
	 * 详见   订单下发扩展字段
	 * 新增
	 */
	private Map<String,String> extendFields;
	
	/**
	 * FALSE   装车信息
	 * 新增
	 */
	private DriverInfo driverInfo;
	
	/**
	 *   FALSE  备注
	 */
	private String   remark;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getErpOrderCode() {
		return erpOrderCode;
	}

	public void setErpOrderCode(String erpOrderCode) {
		this.erpOrderCode = erpOrderCode;
	}

	public OutOrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(OutOrderTypeEnum orderType) {
		this.orderType = orderType;
	}

	public String getOutboundTypeDesc() {
		return outboundTypeDesc;
	}

	public void setOutboundTypeDesc(String outboundTypeDesc) {
		this.outboundTypeDesc = outboundTypeDesc;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getTmsServiceCode() {
		return tmsServiceCode;
	}

	public void setTmsServiceCode(String tmsServiceCode) {
		this.tmsServiceCode = tmsServiceCode;
	}

	public String getTmsServiceName() {
		return tmsServiceName;
	}

	public void setTmsServiceName(String tmsServiceName) {
		this.tmsServiceName = tmsServiceName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	public String getPickCompany() {
		return pickCompany;
	}

	public void setPickCompany(String pickCompany) {
		this.pickCompany = pickCompany;
	}

	public String getPickName() {
		return pickName;
	}

	public void setPickName(String pickName) {
		this.pickName = pickName;
	}

	public String getPickTel() {
		return pickTel;
	}

	public void setPickTel(String pickTel) {
		this.pickTel = pickTel;
	}

	public String getPickCall() {
		return pickCall;
	}

	public void setPickCall(String pickCall) {
		this.pickCall = pickCall;
	}

	public String getCarriersName() {
		return carriersName;
	}

	public void setCarriersName(String carriersName) {
		this.carriersName = carriersName;
	}

	public String getPickId() {
		return pickId;
	}

	public void setPickId(String pickId) {
		this.pickId = pickId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public BatchSendCtrlParam getBatchSendCtrlParam() {
		return batchSendCtrlParam;
	}

	public void setBatchSendCtrlParam(BatchSendCtrlParam batchSendCtrlParam) {
		this.batchSendCtrlParam = batchSendCtrlParam;
	}

	public ReceiverInfo getReceiverInfo() {
		return receiverInfo;
	}

	public void setReceiverInfo(ReceiverInfo receiverInfo) {
		this.receiverInfo = receiverInfo;
	}

	public SenderInfo getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(SenderInfo senderInfo) {
		this.senderInfo = senderInfo;
	}

	public List<WmsStockOutOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<WmsStockOutOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Map<String, String> getExtendFields() {
		return extendFields;
	}

	public void setExtendFields(Map<String, String> extendFields) {
		this.extendFields = extendFields;
	}

	public DriverInfo getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(DriverInfo driverInfo) {
		this.driverInfo = driverInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public OutOrderStateEnum getState() {
		return state;
	}

	public void setState(OutOrderStateEnum state) {
		this.state = state;
	}

}
