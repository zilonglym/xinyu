package com.xinyu.model.trade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xinyu.model.base.DeliverRequirements;
import com.xinyu.model.base.InvoiceInfo;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.SenderInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.base.WmsConsignOrderPackageRequirement;
import com.xinyu.model.common.Entity;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.Person;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.SystemSourceEnums;

/**
 * 加入套套餐属性字段
 * @author yangmin
 * 2017年8月4日
 *
 */
public class ShipOrder extends Entity{
	
	/**
	 * 订单的套餐标记
	 */
	public static final String item_Group_Status="group";
	
	private ShipOrder order;
	private TmsOrder tmsOrder;
	/**
	 * 订单拆单状态
	 * 主单：split
	 * 子单：split_订单orderCode
	 * */
	private String splitStatus;
	
	/**
	 * 订单状态
	 * */
	private OrderStatusEnum status;
	
	/**
	 * 订单来源系统，菜鸟或奇门或其它
	 */
	private SystemSourceEnums systemSource;
	
	
	private String items;
	
	private Person lastUpdateBy;
	
	private Date lastUpdateTime;
	
	/**
	 * 系统创建时间
	 */
	private Date createTime;
	/**
	 * 货主
	 */
	private User user;

	/**
	 * 仓储编码 warehouseCode
	 */
	private String storeCode;

	/**
	 * 仓储中心订单编码
	 */
	private String orderCode;

	/**
	 * 商家订单编码 deliveryOrderCode
	 */
	private String erpOrderCode;
	/**
	 * 单据类型： 201 交易出库单502 换货出库单
	 * 
	 */
	private int orderType;
	/**
	 * 店铺ID
	 */
	private String shopId;

	/**
	 * 店铺名称 shopNick
	 */
	private String shopName;

	/**
	 * 仓库订单需要履行服务标识： 1: cod –货到付款 2: limit-限时配送 3: presell-预售 4:invoiceinfo-需要发票
	 * 8:退换货 9:上门服务 10: 不可改配送方式 12: 买家家承担运费 20: 使用干配服务 22：配送方式 32：标识期货预售的预发货单
	 * 34：生鲜－常温服务标 35：生鲜－冷链服务标 36：退货时同时换货 37：负卖 38：高值订单 39：环保配送 40：预约配送
	 * 详见枚举值映射关系
	 * 
	 * 34,35,39枚举值匹配奇门的deliveryType
	 * 
	 */
	private String orderFlag;
	/**
	 * 订单来源（213 天猫，201 淘宝，214 京东，202 1688 阿里中文站 ，203 苏宁在线，204 亚马逊中国，205 当当，208
	 * 1号店，207 唯品会，209 国美在线，210 拍拍，206 易贝ebay，211 聚美优品，212 乐蜂网，215 邮乐，216 凡客，217
	 * 优购，218 银泰，219 易讯，221 聚尚网，222 蘑菇街，223 POS门店，301 其他 不在范围之内请忽略）
	 * sourcePlatformCode 需适配菜鸟
	 * 
	 */
	private int orderSource;
	/**
	 * 订单创建时间(格式为 YYYY-MM-DD HH:mm:ss)
	 */
	private Date orderCreateTime;
	/**
	 * 订单支付时间(格式为 YYYY-MM-DD HH:mm:ss)
	 */
	private Date orderPayTime;
	/**
	 * 支付平台交易号, 淘系订单传支付宝交易号
	 */
	private String payNo;
	/**
	 * 订单审核时间(格式为 YYYY-MM-DD HH:mm:ss)
	 */
	private Date orderExaminationTime;
	/**
	 * 前台交易创建时间(格式为 YYYY-MM-DD HH:mm:ss)
	 */
	private Date orderShopCreateTime;
	/**
	 * 订单金额（=总商品金额-订单优惠金额+快递费用），单位分 当币种为美元时是美分
	 */
	private Long orderAmount;
	/**
	 * 订单优惠金额，单位分币种为美元时是美分 discountAmount 注意单位是分
	 * 
	 */
	private Long discountAmount;
	/**
	 * 订单应收金额，消费者还需要付多少钱，单位分币种为美元时是美分 arAmount 注意单位是分
	 * 
	 */
	private Long arAmount;
	/**
	 * 订单已收金额，消费者已经支付多少钱，单位分 币种为美元时是美分
	 */
	private Long gotAmount;
	/**
	 * 快递费用，单位分币种为美元时是美分
	 */
	private Long postfee;
	/**
	 * 服务费，单位分，例如COD币种为美元时是美分
	 */
	private Long serviceFee;
	/**
	 * 配送编码 logisticsCode
	 */
	private String tmsServiceCode;
	/**
	 * 配送公司名称 logisticsName
	 */
	private String tmsServiceName;
	/**
	 * 面单显示快递编码
	 */
	private String tmsDisplayName;
	/**
	 * 运单号，指定运单号发货业务 expressCode
	 */
	private String tmsOrderCode;
	
	
	private String tmsRouteCode;
	/**
	 * 电子面单大头笔
	 */
	private String sortationName;
	
	/**
	 * 收货网点信息
	 */
	private String consolidationName;
	
	private String consolidationCode;
	
	private String batchCode;
	
	/**
	 * 原仓储作业单号 适用如下场景： 换货出库单：原发货单号，注意：原发货单可能是其他仓库发出 preDeliveryOrderId
	 * 
	 */
	private String prevOrderCode;
	/**
	 * preDeliveryOrderCode
	 */
	private String prevErpOrderCode;
	/**
	 * 配送要求 deliveryRequirements DeliverRequirements
	 */
	private DeliverRequirements deliverRequirements;
	
	
	/**
	 * receiverInfo 收货方信息 一般是本地语言
	 */
	private ReceiverInfo receiverInfo;
	/**
	 * ReceiverInfo 通用收货方信息 一般是英语
	 */
	private ReceiverInfo uniReceiverInfo;
	/**
	 * 发货方信息 senderInfo
	 */
	private SenderInfo senderInfo;
	/**
	 * 时区 格式如 +0800
	 */
	private String timeZone;
	/**
	 * 币种 如CNY USD
	 */
	private String currency;
	/**
	 * List<InvoinceItem> 发票信息列表 invoices
	 */
	private List<InvoiceInfo> invoiceInfoList;
	/**
	 * List<WmsConsignOrderItem> 订单商品列表 orderLines
	 */
	private List<WmsConsignOrderItem> orderItemList;
	/**
	 * 包装要求 List<WmsConsignOrderPackageRequirement>
	 */
	private List<WmsConsignOrderPackageRequirement> packageRequirements;
	
	private String remark;
	/**
	 * 买家备注
	 */
	private String buyerMessage;
	/**
	 * 卖家备注
	 */
	private String sellerMessage;
	
	private String otherOrderNo;
	/**
	 * 拓展属性数据 详见 订单下发扩展字段 extendProps 数据结构与奇门不一致
	 * 
	 */
	private String extendFields;
	/**
	 * 打印模板URL
	 */
	private String templateURL;
	/**
	 * 套餐状态
	 */
	public String itemGroupStatus;
	
	
	/**
	 * 订单总重量
	 */
	private Double totalWeight;
	
	/**
	 * 排序字段
	 */
	private Long seq;
	/**
	 * 拓展属性数据 详见 订单下发扩展字段
	 */
	private Map<String, Object> tmsExtendFields;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public int getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(int orderSource) {
		this.orderSource = orderSource;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public Date getOrderPayTime() {
		return orderPayTime;
	}
	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public Date getOrderExaminationTime() {
		return orderExaminationTime;
	}
	public void setOrderExaminationTime(Date orderExaminationTime) {
		this.orderExaminationTime = orderExaminationTime;
	}
	public Date getOrderShopCreateTime() {
		return orderShopCreateTime;
	}
	public void setOrderShopCreateTime(Date orderShopCreateTime) {
		this.orderShopCreateTime = orderShopCreateTime;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Long getArAmount() {
		return arAmount;
	}
	public void setArAmount(Long arAmount) {
		this.arAmount = arAmount;
	}
	public Long getGotAmount() {
		return gotAmount;
	}
	public void setGotAmount(Long gotAmount) {
		this.gotAmount = gotAmount;
	}
	public Long getPostfee() {
		return postfee;
	}
	public void setPostfee(Long postfee) {
		this.postfee = postfee;
	}
	public Long getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(Long serviceFee) {
		this.serviceFee = serviceFee;
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
	public String getPrevOrderCode() {
		return prevOrderCode;
	}
	public void setPrevOrderCode(String prevOrderCode) {
		this.prevOrderCode = prevOrderCode;
	}
	public String getPrevErpOrderCode() {
		return prevErpOrderCode;
	}
	public void setPrevErpOrderCode(String prevErpOrderCode) {
		this.prevErpOrderCode = prevErpOrderCode;
	}
	public DeliverRequirements getDeliverRequirements() {
		return deliverRequirements;
	}
	public void setDeliverRequirements(DeliverRequirements deliverRequirements) {
		this.deliverRequirements = deliverRequirements;
	}
	public ReceiverInfo getReceiverInfo() {
		return receiverInfo;
	}
	public void setReceiverInfo(ReceiverInfo receiverInfo) {
		this.receiverInfo = receiverInfo;
	}
	public ReceiverInfo getUniReceiverInfo() {
		return uniReceiverInfo;
	}
	public void setUniReceiverInfo(ReceiverInfo uniReceiverInfo) {
		this.uniReceiverInfo = uniReceiverInfo;
	}
	public SenderInfo getSenderInfo() {
		return senderInfo;
	}
	public void setSenderInfo(SenderInfo senderInfo) {
		this.senderInfo = senderInfo;
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
	public List<InvoiceInfo> getInvoiceInfoList() {
		return invoiceInfoList;
	}
	public void setInvoiceInfoList(List<InvoiceInfo> invoiceInfoList) {
		this.invoiceInfoList = invoiceInfoList;
	}
	public List<WmsConsignOrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<WmsConsignOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public List<WmsConsignOrderPackageRequirement> getPackageRequirements() {
		return packageRequirements;
	}
	public void setPackageRequirements(List<WmsConsignOrderPackageRequirement> packageRequirements) {
		this.packageRequirements = packageRequirements;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getSellerMessage() {
		return sellerMessage;
	}
	public void setSellerMessage(String sellerMessage) {
		this.sellerMessage = sellerMessage;
	}
	public String getExtendFields() {
		return extendFields;
	}
	public void setExtendFields(String extendFields) {
		this.extendFields = extendFields;
	}
	public Map<String, Object> getTmsExtendFields() {
		return tmsExtendFields;
	}
	public void setTmsExtendFields(Map<String, Object> tmsExtendFields) {
		this.tmsExtendFields = tmsExtendFields;
	}
	public OrderStatusEnum getStatus() {
		return status;
	}
	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}
	public SystemSourceEnums getSystemSource() {
		return systemSource;
	}
	public void setSystemSource(SystemSourceEnums systemSource) {
		this.systemSource = systemSource;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getSplitStatus() {
		return splitStatus;
	}
	
	public void setSplitStatus(String splitStatus) {
		this.splitStatus = splitStatus;
	}
	public Person getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Person lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getOtherOrderNo() {
		return otherOrderNo;
	}
	public void setOtherOrderNo(String otherOrderNo) {
		this.otherOrderNo = otherOrderNo;
	}
	public String getTemplateURL() {
		return templateURL;
	}
	public void setTemplateURL(String templateURL) {
		this.templateURL = templateURL;
	}
	public String getTmsRouteCode() {
		return tmsRouteCode;
	}
	public void setTmsRouteCode(String tmsRouteCode) {
		this.tmsRouteCode = tmsRouteCode;
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
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getItemGroupStatus() {
		return itemGroupStatus;
	}
	public void setItemGroupStatus(String itemGroupStatus) {
		this.itemGroupStatus = itemGroupStatus;
	}
	public String getTmsDisplayName() {
		return tmsDisplayName;
	}
	public void setTmsDisplayName(String tmsDisplayName) {
		this.tmsDisplayName = tmsDisplayName;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public ShipOrder getOrder() {
		return order;
	}
	public void setOrder(ShipOrder order) {
		this.order = order;
	}
	public TmsOrder getTmsOrder() {
		return tmsOrder;
	}
	public void setTmsOrder(TmsOrder tmsOrder) {
		this.tmsOrder = tmsOrder;
	}
}
