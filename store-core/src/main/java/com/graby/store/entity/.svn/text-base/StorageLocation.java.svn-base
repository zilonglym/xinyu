package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 仓库库位
 * 001-A-2-3
 * */

@Entity
@Table(name = "sc_storage_location")
public class StorageLocation implements Serializable{

	private static final long serialVersionUID = 4930195250555634944L;
	//ID
	private Long id;
	//货架号
	private String HJ;
	//货架位置
	private String WZ;
	//托板位置
	private String BW;
	//层数
	private String CS;
	//库位商品名称
	private String name;	
	//商家ID
	private String shop;	
	//商品名称
	private String type;	
	//库位商品数量
	private int num;	
	//库位信息更新时间
	private Date lastUpdate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHJ() {
		return HJ;
	}

	public void setHJ(String hJ) {
		HJ = hJ;
	}

	public String getWZ() {
		return WZ;
	}

	public void setWZ(String wZ) {
		WZ = wZ;
	}

	public String getBW() {
		return BW;
	}

	public void setBW(String bW) {
		BW = bW;
	}

	public String getCS() {
		return CS;
	}

	public void setCS(String cS) {
		CS = cS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
