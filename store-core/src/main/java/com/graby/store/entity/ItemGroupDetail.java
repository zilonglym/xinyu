package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sc_itemgroupdetail")
public class ItemGroupDetail  implements Serializable{

	private static final long serialVersionUID = 4749520819375302462L;
	
	private Long id;
	
	private String cu;
	
	private  ItemGroup  itemGroup;
	
	private  Item  item;
	
	private  Long  num;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCu() {
		return cu;
	}

	public void setCu(String cu) {
		this.cu = cu;
	}

	@ManyToOne
	@JoinColumn(name="itemgroup_id")
	public ItemGroup getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}

	@ManyToOne
	@JoinColumn(name="item_id")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
}
