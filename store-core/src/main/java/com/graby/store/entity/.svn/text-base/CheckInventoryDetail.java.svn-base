package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "sc_check_inventory_detail")
public class CheckInventoryDetail implements Serializable{

	private static final long serialVersionUID = -1052656898128385197L;

	private CheckInventory checkInventory;
	
	private Long id;
	
	private Item item;//商品
	
	private int quantity;//盘点数量
	
	
	private Centro centro;//入库仓库

	@Index(name="check_inventory_id")
	public CheckInventory getCheckInventory() {
		return checkInventory;
	}

	public void setCheckInventory(CheckInventory checkInventory) {
		this.checkInventory = checkInventory;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Index(name="item_id")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Index(name="centro_id")
	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}
	
	
	
}
