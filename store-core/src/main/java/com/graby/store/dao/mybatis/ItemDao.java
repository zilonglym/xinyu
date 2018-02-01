package com.graby.store.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.graby.store.base.MyBatisRepository;
import com.graby.store.entity.Item;


@MyBatisRepository
public interface ItemDao {
	
	List<Item> getItemsByString(String q);
	
	Item get(Long id);
	
	Item getItemByCode(Map<String,Object> params);
	
	Item getItemByName(Map<String,Object> params);
	
	List<Item> find();
	
	List<Item> findItems(Long userId);
	
	void updateBarCode(Map<String,Object> params);
	
	void updatePackageWeight(Map<String,Object> params);
	
	 void updateItemCode(Map<String,Object> params);
	 void updateItemTitles(Map<String,Object> params);
	
	Item findItmeByBarCode(String barCode);
	
	List<Item> findItmeByBarCodes(String barCode);
	
	Item findItmeByBarCodeAndUserId(Map<String,Object> params);
	// 删除
	void delete(Long id);
	
	// 获取用户商品
	long getTotalResults(Long userId, String q);
	
	// 用户获取商品(分页)
	List<Item> getItems(Long userId, String q, long start, long offset);
	
	// 解除关联淘宝商品
	void unRelate(Long itemId, Long numIid, Long skuId);
	
	// 解除所有关联
	void unRelateAll(Long itemId);
	
	// 更新商品标题及sku
	void updateItemTitle(Long numIid, String itemTitle, String skuTitle);
	
	// 更新商品 条码信息
	void updateItemBarCode(Long itemId, String braCode);
	
	
	// 更新商品sku
	void updateSku(Long itemId, String sku);
	
	//查询淘宝商品已关联的系统商品ID
	Long getRelatedItemId(Long numIid, Long skuId);
	
	// 查询已关联的淘宝商品
	Map<String,Object> related(Long itemId);
	
	// 更新商品库位
	void updatePositin(Long itemId, String position);
	
	long getItemsByPageCount(Map<String,Object> params);
	
	List<Item> getItemsByPage(Map<String,Object> params);

	Item getItemBySkuAndCode(Map<String, Object> map);

	List<Item> getItemListByParams(Map<String, Object> params);

	//修改商品类型
	void updateItemType(Map<String, Object> params);
	
}
