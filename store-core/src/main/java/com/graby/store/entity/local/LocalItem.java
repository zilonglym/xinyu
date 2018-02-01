package com.graby.store.entity.local;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 本地商品资料
 * 2017-10-23
 * */
@Entity
@Table(name = "sc_local_item")
public class LocalItem implements Serializable{

	private static final long serialVersionUID = -1355162006218519659L;

	private int id;
	//商品名称
	private String name;
	//商品属性（颜色，尺寸，重量，规格等）
	private String sku;
	//商品类型（正品：ZC，赠品:ZP,其他类型：OTHER）
	private String itemType;
	//商品来源（菜鸟：cainiao，奇门：qimen，导入：import，其他：others）
	private String source;
	//商品条码
	private String barCode;
	//所属商家ID
	private String shopId;
	
	//冗余字段
	private String shopName;
	
	//商品低卡位数量
	private int num;
	
	//商品高卡位数量
	private int highNum;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getHighNum() {
		return highNum;
	}

	public void setHighNum(int highNum) {
		this.highNum = highNum;
	}
	
}
