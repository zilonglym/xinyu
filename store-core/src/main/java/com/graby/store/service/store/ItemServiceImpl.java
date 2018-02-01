package com.graby.store.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemMapping;

@Component
public class ItemServiceImpl {
	/*
	@Autowired
	private ItemDao itemDao;
	@Override
	public void saveItem(Item item) {
		// TODO Auto-generated method stub
		this.itemDao.saveItem(item);
	}

	@Override
	public void saveItemList(List<Item> itemList) {
		// TODO Auto-generated method stub
		for (Item item : itemList) {
			this.saveItem(item);
		}
	}
	
	
	
	@Transactional(readOnly = false)
	public void relateItem(String itemId,	com.taobao.api.domain.Item tbItem,	Long  skuid, String skuTitle ) {
		if (getRelatedItemId(""+tbItem.getNumIid(), skuid) == null) {
			ItemMapping mapping = new ItemMapping();
			Item item = new Item();
			item.setId(itemId);
			mapping.setItem(item);
			mapping.setNumIid(tbItem.getNumIid());
			mapping.setSkuId(skuid);
			mapping.setSkuTitle(skuTitle);
			mapping.setTitle(tbItem.getTitle());
			mapping.setDetailUrl(tbItem.getDetailUrl());
		//	itemMappingJpaDao.save(mapping);	
		//	itemDao.updateSku(itemId, skuTitle);
		}
	}
	
	/**
	 * 淘宝商品关联的商品ID
	 * @param itemId
	 * @param numIid
	 * @return
	public String getRelatedItemId(String numIid, Long skuId) {
		return itemDao.getRelatedItemId(numIid, skuId);
	}	

	/**
	 * 更新商品标题及sku
	 * @param itemId
	 * @param itemTitle
	 * @param skuTitle
	public void updateItemTitle(String itemId, String itemTitle, String skuTitle) {
		itemDao.updateItemTitle(itemId, itemTitle, skuTitle);
	}

	@Override
	public Item getItem(String id) {
		// TODO Auto-generated method stub
		return this.itemDao.getItem(id);
	}

	@Override
	public void updateSku(String itemId, String skuPropertiesName) {
		// TODO Auto-generated method stub
		this.itemDao.updateSku(itemId,skuPropertiesName);
	}
	*/
}
