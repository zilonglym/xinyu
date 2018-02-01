package com.graby.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

/**
 * 发货单 包括入库单和出库单
 * 
 * @author huabiao.mahb
 */
@Entity
@Table(name = "sc_ship_order")
public class ShipOrder implements Serializable{

	
	/* ------------- 发货单类型状态 -------------*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7865318877174354850L;

	/** 入库单  */
	public static final String TYPE_ENTRY = "entry";
	/**
	 * 退货入库单
	 */
	public static final String TYPE_ENTRY_RETURN="return";
	
	/** 出库单 */
	public static final String TYPE_SEND = "send";
	/**发货单	*/
	public static final String TYPE_DELIVER="deliver";
	

	/* ------------- 入库单状态 -------------*/
	
	public interface EntryOrderStatus {
		/** WAIT_SELLER_SEND : 等待商家发货 */
		public static final String ENTRY_WAIT_SELLER_SEND = "ENTRY_WAIT_SELLER_SEND";
		
		/** WAIT_STORAGE_RECEIVED ： 等待仓库接收 */
		public static final String ENTRY_WAIT_STORAGE_RECEIVED = "ENTRY_WAIT_STORAGE_RECEIVED";
		
		/** ENTRY_FINISH : 仓库接收成功，关闭入库单。*/
		public static final String ENTRY_FINISH = "ENTRY_FINISH";
	}
	

	/* ------------- 出库单状态 -------------*/
	
	public interface SendOrderStatus {
		
		/**  WAIT_EXPRESS_RECEIVED ：创建成功，等待物流接收　*/
		public static final String WAIT_EXPRESS_RECEIVED = "WAIT_EXPRESS_RECEIVED";
		
		/**  WAIT_EXPRESS_PICKING ：运单打印成功，等待拣货。　*/
		public static final String WAIT_EXPRESS_PICKING = "WAIT_EXPRESS_PICKING";
		
		/**  WAIT_EXPRESS_SEND ：物流方已发货，等待买家签收 　*/
		public static final String WAIT_BUYER_RECEIVED = "WAIT_BUYER_RECEIVED";
		
		/**  SEND_FINISH : 出库单发货完成  */
		public static final String SEND_FINISHED = "SEND_FINISH";
	}


	/* ------------ 基本信息 ------------ */
	
	// 序号
	private Long id;

	// 类型 (包括入库单、出库单)
	private String type;

	// 单号
	private String orderno;

	// 当前状态
	private String status;

	// 仓库ID
	private Long centroId;

	// 总件数
	private int totalnum;

	// 预计到达时间
	private Date fetchDate;
	
	/* ------------ 发货方信息 ------------ */	
	
	// 发货方联系人
	private String originPersion;

	// 发货方联系电话
	private String originPhone;

	// 运输公司编码
	private String expressCompany;
	
	// 运输公司名称
	private String expressCompanyName;

	// 运输公司运单号
	private String expressOrderno;
	
	
	/* ------------ 收货方信息 ------------ */

	// 收货方详细地址
	private String receiverAddress;

	// 收货人的所在省份
	private String receiverState;
	
	// 收货人的所在城市
	private String receiverCity;

	// 收货人的所在地区
	private String receiverDistrict;

	// 收货人的手机号码
	private String receiverMobile;

	// 收货人的姓名
	private String receiverName;

	// 收货人的电话号码
	private String receiverPhone;

	// 收货人的邮编
	private String receiverZip;

	// 备注
	private String remark;
	
	/* ------------  出库单特定属性  ------------ */
	
	// 如果是出库单，此为来源交易订单ID.
	private Long tradeId;
	
	/* ------------  淘宝发货单特定属性  ------------ */
	
	// 淘宝买家昵称
	private String buyerNick;
	
	// 买家留言
	private String buyerMessage;
	
	// 买家备注
	private String buyerMemo;
	
	// 卖家备注
	private String sellerMemo;	
	
	// 卖家手机
	private String sellerMobile;
	
	// 卖家电话
	private String sellerPhone;
	
	/* ------------  其他  ------------ */
	
	// 创建人
	private User createUser;

	// 创建时间
	private Date createDate;

	// 最后更新者
	private User lastUpdateUser;

	// 最后更新时间
	private Date lastUpdateDate;
	
	/** 发货商品明细 */
	private List<ShipOrderDetail> details = new ArrayList<ShipOrderDetail>();
	
	// 发货明细字符串
	private String items;
	
	/*奇门接口加入字段*/
	//单据业务类型
	private String orderType;
	//物流公司编号
	private String logisticsCode;
	private String logisticsName;//物流公司 名称
	private String expressCode;//快递单号
	private String operatorCode;//操作人员编号
	private String operatorName;//操作人姓名
	private String operateTime;//操作时间
	//发货方信息
	private String senderCompany;
	private String senderZipCode;
	private String senderProvince;
	private String senderCity;
	private String senderArea;
	private String senderTown;
	private String senderaddress;
	//
	private String receiveCopmany;
	//退货入库单信息
	/**
	 * 比如VISIT^ SELLER_AFFORD^SYNC_RETURN_BILL 等, 中间用“^”来隔开 订单标记list (所有字母全部大写)
	 *  ： VISIT=上门；SELLER_AFFORD=是否卖家承担运费 (默认是) ；SYNC_RETURN_BILL=同时退回发票；
	 */
	private String orderFlag;//订单标记列表
	/**
	 * 原出库单号（ERP分配）
	 */
	private String preDeliveryOrderCode;
	/**
	 * 原出库单号（WMS分配）
	 */
	private String preDeliveryOrderId;
	
	private String returnReason;//退货原因
	
	private String scheduleDate;//要求出库时间
	
	private String transportMode;//提货方式（到仓自提，快递，干线物流）
	//出库单提货人信息
	private String pickerCompany;
	private String pickerName;
	private String pickerTel;
	private String pickerMobile;
	private String pickerId;//证件号
	private String pickerCarNo;//车牌号码

	private String placeOrderTime;//前台订单 (店铺订单) 创建时间 (下单时间) 
	private String payTime;//订单支付时间,
	private String payNo;//支付平台交易号,
	private String shopNick;//店铺名称,
	private String sellerNick;//卖家名称
	private String logisticsAreaCode;//快递区域编码, 大头笔信息
	private String urgency;//是否紧急, Y/N, 默认为N
	private String invoiceFlag;//是否需要发票, Y/N, 默认为N
	private String invoiceType;//发票类型
	private String invoiceHeader;//发票抬头
	private String invoiceAmount;
	private String invoiceContent;
	private String insuranceFlag;//是否需要保险
	private String insuranceType;
	private String insuranceAmount;
	private String sellerMessage;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@OneToMany(mappedBy = "order")
	public List<ShipOrderDetail> getDetails() {
		return details;
	}

	@ManyToOne
	@JoinColumn(name = "create_userid")
	public User getCreateUser() {
		return createUser;
	}

	@ManyToOne
	@JoinColumn(name = "last_update_userid")
	public User getLastUpdateUser() {
		return lastUpdateUser;
	}
	
	@Index(name="idx_type")
	public String getType() {
		return type;
	}
	
	@Index(name="idx_status")
	public String getStatus() {
		return status;
	}
	
	@Index(name="idx_centro_id")
	public Long getCentroId() {
		return centroId;
	}
	
	@Index(name="idx_trade_id")
	public Long getTradeId() {
		return tradeId;
	}
	
	@Index(name="idx_express_order_no")
	public String getExpressOrderno() {
		return expressOrderno;
	}
	
	@Index(name="idx_buyer_nick")
	public String getBuyerNick() {
		return buyerNick;
	}	
	
	@Index(name="idx_create_date")
	public Date getCreateDate() {
		return createDate;
	}	

	@Transient
	public String getShopName() {
		if (createUser == null) return null;
		return createUser.getShopName();
	}
	
	@Transient
	public String getExpressCompanyName() {
		return expressCompanyName;
	}
	
	@Transient
	public String getReceiverAddressDetail() {
		StringBuffer buf = new StringBuffer();
		buf.append(receiverState).append(" ").append(receiverCity).append(" ").append(receiverDistrict).append(" ").append(receiverAddress);
		return buf.toString();
	}	
	
	@Transient
	public String getItems() {
		return items;
	}
	
	public String getOriginPersion() {
		return originPersion;
	}

	public String getOriginPhone() {
		return originPhone;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public int getTotalnum() {
		return totalnum;
	}

	public Date getFetchDate() {
		return fetchDate;
	}

	public String getRemark() {
		return remark;
	}



	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOriginPersion(String originPersion) {
		this.originPersion = originPersion;
	}

	public void setOriginPhone(String originPhone) {
		this.originPhone = originPhone;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public void setExpressOrderno(String expressOrderno) {
		this.expressOrderno = expressOrderno;
	}

	public void setReceiverAddress(String distAddress) {
		this.receiverAddress = distAddress;
	}

	public void setCentroId(Long centroId) {
		this.centroId = centroId;
	}

	public void setTotalnum(int totalItems) {
		this.totalnum = totalItems;
	}

	public void setFetchDate(Date fetchDate) {
		this.fetchDate = fetchDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setLastUpdateUser(User lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public void setDetails(List<ShipOrderDetail> details) {
		this.details = details;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public String getReceiverState() {
		return receiverState;
	}

	public String getReceiverZip() {
		return receiverZip;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getBuyerMemo() {
		if (buyerMemo == null) return "";
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public String getSellerMemo() {
		if (sellerMemo == null) return "";
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public String getBuyerMessage() {
		if (buyerMessage == null) return "";
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getSellerMobile() {
		if (sellerMobile == null) return "";
		return sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}

	public String getSellerPhone() {
		if (sellerPhone == null) return "";
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getSenderCompany() {
		return senderCompany;
	}

	public void setSenderCompany(String senderCompany) {
		this.senderCompany = senderCompany;
	}

	public String getSenderZipCode() {
		return senderZipCode;
	}

	public void setSenderZipCode(String senderZipCode) {
		this.senderZipCode = senderZipCode;
	}

	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	public String getSenderArea() {
		return senderArea;
	}

	public void setSenderArea(String senderArea) {
		this.senderArea = senderArea;
	}

	public String getSenderTown() {
		return senderTown;
	}

	public void setSenderTown(String senderTown) {
		this.senderTown = senderTown;
	}

	public String getSenderaddress() {
		return senderaddress;
	}

	public void setSenderaddress(String senderaddress) {
		this.senderaddress = senderaddress;
	}

	public String getSenderProvince() {
		return senderProvince;
	}

	public void setSenderProvince(String senderProvince) {
		this.senderProvince = senderProvince;
	}

	public String getReceiveCopmany() {
		return receiveCopmany;
	}

	public void setReceiveCopmany(String receiveCopmany) {
		this.receiveCopmany = receiveCopmany;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getPreDeliveryOrderCode() {
		return preDeliveryOrderCode;
	}

	public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
		this.preDeliveryOrderCode = preDeliveryOrderCode;
	}

	public String getPreDeliveryOrderId() {
		return preDeliveryOrderId;
	}

	public void setPreDeliveryOrderId(String preDeliveryOrderId) {
		this.preDeliveryOrderId = preDeliveryOrderId;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	public String getPickerCompany() {
		return pickerCompany;
	}

	public void setPickerCompany(String pickerCompany) {
		this.pickerCompany = pickerCompany;
	}

	public String getPickerName() {
		return pickerName;
	}

	public void setPickerName(String pickerName) {
		this.pickerName = pickerName;
	}

	public String getPickerTel() {
		return pickerTel;
	}

	public void setPickerTel(String pickerTel) {
		this.pickerTel = pickerTel;
	}

	public String getPickerMobile() {
		return pickerMobile;
	}

	public void setPickerMobile(String pickerMobile) {
		this.pickerMobile = pickerMobile;
	}

	public String getPickerId() {
		return pickerId;
	}

	public void setPickerId(String pickerId) {
		this.pickerId = pickerId;
	}

	public String getPickerCarNo() {
		return pickerCarNo;
	}

	public void setPickerCarNo(String pickerCarNo) {
		this.pickerCarNo = pickerCarNo;
	}

	public String getPlaceOrderTime() {
		return placeOrderTime;
	}

	public void setPlaceOrderTime(String placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getShopNick() {
		return shopNick;
	}

	public void setShopNick(String shopNick) {
		this.shopNick = shopNick;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getLogisticsAreaCode() {
		return logisticsAreaCode;
	}

	public void setLogisticsAreaCode(String logisticsAreaCode) {
		this.logisticsAreaCode = logisticsAreaCode;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getInvoiceFlag() {
		return invoiceFlag;
	}

	public void setInvoiceFlag(String invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getInsuranceFlag() {
		return insuranceFlag;
	}

	public void setInsuranceFlag(String insuranceFlag) {
		this.insuranceFlag = insuranceFlag;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getSellerMessage() {
		return sellerMessage;
	}

	public void setSellerMessage(String sellerMessage) {
		this.sellerMessage = sellerMessage;
	}
	
}
