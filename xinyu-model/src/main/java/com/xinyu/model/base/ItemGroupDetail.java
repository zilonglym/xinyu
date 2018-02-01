package com.xinyu.model.base;

import com.xinyu.model.common.Entity;

public class ItemGroupDetail  extends Entity {

	private  ItemGroup  itemGroup;
	
	private  Item  item;
	
	private  Long  num;

	public ItemGroup getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}

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
