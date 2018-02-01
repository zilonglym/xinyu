package com.graby.store.service.store;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.service.item.ItemService;
import com.graby.store.util.SkuUtils;
import com.graby.store.web.top.TopApi;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Shop;
import com.taobao.api.domain.Sku;

@Component
public class SyncTopServiceImpl  {
	
	@Autowired
	private TopApi topApi;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ItemService itemService;
	 
	public String getNick(String sessionKey) throws Exception{
		return this.topApi.getNick(sessionKey);
	}

	 
	public Shop getShop(String shopName) throws Exception {
		// TODO Auto-generated method stub
		return this.topApi.getShop(shopName);
	}

	/**
	 * 同步所有淘宝商品（库存+在售）
	 * @throws ApiException
	 
	 
	public int syncItem(String sessionKey) throws Exception {
		// TODO Auto-generated method stub
		this.topApi.setSessionKey(sessionKey);
		List<Item> items = topApi.getItems("", 3000);
		if (CollectionUtils.isEmpty(items)) return 0;
		for (Item item : items) {
			// 如果未关联则插入商品
			// 并关联淘宝商品
			List<Sku> skus = item.getSkus();
			if (CollectionUtils.isEmpty(skus)) {
				sync(item, null);
			} else {
				for (Sku sku : skus) {
					sync(item, sku);
				}
			}
		}
		
		return items.size();
	}
	
	private void sync(Item item, Sku sku) {
		String relatedId = itemService.getRelatedItemId(item.getNumIid().toString(), sku == null ? 0L : sku.getSkuId());
		if (relatedId != null) {
			// 更新标题和SKU
			itemService.updateItemTitle(relatedId, item.getTitle(), sku == null ? "" : SkuUtils.convert(sku.getPropertiesName()));
			return;
		}
		com.xinyu.model.system.Item copy = new com.xinyu.model.system.Item();
		copy.setCode(String.valueOf(item.getProductId()));
		copy.setTitle(item.getTitle());
		copy.setWeight(Double.valueOf(0));
		System.err.println(item.getTitle());
		if (sku == null) {
			itemDao.saveItem(copy);
			itemService.relateItem(copy.getId(), item, 0L, null);	
		} else {
			String skuTitle = SkuUtils.convert(sku.getPropertiesName());
			copy.setSku(skuTitle);
			itemDao.saveItem(copy);
			itemService.relateItem(copy.getId(), item, sku.getSkuId(), skuTitle);
		}
	}
	
	/**
	 * 同步淘宝商品（单条）
	 * @param numIid
	 * @param skuId
	 * @throws ApiException
	 */
	public void sync(Long numIid, Long skuId) throws Exception  {
		Item item = topApi.getItem(numIid);
		Sku sku = topApi.getSku(numIid, skuId);
	//	sync(item, sku);
	}
}
