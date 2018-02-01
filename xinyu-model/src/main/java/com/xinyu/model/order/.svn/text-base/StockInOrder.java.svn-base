package com.xinyu.model.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xinyu.model.base.BatchSendCtrlParam;
import com.xinyu.model.base.DriverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.order.child.WmsStockInCaseInfo;
import com.xinyu.model.order.child.WmsStockInOrderItem;
import com.xinyu.model.order.enums.InOrderStateEnum;
import com.xinyu.model.order.enums.InOrderTypeEnum;
import com.xinyu.model.order.enums.OrderFlagEnum;
import com.xinyu.model.order.enums.OrderSourceEnum;



/**
 * @author shark_cj
 * @since  2017-04-27 
 * 
 */
public class StockInOrder extends Entity {
	
	private  InOrderStateEnum  state;
	
	/**
	 * TRUE	14544	货主ID	货主
	 */
	private User user ;
	
	/**
	 * FALSE	***	货主名称	新增字段
	 */
	private  String ownerUserName;
	
	/**
	 * TRUE	4563	仓储编码	    奇门对应字段: warehouseCode，
	 * 一定有仓库编码
	 */
	private  String storeCode;
	
	/**
	 * RUE	1255	仓储中心入库订单编码	新增
	 */
	private String  orderCode;
	
	/**
	 * TRUE	1255	仓储中心入库订单编码	奇门对应字段: entryOrderCode
	 */ 
	private  String  erpOrderCode;
	
	
	/**
	 * 需适配菜鸟库存类型，只有退货入库
	 */
	private InOrderTypeEnum  orderType;
	
	/**
	 * FALSE   预期到货时间 (YYYY-MM-DD HH:MM:SS)  奇门对应字段:expectStartTime
	 */
	private  String  expectStartTime;
	
	/**
	 * FALSE   最迟预期到货时间 (YYYY-MM-DD HH:MM:SS)  奇门对应字段:expectEndTime
	 */
	private String  expectEndTime;
	
	/**
	 * FALSE  加工归还
	 * <pre>
	 *  自定义文本透传至WMS 加工归还 委外归还 借出归还 内部归还
	 *  新增字段，之前如果没有可以忽略
	 * </pre>
	 */
	private String  inboundTypeDesc;
	
	/**
	 * FALSE	9	仓库订单需要履行服务标识
	 * <pre>
	 *  适用如下场景:
	 *  退货入库:
	 *  8:退换货
	 *  9:上门服务
	 *  13: 退货收取发票
	 *  31：退货入库
	 *  36：退货时同时换货
	 *  其他单据忽略,
	 *  orderFlag 需适配菜鸟
	 * </pre>
	 */
	private  OrderFlagEnum orderFlag;
	
	/**
	 * FALSE   订单来源   
	 * 默认  201
	 * <pre>
	 *  适用如下场景:
	 *  退货入库:
	 *  其他单据忽略	新增字段，之前如果没有可以忽略
	 * </pre>
	 * 
	 */
	private OrderSourceEnum  orderSource;
	
	
	/**
	 * true 订单创建时间(格式为 YYYY-MM-DD HH:mm:ss)
	 * 新增字段，之前如果没有可以忽略
	 */
	private  Date  orderCreateTime;
	
	/**
	 * FALSE  供应商编码，往来单位编码
	 * 采购入库单	对应奇门: supplierCode
	 */
	private  String supplierCode ;
	
	/**
	 * FALSE  供应商名称，往来单位名称
	 * 
	 * 采购入库单	对应奇门: supplierName
	 */
	private String  supplierName;
	
	
	/**
	 * FALSE  配送公司编码
	 * 适用如下场景：销退买家拒签：原发货单的配送公司编码；
	 * 销退上门取件，消费者退货，商家下单：新生成的运单号所属配送公司的编码 	logisticsCode
	 */
	private String  tmsServiceCode;
	
	/**
	 * FALSE	配送公司名称
	 * 适用如下场景：
	 * 销退买家拒签：原发货单的配送公司名称
	 * 销退上门取件，消费者退货，商家下单：新生成的运单号所属配送公司的名称 	logisticsName
	 */
	private String  tmsServiceName;
	
	/**
	 * FALSE 运单号
	 * 适用如下场景：
	 * 销退买家拒签：原发货单的运单号
	 * 销退上门取件，消费者退货，商家下单：新生成的运单号	expressCode
	 */
	private String  tmsOrderCode;
	
	/**
	 * FALSE  原仓储作业单号
	 * 适用如下场景：
	 * 销退入库单：原发货单号，
	 * 注意：原发货单可能是其他仓库发出	只在退货时用
	 */
	private String prevEprOrderCode;
	
	/**
	 * FALSE  原仓储作业单号
	 * 适用如下场景：
	 * 销退入库单：原发货单号，注意：原发货单可能是其他仓库发出	preDeliveryOrderId
	 * 只在退货时用
	 */
	private String  prevOrderCode;
	
	/**
	 * FALSE
	 * 退货原因：销退场景下，
	 * 如可能请提供退货的原因，
	 * 多个退货原因用；号分开
	 */
	private String  returnReason;
	
	/**
	 * FALSE 原发货单中的收件人昵称
	 * buyerNick
	 */
	private String  buyerNick;
	
	/**
	 * FALSE 分批下发控制参数
	 * 新增字段，自行决定是否对应分批控制，如果不接要找菜鸟配置
	 * BatchSendCtrlParam
	 */
	private BatchSendCtrlParam  batchSendCtrlParam;
	
	/**
	 * TRUE
	 * 发件方信息	senderInfo
	 */
	private SenderInfo senderInfo;
	
	/**
	 *  TRUE  入库单商品信息列表
	 *  orderLines
	 */
	private List<WmsStockInOrderItem>   orderItemList;
	
	/**
	 * FALSE   装箱列表
	 * 新增字段，之前如果没有可以忽略
	 */
	private  List<WmsStockInCaseInfo>  caseInfoList;
	
	
	/**
	 * FALSE    时区 格式如 +0800
	 * 新增字段，之前如果没有可以忽略
	 */
	private String   timeZone; 
	
	/**
	 *  FALSE 币种
	 *  币种 如CNY USD	
	 *  之前如果没有可以忽略
	 */
	private  String  currency;
	
	
//	extendFields	map	　	FALSE	*****	拓展属性数据
	/**
	 * false  拓展属性数据
	 * 订单下发扩展字段
	 * 新增字段，之前如果没有可以忽略
	 */
	private Map<String,String>   extendFields;
	
	/**
	 * FALSE  装车信息
	 * 新增字段，之前如果没有可以忽略
	 */
	private  DriverInfo  driverInfo;
	
	/**
	 * FALSE  备注
	 * 销退时留言备注填充到此字段	remark
	 */
	private String remark;

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

	

	public String getExpectStartTime() {
		return expectStartTime;
	}

	public void setExpectStartTime(String expectStartTime) {
		this.expectStartTime = expectStartTime;
	}

	public String getExpectEndTime() {
		return expectEndTime;
	}

	public void setExpectEndTime(String expectEndTime) {
		this.expectEndTime = expectEndTime;
	}

	public String getInboundTypeDesc() {
		return inboundTypeDesc;
	}

	public void setInboundTypeDesc(String inboundTypeDesc) {
		this.inboundTypeDesc = inboundTypeDesc;
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

	public String getTmsOrderCode() {
		return tmsOrderCode;
	}

	public void setTmsOrderCode(String tmsOrderCode) {
		this.tmsOrderCode = tmsOrderCode;
	}

	public String getPrevEprOrderCode() {
		return prevEprOrderCode;
	}

	public void setPrevEprOrderCode(String prevEprOrderCode) {
		this.prevEprOrderCode = prevEprOrderCode;
	}

	public String getPrevOrderCode() {
		return prevOrderCode;
	}

	public void setPrevOrderCode(String prevOrderCode) {
		this.prevOrderCode = prevOrderCode;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public BatchSendCtrlParam getBatchSendCtrlParam() {
		return batchSendCtrlParam;
	}

	public void setBatchSendCtrlParam(BatchSendCtrlParam batchSendCtrlParam) {
		this.batchSendCtrlParam = batchSendCtrlParam;
	}

	public SenderInfo getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(SenderInfo senderInfo) {
		this.senderInfo = senderInfo;
	}

	public List<WmsStockInOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<WmsStockInOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<WmsStockInCaseInfo> getCaseInfoList() {
		return caseInfoList;
	}

	public void setCaseInfoList(List<WmsStockInCaseInfo> caseInfoList) {
		this.caseInfoList = caseInfoList;
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

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public InOrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(InOrderTypeEnum orderType) {
		this.orderType = orderType;
	}

	public OrderFlagEnum getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(OrderFlagEnum orderFlag) {
		this.orderFlag = orderFlag;
	}

	public OrderSourceEnum getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(OrderSourceEnum orderSource) {
		this.orderSource = orderSource;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Map<String, String> getExtendFields() {
		return extendFields;
	}

	public void setExtendFields(Map<String, String> extendFields) {
		this.extendFields = extendFields;
	}

	public InOrderStateEnum getState() {
		return state;
	}

	public void setState(InOrderStateEnum state) {
		this.state = state;
	}
}
