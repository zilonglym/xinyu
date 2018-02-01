package com.xinyu.model.inventory;

import java.util.Date;
import java.util.List;

import com.xinyu.model.base.Centro;
import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.child.InventoryOrderItem;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.inventory.enums.InventoryAdjustStateEnum;
import com.xinyu.model.inventory.enums.InventoryAdjustTypeEnum;


/**
 * @author shark_cj
 * @since  2017-06-17
 * 库存状态批次调整
 */
public class InventoryAdjustUpload  extends  Entity{
	
	/**
	 * 本地使用. 状态字段
	 */
	private  InventoryAdjustStateEnum  state;
	
	/**
	 * 正转残:FORWORDDEFECTIVE
	 * 残转正:FORWORDNORMAL
	 * */
	private   InventoryAdjustTypeEnum orderType;
	
	//ownerUserId	string	20	TRUE	94298837	货主ID	ownerCode
	private  User  user;
	
	//	storeCode	string	64	TRUE	STORE_525525	仓库编码	warehouseCode
	private Centro centro;
	
	//adjustBizKey	string	64	true	LBX123	损益调整主单号
	private  String  adjustBizKey;
	
	//	outBizCode	string	64	TRUE	2456	外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样，WMS库存状态调整单号	outBizCode
	private String outBizCode;
	
	//imbalanceOrderCode	string	64	false	LBX2456	差异单订单编号 
	private String imbalanceOrderCode;
	
	//adjustReasonType	string	64	false	WMS_GOODS_OVER_FLOW	差异原因
	private AdjustReasonTypeEnum adjustReasonType;
 	
	//responsibilityCode	string	16	true	T80	盘点责任编码
	private String responsibilityCode;
	
	//	operateTime	date		FALSE		调整时间
	//	YYYY-MM-DD HH:MM:SS	changeTime类型修改，由string变为date
	private  Date  operateTime;
	
	//InitBatchOrderCode	string	64	false	LBZ0027202031001	批次初始化单据
	private String InitBatchOrderCode;
	
	//confirmType	int	11	false	1	是否完成
	private int  confirmType;
	
	//	remark	string	2000	FALSE	***	备注	remark
	private String  remark;
	
//	itemList	List<OrderItem>		TRUE	　	库存状态调整单商品信息列表	items
	private List<InventoryOrderItem> items;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public String getOutBizCode() {
		return outBizCode;
	}

	public void setOutBizCode(String outBizCode) {
		this.outBizCode = outBizCode;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<InventoryOrderItem> getItems() {
		return items;
	}

	public void setItems(List<InventoryOrderItem> items) {
		this.items = items;
	}

	public InventoryAdjustStateEnum getState() {
		return state;
	}

	public void setState(InventoryAdjustStateEnum state) {
		this.state = state;
	}

	public InventoryAdjustTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(InventoryAdjustTypeEnum orderType) {
		this.orderType = orderType;
	}

	public String getAdjustBizKey() {
		return adjustBizKey;
	}

	public void setAdjustBizKey(String adjustBizKey) {
		this.adjustBizKey = adjustBizKey;
	}

	public String getImbalanceOrderCode() {
		return imbalanceOrderCode;
	}

	public void setImbalanceOrderCode(String imbalanceOrderCode) {
		this.imbalanceOrderCode = imbalanceOrderCode;
	}
	
	public AdjustReasonTypeEnum getAdjustReasonType() {
		return adjustReasonType;
	}

	public void setAdjustReasonType(AdjustReasonTypeEnum adjustReasonType) {
		this.adjustReasonType = adjustReasonType;
	}

	public String getResponsibilityCode() {
		return responsibilityCode;
	}

	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}

	public String getInitBatchOrderCode() {
		return InitBatchOrderCode;
	}

	public void setInitBatchOrderCode(String initBatchOrderCode) {
		InitBatchOrderCode = initBatchOrderCode;
	}

	public int getConfirmType() {
		return confirmType;
	}

	public void setConfirmType(int confirmType) {
		this.confirmType = confirmType;
	}
	
}
