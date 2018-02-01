package com.graby.store.service.item;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.util.SkuUtils;
import com.graby.store.web.auth.ShiroContextUtils;
import com.graby.store.web.top.TopApi;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;

/**
 * 淘宝商品同步
 * @author huabiao.mahb
 */
@Component
public class ItemTopSync {
	
	@Autowired
	private TopApi topApi;
	
	@Autowired
	private ItemService itemService;
	
	public static final Logger logger = Logger.getLogger(ItemTopSync.class);
	/**
	 * 同步所有淘宝商品（库存+在售）
	 * @throws ApiException
	 */
	public void sync() throws ApiException {
		List<Item> items = topApi.getItems("", 500);
		if (CollectionUtils.isEmpty(items)) return;
		logger.debug("-------------------商品同步开始-------------");
		for (Item item : items) {
			
			// 如果未关联则插入商品
			// 并关联淘宝商品
			List<Sku> skus = item.getSkus();
			if (CollectionUtils.isEmpty(skus)) {
				sync(item, null);
			} else {
				for (Sku sku : skus) {
					logger.debug("sku信息:"+sku);
					sync(item, sku);
				}
			}
		}
		logger.debug("-------------------商品同步结束-------------");
	}
	
	private void sync(Item item, Sku sku) {
		try {
		
		Long relatedId = itemService.getRelatedItemId(item.getNumIid(), sku == null ? 0L : sku.getSkuId());
		logger.debug("商品名称:"+item.getTitle());
		logger.debug("商品属性:"+(sku == null ? 0L : sku.getPropertiesName()));
		logger.debug("商品UUID:"+(item.getNumIid()));
		logger.debug("商品SKUID:"+(sku == null ? 0L : sku.getSkuId()));
		logger.debug("relatedId:"+relatedId);
		if (relatedId != null) {
			logger.debug("更新商品");
			// 更新标题和SKU
			itemService.updateItemTitle(relatedId, item.getTitle(), sku == null ? "" : SkuUtils.convert(sku.getPropertiesName()));
			return;
		}
		/**
		 * 判断当前商品在数据库中是否存在。如果存在则不处理
		
		
		com.graby.store.entity.Item tempItem=this.itemService.getItemByCode(String.valueOf(item.getProductId()), String.valueOf(ShiroContextUtils.getUserid()));
		if(tempItem!=null){
			return;
		} */
		logger.debug("添加商品");
		com.graby.store.entity.Item copy = new com.graby.store.entity.Item();
		copy.setCode(String.valueOf(item.getOuterId()));//item.getProductId()
		copy.setTitle(item.getTitle());
		copy.setWeight(Double.valueOf(0));
		if (sku == null) {
			itemService.saveItem(copy);
			itemService.relateItem(copy.getId(), item, 0L, null);	
		} else {
			copy.setPosition(sku.getProperties());
			String skuTitle = SkuUtils.convert(sku.getPropertiesName());
			copy.setSku(skuTitle);
			itemService.saveItem(copy);
			itemService.relateItem(copy.getId(), item, sku.getSkuId(), skuTitle);
		}
		
		} catch(Exception e)  {
				logger.debug(e);
				e.printStackTrace();
			}
	}
	
	/**
	 * 同步淘宝商品（单条）
	 * @param numIid
	 * @param skuId
	 * @throws ApiException
	 */
	public void sync(Long numIid, Long skuId) throws ApiException {
		Item item = topApi.getItem(numIid);
		Sku sku = topApi.getSku(numIid, skuId);
		sync(item, sku);
	}

}
