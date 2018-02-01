package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分拣单明细
 * @author lenovo
 *
 */
@Entity
@Table(name = "sc_centro_pickingItem")
public class PickingItem implements Serializable {

	private static final long serialVersionUID = 1471770664397175407L;

	private int id;
	
	private Item item;//商品
	
	private int quantity;//数量
	
	private User user;//商家
	
	private CentroItem centroItem;//商品存放位置 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CentroItem getCentroItem() {
		return centroItem;
	}

	public void setCentroItem(CentroItem centroItem) {
		this.centroItem = centroItem;
	}
	
	
	
	
}
