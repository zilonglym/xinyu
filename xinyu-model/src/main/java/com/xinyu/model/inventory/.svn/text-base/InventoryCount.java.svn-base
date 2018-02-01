package com.xinyu.model.inventory;

import java.util.Date;
import java.util.List;

import com.xinyu.model.base.User;
import com.xinyu.model.common.Entity;
import com.xinyu.model.inventory.child.InventoryCountReturnOrderItem;
import com.xinyu.model.inventory.enums.AdjustReasonTypeEnum;
import com.xinyu.model.inventory.enums.InventoryCountStateEnum;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;

/**
 * @author shark_cj
 * @since 2017-04-30
 * 盘点单
 */
public class InventoryCount  extends Entity {

	/**
	 * 本地使用. 状态字段
	 */
	private InventoryCountStateEnum  state;
	

	/**
	 * TRUE   仓库编码
	 * warehouseCode
	 */
	private  String  storeCode;
	
 	/**
 	 * true  	订单类型：  701
 	 * 盘点出库（盘亏） 702 盘点入库（盘盈）	新增字段，注意这样一个单据中只能是全部盘赢，或者全部盘亏
 	 */
	private InventoryOrderTypeEnum orderType;
	
	/**
	 * TRUE   货主ID	货主
	 * ownerUserId
	 */
	private User user;
	
	/**
	 * FALSE  盘点单号
	 * checkOrderCode
	 */
	private String   checkOrderCode;
	
	/**
	 *  TRUE  外部业务编码
	 *  物流宝用来去重，
	 *  需要每次确认都不一样，
	 *  同一次确认重试需要一样	
	 *  outBizCode
	 */
	private String  outBizCode;
	
	/**
	 * FALSE   差异单
	 * orderCode
	 * 无差异单业务不填
	 * 差异单：主要是为了先锁定库存	
	 * 新增字段，之前如果没有可以忽略
	 */
	private String  imbalanceOrderCode;

	/**
	 *  false  库存差异调整原因类型
	 *  主要是为了区分调整和盘点	新增字段，
	 *  如果没有可以忽略
	 */
	private  AdjustReasonTypeEnum adjustReasonType;
	
	/**
	 * false  盘点责任编码
	 * 新增字段，之前如果没有可以忽略
	 */
	private  String  responsibilityCode;
	
	/**
	 * false   调整主单
	 * 新增字段，之前如果没有可以忽略
	 */
	private  String  adjustBizKey;

	/**
	 * false    盘点单商品信息列表
	 * items
	 */
	private List<InventoryCountReturnOrderItem>  itemList;
	/**
	 * false   盘点时间
	 * checkTime   
	 */
	private  Date  operateTime;
	
	/**
	 * false   备注
	 */
	private String remark;

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public InventoryOrderTypeEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(InventoryOrderTypeEnum orderType) {
		this.orderType = orderType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCheckOrderCode() {
		return checkOrderCode;
	}

	public void setCheckOrderCode(String checkOrderCode) {
		this.checkOrderCode = checkOrderCode;
	}

	public String getOutBizCode() {
		return outBizCode;
	}

	public void setOutBizCode(String outBizCode) {
		this.outBizCode = outBizCode;
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

	public String getAdjustBizKey() {
		return adjustBizKey;
	}

	public void setAdjustBizKey(String adjustBizKey) {
		this.adjustBizKey = adjustBizKey;
	}

	public List<InventoryCountReturnOrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<InventoryCountReturnOrderItem> itemList) {
		this.itemList = itemList;
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

	public InventoryCountStateEnum getState() {
		return state;
	}

	public void setState(InventoryCountStateEnum state) {
		this.state = state;
	}
	
}
