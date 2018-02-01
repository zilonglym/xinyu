package com.graby.store.entity.local;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.graby.store.entity.enums.OrderStatusEnums;

/**
 * 批次单据
 * @author Administrator
 *	
 */
@Entity
@Table(name = "sc_local_batch")
public class LocalBatch implements Serializable{

	private static final long serialVersionUID = 3414542012374829538L;
	
	private int id;
	
	//商家ID(LcalShop)
	private String shopId;
	
	//商品ID（LocalItem）
	private String itemId;
	
	//商品数量
	private int num;
	
	/**
	 * 单据编码
	 * 数据库配置触发器，创建单据时生成ZC开头的编号
	 * 
	 */
	private String orderCode;
	
	//入库单编码
	private String entryCode;
	
	//单据创建时间
	private Date createDate;
	
	//单据更新时间
	private Date lastUpdate;
	
	//商品生产时间
	private Date birthDate;
	
	/**
	 * 单据状态
	 */
	private OrderStatusEnums status;
	
	//是否高板位数据
	private String isHigh;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public OrderStatusEnums getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnums status) {
		this.status = status;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getIsHigh() {
		return isHigh;
	}

	public void setIsHigh(String isHigh) {
		this.isHigh = isHigh;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
