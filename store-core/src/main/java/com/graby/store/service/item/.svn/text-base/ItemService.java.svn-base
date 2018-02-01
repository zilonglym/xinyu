package com.graby.store.service.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.ItemJpaDao;
import com.graby.store.dao.jpa.ItemMappingJpaDao;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemMapping;
import com.graby.store.entity.User;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.ApiException;

@Component
@Transactional
public class ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemJpaDao itemJpaDao;
	
	@Autowired
	private ItemMappingJpaDao itemMappingJpaDao;
	
	@Autowired
	private ItemTopSync itemTopSync;
	
	@Autowired
	private UserDao  userDao;
	
	
	public List<Item> find(){
		return itemDao.find();
	}
	
	public Page<Item> getItemsByPage(int page, int rows,Map<String,Object> params){
		int start = (page-1)*rows;
		int offset = rows;
		params.put("start", start);
		params.put("offset",offset);
		List<Item> itemList=this.itemDao.getItemsByPage(params);
		Long count=this.itemDao.getItemsByPageCount(params);
		PageRequest pageable = new PageRequest(page, rows);
		Page<Item> itemPage = new PageImpl<Item>(itemList, pageable, count);
		return itemPage;
	}
	
	@Transactional(readOnly = false)
	public void saveItem(Item item) {
		setCurrentUserid(item);
		itemJpaDao.save(item);
	}

	private void setCurrentUserid(Item item) {
		if (item.getUserid() == null) {
			item.setUserid(ShiroContextUtils.getUserid());	
		}
	}
	
	public void updateBarCode(Map<String,Object> params){
		this.itemDao.updateBarCode(params);
	}
	
	public void updateItemCode(Map<String,Object> params){
		this.itemDao.updateItemCode(params);
	}
	
	public void updateItemTitle(Map<String,Object> params){
		this.itemDao.updateItemTitles(params);
	}
	public void updatePackageWeight(Map<String,Object> params){
		this.itemDao.updatePackageWeight(params);
	}
	
	@Transactional(readOnly = false)
	public void saveItems(List<Item> items) {
		for (Item item : items) {
			User user=this.userDao.get(item.getUserid());
			item.setBrandName(user.getShopName());
			saveItem(item);
		}
	}
	
	public Item getItem(Long id) {
		return itemDao.get(id);
	}
	
	public Item getItemByCode(String code,String userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("code", code);
		params.put("userId", userId);
		return this.itemDao.getItemByCode(params);
	}
	
	/**
	 * 获取用户商品
	 * @param userId
	 * @param q TODO
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Item> findPageUserItems(Long userId, String q, int pageNo, int pageSize) {
		pageNo = pageNo -1;
		long start = pageNo*pageSize;
		q = "%"+q+"%";
		List<Item> items = itemDao.getItems(userId, q, start, pageSize);
		long total = itemDao.getTotalResults(userId, q);
		PageRequest pageable = new PageRequest((int)pageNo, (int)pageSize);
		Page<Item> page = new PageImpl<Item>(items, pageable, total);
		return page;
	}
	
	/**
	 * 关联淘宝商品
	 * @param itemId
	 * @param skuTitle TODO
	 * @param tbitemId
	 * @param tbItemTitle
	 * @param tbItemUrl
	 */
	@Transactional(readOnly = false)
	public void relateItem(Long itemId,	com.taobao.api.domain.Item tbItem,	Long  skuid, String skuTitle ) {
		if (getRelatedItemId(tbItem.getNumIid(), skuid) == null) {
			ItemMapping mapping = new ItemMapping();
			Item item = new Item();
			item.setId(itemId);
			mapping.setItem(item);
			mapping.setNumIid(tbItem.getNumIid());
			mapping.setSkuId(skuid);
			mapping.setSkuTitle(skuTitle);
			mapping.setTitle(tbItem.getTitle());
			mapping.setDetailUrl(tbItem.getDetailUrl());
			itemMappingJpaDao.save(mapping);	
			itemDao.updateSku(itemId, skuTitle);
		}
	}
	
	/**
	 * 解除淘宝商品关联
	 * @param itemId
	 * @param numIid
	 */
	public void unRelateItem(Long itemId, Long numIid, Long skuId) {
		if (getRelatedItemId(numIid, skuId) != null) {
			itemDao.unRelate(itemId, numIid, skuId);
			itemDao.updateSku(itemId, "");
		}
	}	
	
	/**
	 * 更新商品标题及sku
	 * @param itemId
	 * @param itemTitle
	 * @param skuTitle
	 */
	public void updateItemTitle(Long itemId, String itemTitle, String skuTitle) {
		itemDao.updateItemTitle(itemId, itemTitle, skuTitle);
	}
	
	
	/**
	 *  更新商品条码信息
	 * @param itemId
	 * @param braCode
	 */
	public void updateItemBarCode(Long itemId, String braCode) {
		itemDao.updateItemBarCode(itemId, braCode);
	}
	
	/**
	 * 更新商品sku
	 * @param itemId
	 * @param itemTitle
	 * @param skuTitle
	 */
	public void updateSku(Long itemId, String sku) {
		itemDao.updateSku(itemId, sku);
	}	
	
	/**
	 * 淘宝商品关联的商品ID
	 * @param itemId
	 * @param numIid
	 * @return
	 */
	public Long getRelatedItemId(Long numIid, Long skuId) {
		return itemDao.getRelatedItemId(numIid, skuId);
	}	
	
	/**
	 * 删除商品
	 * @param id
	 */
	public void deleteItem(Long id) {
		itemDao.unRelateAll(id);
		itemDao.delete(id);
	}

	/**
	 * 设置商品库位
	 * @param itemId
	 * @param position
	 */
	public void position(Long itemId, String position) {
		itemDao.updatePositin(itemId, position);
	}
	
	/**
	 * 同步所有淘宝商品
	 * @throws ApiException
	 */
	public void syncTop() throws ApiException {
		itemTopSync.sync();
	}
	
	/**
	 * 同步淘宝商品
	 * @param numIid
	 * @param skuId
	 * @throws ApiException
	 */
	public void syncTop(Long numIid,Long skuId) throws ApiException {
		itemTopSync.sync(numIid, skuId);
	}	
	public long getTotalResults(Long userId, String q){
		return itemDao.getTotalResults(userId, q);
	}
	
	public List<Item> getItemsByString(String q){
		return itemDao.getItemsByString(q);
	}
	public List<Item> findItems(Long userId){
		return this.itemDao.findItems(userId);
	}
	public Item findItmeByBarCodeAndUserId(Map<String,Object>  map){
		return itemDao.findItmeByBarCodeAndUserId(map);
	}
	
	public int findPageUserItemsCount(Long userId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		Long result= this.itemDao.getItemsByPageCount(params);
		return result.intValue();
	}
	
	public List<Item> findItmeByBarCodes(String barCode){
		return itemDao.findItmeByBarCodes(barCode);
	}


	public List<Item> getItemListByParams(Map<String, Object> params) {
		return itemDao.getItemListByParams(params);
	}

	public void updateItemType(Map<String, Object> params) {
		itemDao.updateItemType(params);
	}
}
