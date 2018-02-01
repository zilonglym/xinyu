package com.xinyu.model.order;

import java.util.Date;
import java.util.List;

import com.xinyu.model.common.Entity;
import com.xinyu.model.order.child.StockInCheckItem;
import com.xinyu.model.order.child.StockInOrderItem;
import com.xinyu.model.order.enums.InOrderTypeEnum;

/**
 * @author shark_cj
 * @since  2017-05-03
 * 入库确认
 */
public class StockInOrderConfirm   extends Entity{

	
	/**
	 * 采购入库单
	 */
	private StockInOrder  stockInOrder;
	
	/**
	 * true      仓储中心入库订单编码
	 * 新增，菜鸟订单号
	 */
	private  String orderCode;
	
	/**
	 * TRUE    单据类型
	 * 新增，对应菜鸟下发的orderType
	 */
	private InOrderTypeEnum orderType;
	
	/**
	 * true    	外部业务编码
	 * 物流宝用来去重，
	 * 需要每次确认都不一样，
	 * 同一次确认重试需要一样
	 * 	outBizCode
	 */
	private  String  outBizCode;
	
	/**
	 *   TRUE   支持入库单多次确认(如：每上架一次就立刻回传) 
	 *   0 最后一次确认或是一次性确认； 
	 *   1 多次确认； 默认值为 0 例如输入2认为是0	
	 *   confirmType
	 *   同一入库单;
	 *   如果先收到0;
	 *   后又收到1，不允许修改收货的数量)
	 */
	private int confirmType;
	
	/**
	 *  true    仓库入库单完成时间
	 *  operateTime
	 */
	private Date   orderConfirmTime;
	
	/**
	 *    FALSE
	 *    时区	新增
	 */
	private  String timeZone;
	
	
	private List<StockInOrderItem> orderItems;
	

	/**
	 * FALSE   配送编码
	 * logisticsCode
	 */
	private String  tmsServiceCode;

	/**
	 * FALSE    配送公司名称
	 * logisticsName
	 */
	private String  tmsServiceName;
	
	/**
	 * false    运单号
	 * 指定运单号发货业务	expressCode
	 */
	private String tmsOrderCode;
	
	/**
	 * FALSE  退货原因
	 * 销退场景下，
	 * 如可能请提供退货的原因
	 * 多个退货原因用；号分开	
	 * returnReason
	 */
	private String  returnReason;
	
	/**
	 * FALSE   入库单商品校验信息列
	 * 新增，分批的最后一批时必传，
	 * 此节点是所有回传报文货品数量汇总。
	 */
	private  List<StockInCheckItem>   checkItems;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public InOrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(InOrderTypeEnum orderType) {
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

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public List<StockInCheckItem> getCheckItems() {
		return checkItems;
	}

	public void setCheckItems(List<StockInCheckItem> checkItems) {
		this.checkItems = checkItems;
	}

	public List<StockInOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<StockInOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public StockInOrder getStockInOrder() {
		return stockInOrder;
	}

	public void setStockInOrder(StockInOrder stockInOrder) {
		this.stockInOrder = stockInOrder;
	}
}
